package com.tinqinacademy.bff.core.conversion.commentsconversion.deletecommentconversion;

import com.tinqinacademy.bff.api.operations.commentsoperations.deletecomment.DeleteCommentBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentInput;
import org.springframework.stereotype.Component;

@Component
public class DeleteCommentInputConverter extends BaseConverter<DeleteCommentBffInput, DeleteCommentInput> {
    @Override
    protected DeleteCommentInput convertObject(DeleteCommentBffInput source) {
        return DeleteCommentInput.builder()
            .commentId(source.getCommentId()).build();
    }
}
