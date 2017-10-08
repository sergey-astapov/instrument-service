package io.instrument.service.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InstrumentFactory {
    private static final String FORMAT = "dd-MM-yyyy";

    public static Instrument from(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Arguments shouldn't be null");
        }
        int length = args.length;
        if (length != 6 && length != 8) {
            throw new IllegalArgumentException("Wrong arguments number, current: " + length + ", needs: 6 or 8");
        }
        return new Instrument(
                arg(args, 0),
                Source.valueOf(arg(args, 1)),
                date(args[2]),
                date(args[3]),
                Market.valueOf(arg(args, 4)),
                arg(args, 5),
                length == 8 ? args[6] : null,
                length == 8 ? Boolean.valueOf(args[7]) : null
        );
    }

    private static String arg(String[] args, int i) {
        if (args[i] == null) {
            throw new IllegalArgumentException("Argument #" + i + " should not be null");
        }
        return args[i];
    }

    public static Date date(String s) {
        try {
            return new SimpleDateFormat(FORMAT).parse(s);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
