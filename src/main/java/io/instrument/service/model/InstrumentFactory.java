package io.instrument.service.model;

import io.instrument.service.api.InstrumentDTO;

public class InstrumentFactory {

    public static Instrument from(InstrumentDTO dto) {
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
}
