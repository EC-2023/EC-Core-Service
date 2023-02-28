package src.config.metadata;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import src.config.annotation.SetMetadata;

@Aspect
@Component
public class SetMetadataAspect {
    @Autowired
    private MetadataService metadataService;

    @Around("@annotation(src.config.annotation.SetMetadata)")
    public Object setMetadata(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get the metadata value from the annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SetMetadata setMetadata = signature.getMethod().getAnnotation(SetMetadata.class);
        String metadataValue = setMetadata.value();
        // Call the method
        Object result = joinPoint.proceed();
        // Set the metadata using the MetadataService
        metadataService.setMetadata(metadataValue);
        // Return the result of the method call
        return result;
    }
}
