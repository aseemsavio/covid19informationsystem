package com.aseemsavio.covid19informationsystem.model;

public class CoronaCount {

    private long confirmedCount;
    private long deathCount;

    public CoronaCount() {
        this.confirmedCount = 0;
        this.deathCount = 0;
    }

    public long getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(long confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public long getDeathCount() {
        return deathCount;
    }

    public void setDeathCount(long deathCount) {
        this.deathCount = deathCount;
    }

    @Override
    public String toString() {
        return "CoronaCount{" +
                "confirmedCount=" + confirmedCount +
                ", deathCount=" + deathCount +
                '}';
    }
}
