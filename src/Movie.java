import util.FileIO;

import java.util.ArrayList;

public class Movie extends Media {

    // Konstruktor – det der sker, når vi laver et nyt src.Media-objekt
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

    public String[] getCategories() {
        return categories;
    }

    public void play() {

    }

    public void stop() {

    }
}
