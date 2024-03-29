package kit.edu.informatik.model.aicommands;

import kit.edu.informatik.model.AICommandTypes;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.memory.MemoryCell;

/**
 * The class AICommand is an abstract Superclass to represent a command of an AI.
 *
 * @author uqtwh
 * @version 1.0
 */
public abstract class AICommand {
    private int entryA;
    private int entryB;

    /**
     * Constructor of the AICommand.
     */
    protected AICommand() {
    }

    /**
     * Method to create a copy of the AICommand.
     * @return copy of the AICommand
     */
    public abstract AICommand copy();

    /**
     * Method to execute the AICommand.
     * @param currentAI    current AI
     * @param memoryCells  memory cells
     */
    public abstract void execute(AIObject currentAI, MemoryCell[] memoryCells);

    /**
     * Method to get the command type of the AICommand.
     * @return command type
     */
    public abstract AICommandTypes getCommandType();

    /**
     * Getter of the {@link #entryA}.
     * @return first entry A
     */
    public int getEntryA() {
        return entryA;
    }

    /**
     * Setter of the {@link #entryA}.
     * @param entryA new first entry A
     */
    public void setEntryA(int entryA) {
        this.entryA = entryA;
    }

    /**
     * Getter of the {@link #entryB}.
     * @return second entry B
     */
    public int getEntryB() {
        return entryB;
    }

    /**
     * Setter of the {@link #entryB}.
     * @param entryB new second entry B
     */
    public void setEntryB(int entryB) {
        this.entryB = entryB;
    }

    /**
     * Protected helper method for {@link #execute(AIObject, MemoryCell[])}, setting the symbol of a given index.
     * @param index        index to set the symbol for
     * @param currentAI    current AI
     * @param memoryCells  memory cells
     */
    protected void setSymbol(int index, AIObject currentAI, MemoryCell[] memoryCells) {
        memoryCells[index].setSymbol(commandIsBomb(memoryCells[index].getAICommand()) ? currentAI.getBombSymbol() : currentAI.getSymbol());
    }

    /**
     * Protected helper method for {@link #execute(AIObject, MemoryCell[])}, calculating the cyclical index of a given index.
     * @param index      index to calculate the cyclical index for
     * @param moveBy     value to move the index by
     * @param memorySize size of the memory
     * @return cyclical index
     */
    protected int cyclicalIndex(int index, int moveBy, int memorySize) {
        int targetIndex = (index + moveBy) % memorySize;
        return targetIndex < 0 ? memorySize + targetIndex : targetIndex;
    }

    /**
     * Private helper method for {@link #setSymbol(int, AIObject, MemoryCell[])}, checking if a given command is a bomb.
     * @param command command to check
     * @return {@code true} if the command is a bomb, {@code false} otherwise
     */
    private boolean commandIsBomb(AICommand command) {
        return command.getCommandType() == AICommandTypes.STOP
                || (command.getCommandType() == AICommandTypes.JMP && command.getEntryA() == 0)
                || (command.getCommandType() == AICommandTypes.JMZ && command.getEntryA() == 0 && command.getEntryB() == 0);
    }
}
