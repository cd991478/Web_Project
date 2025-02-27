package Hospital.Board.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Hospital.Board.DTO.BoardDTO;
import Hospital.Board.DTO.BoardEditDTO;
import Hospital.Board.DTO.BoardEditResponseDTO;
import Hospital.Board.DTO.BoardReadDTO;
import Hospital.Board.Entity.Board;
import Hospital.Board.Entity.BoardRepository;
import jakarta.transaction.Transactional;
//글쓰기 Form에서 내용을 입력한 뒤, '글쓰기' 버튼을 누르면 Post 형식으로 요청이 오고, 
//BoardService의 savePost()를 실행한다.
//service 패키지를 만들고, 그 안에 BoardService 클래스 생성
@Service
public class BoardService {
	
    private BoardRepository br;

    public BoardService(BoardRepository br) {
        this.br = br;
    }

    @Transactional
    public Long SavePost(BoardDTO bDTO) {
        return br.save(bDTO.ToEntity()).getId();
    }

    @Transactional
    public List<BoardDTO> GetBoardList() {
        List<Board> boardList = br.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for(Board board : boardList) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .Id(board.getId())
                    .Author(board.getAuthor())
                    .Title(board.getTitle())
                    .Content(board.getContent())
                    .CreatedDate(board.getCreatedDate())
                    .build();
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }
    
    public BoardReadDTO BoardRead(Long Id) {
    	Board board = this.br.findById(Id).orElse(null);
    	return BoardReadDTO.BoardFactory(board);
    }
    
    
    
    public BoardEditResponseDTO BoardEdit(Long Id) {
    	Board board = this.br.findById(Id).orElse(null);
    	return BoardEditResponseDTO.BoardFactory(board);
    }
    
    public void BoardUpdate(BoardEditDTO beDTO) {
    	Board board = this.br.findById(beDTO.getId()).orElse(null);
    	board = beDTO.Fill(board);
    	this.br.save(board);
    }
    
    
    public void BoardDelete(Long Id) {
    	Board board = this.br.findById(Id).orElse(null);
    	if(board != null) {
    		this.br.deleteById(Id);
    	}
    }
}
