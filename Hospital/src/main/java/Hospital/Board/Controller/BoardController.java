package Hospital.Board.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import Hospital.Board.DTO.BoardDTO;
import Hospital.Board.Service.BoardService;


@Controller
public class BoardController {
	
	
    private BoardService bs;

	public BoardController(BoardService bs) {
        this.bs = bs;
    }
   
    @GetMapping("/Board")
    public String BoardList(Model model) {
        List<BoardDTO> boardDTOList = bs.GetBoardList();
        model.addAttribute("postList", boardDTOList);
        return "/Notice/List.html";
    }
    
    @GetMapping("/Write")
    public String BoardCreate() {
        return "/Notice/Write.html";
    }

    @PostMapping("/Write")
    public String BoardWrite(BoardDTO boardDto) {
        bs.SavePost(boardDto);
        return "redirect:/Board";
    }
}