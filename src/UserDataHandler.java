import java.util.ArrayList;

import util.FileIO;
import util.TextUI;

public class UserDataHandler {

    private FileIO io = new FileIO();
    private TextUI ui = new TextUI();
    private String watchedPath = "data/WatchedMovies.csv";
    private String savedPath = "data/SavedMovies.csv";

    public void addToWatched(String title) {
        ArrayList<String> watchedData = io.readData(watchedPath);

        if (!watchedData.contains(title)) {
            watchedData.add(title);
            io.saveData(watchedData, watchedPath, "Watched Movies");
        }
    }

    public void addToSaved(String title) {
        ArrayList<String> savedData = io.readData(savedPath);

        if (!savedData.contains(title)) {
            savedData.add(title);
            io.saveData(savedData, savedPath, "Saved Movies");
        }
    }

    public void showWatched() {
        Search s = new Search();
        ArrayList<String> watchedData = io.readData(watchedPath);

        ui.displayMessage("Sete film/serier:");

        if (watchedData.isEmpty()) {
            ui.displayMessage("Du har endnu ikke set nogen film.");
        } else {
            for (int i = 0; i < watchedData.size(); i++) {
                ui.displayMessage((i + 1) + ". " + watchedData.get(i));
            }
        }

        int choice = ui.promptNumber("Indtast nummeret på den film du vil afspille: ", 1, 10);

        String selectedMovie = watchedData.get(choice - 1);
        ui.displayMessage("Afspiller: " + selectedMovie);
        s.playMenu(selectedMovie);
    }

    public void showSaved() {
        ArrayList<String> savedData = io.readData(savedPath);

        ui.displayMessage("Gemte film/serier:");

        if (savedData.isEmpty()) {
            ui.displayMessage("Du har endnu ikke gemt nogen film eller serier.");
        } else {
            for (int i = 0; i < savedData.size(); i++) {
                ui.displayMessage((i + 1) + ". " + savedData.get(i));
            }
        }
    }
}
