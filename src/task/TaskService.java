package task;

import exception.IncorrectArgumentException;
import exception.TaskNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    Scanner user = new Scanner(System.in);
    List<Task> archiveTask = new ArrayList<>();
    List<Task> taskList = new ArrayList<>(Arrays.asList(
            new OneTimeTask("Кино", "Купить билеты на завтра", TypeDate.ONETIME,
                    PersonalType.PERSONAL, LocalDateTime.of(2023, 2, 24, 12, 0)),
            new YearlyTask("Ольга", "Поздравить с днём рождения", TypeDate.YEARLY,
                    PersonalType.PERSONAL, LocalDateTime.of(2023, 2, 17, 12, 0)),
            new MonthlyTask("Финансы", "Оплатить кредит", TypeDate.MONTHLY,
                    PersonalType.PERSONAL, LocalDateTime.of(2023, 2, 24, 9, 0)),
            new OneTimeTask("Учёба", "Постраться сдать курсовую работу", TypeDate.ONETIME,
                    PersonalType.WORK, LocalDateTime.of(2023, 3, 9, 23, 59)),
            new WeeklyTask("Дежурство", "завести будильник - не проспать ...", TypeDate.WEEKLY,
                    PersonalType.WORK, LocalDateTime.of(2023, 3, 3, 21, 0)),
            new YearlyTask("Праздник", "Завтра Новый Год ;))). Собраться семьёй", TypeDate.YEARLY,
                    PersonalType.PERSONAL, LocalDateTime.of(2023, 12, 30, 23, 59)),
            new DailyTask("Занятия", "Учиться, учиться и т.д. (Ленин В.И.). 2 часа - не меньше", TypeDate.DAILY,
                    PersonalType.PERSONAL, LocalDateTime.of(2023, 3, 1, 19, 30)),
            new OneTimeTask("Отпуск", "... всё будет хорошою. Взять паспорт", TypeDate.ONETIME,
                    PersonalType.PERSONAL, LocalDateTime.of(2023, 5, 29, 0, 1))
    ));

    public Integer sizeTaskList() {
        return taskList.size();
    }

    public void printTaskList() {
        for (Task task : taskList) {
            System.out.println(task.getId() + ". " + task);
        }
    }

    public void printSorttedList() {
        Queue<Task> taskSortDateList = taskList.stream()
                .sorted(Comparator.comparing(Task::getDateTime))
                .collect(Collectors.toCollection(LinkedList::new));

        LocalDateTime dateGroup = taskSortDateList.element().getDateTime();
        int k = taskSortDateList.size();
        System.out.println("\t\t\t" + dateGroup.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        for (int i = 0; i < k; i++) {
            if (taskSortDateList.element().getDateTime().getDayOfMonth() == dateGroup.getDayOfMonth()) {
                System.out.println(taskSortDateList.poll());
            } else {
                System.out.println("\n\t\t\t" + (dateGroup =
                        taskSortDateList.element().getDateTime()).format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
                System.out.println(taskSortDateList.poll());
            }
        }
    }

    public void printTaskForRevision(int idTask) {
        System.out.println(taskList.get(idTask - 1));
        System.out.println(" ");
    }

    public void printArchiveTask() {
        archiveTask
                .forEach(System.out::println);
    }

    public void upDateTaskList() throws TaskNotFoundException {
        List<Integer> idList = new ArrayList<>();
        if (taskList.isEmpty()) {
            throw new TaskNotFoundException("выкинь блокнот - он пуст. Или....");
        }
        for (Task task : taskList) {
                if (task.getType().equals(TypeDate.ONETIME) && task.appearsIn(task.getDateTime())) {
                idList.add(task.getId());
            } else {
                do {
                    if (task.appearsIn(task.getDateTime())) {
                        task.setDateTime(task.setNewDateTime(task.getDateTime()));
                    }
                } while (task.appearsIn(task.getDateTime()));
            }
        }
        for (int i = 0; i < idList.size(); i++) {
            deletingTask(idList.get(i) - i);
        }
        idList.clear();
    }

    public void taskOnNextDay() {
        System.out.println("\n\t" + "\tЗадачи на " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) + ":");
        taskList.stream()
                .filter(x -> x.getDateTime().getDayOfMonth() == LocalDateTime.now().getDayOfMonth())
                .filter(x -> x.getDateTime().compareTo(LocalDateTime.now()) > 0)
                .sorted(Comparator.comparingInt((Task o) -> o.getDateTime().getHour()))
                .peek(x -> System.out.println("< " + x.getTitle() + " >. '" + x.getDescription() + ". Напомнить в " +
                        x.getDateTime().getHour() + ":" + x.getDateTime().getMinute() + "."))
                .collect(Collectors.toList());
    }

    public void removeTask() {
        boolean nextLevel;
        int id = 0;
        do {
            try {
                System.out.print("\nВыберите номер задачи на удаление:_");
                id = user.nextInt();
                nextLevel = true;
                if (id < 1 || id > taskList.size()) {
                    throw new TaskNotFoundException("Задача с таким номером отсутствует...");
                }
            } catch (InputMismatchException | TaskNotFoundException e) {
                System.out.println(e.getMessage());
                nextLevel = false;
            }
            System.out.print(" ");
            user.nextLine();                // для исправления бага ...
        } while (!nextLevel);
        deletingTask(id);
        System.out.println("... перемещение в архив выполнено успешно.");
    }

    public void deletingTask(int id) {
        taskList.stream()
                .filter(x -> x.getId() == id)
                .peek(x -> archiveTask.add(x))
                .count();
        taskList.removeIf(x -> x.getId() == id);
        setNewId(1);
    }

    public void setNewId(int newOrCorrectId) {
        int newId;
        switch (newOrCorrectId) {
            case 0: // id для новой задачи;
                newId = taskList.size();
                taskList.get(taskList.size() - 1).setId(newId);
                break;
            case 1: // присвоение новых id в порядке возрастания;
                newId = 0;
                for (Task task : taskList) {
                    task.setId(++newId);
                }
                break;
        }
    }

    public void changeTitleTask(int idChangeTask) {
        System.out.print("новая <Тема> :' ");
        String newTitle = user.nextLine();
        taskList.get(idChangeTask - 1).setTitle(newTitle);
        System.out.println(taskList.get(idChangeTask - 1) + "\n");
    }

    public void changeDiscriptionTask(int idChangeTask) {
        System.out.print("новoe <Описание> :' ");
        String newDescription = user.nextLine();
        taskList.get(idChangeTask - 1).setDescription(newDescription);
        System.out.println(taskList.get(idChangeTask - 1) + "\n");
    }

    public void creatingTask(int bag) {

        if (bag == 0) {
            System.out.print("нажми клавишу \"Enter\" ");
            user.nextLine();
        }

        System.out.print("Введите заголовок:_");
        String title = user.nextLine();

        System.out.print("Заполните описание заметки:_");
        String description = user.nextLine();

        System.out.println("Укажите переодичность заметки из списка:");
        for (TypeDate type : TypeDate.values()) {
            System.out.println("\t" + type.ordinal() + " : " + type.getTitleType());
        }
        boolean menuTypeDatePersonal;
        int numberTypeDate;
        TypeDate typeDate = null;
        do {
            System.out.print("_");
            try {
                numberTypeDate = user.nextInt();
                typeDate = gettypeDate(numberTypeDate);
                menuTypeDatePersonal = true;
            } catch (IncorrectArgumentException | InputMismatchException e) {
                System.out.print(e.getMessage());
                menuTypeDatePersonal = false;
            }
            System.out.println(" ");
            user.nextLine();
        } while (!menuTypeDatePersonal);

        System.out.println("Укажите тип заметки:");
        for (PersonalType type : PersonalType.values()) {
            System.out.println("\t" + type.ordinal() + " : " + type.getPersonalType());
        }
        int numberPersonalType;
        PersonalType personalType = null;
        do {
            System.out.print("_");
            try {
                numberPersonalType = user.nextInt();
                personalType = getPersonalType(numberPersonalType);
                menuTypeDatePersonal = true;
            } catch (IncorrectArgumentException | InputMismatchException e) {
                System.out.print(e.getMessage());
                menuTypeDatePersonal = false;
            }
            System.out.println(" ");
            user.nextLine();
        } while (!menuTypeDatePersonal);

        System.out.println("Задайте дату и время данного события в формате: \"yyyy MM dd HH mm\" (соответственно -> год месяц день час минуты)");
        boolean dateSet = true;
        do {
            try {
                System.out.print("_");
                String taskDateTime = user.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH mm");
                LocalDateTime dateOfExecutionNewTask = LocalDateTime.parse(taskDateTime, formatter);
                taskList.add(addTaskDependingTypeDate(title, description, typeDate, personalType, dateOfExecutionNewTask));

                dateSet = false;
            } catch (DateTimeParseException e) {
                System.out.println("неформат ...");
            }
        } while (dateSet);
        setNewId(0);
    }

    public TypeDate gettypeDate(int numberTypeDate) throws IncorrectArgumentException {
        switch (numberTypeDate) {
            case 0:
                return TypeDate.ONETIME;
            case 1:
                return TypeDate.DAILY;
            case 2:
                return TypeDate.WEEKLY;
            case 3:
                return TypeDate.MONTHLY;
            case 4:
                return TypeDate.YEARLY;
            default:
                throw new IncorrectArgumentException("нет такого значения, соответствующего списку \"ПЕРЕОДИЧНОСТЬ\"...");
        }
    }

    public PersonalType getPersonalType(int numberPersonalType) throws IncorrectArgumentException {
        switch (numberPersonalType) {
            case 0:
                return PersonalType.PERSONAL;
            case 1:
                return PersonalType.WORK;
            default:
                throw new IncorrectArgumentException("нет такого пункта, соответствующего списку \"ТИП\"... ");
        }
    }

    public Task addTaskDependingTypeDate(String title,
                                         String description,
                                         TypeDate typeDate,
                                         PersonalType personalType,
                                         LocalDateTime dateOfExecutionNewTask) {
        switch (typeDate) {
            case ONETIME:
                return new OneTimeTask(title, description, typeDate, personalType, dateOfExecutionNewTask);
            case DAILY:
                return new DailyTask(title, description, typeDate, personalType, dateOfExecutionNewTask);
            case WEEKLY:
                return new WeeklyTask(title, description, typeDate, personalType, dateOfExecutionNewTask);
            case MONTHLY:
                return new MonthlyTask(title, description, typeDate, personalType, dateOfExecutionNewTask);
            case YEARLY:
                return new YearlyTask(title, description, typeDate, personalType, dateOfExecutionNewTask);
        }
        return null;
    }

    public void burningBook() {
        taskList.clear();
        archiveTask.clear();
        System.err.println("\n\t\t\t\t\t\tживи в моменте...");
    }
}
