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

    public Search() {

        loadMovieList();
        loadSeriesList();
    }

    public void titleSearch(String title) {

        boolean titleFound = false;

        for (Movie movie : loadedMovies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                ui.displayMessage("Fundet film: " + title);
                titleFound = true;
                if (ui.promptBinary("Vil du afspille " + title + "?: Y/N")) {
                    ui.displayMessage("Afspiller: " + title);
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
                    ui.displayMessage("Afspiller: " + title);
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

    public void titles() {

        System.out.println("Indtast titel eller skriv ESC for at komme tilbage til Hovedmenu'en.");

        while (true) {
            String title = sc.nextLine();;

            if (title == null || title.trim().isEmpty()) {
                ui.displayMessage("Skriv et venligst en titel.");
            } else if (title.equalsIgnoreCase("esc")) {
                searchMenu();
                break;
            } else if (!titleExists(title)) {
                ui.displayMessage("Ingen film eller serier blev fundet med titlen " + title + "\nSøg igen eller skriv ESC for at komme tilbage til Hovedmenu'en.");
            } else {
                titleSearch(title);
                break;
            }
        }
    }

    public void series() {

        ui.displayList(seriesData, "");
    }

    public void searchByGenreMovie() {
        String inputGenre = ui.promptText("Indtast genre du vil søge efter (fx Drama, Comedy):").toLowerCase().trim();
        boolean found = false;

        ui.displayMessage("Film i genren: " + inputGenre);
        for (Movie movie : loadedMovies) {
            for (String genre : movie.getGenre()) {
                if (genre.toLowerCase().trim().equals(inputGenre)) {
                    System.out.println(movie);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            ui.displayMessage("Ingen film fundet i genren: " + inputGenre);
        }
    }

    public void searchByGenreSeries(){
        String inputGenre = ui.promptText("Indtast genre du vil søge efter (fx Drama, Comedy):").toLowerCase().trim();
        boolean found = false;

        ui.displayMessage("Serier i genren: " + inputGenre);
        for (Series series : loadedSeries) {
            for (String genre : series.getGenre()) {
                if (genre.toLowerCase().trim().equals(inputGenre)) {
                    System.out.println(series);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            ui.displayMessage("Ingen serier fundet i genren: " + inputGenre);
        }
    }

    public void top10Series() {

        loadedSeries.sort((s1, s2) -> Double.compare(s2.getRating(), s1.getRating()));
        ui.displayMessage("Top 10 Serier: ");

        for (int i = 0; i < Math.min(10, seriesList.size()); i++) {
            ui.displayMessage(loadedSeries.get(i).toString());
        }
        searchMenu();
    }

    public void top10Movies() {

        loadedMovies.sort((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()));
        ui.displayMessage("Top 10 Film: ");

        for (int i = 0; i < Math.min(10, loadedMovies.size()); i++) {
            ui.displayMessage(loadedMovies.get(i).toString());
        }
        searchMenu();
    }

    public void searchMenu() {

        while (true) {
            ui.displayMessage("Hovedmenu: Vælg en mulighed:");
            ui.displayMessage("1. Title Search\n2. All Movies\n3. Show All Series\n4. Genres\n5. Top10 Movies\n6. Top10 Series\n0. Logout");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    titles();
                    break;
                case "2":
                    movieMenu(loadedMovies);
                    break;
                case "3":
                    seriesMenu(loadedSeries);
                    break;
                case "4":
                    searchByGenreMovie();
                    break;
                case "5":
                    searchByGenreSeries();
                    break;
                case "6":
                    top10Movies();
                    break;
                case "7":
                    top10Series();
                case "0":
                    System.out.println("Farvel :)");
                    break;
                default:
                    System.out.println("Skriv et gyldigt tal.");
            }
            break;
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
            String input = ui.promptText("1. Pause\n2. Tilbage til Hovedmenu");

            if (input.equalsIgnoreCase("1")) {
                ui.displayMessage(title + " er sat på pause");

                while (true) {
                    String resumeInput = ui.promptText("1. Genoptag\n2. Tilbage til Hovedmenu");

                    if (resumeInput.equalsIgnoreCase("1")) {
                        ui.displayMessage(title + " er Genoptaget");
                        playMenu(title);
                        break;
                    } else if (resumeInput.equalsIgnoreCase("2")) {
                        searchMenu();
                        break;
                    } else {
                        ui.displayMessage("Skriv et gyldigt tal. " + title + " er stadig på pause");
                    }
                }
                break;
            } else if (input.equalsIgnoreCase("2")) {
                searchMenu();
                break;
            } else {
                ui.displayMessage("Skriv et gyldigt tal. " + title + " afspiller stadig");
            }
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

    public boolean titleExists(String title) {
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

    public void movieMenu(ArrayList<Movie> loadedMovies) {
        ui.displayMessage("Vælg en film:");

        for (int i = 0; i < loadedMovies.size(); i++) {
            ui.displayMessage((i + 1) + ". " + loadedMovies.get(i).toString());
        }

        int choice = ui.promptNumber("Indtast nummeret på den film du vil afspille: ", 1, loadedMovies.size());

        Movie selectedMovie = loadedMovies.get(choice - 1);
        ui.displayMessage("Afspiller: " + selectedMovie.getTitle());
        playMenu(selectedMovie.getTitle());
    }

    public void seriesMenu(ArrayList<Series> loadedSeries) {
        ui.displayMessage("Vælg en serie:");

        for (int i = 0; i < loadedSeries.size(); i++) {
            ui.displayMessage((i + 1) + ". " + loadedSeries.get(i).toString());
        }

        int choice = ui.promptNumber("Indtast nummeret på den serie du vil afspille: ", 1, loadedSeries.size());

        Series selectedSeries = loadedSeries.get(choice - 1);
        ui.displayMessage("Afspiller: " + selectedSeries.getTitle());
        playMenu(selectedSeries.getTitle());
    }

}