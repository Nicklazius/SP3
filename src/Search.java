import util.FileIO;
import util.TextUI;

import java.util.ArrayList;
import java.util.Arrays;


public class Search {
    TextUI ui = new TextUI();
    FileIO io = new FileIO();

    private ArrayList<String[]> moviesList = new ArrayList<>();
    private ArrayList<String[]> seriesList = new ArrayList<>();
    private ArrayList<String> moviesData = io.readData("data/movies.csv");
    private ArrayList<String> seriesData = io.readData("data/series.csv");

    private ArrayList<Series> loadedSeries = readSeries();
    private ArrayList<Movie> loadedMovies = readMovie();

    private boolean titleFound = false;


    public Search() {

    }

    public void titleSearch(String title) {

        ui.displayMessage("Søger efter titel: " + title);

        for (Movie movie : loadedMovies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                ui.displayMessage("Fundet film: " + title);
                titleFound = true;
                break;
            }
        }

        for (Series series : loadedSeries) {
            if (series.getTitle().equalsIgnoreCase(title)) {
                ui.displayMessage("Fundet serie: " + title);
                titleFound = true;
                break;
            }
        }

        if (!titleFound) {
            ui.displayMessage("Ingen film eller serier fundet med titlen " + title);
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
            ui.displayMessage("1. Title Search\n2. Movies\n3. Series\n4. Genres\n5. Top10 Series\n6. Top10 Movies\n0. Logout");

            ui.displayMessage("Vælg en mulighed: ");
            String userChoice = ui.promptText("");

            switch (userChoice) {
                case "1":
                    ui.displayMessage("Indtast titel: ");
                    String title = ui.promptText("");

                    if (title == null || title.trim().isEmpty()) {
                        ui.displayMessage("Indtast venligst en titel.");
                    } else {
                        titleSearch(title);
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

    public ArrayList<Movie>  readMovie() {

        ArrayList<Movie> createdMovies = new ArrayList<>();

        if (!moviesData.isEmpty()) {
            for (String s : moviesData) {
                String[] values = s.split(";");
                int year = Integer.parseInt(values[1].trim());
                String[] genre = values[2].trim().split(",");
                double rating = Double.parseDouble(values[3].trim().replace(",", "."));
                createdMovies.add(new Movie(values[0], year, genre, rating));
            }
        }
        return createdMovies;
    }

    public ArrayList<Series> readSeries() {

        ArrayList<Series> createdSeries = new ArrayList<>();

        if (!seriesData.isEmpty()) {
            for (String s : seriesData) {
                String[] values = s.split(";");
                String[] genre = values[2].trim().split(",");
                double rating = Double.parseDouble(values[3].trim().replace(",", "."));
                String[] seasonEpisodePairs = values[4].trim().split(",");

                for (String pair : seasonEpisodePairs) {
                    String[] parts = pair.trim().split("-");
                    if (parts.length == 2) {
                        int season = Integer.parseInt(parts[0].trim());
                        int episode = Integer.parseInt(parts[1].trim());

                        createdSeries.add(new Series(values[0], values[1], genre, rating, season, episode));
                    }
                }
            }
        }
        return createdSeries;
    }
}