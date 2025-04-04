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
        ui.displayMessage("Velkommen til " + this.StreamName);

        if (!data.isEmpty() && ui.promptBinary("Gemt account fundet, vil du fortsætte herfra?: Y/N")) {
            for (String s : data) {
                String[] values = s.split(";");
                createAccount(values[0], values[1]);
            }

            currentUser = account.getFirst();
            ui.displayMessage("Du er nu logget ind som " + currentUser.getName());
        } else {

            registerUser();
        }
    }

    public void endSession() {
        ArrayList<String> User = new ArrayList<>();

        for (Account p : account) {

            String s = p.toString();
            User.add(s);
        }

        io.saveData(User, "data/account.csv", "Name; Password");
        ui.displayMessage("Du er nu logget ud.");
    }

    public void registerUser() {

        // Ber brugeren om et nyt brugernavn og kode
        String accName = ui.promptText("Vælg et brugernavn: ");
        String accPassword = ui.promptText("Vælg et kodeord: ");
        this.createAccount(accName, accPassword);
        ui.promptText("Bruger oprettet! Du er nu logget ind som " + accName + ".");
        // Vi sender brugeren til homepage
        // HovedMenu.menu();
    }

    public void createAccount(String accountName, String password) {

        Account acc = new Account(accountName,password);
        account.add(acc);
    }

    public void currentUser() {


    }
}
