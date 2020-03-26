package com.aseemsavio.covid19informationsystem.repository;

import com.aseemsavio.covid19informationsystem.model.CoronaData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoronaDataRepository extends MongoRepository<CoronaData, String> {
    List<CoronaData> findByProvince(String province);

    List<CoronaData> findByCountry(String country);
}
