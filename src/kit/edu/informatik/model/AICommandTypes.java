package kit.edu.informatik.model;

import kit.edu.informatik.model.aicommands.AICommand;

/**
 * The enum represents the different types of {@link AICommand} an AI can execute.
 *
 * @author uqtwh
 * @version 1.0
 */
public enum AICommandTypes {

    /**
     * The command is STOP.
     */
    STOP,

    /**
     * The command is MOV_R.
     */
    MOV_R,

    /**
     * The command is MOV_I.
     */
    MOV_I,

    /**
     * The command is ADD.
     */
    ADD,

    /**
     * The command is ADD_R.
     */
    ADD_R,

    /**
     * The command is JMP.
     */
    JMP,

    /**
     * The command is JMZ.
     */
    JMZ,

    /**
     * The command is CMP.
     */
    CMP,

    /**
     * The command is SWAP.
     */
    SWAP;
}
