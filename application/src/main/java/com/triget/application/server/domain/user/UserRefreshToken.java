package com.triget.application.server.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "user_refresh_token")
public class UserRefreshToken {

    @Id
    @JsonIgnore
    @Field("refresh_token_seq")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId refreshTokenSeq;

    @NotNull
    @Size(max = 64)
    @Field("user_id")
    private String userId;

    @NotNull
    @Size(max = 256)
    @Field("refresh_token")
    private String refreshToken;

    public UserRefreshToken(
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max = 256) String refreshToken
    ) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public String getRefreshTokenSeq() {
        return refreshTokenSeq.toString();
    }
}
