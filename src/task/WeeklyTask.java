package task;

import java.time.LocalDateTime;

public class WeeklyTask extends Task {
    public WeeklyTask(String title, String description, TypeDate type, PersonalType personalType, LocalDateTime dateTime) {
        super(title, description, type, personalType, dateTime);
    }


    @Override
    public LocalDateTime setNewDateTime(LocalDateTime time) {
        return time.plusWeeks(1);
    }
}
