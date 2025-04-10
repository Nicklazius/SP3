public class Media {

    protected String title;
    protected int year;
    protected double rating;
    protected String runTime;
    protected int season;
    protected int episode;
    protected String[] genre = {"Action", "Drama", "Mystery", "Adventure", "Thriller", "Comedy", "History", "Animation", "Sci-fi", "Crime",
            "Biography", "Family", "Romance", "Talkshow", "Documentary", "Horror", "War", "Sport", "Film-Noir", "Musical", "Western", "Music", "Fantasy",};

    public Media(String title, int year, String[] genre, double rating, String runTime, int season, int episode) {

        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
        this.runTime = runTime;
        this.season = season;
        this.episode = episode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getGenre() {
        return genre;
    }
}

