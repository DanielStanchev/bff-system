package com.tinqinacademy.bff.rest.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.models.Operation;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;

import java.text.SimpleDateFormat;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return mapper;
    }
    @Bean
    public OperationCustomizer customizeGlobalHeaders() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            if(operation == null || operation.getParameters() == null){
                return operation;
            }
            operation.getParameters().removeIf(parameter ->
                                                   HttpHeaders.AUTHORIZATION.equalsIgnoreCase(parameter.getName()));
            return operation;
        };
    }
}