package kit.edu.informatik.commands;

import kit.edu.informatik.exceptions.InvalidArgumentException;
import kit.edu.informatik.model.memory.Memory;
import kit.edu.informatik.model.memory.Phase;

import java.util.StringJoiner;

/**
 * The class models a help command, listing all available kit.edu.informatik.model.commands with their descriptions.
 * Implements interface {@link Command}.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class HelpCommand implements Command {
    private static final Phase ACCESSIBILITY_PHASE = Phase.BOTH;
    private static final String FORMAT = "input pattern: ";
    private static final Command[] COMMANDS = {new AddAICommand(),
        new EndGameCommand(), new HelpCommand(), new NextCommand(), new QuitCommand(null),
        new RemoveAICommand(), new SetInitModeCommand(), new ShowAICommand(), new ShowMemoryCommand(),
        new StartGameCommand()
    };
    private static final String[] COMMAND_DESCRIPTIONS = {
        "Adding an AI with given names and AI commands, " + FORMAT + "add-ai <name> <ai-command>...",
        "Ending the game, listing all running and stopped AIs, " + FORMAT + "end-game",
        "Listing all currently available commands, " + FORMAT + "help",
        "Executes a given amount of steps in the game, " + FORMAT + "next <number>",
        "Quits the game, " + FORMAT + "quit",
        "Removes an existing AI with a given name, " + FORMAT + "remove-ai <name>",
        "Setting the mode in which the memory is initialized, " + FORMAT + "set-init-mode <mode>",
        "Provides the status of an AI with a given name, " + FORMAT + "show-ai <name>",
        "Provides the current status of the memory with the option to showcase a section of the memory, "
                + FORMAT + "show-memory <section>",
        "Starts the game with a selected group of AIs, " + FORMAT + "start-game <ai-name1> <ai-name2>..."
    };
    private static final String DESCRIPTION_PATTERN = "%s: %s";

    /**
     * Overriding the execute method from Interface {@link Command}.
     * Returns a list of all available commands with their descriptions.
     * @param memory           the {@link Memory memory} to execute the command on
     * @param commandArguments the arguments of the command
     * @return CommandResult of the execution with a {@link CommandResultType type} and a message, depending on the result
     */
    @Override
    public CommandResult execute(Memory memory, Arguments commandArguments) {
        try {
            commandArguments.assertNoMoreArguments();
            return new CommandResult(CommandResultType.SUCCESS, message(memory));
        } catch (InvalidArgumentException e) {
            return new CommandResult(CommandResultType.FAILURE, e.getMessage());
        }
    }

    /**
     * Private helper method for {@link #execute(Memory, Arguments) execute}, building the return message.
     * @param memory the memory of the game
     * @return the message to be returned
     */
    private String message(Memory memory) {
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        for (int i = 0; i < COMMANDS.length; i++) {
            if (COMMANDS[i].accessibilityPhase() == memory.getPhase() || COMMANDS[i].accessibilityPhase() == Phase.BOTH) {
                stringJoiner.add(DESCRIPTION_PATTERN.formatted(UserCommands.values()[i].toString(), COMMAND_DESCRIPTIONS[i]));
            }
        }
        return stringJoiner.toString();
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

