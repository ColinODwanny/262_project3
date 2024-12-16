mport java.util.Comparator;

public class MovieComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        double wAvg1 = MovieReccom.computeWeightedAverage(m1);
        double wAvg2 = MovieReccom.computeWeightedAverage(m2);
        return Double.compare(wAvg2, wAvg1);  // Sort in descending order by weighted average
    }
}
