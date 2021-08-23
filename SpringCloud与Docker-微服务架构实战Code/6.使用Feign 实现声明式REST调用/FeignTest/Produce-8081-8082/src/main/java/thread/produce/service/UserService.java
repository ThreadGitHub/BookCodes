package thread.produce.service;

import thread.produce.domain.User;

import java.util.List;

public interface UserService {

    public List<User> getUserNames();

    public String getUserNameById(int id);
}
