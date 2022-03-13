package com.study.board.controller;

import com.study.board.entity.Bootboard;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write 주소로 접속 시 보여지는 페이지
    public String boardWriteForm(){
        //어떤 html 파일로 이동할지
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Bootboard bootboard){
        boardService.write(bootboard);

        return "";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){

        model.addAttribute("list",boardService.boardList());

        return "boardList";
    }

    @GetMapping("/board/view")
    public String boardView(){
        return "boardview";
    }
}
