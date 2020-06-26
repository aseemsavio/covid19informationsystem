package com.aseemsavio.covid19informationsystem.utils;

public class C19ISConstants {

    public static final String PROVINCE = "Province";

    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer THREE = 3;

    public static final String CONFIRMED_FILE_NAME = "time_series_covid19_confirmed_global.csv";
    public static final String DEATH_FILE_NAME = "time_series_covid19_deaths_global.csv";
    public static final String RECOVERED_FILE_NAME = "time_series_covid19_recovered_global.csv";
    public static final String COMMA = ",";
    public static final String EMPTY_STRING = "";
    public static final String STATUS_OK = "OK";
    public static final String STATUS_FAILED = "FAILED";
    public static final String HEADER_AUTHORIZATION_CODE = "authorization-code";

    // Error Codes
    public static final int ERROR_CODE_NOT_FOUND = -1;
    public static final int ERROR_CODE_LIMIT_EXCEEDED = -2;
    public static final int ERROR_CODE_NOT_A_REGISTERED_USER = -3;
    public static final int ERROR_CODE_HEADER_NOT_FOUND = -4;
    public static final int ERROR_CODE_COVID_ERROR = -5;

    // Error Messages
    public static final String ERROR_MSG_NOT_FOUND = "Data Not Found";
    public static final String ERROR_MSG_LIMIT_EXCEEDED = "You have exceeded the allowed number of requests/minute. Please try again after sometime.";
    public static final String ERROR_MSG_NOT_A_REGISTERED_USER = "You are not a registered user.";
    public static final String ERROR_MSG_HEADER_NOT_FOUND = "One or more of the Mandatory headers is absent in the request.";
    public static final String ERROR_MSG_COVID_ERROR = "Unexpected Error Occurred in the application. Please try later.";

}

