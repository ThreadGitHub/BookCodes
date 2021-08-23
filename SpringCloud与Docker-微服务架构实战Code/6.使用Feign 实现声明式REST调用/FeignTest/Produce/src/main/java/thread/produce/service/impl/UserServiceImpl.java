package thread.produce.service.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import thread.produce.domain.User;
import thread.produce.service.UserService;
import java.util.List;

@Service
@ConfigurationProperties("users")
public class UserServiceImpl implements UserService {
    private List<User> data;

    public void setData(List<User> data) {
        this.data = data;
    }

    @Override
    public List<User> getUserNames() {
        return this.data;
    }

    @Override
    public String getUserNameById(int id) {
        return data.get(id).getName();
    }

}
