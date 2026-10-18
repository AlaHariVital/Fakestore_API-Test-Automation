package com.api.filters;

import com.api.utility.LoggerUtility;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

/** Logs every request and response through Log4j2 (registered once in BaseService). */
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerUtility.getLogger(LoggingFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        logRequest(requestSpec);
        Response response = ctx.next(requestSpec, responseSpec);
        logResponse(response);
        return response;
    }

    private void logRequest(FilterableRequestSpecification requestSpec) {
        logger.info("REQUEST  {} {}", requestSpec.getMethod(), requestSpec.getURI());
        if (requestSpec.getBody() != null) {
            logger.info("REQUEST  body: {}", requestSpec.getBody().toString());
        }
    }

    private void logResponse(Response response) {
        logger.info("RESPONSE status: {}", response.getStatusCode());
        logger.info("RESPONSE body  : {}", response.getBody().asString());
    }
}
