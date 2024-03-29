package kit.edu.informatik.commands;

import kit.edu.informatik.exceptions.InvalidArgumentException;
import kit.edu.informatik.model.memory.Memory;
import kit.edu.informatik.model.memory.Phase;

/**
 * The class models removing an AI from the game.
 * Implements interface {@link Command}.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class RemoveAICommand implements Command {
    private static final String INVALID_ARGUMENT = "AI named \"%s\" does not exist.";
    private static final Phase ACCESSIBILITY_PHASE = Phase.INITIALIZING;

    /**
     * Overriding the execute method from Interface {@link Command}.
     * Removes a given AI from the memory.
     * @param memory           the {@link Memory memory} to execute the command on
     * @param commandArguments the arguments of the command
     * @return CommandResult of the execution with a {@link CommandResultType} and a message, depending on the result
     */
    @Override
    public CommandResult execute(Memory memory, Arguments commandArguments) {
        try {
            String name = commandArguments.nextString();
            commandArguments.assertNoMoreArguments();
            // Checking if an ai with the given name exists
            if (memory.getAI(name, memory.getAiList()).isEmpty()) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENT.formatted(name));
            }
            memory.removeAi(name);
            return new CommandResult(CommandResultType.SUCCESS, name);
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

