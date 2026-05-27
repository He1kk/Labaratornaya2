package laba;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfilingAspect {

    @Around("execution(* laba.CarServiceMessageProvider.getMessage(..))")
    public Object profileCsvParsing(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();

        Object result = joinPoint.proceed();

        long end = System.nanoTime();

        long executionTime = (end - start) / 1000;

        System.out.println("Parsing execution time CSV file: " + executionTime + " ms");

        return result;
    }
}