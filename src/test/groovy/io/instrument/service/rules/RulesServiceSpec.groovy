package io.instrument.service.rules

import io.instrument.service.api.InstrumentDTO
import io.instrument.service.model.Instrument
import io.instrument.service.repository.InstrumentRepository
import io.instrument.service.rules.impl.TradableChangeRule
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@Title("Rules Service")
class RulesServiceSpec extends Specification {
    @Unroll("instrument service created: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument command created from args"() {
        given:
        def repo = Mock(InstrumentRepository.class)
        def sut = new RulesService([new TradableChangeRule()], repo)

        when:
        sut.process(InstrumentDTO.builder()
                .key(key).source(source)
                .lastTradingDate(lastTradingDate)
                .deliveryDate(deliveryDate)
                .market(market).label(label)
                .exchangeCode(exchangeCode)
                .tradable(tradable)
                .build())

        then:
        1 * repo.add(_) >> { Instrument i ->
            assert i != null
        }

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | exchangeCode | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | "Lead 13 March 2018" | null         | null
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "PB_03_2018" | "TRUE"
    }
}
