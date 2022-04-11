package com.example.myapplication;

public class FinanceGoal {

    int id;
    String name = "";
    String monthlyVal = "";
    String annuallyVal = "";
    String futureVal = "";
    String quarterlyVal = "";
    String option = "";
    String filePath = "";

    public FinanceGoal(){ }
    public FinanceGoal(int id, String name, String monthlyVal, String annuallyVal, String futureVal, String quarterlyVal, String filePath, String option) {
        this.id = id;
        this.name = name;
        this.monthlyVal = monthlyVal;
        this.annuallyVal = annuallyVal;
        this.futureVal = futureVal;
        this.quarterlyVal = quarterlyVal;
        this.filePath = filePath;
        this.option = option;
    }

    public int getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonthlyVal() {
        return monthlyVal;
    }

    public void setMonthlyVal(String monthlyVal) {
        this.monthlyVal = monthlyVal;
    }

    public String getAnnuallyVal() {
        return annuallyVal;
    }

    public void setAnnuallyVal(String annuallyVal) {
        this.annuallyVal = annuallyVal;
    }

    public String getFutureVal() {
        return futureVal;
    }

    public void setFutureVal(String futureVal) {
        this.futureVal = futureVal;
    }

    public String getQuarterlyVal() {
        return quarterlyVal;
    }

    public void setQuarterlyVal(String quarterlyVal) {
        this.quarterlyVal = quarterlyVal;
    }
}
