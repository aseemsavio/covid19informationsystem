package com.aseemsavio.covid19informationsystem.model;

import java.util.List;

public class FilterErrorResponse {

    private String status;
    private int totalResults;
    private List<Error> errors;

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

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "FilterErrorResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", errors=" + errors +
                '}';
    }
}
