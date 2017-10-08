package io.instrument.service;

import io.instrument.service.model.Instrument;
import io.instrument.service.model.InstrumentFactory;
import io.instrument.service.repository.InstrumentRepository;
import io.instrument.service.rules.Rule;
import io.instrument.service.rules.RulesProcessStrategy;

import java.util.List;
import java.util.Optional;

public class InstrumentService {
    private final List<Rule> rules;
    private final InstrumentRepository repo;
    private final RulesProcessStrategy<Rule> strategy;

    public InstrumentService(List<Rule> rules, InstrumentRepository repo, RulesProcessStrategy<Rule> strategy) {
        this.rules = rules;
        this.repo = repo;
        this.strategy = strategy;
    }

    public Optional<Instrument> process(String[] args) {
        Instrument instrument = InstrumentFactory.from(args);
        return strategy.stream().apply(rules)
                .map(r -> r.process(instrument))
                .reduce(Instrument::append)
                .map(repo::add);
    }
}
