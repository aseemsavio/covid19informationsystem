package com.aseemsavio.covid19informationsystem.utils;

public class C19ISConstants {

    public static final String PROVINCE = "Province";
    public static final String NULL = null;

    public static final String SEPARATOR = "/*";
    public static final String ADMIN = "/admin";
    public static final String ADMIN_SECURITY = ADMIN.concat(SEPARATOR).intern();
    public static final String USER = "/user";
    public static final String USERS = "/users";
    public static final String ACTUATOR = "/actuator";
    public static final String ACTUATOR_SECURITY = ACTUATOR.concat(SEPARATOR).intern();
    public static final String API_V1 = "/api/v1";
    public static final String TIMESERIES = "/timeSeries";
    public static final String COUNT = "/count";
    public static final String PROVINCES = "/provinces";
    public static final String COUNTRIES = "/countries";
    public static final String TIME_SERIES_PROVINCE = "/timeSeries/province/{province}";
    public static final String TIME_SERIES_COUNTRY = "/timeSeries/country/{country}";
    public static final String COUNT_PROVINCE = "/count/province/{province}";
    public static final String COUNT_COUNTRY = "/count/country/{country}";
    public static final String PROVINCE_PV = "province";
    public static final String COUNTRY_PV = "country";
    public static final String DUB_HEARTBEAT = "dub";
    public static final String HEARTBEAT_HEALTH_ENDPOINT = "/all/lub";
    public static final String CRON_EVERY_ONE_HOUR = "0 0/59 * * * *";
    public static final String CRON_EVERY_THIRTY_MINS = "0 0/30 * * * *";

    public static final String EMAIL_ALREADY_FOUND = "Email already Found.";
    public static final String DUPLICATE_AUTH_KEY = "Could not create your account now. Please try again after sometime.";
    public static final String TRY_AGAIN_LATER = "Couldn't complete your request. Please try again later.";

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

