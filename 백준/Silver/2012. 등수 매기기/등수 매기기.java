import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i=0;i<N;i++) pq.offer(Integer.parseInt(br.readLine()));

        long res = 0;
        for (int i=1;i<=N;i++) {
            res += Math.abs(pq.poll() - i);
        }

        System.out.println(res);
    }
}