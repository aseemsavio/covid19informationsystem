package com.aseemsavio.covid19informationsystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoronaDataExtra extends CoronaData {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long todaysConfirmed;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long todaysDeaths;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastUpdated;

    public long getTodaysConfirmed() {
        return todaysConfirmed;
    }

    public void setTodaysConfirmed(long todaysConfirmed) {
        this.todaysConfirmed = todaysConfirmed;
    }

    public long getTodaysDeaths() {
        return todaysDeaths;
    }

    public void setTodaysDeaths(long todaysDeaths) {
        this.todaysDeaths = todaysDeaths;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "CoronaDataExtra{" +
                "todaysConfirmed=" + todaysConfirmed +
                ", todaysDeaths=" + todaysDeaths +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}
