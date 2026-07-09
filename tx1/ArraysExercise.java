class ArraysExercise {
    String[] arrayA = {
        "dog", "cat", "rat", "fox", "frog",
        "dinosaur", "cow", "bull"
    };

    String[] arrayB = {
        "dog", "dog", "cat", "dog", "frog", "rat", "turtle",
        "fox", "fox", "man", "man", "frog", "man"
    };

    public void compare() {
        int counter = 0;
        int indexB = 0;
        for (int indexA = 0; indexA < arrayA.length; indexA++) {
            for (indexB = 0; indexB < arrayB.length; indexB++) {
                if (arrayA[indexA] == arrayB[indexB]) {
                    counter++;
                }
            } // end for

            if (counter > 0) {
                System.out.println(arrayB[indexB - 1] + " " + counter);
            }
        } // end for
    } // end compare()

    public static void main(String[] args) {
        ArraysExercise a = new ArraysExercise();
        a.compare();
    }
} // end class ArraysExercise