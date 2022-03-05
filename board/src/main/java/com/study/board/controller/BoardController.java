package com.study.board.controller;

import com.study.board.common.CommonUtils;
import com.study.board.entity.Board;
import com.study.board.entity.NaverMap;
import com.study.board.service.BoardService;
import com.study.board.service.NaverMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller     // Spring이 Controller라는 거를 인지함
public class BoardController {

    @Autowired
    private BoardService boardService;      // 마찬가지로 DI로 boardService에 주입

    @Autowired
    private NaverMapService naverMapService;

    @GetMapping("/board/write") // http://localhost:8080/board/write 주소로 접속시, 아래의 html 문서 보여준다고 선언
    public String boardWriteForm(){
        return "boardwrite";  // "" 사이에는 어떤 html file(view file)로 보내줄건지 씀
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{
        if(CommonUtils.isEmpty(board.getContent()) || CommonUtils.isEmpty(board.getTitle())) {
            model.addAttribute("message", "글을 채워주세요");
            model.addAttribute("searchUrl", "/board/write");
            return "message";
        }
        else{
            board.setViewCount(0);
            boardService.write(board, file);      // 넘어온 board 객체를 boardService 객체로 저장
            model.addAttribute("message", "글 작성이 완료되었습니다.");
            model.addAttribute("searchUrl", "/board/list");
            return "message";
        }
    }

    @GetMapping("/board/list")
    // model : data를 담아서 우리가 보는 page로 보내기 위한 클래스
    // PageableDefault 설정. page = 0부터, size는 10개, 정렬기준은 id, direction으로 정렬순서
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword){
        Page<Board> list = null;
        List<NaverMap> naverMap = null;
        naverMap = naverMapService.boardlist();

        if(searchKeyword == null){
            list = boardService.boardlist(pageable);

        }
        else{
            list = boardService.boardSearchList(searchKeyword, pageable);

        }

        int nowPage = list.getPageable().getPageNumber() + 1; // 현재 page 불러와서 담아주기. page는 0부터 시작하기에 1 더해주고.
        // int nowPage = pageable.getPageNumber() 로 해도 된대.
        int startPage = Math.max(nowPage - 4, 1); // page가 4보다 작다면 -page가 나오니까 max로 1보다 작지 않게
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);  // list라는 이름으로 boardService의 boardlist를 보낼거다
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("naverMap", naverMap);

        return "boardlist";
    }

    @GetMapping("/board/view")      // localhost:8080/board/view?id=1 하면, 이 1을 받아서 아래에 넣어주는것
    public String boardView(Model model, Integer id, Board board, MultipartFile file) throws Exception{
        model.addAttribute("board", boardService.boardView(id));
        Integer i = boardService.boardView(id).getViewCount() + 1; // 현재 조회수 + 1
        Board boardTemp = boardService.boardView(id);    // 현재 board내용 boardTemp 에 담고
        boardTemp.setViewCount(i);          // 조회수 +1한 거로 조회수 다시 세팅
        boardService.viewCount(boardTemp);    // 그리고 덮어쓰기
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";      // 삭제되면 게시물 상세페이지로 redirect
    }

    @GetMapping("/board/modify/{id}")        // 이번엔 pathVariable로 id 받아보기
    public String boardModify(@PathVariable("id") Integer id, Model model){
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardView(id);       // 기존의 글 boardTemp에 담기
        boardTemp.setTitle(board.getTitle());       // 수정된 내용으로 boardTemp의 title setting
        boardTemp.setContent(board.getContent());
        if(CommonUtils.isEmpty(board.getContent()) || CommonUtils.isEmpty(board.getTitle())) {
            model.addAttribute("message", "글 수정 실패..");
            model.addAttribute("searchUrl", "/board/modify/" + id);
            return "message";
        }
        else{
            System.out.println(boardService);
            boardService.write(boardTemp, file);
            return "redirect:/board/list";
        }
    }
}