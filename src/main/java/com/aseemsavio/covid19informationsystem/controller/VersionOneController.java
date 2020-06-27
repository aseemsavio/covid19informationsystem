package com.aseemsavio.covid19informationsystem.controller;

import com.aseemsavio.covid19informationsystem.exceptions.CovidInvalidDataException;
import com.aseemsavio.covid19informationsystem.exceptions.DataNotFoundException;
import com.aseemsavio.covid19informationsystem.model.CoronaData;
import com.aseemsavio.covid19informationsystem.model.CoronaDataExtra;
import com.aseemsavio.covid19informationsystem.model.Error;
import com.aseemsavio.covid19informationsystem.model.Response;
import com.aseemsavio.covid19informationsystem.service.CoronaDataService;
import com.aseemsavio.covid19informationsystem.service.LocalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;

@RestController
@RequestMapping(API_V1)
public class VersionOneController {

    @Autowired
    CoronaDataService coronaDataService;

    @Async
    @GetMapping(TIMESERIES)
    public CompletableFuture<ResponseEntity<Response>> findAllTimeSeries() throws DataNotFoundException {
        List<CoronaData> data;
        var response = new Response();
        data = coronaDataService.findAllData();
        var localCache = LocalCache.getInstance();
        response.setDates(localCache.getDates());
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setTimeSeriesData(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping(COUNT)
    public CompletableFuture<ResponseEntity<Response>> findAllCount() throws DataNotFoundException {
        List<CoronaDataExtra> data;
        var response = new Response();
        data = coronaDataService.findAllCount();
        var localCache = LocalCache.getInstance();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setCountData(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping(PROVINCES)
    public CompletableFuture<ResponseEntity<Response>> getAllProvinces() throws DataNotFoundException {
        List<String> data;
        var response = new Response();
        data = coronaDataService.findAllProvinces();
        var localCache = LocalCache.getInstance();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setProvinces(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping(COUNTRIES)
    public CompletableFuture<ResponseEntity<Response>> getAllCountries() throws DataNotFoundException {
        List<String> data;
        var response = new Response();
        data = coronaDataService.findAllCountries();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        var localCache = LocalCache.getInstance();
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setCountries(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping(value = TIME_SERIES_PROVINCE)
    public CompletableFuture<ResponseEntity<Response>> getTimeSeriesByProvince(@PathVariable(PROVINCE_PV) String province) throws DataNotFoundException, CovidInvalidDataException {
        List<CoronaData> data = coronaDataService.findByProvinces(province);
        var response = new Response();
        var localCache = LocalCache.getInstance();
        response.setDates(localCache.getDates());
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setTimeSeriesData(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping(value = TIME_SERIES_COUNTRY)
    public CompletableFuture<ResponseEntity<Response>> getTimeSeriesByCountry(@PathVariable(COUNTRY_PV) String country) throws DataNotFoundException, CovidInvalidDataException {
        List<CoronaData> data;
        var response = new Response();
        data = coronaDataService.findByCountry(country);
        var localCache = LocalCache.getInstance();
        response.setDates(localCache.getDates());
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setTimeSeriesData(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping(value = COUNT_PROVINCE)
    public CompletableFuture<ResponseEntity<Response>> getCountByProvince(@PathVariable(PROVINCE_PV) String province) throws DataNotFoundException, CovidInvalidDataException {
        List<CoronaDataExtra> data;
        data = coronaDataService.findByProvinceCount(province);
        var response = new Response();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        var localCache = LocalCache.getInstance();
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setCountData(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping(value = COUNT_COUNTRY)
    public CompletableFuture<ResponseEntity<Response>> getCountByCountry(@PathVariable(COUNTRY_PV) String country) throws CovidInvalidDataException, DataNotFoundException {
        List<CoronaDataExtra> data;
        var response = new Response();
        data = coronaDataService.findByCountryCount(country);
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        var localCache = LocalCache.getInstance();
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setCountData(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    private ResponseEntity<Response> getNotFoundErrorResponse(Response response) {
        var localCache = LocalCache.getInstance();
        response.setStatus(STATUS_FAILED);
        response.setTotalResults(0);
        var error = new Error(ERROR_CODE_NOT_FOUND, ERROR_MSG_NOT_FOUND);
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setErrors(List.of(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
