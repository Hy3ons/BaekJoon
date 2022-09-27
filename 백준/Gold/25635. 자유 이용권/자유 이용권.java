import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i=0;i<n;i++) arr[i] = Integer.parseInt(st.nextToken());

        Arrays.sort(arr);
        long sum = 0;
        for (int i=0;i<n-1;i++) sum += arr[i];

        long max = arr[n-1];

        sum = sum + Math.min(sum, max-1) + 1;
        System.out.println(sum);
    }

}
