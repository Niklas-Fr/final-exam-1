package kit.edu.informatik.model.memory;

import kit.edu.informatik.model.aicommands.AICommand;

/**
 * The enum represents the different modes of initialization for the {@link Memory}.
 *
 * @author uqtwh
 * @version 1.0
 */
public enum InitMode {

    /**
     * The mode of initialization is a random {@link AICommand}.
     */
    INIT_MODE_RANDOM,

    /**
     * The mode of initialization is STOP.
     */
    INIT_MODE_STOP;
}
