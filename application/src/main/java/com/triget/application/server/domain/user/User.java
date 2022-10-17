package com.triget.application.server.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.triget.application.server.oauth.entity.ProviderType;
import com.triget.application.server.oauth.entity.RoleType;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

    @JsonIgnore
    @Id
    @Field("user_seq")
    private ObjectId userSeq;

    @NotNull
    @Size(max = 64)
    private String userId;

    @NotNull
    @Size(max = 100)
    private String username;

    @JsonIgnore
    @NotNull
    @Size(max = 128)
    private String password;

    @NotNull
    @Size(max = 512)
    private String email;

    @NotNull
    @Size(min = 1, max = 1)
    private String emailVerifiedYn;

    @NotNull
    @Size(max = 512)
    private String profileImageUrl;

    @Field("provider_type")
    //@Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;


    @Field("role_type")
    //@Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @Field("created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Field("modified_at")
    @NotNull
    private LocalDateTime modifiedAt;

    public User(
            String userId,
            @NotNull @Size(max = 100) String username,
            @NotNull @Size(max = 512) String email,
            @NotNull @Size(max = 1) String emailVerifiedYn,
            @NotNull @Size(max = 512) String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType,
            @NotNull LocalDateTime createdAt,
            @NotNull LocalDateTime modifiedAt
    ) {
        this.userId = userId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public String getId() {
        return userSeq.toString();
    }
}
