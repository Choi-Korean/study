package com.study.board.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity     // Entity는 table을 의미. 이걸 붙이면, java가 아래 Board class가 table을 의미한다고 인식
@Data       // 이게 Lombok이래. 이거 쓰면 get/set 안써도 getTitle같은걸 할수가 있네??? 옷 신기
public class NaverMap {

    @Id     // Primary key를 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Identity : MySQL/MariDB에서 사용. Sequence는 oracle
    private Integer id;

    private String address;

    private Double lat;

    private Double lng;

    private String crimeGubun;

    private String crimeTime;

    private String serious;

    private String insertDate;
}
