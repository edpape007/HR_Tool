package com.hrtool.model.request;
import com.hrtool.model.Goal;
import java.util.List;

public class GoalRequest {
    private final String employeeId;
    private final List<Goal> goals;

    public GoalRequest(String employeeId, List<Goal> goals) {
        this.employeeId = employeeId;
        this.goals = goals;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public List<Goal> getGoals() {
        return goals;
    }
}
