import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PriorityQueue<Long> pq = new PriorityQueue<>(Comparator.reverseOrder());

        final int N = Integer.parseInt(br.readLine());
        long[] min = new long[N + 1], A = new long[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 1; i <= N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        long mn = 0;
        long diff = 0;

        for (int i = 1; i <= N; i++, diff++) {
            if (pq.isEmpty() || pq.peek()<= A[i] - diff) {
                min[i] = min[i - 1];
            } else {
                min[i] = min[i-1] + pq.peek() - A[i] + diff;
                pq.offer(A[i] - diff);
                pq.poll();
            }

            pq.offer(A[i] - diff);
        }

        System.out.println(min[N]);
    }
}