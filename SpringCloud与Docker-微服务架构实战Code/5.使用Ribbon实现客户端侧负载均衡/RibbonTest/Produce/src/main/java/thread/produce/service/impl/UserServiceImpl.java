package thread.produce.service.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import thread.produce.service.UserService;
import java.util.List;

@Service
@ConfigurationProperties("users")
public class UserServiceImpl implements UserService {
    private List<String> data;

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public List<String> getUserNames() {
        return this.data;
    }

}
