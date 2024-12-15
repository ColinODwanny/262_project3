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

    //I am Unsure if these should be a static variables or not
    static LinkedList<Integer> commaIndexes = new LinkedList<>();
    static String[] PartsOfMovie = new String[3];
    static char comma = ',';
    
    
        public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            // Default dataset is small
            String dataset = "small";
    
            // Check if the command-line argument is "large" to switch to a larger dataset
            if (args.length > 0 && args[0].equals("large")) {
                dataset = "large";  // Change dataset to "large" if the command-line argument is "large"
            }
    
            // Call the loadData method with the selected dataset
            loadData(dataset);
            

            //First menu
            System.out.println("Choose from the following menu:");
            System.out.println("  1 - See top n");
            System.out.println("  2 - See top n in given genre(s)");
            System.out.println("  3 - search movie titles");
            System.out.println("  4 - quit");

            //This loop ensures that the user enters an int that is between 1 (inclusive) and 4 (inclusive)
            int menuOneOptionInt = 0;
            boolean keepLooping = true;
            while(keepLooping){
                String menuOneOption = in.next();
                try { 
                    menuOneOptionInt = Integer.parseInt(menuOneOption);
                    if (menuOneOptionInt > 0 && menuOneOptionInt < 5) {
                        keepLooping = false;
                    } else {
                        System.out.println("Please enter a valid integer (1 - 4)");
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter a valid integer (1 - 4)");
                }
            }

            if (menuOneOptionInt == 1) {
                int topN = 0;
                keepLooping = true;
                while(keepLooping) {
                    try {
                        System.out.print("n: ");
                        topN = in.nextInt();
                        keepLooping = false;
                    } catch (InputMismatchException ime) {
                        System.out.println("Please enter a valid integer");
                        //clears out buffer
                        in.nextLine();
                    }
                }
                //optionOne(topN);

                
            }
        }
    
        // Method to load the dataset based on the input (either "small" or "large")
        public static void loadData(String dataset) {
            // Create a map to store movies by their movie ID
            Map<Integer, Movie> movieMap = new HashMap<>();
    
            // Create a list to store ratings
            List<Rating> ratingsList = new ArrayList<>();
    
            // Load movies from the movies.csv file
            try (BufferedReader movieReader = new BufferedReader(new FileReader("small_movies.csv"));
            BufferedReader ratingReader = new BufferedReader(new FileReader(dataset + "/ratings.csv"));) {

                String ratingLine;

                //This gets rid of the table headings because if we pass "userId" into parseInt, it'll break
                ratingReader.readLine();
                while ((ratingLine = ratingReader.readLine()) != null) {
                    // Split the line into parts (userId, movieId, and rating)
                    String[] parts = ratingLine.split(",");
    
                    // Parse the userId, movieId, and rating values
                    
                    int userId = Integer.parseInt(parts[0]);
                    int movieId = Integer.parseInt(parts[1]);
                    double rating = Double.parseDouble(parts[2]);
    
                    // Create a Rating object and add it to the ratingsList
                    ratingsList.add(new Rating(userId, movieId, rating));
                }
                String movieLine;

                /*This gets rid of the first line because it has the table headings. 
                if we pass the table heading "movie" into ParseInt, it is going to throw an error*/ 
                movieReader.readLine();

                while ((movieLine = movieReader.readLine()) != null) {
                    // Parse the current CSV line into parts using the CSVLine method
                    String[] parts = CSVLine(movieLine);
    
                    // Get the movie ID, title, and genre from the parsed parts
                    int movieId = Integer.parseInt(parts[0]);
                    String title = parts[1];
    
                    String[] genres = parts[2].split("\\|"); 
    
                    // Create a list of genres for the current movie
                    List<String> genreList = new ArrayList<>(Arrays.asList(genres));
    
                    // Create a Movie object and store it in the movieMap with the movieId as the key

                    //movieMap.put(movieId, new Movie(movieId, title, genreList));
                }
            } catch (IOException e) {
                // If the movies.csv file can't be found or read, print an error message
                System.out.println("Can't find movies.csv");
            }
            /* 
            // Load ratings from the ratings.csv file based on the specified dataset
            try (BufferedReader ratingReader = new BufferedReader(new FileReader(dataset + "/ratings.csv"))) {
                String line;

                //This gets rid of the table headings because if we pass "userId" into parseInt, it'll break
                ratingReader.readLine();
                while ((line = ratingReader.readLine()) != null) {
                    // Split the line into parts (userId, movieId, and rating)
                    String[] parts = line.split(",");
    
                    // Parse the userId, movieId, and rating values
                    
                    int userId = Integer.parseInt(parts[0]);
                    int movieId = Integer.parseInt(parts[1]);
                    double rating = Double.parseDouble(parts[2]);
    
                    // Create a Rating object and add it to the ratingsList
                    ratingsList.add(new Rating(userId, movieId, rating));
                }
            } catch (IOException e) {
                // If the ratings.csv file can't be found or read, print an error message
                System.out.println("This file DOES NOT exist ");
            }
                */
        }
    
        
        /*
        // Helper method to handle CSV lines (quoted values, commas).
        // It is incomplete 
         */
        /*
        public static String[] CSVLine(String line) {
        // List to store the resulting fields from the CSV line
        List<String> result = new ArrayList<>();
    
        // Boolean flag to indicate whether we are inside quotes or not
        boolean inQuotes = false;
    
        // StringBuilder to accumulate characters for the current field
        StringBuilder current = new StringBuilder();
    
        // Loop through each character in the line
        for (char ch : line.toCharArray()) {
        // Check if we encounter a quote character (not escaped by a backslash)
        if (ch == '"' && (current.length() == 0 || current.charAt(current.length() - 1) != '\')) {
        // Toggle the inQuotes flag when encountering an unescaped quote
        inQuotes = !inQuotes;
        }
    
        }
    
    }*/
    
        public static String[] CSVLine(String line){
            /*commaIndexes is going to hold the indexes of all the commas in the line.
            Then we are going to split the line on the first and last comma using peekFirst() and peakLast().
            first we clear the commaIndexes because otherwise it will be holding values from the last line*/
            commaIndexes.clear();
    
            if (line != null) {
                for(int i = 0; i < line.length(); i++){
                    if (line.charAt(i) == comma) {
                        commaIndexes.add(i);
                    }
                }
            }
        PartsOfMovie[0] = line.substring(0, commaIndexes.peekFirst());
        PartsOfMovie[1] = line.substring(commaIndexes.peekFirst() + 1, commaIndexes.peekLast());
        PartsOfMovie[2] = line.substring(commaIndexes.peekLast() + 1);

        return PartsOfMovie;
    }

    public void optionOne(int n) {

    }
}

