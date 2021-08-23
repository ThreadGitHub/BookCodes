package thread.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import thread.producer.dao.UserRepository;
import thread.producer.domain.UserInfo;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/getUserInfo/{id}")
    @ResponseBody
    public UserInfo getUserInfo(@PathVariable int id){
        Optional<UserInfo> optional = userRepository.findById(id);
        UserInfo userInfo = optional.get();
        return userInfo;
    }

}
