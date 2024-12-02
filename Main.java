import java.util.*;

public class Main{
    public static void main(String args[]) {
        List<MyWord> meep = new ArrayList<MyWord>();

        char[] wordOne = {'c', 'b', 'c'};
        MyWord word1 = new MyWord(wordOne);

        char[] wordTwo = {'b', 'a', 'a'};
        MyWord word2 = new MyWord(wordTwo);

        char[] wordThree = {'c', 'a', 't'};
        MyWord word3 = new MyWord(wordThree);

        char[] wordFour = {'c', 'a', 't'};
        MyWord word4 = new MyWord(wordFour);

        meep.add(word2);
        meep.add(word1);
        meep.add(word3);

        System.out.println(word3.equals(word4));
        System.out.println(word3.hashCode() == word4.hashCode());

    }
}