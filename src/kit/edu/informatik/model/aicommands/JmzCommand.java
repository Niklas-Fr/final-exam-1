package kit.edu.informatik.model.aicommands;

import kit.edu.informatik.model.AICommandTypes;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.memory.MemoryCell;

/**
 * The class models the JMZ Command.
 * Inherited from {@link AICommand}.
 *
 * @author uqtwh
 * @version 1.0
 */
public class JmzCommand extends AICommand {
    private static final AICommandTypes COMMAND_TYPE = AICommandTypes.JMZ;

    /**
     * Executes the JMZ Command, jumping to the targetIndex if the value of entryB of the checkcell is 0.
     * TargetIndex is derived from the currentAI and the memoryCells.
     * @param currentAI   current AI
     * @param memoryCells memory
     */
    @Override
    public void execute(AIObject currentAI, MemoryCell[] memoryCells) {
        int currentIndex = currentAI.getIndex();
        int checkcellIndex = cyclicalIndex(currentIndex, memoryCells[currentIndex].getAICommand().getEntryB(), memoryCells.length);

        if (memoryCells[checkcellIndex].getAICommand().getEntryB() == 0) {
            currentAI.setIndex(cyclicalIndex(currentIndex, memoryCells[currentIndex].getAICommand().getEntryA() - 1, memoryCells.length));
        }
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public AICommand copy() {
        AICommand result = new JmzCommand();
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
