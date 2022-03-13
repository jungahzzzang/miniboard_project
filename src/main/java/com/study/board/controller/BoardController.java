package com.study.board.controller;

import com.study.board.entity.Bootboard;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String boardWritePro(Bootboard bootboard, Model model){
        boardService.write(bootboard);
        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");
        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){

        model.addAttribute("list",boardService.boardList());

        return "boardList";
    }

    @GetMapping("/board/view")  //localhost:8080/board/view?id=1
    public String boardView(Model model, Integer id){
        model.addAttribute("bootboard",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){
        model.addAttribute("bootboard",boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Bootboard bootboard){
        //기존 글을 가져옴
        Bootboard boardTemp = boardService.boardView(id);
        boardTemp.setTitle(bootboard.getTitle());
        boardTemp.setContent(bootboard.getContent());

        //수정한 내용 덮어씌움
        boardService.write(boardTemp);

        return "redirect:/board/list";
    }
}
