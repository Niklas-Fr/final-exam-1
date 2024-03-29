package kit.edu.informatik.model;

import kit.edu.informatik.model.aicommands.AICommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class represents an AI Object.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class AIObject {
    private List<AICommand> aiCommands = new ArrayList<>();
    private String name;
    private String symbol;
    private String bombSymbol;
    private int counter = 0;
    private int index = -1;
    private int order = -1;
    private boolean isRunning = false;

    /**
     * Constructor of the class.
     * @param name name of the AI
     */
    public AIObject(String name) {
        this.name = name;
    }

    /**
     * Copy constructor of the class, taking additional parameters.
     * @param copyAI     AI to copy
     * @param symbol     the symbol of the AI
     * @param bombSymbol the bomb symbol of the AI
     * @param order      the order of the AI
     */
    public AIObject(AIObject copyAI, String symbol, String bombSymbol, int order) {
        this.name = copyAI.getName();
        this.symbol = symbol;
        this.bombSymbol = bombSymbol;
        this.order = order;
        this.aiCommands = new ArrayList<>(copyAI.getCommands());
        isRunning = true;
    }

    /**
     * Getter of the {@link #name}.
     * @return name of the AI
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the {@link #name}.
     * @param name new name of the AI
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the {@link #symbol}.
     * @return symbol of the AI
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Getter of the {@link #bombSymbol}.
     * @return bomb symbol of the AI
     */
    public String getBombSymbol() {
        return bombSymbol;
    }

    /**
     * Getter of the {@link #index}.
     * @return index of the AI
     */
    public int getIndex() {
        return index;
    }

    /**
     * Setter of the {@link #index}.
     * @param index new index of the AI
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter of the {@link #order}.
     * @return order of the AI
     */
    public int getOrder() {
        return order;
    }

    /**
     * Getter of the {@link #isRunning} state.
     * @return running state of the AI
     */
    public boolean getRunning() {
        return isRunning;
    }

    /**
     * Setter of the {@link #isRunning} state.
     * @param isRunning new running state of the AI
     */
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * Getter of the {@link #counter}.
     * @return counter of the AI
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Setter of the {@link #counter}, increasing it by the parameter.
     * @param counter value to increase the counter by
     */
    public void setCounter(int counter) {
        this.counter += counter;
    }

    /**
     * Adds a command to the {@link #aiCommands}.
     * @param command command to add
     */
    public void addCommand(AICommand command) {
        aiCommands.add(command);
    }

    /**
     * Getter of an unmodifiable copy of {@link #aiCommands}.
     * @return copy of the list of command.
     */
    public List<AICommand> getCommands() {
        return Collections.unmodifiableList(aiCommands);
    }
}
