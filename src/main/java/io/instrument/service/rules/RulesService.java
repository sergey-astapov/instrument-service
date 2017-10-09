package io.instrument.service.rules;

import io.instrument.service.api.InstrumentDTO;
import io.instrument.service.model.Instrument;
import io.instrument.service.repository.InstrumentRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static io.instrument.service.model.InstrumentFactory.from;

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

    public Instrument process(InstrumentDTO dto) {
        log.info("Original instrument: " + dto);

        InstrumentDTO merged = mergedInstrument(dto);
        log.info("Merged instrument: " + merged);

        return repo.add(from(merged));
    }

    protected InstrumentDTO mergedInstrument(InstrumentDTO dto) {
        return merge(rules.stream(), dto);
    }

    public static InstrumentDTO merge(Stream<Rule> stream, InstrumentDTO dto) {
        return stream
                .map(r -> r.process(dto))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(dto, InstrumentDTO::append);
    }
}
