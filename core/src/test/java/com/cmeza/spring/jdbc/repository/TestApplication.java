package com.cmeza.spring.jdbc.repository;

import com.cmeza.spring.jdbc.repository.support.annotations.EnableJdbcRepositories;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJdbcRepositories
@EnableTransactionManagement
@SpringBootApplication
public class TestApplication {

}
