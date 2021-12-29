package jpabook.jpashop.snackDomain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SnackItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snack_id")
    private Long id;

    private String name;
}
