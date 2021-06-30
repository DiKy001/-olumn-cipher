import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

// Column method
public class Program {

    static Scanner in = new Scanner(System.in);

    static void printMatrix(int[][] table) {
        for (int[] arr : table) {
            System.out.print(arr[0] + "\t");
        }
        System.out.println();
        for (int j = 1; j < table[0].length; j++) {
            for (int[] arr : table) {
                System.out.print((char)arr[j] + "\t");
            }
            System.out.println();
        }
    }

    static void printMatrix(char[][] table) {
        for (int j = 0; j < table[0].length; j++) {
            for (char[] chars : table)
                System.out.print(chars[j] + "\t");
            System.out.println();
        }
    }

    static void sortTable(char[][] table) {
        Arrays.sort(table, new Comparator<char[]>() {
            @Override
            public int compare(char[] o1, char[] o2) {
                return Character.compare(o1[0],o2[0]);
            }
        });
    }

    static void sortTable(int[][] table) {
        Arrays.sort(table, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });
    }

    static String getEncryptSting (char[][] table) {
        String c = "";
        int n;
        for (char[] chars : table) {
            n = 1;
            while (n < table[0].length && chars[n] != 0) {
                c = c.concat(String.valueOf(chars[n]));
                n++;
            }
        }
        return c;
    }

    static String encrypt (String m, String k){
        char[][] table = new char[k.length()][(int)Math.ceil((double) m.length() / k.length()) + 1];
        for (int i = 0; i < table.length; i++)
            table[i][0] = k.charAt(i);
        for (int j = 1; j < table[0].length; j++)
            for (int i = 0; i < table.length; i++) {
                if (i + table.length * (j - 1) < m.length())
                    table[i][j] = m.charAt(i + table.length * (j - 1));
                else {
                    System.out.println();
                    System.out.println("Crypt table");
                    printMatrix(table);

                    sortTable(table);

                    System.out.println("\nSort crypt table");
                    printMatrix(table);
                    System.out.println();

                    return getEncryptSting(table);
                }
            }
        return "";
    }

    static int[] sortPosString (String s) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        int[] pos = new int[arr.length];
        for (int i = 0; i < pos.length; i++) {
            for (int j = 0; j < s.length(); j++)
                if (arr[i] == s.charAt(j)) {
                    pos[i] = j;
                    s = s.substring(0, j).concat(" ").concat(s.substring(j + 1));
                    break;
                }
        }
        return pos;
    }

    static String getInitialString(String[] s, int[] pos, String k, int lastRow, int n) {

        int[][] table = new int[pos.length][];
        for (int i = 0; i < pos.length; i++) {
            table[i] = new int[n + 1];
            for (int j = 0; j < s[i].length(); j++) {
                table[i][j + 1] = s[i].charAt(j);
            }
        }
        for (int i = 0; i < pos.length; i++) {
            table[i][0] = pos[i];
        }
        System.out.println();
        System.out.println("Crypt table");
        printMatrix(table);
        System.out.println("\n");

        sortTable(table);

        System.out.println("Decrypt table");
        printMatrix(table);
        System.out.println("\n");

        String init = "";
        for (int j = 1; j < table[0].length - 1; j++)
            for (int[] ints : table)
                init = init.concat(String.valueOf((char) ints[j]));
        for (int i = 0; i < lastRow; i++)
            init = init.concat(String.valueOf((char)table[i][table[0].length - 1]));
        return init;
    }

    static String findSplitChar(String s) {
        for (int i = 1; i < Character.MAX_VALUE; i++)
            if (!s.contains(String.valueOf((char)i)))
                return String.valueOf((char)i);
        return " ";
    }

    static String decrypt (String c, String k){
        int lastRow = c.length() % k.length();
        int[] pos = sortPosString(k);
        int index = 0;
        int n = (int)Math.ceil((double) c.length() / k.length());
        String split = findSplitChar(c);
        for (int i = 0; i < k.length(); i++) {
            index += pos[i] < lastRow ? n: n - 1;
            c = c.substring(0, index).concat(split).concat(c.substring(index));
            index++;
        }
        return getInitialString(c.split(split), pos, k, lastRow, n);
    }

    public static void main(String[] args) {
        System.out.println("Enter the string, required to encryption, and the encryption key");
        System.out.println("Encrypt string\n" + encrypt(in.nextLine(), in.nextLine()));
        System.out.println("\nEnter the string, required to decryption, and the decryption key");
        System.out.println("Decrypt string\n" + decrypt(in.nextLine() , in.nextLine()));
    }
}
