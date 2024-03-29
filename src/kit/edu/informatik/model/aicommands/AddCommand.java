package kit.edu.informatik.model.aicommands;

import kit.edu.informatik.model.AICommandTypes;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.memory.MemoryCell;

/**
 * The class models the ADD Command.
 * Inherited from {@link AICommand}.
 *
 * @author uqtwh
 * @version 1.0
 */
public class AddCommand extends AICommand {
    private static final AICommandTypes COMMAND_TYPE = AICommandTypes.ADD;

    /**
     * Adds the value of entryA of the current cell to the value of entryB of the current cell.
     * @param currentAI    current AI
     * @param memoryCells  memory
     */
    @Override
    public void execute(AIObject currentAI, MemoryCell[] memoryCells) {
        AICommand command = memoryCells[currentAI.getIndex()].getAICommand();
        command.setEntryB(command.getEntryA() + command.getEntryB());

        setSymbol(currentAI.getIndex(), currentAI, memoryCells);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public AICommand copy() {
        AICommand result = new AddCommand();
        result.setEntryA(this.getEntryA());
        result.setEntryB(this.getEntryB());
        return result;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public AICommandTypes getCommandType() {
        return COMMAND_TYPE;
    }
}
