package io.instrument.service.model;

import java.util.Date;
import java.util.Optional;

public class Instrument {

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

    public Optional<String> getKey() {
        return Optional.ofNullable(key);
    }

    public Optional<Source> getSource() {
        return Optional.ofNullable(source);
    }

    public Optional<Date> getLastTradingDate() {
        return Optional.ofNullable(lastTradingDate);
    }

    public Optional<Date> getDeliveryDate() {
        return Optional.ofNullable(deliveryDate);
    }

    public Optional<Market> getMarket() {
        return Optional.ofNullable(market);
    }

    public Optional<String> getLabel() {
        return Optional.ofNullable(label);
    }

    public Optional<String> getExchangeCode() {
        return Optional.ofNullable(exchangeCode);
    }

    public Optional<Boolean> getTradable() {
        return Optional.ofNullable(tradable);
    }

    public Instrument append(Instrument other) {
        return new Instrument(
                other.getKey().orElseGet(() -> getKey().orElse(null)),
                other.getSource().orElseGet(() -> getSource().orElse(null)),
                other.getLastTradingDate().orElseGet(() -> getLastTradingDate().orElse(null)),
                other.getDeliveryDate().orElseGet(() -> getDeliveryDate().orElse(null)),
                other.getMarket().orElseGet(() -> getMarket().orElse(null)),
                other.getLabel().orElseGet(() -> getLabel().orElse(null)),
                other.getExchangeCode().orElseGet(() -> getExchangeCode().orElse(null)),
                other.getTradable().orElseGet(() -> getTradable().orElse(null))
        );
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
}
