package com.tinqinacademy.bff.core.conversion.commentsconversion.editadmincommentconversion;

import com.tinqinacademy.bff.api.operations.commentsoperations.editadmincomment.EditCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class EditCommentOutputConverter extends BaseConverter<EditCommentOutput, EditCommentBffOutput> {
    @Override
    protected EditCommentBffOutput convertObject(EditCommentOutput source) {
        return EditCommentBffOutput.builder()
            .id(source.getId()).build();
    }
}
