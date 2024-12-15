/**
 * Write a description of class movies here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class Movie {
    private int id;
    private String title;
    private String[] genres;  // Genres could be a list, e.g., ["Action", "Drama"]
    private double rating;
    private double weightedRating;

    public Movie(int id, String title, String[] genres) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.rating = rating;
        
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String[] getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        StringBuilder genresString = new StringBuilder();
        for (int i = 0; i < genres.length; i++) {
            genresString.append(genres[i]);
            if (i < genres.length - 1) { // Add a comma and space if it's not the last genre
                genresString.append(", ");
            }
        }
        return title + " (" + genresString + ")";
    }

}
