package com.study.board.service;

import com.study.board.entity.NaverMap;
import com.study.board.repository.NaverMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service        // Service로 지정
public class NaverMapService {

    @Autowired      // dependency injection래 : 이 annotation 사용하면 알아서 읽어와서 interface에 넣어준대. 나중에 이해하자
    private NaverMapRepository naverMapRepository;

    // 맵 리스트 처리
    public List<NaverMap> boardlist(){
        return naverMapRepository.findAll();       // board라는 class가 담긴 list 그대로 전부 반환
    }


}
