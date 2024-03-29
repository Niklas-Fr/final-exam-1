package kit.edu.informatik.model.aicommands;

import kit.edu.informatik.model.AICommandTypes;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.memory.MemoryCell;

/**
 * The class models the SWAP Command.
 * Inherited from {@link AICommand}.
 *
 * @author uqtwh
 * @version 1.0
 */
public class SwapCommand extends AICommand {
    private static final AICommandTypes COMMAND_TYPE = AICommandTypes.SWAP;

    /**
     * Swaps entryA of sourceIndex with entryB of targetIndex.
     * SourceIndex and targetIndex are derived from the currentAI and the memoryCells.
     * @param currentAI    current AI
     * @param memoryCells  memory
     */
    @Override
    public void execute(AIObject currentAI, MemoryCell[] memoryCells) {
        int currentIndex = currentAI.getIndex();
        int sourceIndex = cyclicalIndex(currentIndex, memoryCells[currentIndex].getAICommand().getEntryA(), memoryCells.length);
        int targetIndex = cyclicalIndex(currentIndex, memoryCells[currentIndex].getAICommand().getEntryB(), memoryCells.length);

        int swap = memoryCells[sourceIndex].getAICommand().getEntryA();
        memoryCells[sourceIndex].getAICommand().setEntryA(memoryCells[targetIndex].getAICommand().getEntryB());
        memoryCells[targetIndex].getAICommand().setEntryB(swap);

        setSymbol(sourceIndex, currentAI, memoryCells);
        setSymbol(targetIndex, currentAI, memoryCells);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public AICommand copy() {
        AICommand result = new SwapCommand();
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
