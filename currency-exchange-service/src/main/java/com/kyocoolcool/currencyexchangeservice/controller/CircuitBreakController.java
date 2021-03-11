package com.kyocoolcool.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author 陳金昌 Chris Chen
 * @version 1.0 2021/3/11 2:45 PM
 */
@RestController
public class CircuitBreakController {
    Logger logger = LoggerFactory.getLogger(CircuitBreakController.class);
    @GetMapping("/sample-api")
//    @Retry(name = "sample-api",fallbackMethod = "hardcodedResponse")
   @CircuitBreaker(name = "default",fallbackMethod = "hardcodedResponse")
    public String sampleApi() {
        logger.info("Sample API received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://demo.tw", String.class);
        return forEntity.getBody();
    }

    @GetMapping("/sample-api2")
//    @RateLimiter(name = "default")
    @Bulkhead(name = "sample-api", fallbackMethod = "hardcodedResponse")
    public String sampleApi2()  {
        logger.info("Sample API received");
        return "sample-api";
    }

    public String hardcodedResponse(Exception ex) {
        logger.info("fallback");
        return "fallback-response";
    }
}
