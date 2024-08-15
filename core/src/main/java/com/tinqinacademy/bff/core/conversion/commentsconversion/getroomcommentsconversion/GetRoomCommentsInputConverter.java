package com.tinqinacademy.bff.core.conversion.commentsconversion.getroomcommentsconversion;

import com.tinqinacademy.bff.api.operations.commentsoperations.getroomcomments.GetRoomCommentsBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import org.springframework.stereotype.Component;

@Component
public class GetRoomCommentsInputConverter extends BaseConverter<GetRoomCommentsBffInput, GetRoomCommentsInput> {
    @Override
    protected GetRoomCommentsInput convertObject(GetRoomCommentsBffInput source) {
        return GetRoomCommentsInput.builder()
            .roomId(source.getRoomId()).build();
    }
}
