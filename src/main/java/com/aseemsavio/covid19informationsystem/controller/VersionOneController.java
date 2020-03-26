package com.aseemsavio.covid19informationsystem.controller;

import com.aseemsavio.covid19informationsystem.model.CoronaData;
import com.aseemsavio.covid19informationsystem.model.CoronaDataExtra;
import com.aseemsavio.covid19informationsystem.model.Error;
import com.aseemsavio.covid19informationsystem.model.Response;
import com.aseemsavio.covid19informationsystem.service.CoronaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;

@RestController
@RequestMapping("/api/v1")
public class VersionOneController {

    @Autowired
    CoronaDataService coronaDataService;

    @GetMapping("/timeSeries")
    public ResponseEntity<Response> findAllTimeSeries() {
        List<CoronaData> data = coronaDataService.findAllData();
        Response response = new Response();
        if (data == null || data.size() == 0) {
            return getNotFoundErrorResponse(response);
        }
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setTimeSeriesData(data);
        return new ResponseEntity< >(response, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Response> findAllCount() {
        List<CoronaDataExtra> data = coronaDataService.findAllCount();
        Response response = new Response();
        if (data == null || data.size() == 0) {
            return getNotFoundErrorResponse(response);
        }
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setCountData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/provinces")
    public ResponseEntity<Response> getAllProvinces() {
        List<String> data = coronaDataService.findAllProvinces();
        Response response = new Response();
        if (data == null || data.size() == 0) {
            return getNotFoundErrorResponse(response);
        }
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setProvinces(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/countries")
    public ResponseEntity<Response> getAllCountries() {
        List<String> data = coronaDataService.findAllCountries();
        Response response = new Response();
        if (data == null || data.size() == 0) {
            return getNotFoundErrorResponse(response);
        }
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setCountries(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/timeSeries/findByProvince")
    public ResponseEntity<Response> getTimeSeriesByProvince(@RequestParam String province) {
        List<CoronaData> data = coronaDataService.findByProvinces(province);
        Response response = new Response();
        if (data == null || data.size() == 0) {
            return getNotFoundErrorResponse(response);
        }
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setTimeSeriesData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/timeSeries/findByCountry")
    public ResponseEntity<Response> getTimeSeriesByCountry(@RequestParam String country) {
        List<CoronaData> data = coronaDataService.findByCountry(country);
        Response response = new Response();
        if (data == null || data.size() == 0) {
            return getNotFoundErrorResponse(response);
        }
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setTimeSeriesData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/count/findByProvince")
    public ResponseEntity<Response> getCountByProvince(@RequestParam String province) {
        List<CoronaDataExtra> data = coronaDataService.findByProvinceCount(province);
        Response response = new Response();
        if (data == null || data.size() == 0) {
            return getNotFoundErrorResponse(response);
        }
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setCountData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/count/findByCountry")
    public ResponseEntity<Response> getCountByCountry(@RequestParam String country) {
        List<CoronaDataExtra> data = coronaDataService.findByCountryCount(country);
        Response response = new Response();
        if (data == null || data.size() == 0) {
            return getNotFoundErrorResponse(response);
        }
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setCountData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<Response> getNotFoundErrorResponse(Response response) {
        response.setStatus(STATUS_FAILED);
        response.setTotalResults(0);
        Error error = new Error(ERRORCODE_NOT_FOUND, ERROR_MSG_NOT_FOUND);
        response.setErrors(Arrays.asList(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
