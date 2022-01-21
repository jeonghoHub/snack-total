package jpabook.jpashop.snackDomain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@SqlResultSetMapping(
        name="snackRankingMapping",
        classes = @ConstructorResult(
                targetClass = voteRankingDto.class,
                columns = {
                        @ColumnResult(name="count", type = Long.class),
                        @ColumnResult(name="name", type = String.class),
                        @ColumnResult(name="cate_gory", type = String.class),
                })
)


@Entity
@Getter
@Setter
public class SnackTotal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snack_total_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "snack_id")
    private SnackItem snackItem;

    @CreatedDate
    private LocalDateTime createdDate;
}

