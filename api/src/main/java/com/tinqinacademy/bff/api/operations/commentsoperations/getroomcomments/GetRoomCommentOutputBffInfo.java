package com.tinqinacademy.bff.api.operations.commentsoperations.getroomcomments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GetRoomCommentOutputBffInfo {

    private String id;
    private String userId;
    private String content;
    private LocalDate publishDate;
    private LocalDate lastEditedDate;
    private String lastEditedBy;

}
