package com.aseemsavio.covid19informationsystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int totalResults;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CoronaData> timeSeriesData;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CoronaDataExtra> countData;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> provinces;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> countries;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<CoronaData> getTimeSeriesData() {
        return timeSeriesData;
    }

    public void setTimeSeriesData(List<CoronaData> timeSeriesData) {
        this.timeSeriesData = timeSeriesData;
    }

    public List<CoronaDataExtra> getCountData() {
        return countData;
    }

    public void setCountData(List<CoronaDataExtra> countData) {
        this.countData = countData;
    }

    public List<String> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<String> provinces) {
        this.provinces = provinces;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", timeSeriesData=" + timeSeriesData +
                ", countData=" + countData +
                ", provinces=" + provinces +
                ", countries=" + countries +
                '}';
    }
}
