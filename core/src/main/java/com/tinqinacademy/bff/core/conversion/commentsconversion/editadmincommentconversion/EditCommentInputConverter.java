package com.tinqinacademy.bff.core.conversion.commentsconversion.editadmincommentconversion;

import com.tinqinacademy.bff.api.operations.commentsoperations.editadmincomment.EditCommentBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentInput;
import org.springframework.stereotype.Component;

@Component
public class EditCommentInputConverter extends BaseConverter<EditCommentBffInput, EditCommentInput> {
    @Override
    protected EditCommentInput convertObject(EditCommentBffInput source) {
        return EditCommentInput.builder()
            .commentId(source.getCommentId())
            .roomId(source.getRoomId())
            .content(source.getContent()).build();
    }
}
