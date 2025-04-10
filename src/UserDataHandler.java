import java.util.ArrayList;

import util.FileIO;
import util.TextUI;

public class UserDataHandler {

    private FileIO io = new FileIO();
    private TextUI ui = new TextUI();
    private Search s;
    private String watchedPath = "data/WatchedMovies.csv";

    public void addToWatched(String title) {
        ArrayList<String> watchedData = io.readData(watchedPath);

        if (!watchedData.contains(title)) {
            watchedData.add(title);
            io.saveData(watchedData, watchedPath, "Watched Movies");
        }
    }

    public void showWatched() {
        s = new Search();
        ArrayList<String> watchedData = io.readData(watchedPath);

        ui.displayMessage("Sete film/serier:");

        if (watchedData.isEmpty()) {
            ui.displayMessage("Du har endnu ikke set nogen film.\n");
            return;
        } else {
            for (int i = 0; i < watchedData.size(); i++) {
                ui.displayMessage((i + 1) + ". " + watchedData.get(i));
            }
        }

        int choice = ui.promptNumber("Indtast nummeret på den film du vil afspille: ", 1, watchedData.size());

        String selectedMovie = watchedData.get(choice - 1);
        ui.displayMessage("Afspiller: " + selectedMovie);
        s.playMenu(selectedMovie);
    }
}
