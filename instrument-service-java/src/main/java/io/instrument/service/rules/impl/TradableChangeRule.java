package io.instrument.service.rules.impl;

import io.instrument.service.api.InstrumentDTO;
import io.instrument.service.rules.Rule;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.instrument.service.api.InstrumentDTO.builder;

public class TradableChangeRule implements Rule {
    private static Logger log = Logger.getLogger(TradableChangeRule.class.getName());

    @Override
    public Optional<InstrumentDTO> process(InstrumentDTO dto) {
        switch (dto.getSource()) {
            case LME:
                log.info("LME, add tradable: true");
                return Optional.of(builder().tradable(Boolean.TRUE.toString()).partialBuild());
            case PRIME:
                log.info("PRIME, enforce tradable: false");
                return Optional.of(builder().tradable(Boolean.FALSE.toString()).partialBuild());
            default:
                if (log.isLoggable(Level.FINE)) {
                    log.fine(dto.getSource().name() + ", do nothing");
                }
                return Optional.empty();
        }
    }
}
