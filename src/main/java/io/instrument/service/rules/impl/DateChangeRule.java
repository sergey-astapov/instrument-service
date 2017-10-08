package io.instrument.service.rules.impl;

import io.instrument.service.model.Instrument;
import io.instrument.service.model.Source;
import io.instrument.service.repository.InstrumentRepository;
import io.instrument.service.rules.Rule;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.instrument.service.model.Instrument.format;

public class DateChangeRule implements Rule {
    private static Logger log = Logger.getLogger(DateChangeRule.class.getName());

    private final InstrumentRepository repo;

    public DateChangeRule(InstrumentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Instrument> process(Instrument instrument) {
        switch (instrument.getSource()) {
            case PRIME:
                return delta(instrument);
            default:
                if (log.isLoggable(Level.FINE)) {
                    log.fine(instrument.getSource().name() + ", do nothing");
                }
                return Optional.empty();
        }
    }

    private Optional<Instrument> delta(Instrument i) {
        return repo.findBySourceAndKey(Source.LME, i.getExchangeCode())
                .map(x -> {
                    log.info("PRIME, add lastTradingDate: " + format(x.getLastTradingDate()) +
                            ", deliveryDate: " + format(x.getDeliveryDate()));
                    return Instrument.withDates(x.getLastTradingDate(), x.getDeliveryDate());
                });
    }
}
