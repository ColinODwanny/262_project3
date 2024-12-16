import java.io.*;
import java.util.*;

/**
 * A movie recommendation system that reads data from the MovieLens dataset
 * and provides recommendations based on weighted average ratings.
 * The user can interactively get top movies, search by genre, and search by title.
 * 
 * @author Bakary Ceesay
 * @version 1.0
 */
public class MovieReccom {

    // Movie data structures
    static Map<Integer, Movie> movieMap = new HashMap<>(); // Map to store movies with their movieId as key
    static Map<Integer, List<Rating>> ratingsMap = new HashMap<>(); // Map to store lists of ratings for each movieId
    static List<Movie> movieList = new ArrayList<>(); // List to store all movies

    // Method to load the dataset based on the input (either "small" or "large")
    public static void loadData(String dataset) {
        // Load movies and ratings from files
        try (BufferedReader movieReader = new BufferedReader(new FileReader("movies.csv"));
        BufferedReader ratingReader = new BufferedReader(new FileReader("ratings.csv"))) {

            // Read and process movies.csv
            String movieLine;
            movieReader.readLine();  // Skip header row
            while ((movieLine = movieReader.readLine()) != null) {
                String[] parts = CSVLine(movieLine);  // Parse the movie line into parts
                int movieId = Integer.parseInt(parts[0]);  // Movie ID (parsed as integer)
                String title = parts[1];  // Movie title
                String[] genres = parts[2].split("\\|");  // Movie genres (separated by |)
                List<String> genreList = new ArrayList<>(Arrays.asList(genres));  // Convert genres to a list
                Movie movie = new Movie(movieId, title, genreList);  // Create a new Movie object
                movieMap.put(movieId, movie);  // Add the movie to the movieMap
                movieList.add(movie);  // Add the movie to the movieList
            }

            // Read and process ratings.csv
            String ratingLine;
            ratingReader.readLine();  // Skip header row
            while ((ratingLine = ratingReader.readLine()) != null) {
                String[] parts = ratingLine.split(",");  // Split the rating line into parts
                int userId = Integer.parseInt(parts[0]);  // User ID (parsed as integer)
                int movieId = Integer.parseInt(parts[1]);  // Movie ID (parsed as integer)
                double rating = Double.parseDouble(parts[2]);  // Rating (parsed as double)
                Rating ratingObj = new Rating(userId, movieId, rating);  // Create a new Rating object

                // Get the list of ratings for the movieId from the ratingsMap
                List<Rating> ratings = ratingsMap.get(movieId);

                // If the list of ratings for the movieId doesn't exist, create a new list and add it to the map
                if (ratings == null) {
                    ratings = new ArrayList<>();  // Create a new ArrayList if it doesn't exist
                    ratingsMap.put(movieId, ratings);  // Add the new list to the map with the movieId as key
                }

                // Add the ratingObj to the list of ratings for the movie
                ratings.add(ratingObj);  // Add the rating to the list
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());  // Handle file reading errors
        }
    }

    // Method to compute the weighted average for a movie based on the formula provided
    public static double computeWeightedAverage(Movie movie) {
        List<Rating> ratings = ratingsMap.get(movie.getId());  // Get the list of ratings for the movieId

        // If there are fewer than 15 ratings, return 0 or handle it accordingly
        if (ratings == null || ratings.size() < 15) {
            return 0;  // If there are too few ratings, we return 0
        }

        // Calculate the average rating for the movie
        double totalRating = 0;
        for (Rating rating : ratings) {
            totalRating += rating.getRating();  // Sum up all ratings for the movie
        }
        double averageRating = totalRating / ratings.size();  // Calculate the average rating for the movie

        // Calculate the global average rating (across all movies)
        double globalAverageRating = 0.0;
        int totalMovies = 0;
        for (List<Rating> movieRatings : ratingsMap.values()) {
            for (Rating rating : movieRatings) {
                globalAverageRating += rating.getRating();  // Sum up all ratings across all movies
                totalMovies++;  // Count the number of ratings
            }
        }
        globalAverageRating /= totalMovies;  // Calculate the global average rating across all movies

        // Set the minimum number of ratings required for significance
        int minRatingsThreshold = 15;

        // Calculate the weighted average score based on the provided formula
        double score = (ratings.size() - averageRating) / (ratings.size() + minRatingsThreshold) + ((minRatingsThreshold * globalAverageRating) / (ratings.size() + minRatingsThreshold));

        return score;  // Return the weighted average score
    }

