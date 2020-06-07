import commands.Commands;
import manager.TodoManager;
import manager.UserManager;
import model.Todo;
import model.User;
import status.TodoStatus;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main implements Commands {
    public static Scanner scanner = new Scanner(System.in);
    public static UserManager userManager = new UserManager();
    private static User currentUser = null;
    public static TodoManager todoManager = new TodoManager();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Commands.printStartingCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    private static void registerUser() {
        System.out.println("Input user data (name,surname,email,password)");
        try {
            String userData = scanner.nextLine();
            String[] userDataStr = userData.split(",");
            User userFromStorage = userManager.getByEmail(userDataStr[2]);
            if (userFromStorage == null) {
                User user = new User();
                user.setName(userDataStr[0]);
                user.setSurName(userDataStr[1]);
                user.setEmail(userDataStr[2]);
                user.setPassword(userDataStr[3]);
                if (userManager.register(user)) {
                    System.out.println("User added!");
                } else {
                    System.out.println("Something went wrong!");
                }
            } else {
                System.out.println("User already exists!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data!");
        }
    }

    private static void loginUser() {
        System.out.println("Input user email and password");
        try {
            String loginUser = scanner.nextLine();
            String[] loginUserStr = loginUser.split(",");
            User user = userManager.getByEmailAndPassword(loginUserStr[0], loginUserStr[1]);
            if (user != null) {
                currentUser = user;
                loginSuccess();
            } else {
                System.out.println("Wrong email or password!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data!");
        }
    }

    private static void loginSuccess() {
        System.out.println("Welcome " + currentUser.getName() + "!");
        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_NEW_TODO:
                    addTodo();
                    break;
                case MY_ALL_LIST:
                    printTodos(todoManager.getAllTodosByUser(currentUser.getId()));
                    break;
                case MY_TODO_LIST:
                    printTodos(todoManager.getAllTodosBtUserAndStatus(currentUser.getId(), TodoStatus.TODO));
                    break;
                case MY_IN_PROGRESS_LIST:
                    printTodos(todoManager.getAllTodosBtUserAndStatus(currentUser.getId(), TodoStatus.IN_PROGRESS));
                    break;
                case MY_FINISHED_LIST:
                    printTodos(todoManager.getAllTodosBtUserAndStatus(currentUser.getId(), TodoStatus.FINISHED));
                    break;
                case CHANGE_TODO_STATUS:
                    changeTodoStatus();
                    break;
                case DELETE_TODO_BY_ID:
                    deleteTodo();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    private static void printTodos(List<Todo> allTodosByUser) {
        for (Todo todo : allTodosByUser) {
            System.out.println(todo);
        }
    }


    private static void changeTodoStatus() {
        System.out.println("Please choose TODO from list:");
        List<Todo> list = todoManager.getAllTodosByUser(currentUser.getId());
        for (Todo todo : list) {
            System.out.println(todo);
        }
        long id = Long.parseLong(scanner.nextLine());
        Todo byId = todoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            System.out.println("Please choose Status");
            System.out.println(Arrays.toString(TodoStatus.values()));
            TodoStatus status = TodoStatus.valueOf(scanner.nextLine());
            if (todoManager.update(id,status)) {
                System.out.println("Status was changed!");
            } else {
                System.out.println("Something went wrong");
            }
        } else {
            System.out.println("Wrong id");
        }
    }

    private static void addTodo() {
        System.out.println("Input todo data (title, deadline(yyyy-MM-dd HH:mm:ss))");
        String todoData = scanner.nextLine();
        String[] split = todoData.split(",");
        Todo todo = new Todo();
        try {
            String title = split[0];
            todo.setTitle(title);
            try {
                if (split[1] != null) {
                    todo.setDeadLine(sdf.parse(split[1]));
                }
            } catch (IndexOutOfBoundsException e) {
            } catch (ParseException e) {
                System.out.println("Please input date by this format: yyyy-MM-dd HH:mm:ss");
            }
            todo.setStatus(TodoStatus.TODO);
            todo.setUser(currentUser);
            if (todoManager.create(todo)) {
                System.out.println("Todo was added!");
            } else {
                System.out.println("Something went wrong!");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong data");
        }
    }
    private static void deleteTodo() {
        System.out.println("Please choose TODO from list:");
        List<Todo> list = todoManager.getAllTodosByUser(currentUser.getId());
        for (Todo todo : list) {
            System.out.println(todo);
        }
        long id = Long.parseLong(scanner.nextLine());
        Todo byId = todoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            todoManager.delete(id);
        } else {
            System.out.println("Wrong id");
        }
    }
}
