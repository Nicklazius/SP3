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
        readMedia();
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
        ui.displayMessage("Bruger oprettet! Du er nu logget ind som " + accName + ".");
        // Vi sender brugeren til homepage
        // HovedMenu.menu();
    }

    public void createAccount(String accountName, String password) {

        Account acc = new Account(accountName,password);
        account.add(acc);
    }

    public void readMedia(){

        ArrayList<String> readMovies = io.readData("data/Movies.csv");
        ArrayList<String> readSeries = io.readData("data/Series.csv");
    }
    private static void searchMenu() {
        Account acc = new Account("","");
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("1. Se alt media");
            System.out.println("2. Søg efter film");
            System.out.println("3. Søg efter serie");
            System.out.println("4. Søg efter genre");
            System.out.println("5. Vis historie");
            System.out.println("6. Vis gemte media");
            System.out.println("7. logout");

            int choice = scan.nextInt();

        }
    }


}
