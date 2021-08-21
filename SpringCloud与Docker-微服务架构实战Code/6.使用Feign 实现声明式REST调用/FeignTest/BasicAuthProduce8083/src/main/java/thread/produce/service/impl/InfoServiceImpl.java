package thread.produce.service.impl;

import org.springframework.stereotype.Service;
import thread.produce.service.InfoServicee;

@Service
public class InfoServiceImpl implements InfoServicee {

    @Override
    public String getInfo() {
        return "BasicAuthProduce:8083";
    }
}
