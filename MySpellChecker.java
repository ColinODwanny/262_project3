import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MySpellChecker{

    public static void spellChecker(MyWord inputWord) {
        File source;
        //FileWriter w;
        Scanner scanForFile = new Scanner(System.in); //scanForFile will point to a file later instead of System.in.  I just needed to initialize it
        Scanner in = new Scanner(System.in); //used to take in input
        source = new File("dictionary.txt");

        /*try{
        w = new FileWriter(source); //used to write new lines into dicitonary.txt
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }*/
        
        try {
            scanForFile = new Scanner(source);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        HashSet<MyWord> setOfWords = new HashSet<>();

        while(scanForFile.hasNext()){ //Scans through every line in dictionary.txt, and puts word in HashSet
            MyWord wordToAdd = new MyWord(scanForFile.next().toCharArray());
            setOfWords.add(wordToAdd);
        }
        if(!setOfWords.contains(inputWord)) {
            int selection = -1; //Initialized to -1 so it will enter the while loop
            while(selection > 3 || selection < 0){
                System.out.println("this word is not in the dictionary");
                System.out.println("Press 1 to reenter the word");
                System.out.println("Press 2 to ignore");
                System.out.println("Press 3 to add word to dictionary");
                selection = in.nextInt();
            }
            if(selection == 1) {
                // TO DO  
            }
            else if(selection == 2) {

            }
            else if(selection == 3) {
                try{
                    FileWriter w = new FileWriter(source, true); //used to write new lines into dicitonary.txt
                    w.write("\n");
                    w.write(inputWord.myCharArr);
                    w.close();
                    }catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
            }
        }
    }
}