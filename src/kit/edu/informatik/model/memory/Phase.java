package kit.edu.informatik.model.memory;

import kit.edu.informatik.model.aicommands.AICommand;

/**
 * The enum represents the different phases of the game, modeling accessibility of {@link AICommand}s.
 *
 * @author uqtwh
 * @version 1.0
 */
public enum Phase {

    /**
     * The game is in the initializing phase / the command is accessible in the initializing phase.
     */
    INITIALIZING,

    /**
     * The game is in the game phase / the command is accessible in the game phase.
     */
    GAME,

    /**
     * The command is accessible in both phases.
     */
    BOTH;
}
