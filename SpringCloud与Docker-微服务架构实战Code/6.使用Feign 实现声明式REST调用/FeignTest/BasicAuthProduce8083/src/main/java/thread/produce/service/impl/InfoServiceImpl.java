package thread.produce.service.impl;

import org.springframework.stereotype.Service;
import thread.produce.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {

    @Override
    public String getInfo() {
        return "BasicAuthProduce:8083";
    }
}
