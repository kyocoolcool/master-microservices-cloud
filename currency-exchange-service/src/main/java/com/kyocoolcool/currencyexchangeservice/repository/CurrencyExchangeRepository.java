package com.kyocoolcool.currencyexchangeservice.repository;

import com.kyocoolcool.currencyexchangeservice.bean.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 陳金昌 Chris Chen
 * @version 1.0 2021/3/9 4:41 PM
 */
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
    CurrencyExchange findByFromAndTo(String from, String to);
}
