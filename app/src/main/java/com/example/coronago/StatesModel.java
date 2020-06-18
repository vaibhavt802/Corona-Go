package com.example.coronago;

public class StatesModel {
    private String state, cured, deaths, noOfCases;
    public StatesModel() {

    }

    public StatesModel(String state, String cured, String deaths, String noOfCases) {
        this.state = state;
        this.cured = cured;
        this.deaths = deaths;
        this.noOfCases = noOfCases;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCured() {
        return cured;
    }

    public void setCured(String cured) {
        this.cured = cured;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getNoOfCases() {
        return noOfCases;
    }

    public void setNoOfCases(String noOfCases) {
        this.noOfCases = noOfCases;
    }
}
