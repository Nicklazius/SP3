import util.FileIO;
import util.TextUI;

import java.util.ArrayList;
import java.util.Scanner;


public class StreamingService {
    private TextUI ui = new TextUI();
    private FileIO io = new FileIO();

    private String StreamName;
    private String password;
    private Account currentUser;
    private ArrayList<Account> account;

    public StreamingService(String name) {

        this.StreamName = name;
        this.account = new ArrayList<>();
    }

    public void startSession() {
        ArrayList<String> data = io.readData("data/account.csv");

        ui.displayMessage("Velkommen til " + this.StreamName + "\nStartMenu:");

        String input = ui.promptText("1. Login\n2. Tilmeld dig");
        while (true) {
            switch (input) {
                case "1":
                    if (!data.isEmpty()) {
                        for (String s : data) {
                            String[] values = s.split(";");
                            createAccount(values[0], values[1]);
                        }

                        currentUser = account.getFirst();
                        if (ui.promptBinary("Gemt account fundet, vil du logge ind som " + currentUser.getName() + "?: Y/N")) {
                            ui.displayMessage("Du er nu logget ind som " + currentUser.getName());
                            break;
                        } else {
                            ui.displayMessage("Login blev annulleret.");
                            startSession();
                        }
                        break;
                    }
                    break;
                case "2":
                    registerUser();
                    break;
                default:
                    ui.displayMessage("Skriv et gyldigt tal.");
            }
            break;
        }
    }

    public void runStreamingService() {

        Search search = new Search();
        search.readMovie();
        search.readSeries();
        search.searchMenu();
    }

    public void endSession() {

        ui.displayMessage("Du er nu logget ud.");
    }

    public void registerUser() {

        String accName = ui.promptText("Vælg et brugernavn: ");
        String accPassword = ui.promptText("Vælg et kodeord: ");
        boolean choice = ui.promptBinary("Vil du gemme brugeren? Y/N");
        if (choice) {
            this.createAccount(accName, accPassword);
            ui.displayMessage("Bruger er oprettet og gemt!\nDu er nu logget ind som " + accName + ".\n");
        } else {
            ui.displayMessage("Du er nu logget ind som " + accName + ".\n");
        }

    }

    public void createAccount(String accountName, String password) {
        ArrayList<String> User = new ArrayList<>();
        Account acc = new Account(accountName, password);
        account.add(acc);

        for (Account p : account) {

            String s = p.toString();
            User.add(s);
        }
        io.saveData(User, "data/account.csv", "AccountName; Password");
    }
}
