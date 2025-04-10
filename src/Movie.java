public class Movie extends Media {

    public Movie(String title, int year, String[] genre, double rating) {
        super(title, year, genre, rating,null,0,0);
    }

    public String getTitle() {
        return title;
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
