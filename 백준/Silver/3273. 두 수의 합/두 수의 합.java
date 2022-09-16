import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];

        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        Arrays.sort(arr);
        int goal = Integer.parseInt(br.readLine());
        int result = 0;
        for (int i = 0, j = arr.length-1;i < j;) {
            int temp = arr[i] + arr[j];

            if (temp < goal) {
                i++;
            } else if (temp == goal) {
                i++;
                j--;
                result++;
            } else {
                j--;
            }
        }

        System.out.println(result);
    }
}
