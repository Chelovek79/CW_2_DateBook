package task;

import java.time.LocalDateTime;

public class YearlyTask extends Task{
    public YearlyTask(String title, String description, TypeDate type, PersonalType personalType, LocalDateTime dateTime) {
        super(title, description, type, personalType, dateTime);
    }
}
