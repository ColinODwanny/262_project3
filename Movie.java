import java.util.List;

public class Movie {
    private int id;
    private String title;
    private List<String> genres;

    public Movie(int id, String title, List<String> genres) {
        this.id = id;
        this.title = title;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenres() {
        return genres;
    }
}
