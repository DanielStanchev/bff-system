package com.tinqinacademy.bff.core.conversion.commentsconversion.deletecommentconversion;

import com.tinqinacademy.bff.api.operations.commentsoperations.deletecomment.DeleteCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class DeleteCommentOutputConverter extends BaseConverter<DeleteCommentOutput, DeleteCommentBffOutput> {
    @Override
    protected DeleteCommentBffOutput convertObject(DeleteCommentOutput source) {
        return DeleteCommentBffOutput.builder()
            .build();
    }
}
