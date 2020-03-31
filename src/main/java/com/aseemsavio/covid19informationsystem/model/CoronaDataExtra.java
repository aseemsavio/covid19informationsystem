package com.aseemsavio.covid19informationsystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoronaDataExtra extends CoronaData {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long totalConfirmed;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long totalDeaths;

    /*@JsonInclude(JsonInclude.Include.NON_NULL)
    private long todaysRecovered;*/

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastUpdated;

    public long getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(long totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    /*public long getTodaysRecovered() {
        return todaysRecovered;
    }

    public void setTodaysRecovered(long todaysRecovered) {
        this.todaysRecovered = todaysRecovered;
    }
*/
    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "CoronaDataExtra{" +
                "totalConfirmed=" + totalConfirmed +
                ", totalDeaths=" + totalDeaths +
             //   ", todaysRecovered=" + todaysRecovered +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}
