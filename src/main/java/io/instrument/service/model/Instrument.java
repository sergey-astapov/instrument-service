package io.instrument.service.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Instrument {
    private static final String FORMAT = "dd-MM-yyyy";

    private final String key;
    private final Source source;
    private final Date lastTradingDate;
    private final Date deliveryDate;
    private final Market market;
    private final String label;
    private final String exchangeCode;
    private final Boolean tradable;

    public Instrument(String key, Source source, Date lastTradingDate, Date deliveryDate, Market market,
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

    public String getExchangeCode() {
        return exchangeCode;
    }

    public Boolean getTradable() {
        return tradable;
    }

    public Instrument append(Instrument other) {
        return new Instrument(
                Optional.ofNullable(other.getKey()).orElseGet(this::getKey),
                Optional.ofNullable(other.getSource()).orElseGet(this::getSource),
                Optional.ofNullable(other.getLastTradingDate()).orElseGet(this::getLastTradingDate),
                Optional.ofNullable(other.getDeliveryDate()).orElseGet(this::getDeliveryDate),
                Optional.ofNullable(other.getMarket()).orElseGet(this::getMarket),
                Optional.ofNullable(other.getLabel()).orElseGet(this::getLabel),
                Optional.ofNullable(other.getExchangeCode()).orElseGet(this::getExchangeCode),
                Optional.ofNullable(other.getTradable()).orElseGet(this::getTradable)
        );
    }

    @Override
    public String toString() {
        return "key: " + key +
                ", source: " + source +
                ", lastTradingDate: " + format(lastTradingDate) +
                ", deliveryDate: " + format(deliveryDate) +
                ", market: " + market +
                ", label: " + label +
                ", exchangeCode: " + exchangeCode +
                ", tradable: " + tradable;
    }

    public static Instrument from(Instrument other) {
        return new Instrument(
                other.key,
                other.source,
                other.lastTradingDate != null ? new Date(other.lastTradingDate.getTime()) : null,
                other.deliveryDate != null ? new Date(other.deliveryDate.getTime()) : null,
                other.market,
                other.label,
                other.exchangeCode,
                other.tradable
        );
    }

    public static Instrument withTradable(Boolean tradable) {
        return new Instrument(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                tradable
        );
    }

    public static Instrument withDates(Date lastTradingDate, Date deliveryDate) {
        return new Instrument(
                null,
                null,
                lastTradingDate,
                deliveryDate,
                null,
                null,
                null,
                null
        );
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
