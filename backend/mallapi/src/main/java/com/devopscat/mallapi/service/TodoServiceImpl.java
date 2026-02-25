package com.devopscat.mallapi.service;

import com.devopscat.mallapi.domain.Todo;
import com.devopscat.mallapi.dto.TodoDTO;
import com.devopscat.mallapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor    // 생성자 자동 주입
@Log4j2
public class TodoServiceImpl implements TodoService {

    // 자동 주입 대상은 final로
    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    @Override
    // 등록
    public Long register(TodoDTO todoDTO) {
        log.info(".........");
        // DTO를 엔티티 객체로 바꿔준다.
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        Todo saveTodo = todoRepository.save(todo);
        return saveTodo.getTno();
    }

    @Override
    // 조회
    public TodoDTO get(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        TodoDTO dto = modelMapper.map(todo, TodoDTO.class);
        return dto;
    }
}
