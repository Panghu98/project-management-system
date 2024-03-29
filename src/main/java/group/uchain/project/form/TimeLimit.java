package group.uchain.project.form;

import lombok.Data;

@Data
public class TimeLimit {

    private Long start;

    private Long end;

    @Override
    public String toString() {
        return "TimeLimit{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
