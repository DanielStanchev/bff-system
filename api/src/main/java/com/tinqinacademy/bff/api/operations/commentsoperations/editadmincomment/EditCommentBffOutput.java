package com.tinqinacademy.bff.api.operations.commentsoperations.editadmincomment;

import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EditCommentBffOutput implements OperationOutput {
    private String id;
}
