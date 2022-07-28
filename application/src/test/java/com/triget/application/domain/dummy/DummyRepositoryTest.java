package com.triget.application.domain.dummy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DummyRepositoryTest {

    @Autowired
    DummyRepository dummyRepository;

    @Test
    public void print_dummy() {
        List<Dummy> result = dummyRepository.findAll();
        for (Dummy dummy : result){
            System.out.print(dummy.getX());
        }
    }
}
