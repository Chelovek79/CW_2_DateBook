import exception.TaskNotFoundException;
import task.TaskService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public static void startMenu() {

        TaskService taskService = new TaskService();
        Scanner menu = new Scanner(System.in);
        boolean exit;

        do {
            try {
                taskService.upDateTaskList();
            } catch (TaskNotFoundException e) {
                System.out.print(e + " давай создадим новую напоминалку ...\n");
                System.out.println(" ");
                menu.nextLine();
            } finally {
                if (taskService.sizeTaskList() == 0) {
                    taskService.creatingTask(0);
                }
            }
            System.out.println("\n\t     Добро пожаловать в меню:\n\t" +
                    "(1) Список задач по датам.\n\t" +
                    "(2) Задачи на день.\n\t" +
                    "(3) Добавить задачу.\n\t" +
                    "(4) Редактор ('тема' и/или 'описание').\n\t" +
                    "(5) Удалить задачу из списка.\n\t" +
                    "(6) Архив удалённых задач.");
            int numberMenu;
            do {
                System.out.print("_");
                try {
                    numberMenu = menu.nextInt();
                    exit = true;
                } catch (InputMismatchException e) {
                    System.out.println("ошибка ввода ...");
                    numberMenu = 1;
                    menu.next();
                    exit = false;
                }
                if (numberMenu < 1 || numberMenu > 6) {
                    System.out.println("ошибка ввода...");
                    exit = false;
                }
            } while (!exit);
            switch (numberMenu) {
                case 1:
                    taskService.printSorttedList();
                    break;
                case 2:
                    taskService.taskOnNextDay();
                    break;
                case 3:
                    taskService.creatingTask(1);
                    break;
                case 4:
                    taskService.printTaskList();
                    int idChangeTask = 0;
                    do {
                        System.out.print("Укажите номер задачи для редактирования:_");
                        try {
                            idChangeTask = menu.nextInt();
                            exit = false;
                            if (idChangeTask < 1 || idChangeTask > taskService.sizeTaskList()) {
                                throw new TaskNotFoundException("\"Задача с таким номером отсутствует...\"");
                            }
                        } catch (InputMismatchException | TaskNotFoundException e) {
                            System.out.println(e.getMessage());
                            exit = true;
                        }
                        System.out.println(" ");
                        menu.nextLine();
                    } while (exit);

                    taskService.printTaskForRevision(idChangeTask);
                    do {
                        System.out.println("... что делаем дальше:\n" +
                                "\t(1) Тема (изменение).\n" +
                                "\t(2) Описание(изменение).\n" +
                                "\t(3) Выход в основное меню.");
                        int subMenu;
                        do {
                            System.out.print("_");
                            try {
                                subMenu = menu.nextInt();
                                exit = false;
                            } catch (InputMismatchException e) {
                                System.out.println("ошибка ввода...");
                                subMenu = 1;
                                menu.next();
                                exit = true;
                            }
                            if (subMenu < 1 || subMenu > 3) {
                                System.out.println("ошибка ввода...");
                                exit = true;
                            }
                        } while (exit);

                        switch (subMenu) {
                            case 1:
                                taskService.changeTitleTask(idChangeTask);
                                break;
                            case 2:
                                taskService.changeDiscriptionTask(idChangeTask);
                                break;
                            case 3:
                                exit = true;
                                break;
                        }
                    } while (!exit);
                    break;
                case 5:
                    taskService.printTaskList();
                    taskService.removeTask();
                    break;
                case 6:
                    taskService.printArchiveTask();
                    break;
            }
            System.out.println("\nпродолжаем ... \n\t(1) Продолжить работу в основном меню;\n\t(*) Сжечь БЛОКНОТ !!! ");
            int menuExit = 0;
            do {
                System.out.print("_");
                try {
                    menuExit = menu.nextInt();
                    exit = true;
                } catch (InputMismatchException e) {
                    System.out.println("ошибка ввода...");
                    menu.next();
                    exit = false;
                }
            } while (!exit);
            if (menuExit != 1) {
                System.out.println("\tДо новых встреч...");
                taskService.burningBook();
                exit = false;
            }
        } while (exit);
    }
}
