package jpabook.jpashop.snackDomain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SnackTotal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snack_total_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "snack_id")
    private SnackItem snackItem;

    @CreatedDate
    private LocalDateTime createdDate;
}
