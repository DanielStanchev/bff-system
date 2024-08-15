package com.tinqinacademy.bff.core.conversion.commentsconversion.addcommentconversion;

import com.tinqinacademy.bff.api.operations.commentsoperations.addcomment.AddCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.addcomment.AddCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class AddCommentOutputConverter extends BaseConverter<AddCommentOutput, AddCommentBffOutput> {
    @Override
    protected AddCommentBffOutput convertObject(AddCommentOutput source) {
        return AddCommentBffOutput.builder()
            .id(source.getId()).build();
    }
}
