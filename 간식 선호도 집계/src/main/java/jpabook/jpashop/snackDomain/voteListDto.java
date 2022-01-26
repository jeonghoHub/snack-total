package jpabook.jpashop.snackDomain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class voteListDto {
    private String filePath;
    private String name;
    private String createUser;

    public voteListDto(@JsonProperty String filePath, @JsonProperty String name,
                       @JsonProperty String createUser) {
        this.filePath = filePath;
        this.name = name;
        this.createUser = createUser;
    }
}
