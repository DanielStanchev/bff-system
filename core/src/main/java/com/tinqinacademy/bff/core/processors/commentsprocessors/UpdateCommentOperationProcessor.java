package com.tinqinacademy.bff.core.processors.commentsprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.commentsoperations.updateusercomment.UpdateComment;
import com.tinqinacademy.bff.api.operations.commentsoperations.updateusercomment.UpdateCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsoperations.updateusercomment.UpdateCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.CommentsRestClient;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentInput;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentOutput;
import feign.FeignException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class UpdateCommentOperationProcessor extends BaseOperationProcessor implements UpdateComment {

    private final CommentsRestClient commentsRestClient;

    public UpdateCommentOperationProcessor(Validator validator, ConversionService conversionService, ErrorMapper errorMapper,
                                           CommentsRestClient commentsRestClient) {
        super(conversionService, errorMapper,validator);
        this.commentsRestClient = commentsRestClient;
    }

    @Override
    public Either<ErrorWrapper, UpdateCommentBffOutput> process(UpdateCommentBffInput bffInput) {
        log.info("Start updateRoomComment input {}", bffInput);
        return validateInput(bffInput).flatMap(validated -> updateComment(bffInput));
    }

    private Either<ErrorWrapper, UpdateCommentBffOutput> updateComment(UpdateCommentBffInput bffInput) {
        return Try.of(() -> {
                UpdateCommentInput input = conversionService.convert(bffInput,UpdateCommentInput.class);
                UpdateCommentOutput output = commentsRestClient.updateCommentByUser(input.getCommentId(),input);
                UpdateCommentBffOutput bffOutput = conversionService.convert(output, UpdateCommentBffOutput.class);
                log.info("End updateRoomComment output {}", bffOutput);
                return bffOutput;

            })
            .toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
            ));
    }
}
