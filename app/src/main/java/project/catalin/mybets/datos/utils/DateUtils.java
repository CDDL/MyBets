package project.catalin.mybets.datos.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by CDD on 13/05/2016.
 */
public class DateUtils {
    private Date mDate;
    private String mStringDate;

    public DateUtils from(Date date) {
        mDate = date;
        return this;
    }

    public String to(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
        return formatter.format(mDate);
    }

    public DateUtils from(String date) {
        mStringDate = date;
        return this;
    }

    public Date toDate(String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
        return formatter.parse(mStringDate);
    }
}
