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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.aseemsavio.covid19informationsystem.utils.C19ISConstants.*;

@RestController
@RequestMapping("/api/v1")
public class VersionOneController {

    @Autowired
    CoronaDataService coronaDataService;

    @Async
    @GetMapping("/timeSeries")
    public CompletableFuture<ResponseEntity<Response>> findAllTimeSeries() throws DataNotFoundException {
        List<CoronaData> data = null;
        Response response = new Response();
        data = coronaDataService.findAllData();
        LocalCache localCache = LocalCache.getInstance();
        response.setDates(localCache.getDates());
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setTimeSeriesData(data);
        return CompletableFuture.completedFuture(new ResponseEntity< >(response, HttpStatus.OK));
    }

    @Async
    @GetMapping("/count")
    public CompletableFuture<ResponseEntity<Response>> findAllCount() throws DataNotFoundException {
        List<CoronaDataExtra> data = null;
        Response response = new Response();
        data = coronaDataService.findAllCount();
        LocalCache localCache = LocalCache.getInstance();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setCountData(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping("/provinces")
    public CompletableFuture<ResponseEntity<Response>> getAllProvinces() throws DataNotFoundException {
        List<String> data = null;
        Response response = new Response();
        data = coronaDataService.findAllProvinces();
        LocalCache localCache = LocalCache.getInstance();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setProvinces(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping("/countries")
    public CompletableFuture<ResponseEntity<Response>> getAllCountries() throws DataNotFoundException {
        List<String> data = null;
        Response response = new Response();
        data = coronaDataService.findAllCountries();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        LocalCache localCache = LocalCache.getInstance();
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setCountries(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping("/timeSeries/findByProvince")
    public CompletableFuture<ResponseEntity<Response>> getTimeSeriesByProvince(@RequestParam String province) throws DataNotFoundException, CovidInvalidDataException {
        List<CoronaData> data = coronaDataService.findByProvinces(province);
        Response response = new Response();
        LocalCache localCache = LocalCache.getInstance();
        response.setDates(localCache.getDates());
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setTimeSeriesData(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping("/timeSeries/findByCountry")
    public CompletableFuture<ResponseEntity<Response>> getTimeSeriesByCountry(@RequestParam String country) throws DataNotFoundException, CovidInvalidDataException {
        List<CoronaData> data = null;
        Response response = new Response();
        data = coronaDataService.findByCountry(country);
        LocalCache localCache = LocalCache.getInstance();
        response.setDates(localCache.getDates());
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setTimeSeriesData(data);
        return CompletableFuture.completedFuture(new ResponseEntity< >(response, HttpStatus.OK));
    }

    @Async
    @GetMapping("/count/findByProvince")
    public CompletableFuture<ResponseEntity<Response>> getCountByProvince(@RequestParam String province) throws DataNotFoundException, CovidInvalidDataException {
        List<CoronaDataExtra> data = null;
        data = coronaDataService.findByProvinceCount(province);
        Response response = new Response();
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        LocalCache localCache = LocalCache.getInstance();
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setCountData(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Async
    @GetMapping("/count/findByCountry")
    public CompletableFuture<ResponseEntity<Response>> getCountByCountry(@RequestParam String country) throws CovidInvalidDataException, DataNotFoundException {
        List<CoronaDataExtra> data = null;
        Response response = new Response();
        data = coronaDataService.findByCountryCount(country);
        response.setStatus(STATUS_OK);
        response.setTotalResults(data.size());
        LocalCache localCache = LocalCache.getInstance();
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setCountData(data);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }

    private ResponseEntity<Response> getNotFoundErrorResponse(Response response) {
        LocalCache localCache = LocalCache.getInstance();
        response.setStatus(STATUS_FAILED);
        response.setTotalResults(0);
        Error error = new Error(ERROR_CODE_NOT_FOUND, ERROR_MSG_NOT_FOUND);
        response.setLastUpdatedMinutes(TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - localCache.getLastUpdatedMilliSeconds()));
        response.setErrors(Arrays.asList(error));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
