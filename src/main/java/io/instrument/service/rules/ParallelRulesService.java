package io.instrument.service.rules;

import io.instrument.service.model.Instrument;
import io.instrument.service.repository.InstrumentRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class ParallelRulesService extends RulesService {
    private static Logger log = Logger.getLogger(ParallelRulesService.class.getName());

    private final ExecutorService executor;

    public ParallelRulesService(List<Rule> rules, InstrumentRepository repo, ExecutorService executor) {
        super(rules, repo);
        this.executor = executor;
    }

    @Override
    protected Instrument mergedInstrument(Instrument instrument) {
        try {
            return executor.submit(() -> merge(getRules().parallelStream(), instrument)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.severe("merge failed for instrument: " + instrument);
            throw new IllegalStateException(e);
        }
    }
}
