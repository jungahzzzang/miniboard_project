package com.study.board.repository;

import com.study.board.entity.Bootboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository                                //엔티티, PK로 지정한 컬럼의 데이터 타입
public interface BoardRepository extends JpaRepository<Bootboard, Integer> {
}
