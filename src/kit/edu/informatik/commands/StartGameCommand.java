package kit.edu.informatik.commands;

import kit.edu.informatik.exceptions.InvalidArgumentException;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.memory.Memory;
import kit.edu.informatik.model.memory.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * The class models starting the game.
 * Implements interface {@link Command}.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class StartGameCommand implements Command {
    private static final String INVALID_ARGUMENTS = "number of AIs has to be between %d and %d, %d found.";
    private static final String INVALID_AI_NAME = "AI named \"%s\" does not exist.";
    private static final String SUCCESS_MESSAGE = "Game started.";
    private static final String INVALID_AI_POSITION = "since there is an uneven amount of memory cells, "
            + "the first player is only allowed to have %d commands";
    private static final Phase ACCESSIBILITY_PHASE = Phase.INITIALIZING;
    private static final int MINIMUM_AMOUNT_AIS = 2;

    /**
     * Overriding the execute method from Interface {@link Command}.
     * Starts the game with the given AIs.
     * @param memory           the {@link Memory memory} to execute the command on
     * @param commandArguments the arguments of the command
     * @return CommandResult of the execution with a {@link CommandResultType type} and a message, depending on the result
     */
    @Override
    public CommandResult execute(Memory memory, Arguments commandArguments) {
        try {
            int amount = commandArguments.getLength();

            // Checking for invalid amount of AIs
            if (amount < 2 || amount > memory.getMax()) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENTS.formatted(
                        MINIMUM_AMOUNT_AIS, memory.getMax(), amount));
            }

            // Checking if AIs with the given names exist
            List<AIObject> aiList = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                String name = commandArguments.nextString();
                if (memory.getAI(name, memory.getAiList()).isEmpty()) {
                    return new CommandResult(CommandResultType.FAILURE, INVALID_AI_NAME.formatted(name));
                }
                aiList.add(memory.getAI(name, memory.getAiList()).get());
            }

            int positionConstant = (int) Math.floor((double) memory.getSize() / aiList.size());
            if (aiList.get(0).getCommands().size() > positionConstant) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_AI_POSITION.formatted(positionConstant));
            }

            memory.startGame(aiList);
            return new CommandResult(CommandResultType.SUCCESS, SUCCESS_MESSAGE);
        } catch (InvalidArgumentException e) {
            return new CommandResult(CommandResultType.FAILURE, e.getMessage());
        }
    }

    /**
     * Returns the {@link #ACCESSIBILITY_PHASE} in which the command is accessible.
     * @return accessibility phase
     */
    @Override
    public Phase accessibilityPhase() {
        return ACCESSIBILITY_PHASE;
    }
}

