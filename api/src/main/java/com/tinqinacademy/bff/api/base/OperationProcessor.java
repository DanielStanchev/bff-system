package com.tinqinacademy.bff.api.base;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import io.vavr.control.Either;

public interface OperationProcessor<O extends OperationOutput, I extends OperationInput> {
    Either<ErrorWrapper, O> process(I input);
}
