package org.mymoney.service;

import org.junit.jupiter.api.*;
import org.mymoney.entity.Investment;
import org.mymoney.enums.FundType;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PortfolioManagerTest {
    PortfolioManager portfolioManager = new PortfolioManager();

    @Nested
    class addAllocationToPortFolio {

        FundType fundType;
        int value;

        @BeforeEach
        void setup() {
            fundType = null;
            value = 0;
            portfolioManager = clearPortfolioManager(portfolioManager);
        }

        void call() {
            portfolioManager.addAllocationToPortFolio(fundType, value);
        }

        @Nested
        class shouldSaveCorrectAllocationToPortfolio {
            FundType otherFundType = FundType.EQUITY;

            void setBaseConditions(){
                fundType = FundType.DEBT;
                value = 100;
            }
            @Test
            void isFundValueCorrect() {
                setBaseConditions();
                call();
                Investment investment = portfolioManager.getPortfolio().get(fundType);
                assertEquals(investment.getStartingAmount(), value);
            }

            @Test
            void isOtherFundValueBlank() {
                setBaseConditions();
                call();
                Investment investment = portfolioManager.getPortfolio().get(otherFundType);
                assertNull(investment);
            }

        }
        private PortfolioManager clearPortfolioManager(PortfolioManager portfolioManager) {
            return new PortfolioManager();
        }
    }

}