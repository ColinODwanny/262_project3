// Rating class
class Rating {
    private int userId;
    private int movieId;
    private double rating;

    public Rating(int userId, int movieId, double rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieId() {
        return movieId;
    }
}
