package io.instrument.service.rules.impl;

import io.instrument.service.model.Instrument;
import io.instrument.service.rules.Rule;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TradableChangeRule implements Rule {
    private static Logger log = Logger.getLogger(TradableChangeRule.class.getName());

    @Override
    public Optional<Instrument> process(Instrument instrument) {
        switch (instrument.getSource()) {
            case LME:
                log.info("LME, add tradable: true");
                return Optional.of(Instrument.withTradable(true));
            case PRIME:
                log.info("PRIME, enforce tradable: false");
                return Optional.of(Instrument.withTradable(false));
            default:
                if (log.isLoggable(Level.FINE)) {
                    log.fine(instrument.getSource().name() + ", do nothing");
                }
                return Optional.empty();
        }
    }
}
