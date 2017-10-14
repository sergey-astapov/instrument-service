package io.instrument.service.api;

import io.instrument.service.model.Instrument;
import io.instrument.service.model.Market;
import io.instrument.service.model.Source;

import java.util.Date;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public class InstrumentDTO {
    private String key;
    private Source source;
    private Date lastTradingDate;
    private Date deliveryDate;
    private Market market;
    private String label;
    private String exchangeCode;
    private Boolean tradable;

    private InstrumentDTO() {
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

    public InstrumentDTO append(InstrumentDTO other) {
        return builder()
                .key(ofNullable(other.getKey()).orElseGet(this::getKey))
                .source(ofNullable(other.getSource()).orElseGet(this::getSource))
                .addLastTradingDate(ofNullable(other.getLastTradingDate()).orElseGet(this::getLastTradingDate))
                .addDeliveryDate(ofNullable(other.getDeliveryDate()).orElseGet(this::getDeliveryDate))
                .addMarket(ofNullable(other.getMarket()).orElseGet(this::getMarket))
                .label(ofNullable(other.getLabel()).orElseGet(this::getLabel))
                .exchangeCode(ofNullable(other.getExchangeCode()).orElseGet(this::getExchangeCode))
                .addTradable(ofNullable(other.getTradable()).orElseGet(this::getTradable))
                .partialBuild();
    }

    @Override
    public String toString() {
        return "key: " + key +
                ", source: " + source +
                ", lastTradingDate: " + Instrument.format(lastTradingDate) +
                ", deliveryDate: " + Instrument.format(deliveryDate) +
                ", market: " + market +
                ", label: " + label +
                ", exchangeCode: " + exchangeCode +
                ", tradable: " + tradable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Instrument instrument(InstrumentDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Arguments shouldn't be null");
        }
        return new Instrument(
                arg(dto.getKey()),
                arg(dto.getSource()),
                arg(dto.getLastTradingDate()),
                arg(dto.getDeliveryDate()),
                arg(dto.getMarket()),
                arg(dto.getLabel()),
                arg(dto.getTradable())
        );
    }

    private static <T> T arg(T v) {
        if (v == null) {
            throw new IllegalArgumentException("Argument should not be null");
        }
        return v;
    }

    public static class Builder {
        private String key;
        private String source;
        private String lastTradingDate;
        private String deliveryDate;
        private String market;
        private String label;
        private String exchangeCode;
        private String tradable;

        private Builder() {}

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder source(Source source) {
            this.source = source != null ? source.name() : null;
            return this;
        }

        public Builder lastTradingDate(String lastTradingDate) {
            this.lastTradingDate = lastTradingDate;
            return this;
        }

        public Builder addLastTradingDate(Date lastTradingDate) {
            this.lastTradingDate = lastTradingDate != null ? Instrument.format(lastTradingDate) : null;
            return this;
        }

        public Builder deliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public Builder addDeliveryDate(Date deliveryDate) {
            this.deliveryDate = deliveryDate != null ? Instrument.format(deliveryDate) : null;
            return this;
        }

        public Builder market(String market) {
            this.market = market;
            return this;
        }

        public Builder addMarket(Market market) {
            this.market = market != null ? market.name() : null;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder exchangeCode(String exchangeCode) {
            this.exchangeCode = exchangeCode;
            return this;
        }

        public Builder tradable(String tradable) {
            this.tradable = tradable;
            return this;
        }

        public Builder addTradable(Boolean tradable) {
            this.tradable = tradable != null ? tradable.toString() : null;
            return this;
        }

        public InstrumentDTO build() {
            InstrumentDTO dto = new InstrumentDTO();
            dto.key = mandatoryArg(x -> x).apply(key);
            dto.source = mandatoryArg(Source::valueOf).apply(source);
            dto.lastTradingDate = mandatoryArg(Instrument::date).apply(lastTradingDate);
            dto.deliveryDate = mandatoryArg(Instrument::date).apply(deliveryDate);
            dto.market = mandatoryArg(Market::valueOf).apply(market);
            dto.label = mandatoryArg(x -> x).apply(label);
            if (exchangeCode != null && tradable != null) {
                dto.exchangeCode = exchangeCode;
                dto.tradable = mandatoryArg(Boolean::valueOf).apply(tradable);
            } else if (exchangeCode != null || tradable != null) {
                throw new IllegalArgumentException("Argument should not be null");
            }
            return dto;
        }

        public InstrumentDTO partialBuild() {
            InstrumentDTO dto = new InstrumentDTO();
            dto.key = key;
            dto.source = arg(Source::valueOf).apply(source);
            dto.lastTradingDate = arg(Instrument::date).apply(lastTradingDate);
            dto.deliveryDate = arg(Instrument::date).apply(deliveryDate);
            dto.market = arg(Market::valueOf).apply(market);
            dto.label = label;
            dto.exchangeCode = exchangeCode;
            dto.tradable = arg(Boolean::valueOf).apply(tradable);
            return dto;
        }

        private static <R> Function<String, R> mandatoryArg(Function<String, R> func) {
            return (v) -> {
                if (v == null) {
                    throw new IllegalArgumentException("Argument should not be null");
                }
                return func.apply(v);
            };
        }

        private static <R> Function<String, R> arg(Function<String, R> func) {
            return (v) -> v != null ? func.apply(v) : null;
        }
    }
}
