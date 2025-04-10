import util.FileIO;
import util.TextUI;

import java.util.ArrayList;
import java.util.Scanner;

public class Search {
    TextUI ui = new TextUI();
    FileIO io = new FileIO();
    UserDataHandler userDataHandler = new UserDataHandler();
    Scanner sc = new Scanner(System.in);

    private ArrayList<String[]> moviesList = new ArrayList<>();
    private ArrayList<String[]> seriesList = new ArrayList<>();
    private ArrayList<String> moviesData = io.readData("data/movies.csv");
    private ArrayList<String> seriesData = io.readData("data/series.csv");
    private ArrayList<Series> loadedSeries = readSeries();
    private ArrayList<Movie> loadedMovies = readMovie();

    String genreMsg = ("1. Action\n2. Drama\n3. Mystery\n4. Adventure\n5. Thriller\n6. Comedy\n7. History\n" +
            "8. Animation\n9. Sci-fi\n10. Crime\n11. Biography\n12. Family\n13. Romance\n14. Talk-show\n15. Documentary\n" +
            "16. Horror\n17. War\n18. Sport\n19. Film-Noir\n20. Musical\n21. Western\n22. Music\n23. Fantasy").toLowerCase().trim();

    public Search() {
        loadMovieList();
        loadSeriesList();
    }

