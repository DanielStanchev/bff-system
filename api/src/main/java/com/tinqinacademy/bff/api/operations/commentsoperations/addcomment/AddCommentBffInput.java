package com.tinqinacademy.bff.api.operations.commentsoperations.addcomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class AddCommentBffInput implements OperationInput {

    @JsonIgnore
    @NotBlank(message = "Room ID cannot be blank.")
    private String roomId;

    @NotBlank(message = "User ID cannot be blank.")
    @JsonIgnore
    private String userId;

    @NotNull(message = "Content cannot be null.")
    @Size(min = 2,max = 150,message = "Comment content should be between 2 and 150 symbols.")
    private String content;
}
