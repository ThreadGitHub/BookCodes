package thread.produce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import thread.produce.domain.User;
import thread.produce.service.UserService;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getUserNames")
    public List<User> getUserNames(){
        return userService.getUserNames();
    }

    @RequestMapping("/user/{id}")
    public String getUserNameById(@PathVariable int id){
        return userService.getUserNameById(id);
    }

    @GetMapping("/get/user")
    public String getUserNameByUserGET(User user){
        int id = user.getId();
        return userService.getUserNameById(id);
    }

    @PostMapping("/post/user")
    public String getUserNameByUserPOST(@RequestBody User user){
        int id = user.getId();
        return userService.getUserNameById(id);
    }
}
