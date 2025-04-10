import util.FileIO;
import util.TextUI;

import java.util.ArrayList;


public class StreamingService {
    private TextUI ui = new TextUI();
    private FileIO io = new FileIO();

    private String streamName;
    private Account currentUser;
    private ArrayList<Account> account;

    public StreamingService(String name) {

        this.streamName = name;
        this.account = new ArrayList<>();
    }

    public void startSession() {
        ArrayList<String> data = io.readData("data/account.csv");

        ui.displayMessage("Velkommen til " + this.streamName + "\nStartMenu:");

        String input;
        while (true) {
            input = ui.promptText("1. Login\n2. Tilmeld dig");
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
                            runStreamingService();
                            return;
                        } else {
                            ui.displayMessage("Login blev annulleret.");
                            startSession();
                            return;
                        }
                    } else {
                        ui.displayMessage("Ingen gemte brugere på enheden.\nRegistrer dig nu.");
                        registerUser();
                        return;
                    }
                case "2":
                    registerUser();
                    break;
                default:
                    ui.displayMessage("Skriv et gyldigt tal.");
                    break;
            }
        }
    }

    public void runStreamingService() {

        Search search = new Search();
        search.readMovie();
        search.readSeries();
        search.searchMenu();
    }

    public void registerUser() {

        String accName;
        while (true) {
            accName = ui.promptText("Vælg et brugernavn:");

            if (accName == null || accName.trim().isEmpty()) {
                ui.displayMessage("Du kan ikke oprette et brugernavn som er tomt");
            } else {
                break;
            }
        }
        String accPassword;
        while (true) {
            accPassword = ui.promptText("Vælg et password:\nDet skal minimum være 8 tegn.");

            if (accPassword == null || accPassword.trim().isEmpty() || accPassword.length() < 8) {
                ui.displayMessage("Du kan ikke oprette et password som er tomt og det skal være på minimum 8 tegn.");
            } else {
                break;
            }
        }
        boolean choice = ui.promptBinary("Vil du gemme brugeren? Y/N");
        if (choice) {
            this.createAccount(accName, accPassword);
            ui.displayMessage("Bruger er oprettet og gemt!\nDu er nu logget ind som " + accName + ".\n");
            runStreamingService();
        } else {
            ui.displayMessage("Du er nu logget ind som " + accName + ".\n");
            runStreamingService();
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
