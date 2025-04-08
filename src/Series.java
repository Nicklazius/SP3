import util.FileIO;

import java.util.ArrayList;

// En klasse der repræsenterer en serie
public class Series extends Media {

    // Konstruktor – det der sker, når vi laver et nyt Series-objekt
    public Series(String title, String runTime, String[] genre, double rating, int season, int episode) {
        super(title, 0, genre, rating, runTime, season, episode);

        this.title = title;
        this.runTime = runTime;
        this.genre = genre;
        this.rating = rating;
        this.season = season;
        this.episode = episode;
    }

    // Metoder – som giver adgang til informationer om objektet
    public String getTitle() {
        return title;
    }

    public String getRuntime() {
        return runTime;
    }

    public String[] getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public void play() {

    }

    public void stop() {

    }

    public void readSeries(){

        FileIO io = new FileIO();
        ArrayList<String> seriesList = io.readData("data/series.csv");

        if (!seriesList.isEmpty()) {
            for (String s : seriesList) {
                String[] values = s.split(";");
                String[] genre = values[2].split(",".trim());
                double rating = Double.parseDouble(values[3]);
                String[] part = values[4].split("-");
                int season = Integer.parseInt(part[0]);
                int episode = Integer.parseInt(part[1]);
                Series series = new Series(values[0], values[1], genre, rating, season, episode);
            }
        }
    }
}
