package org.mymoney.pojo;

import org.mymoney.enums.FundType;

import java.time.Month;
import java.util.ArrayList;

public class FundChangeRequest {
    private ArrayList<FundChange> fundChangeList;
    private final Month month;

    public FundChangeRequest(Month month) {
        this.month = month;
    }

    public Month getMonth() {
        return month;
    }

    public ArrayList<FundChange> getFundChangeList() {
        return fundChangeList;
    }

    public void addFundChange(FundType fundType, float value) {
        if (fundChangeList == null) fundChangeList = new ArrayList<>();
        fundChangeList.add(new FundChange(fundType, value));
    }

    public class FundChange {
        private FundType fundType;
        private float value;

        public FundChange(FundType fundType, float value) {
            this.fundType = fundType;
            this.value = value;
        }

        public FundType getFundType() {
            return fundType;
        }

        public void setFundType(FundType fundType) {
            this.fundType = fundType;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }
}
