package jpabook.jpashop.snackDomain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter

public class SnackItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snack_id")
    private Long id;

    private String name;

    private String origFilename;

    private String filename;

    private String filePath;

}
