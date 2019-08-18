package group.uchain.project.config;


import group.uchain.project.interceptor.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
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

}
