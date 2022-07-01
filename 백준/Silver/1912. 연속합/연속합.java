import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[testcase]
                , left = new int[testcase]
                , right = new int[testcase];

        for (int i=0;i<testcase;i++)
            arr[i] = Integer.parseInt(st.nextToken());
        left[0] = arr[0];
        right[right.length-1] = arr[arr.length-1];

        for (int i=1;i<testcase;i++) {
            left[i] = Math.max(arr[i], arr[i]+left[i-1]);
            right[right.length-1-i] = Math.max(arr[right.length-1-i]
                    , arr[right.length-1-i] + right[right.length-i]);
        }

        int count = Integer.MIN_VALUE;

        for (int i=0;i<arr.length;i++) {
            count = Math.max(count, left[i]+right[i]-arr[i]);
        }

        System.out.println(count);
    }
}