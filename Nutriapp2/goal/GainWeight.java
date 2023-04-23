package goal;

/**
 * concrete state
 */
public class GainWeight implements GoalState {

    public GainWeight() {
    }

    @Override
    public String toString() {
        return "gain-weight";
    }

    @Override
    public void setGoal(GoalState goalState) {
        goalState.setGoal(this);
    }

    @Override
    public GoalState update(int weight, int targetWeight) {
        int difference = weight - targetWeight;

        if (difference < -5) {
            return new GainWeight();
        } else if (difference > -5 && difference < 5) {
            return new MaintainWeight();
        } else {
            return new LoseWeight();
        }
    }

}
