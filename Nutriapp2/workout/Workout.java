package workout;

public class Workout {

    public static final int HIGH = 10;

    public static final int MEDIUM = 7;

    public static final int LOW = 5;

    private int id;

    private int minutes;

    private String intensity;

    private String date;

    private int userId;

    public Workout(int id, int minutes, String intensity, String date, int userId) {
        this.minutes = minutes;
        this.intensity = intensity;
        this.date = date;
        this.userId = userId;

    }

    public Workout() {
    }

    public int getUserId() {
        return this.userId;
    }

    public int getId() {
        return id;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getIntensity() {
        return this.intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getDate() {
        return this.date;
    }

    public int calculateCaloriesBurned() {
        switch (intensity) {
            case "high":
                return HIGH * minutes;
            case "medium":
                return MEDIUM * minutes;
            case "low":
                return LOW * minutes;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", minutes='" + getMinutes() + "'" +
                ", intensity='" + getIntensity() + "'" +
                ", date='" + getDate() + "'" +
                ", userId='" + getUserId() + "'" +
                "}";
    }

}
