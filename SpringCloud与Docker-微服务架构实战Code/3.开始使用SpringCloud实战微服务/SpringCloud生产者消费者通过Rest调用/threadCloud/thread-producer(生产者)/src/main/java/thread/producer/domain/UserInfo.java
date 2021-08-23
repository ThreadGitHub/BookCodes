package thread.producer.domain;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "user_info")
@Data
public class UserInfo {
    //定义用户信息表的主键
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_message")
    private String mssage;
}
