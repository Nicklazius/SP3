import util.FileIO;
import java.util.ArrayList;

public class Movie extends Media {

    public Movie(String title, int year, String[] genre, double rating) {
        super(title, year, genre, rating, null, 0, 0);
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public String[] getGenre() {
        return genre;
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
