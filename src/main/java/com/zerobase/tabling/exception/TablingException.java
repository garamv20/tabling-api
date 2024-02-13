package com.zerobase.tabling.exception;
import com.zerobase.tabling.type.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TablingException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public TablingException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
