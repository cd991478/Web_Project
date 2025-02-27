package Hospital.Board.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Hospital.Board.DTO.BoardDTO;
import Hospital.Board.DTO.BoardEditDTO;
import Hospital.Board.DTO.BoardEditResponseDTO;
import Hospital.Board.DTO.BoardListResponseDTO;
import Hospital.Board.DTO.BoardReadDTO;
import Hospital.Board.Entity.Board;
import Hospital.Board.Entity.BoardRepository;
import Hospital.Board.Service.BoardService;
import jakarta.servlet.http.HttpSession;


@Controller
public class BoardController {
	
	@Autowired
	private BoardRepository br;
	
    private BoardService bs;
    

	public BoardController(BoardService bs) {
        this.bs = bs;
    }
	@GetMapping("/BoardPage/List") // 문진표 목록 이동
	public ModelAndView BoardList(@RequestParam(name="page", required=false) Integer page,
	                                    HttpSession session, Model model) {
	    final int PageSize = 5;
	    //List<BoardDTO> boardDTOList = bs.GetBoardList();
        //model.addAttribute("BoardList", boardDTOList);
        String UserId = (String) session.getAttribute("UserId");
        if(UserId != null) {
        	model.addAttribute("UserId", UserId);
        }
	    // 페이지 번호가 null일 경우 0으로 설정
	    if (page == null || page < 0) {
	        page = 0;  // 첫 페이지를 기본값으로 설정
	    }
	    // 목록 가져오기
	    List<BoardListResponseDTO> BoardList = this.bs.BoardListPage(page, PageSize);
	    // 전체 개수 구하기 (findAll()으로 전체 데이터 확인 후 필터링)
	    List<Board> allboard = this.br.findAll();  // 전체 데이터 조회
	    long totalElements = allboard.stream()
	                                 .count();  // 필터링된 목록의 개수
	    // 전체 페이지 수 계산
	    int totalPages = (int) Math.ceil((double) totalElements / PageSize);  // 전체 페이지 수 계산
	    int currentPage = page;  // 현재 페이지 번호
	    if(totalElements == 0) {
	    	BoardList = null;
	    }
	    // 모델에 페이지네이션 정보 추가
	    model.addAttribute("BoardList", BoardList);
	    model.addAttribute("totalPages", totalPages);  // 전체 페이지 수
	    model.addAttribute("totalElements", totalElements);  // 전체 수
	    model.addAttribute("currentPage", currentPage);  // 현재 페이지 번호
	    model.addAttribute("pageSize", PageSize);  // 한 페이지당 개수
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("BoardPage/List");
	    return mav;
	}
	
//    @GetMapping("/BoardPage/List")
//    public String BoardList(HttpSession session, Model model) {
//        List<BoardDTO> boardDTOList = bs.GetBoardList();
//        model.addAttribute("BoardList", boardDTOList);
//        String UserId = (String) session.getAttribute("UserId");
//        if(UserId != null) {
//        	model.addAttribute("UserId", UserId);
//        }
//        return "/BoardPage/List";
//    }
    
    @GetMapping("/BoardPage/Write")
    public String BoardCreate(HttpSession session) {
    	String UserId = (String)session.getAttribute("UserId");
    	if((UserId!=null) && (UserId.equals("admin"))) {
    		return "/BoardPage/Write";
    	}
    	else {
    		return "redirect:/BoardPage/List";
    	}
        
    }

    @PostMapping("/BoardPage/Write")
    public String BoardWrite(BoardDTO boardDto) {
        bs.SavePost(boardDto);
        return "redirect:/BoardPage/List";
    }
    
    @GetMapping("/BoardPage/Contents/{id}")
    public ModelAndView BoardRead(@PathVariable("id") Long id, Model model) {
        BoardReadDTO brDTO = new BoardReadDTO();
        brDTO = this.bs.BoardRead(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("Board",brDTO);
        mav.setViewName("BoardPage/Contents");
        return mav;
    }
    
    @GetMapping("/BoardPage/Edit")
    public ModelAndView BoardEdit(@RequestParam("Id") Long Id, HttpSession session) {
    	String UserId = (String)session.getAttribute("UserId");
    	ModelAndView mav = new ModelAndView();
    	if((UserId!=null) && (UserId.equals("admin"))) {
    		BoardEditResponseDTO berDTO = this.bs.BoardEdit(Id);
        	mav.addObject("BoardEdit",berDTO);
        	mav.setViewName("BoardPage/Edit");
        	return mav;
    	}
    	else {
    		mav.setViewName("BoardPage/List");
    		return mav;
    	}
        
    }
    
    @PostMapping("/BoardPage/Edit")
    public String BoardUpdate(@Validated BoardEditDTO beDTO
    						) {
    	ModelAndView mav = new ModelAndView();
    	this.bs.BoardUpdate(beDTO);
    	return "redirect:/BoardPage/Contents/" + beDTO.getId();
    }
    
    
    @GetMapping("/BoardPage/Delete")
    public String BoardDelete(@RequestParam("Id") Long Id, HttpSession session) {
    	String UserId = (String)session.getAttribute("UserId");
    	if((UserId!=null) && (UserId.equals("admin"))) {
    		this.bs.BoardDelete(Id);
        	return "redirect:/BoardPage/List";
    	}
    	else {
    		return "redirect:/BoardPage/List";
    	}
        
    }
    
    
    
}