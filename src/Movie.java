import java.util.ArrayList;

// En abstrakt klasse, som er en "grundform" for medier som film eller serier
public class Movie extends Media {

    // Konstruktor – det der sker, når vi laver et nyt src.Media-objekt
    public Movie(String title, int year, String[] genre, double rating) {

        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    // Metoder – som giver adgang til informationer om objektet
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

    // "Play" og "Stop" er metoder som vi bare skriver, men ikke udfylder endnu
    public void play(){

    }

    public void stop(){

    }
}
