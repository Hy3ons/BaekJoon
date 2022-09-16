import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class Main {
    static class Pair {
        int x, y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.x, o2.x);
            }
        });


        for (int i=0;i<n;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            pq.offer(new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        int time = 0;
        PriorityQueue<Pair> going = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.y, o2.y);
            }
        });

        int res = 0;
        while(!pq.isEmpty()) {
            time = pq.peek().x;
            while(!going.isEmpty() && going.peek().y <= time) going.poll();

            going.offer(pq.poll());
            res = Math.max(res, going.size());
        }
        System.out.println(res);




    }
}