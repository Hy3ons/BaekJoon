import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


class Pair {
    long w, price;
    Pair (long w, long price) {
        this.w= w;
        this.price = price;
    }
}

public class Main {
    public static long weight;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        weight = Integer.parseInt(st.nextToken());

        al = new ArrayList<>(N);
        for (int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if(a == 0) continue;

            al.add(new Pair(a,b));
        }

        al.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1.price == o2.price) return Long.compare(o2.w, o1.w);
                return Long.compare(o1.price, o2.price);
            }
        });

        long maxPrice = -1;

        for (int i=0;i<al.size();i++){
            if (maxPrice < al.get(i).price) {
                maxPrice = al.get(i).price;
                clearing(al.get(i));
                pq.offer(al.get(i));
            } else {
                pq.offer(al.get(i));
            }
        }
        clearing(new Pair(1, Long.MAX_VALUE));
        System.out.println(-1);

    }
    public static PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
        @Override
        public int compare(Pair o1, Pair o2) {
            return Long.compare(o2.w, o1.w);
        }
    });
    public static ArrayList<Pair> al;

    public static void clearing(Pair next) {
        long money = 0;
        boolean be = true;
        while(!pq.isEmpty()) {
            Pair p = pq.poll();
            money += p.price;
            weight -= p.w;

            if (money >= next.price) {
                be = false;
            } else {
                if (be && weight <= 0) {
                    System.out.println(money);
                    System.exit(0);
                }
            }
        }
    }
}