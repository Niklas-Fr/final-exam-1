package kit.edu.informatik.commands;

/**
 * The enum models the {@link Command}s a user can execute.
 *
 * @author uqtwh
 * @version 1.0
 */
public enum UserCommands {

    /**
     * Adding a new AI.
     */
    ADD_AI,

    /**
     * Ending the current game.
     */
    END_GAME,

    /**
     * Listing all commands.
     */
    HELP,

    /**
     * Executing AI commands.
     */
    NEXT,

    /**
     * Quitting the game.
     */
    QUIT,

    /**
     * Removing an AI.
     */
    REMOVE_AI,

    /**
     * Setting the initialization mode of the game.
     */
    SET_INIT_MODE,

    /**
     * Showing the status of an AI.
     */
    SHOW_AI,

    /**
     * Showing the status of the memory.
     */
    SHOW_MEMORY,

    /**
     * Starting a new game.
     */
    START_GAME;

    private static final String ENUM_DELIMITER = "_";
    private static final String STRING_DELIMITER = "-";

    @Override
    public String toString() {
        return this.name().toLowerCase().replace(ENUM_DELIMITER, STRING_DELIMITER);
    }
}
