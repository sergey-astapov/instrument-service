package io.instrument.service.rules.impl;

import io.instrument.service.rules.RulesProcessStrategy;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class ParallelRulesProcessStrategy<T> implements RulesProcessStrategy<T> {
    @Override
    public Function<Collection<T>, Stream<T>> stream() {
        return Collection::parallelStream;
    }
}
