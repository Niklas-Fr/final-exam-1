package kit.edu.informatik.model.aicommands;

import kit.edu.informatik.model.AICommandTypes;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.memory.MemoryCell;

/**
 * The class models the ADD_R Command.
 * Inherited from {@link AICommand}.
 *
 * @author uqtwh
 * @version 1.0
 */
public class AddRCommand extends AICommand {
    private static final AICommandTypes COMMAND_TYPE = AICommandTypes.ADD_R;

    /**
     * Adds the value of entryA of the current cell to the value of entryB of the target cell.
     * TargetIndex is derived from the currentAI and the memoryCells.
     * @param currentAI    current AI
     * @param memoryCells  memory
     */
    @Override
    public void execute(AIObject currentAI, MemoryCell[] memoryCells) {
        int targetIndex = cyclicalIndex(currentAI.getIndex(), memoryCells[currentAI.getIndex()].getAICommand().getEntryB(),
                memoryCells.length);
        AICommand command = memoryCells[targetIndex].getAICommand();
        command.setEntryB(memoryCells[currentAI.getIndex()].getAICommand().getEntryA() + command.getEntryB());

        setSymbol(targetIndex, currentAI, memoryCells);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public AICommand copy() {
        AICommand result = new AddRCommand();
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
