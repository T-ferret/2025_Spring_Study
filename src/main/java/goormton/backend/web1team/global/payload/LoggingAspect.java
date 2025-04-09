package goormton.backend.web1team.global.payload;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

//    패키지 내 모든 메서드에 적용될 Pointcut을 정의
//    config에 대한 내용은 loggin X
    @Pointcut("within(goormton.backend.web1team..*) && !within(goormton.backend.web1team.global.config..*)")
    public void applicationPackagePointcut() {
    }

//    실제 Advice. 위에서 정의한 Pointcut에 대해 동작
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        디버그 모드일 때 메서드 진입 로그 출력
        if (log.isDebugEnabled()) {
            log.debug("진입: {}.{}() 인수 = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            // 실제 메서드 실행
            Object result = joinPoint.proceed();
            // 디버그 모드일 때 메서드 종료 로그 출력
            if (log.isDebugEnabled()) {
                log.debug("종료: {}.{}() 결과 = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            // IllegalArgumentException 발생 시 로그 출력
            log.error("잘못된 인수: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e; // 예외를 다시 던져서 처리를 위임
        }
    }
}
