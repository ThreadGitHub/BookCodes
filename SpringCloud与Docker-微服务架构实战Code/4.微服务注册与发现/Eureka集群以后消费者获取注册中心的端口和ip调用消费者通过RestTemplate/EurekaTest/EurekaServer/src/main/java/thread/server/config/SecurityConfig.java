package thread.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置拦截所有的请求 用 httpBasic方式验证
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
        http.csrf().disable();
    }
}
