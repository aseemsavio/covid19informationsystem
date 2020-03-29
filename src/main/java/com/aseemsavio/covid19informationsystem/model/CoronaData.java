package com.aseemsavio.covid19informationsystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "corona_data")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoronaData {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String province;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String country;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double latitude;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double longitude;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> confirmedCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> deathCount;

    /*@JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> recoveredCount;*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Long> getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(List<Long> confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public List<Long> getDeathCount() {
        return deathCount;
    }

    public void setDeathCount(List<Long> deathCount) {
        this.deathCount = deathCount;
    }

   /* public List<Long> getRecoveredCount() {
        return recoveredCount;
    }

    public void setRecoveredCount(List<Long> recoveredCount) {
        this.recoveredCount = recoveredCount;
    }*/

    @Override
    public String toString() {
        return "CoronaData{" +
                "id='" + id + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", confirmedCount=" + confirmedCount +
                ", deathCount=" + deathCount +
               // ", recoveredCount=" + recoveredCount +
                '}';
    }
}
