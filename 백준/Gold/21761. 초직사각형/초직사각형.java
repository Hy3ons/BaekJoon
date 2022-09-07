import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static PriorityQueue<Long>[] pq = new PriorityQueue[4];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        final int N = Integer.parseInt(st.nextToken()), K = Integer.parseInt(st.nextToken());
        long[] now = new long[4];
        st = new StringTokenizer(br.readLine());

        for (int i=0;i<4;i++) {
            now[i] = Long.parseLong(st.nextToken());
        }

        for (int i=0;i<4;i++) pq[i] = new PriorityQueue<>(Comparator.reverseOrder());

        for (int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            char type = st.nextToken().charAt(0);
            long value = Long.parseLong(st.nextToken());

            pq[type - 'A'].offer(value);
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i=0;i<K;i++) {
            char card = 0;
            double temp = -1;

            for (int j=0;j<4;j++) {
                if (!pq[j].isEmpty()) {
                    if (temp < (double) pq[j].peek() / now[j]) {
                        temp = (double) pq[j].peek() / now[j]; 
                        card = (char) ('A' + j);
                    }
                }
            }

            bw.write(card +" "+pq[card-'A'].peek()+"\n");
            now[card-'A'] += pq[card-'A'].poll();
        }
        bw.flush();
    }
}