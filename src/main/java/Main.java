import commands.Commands;
import manager.TodoManager;
import manager.UserManager;
import model.Todo;
import model.User;

import java.sql.SQLException;
import java.util.Scanner;

public class Main implements Commands {
    public static Scanner scanner = new Scanner(System.in);
    public static UserManager userManager = new UserManager();

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
            User user = new User();
            user.setName(userDataStr[0]);
            user.setSurName(userDataStr[1]);
            user.setEmail(userDataStr[2]);
            user.setPassword(userDataStr[3]);
            userManager.addUser(user);
            System.out.println("User added!");
        } catch (ArrayIndexOutOfBoundsException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loginUser() {
        System.out.println("Input user email and password");
        String loginUser = scanner.nextLine();
        String[] loginUserStr = loginUser.split(",");
        try {
            userManager.getUser(loginUserStr[0], loginUserStr[1]);
            loginSuccess();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loginSuccess() throws SQLException {
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
                case MY_LIST:
                    myList();
                    break;
                case MY_IN_PROGRESS_LIST:
                    TodoManager.myInProgressList();
                    break;
                case MY_FINISHED_LIST:
                    TodoManager.myFinishedList();
                    break;
                case ADD_TODO:
                    addTodo();
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

    private static void changeTodoStatus() {
        try {
            TodoManager.myList();
            System.out.println("Select id fro change status");
            int id = scanner.nextInt();
            TodoManager.changeTodoStatus(id);
            System.out.println("Changed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTodo() {
        System.out.println("Input todo data (name, deadline, status, user_id)");
        String todoData = scanner.nextLine();
        String[] todoDataStr = todoData.split(",");
        try {
            Todo todo = new Todo();
            todo.setName(todoDataStr[0]);
            todo.setDeadLine(todoDataStr[1]);
            todo.setStatus(todoDataStr[2]);
            todo.setUserid(Integer.parseInt(todoDataStr[3]));
            TodoManager.addTodo(todo);
            System.out.println("Todo added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteTodo() throws SQLException {
        System.out.println("Input id todo for delete");
        int id = scanner.nextInt();
        TodoManager.deleteTodoById(id);
    }

    private static void myList() throws SQLException {
        TodoManager.myList();
    }
}
