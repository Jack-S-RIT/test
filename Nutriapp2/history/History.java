package history;

import java.util.ArrayList;

public class History {

    private int id;

    private int weight;

    private int caloriesConsumed;

    private int caloryTarget;

    private ArrayList<Integer> mealIds;

    private ArrayList<Integer> workoutIds;

    private String date;

    private int userId;

    public History(int id, int weight, int caloriesConsumed, int caloryTarget, ArrayList<Integer> mealIds,
            ArrayList<Integer> workoutIds, String date, int userId) {
        this.id = id;
        this.weight = weight;
        this.caloriesConsumed = caloriesConsumed;
        this.caloryTarget = caloryTarget;
        this.mealIds = mealIds;
        this.workoutIds = workoutIds;
        this.date = date;
        this.userId = userId;
    }

    public History() {
    }

    public int getId() {
        return this.id;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getCaloriesConsumed() {
        return this.caloriesConsumed;
    }

    public int getCaloryTarget() {
        return this.caloryTarget;
    }

    public ArrayList<Integer> getMealIds() {
        return this.mealIds;
    }

    public ArrayList<Integer> getWorkoutIds() {
        return this.workoutIds;
    }

    public String getDate() {
        return this.date;
    }

    public int getUserId() {
        return this.userId;
    }

}