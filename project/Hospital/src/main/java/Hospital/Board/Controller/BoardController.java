package Hospital.Board.Controller;

import java.util.List;

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
import Hospital.Board.DTO.BoardReadDTO;
import Hospital.Board.Service.BoardService;
import jakarta.servlet.http.HttpSession;


@Controller
public class BoardController {
	
	
    private BoardService bs;

	public BoardController(BoardService bs) {
        this.bs = bs;
    }
   
    @GetMapping("/BoardPage/List")
    public String BoardList(HttpSession session, Model model) {
        List<BoardDTO> boardDTOList = bs.GetBoardList();
        model.addAttribute("BoardList", boardDTOList);
        String UserId = (String) session.getAttribute("UserId");
        if(UserId != null) {
        	model.addAttribute("UserId", UserId);
        }
        return "/BoardPage/List";
    }
    
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