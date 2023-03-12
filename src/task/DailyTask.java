package task;

import java.time.LocalDateTime;

public class DailyTask extends Task {
    public DailyTask(String title, String description, TypeDate type, PersonalType personalType, LocalDateTime dateTime) {
        super(title, description, type, personalType, dateTime);
    }

    @Override
    public LocalDateTime setNewDateTime(LocalDateTime time) {
        return time.plusDays(1);
    }
}
