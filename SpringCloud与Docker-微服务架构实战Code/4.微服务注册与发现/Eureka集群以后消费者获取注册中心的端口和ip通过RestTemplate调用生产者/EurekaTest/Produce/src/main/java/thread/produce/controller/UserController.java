package thread.produce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thread.produce.sevice.UserService;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getUserNames")
    public List<String> getUserNames(){
        return userService.getUserNames();
    }

}
