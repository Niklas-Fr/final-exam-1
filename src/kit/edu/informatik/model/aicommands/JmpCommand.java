package kit.edu.informatik.model.aicommands;

import kit.edu.informatik.model.AICommandTypes;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.memory.MemoryCell;

/**
 * The class models the JMP Command.
 * Inherited from {@link AICommand}.
 *
 * @author uqtwh
 * @version 1.0
 */
public class JmpCommand extends AICommand {
    private static final AICommandTypes COMMAND_TYPE = AICommandTypes.JMP;

    /**
     * Executes the JMP Command, jumping to the targetIndex.
     * TargetIndex is derived from the currentAI and the memoryCells.
     * @param currentAI   current AI
     * @param memoryCells memory
     */
    @Override
    public void execute(AIObject currentAI, MemoryCell[] memoryCells) {
        currentAI.setIndex(cyclicalIndex(currentAI.getIndex(),
                memoryCells[currentAI.getIndex()].getAICommand().getEntryA() - 1, memoryCells.length));
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public AICommand copy() {
        AICommand result = new JmpCommand();
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
