package kit.edu.informatik.model;

import kit.edu.informatik.model.aicommands.AICommand;
import kit.edu.informatik.model.aicommands.AddCommand;
import kit.edu.informatik.model.aicommands.AddRCommand;
import kit.edu.informatik.model.aicommands.CmpCommand;
import kit.edu.informatik.model.aicommands.JmpCommand;
import kit.edu.informatik.model.aicommands.JmzCommand;
import kit.edu.informatik.model.aicommands.MovICommand;
import kit.edu.informatik.model.aicommands.MovRCommand;
import kit.edu.informatik.model.aicommands.StopCommand;
import kit.edu.informatik.model.aicommands.SwapCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * The class is used to instantiate the different {@link AICommand} objects.
 *
 * @author uqtwh
 * @version 1.0
 */
public final class InstantiateCommand {
    private static final Map<AICommandTypes, AICommand> COMMAND_MAP = new HashMap<>();
    private static final AICommand[] COMMANDS = {new StopCommand(), new MovRCommand(), new MovICommand(),
        new AddCommand(), new AddRCommand(), new JmpCommand(), new JmzCommand(), new CmpCommand(),
        new SwapCommand()
    };

    /**
     * Private constructor to prevent instantiation.
     */
    private InstantiateCommand() {
        throw new UnsupportedOperationException();
    }

    /**
     * Instantiates a new {@link AICommand} object.
     * @param commandType the type of the command
     * @param entryA      the first entry
     * @param entryB      the second entry
     * @return the new AICommand
     */
    public static AICommand instantiateCommand(AICommandTypes commandType, int entryA, int entryB) {
        initCommands();
        AICommand result = COMMAND_MAP.get(commandType).copy();
        result.setEntryA(entryA);
        result.setEntryB(entryB);
        return result;
    }

    /**
     * Initializes the command map.
     */
    private static void initCommands() {
        for (int i = 0; i < AICommandTypes.values().length; i++) {
            COMMAND_MAP.put(AICommandTypes.values()[i], COMMANDS[i]);
        }
    }
}
