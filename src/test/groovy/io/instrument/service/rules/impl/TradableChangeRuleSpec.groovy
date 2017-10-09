package io.instrument.service.rules.impl

import io.instrument.service.api.InstrumentDTO
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@Title("Date Change Rule")
class TradableChangeRuleSpec extends Specification {
    @Unroll("process instrument: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "process instrument"() {
        given:
        def sut = new TradableChangeRule()

        when:
        Optional<InstrumentDTO> res = sut.process(InstrumentDTO.builder()
                .key(key).source(source)
                .partialBuild())

        then:
        assert res.isPresent()
        res.map {InstrumentDTO x ->
            assert x.tradable == tradable
        }

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | exchangeCode | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | "Lead 13 March 2018" | null         | true
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "PB_03_2018" | false
    }
}
