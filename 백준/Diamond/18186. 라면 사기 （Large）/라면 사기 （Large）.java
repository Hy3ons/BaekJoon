import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st= new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long B = Integer.parseInt(st.nextToken()), C = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];

        st = new StringTokenizer(br.readLine()  );
        long sum = 0;
        for (int i=0;i<N;i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        if (B <= C) {
            for (int i=0;i<arr.length;i++) sum += arr[i] * B;
            System.out.println(sum);
            return;
        }

        for (int i=0;i<N;i++) {
            int first = i, second = i+1, third = i + 2;
            if (i + 2 < N) {
                if (arr[second] > arr[third]) {
                    int minus = Math.min(arr[second] - arr[third], arr[first]);
                    sum += minus * ( B+ C);
                    arr[first] -= minus;
                    arr[second] -= minus;
                }

                int minus = min(arr[first], arr[second], arr[third]);
                arr[first] -= minus;
                arr[second] -=minus;
                arr[third] -= minus;
                sum += minus * (B + 2*C);
                sum += arr[first] * B;
            } else if (i + 1 < N) {
                int minus = Math.min(arr[first], arr[second]);
                arr[first] -= minus;
                arr[second] -= minus;
                sum += minus * (B+C);
                sum += arr[first] * B;
            } else {
                sum += arr[first] * B;
            }
        }

        System.out.println(sum);

    }

    public static int min (int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}