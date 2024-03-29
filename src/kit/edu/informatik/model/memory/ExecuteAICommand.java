package kit.edu.informatik.model.memory;

import kit.edu.informatik.model.aicommands.AICommand;
import kit.edu.informatik.model.AIObject;

import java.util.Optional;

/**
 * The class models the execution of an {@link AICommand}.
 * 
 * @author uqtwh
 * @version 1.0
 */
public final class ExecuteAICommand {
    /**
     * Executes the command of an AI.
     * @param index       the index of the current command
     * @param currentAI   the AI to execute the command for
     * @param memoryCells the memory cells to execute the command on
     * @return an Optional of the AI that has stopped, otherwise an empty Optional
     */
    public Optional<AIObject> executeCommand(int index, AIObject currentAI, MemoryCell[] memoryCells) {
        AICommand command = memoryCells[index].getAICommand();
        command.execute(currentAI, memoryCells);
        currentAI.setCounter(1);
        if (!currentAI.getRunning()) {
            return Optional.of(currentAI);
        }
        currentAI.setIndex((currentAI.getIndex() + 1) % memoryCells.length);
        return Optional.empty();
    }
}
