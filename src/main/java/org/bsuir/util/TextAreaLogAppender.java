package org.bsuir.util;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import javax.swing.JTextArea;

public class TextAreaLogAppender extends AppenderSkeleton {
    private JTextArea areaToLogTo;

    public TextAreaLogAppender() {
        super();
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        String message = "";
        if (loggingEvent.locationInformationExists()) {
            message = loggingEvent.getLocationInformation().getClassName() +
                    "." +
                    loggingEvent.getLocationInformation().getMethodName() +
                    ":" +
                    loggingEvent.getLocationInformation().getLineNumber() +
                    " - " +
                    loggingEvent.getMessage().toString();
        }
        this.areaToLogTo.append(message + "\n");
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public void setAreaToLogTo(JTextArea areaToLogTo) {
        this.areaToLogTo = areaToLogTo;
    }
}
