package thread.produce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thread.produce.service.UserService;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getUserNames")
    public List<String> getUserNames(){
        return userService.getUserNames();
    }

    @RequestMapping("/user/{id}")
    public String getUserNameById(@PathVariable int id){
        return userService.getUserNameById(id);
    }
}
