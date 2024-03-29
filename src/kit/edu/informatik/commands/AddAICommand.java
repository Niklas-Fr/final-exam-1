package kit.edu.informatik.commands;

import kit.edu.informatik.exceptions.InvalidArgumentException;
import kit.edu.informatik.model.InstantiateCommand;
import kit.edu.informatik.model.AICommandTypes;
import kit.edu.informatik.model.AIObject;
import kit.edu.informatik.model.memory.Memory;
import kit.edu.informatik.model.memory.Phase;

import java.util.Optional;

/**
 * The Class models the adding of an {@link AIObject} to the game.
 * Implements interface {@link Command}.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class AddAICommand implements Command {
    private static final String DELIMITER = ",";
    private static final String INVALID_CHARACTER = " ";
    private static final String INVALID_NAME = "no such character \"" + INVALID_CHARACTER + "\" allowed in name " + "\"%s %s\".";
    private static final String EXISTING_NAME = "AI named \"%s\" does already exist.";
    private static final String NONEXISTENT_COMMAND = "command named \"%s\" does not exist.";
    private static final String INVALID_COMMANDS = "there has to be at least one non STOP command.";
    private static final String INVALID_COMMAND_AMOUNT = "memory is only %d commands long. An AI with %d commands cannot be added, "
            + "because it would overlap.";
    private static final Phase ACCESSIBILITY_PHASE = Phase.INITIALIZING;
    private static final int COMMAND_ARGUMENTS = 3;

    /**
     * Overriding the execute method from Interface {@link Command}.
     * Adds an {@link AIObject} to the memory.
     * @param memory           the {@link Memory memory} to execute the command on
     * @param commandArguments the arguments of the command
     * @return CommandResult of the execution with a {@link CommandResultType type} and a message, depending on the result
     */
    @Override
    public CommandResult execute(Memory memory, Arguments commandArguments) {
        try {
            String name = commandArguments.nextString();
            String commands = commandArguments.nextString();

            // Checking if the input is invalid
            if (!commands.contains(DELIMITER)) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_NAME.formatted(name, commands));
            }
            commandArguments.assertNoMoreArguments();

            // Checking if an AI with the given name already exists
            if (memory.getAI(name, memory.getAiList()).isPresent()) {
                return new CommandResult(CommandResultType.FAILURE, EXISTING_NAME.formatted(name));
            }
            AIObject newAI = new AIObject(name);

            String[] commandList = commands.split(DELIMITER);
            Arguments arguments = new Arguments(commandList);

            // Converting the given arguments to AICommands
            for (int i = 0; i < commandList.length; i += COMMAND_ARGUMENTS) {
                String type = arguments.nextString();
                if (value(type).isEmpty()) {
                    return new CommandResult(CommandResultType.FAILURE, NONEXISTENT_COMMAND.formatted(type));
                }
                newAI.addCommand(InstantiateCommand.instantiateCommand(value(type).get(),
                        arguments.nextInt(), arguments.nextInt()));
            }
            // Checking if the AI has a non STOP command
            if (!getNonStop(newAI)) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_COMMANDS);
            }
            if ((commandList.length / COMMAND_ARGUMENTS) > (int) Math.ceil((double) memory.getSize() / 2)) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_COMMAND_AMOUNT.formatted(
                        memory.getSize(), commandList.length / COMMAND_ARGUMENTS));
            }

            memory.addAI(newAI);
            return new CommandResult(CommandResultType.SUCCESS, newAI.getName());
        } catch (InvalidArgumentException e) {
            return new CommandResult(CommandResultType.FAILURE, e.getMessage());
        }
    }

    /**
     * Private helper method for {@link #execute(Memory, Arguments)}, evaluating if the AI has a non-STOP command.
     * @param checkedAI AI to be evaluated
     * @return {@code true} if there is a non-STOP command, otherwise {@code false}
     */
    private boolean getNonStop(AIObject checkedAI) {
        for (int i = 0; i < checkedAI.getCommands().size(); i++) {
            if (checkedAI.getCommands().get(i).getCommandType() != AICommandTypes.STOP) {
                return true;
            }
        }
        return false;
    }

    /**
     * Private helper method for {@link #execute(Memory, Arguments)}, returns an {@link Optional} of
     * the enum value of a given String.
     * If the String does not match any value, an empty Optional is returned.
     * @param name the String to convert to an enum value
     * @return Optional of the enum value of the given String or an emtpy Optional
     */
    private Optional<AICommandTypes> value(String name) {
        for (AICommandTypes command : AICommandTypes.values()) {
            if (command.name().equals(name)) {
                return Optional.of(command);
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

