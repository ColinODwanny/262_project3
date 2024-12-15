public class Rating {
    int userId;
    int movieId;
    double rating;

    public Rating(int userId, int movieId, double rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Rating{userId=" + userId + ", movieId=" + movieId + ", rating=" + rating + "}";
    }
}
