package com.tinqinacademy.bff.domain;

import com.tinqinacademy.authentication.restexport.AuthenticationRestExport;
import com.tinqinacademy.bff.domain.config.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "authentication", url = "${authentication.url}", configuration = ClientConfiguration.class)
public interface AuthenticationRestClient extends AuthenticationRestExport {}
