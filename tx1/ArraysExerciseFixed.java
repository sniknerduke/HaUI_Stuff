package tx1;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArraysExerciseFixed {
    public Map<String, Integer> compare(String[] arrayA, String[] arrayB) {
        Map<String, Integer> results = new LinkedHashMap<>();

        if (arrayA == null || arrayB == null) {
            return results;
        }

        for (int indexA = 0; indexA < arrayA.length; indexA++) {
            int counter = 0;
            for (int indexB = 0; indexB < arrayB.length; indexB++) {
                if (arrayA[indexA] != null && arrayA[indexA].equals(arrayB[indexB])) {
                    counter++;
                }
            } // end for

            if (counter > 0) {
                results.put(arrayA[indexA], counter);
            }
        } // end for

        return results;
    } // end compare()

    public void printCompare(String[] arrayA, String[] arrayB) {
        Map<String, Integer> results = compare(arrayA, arrayB);
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        String[] arrayA = {
            "dog", "cat", "rat", "fox", "frog",
            "dinosaur", "cow", "bull"
        };

        String[] arrayB = {
            "dog", "dog", "cat", "dog", "frog", "rat", "turtle",
            "fox", "fox", "man", "man", "frog", "man"
        };

        ArraysExerciseFixed a = new ArraysExerciseFixed();
        a.printCompare(arrayA, arrayB);
    }
}
