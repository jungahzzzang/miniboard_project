package com.study.board.service;

import com.study.board.entity.Bootboard;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //글 작성 처리
    public void write(Bootboard bootboard, MultipartFile file) throws Exception{
        //저장 경로 지정
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
        //랜덤으로 파일 이름 만들어줌
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        //파일을 넣을 껍데기 생성
        File saveFile = new File(projectPath,fileName);
        file.transferTo(saveFile);

        //DB에 값 넣기
        bootboard.setFilename(fileName);    //저장된 파일 이름
        bootboard.setFilepath("/files/"+fileName);  //저장된 파일 경로

        boardRepository.save(bootboard);
    }

    //게시글 리스트 처리
    public Page<Bootboard> boardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Page<Bootboard> boardSearchList(String searchKeyword, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    //특정 게시글 불러오기
    public Bootboard boardView(Integer id){
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}
