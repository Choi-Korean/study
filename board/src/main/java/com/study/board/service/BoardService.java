package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
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
public class BoardService {

    @Autowired      // dependency injection래 : 이 annotation 사용하면 알아서 읽어와서 interface에 넣어준대. 나중에 이해하자
    private BoardRepository boardRepository;


    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{        // file 없을시 Exception 처리하는 건가본데?

        // 우리 project 경로 담기(file 담을)
        String projectPath = System.getProperty("user.dir") + "\\board\\src\\main\\resources\\static\\files";
        
        UUID uuid = UUID.randomUUID();  // UUID는 식별자. random으로 식별자 이름 만들어준대

        String fileName = uuid + "_" + file.getOriginalFilename(); // 식별자 + 원래 file 이름이 file명으로
        
        // File 생성. 해당 경로에 name으로 담기는 file
        File saveFile = new File(projectPath, fileName);
        
        // File 저장. 위 throws Exception 안하면 exception 대비하라고 경고떠서 추가했음
        file.transferTo(saveFile);
        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);
        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<Board> boardlist(Pageable pageable){
        return boardRepository.findAll(pageable);       // board라는 class가 담긴 list 그대로 전부 반환
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();      // findById로 받아오면 optional 값으로 받아와서 get() 써줘야 함
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

    // 글 조회수 처리
    public void viewCount(Board board){
        boardRepository.save(board);
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

}
