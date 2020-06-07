package commands;

public interface Commands {

    int LOGOUT = 0;
    int ADD_NEW_TODO = 1;
    int MY_ALL_LIST = 2;
    int MY_TODO_LIST = 3;
    int MY_IN_PROGRESS_LIST = 4;
    int MY_FINISHED_LIST = 5;
    int CHANGE_TODO_STATUS = 6;
    int DELETE_TODO_BY_ID = 7;

    int EXIT = 0;
    int REGISTER = 1;
    int LOGIN = 2;

    static void printStartingCommands() {
        System.out.println("Input " + EXIT + " for EXIT");
        System.out.println("Input " + REGISTER + " for REGISTER");
        System.out.println("Input " + LOGIN + " for LOGIN");
    }

    static void printMainCommands() {
        System.out.println("Input " + LOGOUT + " for LOGOUT");
        System.out.println("Input " + ADD_NEW_TODO + " for ADD_NEW_TODO");
        System.out.println("Input " + MY_ALL_LIST + " for MY_ALL_LIST");
        System.out.println("Input " + MY_TODO_LIST + " for MY_TODO_LIST");
        System.out.println("Input " + MY_IN_PROGRESS_LIST + " for MY_IN_PROGRESS_LIST");
        System.out.println("Input " + MY_FINISHED_LIST + " for MY_FINISHED_LIST");
        System.out.println("Input " + CHANGE_TODO_STATUS + " for CHANGE_TODO_STATUS");
        System.out.println("Input " + DELETE_TODO_BY_ID + " for DELETE_TODO_BY_ID");

    }

}
