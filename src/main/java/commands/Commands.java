package commands;

public interface Commands {

    int LOGOUT = 0;
    int MY_LIST = 1;
    int MY_IN_PROGRESS_LIST = 2;
    int MY_FINISHED_LIST = 3;
    int ADD_TODO = 4;
    int CHANGE_TODO_STATUS = 5;
    int DELETE_TODO_BY_ID = 6;

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
        System.out.println("Input " + MY_LIST + " for MY_LIST");
        System.out.println("Input " + MY_IN_PROGRESS_LIST + " for MY_IN_PROGRESS_LIST");
        System.out.println("Input " + MY_FINISHED_LIST + " for MY_FINISHED_LIST");
        System.out.println("Input " + ADD_TODO + " for ADD_TODO");
        System.out.println("Input " + CHANGE_TODO_STATUS + " for CHANGE_TODO_STATUS");
        System.out.println("Input " + DELETE_TODO_BY_ID + " for DELETE_TODO_BY_ID");

    }

}
