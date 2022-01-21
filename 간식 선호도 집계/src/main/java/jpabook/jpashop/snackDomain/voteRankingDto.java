package jpabook.jpashop.snackDomain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class voteRankingDto {
    private Long count;
    private String name;
    private String cate_gory;

    public voteRankingDto(@JsonProperty Long count,@JsonProperty String name,
                          @JsonProperty String cate_gory) {
        this.count = count;
        this.name = name;
        this.cate_gory = cate_gory;
    }
}
