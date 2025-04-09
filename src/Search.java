import util.FileIO;
import util.TextUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Search {
    TextUI ui = new TextUI();
    FileIO io = new FileIO();
    Scanner sc = new Scanner(System.in);

    private ArrayList<String[]> moviesList = new ArrayList<>();
    private ArrayList<String[]> seriesList = new ArrayList<>();
    private ArrayList<String> moviesData = io.readData("data/movies.csv");
    private ArrayList<String> seriesData = io.readData("data/series.csv");
    private ArrayList<Series> loadedSeries = readSeries();
    private ArrayList<Movie> loadedMovies = readMovie();
    private boolean titleFound = false;

    public Search() {

        loadMovieList();
        loadSeriesList();
    }

    public void titleSearch(String title) {

        for (Movie movie : loadedMovies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                ui.displayMessage("Fundet film: " + title);
                titleFound = true;
                if (ui.promptBinary("Vil du afspille " + title + "?: Y/N")) {
                    playMenu(title);
                } else {
                    ui.displayMessage("Tilbage til Hovedmenu:");
                    searchMenu();
                }
                break;
            }
        }

        for (Series series : loadedSeries) {
            if (series.getTitle().equalsIgnoreCase(title)) {
                ui.displayMessage("Fundet serie: " + title);
                titleFound = true;
                if (ui.promptBinary("Vil du afspille " + title + "?: Y/N")) {
                    playMenu(title);
                } else {
                    ui.displayMessage("Tilbage til Hovedmenu:");
                    searchMenu();
                }
                break;
            }
        }

        if (!titleFound) {
            ui.displayMessage("Ingen film eller serier blev fundet med titlen " + title + "\nTilbage til Hovedmenu.");

        }
    }

    public void movies() {

        ui.displayList(moviesData, "");
    }

    public void series() {

        ui.displayList(seriesData, "");
    }

    public void genres() {

        ArrayList<String> allGenres = new ArrayList<>();

        for (String[] movie : moviesList) {
            String[] genres = movie[2].split(",");
            for (String genre : genres) {
                genre = genre.trim(); // Fjern evt. mellemrum
                if (!allGenres.contains(genre)) {
                    allGenres.add(genre);
                }
            }
        }

        for (String[] serie : seriesList) {
            String[] genres = serie[2].split(",");
            for (String genre : genres) {
                genre = genre.trim();
                if (!allGenres.contains(genre)) {
                    allGenres.add(genre);
                }
            }
        }

        ui.displayMessage("Alle genrer: " + allGenres);
    }

    public void top10Series() {

        loadedSeries.sort((s1, s2) -> Double.compare(s2.getRating(), s1.getRating()));
        ui.displayMessage("Top 10 Serier: ");
        for (int i = 0; i < Math.min(10, seriesList.size()); i++) {
            ui.displayMessage(loadedSeries.get(i).toString());
        }
    }

    public void top10Movies() {

        loadedMovies.sort((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()));
        ui.displayMessage("Top 10 Film: ");

        for (int i = 0; i < Math.min(10, loadedMovies.size()); i++) {
            ui.displayMessage(loadedMovies.get(i).toString());
        }
    }

    public void searchMenu() {

        while (true) {
            ui.displayMessage("Hovedmenu: Vælg en mulighed:");
            ui.displayMessage("1. Title Search\n2. Show All Movies\n3. Show All Series\n4. Genres\n5. Top10 Movies\n6. Top10 Series\n0. Logout");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    while (true) {
                        System.out.print("Indtast titel eller skriv ESC");
                        String title = ui.promptText("");

                        if (title == null || title.trim().isEmpty()) {

                        } else if (title.equalsIgnoreCase("esc")) {
                            searchMenu();
                            break;
                        } else if (!titleExists(title)) {

                        } else {
                            titleSearch(title);
                            break;
                        }
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
                    top10Movies();
                    break;
                case "6":
                    top10Series();
                    break;
                case "0":
                    System.out.println("Farvel :)");
                    return;
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    public ArrayList<Movie> readMovie() {

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

    public void playMenu(String title) {

        while (true) {
            ui.displayMessage("1. Pause\n2. Afslut\n3. Genoptag");
            String input = ui.promptText("");

            if (input.equalsIgnoreCase("1")) {
                ui.displayMessage(title + " er sat på pause");
            } else if (input.equalsIgnoreCase("2")) {
                ui.displayMessage(title + " er afsluttet");
                return;
            } else if (input.equalsIgnoreCase("3")) {
                ui.displayMessage(title + " er Genoptaget");
            } else
                ui.displayMessage("");
        }
    }

    public void loadMovieList() {
        for (String s : moviesData) {
            String[] values = s.split(";");
            moviesList.add(values);
        }
    }

    public void loadSeriesList() {
        for (String s : seriesData) {
            String[] values = s.split(";");
            seriesList.add(values);
        }
    }

    private boolean titleExists(String title) {
        for (Movie movie : loadedMovies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        for (Series series : loadedSeries) {
            if (series.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }
}