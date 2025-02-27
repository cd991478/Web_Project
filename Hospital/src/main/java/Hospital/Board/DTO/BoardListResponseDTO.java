package Hospital.Board.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardListResponseDTO {
	private Long Id;
    private String Author;
    private String Title;
    private LocalDateTime CreatedDate;

	public BoardListResponseDTO(Long id, String author,
							    String title, LocalDateTime createddate) {
		this.Id = id;
		this.Author = author;
		this.Title = title;
		this.CreatedDate = createddate;
	}
}
