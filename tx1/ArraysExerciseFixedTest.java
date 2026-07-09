package tx1;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ArraysExerciseFixedTest {

    @Test
    public void testNormalCase() {
        ArraysExerciseFixed aef = new ArraysExerciseFixed();
        String[] arrayA = {
                "dog", "cat", "rat", "fox", "frog",
                "dinosaur", "cow", "bull"
        };
        String[] arrayB = {
                "dog", "dog", "cat", "dog", "frog", "rat", "turtle",
                "fox", "fox", "man", "man", "frog", "man"
        };

        Map<String, Integer> result = aef.compare(arrayA, arrayB);

        System.out.println("testNormalCase result: " + result);

        assertEquals(5, result.size());
        assertEquals(3, result.get("dog"));
        assertEquals(1, result.get("cat"));
        assertEquals(1, result.get("rat"));
        assertEquals(2, result.get("fox"));
        assertEquals(2, result.get("frog"));
        assertNull(result.get("dinosaur"));
    }

    @Test
    public void testEmptyArrays() {
        ArraysExerciseFixed aef = new ArraysExerciseFixed();
        String[] arrayA = {};
        String[] arrayB = {};

        Map<String, Integer> result = aef.compare(arrayA, arrayB);

        System.out.println("testEmptyArrays result: " + result);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testEmptyArrayA() {
        ArraysExerciseFixed aef = new ArraysExerciseFixed();
        String[] arrayA = {};
        String[] arrayB = {"dog", "cat"};

        Map<String, Integer> result = aef.compare(arrayA, arrayB);

        System.out.println("testEmptyArrayA result: " + result);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testEmptyArrayB() {
        ArraysExerciseFixed aef = new ArraysExerciseFixed();
        String[] arrayA = {"dog", "cat"};
        String[] arrayB = {};

        Map<String, Integer> result = aef.compare(arrayA, arrayB);

        System.out.println("testEmptyArrayB result: " + result);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testNullArrays() {
        ArraysExerciseFixed aef = new ArraysExerciseFixed();

        Map<String, Integer> result1 = aef.compare(null, new String[]{"dog"});
        System.out.println("testNullArrays result1: " + result1);
        assertTrue(result1.isEmpty());

        Map<String, Integer> result2 = aef.compare(new String[]{"dog"}, null);
        System.out.println("testNullArrays result2: " + result2);
        assertTrue(result2.isEmpty());

        Map<String, Integer> result3 = aef.compare(null, null);
        System.out.println("testNullArrays result3: " + result3);
        assertTrue(result3.isEmpty());
    }

    @Test
    public void testNullElements() {
        ArraysExerciseFixed aef = new ArraysExerciseFixed();
        String[] arrayA = {"dog", null, "cat"};
        String[] arrayB = {"dog", null, "fox", "cat"};

        // The implementation skips null elements in arrayA comparisons.
        Map<String, Integer> result = aef.compare(arrayA, arrayB);

        System.out.println("testNullElements result: " + result);

        assertEquals(2, result.size());
        assertEquals(1, result.get("dog"));
        assertEquals(1, result.get("cat"));
    }

    @Test
    public void testNoMatches() {
        ArraysExerciseFixed aef = new ArraysExerciseFixed();
        String[] arrayA = {"lion", "tiger"};
        String[] arrayB = {"dog", "cat"};

        Map<String, Integer> result = aef.compare(arrayA, arrayB);

        System.out.println("testNoMatches result: " + result);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testAllMatches() {
        ArraysExerciseFixed aef = new ArraysExerciseFixed();
        String[] arrayA = {"dog", "cat"};
        String[] arrayB = {"dog", "cat", "cat", "dog", "dog"};

        Map<String, Integer> result = aef.compare(arrayA, arrayB);

        System.out.println("testAllMatches result: " + result);

        assertEquals(2, result.size());
        assertEquals(3, result.get("dog"));
        assertEquals(2, result.get("cat"));
    }
}
