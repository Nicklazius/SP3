import java.util.ArrayList;

// En abstrakt klasse, som er en "grundform" for medier som film eller serier
public class Movie {
    // Egenskaber (variabler)
    protected String title;
    protected String yearReleased;
    protected double rating;
    protected ArrayList<String> categories;

    // Konstruktor – det der sker, når vi laver et nyt src.Media-objekt
    public Movie(String title, String yearReleased, ArrayList<String> categories, double rating) {
        this.title = title;
        this.yearReleased = yearReleased;
        this.categories = categories;
        this.rating = rating;
    }

    // Metoder – som giver adgang til informationer om objektet
    public String getTitle() {
        return title;
    }

    public String getYearReleased() {
        return yearReleased;
    }

    public double getRating() {
        return rating;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    // "Play" og "Stop" er metoder som vi bare skriver, men ikke udfylder endnu
    public void play(){

    }

    public void stop(){

    }
}
