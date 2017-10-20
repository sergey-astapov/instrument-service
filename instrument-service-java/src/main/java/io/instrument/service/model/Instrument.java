package io.instrument.service.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
    public int hashCode() {
        final int prime = 31;
        int result = hash(key);
        result = prime * result + hash(source);
        result = prime * result + hash(lastTradingDate);
        result = prime * result + hash(deliveryDate);
        result = prime * result + hash(market);
        result = prime * result + hash(label);
        result = prime * result + hash(tradable);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Instrument other = (Instrument) o;
        return Objects.equals(key, other.key) &&
                Objects.equals(source, other.source) &&
                Objects.equals(lastTradingDate, other.lastTradingDate) &&
                Objects.equals(deliveryDate, other.deliveryDate) &&
                Objects.equals(market, other.market) &&
                Objects.equals(label, other.label) &&
                Objects.equals(tradable, other.tradable);
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

    private static <T> int hash(T v) {
        return v != null ? v.hashCode() : 0;
    }
}
