package com.tinqinacademy.bff.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.base.OperationOutput;
import com.tinqinacademy.bff.api.exceptionmodel.ErrorResponseInfo;
import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;
import java.util.Map;

public abstract class BaseController {

    private final ObjectMapper objectMapper;

    protected BaseController(ObjectMapper objectMapper) {this.objectMapper = objectMapper;}

    protected  ResponseEntity<?> handleResult(Either<ErrorWrapper,? extends OperationOutput> result, HttpStatus httpStatus) {

        if (result.isLeft()) {
            ErrorWrapper error = result.getLeft();
            HttpStatus errorStatus = error.getErrorResponseInfoList().stream()
                .findFirst()
                .map(ErrorResponseInfo::getHttpStatus)
                .orElse(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(error, errorStatus);

        }
            OperationOutput output = result.get();
            return new ResponseEntity<>(output, httpStatus);
    }


    protected String getUserIdViaHeaders(String authorization) throws JsonProcessingException {
        String[] chunks = authorization.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        Map<String, Object> claims = objectMapper.readValue(payload, Map.class);

        // Extract specific claims
       return  (String) claims.get("id");
    }

}
