package group.uchain.panghu.entity;

import lombok.Data;

/**
 * @author panghu
 * @title: ProjectInfo
 * @projectName panghu
 * @date 19-7-13 下午9:17
 */
@Data
public class ProjectInfo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 类别
     */
    private String category;

    /**
     * 项目说明
     */
    private String instruction;

    /**
     * 级别 省级 国家级 等
     */
    private String level;

    /**
     * 等级
     */
    private String grade;

    /**
     * 项目数量
     */
    private String number;

    /**
     * 类型
     */
    private String variety;

    /**
     * 分数
     */
    private Double score;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 比例划分说明
     */
    private String division;

}
