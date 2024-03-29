package kit.edu.informatik.model.memory;

import kit.edu.informatik.model.aicommands.AICommand;

/**
 * The class models a cell of the {@link Memory}.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class MemoryCell {
    private AICommand aiCommand;
    private String symbol;

    /**
     * Constructor for the MemoryCell class.
     * @param aiCommand the AICommand
     * @param symbol    the symbol
     */
    public MemoryCell(AICommand aiCommand, String symbol) {
        this.aiCommand = aiCommand;
        this.symbol = symbol;
    }

    /**
     * Gets the {@link #aiCommand}.
     * @return the AICommand
     */
    public AICommand getAICommand() {
        return aiCommand;
    }

    /**
     * Sets the {@link #aiCommand}.
     * @param aiCommand the AICommand
     */
    public void setAICommand(AICommand aiCommand) {
        this.aiCommand = aiCommand;
    }

    /**
     * Gets the {@link #symbol}.
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the {@link #symbol}.
     * @param symbol the symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
