package kit.edu.informatik.commands;

import kit.edu.informatik.exceptions.InvalidArgumentException;
import kit.edu.informatik.model.memory.InitMode;
import kit.edu.informatik.model.memory.Memory;
import kit.edu.informatik.model.memory.Phase;

import java.util.Optional;

/**
 * The class models changing the init mode of the memory.
 * Implements interface {@link Command}.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class SetInitModeCommand implements Command {
    private static final int LOWER_BOUND = -1337;
    private static final int UPPER_BOUND = 1337;
    private static final String OUT_OF_BOUNDS = "value %d is out of bounds %d to %d.";
    private static final String SUCCESS_MESSAGE = "Changed init mode from %s to %s";
    private static final String INVALID_ARGUMENT = "mode named %s does not exist.";
    private static final String SEED_PATTERN = "%s %d";
    private static final Phase ACCESSIBILITY_PHASE = Phase.INITIALIZING;

    /**
     * Overriding the execute method from Interface {@link Command}.
     * Changes the init mode of the memory.
     * @param memory           the {@link Memory memory} to execute the command on
     * @param commandArguments the arguments of the command
     * @return CommandResult of the execution with a {@link CommandResultType type} and a message, depending on the result
     */
    @Override
    public CommandResult execute(Memory memory, Arguments commandArguments) {
        try {
            InitMode currentMode = memory.getInitMode();
            String modeName = commandArguments.nextString();
            if (value(modeName).isEmpty()) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENT.formatted(modeName));
            }
            InitMode mode = value(modeName).get();

            int seed = 0;
            if (mode == InitMode.INIT_MODE_RANDOM) {
                seed = commandArguments.nextInt();
            }
            // Checking if the seed is within the bounds
            if (seed < LOWER_BOUND || seed > UPPER_BOUND) {
                return new CommandResult(CommandResultType.FAILURE, OUT_OF_BOUNDS.formatted(seed, LOWER_BOUND, UPPER_BOUND));
            }
            commandArguments.assertNoMoreArguments();
            memory.setInitMode(mode);

            int oldSeed = memory.getSeed();
            memory.setSeed(seed);

            // Checking if the mode changed
            if (mode == currentMode) {
                if (oldSeed != seed && mode.equals(InitMode.INIT_MODE_RANDOM)) {
                    return new CommandResult(CommandResultType.SUCCESS,
                            SUCCESS_MESSAGE.formatted(
                                    SEED_PATTERN.formatted(InitMode.INIT_MODE_RANDOM, oldSeed),
                                    SEED_PATTERN.formatted(InitMode.INIT_MODE_RANDOM, seed)));
                }
                return new CommandResult(CommandResultType.SUCCESS, null);
            }

            // Constructing the message based on the previous mode
            String message;
            if (currentMode.equals(InitMode.INIT_MODE_RANDOM)) {
                message = SUCCESS_MESSAGE.formatted(SEED_PATTERN.formatted(InitMode.INIT_MODE_RANDOM, oldSeed), InitMode.INIT_MODE_STOP);
            } else {
                message = SUCCESS_MESSAGE.formatted(InitMode.INIT_MODE_STOP, SEED_PATTERN.formatted(InitMode.INIT_MODE_RANDOM, seed));
            }
            return new CommandResult(CommandResultType.SUCCESS, message);
        } catch (InvalidArgumentException e) {
            return new CommandResult(CommandResultType.FAILURE, e.getMessage());
        }
    }

    /**
     * Private helper method for {@link #execute(Memory, Arguments)}, returns an {@link Optional} of the
     * {@link InitMode} enum value of a given String.
     * If the String does not match any value, an empty Optional is returned.
     * @param mode the String to convert to an enum value
     * @return Optional of the enum value of the given String or an empty Optional
     */
    private Optional<InitMode> value(String mode) {
        for (InitMode initMode : InitMode.values()) {
            if (initMode.name().equals(mode)) {
                return Optional.of(initMode);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns the {@link #ACCESSIBILITY_PHASE} in which the command is accessible.
     * @return accessibility phase
     */
    @Override
    public Phase accessibilityPhase() {
        return ACCESSIBILITY_PHASE;
    }
}


