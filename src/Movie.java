import java.util.ArrayList;

// En abstrakt klasse, som er en "grundform" for medier som film eller serier
public class Movie extends Media {

    private int year;
    private String title;

    // Konstruktor – det der sker, når vi laver et nyt src.Media-objekt
    public Movie(String title, int year, String[] genre, double rating) {
        super(title, year, genre, rating,null,0,0);
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



    @Override
    public String toString() {

        return "Titel: " + title + "  Info om filmen - År: " + year + ", Genre: " + String.join(", ", genre) + ", Rating: " + rating;
    }

    public String[] getGenre() {
        return genre;
    }
}
