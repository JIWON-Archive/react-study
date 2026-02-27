package com.devopscat.mallapi.service;

import com.devopscat.mallapi.domain.Todo;
import com.devopscat.mallapi.dto.PageRequestDTO;
import com.devopscat.mallapi.dto.PageResponseDTO;
import com.devopscat.mallapi.dto.TodoDTO;
import com.devopscat.mallapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor    // 생성자 자동 주입
@Log4j2
public class TodoServiceImpl implements TodoService {

    // 자동 주입 대상은 final로
    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    // 등록
    @Override
    public Long register(TodoDTO todoDTO) {
        log.info(".........");
        // DTO를 엔티티 객체로 바꿔준다.
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        Todo saveTodo = todoRepository.save(todo);
        return saveTodo.getTno();
    }

    // 조회
    @Override
    public TodoDTO get(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        TodoDTO dto = modelMapper.map(todo, TodoDTO.class);
        return dto;
    }

    // 수정
    @Override
    public void modify(TodoDTO todoDTO) {
        // Todo 엔티티 조회
        Optional<Todo> result = todoRepository.findById(todoDTO.getTno());
        Todo todo = result.orElseThrow();

        // title, complete, dueDate 변경
        todo.changeTitle(todoDTO.getTitle());
        todo.changeComplete(todoDTO.isComplete());
        todo.changeDueDate(todoDTO.getDueDate());

        // dirty checking
    }

    // 삭제
    @Override
    public void remove(Long tno) {
        log.info("remove.......");

        //todoRepository.deleteById(tno);
    }

    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        // Pageable 생성
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1,    // 1페이지가 0이므로 주의
                pageRequestDTO.getSize(),
                Sort.by("tno").descending());

        // todoRepository 호출
        Page<Todo> result = todoRepository.findAll(pageable);

        List<TodoDTO> dtoList = result.getContent().stream()
            .map(todo -> modelMapper.map(todo, TodoDTO.class))
            .collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        // 결과를 PageResposneDTO로 처리
        PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
        return responseDTO;
    }
}
