package org.scoula.board.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                    // getter, setter, toString 자동 생성
@NoArgsConstructor       // 기본 생성자 생성
@AllArgsConstructor      // 모든 필드 생성자 생성
@Builder                 // 빌더 패턴 적용
// BoardVO - 데이터베이스 테이블과 1:1 매칭
public class BoardVO {
    private Long no;           // tbl_board.no
    private String title;      // tbl_board.title
    private String content;    // tbl_board.content
    private String writer;     // tbl_board.writer
    private Date regDate;      // tbl_board.reg_date
    private Date updateDate;   // tbl_board.update_date
}