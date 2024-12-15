import java.util.List;
/**
 * Write a description of class movies here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class Movie {
    private int id;
    private String title;
    private List<String> genres;  // Genres could be a list, e.g., ["Action", "Drama"]

    public Movie(int id, String title, List<String> genres) {
        this.id = id;
        this.title = title;
        this.genres = genres;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        StringBuilder genresString = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            genresString.append(genres.get(i));
            if (i < genres.size() - 1) { // Add a comma and space if it's not the last genre
                genresString.append(", ");
            }
        }
        return title + " (" + genresString + ")";
    }

}
