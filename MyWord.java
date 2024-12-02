import java.util.Arrays;

public class MyWord implements Comparable<MyWord> {

    protected char[] myCharArr;

    public MyWord(char[] inputArr) {
        myCharArr = new char[inputArr.length];
        
        //making deep copy of inputArr
        for(int i = 0; i < inputArr.length; i++) {
            myCharArr[i] = Character.toLowerCase(inputArr[i]); //project3.pdf says to not care about upper or lowercase, so all will be lowercase for ease.
        }
    }

    public char[] getCharArr(){
        return myCharArr;
    }

    @Override
    public int compareTo(MyWord comparedWord) {
        int returnValue = 0;
        int originalWordLen = myCharArr.length;
        int comparedWordLen = comparedWord.myCharArr.length;
        int shorterWordLen;
        if(originalWordLen > comparedWordLen) {
            shorterWordLen = comparedWordLen;
        }
        else if(originalWordLen < comparedWordLen) {
            shorterWordLen = originalWordLen;
        }
        else{ //if the two words are of equal length
            shorterWordLen = originalWordLen;
        }

        int iteration = 0;
        while(returnValue == 0 && iteration != shorterWordLen) {
            System.out.println("iteration: " + iteration);

            /*this is the inverse of how a typical compareTo method is writen.  It is
            written this way because the lab says to sort in reverse alphabetical order. */
            if(myCharArr[iteration] < comparedWord.myCharArr[iteration]){ 
                returnValue = 1;
            }
            else if(myCharArr[iteration] > comparedWord.myCharArr[iteration]) {
                returnValue = -1;
            }
            else{ returnValue = 0; }

            iteration++;
        }
        return returnValue;
}

    public String toString(){
        return new String(myCharArr);
    }

    //I think this needs to change to public boolean equals( Object o ) for HashSet.contains() to work properly 
    public boolean equals(MyWord otherWord){
        boolean returnVal;
        if(myCharArr.length == otherWord.myCharArr.length) { //checks if words are same length
            returnVal = true;
            for(int i = 0; i < myCharArr.length; i++) {
                if(myCharArr[i] != otherWord.myCharArr[i]) {
                    returnVal = false;
                }
            }
        } 
        else {
            returnVal = false;
        }
        return returnVal;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(myCharArr);
        return result;
    }

}