    public void searchMenu() {

        ui.displayMessage("Hovedmenu: Vælg en mulighed:");
        ui.displayMessage("1. Titel Søgning\n2. Alle Film\n3. Alle Serier\n4. Søg Genre for Film\n" +
                "5. Søg Genre for Serier\n6. Top10 Film\n7. Top10 Serier\n8. Sete Film/Serier\n0. Logout");
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
                break;
            case "8":
                userDataHandler.showWatched();
                break;
            case "0":
                System.out.println("Farvel :)\nDu er nu logget ud.");
                System.exit(0);
                break;
            default:
                System.out.println("Skriv et gyldigt tal.");
                searchMenu();
                break;
        }
    }

    public void titles() {
        System.out.println("Indtast titel eller skriv ESC for at komme tilbage til Hovedmenu'en.");

        while (true) {
            String title = sc.nextLine();

            if (title == null || title.trim().isEmpty()) {
                ui.displayMessage("Skriv venligst en titel.");
            } else if (title.equalsIgnoreCase("esc")) {
                searchMenu();
                return;
            } else if (!titleExists(title)) {
                ui.displayMessage("Ingen film eller serier blev fundet med titlen " + title + "\nSøg igen eller skriv ESC for at komme tilbage til Hovedmenu'en.");
            } else {
                titleSearch(title);
                return;
            }
        }
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

    public void searchByGenreMovie() {
        String[] genres = genreMsg.split("\n");

        while (true) {
            int choice = ui.promptNumber(genreMsg + "\nVælg venligst en genre:", 1, genres.length);
            String inputGenre = genres[choice - 1].substring(3).toLowerCase().trim();
            ArrayList<Movie> genreMatches = new ArrayList<>();

            for (Movie movie : loadedMovies) {
                for (String genre : movie.getGenre()) {
                    if (genre.toLowerCase().trim().equals(inputGenre)) {
                        genreMatches.add(movie);
                        break;
                    }
                }
            }

            if (genreMatches.isEmpty()) {
                ui.displayMessage("Ingen film fundet i genren: " + inputGenre + "\nPrøv igen.");
                continue;
            }

            ui.displayMessage("Film i genren: " + inputGenre);
            for (int i = 0; i < genreMatches.size(); i++) {
                ui.displayMessage((i + 1) + ". " + genreMatches.get(i).toString());
            }

            int filmChoice = ui.promptNumber("Indtast nummeret på den film du vil afspille: ", 1, genreMatches.size());
            Movie selectedMovie = genreMatches.get(filmChoice - 1);
            ui.displayMessage("Afspiller: " + selectedMovie.getTitle());
            playMenu(selectedMovie.getTitle());
            break;
        }
    }


    public void searchByGenreSeries() {
        String[] genres = genreMsg.split("\n");

        while (true) {
            int choice = ui.promptNumber(genreMsg + "\nVælg venligst en genre:", 1, genres.length);
            String inputGenre = genres[choice - 1].substring(3).toLowerCase().trim();

            ArrayList<Series> genreMatches = new ArrayList<>();

            for (Series series : loadedSeries) {
                for (String genre : series.getGenre()) {
                    if (genre.toLowerCase().trim().equals(inputGenre)) {
                        genreMatches.add(series);
                        break;
                    }
                }
            }

            if (genreMatches.isEmpty()) {
                ui.displayMessage("Ingen film fundet i genren: " + inputGenre + "\nPrøv igen.");
                continue;
            }

            ui.displayMessage("Film i genren: " + inputGenre);
            for (int i = 0; i < genreMatches.size(); i++) {
                ui.displayMessage((i + 1) + ". " + genreMatches.get(i).toString());
            }

            int seriesChoice = ui.promptNumber("Indtast nummeret på den film du vil afspille: ", 1, genreMatches.size());
            Series selectedseries = genreMatches.get(seriesChoice - 1);
            ui.displayMessage("Afspiller: " + selectedseries.getTitle());
            playMenu(selectedseries.getTitle());
            break;
        }
    }


    public void top10Movies() {
        loadedMovies.sort((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()));
        ui.displayMessage("Top 10 Film: ");

        for (int i = 0; i < Math.min(10, loadedMovies.size()); i++) {
            ui.displayMessage((i + 1) + ". " + loadedMovies.get(i).toString());
        }

        int choice = ui.promptNumber("Indtast nummeret på den film du vil afspille: ", 1, 10);

        Movie selectedMovie = loadedMovies.get(choice - 1);
        ui.displayMessage("Afspiller: " + selectedMovie.getTitle());
        playMenu(selectedMovie.getTitle());
    }

    public void top10Series() {
        loadedSeries.sort((s1, s2) -> Double.compare(s2.getRating(), s1.getRating()));
        ui.displayMessage("Top 10 Serier: ");

        for (int i = 0; i < Math.min(10, loadedSeries.size()); i++) {
            ui.displayMessage((i + 1) + ". " + loadedSeries.get(i).toString());
        }

        int choice = ui.promptNumber("Indtast nummeret på den serie du vil afspille: ", 1, 10);

        Series selectedSeries = loadedSeries.get(choice - 1);
        ui.displayMessage("Afspiller: " + selectedSeries.getTitle());
        playMenu(selectedSeries.getTitle());
    }

    public void titleSearch(String title) {

        for (Movie movie : loadedMovies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                ui.displayMessage("Fundet film: " + title);

                if (ui.promptBinary("Vil du afspille " + title + "?: Y/N")) {
                    ui.displayMessage("Afspiller: " + title);
                    playMenu(title);
                }
                return;
            }
        }

        for (Series series : loadedSeries) {
            if (series.getTitle().equalsIgnoreCase(title)) {
                ui.displayMessage("Fundet serie: " + title);

                if (ui.promptBinary("Vil du afspille " + title + "?: Y/N")) {
                    ui.displayMessage("Afspiller: " + title);
                    playMenu(title);
                }
                return;
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
        userDataHandler.addToWatched(title);

        boolean playLoop = true;

        while (playLoop) {
            String input = ui.promptText("1. Pause\n2. Tilbage til Hovedmenu");

            if (input.equals("1")) {
                ui.displayMessage(title + " er sat på pause");
                boolean pauseLoop = true;

                while (pauseLoop) {
                    String resumeInput = ui.promptText("1. Genoptag\n2. Tilbage til Hovedmenu");

                    if (resumeInput.equals("1")) {
                        ui.displayMessage(title + " er Genoptaget");
                        break;
                    } else if (resumeInput.equals("2")) {
                        pauseLoop = false;
                        searchMenu();
                    } else {
                        ui.displayMessage("Skriv et gyldigt tal. " + title + " er stadig på pause");
                    }
                }
            } else if (input.equals("2")) {
                playLoop = false;
                searchMenu();
            } else {
                ui.displayMessage("Skriv et gyldigt tal. " + title + " afspiller stadig");
            }
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
