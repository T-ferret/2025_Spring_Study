package goormton.backend.web1team.global.error;

import goormton.backend.web1team.global.payload.ErrorCode;
import lombok.Getter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class InvalidParameterException extends DefaultException {

    private Errors errors;

    public InvalidParameterException(Errors errors) {
        super(ErrorCode.INVALID_PARAMETER);
        this.errors = errors;
    }

    public List<FieldError> getFieldErrors() {
        return errors.getFieldErrors();
    }
}
