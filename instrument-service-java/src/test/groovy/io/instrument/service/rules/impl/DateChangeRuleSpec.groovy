package io.instrument.service.rules.impl

import io.instrument.service.api.InstrumentDTO
import io.instrument.service.model.Instrument
import io.instrument.service.model.Market
import io.instrument.service.model.Source
import io.instrument.service.repository.InstrumentRepository
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@Title("Date Change Rule")
class DateChangeRuleSpec extends Specification {
    @Unroll("process instrument: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "process instrument"() {
        given: "date change rule specified"
        def repo = Stub(InstrumentRepository.class) {
            findBySourceAndKey(Source.LME, exchangeCode) >> Optional.of(
                    new Instrument(key,
                            Source.LME,
                            Instrument.date(lastTradingDate),
                            Instrument.date(deliveryDate),
                            Market.valueOf(market),
                            label,
                            tradable)
            )
        }
        def sut = new DateChangeRule(repo)

        when: "rule was invoked"
        Optional<InstrumentDTO> res = sut.process(InstrumentDTO.builder()
                .key(key).source(source)
                .exchangeCode(exchangeCode)
                .partialBuild())

        then: "result contains changes"
        assert res.isPresent() == present
        res.map {InstrumentDTO x ->
            assert x.lastTradingDate == Instrument.date(lastTradingDate)
            assert x.deliveryDate == Instrument.date(deliveryDate)
        }

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | exchangeCode | tradable | present
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | "Lead 13 March 2018" | null         | true     | false
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "PB_03_2018" | false    | true
    }
}
