package com.olxseller.olx.helper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  @Around("execution(* com.olxseller.olx..*(..))")
  public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().toShortString();
    logger.info("➡️ Entering: {}", methodName);
    try {
      Object result = joinPoint.proceed();
      logger.info("✅ Exiting: {}", methodName);
      return result;
    } catch (Exception e) {
      logger.error("❌ Exception in {}: {}", methodName, e.getMessage(), e);
      throw e;
    }
  }
}
