import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PriorityQueue<Long> pq = new PriorityQueue<>(Comparator.reverseOrder());

        final int N = Integer.parseInt(br.readLine());
        long[] min = new long[N + 1], A = new long[N + 1], dp = new long[N+1];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 1; i <= N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= N; i++) {
            if (!pq.isEmpty() && pq.peek() > A[i] - i) {
                pq.offer(A[i] - i);
                pq.poll();
            }
            pq.offer(A[i] - i);
            dp[i] = pq.peek() + i;
        }

        for (int i=N-1;i>=1;i--) {
            dp[i] = Math.min(dp[i], dp[i+1]-1);
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i=1;i<=N;i++) {
            bw.write(dp[i]+"\n");
        }
        bw.flush();

    }
}