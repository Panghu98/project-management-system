package group.uchain.project.DTO;

import lombok.Data;

/**
 * @author panghu
 */
@Data
public class OverdueProjectInfo {

    private String projectId;

    private Long userId;

    private Double score;


}
