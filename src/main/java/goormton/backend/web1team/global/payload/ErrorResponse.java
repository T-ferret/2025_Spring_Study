package goormton.backend.web1team.global.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorResponse {

    private final boolean check = false;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @JsonProperty("class")
    private final String clazz;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<ValidationError> errors;

    @Builder
    public ErrorResponse (HttpStatus httpStatus, String code, String message, String clazz, BindingResult bindingResult){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.clazz = clazz;
        this.errors = ValidationError.of(bindingResult);
    }

    @Getter
    public static class ValidationError{
        private final String field;
        private final String value;
        private final String message;

        private ValidationError(FieldError fieldError){
            this.field = fieldError.getField();
            this.value = fieldError.getRejectedValue() == null? "" :fieldError.getRejectedValue().toString() ;
            this.message = fieldError.getDefaultMessage();
        }

        public static List<ValidationError> of(final BindingResult bindingResult){
            return bindingResult.getFieldErrors().stream()
                    .map(ValidationError :: new)
                    .toList();
        }
    }
}
