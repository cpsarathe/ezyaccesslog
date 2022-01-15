package com.ezy.access.parser;

import com.ezy.access.dto.common.LogFilePatternInfo;
import com.ezy.access.model.AccessLogModelV2;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * %t
 */
@Component
@CommonsLog
public class DateTimeElementParser implements ElementParser {

    private static final String REQUEST_DATE_TIME_DEF_CHAR = "t";

    @Override
    public void parse(String line, LogFilePatternInfo logFilePatternInfo, AccessLogModelV2 accessLogModel) {
        if (logFilePatternInfo.getUserDefinedPattern().contains(REQUEST_DATE_TIME_DEF_CHAR)) {
            int index = getIndex(logFilePatternInfo.getUserDefinedPattern(), REQUEST_DATE_TIME_DEF_CHAR);
            String[] elements = line.split(logFilePatternInfo.getPatternSplitter());
            String dateTime = elements[index].replace("\"","");
            dateTime = dateTime.replace("[","").replace("[","");
            accessLogModel.setDateTime(convertStringToDate(dateTime, "dd/MMM/yyyy:hh:mm:ss"));
        }
    }
    private Date convertStringToDate(String date, String format) {
        if (date != null) {
            try {
                return new SimpleDateFormat(format).parse(date);
            } catch (java.text.ParseException e) {
                log.error("Error while converting Date", e);
            }
        }
        return null;
    }


}
