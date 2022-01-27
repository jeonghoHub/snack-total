package jpabook.jpashop.snackDomain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class voteListDto {
    private String filePath;
    private String name;
    private String userId;
    private String createUser;
    private String itemId;

    public voteListDto(@JsonProperty String filePath, @JsonProperty String name, @JsonProperty String userId,
                       @JsonProperty String createUser, @JsonProperty String itemId) {
        this.filePath = filePath;
        this.name = name;
        this.userId = userId;
        this.createUser = createUser;
        this.itemId = itemId;
    }
}
