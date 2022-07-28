package com.triget.application.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import  com.triget.application.domain.dummy.Dummy;
import  com.triget.application.domain.dummy.DummyRepository;
import com.triget.application.web.dto.DummySaveDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // DI
@RestController // 데이터 리턴 서버
public class DummyController {
    // DI
    private final DummyRepository dummyRepository;

    @PutMapping("dummy/{id}")
    public void update(@RequestBody DummySaveDto dto, @PathVariable String id) {

        Dummy dummy = dto.toEntity();
        dummy.set_id(id); // save함수는 같은 아이디면 수정한다.

        dummyRepository.save(dummy);
    }

    @DeleteMapping("dummy/{id}")
    public int deleteById(@PathVariable String id) {

        dummyRepository.deleteById(id); // 내부적으로 실행되다가 오류 Exception 발동

        return 1; // 1 : 성공, -1 : 실패
    }

    @GetMapping("/dummy/{id}")
    public Dummy findById(@PathVariable String id) {
        return dummyRepository.findById(id).get();
    }

    @GetMapping("/dummy")
    public List<Dummy> findAll() {
        // 리턴을 JavaObject로 하면 스프링 내부적으로 JSON으로 자동 변환 해준다.
        return dummyRepository.findAll();
    }

    @PostMapping("/dummy")
    public Dummy save(@RequestBody DummySaveDto dto) { // {"title":"제목3","content":"내용3"}
        Dummy dummyEntity = dummyRepository.save(dto.toEntity());

        return dummyEntity; // MessageConverter 발동 -> 자바오브제그를 Json 변환해서 응답함.
    }
}
