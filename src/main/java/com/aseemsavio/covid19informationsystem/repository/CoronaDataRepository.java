package com.aseemsavio.covid19informationsystem.repository;

import com.aseemsavio.covid19informationsystem.model.CoronaData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoronaDataRepository extends MongoRepository<CoronaData, String> {
}
