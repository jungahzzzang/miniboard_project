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

    public void write(Bootboard bootboard){
        boardRepository.save(bootboard);
    }

    public List<Bootboard> boardList(){
        return boardRepository.findAll();
    }
}
