package task;

import java.time.LocalDateTime;

public class MonthlyTask extends Task {
    public MonthlyTask(String title, String description, TypeDate type, PersonalType personalType, LocalDateTime dateTime) {
        super(title, description, type, personalType, dateTime);
    }

    @Override
    public LocalDateTime setNewDateTime(LocalDateTime time) {
        return time.plusMonths(1);
    }
}
