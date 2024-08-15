package com.tinqinacademy.bff.core.processors.commentsprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.commentsoperations.editadmincomment.EditComment;
import com.tinqinacademy.bff.api.operations.commentsoperations.editadmincomment.EditCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsoperations.editadmincomment.EditCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.CommentsRestClient;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentOutput;
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
public class EditCommentOperationProcessor extends BaseOperationProcessor implements EditComment {

    private final CommentsRestClient commentsRestClient;

    public EditCommentOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper,
                                         CommentsRestClient commentsRestClient) {
        super(conversionService, errorMapper,validator);
        this.commentsRestClient = commentsRestClient;
    }

    @Override
    public Either<ErrorWrapper, EditCommentBffOutput> process(EditCommentBffInput bffInput) {
        log.info("Start EditRoomComment input {}", bffInput);
        return validateInput(bffInput).flatMap(validated -> editComment(bffInput));
    }

    private Either<ErrorWrapper, EditCommentBffOutput> editComment(EditCommentBffInput bffInput) {
        return Try.of(() -> {
                EditCommentInput input = conversionService.convert(bffInput, EditCommentInput.class);
                EditCommentOutput output = commentsRestClient.editCommentByAdmin(input.getCommentId(),input);
                EditCommentBffOutput bffOutput = conversionService.convert(output, EditCommentBffOutput.class);
                log.info("End editRoomComment output {}", bffOutput);
                return bffOutput;

            })
            .toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
            ));
    }
}
