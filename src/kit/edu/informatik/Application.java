package kit.edu.informatik;

import kit.edu.informatik.commands.Arguments;
import kit.edu.informatik.commands.CommandHandler;
import kit.edu.informatik.exceptions.InvalidArgumentException;
import kit.edu.informatik.model.memory.Memory;

import java.util.Optional;

/**
 * This class is the entry point of the program.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class Application {
    private static final int LOWER_BOUND = 7;
    private static final int UPPER_BOUND = 1337;
    private static final String ERROR_PREFIX = "Error, ";
    private static final String OUT_OF_BOUNDS = ERROR_PREFIX + "argument %d is outside bounds of %d to %d.";
    private static final String INVALID_ARGUMENT_AMOUNT = ERROR_PREFIX + "number of arguments %d is invalid";
    private static final String SUCCESSFUL_START = "Welcome to CodeFight 2024. Enter 'help' for more details.";
    private static final String DUPLICATE_SYMBOLS = "symbol %s occurs multiple times, which is not allowed.";
    private static final String[] SYMBOLS = new String[4];
    private static final int MIN_ARGUMENT_AMOUNT = 4;
    private static String[] aiSymbols;
    private static int range;

    /**
     * Private constructor, not accessible.
     */
    private Application() {
        throw new UnsupportedOperationException();
    }

    /**
     * Main method of the class.
     * @param args arguments of the command line
     */
    public static void main(String[] args) {
        try {
            Arguments arguments = new Arguments(args);
            invalidArgs(arguments);

            System.out.println(SUCCESSFUL_START);
            Memory memory = new Memory(range, SYMBOLS, aiSymbols);
            CommandHandler commandHandler = new CommandHandler(memory);
            commandHandler.handleUserInput();
        } catch (InvalidArgumentException e) {
            System.err.println(ERROR_PREFIX + e.getMessage());
        }
    }

    /**
     * Validates the given arguments.
     * @param  arguments                arguments to validate
     * @throws InvalidArgumentException if the memory size is out of given bounds
     * @throws InvalidArgumentException if the number of arguments is invalid
     * @throws InvalidArgumentException if a symbol occurs multiple times
     */
    private static void invalidArgs(Arguments arguments) throws InvalidArgumentException {
        range = arguments.nextInt();
        // Validating range
        if (range < LOWER_BOUND || range > UPPER_BOUND) {
            throw new InvalidArgumentException(OUT_OF_BOUNDS.formatted(range, LOWER_BOUND, UPPER_BOUND));
        }
        // Reading AI independent symbols
        for (int i = 0; i < SYMBOLS.length; i++) {
            SYMBOLS[i] = arguments.nextString();
        }

        aiSymbols = new String[arguments.getLength() - SYMBOLS.length - 1];
        // Validating length, as a minimum of 4 arguments as well as an even amount of arguments is required
        if (aiSymbols.length < MIN_ARGUMENT_AMOUNT || aiSymbols.length % 2 == 1) {
            throw new InvalidArgumentException(INVALID_ARGUMENT_AMOUNT.formatted(arguments.getLength()));
        }
        // Reading AI dependent symbols
        for (int i = 0; i < aiSymbols.length; i++) {
            aiSymbols[i] = arguments.nextString();
        }

        // Checking for duplicates
        if (validateArray(arguments.getArguments()).isPresent()) {
            throw new InvalidArgumentException(DUPLICATE_SYMBOLS.formatted(validateArray(arguments.getArguments())));
        }
    }

    /**
     * Helper method for {@link #invalidArgs(Arguments)}, checking for duplicates in a given String array.
     * @param symbols array to check
     * @return empty Optional if no duplicate exists, otherwise an Optional of the symbol which occurs multiple times
     */
    private static Optional<String> validateArray(String[] symbols) {
        for (int i = 1; i < symbols.length; i++) {
            for (int j = i + 1; j < symbols.length; j++) {
                if (symbols[i].equals(symbols[j])) {
                    return Optional.of(symbols[i]);
                }
            }
        }
        return Optional.empty();
    }
}
