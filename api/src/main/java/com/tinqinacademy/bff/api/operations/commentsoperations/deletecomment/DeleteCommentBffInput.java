package com.tinqinacademy.bff.api.operations.commentsoperations.deletecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
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
public class DeleteCommentBffInput implements OperationInput {

    @JsonIgnore
    @NotBlank(message = "Comment ID cannot be blank.")
    private String commentId;

}
