package com.tinqinacademy.bff.core.conversion.commentsconversion.getroomcommentsconversion;

import com.tinqinacademy.bff.api.operations.commentsoperations.getroomcomments.GetRoomCommentOutputBffInfo;
import com.tinqinacademy.bff.api.operations.commentsoperations.getroomcomments.GetRoomCommentsBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import org.springframework.stereotype.Component;

@Component
public class GetRoomCommentsOutputConverter extends BaseConverter<GetRoomCommentsOutput, GetRoomCommentsBffOutput> {
    @Override
    protected GetRoomCommentsBffOutput convertObject(GetRoomCommentsOutput source) {
        return GetRoomCommentsBffOutput.builder()
            .comments(source.getComments()
                          .stream()
                          .map(comment -> GetRoomCommentOutputBffInfo.builder()
                              .id(comment.getId())
                              .userId(comment.getUserId())
                              .content(comment.getContent())
                              .publishDate(comment.getPublishDate())
                              .lastEditedDate(comment.getLastEditedDate())
                              .lastEditedBy(comment.getLastEditedBy())
                              .build())
                          .toList())
            .build();
    }
}
