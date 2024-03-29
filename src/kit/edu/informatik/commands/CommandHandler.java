/*
 * Copyright (c) 2023, KASTEL. All rights reserved.
 */

package kit.edu.informatik.commands;

import kit.edu.informatik.model.memory.Memory;
import kit.edu.informatik.model.memory.Phase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class handles the user input and executes the {@link Command}s.
 *
 * @author Programmieren-Team
 * @author uqtwh
 * @version 1.0
 */
public final class CommandHandler {
    private static final String COMMAND_SEPARATOR_REGEX = " ";
    private static final String ERROR_PREFIX = "Error, ";
    private static final String COMMAND_NOT_FOUND_FORMAT = ERROR_PREFIX + "command '%s' not found";
    private static final String QUIT_COMMAND_NAME = "quit";
    private static final String NOT_ACCESSIBLE = ERROR_PREFIX + "this command is not accessible in current phase \"%s\".";
    private static final Command[] COMMANDS = {new AddAICommand(), new EndGameCommand(),
        new HelpCommand(), new NextCommand(), new QuitCommand(null), new RemoveAICommand(),
        new SetInitModeCommand(), new ShowAICommand(), new ShowMemoryCommand(), new StartGameCommand()};
    private final Map<String, Command> commands;
    private final Memory memory;
    private boolean running = false;

    /**
     * Constructs a new CommandHandler.
     * @param memory memory responsible for the game logic
     */
    public CommandHandler(Memory memory) {
        this.memory = Objects.requireNonNull(memory);
        this.commands = new HashMap<>();
        this.initCommands();
    }

    /**
     * Starts the interaction with the user.
     */
    public void handleUserInput() {
        this.running = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while (running && scanner.hasNextLine()) {
                executeCommand(scanner.nextLine());
            }
        }
    }

    /**
     * Quits the interaction with the user.
     */
    public void quit() {
        this.running = false;
    }

    /**
     * Exectutes a {@link Command} from a given String
     * @param commandWithArguments String with command
     */
    private void executeCommand(String commandWithArguments) {
        String[] splittedCommand = commandWithArguments.trim().split(COMMAND_SEPARATOR_REGEX);
        String commandName = splittedCommand[0];
        String[] commandArguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

        executeCommand(commandName, new Arguments(commandArguments));
    }

    /**
     * Exectutes a given command with given arguments.
     * @param commandName      name of the command to be executed
     * @param commandArguments arguments for the command
     */
    private void executeCommand(String commandName, Arguments commandArguments) {
        if (!commands.containsKey(commandName)) {
            System.err.println(COMMAND_NOT_FOUND_FORMAT.formatted(commandName));
        } else if (commands.get(commandName).accessibilityPhase() != memory.getPhase()
                && commands.get(commandName).accessibilityPhase() != Phase.BOTH) {
            System.err.println(NOT_ACCESSIBLE.formatted(memory.getPhase()));
        } else {
            CommandResult result = commands.get(commandName).execute(memory, commandArguments);
            if (result.getMessage() != null) {
                switch (result.getType()) {
                    case SUCCESS -> System.out.println(result.getMessage());
                    case FAILURE -> System.err.println(ERROR_PREFIX + result.getMessage());
                    default -> throw new IllegalStateException();
                }
            }
        }
    }

    /**
     * Initializes the {@link Command}s of the game.
     */
    private void initCommands() {
        for (int i  = 0; i < UserCommands.values().length; i++) {
            if (UserCommands.values()[i] == UserCommands.QUIT) {
                this.addCommand(QUIT_COMMAND_NAME, new QuitCommand(this));
            } else {
                this.addCommand(UserCommands.values()[i].toString(), COMMANDS[i]);
            }
        }
    }

    /**
     * Adds a {@link Command} to the command handler.
     * @param commandName the name of the command
     * @param command     the command
     */
    private void addCommand(String commandName, Command command) {
        this.commands.put(commandName, command);
    }
}

