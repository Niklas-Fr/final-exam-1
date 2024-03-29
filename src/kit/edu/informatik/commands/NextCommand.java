package kit.edu.informatik.commands;

import kit.edu.informatik.exceptions.InvalidArgumentException;
import kit.edu.informatik.model.memory.Memory;
import kit.edu.informatik.model.memory.Phase;

/**
 * The class models executing steps in the memory.
 * Implements interface {@link Command}.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class NextCommand implements Command {
    private static final Phase ACCESSIBILITY_PHASE = Phase.GAME;
    private static final int DEFAULT_VALUE = 1;

    /**
     * Overriding the execute method from Interface {@link Command}.
     * Simulates a given number of steps in the memory.
     * @param memory           the {@link Memory memory} to execute the command on
     * @param commandArguments the arguments of the command
     * @return CommandResult of the execution with a {@link CommandResultType type} and a message, depending on the result
     */
    @Override
    public CommandResult execute(Memory memory, Arguments commandArguments) {
        try {
            int steps = DEFAULT_VALUE;
            if (commandArguments.getLength() == 0) {
                return new CommandResult(CommandResultType.SUCCESS, memory.next(steps));
            }

            steps = commandArguments.nextInt();
            commandArguments.assertNoMoreArguments();
            return new CommandResult(CommandResultType.SUCCESS, memory.next(steps));
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


