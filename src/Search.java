import util.FileIO;
import util.TextUI;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class Search extends Media {
    FileIO io = new FileIO();
    TextUI ui = new TextUI();

    private String[] categories = {"Title Search", "Movies", "Series", "Genres", "Top10 Series", "Top10 Movies"};
    private ArrayList<String[]> moviesList = new ArrayList<>();
    private ArrayList<String[]> seriesList = new ArrayList<>();
    private boolean titleFound = false;


    public Search() {

        io.readData("data/movies.csv");
        io.readData("data/series.csv");
    }

    public void titleSearch(String title) {

        ui.displayMessage("Søger efter titel: " + this.title);

        for (String[] movie : moviesList) {
            if (movie[0].equalsIgnoreCase(this.title)) {
                ui.displayMessage("Fundet film: " + this.title);
                titleFound = true;
                break;
            }
        }

        for (String[] series : seriesList) {
            if (series[0].equalsIgnoreCase(this.title)) {
                ui.displayMessage("Fundet serie: " + this.title);
                titleFound = true;
                break;
            }
        }

        if (!titleFound) {
            ui.displayMessage("Ingen film eller serier fundet med titlen " + this.title);
        }
    }

    public void movies() {

        ui.displayMessage("Alle film:");
        for (String[] movie : moviesList) {
            ui.displayMessage(Arrays.toString(movie));
        }
    }

    public void series() {

        ui.displayMessage("Alle serier:");
        for (String[] series : seriesList) {
            ui.displayMessage(Arrays.toString(series));
        }
    }

    public void genres() {

        ArrayList<String> allGenres = new ArrayList<>();

        for (String[] movie : moviesList) {
            if (!allGenres.contains(movie[2])) {
                allGenres.add(movie[2]);
            }
        }

        for (String[] serie : seriesList) {
            if (!allGenres.contains(serie[2])) {
                allGenres.add(serie[2]);
            }
        }

        ui.displayMessage("Alle genrer: " + allGenres);
    }

    public void top10Series() {

        seriesList.sort((a, b) -> Double.compare(Double.parseDouble(b[3]), Double.parseDouble(a[3])));
        ui.displayMessage("Top 10 Serier: ");
        for (int i = 0; i < Math.min(10, seriesList.size()); i++) {
            ui.displayMessage(Arrays.toString(seriesList.get(i)));
        }
    }

    public void top10Movies() {

        moviesList.sort((a, b) -> Double.compare(Double.parseDouble(b[3]), Double.parseDouble(a[3])));
        ui.displayMessage("Top 10 Film: ");
        for (int i = 0; i < Math.min(10, moviesList.size()); i++) {
            ui.displayMessage(Arrays.toString(moviesList.get(i)));
        }
    }

    public void searchMenu() {

        while (true) {
            ui.displayMessage("1. Title Search\n2. Movies\n3. Series\n4. Genres\n5. Top10 Series\n6. Top10 Movies\n0. Afslut");

            ui.displayMessage("Vælg en mulighed: ");
            String userChoice = ui.promptText("");

            switch (userChoice) {
                case "1":
                    ui.displayMessage("Indtast titel: ");
                    String title = ui.promptText("");  // Get the title input

                    if (title == null || title.trim().isEmpty()) {  // Check for empty or null input
                        ui.displayMessage("Indtast venligst en titel.");
                    } else {
                        titleSearch(title);  // Call the titleSearch method with the input title
                    }
                    break;
                case "2":
                    movies();
                    break;
                case "3":
                    series();
                    break;
                case "4":
                    genres();
                    break;
                case "5":
                    top10Series();
                    break;
                case "6":
                    top10Movies();
                    break;
                case "0":
                    ui.displayMessage("Farvel!");
                    return;
                default:
                    ui.displayMessage("Ugyldigt valg. Prøv igen.");
            }
        }
    }
}
