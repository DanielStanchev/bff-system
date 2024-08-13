package com.tinqinacademy.bff.core.base;

import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.api.base.OperationInput;
import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import io.vavr.control.Either;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Slf4j
public abstract class BaseOperationProcessor {

    protected final ConversionService conversionService;
    protected final ErrorMapper errorMapper;
    protected final Validator validator;

    protected BaseOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator) {
        this.conversionService = conversionService;
        this.errorMapper = errorMapper;
        this.validator = validator;
    }

    public Either<ErrorWrapper, ? extends OperationInput> validateInput(OperationInput input){

        Set<ConstraintViolation<OperationInput>> validationResponse = validator.validate(input);

        if(validationResponse.isEmpty()){
            return Either.right(input);
        }
        ErrorWrapper validationErrors = errorMapper.handleValidationViolation(validationResponse, HttpStatus.BAD_REQUEST);
        return Either.left(validationErrors);
    }

}
