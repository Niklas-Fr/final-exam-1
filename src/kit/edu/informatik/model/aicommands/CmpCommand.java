package kit.edu.informatik.model.aicommands;

import kit.edu.informatik.model.AICommandTypes;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.memory.MemoryCell;

/**
 * The class models the CMP Command.
 * Inherited from {@link AICommand}.
 *
 * @author uqtwh
 * @version 1.0
 */
public class CmpCommand extends AICommand {
    private static final AICommandTypes COMMAND_TYPE = AICommandTypes.CMP;

    /**
     * Checks if the value of entryA of the source cell is equal to the value of entryB of the target cell and skips
     * the next command if not.
     * SourceIndex and targetIndex are derived from the currentAI and the memoryCells.
     * @param currentAI    current AI
     * @param memoryCells  memory
     */
    @Override
    public void execute(AIObject currentAI, MemoryCell[] memoryCells) {
        int currenIndex = currentAI.getIndex();
        int sourceIndex = cyclicalIndex(currenIndex, memoryCells[currenIndex].getAICommand().getEntryA(), memoryCells.length);
        int targetIndex = cyclicalIndex(currenIndex, memoryCells[currenIndex].getAICommand().getEntryB(), memoryCells.length);

        if (memoryCells[sourceIndex].getAICommand().getEntryA() != memoryCells[targetIndex].getAICommand().getEntryB()) {
            currentAI.setIndex(cyclicalIndex(currentAI.getIndex(), 1, memoryCells.length));
        }
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public AICommand copy() {
        AICommand result = new CmpCommand();
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
