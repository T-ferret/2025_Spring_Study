package goormton.backend.web1team.global.payload;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

//    Common Error
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,null,"잘못된 요청 데이터입니다."),
    INVALID_REPRESENTATION(HttpStatus.BAD_REQUEST,null,"잘못된 표현 입니다."),
    INVALID_FILE_PATH(HttpStatus.BAD_REQUEST,null,"잘못된 파일 경로 입니다."),
    INVALID_OPTIONAL_ISPRESENT(HttpStatus.BAD_REQUEST,null,"해당 값이 존재하지 않습니다."),
    INVALID_CHECK(HttpStatus.BAD_REQUEST,null,"해당 값이 유효하지 않습니다."),
    INVALID_AUTHENTICATION(HttpStatus.BAD_REQUEST,null,"잘못된 인증입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,null,"서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),

//    User Error
    USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,null,"존재하지 않는 사용자입니다"),
    USER_ALREADY_EXISTS_ERROR(HttpStatus.CONFLICT,null,"는 이미 존재하는 회원입니다"),
    INACTIVE_USER_ERROR(HttpStatus.FORBIDDEN,null,"권한이 없는 사용자입니다"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
