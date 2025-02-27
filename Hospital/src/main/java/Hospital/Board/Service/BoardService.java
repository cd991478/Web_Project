package Hospital.Board.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import Hospital.Board.DTO.BoardDTO;
import Hospital.Board.DTO.BoardEditDTO;
import Hospital.Board.DTO.BoardEditResponseDTO;
import Hospital.Board.DTO.BoardListResponseDTO;
import Hospital.Board.DTO.BoardReadDTO;
import Hospital.Board.Entity.Board;
import Hospital.Board.Entity.BoardRepository;
import Hospital.Patient.DTO.PatientInfoListResponseDTO;
import Hospital.Patient.Entity.Patient;
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
    
    public List<BoardListResponseDTO> BoardListPage(Integer page, Integer maxsize) {
	    // 페이지 번호가 null이면 첫 페이지로 기본값 설정
	    if (page == null || page < 0) {
	        page = 0;
	    }
	    // 전체 데이터 조회
	    List<Board> allboard = this.br.findAll();  // 전체 데이터 조회
	    // 날짜를 기준으로 내림차순 정렬
	    allboard.sort((p1, p2) -> p2.getCreatedDate().compareTo(p1.getCreatedDate()));  // 내림차순 정렬
	    // 전체 데이터 개수
	    long totalElements = allboard.size();
	    // 전체 페이지 수 계산
	    int totalPages = (int) Math.ceil((double) totalElements / maxsize);
	    // 페이지네이션: 현재 페이지의 시작 인덱스 계산
	    int startIndex = page * maxsize;
	    int endIndex = Math.min(startIndex + maxsize, allboard.size());
	    // 현재 페이지에 해당하는 데이터만 잘라서 가져오기
	    List<Board> pagedPatients = allboard.subList(startIndex, endIndex);
	    // DTO로 변환하여 반환
	    return pagedPatients.stream()
	                         .map(board -> new BoardListResponseDTO(board.getId(), board.getAuthor(), board.getTitle(), board.getCreatedDate()))
	                         .collect(Collectors.toList());
	}
    
}
