package jpabook.jpashop.snackDomain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter

public class User {
    @Id
    @Column(name = "user_id")
    private String id;

    private String name;

    private String password;

    private String admin_chk;
}
