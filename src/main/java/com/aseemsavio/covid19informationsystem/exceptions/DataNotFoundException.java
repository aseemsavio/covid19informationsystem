package com.aseemsavio.covid19informationsystem.exceptions;

public class DataNotFoundException extends Exception {

    public DataNotFoundException() {
        super("COVID-19 Data Not found for the requested resource");
    }

}
