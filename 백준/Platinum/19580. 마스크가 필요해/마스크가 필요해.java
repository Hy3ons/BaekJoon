import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Pair {
    long x, y;
    Pair (long x, long y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int people = Integer.parseInt(st.nextToken()), store = Integer.parseInt(st.nextToken());

        PriorityQueue<Pair> seller = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Long.compare(o1.x, o2.x);
            }
        });

        PriorityQueue<Pair> buyer = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Long.compare(o1.x, o2.x);

            }
        });
        for (int i=0;i<people;i++) {
            st = new StringTokenizer(br.readLine());
            buyer.add(new Pair(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken())));
        }
        for (int i=0;i<store;i++) {
            st = new StringTokenizer(br.readLine());
            seller.offer(new Pair(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken())));
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Long.compare(o1.y, o2.y);
            }
        });

        int res = 0;

        while(!seller.isEmpty()) {
            while(!pq.isEmpty() && pq.peek().y < seller.peek().x) pq.poll();

            long price = seller.peek().x;

            while(!buyer.isEmpty()) {
                if (price < buyer.peek().x) break;
                else if (buyer.peek().x <= price && price <= buyer.peek().y) pq.offer(buyer.poll());
                else buyer.poll();
            }

            long x = seller.poll().y;

            while(!pq.isEmpty() && x-->0) {
                res++;
                pq.poll();
            }
        }
        System.out.println(res);
    }
}
