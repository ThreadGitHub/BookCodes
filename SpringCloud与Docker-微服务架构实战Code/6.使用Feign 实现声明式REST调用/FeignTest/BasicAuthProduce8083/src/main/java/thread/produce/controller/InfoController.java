package thread.produce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thread.produce.service.InfoServicee;

@RestController
public class InfoController {
    @Autowired
    private InfoServicee infoServicee;

    @RequestMapping("/getInfo")
    public String getInfo(){
        return infoServicee.getInfo();
    }

}
