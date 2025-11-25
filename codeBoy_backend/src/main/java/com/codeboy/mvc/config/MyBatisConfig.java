package com.codeboy.mvc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.codeboy.mvc.model.dao")
public class MyBatisConfig {

}
