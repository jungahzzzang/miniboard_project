package com.study.board.service;

import com.study.board.entity.Bootboard;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //글 작성 처리
    public void write(Bootboard bootboard){
        boardRepository.save(bootboard);
    }

    //게시글 리스트 처리
    public List<Bootboard> boardList(){
        return boardRepository.findAll();
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
