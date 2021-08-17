package thread.produce.sevice.impl;

import org.springframework.stereotype.Service;
import thread.produce.sevice.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<String> getUserNames() {
        List<String> list = new ArrayList<>();
        list.add("小初");
        list.add("小贺");
        list.add("小仓鼠");
        return list;
    }
}
