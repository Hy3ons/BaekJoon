import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.function.IntPredicate;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[N];

        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());
        int[] left = new int[N], right = new int[N];

        left[0] = Integer.MIN_VALUE;

        int min = arr[0];
        for (int i=1;i<N;i++) {
            left[i] = Math.max(left[i-1], arr[i] - min);
            min = Math.min(min, arr[i]);
        }

        int max = arr[arr.length-1];
        right[N-1] = Integer.MIN_VALUE;
        for (int i=N-2;i>=0;i--) {
            right[i] = Math.max(right[i+1], max - arr[i]);
            max = Math.max(max, arr[i]);
        }

        int ans = Integer.MIN_VALUE;

        for (int i=1;i<N-2;i++) {
            ans = Math.max(ans, left[i] + right[i+1]);
        }

        System.out.println(ans);
    }
}