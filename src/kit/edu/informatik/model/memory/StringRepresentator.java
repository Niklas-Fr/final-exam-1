package kit.edu.informatik.model.memory;

import kit.edu.informatik.model.aicommands.AICommand;
import kit.edu.informatik.model.AIObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * The class enables a representation of the {@link Memory} as a String.
 * 
 * @author uqtwh
 * @version 1.0
 */
public final class StringRepresentator {
    private static final String COMMAND_OUTPUT_PATTERN = "%s %s: %s | %s | %s";
    private static final String[] PAD_COMMAND_PATTERN = {"%", "s"};
    private static final int SHOW_MEMORY_RANGE = 10;
    private static final int MEMORY_INDEX = 0;
    private static final int COMMAND_ENTRY_A_INDEX = 1;
    private static final int COMMAND_ENTRY_B_INDEX = 2;
    private static final int COMMAND_TYPE_INDEX = 3;
    private static final int AREA_DISPLAY_INDEX = 1;
    private static final int CURRENT_AI_COMMAND_INDEX = 2;
    private static final int NEXT_AI_COMMAND_INDEX = 3;
    private final MemoryCell[] memoryCells;
    private final List<AIObject> runningAIs;
    private final String[] symbols;
    private int currentIndex;

    /**
     * Constructor for the class.
     * @param memoryCells the memory cells
     * @param runngingAIs the running AIs
     * @param symbols     the symbols
     */
    public StringRepresentator(MemoryCell[] memoryCells, List<AIObject> runngingAIs, String[] symbols) {
        this.memoryCells = memoryCells.clone();
        this.runningAIs = new ArrayList<>(runngingAIs);
        this.symbols = symbols.clone();
    }

    /**
     * The method returns a String representation of the memory.
     * @param index        given index
     * @param currentIndex current index of the game
     * @return String representation of the memory
     */
    public String represent(int index, int currentIndex) {
        this.currentIndex = currentIndex;
        List<String> memorySymbols = new ArrayList<>();
        for (int i = 0; i < memoryCells.length; i++) {
            memorySymbols.add(getCorrectSymbol(i));
        }
        if (index == -1) {
            return buildString(memorySymbols);
        }
        // Calculating the upper index for the memory view
        int upperIndex = index + SHOW_MEMORY_RANGE;
        if (index + SHOW_MEMORY_RANGE > memoryCells.length) {
            upperIndex = Math.min(index + SHOW_MEMORY_RANGE - memoryCells.length, index);
            upperIndex = upperIndex == 0 ? memoryCells.length : upperIndex;
        }
        // Inserting the display symbols
        List<String> firstPart = new ArrayList<>(memorySymbols.subList(0, Math.min(index, upperIndex)));
        List<String> secondPart = new ArrayList<>(memorySymbols.subList(Math.min(index, upperIndex), Math.max(index, upperIndex)));
        List<String> thirdPart = new ArrayList<>(memorySymbols.subList(Math.max(index, upperIndex), memoryCells.length));

        firstPart.add(symbols[AREA_DISPLAY_INDEX]);
        secondPart.add(symbols[AREA_DISPLAY_INDEX]);
        secondPart.addAll(thirdPart);
        firstPart.addAll(secondPart);
        return buildString(firstPart) + System.lineSeparator() + extendedView(index, upperIndex == memoryCells.length ? 0 : upperIndex);
    }

    /**
     * Private helper method for {@link #represent(int, int)}, extending the view of the memory.
     * @param lowerIndex lower index
     * @param upperIndex upper index
     * @return extended view of the memory
     */
    private String extendedView(int lowerIndex, int upperIndex) {
        boolean overflow = false;
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        for (int i = lowerIndex; i < lowerIndex + SHOW_MEMORY_RANGE; i++) {
            overflow = overflow || i > memoryCells.length - 1;
            int targetIndex = i > memoryCells.length - 1 ? i - memoryCells.length : i;
            if (!overflow || targetIndex < upperIndex) {
                int[] lengths = getLongestLength(lowerIndex, lowerIndex + SHOW_MEMORY_RANGE);
                AICommand command = memoryCells[targetIndex].getAICommand();
                stringJoiner.add(COMMAND_OUTPUT_PATTERN.formatted(getCorrectSymbol(targetIndex),
                        padString(targetIndex, lengths[MEMORY_INDEX]),
                        padString(command.getCommandType(), lengths[COMMAND_TYPE_INDEX]),
                        padString(command.getEntryA(), lengths[COMMAND_ENTRY_A_INDEX]),
                        padString(String.valueOf(command.getEntryB()), lengths[COMMAND_ENTRY_B_INDEX])));
            }
        }
        return stringJoiner.toString();
    }

    /**
     * Private helper method for {@link #represent(int, int)}, getting the correct symbol for a given index,
     * meaning either the symbols in the memory, or if a running AI is currently at the index, one of the corresponding symbols.
     * @param index index of the command
     * @return correct symbol
     */
    private String getCorrectSymbol(int index) {
        if (index == currentIndex) {
            return symbols[CURRENT_AI_COMMAND_INDEX];
        }
        for (AIObject currentAI : runningAIs) {
            if (currentAI.getIndex() == index && currentAI.getRunning()) {
                return symbols[NEXT_AI_COMMAND_INDEX];
            }
        }
        return memoryCells[index].getSymbol();
    }
    /**
     * Private helper method for {@link #represent(int, int)}, return an Array of the lengths of
     * the longest elements of the entries of the {@link AICommand}s.
     * @param lowerBound lower bound of the range
     * @param upperBound upper bound of the range
     * @return Array with the lengths
     */
    private int[] getLongestLength(int lowerBound, int upperBound) {
        int[] result = {-1, -1, -1, -1};
        for (int i = lowerBound; i < upperBound; i++) {
            int index = i % memoryCells.length;
            AICommand aiCommand = memoryCells[index].getAICommand();
            result[MEMORY_INDEX] = Math.max(result[MEMORY_INDEX], String.valueOf(index).length());
            result[COMMAND_ENTRY_A_INDEX] = Math.max(result[COMMAND_ENTRY_A_INDEX], String.valueOf(aiCommand.getEntryA()).length());
            result[COMMAND_ENTRY_B_INDEX] = Math.max(result[COMMAND_ENTRY_B_INDEX], String.valueOf(aiCommand.getEntryB()).length());
            result[COMMAND_TYPE_INDEX] = Math.max(result[COMMAND_TYPE_INDEX], String.valueOf(aiCommand.getCommandType()).length());
        }
        return result;
    }
    /**
     * Private helper method for {@link #represent(int, int)}, padding a string with a given amount of spaces.
     * @param word     String to pad
     * @param padSize  amount of spaces to pad with
     * @return padded String
     */
    private String padString(Object word, int padSize) {
        return (PAD_COMMAND_PATTERN[0] + padSize + PAD_COMMAND_PATTERN[1]).formatted(String.valueOf(word));
    }

    /**
     * Private helper method for {@link #represent(int, int)}, building a String from a given List of Strings.
     * @param stringList List of Strings
     * @return built String
     */
    private String buildString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String symbol : stringList) {
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
}
