package by.epam.safonenko.pharmacy.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class MultipartCommandFactory {
    private static Logger logger = LogManager.getLogger();

    public Optional<MultipartCommand> defineCommand(String commandType) {
        Optional<MultipartCommand> command = Optional.empty();
        if (commandType == null){
            return command;
        }
        try {
            MultipartCommandType type = MultipartCommandType.valueOf(commandType.toUpperCase());
            command = Optional.of(type.getCommand());
        }catch (IllegalArgumentException e){
            logger.catching(Level.WARN, e);
        }
        return command;
    }
}
