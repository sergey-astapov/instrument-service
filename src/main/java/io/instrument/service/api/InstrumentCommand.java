package io.instrument.service.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class InstrumentCommand {
    private static final String FORMAT = "dd-MM-yyyy";

    private final String key;
    private final String source;
    private final Date lastTradingDate;
    private final Date deliveryDate;
    private final String market;
    private final String label;
    private final String exchangeCode;
    private final Boolean tradable;

    private InstrumentCommand(String key, String source, Date lastTradingDate, Date deliveryDate, String market,
                              String label, String exchangeCode, Boolean tradable) {
        this.key = key;
        this.source = source;
        this.lastTradingDate = lastTradingDate;
        this.deliveryDate = deliveryDate;
        this.market = market;
        this.label = label;
        this.exchangeCode = exchangeCode;
        this.tradable = tradable;
    }

    public String getKey() {
        return key;
    }

    public String getSource() {
        return source;
    }

    public Date getLastTradingDate() {
        return lastTradingDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String getMarket() {
        return market;
    }

    public String getLabel() {
        return label;
    }

    public Optional<String> getExchangeCode() {
        return Optional.ofNullable(exchangeCode);
    }

    public Optional<Boolean> getTradable() {
        return Optional.ofNullable(tradable);
    }

    public static InstrumentCommand from(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Arguments shouldn't be null");
        }
        int length = args.length;
        if (length != 6 && length != 8) {
            throw new IllegalArgumentException("Wrong arguments number, current: " + length + ", needs: 6 or 8");
        }
        return new InstrumentCommand(
                arg(args, 0),
                arg(args, 1),
                date(args[2]),
                date(args[3]),
                arg(args, 4),
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
