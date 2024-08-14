package com.tinqinacademy.bff.domain.config;


import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    //used with feign client as default http client used by feign does not support PATCH requests
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

////used with requestLine
//    @Bean
//    public HotelRestExport hotelRestExport() {
//        return Feign.builder()
//            .contract(new Contract.Default())
//            .client(new OkHttpClient())
//            .encoder(new JacksonEncoder(objectMapper))
//            .decoder(new JacksonDecoder(objectMapper))
//            .target(HotelRestExport.class,"http://localhost:8081");
//
//    }

}
