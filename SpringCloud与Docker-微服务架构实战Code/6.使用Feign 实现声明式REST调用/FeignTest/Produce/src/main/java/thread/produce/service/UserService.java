package thread.produce.service;

import java.util.List;

public interface UserService {

    public List<String> getUserNames();

    public String getUserNameById(int id);
}
