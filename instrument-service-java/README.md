# Instrument Service

## Current Version

    0.0.1-SNAPSHOT

## Specification

**Story 1**

    Given the “LME” instrument “PB_03_2018” with these details:
    | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              |
    | 15-03-2018        | 17-03-2018    | PB     | Lead 13 March 2018 |

    When “LME” publishes instrument “PB_03_2018”
    Then the application publishes the following instrument internally:
    | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | TRADABLE |
    | 15-03-2018        | 17-03-2018    | PB     | Lead 13 March 2018 | TRUE     |

**Story 2**

    Given the “LME” instrument “PB_03_2018” with these details:
    | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              |
    | 15-03-2018        | 17-03-2018    | LME_PB | Lead 13 March 2018 |

    And a  “PRIME” instrument “PRIME_PB_03_2018” with these details:
    | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | EXCHANGE_CODE | TRADABLE |
    | 14-03-2018        | 18-03-2018    | LME_PB | Lead 13 March 2018 | PB_03_2018    | FALSE    |

    When “LME” publishes instrument “PB_03_2018”
    Then the application publishes the following instrument internally:
    | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | TRADABLE |
    | 15-03-2018        | 17-03-2018    | LME_PB | Lead 13 March 2018 | TRUE     |

    When “PRIME” publishes instrument “PRIME_PB_03_2018”
    Then the application publishes the following instrument internally:
    | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | TRADABLE |
    | 15-03-2018        | 17-03-2018    | LME_PB | Lead 13 March 2018 | FALSE    |

## How to build

    mvn clean install

## How to run tests

    mvn tests

## Specification run results

    target/spock-reports/index.html

## E2E tests

End to end acceptance criteria tests:

    AcceptanceCriteriaSpec.groovy

## Test code coverage

### Run coverage

    mvn clean clover:setup test clover:clover

### Test code coverage run results

    target/clover/report/index.xml

