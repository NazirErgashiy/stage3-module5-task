package com.mjc.school.service;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableTransactionManagement
//@EnableCaching(proxyTargetClass = true)
//@EnableAutoConfiguration
@ComponentScan("com.mjc.school.repository")
@ComponentScan("com.mjc.school.service")
public class ServiceConfig {
}
