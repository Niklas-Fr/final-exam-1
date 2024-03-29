package kit.edu.informatik.commands;

import kit.edu.informatik.exceptions.InvalidArgumentException;
import kit.edu.informatik.model.memory.Memory;
import kit.edu.informatik.model.memory.Phase;

/**
 * The class models showing the status of an ai in the game.
 * Implements interface {@link Command}.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class ShowAICommand implements Command {
    private static final String INVALID_ARGUMENT = "AI named \"%s\" does not exist.";
    private static final Phase ACCESSIBILITY_PHASE = Phase.GAME;

    /**
     * Overriding the execute method from Interface {@link Command}.
     * Shows the current status of a given AI.
     * @param memory           the {@link Memory memory} to execute the command on
     * @param commandArguments the arguments of the command
     * @return CommandResult of the execution with a {@link CommandResultType type} and a message, depending on the result
     */
    @Override
    public CommandResult execute(Memory memory, Arguments commandArguments) {
        try {
            String name = commandArguments.nextString();
            commandArguments.assertNoMoreArguments();
            if (memory.getNameAI(name).isEmpty()) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENT.formatted(name));
            }
            return new CommandResult(CommandResultType.SUCCESS, memory.showAI(memory.getNameAI(name).get()));
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

