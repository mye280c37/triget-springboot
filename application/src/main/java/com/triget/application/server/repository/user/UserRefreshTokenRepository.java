package com.triget.application.server.repository.user;

import com.triget.application.server.domain.user.UserRefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRefreshTokenRepository extends MongoRepository<UserRefreshToken, Long> {
    UserRefreshToken findByUserId(String userId);
    UserRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);
}
