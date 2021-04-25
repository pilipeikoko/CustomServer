package org.bsuir.util;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CustomDate implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Date date;

    public CustomDate(Date date) {
        this.date = date;
    }

    public CustomDate(String date) throws ParseException {
        DateLabelFormatter dateLabelFormatter = new DateLabelFormatter();
        this.date = (Date) dateLabelFormatter.stringToValue(date);
    }

    @Override
    public String toString() {
        if (date == null)
            return "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return new DateLabelFormatter().valueToString(calendar);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        CustomDate customDate = (CustomDate) obj;

        return customDate.toString().equals(this.toString());
    }
}
