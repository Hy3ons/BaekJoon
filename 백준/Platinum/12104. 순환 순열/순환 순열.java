import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] a = br.readLine().toCharArray();
        String temp = br.readLine();
        char[] b = (temp+temp).toCharArray();

        int[] fail = failureF(a);

        int count = 0;
        int point = 0;

        K: for (int i=1;i<b.length;i++) {
            while (point > 0 && a[point]!=b[i]) {
                point = fail[point - 1];
            }
            if (a[point]==b[i]) {
                if (point==a.length-1) {
                    count++;
                    point = fail[fail.length-1];
                } else {
                    point++;
                }
            }
        }

        System.out.println(count);
    }

    public static int[] failureF (char[] arr) {
        int[] result = new int[arr.length];
        int j = 0;

        for (int i=1;i<arr.length;i++) {

            while (j>0 && arr[j]!=arr[i]) {
                j = result[j-1];
            }

            if (arr[i]==arr[j]) {
                result[i] = ++j;
            }
        }
        return result;
    }
}
