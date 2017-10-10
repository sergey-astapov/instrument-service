package io.instrument.service.e2e

import io.instrument.service.api.InstrumentDTO
import io.instrument.service.model.Instrument
import io.instrument.service.repository.InstrumentRepository
import io.instrument.service.repository.impl.MemoryInstrumentRepository
import io.instrument.service.rules.ParallelRulesService
import io.instrument.service.rules.impl.DateChangeRule
import io.instrument.service.rules.impl.TradableChangeRule
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@Title("Acceptance Criteria")
class AcceptanceCriteriaSpec extends Specification {
    @Unroll
    def "Story 1: #dto.source instrument"() {
        given: """the #dto.source instrument #dto.key with these details: #dto"""
        def repo = new MemoryInstrumentRepository()
        def sut = makeSUT(repo)

        when: """When #dto.source publishes instrument #dto.key"""
        sut.process(dto)

        then: """the application publishes the following instrument internally"""
        def opt = repo.findBySourceAndKey(dto.source, dto.key)
        assert opt.isPresent()
        opt.map { Instrument i ->
            assert i.key == dto.key
            assert i.source == dto.source
            assert i.lastTradingDate == dto.lastTradingDate
            assert i.deliveryDate == dto.deliveryDate
            assert i.market == dto.market
            assert i.label == dto.label
            assert i.tradable
        }

        where: "parameters: #dto"
        dto = InstrumentDTO.builder()
                .key("PB_03_2018")
                .source("LME")
                .lastTradingDate("15-03-2018")
                .deliveryDate("17-03-2018")
                .market("PB")
                .label("Lead 13 March 2018")
                .build()
    }

    @Unroll
    def "Story 2: #dto.source and #dto2.source instruments"() {
        given: """the #dto.source instrument #dto.key with these details: #dto"""
        def repo = new MemoryInstrumentRepository()
        def sut = makeSUT(repo)

        and: """the #dto2.source instrument #dto2.key with these details: #dto2"""

        when: """When #dto.source publishes instrument #dto.key"""
        sut.process(dto)

        then: """the application publishes the #dto.key instrument internally"""
        def opt = repo.findBySourceAndKey(dto.source, dto.key)
        assert opt.isPresent()
        opt.map { Instrument i ->
            assert i.key == dto.key
            assert i.source == dto.source
            assert i.lastTradingDate == dto.lastTradingDate
            assert i.deliveryDate == dto.deliveryDate
            assert i.market == dto.market
            assert i.label == dto.label
            assert i.tradable
        }

        when: """#dto2.source publishes instrument #dto2.key"""
        sut.process(dto2)

        then: """the application publishes the #dto2.key instrument internally"""
        def opt2 = repo.findBySourceAndKey(dto2.source, dto2.key)
        assert opt2.isPresent()
        opt2.map { Instrument i ->
            assert i.key == dto2.key
            assert i.source == dto2.source
            assert i.lastTradingDate == dto.lastTradingDate
            assert i.deliveryDate == dto.deliveryDate
            assert i.market == dto2.market
            assert i.label == dto2.label
            assert !i.tradable
        }

        where: "parameters: {#dto}, {#dto2}"
        dto = InstrumentDTO.builder()
                .key("PB_03_2018")
                .source("LME")
                .lastTradingDate("15-03-2018")
                .deliveryDate("17-03-2018")
                .market("PB")
                .label("Lead 13 March 2018")
                .build()
        dto2 = InstrumentDTO.builder()
                .key("PRIME_PB_03_2018")
                .source("PRIME")
                .lastTradingDate("14-03-2018")
                .deliveryDate("18-03-2018")
                .market("LME_PB")
                .label("Lead 13 March 2018")
                .exchangeCode("PB_03_2018")
                .tradable("FALSE")
                .build()
    }

    def makeSUT(InstrumentRepository repo) {
        def rules = [new TradableChangeRule(), new DateChangeRule(repo)]
        new ParallelRulesService(rules, repo, ParallelRulesService.executor())
        //new RulesService(rules, repo)
    }
}
