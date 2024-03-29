package kit.edu.informatik.commands;

import kit.edu.informatik.model.memory.Memory;
import kit.edu.informatik.model.memory.Phase;

/**
 * This interface represents an executable command.
 *
 * @author Programmieren-Team
 * @author uqtwh
 * @version 1.0
 */
public interface Command {

    /**
     * Executes the command.
     * @param memory           the {@link Memory memory} to execute the command on
     * @param commandArguments the arguments of the command
     * @return the result of the command
     */
    CommandResult execute(Memory memory, Arguments commandArguments);

    /**
     * Returns the accessibility level of the command.
     * @return accessibiltiy level of the command.
     */
    Phase accessibilityPhase();
}
