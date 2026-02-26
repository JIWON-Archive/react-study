package com.devopscat.mallapi.service;

import com.devopscat.mallapi.domain.Todo;
import com.devopscat.mallapi.dto.TodoDTO;

import java.util.Optional;

public interface TodoService {
    Long register(TodoDTO todoDTO);
    TodoDTO get(Long tno);

    void modify(TodoDTO todoDTO);

    void remove(Long tno);
}
