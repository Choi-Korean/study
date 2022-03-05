package com.study.board.repository;

import com.study.board.entity.NaverMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NaverMapRepository extends JpaRepository<NaverMap, Integer> {
    // JpaRepository 상속받고, Generic으로 type 지정.
    // < > 사이에 두가지 넣어야 함. 어떤 Entity 넣을건지, Primary key로 지정한 column의 type이 뭔지

    List<NaverMap> findAll();
}