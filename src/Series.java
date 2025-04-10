public class Series extends Media {

    public Series(String title, String runTime, String[] genre, double rating, int season, int episode) {
        super(title, 0, genre, rating, runTime, season, episode);
    }

    public String getTitle() {
        return title;
    }

    public String[] getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Titel: " + title + "  Info om serien - RunTime: " + runTime + ", Genre: " + String.join(", ", genre) + ", Rating: " + rating;
    }
}
