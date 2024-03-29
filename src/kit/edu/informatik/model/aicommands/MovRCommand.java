package kit.edu.informatik.model.aicommands;

import kit.edu.informatik.model.AICommandTypes;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.memory.MemoryCell;

/**
 * The class models the MOV_R Command.
 * Inherited from {@link AICommand}.
 *
 * @author uqtwh
 * @version 1.0
 */
public class MovRCommand extends AICommand {
    private static final AICommandTypes COMMAND_TYPE = AICommandTypes.MOV_R;

    /**
     * Moves the AICommand from the sourceIndex to the targetIndex.
     * SourceIndex and targetIndex are derived from the currentAI and the memoryCells.
     * @param currentAI    current AI
     * @param memoryCells  memory
     */
    @Override
    public void execute(AIObject currentAI, MemoryCell[] memoryCells) {
        int currentIndex = currentAI.getIndex();
        int sourceIndex = cyclicalIndex(currentIndex, memoryCells[currentIndex].getAICommand().getEntryA(), memoryCells.length);
        int targetIndex = cyclicalIndex(currentIndex, memoryCells[currentIndex].getAICommand().getEntryB(), memoryCells.length);

        memoryCells[targetIndex].setAICommand(memoryCells[sourceIndex].getAICommand().copy());

        setSymbol(targetIndex, currentAI, memoryCells);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public AICommand copy() {
        AICommand result = new MovRCommand();
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
