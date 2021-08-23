package thread.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import thread.consumer.domain.UserInfo;

@Controller
public class InfoController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/findUser")
    @ResponseBody
    public UserInfo findUser(){
        //消费者 通过 Rest HTTP 请求消费者的资源 然后获取数据 转化为UserInfo对象
        UserInfo userInfo = restTemplate.getForObject("http://localhost:8081/getUserInfo/" + 2, UserInfo.class);
        System.out.println(userInfo);
        return userInfo;
    }

}
