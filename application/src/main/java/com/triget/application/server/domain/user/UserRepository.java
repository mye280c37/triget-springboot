package com.triget.application.server.domain.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByUserId(String userId);
    User findByEmail(String email);
}
