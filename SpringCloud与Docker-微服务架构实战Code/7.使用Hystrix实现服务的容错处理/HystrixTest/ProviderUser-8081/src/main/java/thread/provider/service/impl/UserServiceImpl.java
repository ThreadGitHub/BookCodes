package thread.provider.service.impl;

import org.springframework.stereotype.Service;
import thread.provider.service.UserService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thread丶
 */
@Service
public class UserServiceImpl implements UserService {
    private List<String> list = new ArrayList<>();

    {
        list.add("小明");
        list.add("小花");
        list.add("小刘");
        list.add("小张");
    }

    @Override
    public String getUserNameById(int id) {
        String username = list.get(id);
        return username;
    }
}
