import util.FileIO;
import java.util.ArrayList;

public class Series extends Media {

    public Series(String title, String runTime, String[] genre, double rating, int season, int episode) {
        super(title, 0, genre, rating, runTime, season, episode);
        this.title = title;
        this.runTime = runTime;
        this.genre = genre;
        this.rating = rating;
        this.season = season;
        this.episode = episode;
    }

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

    @Override
    public String toString() {
        return "Titel: " + title;
    }
}
