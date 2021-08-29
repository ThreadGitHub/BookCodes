package thread.provider.service;

/**
 * 用户服务
 * @author Thread丶
 */
public interface UserService {
    /**
     * 根据用户id 获取 用户名称
     * @param id 用户id
     * @return
     */
    public String getUserNameById(int id);
}
