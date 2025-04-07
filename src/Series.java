import java.util.ArrayList;

// En klasse der repræsenterer en serie
public class Series extends Media {

    // Konstruktor – det der sker, når vi laver et nyt Series-objekt
    public Series(String title, String runTime, String[] genre, double rating, int seasons, int episodes) {
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

    // "Play" og "Stop" metoder
    public void play() {
        // her kunne du f.eks. skrive: System.out.println("Spiller serien " + title);
    }

    public void stop() {
        // her kunne du f.eks. skrive: System.out.println("Stopper serien " + title);
    }
}
