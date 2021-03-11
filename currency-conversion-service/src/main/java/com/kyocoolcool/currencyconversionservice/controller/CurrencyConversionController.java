package com.kyocoolcool.currencyconversionservice.controller;

import com.kyocoolcool.currencyconversionservice.CurrencyExchangeProxy;
import com.kyocoolcool.currencyconversionservice.bean.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 陳金昌 Chris Chen
 * @version 1.0 2021/3/9 7:11 PM
 */
@RestController
public class CurrencyConversionController {
    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
        CurrencyConversion currencyConversion = responseEntity.getBody();
        return new CurrencyConversion(10001L, from, to, quantity, currencyConversion.getConversionMultiple(), quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + "  rest template");
    }


    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);
        return new CurrencyConversion(10001L, from, to, quantity, currencyConversion.getConversionMultiple(), quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + "   feign");
    }


    @GetMapping("/hello")
    public String hello() {
        AtomicInteger i = new AtomicInteger();
        while (i.get() < 3) {
            new Thread(() -> {
                ResponseEntity responseEntity = new RestTemplate().getForEntity("http://localhost:8001/sample-api2", String.class);
                Object body = responseEntity.getBody();
                i.getAndIncrement();
            }).start();

        }
        return "hello";
    }
}
