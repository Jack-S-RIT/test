package goal;

/**
 * context in state pattern
 */
public class Goal {

    private int id;
    private String state;
    private int targetWeight;
    private int userId;

    public Goal(int id, String state, int targetWeight, int userId) {
        this.id = id;
        this.state = state;
        this.targetWeight = targetWeight;
        this.userId = userId;
    }

    public Goal() {
    }

    public static GoalState convertState(String state) {
        switch (state) {
            case "gain-weight":
                return new GainWeight();
            case "lose-weight":
                return new LoseWeight();
            case "maintain-weight":
                return new MaintainWeight();
        }
        return null;
    }

    public void update(int userWeight) {
        GoalState goalState = convertState(state);
        GoalState transitionState = goalState.update(userWeight, targetWeight);
        setState(transitionState.toString());
    }

    public int getId() {
        return this.id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTargetWeight() {
        return this.targetWeight;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getUserId() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "{state='" + getState() + "'" +
                ", targetWeight='" + getTargetWeight() + "'}";
    }

    public static void main(String[] args) {
        Goal goal = new Goal(1, "gain-weight", 150, 1);
        System.out.println(goal);
    }
}