    // Method to display the top N movies
    public static void displayTopN(int n) {
        // Sort the movies based on their weighted average using the MovieComparator class
        movieList.sort(new MovieComparator());

        System.out.println("Top " + n + " Movies:");

        // Loop through the top n movies to display them
        for (int i = 0; i < n && i < movieList.size(); i++) {
            Movie movie = movieList.get(i);  // Get the current movie

            // Compute the weighted average for the movie using the computeWeightedAverage method
            double weightedAvg = computeWeightedAverage(movie);

            // Compute the true average from the ratings of the movie manually (no stream)
            List<Rating> ratings = ratingsMap.get(movie.getId());
            double totalRating = 0.0;
            for (Rating rating : ratings) {
                totalRating += rating.getRating();  // Sum all ratings
            }

            // Calculate the true average using a simple loop
            double trueAvg;
            if (ratings.size() > 0) {
                trueAvg = totalRating / ratings.size();  // Calculate true average
            } else {
                trueAvg = 0.0;  // If no ratings, set true average to 0
            }

            // Get the year from the movie title by manually finding the parentheses
            String title = movie.getTitle();
            int startIdx = title.indexOf('(');
            int endIdx = title.indexOf(')');

            String year = "";
            if (startIdx != -1 && endIdx != -1 && endIdx > startIdx) {
                year = title.substring(startIdx + 1, endIdx);  // Extract the year from the title
            }

            // Build the genres string
            StringBuilder genresString = new StringBuilder();
            List<String> genres = movie.getGenres();
            for (int j = 0; j < genres.size(); j++) {
                genresString.append(genres.get(j));  // Add each genre to the string
                if (j < genres.size() - 1) {
                    genresString.append(", ");  // Add commas between genres
                }
            }

            // Print movie details, including weighted average, true average, year, and genres
            System.out.printf("%d. %.3f (%.3f) %s (%s) [%s]\n",
                i + 1, 
                weightedAvg, 
                trueAvg, 
                movie.getTitle(), 
                year, 
                genresString.toString());  // Print the formatted output
        }
    }

    // Method to handle the CSV line parsing correctly
    public static String[] CSVLine(String line) {
        String[] result = new String[3];
        int firstCommaIndex = line.indexOf(",");
        int lastCommaIndex = line.lastIndexOf(",");

        result[0] = line.substring(0, firstCommaIndex);  // Movie ID
        result[1] = line.substring(firstCommaIndex + 1, lastCommaIndex);  // Title
        result[2] = line.substring(lastCommaIndex + 1);  // Genres
        return result;
    }

    
    public static void searchByGenre() {
    
    }

    
    public static void searchByTitle() {
    
    }
     

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String dataset = "small";
        if (args.length > 0 && args[0].equals("large")) {
            dataset = "large";
        }

        loadData(dataset);  // Load movie and rating data based on dataset choice

        // Display menu
        while (true) {
            System.out.println("Choose from the following menu:");
            System.out.println("1 - See top n");
            System.out.println("2 - See top n in given genre(s)");
            System.out.println("3 - Search movie titles");
            System.out.println("4 - Quit");

            int menuOption = in.nextInt();
            switch (menuOption) {
                case 1:
                    System.out.print("Enter number of top movies to display: ");
                    int topN = in.nextInt();
                    displayTopN(topN);
                    break;
                case 2:
                    //searchByGenre();
                    break;
                case 3:
                    //searchByTitle();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    in.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
