package seedu.address.logic.parser.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.lesson.LessonEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new LessonEditCommand object
 */
public class LessonEditCommandParser implements Parser<LessonEditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LessonEditCommand
     * and returns an LessonEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LessonEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_MODULE_CODE, PREFIX_AT, PREFIX_VENUE, PREFIX_TYPE, PREFIX_NEXT);

        Index index;
        if (argMultimap.getPreamble().trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonEditCommand.MESSAGE_USAGE));
        }
        index = ParserUtil.parseIndex(argMultimap.getPreamble());
        ModuleCode target = null;

        LessonEditCommand.EditLessonDescriptor editLessonDescriptor = new LessonEditCommand.EditLessonDescriptor();

        if (argMultimap.getAllValues(PREFIX_MODULE_CODE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonEditCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getAllValues(PREFIX_MODULE_CODE).size() < 2
                && argMultimap.getValue(PREFIX_TYPE) == null && argMultimap.getValue(PREFIX_AT) == null
                && argMultimap.getValue(PREFIX_VENUE) == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonEditCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getAllValues(PREFIX_MODULE_CODE).isEmpty()) {
            List<String> mods = argMultimap.getAllValues(PREFIX_MODULE_CODE);
            target = ParserUtil.parseModuleCode(mods.get(0));
            if (mods.size() == 2) {
                editLessonDescriptor.setModuleCode(ParserUtil.parseModuleCode(mods.get(1)));
                target = ParserUtil.parseModuleCode(mods.get(0));
            } else if (mods.size() > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        LessonEditCommand.MESSAGE_USAGE));
            }
        }

        if (argMultimap.getValue(PREFIX_TYPE) != null) {
            editLessonDescriptor.setLessonType(ParserUtil.parseLessonType(argMultimap.getValue(PREFIX_TYPE)));
        }

        if (argMultimap.getValue(PREFIX_AT) != null) {
            // need to change
            String trimmed = argMultimap.getValue(PREFIX_AT).trim();
            String[] splitted = trimmed.split(" ");
            if (splitted.length != 3) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        LessonEditCommand.MESSAGE_USAGE));
            }

            editLessonDescriptor.setDay(ParserUtil.parseDay(argMultimap.getValue(PREFIX_AT)));
            editLessonDescriptor.setStartTime(ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_AT)));
            editLessonDescriptor.setEndTime(ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_AT)));
        }

        if (argMultimap.getValue(PREFIX_VENUE) != null) {
            if (argMultimap.getValue(PREFIX_VENUE).equals("")) {
                editLessonDescriptor.setVenue("");
            } else {
                editLessonDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE)));
            }
        }

        return new LessonEditCommand(target, index, editLessonDescriptor);

    }
}
