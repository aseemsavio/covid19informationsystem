
## Introduction
COVID-19 Information System is a REST API that can be used as a source for providing live updates on the ongoing pandemic.
We get our data from CSSE at Johns Hopkins University.
Websites and mobile applications that do not intend in monetizing this information are free to consume this API while adding the Name
and URL of this API in your cedits.


## Overview
I think a fellow developer should be aware that this service is hosted on a free Heroku dyno.
So, be informed that you might experience latency in response.


## Authentication
Authorization to this service is based on a Secret Authorization API Code that needs to be added
to the header of every request you send in. To get your authorization Code, please write to me
(aseemsavio3@gmail.com) with your full name, email address, your comapany, a brief note about how you're planning to
use this API (about your app/website).


## Error Codes

| Error Code | Error Message                               |
|------------|---------------------------------------------|
| -1         | Data Not Found                              |
| -2         | Allowed Number of requests/minutes exceeded |
| -3         | Not a registered User                       |
| -4         | Mandatory Header(s) not found               |


When the request is a success, you'll receive a status message of "OK" and in the unfortunate other case,
you'll receive a "FAILED" message.


## Rate limit
Your requests will be tracked on the basis of your Authorization Code.
Each authorization code is allowed a maximum of 30 requests per minute.


# End Points

Base URL:   https://covid19-information-system.herokuapp.com/

### Get All Countries

/api/v1/countries

Header: authorization-code


### Get All Provinces

/api/v1/provinces

Header: authorization-code


### Get Time Series data of Provinces

/api/v1/timeSeries/province/{province}

Header: authorization-code


### Get Time Series data of Countries

/api/v1/timeSeries/country/{country}

Header: authorization-code


### Get Count data of Countries

/api/v1/count/country/{country}

Header: authorization-code


### Get Count data of Provinces

/api/v1/count/province/{province}

Header: authorization-code


For any queries and for contributing to this project, please email me - aseemsavio3@gmail.com

The information source for this project is https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data/csse_covid_19_time_series
