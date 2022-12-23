package org.mymoney.pojo;

import org.mymoney.enums.FundType;

public class FundValueRequest {
    private FundType fundType;
    private int value;

    public FundValueRequest(FundType fundType, int value) {
        this.fundType = fundType;
        this.value = value;
    }

    public FundType getFundType() {
        return fundType;
    }

    public void setFundType(FundType fundType) {
        this.fundType = fundType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
