package com.tinqinacademy.bff.core.conversion.commentsconversion.addcommentconversion;

import com.tinqinacademy.bff.api.operations.commentsoperations.addcomment.AddCommentBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.addcomment.AddCommentInput;
import org.springframework.stereotype.Component;

@Component
public class AddCommentInputConversion extends BaseConverter<AddCommentBffInput, AddCommentInput> {
    @Override
    protected AddCommentInput convertObject(AddCommentBffInput source) {
        return AddCommentInput.builder()
            .roomId(source.getRoomId())
            .userId(source.getUserId())
            .content(source.getContent())
            .build();
    }
}
