package io.instrument.service.rules.impl;

import io.instrument.service.api.InstrumentDTO;
import io.instrument.service.model.Source;
import io.instrument.service.repository.InstrumentRepository;
import io.instrument.service.rules.Rule;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.instrument.service.api.InstrumentDTO.builder;
import static io.instrument.service.model.Instrument.format;

public class DateChangeRule implements Rule {
    private static Logger log = Logger.getLogger(DateChangeRule.class.getName());

    private final InstrumentRepository repo;

    public DateChangeRule(InstrumentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<InstrumentDTO> process(InstrumentDTO dto) {
        switch (dto.getSource()) {
            case PRIME:
                return delta(dto);
            default:
                if (log.isLoggable(Level.FINE)) {
                    log.fine(dto.getSource().name() + ", do nothing");
                }
                return Optional.empty();
        }
    }

    private Optional<InstrumentDTO> delta(InstrumentDTO dto) {
        return repo.findBySourceAndKey(Source.LME, dto.getExchangeCode())
                .map(x -> {
                    log.info("PRIME, add lastTradingDate: " + format(x.getLastTradingDate()) +
                            ", deliveryDate: " + format(x.getDeliveryDate()));
                    return builder()
                            .lastTradingDate(format(x.getLastTradingDate()))
                            .deliveryDate(format(x.getDeliveryDate()))
                            .partialBuild();
                });
    }
}
