import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class MySpellChecker {
    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);
        int userChoice;

        // Add all words from the dictionary and store them in a TreeSet
        TreeSet<String> dictionary = new TreeSet<>();
        BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            dictionary.add(line.trim().toLowerCase());
        }

        // Prepare lists for input words and ignored words
        List<String> wordsToIgnore = new ArrayList<>();

        // Read the input file
        try (BufferedReader myReader = new BufferedReader(new FileReader("input.txt"))) {
            String line2;
            while ((line2 = myReader.readLine()) != null) {
                String[] lineWords = line2.split("\\s+");
                for (String myWord : lineWords) {
                    String cleanWord = wordCleaner(myWord).toLowerCase();
                    if (!cleanWord.isEmpty() && !dictionary.contains(cleanWord) && !wordsToIgnore.contains(cleanWord)) {
                        // Display the line and the misspelled word
                        System.out.println("Line: " + line2);
                        System.out.println("Word not in dictionary: " + cleanWord);

                        // User options
                        System.out.println("What would you like to do with this word?");
                        System.out.println("1: Add it to the dictionary");
                        System.out.println("2: Ignore it");
                        System.out.println("3: Ignore all cases of it");
                        System.out.println("4: Make corrections to the word");

                        userChoice = scnr.nextInt();
                        
                        scnr.nextLine();

                        switch (userChoice) {
                            case 1:
                                dictionary.add(cleanWord);
                                System.out.println("The word " + cleanWord +  " has been added to the dictionary.");
                                break;
                            case 2:
                                System.out.println("Ignoring the word " + cleanWord );
                                break;
                            case 3:
                                wordsToIgnore.add(cleanWord);
                                System.out.println("Ignoring all cases of the word " + cleanWord );
                                break;
                            case 4:
                                System.out.println("Enter the correct word:");
                                String newWord = scnr.nextLine();
                                System.out.println("Replaced " + cleanWord + " with " + newWord );
                                break;
                            default:
                                System.out.println("Please make a valid selection.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot read the file: " + e.getMessage());
        }
    }

    // Method to clean up a word
    public static String wordCleaner(String word) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c) || c == '\'' || c == '-'){
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

