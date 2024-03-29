package kit.edu.informatik.commands;

import kit.edu.informatik.model.memory.Phase;
import kit.edu.informatik.exceptions.InvalidArgumentException;
import kit.edu.informatik.model.memory.Memory;

/**
 * The class models ending the game.
 * Implements interface {@link Command}.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class EndGameCommand implements Command {
    private static final Phase ACCESSIBILITY_PHASE = Phase.GAME;

    /**
     * Overriding the execute method from Interface {@link Command}.
     * Ends the current game.
     * @param memory           the {@link Memory memory} to execute the command on
     * @param commandArguments the arguments of the command
     * @return CommandResult of the execution with a {@link CommandResultType type} and a message, depending on the result
     */
    @Override
    public CommandResult execute(Memory memory, Arguments commandArguments) {
        try {
            commandArguments.assertNoMoreArguments();
            return new CommandResult(CommandResultType.SUCCESS, memory.endGame());
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

