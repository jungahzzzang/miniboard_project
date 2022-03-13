package com.study.board.controller;

import com.study.board.entity.Bootboard;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    public String boardWritePro(Bootboard bootboard, Model model, MultipartFile file) throws Exception{
        boardService.write(bootboard, file);
        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");
        return "message";
    }

    @GetMapping("/board/list")          /*page:default 페이지,size:한 페이지 게시글 수, sort:정렬기준컬럼, direction:정렬순서 */
    public String boardList(Model model,@PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.DESC)
            Pageable pageable, String searchKeyword){

        Page<Bootboard> list = null;

        //기존에는 모든 글을 가져왔으나 이제 검색했을 때와 검색하지 않았을 때를 구분해야 함.
        if(searchKeyword == null) {
            list = boardService.boardList(pageable);
        }else {
            list = boardService.boardSearchList(searchKeyword,pageable);
        }

        int nowPage = list.getPageable().getPageNumber()+1; //pageable이 갖고 있는 페이지는 0부터 시작하기 때문에
        int startPage = Math.max(nowPage -4,1);
        int endPage = Math.min(nowPage +5,list.getTotalPages());

        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

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
    public String boardUpdate(@PathVariable("id") Integer id, Bootboard bootboard, MultipartFile file) throws Exception{
        //기존 글을 가져옴
        Bootboard boardTemp = boardService.boardView(id);
        boardTemp.setTitle(bootboard.getTitle());
        boardTemp.setContent(bootboard.getContent());

        //수정한 내용 덮어씌움
        boardService.write(boardTemp, file);

        return "redirect:/board/list";
    }
}
