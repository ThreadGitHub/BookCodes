package thread.producer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thread.producer.domain.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Integer> {

}
