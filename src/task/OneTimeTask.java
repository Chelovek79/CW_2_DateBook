package task;

import java.time.LocalDateTime;

public class OneTimeTask extends Task{
    public OneTimeTask(String title, String description, TypeDate type, PersonalType personalType, LocalDateTime dateTime) {
        super(title, description, type, personalType, dateTime);
    }
}
