package kit.edu.informatik.commands;

import kit.edu.informatik.exceptions.InvalidArgumentException;
import kit.edu.informatik.model.memory.Memory;
import kit.edu.informatik.model.memory.Phase;

/**
 * The class models showing the memory of the game.
 * Implements interface {@link Command}.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class ShowMemoryCommand implements Command {
    private static final String ARGUMENTS_OUT_OF_BOUNDS = "argument %d is out of bounds of %d to %d.";
    private static final Phase ACCESSIBILITY_PHASE = Phase.GAME;

    /**
     * Overriding the execute method from Interface {@link Command}.
     * Shows the current status of the memory.
     * @param memory           the {@link Memory memory} to execute the command on
     * @param commandArguments the arguments of the command
     * @return CommandResult of the execution with a {@link CommandResultType type} and a message, depending on the result
     */
    @Override
    public CommandResult execute(Memory memory, Arguments commandArguments) {
        try {
            int index = -1;
            // Validating possible arguments
            if (commandArguments.getLength() > 0) {
                index = commandArguments.nextInt();
                if (index < 0 || index > memory.getSize() - 1) {
                    return new CommandResult(CommandResultType.FAILURE, ARGUMENTS_OUT_OF_BOUNDS.formatted(index, 0, memory.getSize() - 1));
                }
            }

            commandArguments.assertNoMoreArguments();
            return new CommandResult(CommandResultType.SUCCESS, memory.getMemory(index));
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

