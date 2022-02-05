package com.study.board.repository;


import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository     // Repository 지정
public interface BoardRepository extends JpaRepository<Board, Integer> {
    // JpaRepository 상속받고, Generic으로 type 지정.
    // < > 사이에 두가지 넣어야 함. 어떤 Entity 넣을건지, Primary key로 지정한 column의 type이 뭔지

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
}