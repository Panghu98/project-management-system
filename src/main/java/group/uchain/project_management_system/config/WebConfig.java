package group.uchain.project_management_system.config;


import group.uchain.project_management_system.interceptor.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author dgh
 * @date 19-1-19 下午8:11
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private AuthorityInterceptor authorityInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {

    }

    /**
     * 在这个方法当中配置拦截器需要拦截的页面
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorityInterceptor).addPathPatterns("/**")
                //设置拦截器拦截的位置
                .excludePathPatterns("");
    }


    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }


}
