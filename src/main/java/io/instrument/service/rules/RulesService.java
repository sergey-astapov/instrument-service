package io.instrument.service.rules;

import io.instrument.service.model.Instrument;
import io.instrument.service.model.InstrumentFactory;
import io.instrument.service.repository.InstrumentRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class RulesService {
    private static Logger log = Logger.getLogger(RulesService.class.getName());

    private final List<Rule> rules;
    private final InstrumentRepository repo;

    public RulesService(List<Rule> rules, InstrumentRepository repo) {
        this.rules = rules;
        this.repo = repo;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public InstrumentRepository getRepo() {
        return repo;
    }

    public Instrument process(String[] args) {
        Instrument instrument = InstrumentFactory.from(args);
        log.info("Original instrument: " + instrument);

        Instrument merged = mergedInstrument(instrument);
        log.info("Merged instrument: " + merged);

        return repo.add(merged);
    }

    protected Instrument mergedInstrument(Instrument instrument) {
        return merge(rules.stream(), instrument);
    }

    protected Instrument merge(Stream<Rule> stream, Instrument instrument) {
        return stream
                .map(r -> r.process(instrument))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(instrument, Instrument::append);
    }
}
