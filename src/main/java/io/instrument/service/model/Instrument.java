package io.instrument.service.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Instrument {
    private static final String FORMAT = "dd-MM-yyyy";

    private final String key;
    private final Source source;
    private final Date lastTradingDate;
    private final Date deliveryDate;
    private final Market market;
    private final String label;
    private final Boolean tradable;

    public Instrument(String key, Source source, Date lastTradingDate, Date deliveryDate, Market market,
                      String label, Boolean tradable) {
        this.key = key;
        this.source = source;
        this.lastTradingDate = lastTradingDate;
        this.deliveryDate = deliveryDate;
        this.market = market;
        this.label = label;
        this.tradable = tradable;
    }

    public String getKey() {
        return key;
    }

    public Source getSource() {
        return source;
    }

    public Date getLastTradingDate() {
        return lastTradingDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Market getMarket() {
        return market;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getTradable() {
        return tradable;
    }

    @Override
    public String toString() {
        return "key: " + key +
                ", source: " + source +
                ", lastTradingDate: " + format(lastTradingDate) +
                ", deliveryDate: " + format(deliveryDate) +
                ", market: " + market +
                ", label: " + label +
                ", tradable: " + tradable;
    }

    public static Date date(String s) {
        try {
            return new SimpleDateFormat(FORMAT).parse(s);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String format(Date date) {
        return new SimpleDateFormat(FORMAT).format(date);
    }
}
