package com.tinqinacademy.bff.core.conversion.commentsconversion.updateuserconversion;

import com.tinqinacademy.bff.api.operations.commentsoperations.updateusercomment.UpdateCommentBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentInput;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommentInputConverter extends BaseConverter<UpdateCommentBffInput, UpdateCommentInput> {
    @Override
    protected UpdateCommentInput convertObject(UpdateCommentBffInput source) {
        return UpdateCommentInput.builder()
            .commentId(source.getCommentId())
            .userId(source.getUserId())
            .content(source.getContent()).build();
    }
}
