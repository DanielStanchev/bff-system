package com.tinqinacademy.bff.domain;

import com.tinqinacademy.bff.domain.config.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "comments", url = "${comments.url}", configuration = ClientConfiguration.class)
public interface CommentsRestClient {}
