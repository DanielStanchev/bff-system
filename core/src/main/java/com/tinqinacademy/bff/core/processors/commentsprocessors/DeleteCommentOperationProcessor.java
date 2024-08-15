package com.tinqinacademy.bff.core.processors.commentsprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.commentsoperations.deletecomment.DeleteComment;
import com.tinqinacademy.bff.api.operations.commentsoperations.deletecomment.DeleteCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsoperations.deletecomment.DeleteCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.CommentsRestClient;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentOutput;
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
public class DeleteCommentOperationProcessor extends BaseOperationProcessor implements DeleteComment {

    private final CommentsRestClient commentsRestClient;

    public DeleteCommentOperationProcessor(Validator validator, ConversionService conversionService,
                                           ErrorMapper errorMapper, CommentsRestClient commentsRestClient) {
        super(conversionService,errorMapper,validator);
        this.commentsRestClient = commentsRestClient;
    }

    @Override
    public Either<ErrorWrapper, DeleteCommentBffOutput> process(DeleteCommentBffInput bffInput) {
        log.info("Start deleteRoomComment input {}", bffInput);
        return validateInput(bffInput).flatMap(validated->deleteComment(bffInput));
    }

    private Either<ErrorWrapper,DeleteCommentBffOutput> deleteComment(DeleteCommentBffInput bffInput) {
        return Try.of(()->{
            DeleteCommentInput input = conversionService.convert(bffInput, DeleteCommentInput.class);
            DeleteCommentOutput output = commentsRestClient.deleteComment(input.getCommentId(),input);
            DeleteCommentBffOutput bffOutput = conversionService.convert(output, DeleteCommentBffOutput.class);
            log.info("End deleteRoomComment output {}", bffOutput);
            return bffOutput;

        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }
}
