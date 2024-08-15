package com.tinqinacademy.bff.core.processors.commentsprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.commentsoperations.getroomcomments.GetRoomComment;
import com.tinqinacademy.bff.api.operations.commentsoperations.getroomcomments.GetRoomCommentsBffInput;
import com.tinqinacademy.bff.api.operations.commentsoperations.getroomcomments.GetRoomCommentsBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.CommentsRestClient;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
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
public class GetRoomCommentsOperationProcessor extends BaseOperationProcessor implements GetRoomComment {

    private final CommentsRestClient commentsRestClient;

    public GetRoomCommentsOperationProcessor(Validator validator, ConversionService conversionService,
                                             ErrorMapper errorMapper, CommentsRestClient commentsRestClient) {
        super(conversionService,errorMapper,validator);
        this.commentsRestClient = commentsRestClient;
    }

    @Override
    public Either<ErrorWrapper, GetRoomCommentsBffOutput> process (GetRoomCommentsBffInput bffInput) {
        log.info("Start getAllRoomComments input {}",bffInput);
        return validateInput(bffInput).flatMap(validated-> editComment(bffInput));
    }

    private Either<ErrorWrapper,GetRoomCommentsBffOutput> editComment(GetRoomCommentsBffInput bffInput) {
        return Try.of(()->{
            GetRoomCommentsInput input = conversionService.convert(bffInput, GetRoomCommentsInput.class);
            GetRoomCommentsOutput output = commentsRestClient.getComments(input.getRoomId());
            GetRoomCommentsBffOutput bffOutput = conversionService.convert(output, GetRoomCommentsBffOutput.class);
            log.info("End getAllRoomComments output {}", bffOutput);
            return bffOutput;

        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }
}
