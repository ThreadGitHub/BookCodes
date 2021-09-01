package thread.client.service.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import thread.client.service.UserService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thread丶
 */
@Service
@ConfigurationProperties(prefix = "data")
public class UserServiceImpl implements UserService {
    /**
     * 通过application配置注入
     */
    List<String> users = new ArrayList<>();

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public String getUserNameById(int id) {
        return users.get(id);
    }
}
