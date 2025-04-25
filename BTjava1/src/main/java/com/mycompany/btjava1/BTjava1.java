/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.btjava1;

import java.util.*;
/**
 *
 * @author PC
 */
public class BTjava1 {


public class JavaExercises {
    
    // Bài 1: Kiểm tra số nguyên tố
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
    
    // Bài 2: Giải phương trình bậc 2
    public static void solveQuadraticEquation(double a, double b, double c) {
        double delta = b * b - 4 * a * c;
        if (delta > 0) {
            double x1 = (-b + Math.sqrt(delta)) / (2 * a);
            double x2 = (-b - Math.sqrt(delta)) / (2 * a);
            System.out.println("Nghiệm: x1 = " + x1 + ", x2 = " + x2);
        } else if (delta == 0) {
            double x = -b / (2 * a);
            System.out.println("Nghiệm kép: x = " + x);
        } else {
            System.out.println("Nghiệm phức");
        }
    }
    
    // Bài 3: Tính giai thừa n!
    public static long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    // Bài 4: Tính C(m, n)
    public static long combination(int m, int n) {
        if (n > m) return 0;
        return factorial(m) / (factorial(n) * factorial(m - n));
    }
    
    // Bài 5: Tìm UCLN của hai số
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    // Bài 6: Kiểm tra số nguyên tố (lặp lại bài 1)
    
    // Bài 7: Tìm kiếm nhị phân
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
    
    // Bài 8: Chèn phần tử vào mảng đã sắp xếp
    public static int[] insertSorted(int[] arr, int value) {
        int[] newArr = new int[arr.length + 1];
        int i = 0;
        while (i < arr.length && arr[i] < value) {
            newArr[i] = arr[i];
            i++;
        }
        newArr[i] = value;
        while (i < arr.length) {
            newArr[i + 1] = arr[i];
            i++;
        }
        return newArr;
    }
    
    // Bài 9: Tìm các mảng con có tổng bằng giá trị cho trước
    public static void findSubarraysWithSum(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                if (sum == target) {
                    System.out.println("Mảng con: " + Arrays.toString(Arrays.copyOfRange(arr, i, j + 1)));
                }
            }
        }
    }
    
    // Bài 10: Đưa số nguyên tố lên đầu và sắp xếp
    public static void movePrimesToFront(int[] arr) {
        List<Integer> primes = new ArrayList<>(), others = new ArrayList<>();
        for (int num : arr) {
            if (isPrime(num)) primes.add(num);
            else others.add(num);
        }
        Collections.sort(primes);
        Collections.sort(others);
        primes.addAll(others);
        System.out.println("Mảng sau khi sắp xếp: " + primes);
    }
    // Bài 11: Tìm số nguyên tố lớn nhất trong mảng hai chiều
    public static int findLargestPrimeInMatrix(int[][] matrix) {
        int maxPrime = -1;
        for (int[] row : matrix) {
            for (int num : row) {
                if (isPrime(num) && num > maxPrime) {
                    maxPrime = num;
                }
            }
        }
        return maxPrime;
    }
    
    // Bài 12: Nhân hai ma trận
    public static int[][] multiplyMatrices(int[][] a, int[][] b) {
        int rows = a.length, cols = b[0].length, common = b.length;
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < common; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
    
    // Bài 13: Sao chép mảng n chiều
    public static int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }
        return copy;
    }
    
    // Bài 14: Đếm số ký tự khác nhau trong chuỗi
    public static int countUniqueCharacters(String str) {
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : str.toCharArray()) {
            uniqueChars.add(c);
        }
        return uniqueChars.size();
    }
    
    // Bài 15: Tìm ký tự có tần suất xuất hiện lớn nhất
    public static Set<Character> findMostFrequentChars(String str) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int maxFreq = 0;
        for (char c : str.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            maxFreq = Math.max(maxFreq, frequencyMap.get(c));
        }
        Set<Character> result = new HashSet<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFreq) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
    
    // Bài 16: Đếm số từ trong chuỗi
    public static int countWords(String str) {
        return str.trim().isEmpty() ? 0 : str.trim().split("\\s+").length;
    }
    
    // Bài 17: Viết hoa ký tự đầu mỗi từ
    public static String capitalizeWords(String str) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        return result.toString().trim();
    }
    
    // Bài 18: So sánh sự giống nhau giữa hai chuỗi
    public static boolean areStringsEqual(String str1, String str2) {
        return str1.equals(str2);
    }
    
    // Bài 19: Cắt một số lượng từ trong chuỗi
    public static String truncateWords(String str, int wordCount) {
        String[] words = str.split("\\s+");
        return String.join(" ", Arrays.copyOfRange(words, 0, Math.min(words.length, wordCount)));
    }
    
}
}
