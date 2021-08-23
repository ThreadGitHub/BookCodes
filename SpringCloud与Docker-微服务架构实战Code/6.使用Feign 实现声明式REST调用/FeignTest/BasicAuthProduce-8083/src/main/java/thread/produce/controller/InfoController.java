package thread.produce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thread.produce.service.InfoService;
import java.util.Collection;

@RestController
public class InfoController {
    @Autowired
    private InfoService infoService;

    private Logger logger = LoggerFactory.getLogger(InfoController.class);

    @RequestMapping("/getInfo")
    public String getInfo(){
        return infoService.getInfo();
    }

    //获取登录用户
    @RequestMapping("/loginName")
    public String getLoginName(){
        String username = "";
        String password = "";
        String role = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails)principal;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            for(GrantedAuthority grantedAuthority : authorities){
                username = userDetails.getUsername();
                password = userDetails.getPassword();
                role = grantedAuthority.getAuthority();
                logger.info("用户名：{}\t密码：{}\t角色：{}", username, password, role);
            }
        }
        return "<h1>当前用户</h1><br/>用户名："+ username +"\t密码："+ password +"\t角色：" + role;
    }

}
