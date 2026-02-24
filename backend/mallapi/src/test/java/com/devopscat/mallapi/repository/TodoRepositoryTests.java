package com.devopscat.mallapi.repository;

import com.devopscat.mallapi.domain.QTodo;
import com.devopscat.mallapi.domain.Todo;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
@Transactional // 클래스에 붙이면 모든 메서드가 끝날 때 자동으로 롤백
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JPQLQueryFactory queryFactory;

    @Test
    @Disabled
    public void testInsert() {

        for (int i = 1; i <= 100; i++) {

            Todo todo = Todo.builder()
                    .title("Title..." + i)
                    .dueDate(LocalDate.of(2023, 12, 31))
                    .writer("user00")
                    .build();

            todoRepository.save(todo);
        }

    }

    @Disabled
    @Test
    public void testRead() {
        // Create a Todo first
        Todo todo = Todo.builder()
                .title("Read Test")
                .dueDate(LocalDate.of(2023, 12, 31))
                .writer("user00")
                .build();
        Todo savedTodo = todoRepository.save(todo);
        Long tno = savedTodo.getTno();

        java.util.Optional<Todo> result = todoRepository.findById(tno);

        Todo readTodo = result.orElseThrow();

        log.info(readTodo);
    }

    @Disabled
    @Test
    public void testModify() {
        // Create a Todo first
        Todo todo = Todo.builder()
                .title("Modify Test")
                .dueDate(LocalDate.of(2023, 12, 31))
                .writer("user00")
                .build();
        Todo savedTodo = todoRepository.save(todo);
        Long tno = savedTodo.getTno();

        Optional<Todo> result = todoRepository.findById(tno);

        Todo targetTodo = result.orElseThrow();

        targetTodo.changeTitle("Modified Title...");
        targetTodo.changeComplete(true);
        targetTodo.changeDueDate(LocalDate.of(2024, 10, 10));

        todoRepository.save(targetTodo);
    }

    @Disabled
    @Test
    public void testDelete() {
        // Create a Todo first
        Todo todo = Todo.builder()
                .title("Delete Test")
                .dueDate(LocalDate.of(2023, 12, 31))
                .writer("user00")
                .build();
        Todo savedTodo = todoRepository.save(todo);
        Long tno = savedTodo.getTno();

        todoRepository.deleteById(tno);
    }

//    @Disabled
    @Test
    public void testPaging() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalPages());
        log.info(result.getTotalElements());

//        result.getContent().stream().forEach(todo -> log.info(todo));

    }

    @Disabled
    @Test
    public void testSearch1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
        Page<Todo> result = todoRepository.findByTitleContaining("1", pageable);

        result.stream().forEach(todo -> log.info(todo));

    }

    // QTodo를 이용해서 title로 '11'이라는 글자가 있는 데이터 검색
    @Test
    public void testSearch2() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("tno").descending());

        // JPQLQueryFactory를 이용해서 검색
        QTodo qTodo = QTodo.todo;

        java.util.List<Todo> list = queryFactory.selectFrom(qTodo).where(qTodo.title.contains("11")).fetch();

        log.info(list);

    }

}
