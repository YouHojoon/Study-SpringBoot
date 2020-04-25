package ac.kr.smu.rest.domain.projection;

import ac.kr.smu.rest.domain.Board;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="getOnlyTitle", types = {Board.class})
public interface BoardOnlyContationTitle {
    String getTitle();
}
