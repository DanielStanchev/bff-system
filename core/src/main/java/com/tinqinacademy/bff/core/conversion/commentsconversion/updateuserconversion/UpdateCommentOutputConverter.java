package com.tinqinacademy.bff.core.conversion.commentsconversion.updateuserconversion;

import com.tinqinacademy.bff.api.operations.commentsoperations.updateusercomment.UpdateCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommentOutputConverter extends BaseConverter<UpdateCommentOutput, UpdateCommentBffOutput> {
    @Override
    protected UpdateCommentBffOutput convertObject(UpdateCommentOutput source) {
        return UpdateCommentBffOutput.builder()
            .id(source.getId()).build();
    }
}
