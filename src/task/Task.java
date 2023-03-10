package task;

import exception.IncorrectArgumentException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Task {

    private String title;                   // Заголовок;
    private String description;             // Описание;
    private final TypeDate type;                  // Тип заметки;
    private final PersonalType personalType;      // Личная или рабочая заметка;
    private LocalDateTime dateTime;         // Дата и время исполнения;
    private int id;
    public static int counter = 0;

    public Task(String title,
                String description,
                TypeDate type,
                PersonalType personalType,
                LocalDateTime dateTime) {

        this.title = title;
        this.description = description;
        this.type = type;
        this.personalType = personalType;
        this.dateTime = dateTime;
        this.id = ++counter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeDate getType() {
        return type;
    }

    public PersonalType getPersonalType() {
        return personalType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) throws DateTimeParseException {

        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && title.equals(task.title) && description.equals(task.description) && type == task.type
                && personalType == task.personalType && dateTime.equals(task.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, type, personalType, dateTime, id);
    }

    @Override
    public String toString() {
        return "Задача ('" + personalType + "') - < " + title + " >. '" + description + "'.\n\tТип: " + type +
                ". Ближайшая дата : " + dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy в HH:mm")) + ".";
    }
}
