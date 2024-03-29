package kit.edu.informatik.model.memory;

import kit.edu.informatik.model.AICommandTypes;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.InstantiateCommand;
import kit.edu.informatik.model.aicommands.AICommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringJoiner;
import java.util.Optional;

/**
 * The class represents the memory of the game.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class Memory {
    private static final String DELIMITER = ", ";
    private static final String STATUS_PATTERN = "%s AIs: ";
    private static final String END_GAME_PATTERN = "%s%s%s";
    private static final String AI_NAME_PATTERN = "%s#%s";
    private static final String NEXT_PATTERN = "%s executed %d steps until stopping.";
    private static final String AI_STATUS_PATTERN = "%s (%s@%d)%s";
    private static final String AI_STATUS_COMMAND_PATTERN = "Next Command: %s|%d|%d @%d";
    private static final String[] STATUS = {"Running", "Stopped"};
    private final List<AIObject> aiList = new ArrayList<>();
    private final List<AIObject> runningAIs = new ArrayList<>();
    private final List<AIObject> stoppedAIs = new ArrayList<>();
    private final MemoryCell[] memoryCells;
    private final String[] memorySymbols;
    private final String[] aiSymbols;
    private final int max;
    private ExecuteAICommand commandExecutor;
    private Phase phase = Phase.INITIALIZING;
    private InitMode initMode = InitMode.INIT_MODE_STOP;
    private int seed = 0;
    private int currentIndex = 0;
    private int currentAIindex = 0;

    /**
     * Constructor of the class.
     * @param size      size of the memory
     * @param symbols   standard representation symbols of the memory
     * @param aiSymbols representation symbols of the AIs
     */
    public Memory(int size, String[] symbols, String[] aiSymbols) {
        memoryCells = new MemoryCell[size];
        this.memorySymbols = symbols.clone();
        this.aiSymbols = aiSymbols.clone();
        max = aiSymbols.length / 2;
    }
    /**
     * Initializes the game with a given list of AIs.
     * @param aiList list of AIs to play the game with
     */
    public void startGame(List<AIObject> aiList) {
        commandExecutor = new ExecuteAICommand();
        initializeMemory(seed);
        for (int i = 0; i < aiList.size(); i++) {
            runningAIs.add(new AIObject(aiList.get(i), aiSymbols[2 * i], aiSymbols[2 * i + 1], i));
        }
        phase = Phase.GAME;
        for (int i = 0; i < runningAIs.size(); i++) {
            // Calculating the positions of the AIs in the memory
            int positionConstant = (int) Math.floor(i * (double) memoryCells.length / aiList.size());
            int firstNonstopIndex = -1;
            // Loaidng the commands of the AIs into the memory
            for (AICommand aiCommand : runningAIs.get(i).getCommands()) {
                memoryCells[positionConstant] = new MemoryCell(aiCommand.copy(), runningAIs.get(i).getSymbol());
                if (aiCommand.getCommandType() != AICommandTypes.STOP && firstNonstopIndex == -1) {
                    firstNonstopIndex = positionConstant;
                }
                positionConstant++;
            }
            runningAIs.get(i).setIndex(firstNonstopIndex);
        }
        assignNames(runningAIs);
        currentAIindex = 0;
        currentIndex = runningAIs.get(0).getIndex();
    }
    /**
     * Private helper method for {@link #startGame(List)}, assigning names to the AIs in the list.
     * If a name occures more than once, a number, starting from 0, is added to the name to make it unique.
     * @param aiList list of AIs
     */
    private void assignNames(List<AIObject> aiList) {
        Map<String, Integer> nameCounter = new HashMap<>();
        List<Integer> counter = new ArrayList<>();
        for (AIObject currentAI : aiList) {
            // Getting the current count of the name and adding the name to the list with count + 1
            int count = nameCounter.getOrDefault(currentAI.getName(), -1);
            nameCounter.put(currentAI.getName(), count + 1);
            counter.add(count + 1);
        }
        // Setting the names according to the count of the names
        for (int i = 0; i < aiList.size(); i++) {
            if (nameCounter.get(aiList.get(i).getName()) > 0) {
                aiList.get(i).setName(AI_NAME_PATTERN.formatted(aiList.get(i).getName(), counter.get(i)));
            }
        }
    }
    /**
     * Private helper method for {@link #startGame(List)}, initializing with either STOP commands,
     * if the {@code seed} is 0, or randomly generated {@link AICommandTypes} with the {@code seed} otherwise.
     * @param seed seed of the initialization mode
     */
    private void initializeMemory(int seed) {
        Random random = new Random(seed);
        for (int i = 0; i < memoryCells.length; i++) {
            AICommandTypes commandType = AICommandTypes.values()[seed == 0 ? 0 : random.nextInt(AICommandTypes.values().length)];
            int entryA = seed == 0 ? 0 : random.nextInt();
            int entryB = seed == 0 ? 0 : random.nextInt();
            memoryCells[i] = new MemoryCell(InstantiateCommand.instantiateCommand(commandType, entryA, entryB), memorySymbols[0]);
        }
        this.seed = seed;
    }
    /**
     * Ends the game and returns a message about the stopped and running AIs.
     * Also resetting the memory and the lists of AIs, enabling a new game to be started.
     * @return message about the stopped and running AIs
     */
    public String endGame() {
        // Sort the AIs by their order in which they have been added to the game
        stoppedAIs.sort((firstAI, secondAI) -> Integer.compare(firstAI.getOrder(), secondAI.getOrder()));
        String message = END_GAME_PATTERN.formatted(
                runningAIs.isEmpty() ? "" : STATUS_PATTERN.formatted(STATUS[0]) + buildString(runningAIs),
                !runningAIs.isEmpty() && !stoppedAIs.isEmpty() ? System.lineSeparator() : "",
                stoppedAIs.isEmpty() ? "" : STATUS_PATTERN.formatted(STATUS[1]) + buildString(stoppedAIs));
        // Reset the memory and the lists of AIs
        runningAIs.clear();
        stoppedAIs.clear();
        phase = Phase.INITIALIZING;
        return message;
    }
    /**
     * Private helper method for {@link #endGame()}, building a String of the names of the AIs in a given list
     * with a {@link #DELIMITER} between each name.
     * @param aiList list of AIs
     * @return String of the names of the AIs
     */
    private String buildString(List<AIObject> aiList) {
        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        for (AIObject currentAI : aiList) {
            stringJoiner.add(currentAI.getName());
        }
        return stringJoiner.toString();
    }
    /**
     * The method executes a given amount of turns of the game.
     * If an AI stops during the execution, the method returns a message about the stopped AI.
     * @param steps amount of turns to execute
     * @return message about the stopped AI or {@code null} if no AI stopped
     */
    public String next(int steps) {
        List<AIObject> stoppedThisRound = new ArrayList<>();
        for (int i = 0; i < steps && !runningAIs.isEmpty(); i++) {
            Optional<AIObject> stoppedAI = commandExecutor.executeCommand(currentIndex, runningAIs.get(currentAIindex), memoryCells);
            // If an AI has been stopped, remove it from the List of running AIs
            if (stoppedAI.isPresent()) {
                stoppedThisRound.add(stoppedAI.get());
                stoppedAIs.add(stoppedAI.get());
                runningAIs.remove(stoppedAI.get());
            }

            if (!runningAIs.isEmpty()) {
                // Calculate the next index of the AI whose turn it is now
                if (stoppedAI.isEmpty()) {
                    currentAIindex = (currentAIindex + 1) % runningAIs.size();
                }
                currentAIindex %= runningAIs.size();
                currentIndex = runningAIs.get(currentAIindex).getIndex();
            } else {
                currentIndex = -1;
            }
        }
        // If there are AIs that have been stopped, return a message about them
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        for (AIObject currentAI : stoppedThisRound) {
            stringJoiner.add(NEXT_PATTERN.formatted(currentAI.getName(), currentAI.getCounter() - 1));
        }
        return stringJoiner.length() == 0 ? null : stringJoiner.toString();
    }
    /**
     * Returns the current state of a given AI as a String.
     * If the AI is still running, the next command is also shown.
     * @param currentAI the AI to show the state of
     * @return state of the AI as a String
     */
    public String showAI(AIObject currentAI) {
        AICommand command = memoryCells[currentAI.getIndex()].getAICommand();
        String nextCommand = AI_STATUS_COMMAND_PATTERN.formatted(
                String.valueOf(command.getCommandType()), command.getEntryA(), command.getEntryB(), currentAI.getIndex());
        return AI_STATUS_PATTERN.formatted(currentAI.getName(),
                STATUS[currentAI.getRunning() ? 0 : 1].toUpperCase(), currentAI.getCounter(),
                currentAI.getRunning() ? System.lineSeparator() + nextCommand : "");
    }
    /**
     * Returns a string representation of the memory. If the given index is not -1, a more detailed
     * representation of the memory, from {@code index} , is returned.
     * @param index index of the representation
     * @return String representation of the memory
     */
    public String getMemory(int index) {
        return new StringRepresentator(memoryCells, runningAIs, memorySymbols).represent(index, currentIndex);
    }
    /**
     * Getter of the {@link #phase}.
     * @return phase of the game
     */
    public Phase getPhase() {
        return phase;
    }
    /**
     * Getter of the {@link #seed} of the initialization mode.
     * @return seed of the initialization mode
     */
    public int getSeed() {
        return seed;
    }
    /**
     * Sets the {@link #seed} of the initialization mode.
     * @param seed new seed of the initialization mode
     */
    public void setSeed(int seed) {
        this.seed = seed;
    }
    /**
     * Returns the size of the memory.
     * @return size of the memory
     */
    public int getSize() {
        return memoryCells.length;
    }
    /**
     * Getter of the maximum amount of playable AIs {@link #max}.
     * @return maximum amount of playable AIs
     */
    public int getMax() {
        return max;
    }
    /**
     * Getter of the {@link #initMode}.
     * @return init mode of the game
     */
    public InitMode getInitMode() {
        return initMode;
    }
    /**
     * Setter of the {@link #initMode}.
     * @param initMode new init mode of the game
     */
    public void setInitMode(InitMode initMode) {
        this.initMode = initMode;
    }
    /**
     * Adds an AI to the {@link #aiList}.
     * @param addAI AI to add
     */
    public void addAI(AIObject addAI) {
        aiList.add(addAI);
    }
    /**
     * Removes the AI with a given name from the {@link #aiList}.
     * @param aiName name of the AI
     */
    public void removeAi(String aiName) {
        aiList.removeIf(ai -> ai.getName().equals(aiName));
    }
    /**
     * Returns the {@link AIObject} from a List of AIs with a given name.
     * @param aiName name of the AI
     * @param aiList list of AIs
     * @return Optional of the AI or an empty Optional if no such AI exists
     */
    public Optional<AIObject> getAI(String aiName, List<AIObject> aiList) {
        for (AIObject currentAI : aiList) {
            if (aiName.equals(currentAI.getName())) {
                return Optional.of(currentAI);
            }
        }
        return Optional.empty();
    }
    /**
     * Returns the AI currently playing the game with a given name.
     * @param name name of the AI
     * @return Optional of the AI or an empty Optional if no such AI exists
     */
    public Optional<AIObject> getNameAI(String name) {
        return getAI(name, getAI(name, runningAIs).isEmpty() ? stoppedAIs : runningAIs);
    }
    /**
     * Getter of the {@link #aiList}.
     * @return unmodifiable list of AIs
     */
    public List<AIObject> getAiList() {
        return Collections.unmodifiableList(aiList);
    }
}
