package com.devopscat.mallapi.repository;

import com.devopscat.mallapi.dto.PageRequestDTO;
import com.devopscat.mallapi.dto.PageResponseDTO;
import com.devopscat.mallapi.dto.TodoDTO;
import com.devopscat.mallapi.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class TodoServiceTests {
    @Autowired
    private TodoService todoService;

    @Disabled
    @Test
    public void testRegister() {

        TodoDTO todoDTO = TodoDTO.builder()
                .title("Test todo")
                .dueDate(LocalDate.of(2025,12,31))
                .writer("tester")
                .build();

        Long tno = todoService.register(todoDTO);
        log.info("TNO: " + tno);
    }

    @Disabled
    @Test
    public void testRead() {
        // Long 타입으로 1을 저장
        Long tno = 1L;
        // tno = 1
        TodoDTO todoDTO = todoService.get(tno);
        log.info(todoDTO);
    }

    @Disabled
    @Test
    public void testModify() {
        TodoDTO todoDTO = TodoDTO.builder()
                .tno(1L)
                .title("Test Update Title")
                .complete(true)
                .dueDate(LocalDate.of(2025,12,31))
                .build();

        todoService.modify(todoDTO);
        log.info(todoDTO);
    }

    // list()에 대한 테스트 코드
    @Test
    public void testList() {
        log.info("-----------");
        PageRequestDTO pageResponseDTO = PageRequestDTO.builder().page(11).build();
        PageResponseDTO<TodoDTO> dto = todoService.list(pageResponseDTO);

        log.info(dto);
        log.info(dto.getPageNumList());
    }

}
