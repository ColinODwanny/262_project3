import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * A simple spell checker that checks the spelling of words in a given input file.
 * It uses a dictionary file for correct words and allows the user to add, ignore,
 * or correct misspelled words interactively.
 * 
 * @author Bakary and colin
 */
public class MySpellChecker {
    /**
     * Main method that handles the spell checking of the input file.
     * 
     * @param args Command-line arguments: 1) Dictionary file path, 2) Input file path
     * @throws IOException if there is an error reading or writing files
     */
    public static void main(String[] args) throws IOException {
        // Ensure both dictionary and input files are provided
        if (args.length != 2) {
            System.out.println("please enter an input aand output file path");
            return;
        }

        String dictionaryFile = args[0];
        String inputFile = args[1];

        // Use a TreeSet to store words as MyWord objects for efficient lookup
        TreeSet<MyWord> dictionary = new TreeSet<>();
        
        // Reading the dictionary file and adding words to the dictionary
        BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile));
        String line;
        //Trimming it to remove white space before and after the word
        while ((line = reader.readLine()) != null) {
            dictionary.add(new MyWord(line.trim()));  // Add words as MyWord objects
        }
        reader.close();

        // List to store ignored words
        List<String> ignore = new ArrayList<>();

        // Handle input file based on its format URL or local
        BufferedReader myReader;

        // Check if the input is a URL or local file using indexOf
        if (inputFile.indexOf("http://") != -1 || inputFile.indexOf("https://") != -1) {
            // Read from the web if the input file is a URL
            URL url = new URL(inputFile);
            myReader = new BufferedReader(new InputStreamReader(url.openStream()));
        } else {
            // Read from a local file
            myReader = new BufferedReader(new FileReader(inputFile));
        }

        // StringBuilder to store the corrected text
        StringBuilder correctedText = new StringBuilder();
        String line2;
        int lineNumber = 0;  // To track line numbers
        Scanner scnr = new Scanner(System.in);

        // Process each line of the input file
        while ((line2 = myReader.readLine()) != null) {
            lineNumber++;  // Increment the line number for each line read
            correctedText.append(line2).append("\n");

            // Split the line into words and check for spelling errors
            String[] lineWords = line2.split("\\s+");
            for (String myWord : lineWords) {
                String cleanWord = wordCleaner(myWord).toLowerCase();
                // If the word is not in the dictionary and is not ignored
                if (!cleanWord.isEmpty() && !dictionary.contains(new MyWord(cleanWord)) && !ignore.contains(cleanWord)) {
                    // Display the line number and the misspelled word
                    System.out.println("Line " + lineNumber + ": " + line2);
                    System.out.println("Word not in dictionary: " + cleanWord);

                    // User options
                    System.out.println("What would you like to do with this word?");
                    System.out.println("1: Add it to the dictionary");
                    System.out.println("2: Ignore it");
                    System.out.println("3: Ignore all cases of it");
                    System.out.println("4: Make corrections to the word");

                    int userChoice = scnr.nextInt();
                    scnr.nextLine();  // Consume the newline character

                    // Process the user's choice
                    switch (userChoice) {
                        case 1:
                            dictionary.add(new MyWord(cleanWord));
                            System.out.println("The word " + cleanWord + " has been added to the dictionary.");
                            break;
                        case 2:
                            System.out.println("Ignoring the word " + cleanWord);
                            break;
                        case 3:
                            ignore.add(cleanWord);
                            System.out.println("Ignoring all cases of the word " + cleanWord);
                            break;
                        case 4:
                            System.out.println("Enter the correct word:");
                            String newWord = scnr.nextLine();
                            line2 = line2.replace(cleanWord, newWord);  // Replace the misspelled word
                            System.out.println("Replaced " + cleanWord + " with " + newWord);
                            break;
                        default:
                            System.out.println("Please make a valid selection.");
                    }
                }
            }
        }
        myReader.close();

        // Update the dictionary file if any new words were added
        BufferedWriter writer = new BufferedWriter(new FileWriter(dictionaryFile));
        for (MyWord word : dictionary) {
            writer.write(word.getWord());  // Write the word back to the dictionary
            writer.newLine();  // Adds a newline after each word
        }
        writer.close();

        // If the input file is local, write the corrected text back to the file
        if (inputFile.indexOf("http://") == -1 && inputFile.indexOf("https://") == -1) {
            BufferedWriter outputFileWriter = new BufferedWriter(new FileWriter(inputFile));
            outputFileWriter.write(correctedText.toString());
            outputFileWriter.close();
        }

        System.out.println("Spell check completed.");
    }

    /**
     * Cleans up a word by removing punctuation and special characters.
     * Only letters, apostrophes, and hyphens are kept.
     * 
     * @param word The word to clean
     * @return A cleaned word with unwanted characters removed
     */
    public static String wordCleaner(String word) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c) || c == '\'' || c == '-') {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

