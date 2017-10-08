package io.instrument.service.repository.impl

import io.instrument.service.model.InstrumentFactory
import io.instrument.service.model.InstrumentFactorySpec
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@Title("Memory Instrument Repository")
class MemoryInstrumentRepositorySpec extends Specification {
    @Unroll("instrument added: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument added"() {
        given:
        def sut = new MemoryInstrumentRepository()

        when:
        sut.add(InstrumentFactory.from(args))

        then:
        assert sut.findAll().size() == 1

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | exchangeCode | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | "Lead 13 March 2018" | null         | null
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "PB_03_2018" | "TRUE"

        args = InstrumentFactorySpec.toArgs([key, source, lastTradingDate, deliveryDate, market, label, exchangeCode, tradable])
    }
}
