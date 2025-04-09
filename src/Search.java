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

    private ArrayList<Movie> loadedMovies = new ArrayList<>();
    private ArrayList<Series> loadedSeries = new ArrayList<>();

    private boolean titleFound = false;

    public Search() {
        readMovie();
        readSeries();
        loadMovieList();
        loadSeriesList();
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

    public void searchByGenre() {
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
            ui.displayMessage("Ingen film eller serier fundet i genren: " + inputGenre);
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
            String[] genres = movie[2].split(",");
            for (String genre : genres) {
                genre = genre.trim();
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
        seriesList.sort((a, b) -> Double.compare(Double.parseDouble(b[3].replace(",", ".")), Double.parseDouble(a[3].replace(",", "."))));
        ui.displayMessage("Top 10 Serier: ");
        for (int i = 0; i < Math.min(10, seriesList.size()); i++) {
            ui.displayMessage(Arrays.toString(seriesList.get(i)));
        }
    }

    public void top10Movies() {
        moviesList.sort((a, b) -> Double.compare(Double.parseDouble(b[3].replace(",", ".")), Double.parseDouble(a[3].replace(",", "."))));
        ui.displayMessage("Top 10 Film: ");
        for (int i = 0; i < Math.min(10, moviesList.size()); i++) {
            ui.displayMessage(Arrays.toString(moviesList.get(i)));
        }
    }

    public void searchMenu() {
        while (true) {
            ui.displayMessage("1. Title Search\n2. Show All Movies\n3. Show All Series\n4. Genres\n5. Top10 Movies\n6. Top10 Series\n7. Søg efter genre\n0. Logout");

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
                    top10Movies();
                    break;
                case "6":
                    top10Series();
                    break;
                case "7":
                    searchByGenre();
                    break;
                case "0":
                    ui.displayMessage("Farvel!");
                    return;
                default:
                    ui.displayMessage("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    public void readMovie() {
        if (!moviesData.isEmpty()) {
            for (String s : moviesData) {
                String[] values = s.split(";");
                int year = Integer.parseInt(values[1].trim());
                String[] genre = values[2].trim().split(",");
                double rating = Double.parseDouble(values[3].trim().replace(",", "."));
                loadedMovies.add(new Movie(values[0], year, genre, rating));
            }
        }
    }

    public void readSeries() {
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

                        loadedSeries.add(new Series(values[0], values[1], genre, rating, season, episode));
                    }
                }
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



}
