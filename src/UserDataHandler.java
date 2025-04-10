package util;

import java.util.ArrayList;

public class UserDataHandler {

    private FileIO io = new FileIO();
    private final String watchedPath = "data/WatchedMovies.csv";
    private final String savedPath = "data/SavedMovies.csv";

    // Tilføjer titel til WatchedMovies.csv (hvis ikke allerede set)
    public void addToWatched(String title) {
        ArrayList<String> watchedData = io.readData(watchedPath);

        if (!watchedData.contains(title)) {
            watchedData.add(title);
            io.saveData(watchedData, watchedPath, "Watched Movies");
        }
    }

    // Tilføjer titel til SavedMovies.csv (hvis ikke allerede gemt)
    public void addToSaved(String title) {
        ArrayList<String> savedData = io.readData(savedPath);

        if (!savedData.contains(title)) {
            savedData.add(title);
            io.saveData(savedData, savedPath, "Saved Movies");
        }
    }

    // Viser alle sete film/serier
    public void showWatched(TextUI ui) {
        ArrayList<String> watchedData = io.readData(watchedPath);

        ui.displayMessage("Sete film/serier:");

        if (watchedData.isEmpty()) {
            ui.displayMessage("Du har endnu ikke set nogen film eller serier.");
        } else {
            for (String title : watchedData) {
                System.out.println(title);
            }
        }
    }

    // Viser alle gemte film/serier
    public void showSaved(TextUI ui) {
        ArrayList<String> savedData = io.readData(savedPath);

        ui.displayMessage("Gemte film/serier:");

        if (savedData.isEmpty()) {
            ui.displayMessage("Du har endnu ikke gemt nogen film eller serier.");
        } else {
            for (String title : savedData) {
                System.out.println(title);
            }
        }
    }
}
