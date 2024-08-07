package com.tinqinacademy.bff.domain.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
//    private final ObjectMapper objectMapper;
//
//    public ClientConfiguration(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }

    //used with feign client
//    @Bean
//    public OkHttpClient client() {
//        return new OkHttpClient();
//    }

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
