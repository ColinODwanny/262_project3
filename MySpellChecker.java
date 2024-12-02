import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MySpellChecker{

    public static void spellChecker(MyWord inputWord) {
        File source;
        Scanner scan = new Scanner(System.in);
        source = new File("meep.txt");
        
        try {
            scan = new Scanner(source);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        HashSet<MyWord> setOfWords = new HashSet<>();
        while(scan.hasNext()){
            MyWord test = new MyWord(scan.next().toCharArray());
            System.out.println(test.hashCode());
            setOfWords.add(test);
        }

        System.out.println(inputWord.hashCode());
        if(!setOfWords.contains(inputWord)) {

            System.out.println(setOfWords);

            System.out.println("this word is not in the dictionary, would you like to add it?");
        }
    }
}