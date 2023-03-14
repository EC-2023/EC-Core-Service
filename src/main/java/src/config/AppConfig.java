package src.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import src.config.annotation.ApiPrefixController;
import src.config.middleware.GlobalApiLoggerInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private GlobalApiLoggerInterceptor globalApiLoggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalApiLoggerInterceptor);
    }
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api/v1", HandlerTypePredicate.forAnnotation(ApiPrefixController.class));
    }
//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false) {
//            @Override
//            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
//                return true;
//            }
//        };
//        provider.addIncludeFilter(new AnnotationTypeFilter(PrefixMapping.class));
//
//        String pkgName = "src.config.annotation";
//        provider.findCandidateComponents(pkgName).forEach(bean -> {
//            try {
//                String className = bean.getBeanClassName();
//                Class<? extends Annotation> clz = (Class<? extends Annotation>) Class.forName(className);
//
//                String prefix = clz.getDeclaredAnnotation(PrefixMapping.class).value();
//                configurer.addPathPrefix(prefix, HandlerTypePredicate.forAnnotation(clz));
//            } catch (ClassNotFoundException | ClassCastException e) {
//                e.printStackTrace(System.err);
//            }
//        });
//    }
}