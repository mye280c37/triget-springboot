package com.triget.application.domain.dummy;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyRepository extends MongoRepository<Dummy, String> {
}
