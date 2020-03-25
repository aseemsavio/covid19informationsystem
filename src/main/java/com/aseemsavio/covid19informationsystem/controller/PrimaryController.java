package com.aseemsavio.covid19informationsystem.controller;

import com.aseemsavio.covid19informationsystem.model.CoronaData;
import com.aseemsavio.covid19informationsystem.model.CoronaDataExtra;
import com.aseemsavio.covid19informationsystem.model.Response;
import com.aseemsavio.covid19informationsystem.repository.UserRepository;
import com.aseemsavio.covid19informationsystem.service.CoronaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.STATUS_OK;

@RestController
@RequestMapping("/latest")
public class PrimaryController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CoronaDataService coronaDataService;

    @GetMapping("/all/timeSeries")
    public ResponseEntity<Response> findAllTimeSeries() {
        List<CoronaData> data = coronaDataService.findAllData();
        Response response = new Response();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setTimeSeriesData(data);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping("/all/count")
    public ResponseEntity<Response> findAllCount() {
        List<CoronaDataExtra> data = coronaDataService.findAllCount();
        Response response = new Response();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setCountData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/provinces")
    public ResponseEntity<Response> getAllProvinces() {
        List<String> data = coronaDataService.findAllProvinces();
        Response response = new Response();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setProvinces(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
