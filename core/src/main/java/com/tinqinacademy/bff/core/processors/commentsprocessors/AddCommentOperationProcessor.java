package com.tinqinacademy.bff.core.processors.commentsprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.commentsoperations.addcomment.AddComment;
import com.tinqinacademy.bff.api.operations.commentsoperations.addcomment.AddCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsoperations.addcomment.AddCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.CommentsRestClient;
import com.tinqinacademy.comments.api.operations.addcomment.AddCommentInput;
import com.tinqinacademy.comments.api.operations.addcomment.AddCommentOutput;
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
public class AddCommentOperationProcessor extends BaseOperationProcessor implements AddComment {

    private final CommentsRestClient commentsRestClient;

    public AddCommentOperationProcessor(Validator validator, ConversionService conversionService, ErrorMapper errorMapper,
                                        CommentsRestClient commentsRestClient) {
        super(conversionService, errorMapper, validator);
        this.commentsRestClient = commentsRestClient;
    }

    @Override
    public Either<ErrorWrapper, AddCommentBffOutput> process(AddCommentBffInput bffInput) {
        log.info("Start addRoomComment input {}", bffInput);
        return validateInput(bffInput).flatMap(validated -> postComment(bffInput));
    }

    private Either<ErrorWrapper, AddCommentBffOutput> postComment(AddCommentBffInput bffInput) {
        return Try.of(() -> {
                AddCommentInput input = conversionService.convert(bffInput, AddCommentInput.class);
                AddCommentOutput output = commentsRestClient.addComment(input.getRoomId(),input);
                AddCommentBffOutput bffOutput = conversionService.convert(output, AddCommentBffOutput.class);
                log.info("End addRoomComment output {}", bffOutput);
                return bffOutput;

            })
            .toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
            ));
    }
}
