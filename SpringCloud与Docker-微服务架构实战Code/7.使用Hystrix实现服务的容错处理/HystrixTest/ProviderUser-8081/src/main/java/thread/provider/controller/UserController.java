package thread.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thread.provider.service.UserService;

/**
 * 用户接口
 * @author Thread丶
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getUserNameById")
    public String getUserNameById(int id){
        return userService.getUserNameById(id);
    }

}
