package seedu.address.logic.parser.calendar;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.calendar.CalViewCommand;
import seedu.address.model.calendar.Calendar;

public class CalViewCommandParserTest {
    private CalViewCommandParser parser = new CalViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "       ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validField_success() {
        assertParseSuccess(parser, " /week this", new CalViewCommand(Calendar.getNowCalendar()));
        assertParseSuccess(parser, " /week next", new CalViewCommand(Calendar.getNextWeekCalendar()));
    }

    @Test
    public void parse_invalidField_throwsParseException() {
        //empty input
        assertParseFailure(parser, " /week",
                Calendar.MESSAGE_CONSTRAINTS);

        //invalid word
        assertParseFailure(parser, " /week those",
                Calendar.MESSAGE_CONSTRAINTS);

    }

}
