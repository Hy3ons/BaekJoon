import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Imf {
    int loc, fuel;
    Imf(int loc, int fuel) {
        this.loc = loc;
        this.fuel = fuel;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        Imf[] ms = new Imf[N];
        for (int i=0;i<ms.length;i++) {
            st = new StringTokenizer(br.readLine());
            ms[i] = new Imf(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        Arrays.sort(ms, new Comparator<Imf>() {
            @Override
            public int compare(Imf o1, Imf o2) {
                return Integer.compare(o1.loc, o2.loc);
            }
        });

        st = new StringTokenizer(br.readLine());
        int goal = Integer.parseInt(st.nextToken()), now = Integer.parseInt(st.nextToken());
        int result = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i=0;i<ms.length;i++) {
            if (ms[i].loc <= now) {
                pq.offer(ms[i].fuel);
            } else {
                while(!pq.isEmpty() && ms[i].loc > now) {
                    now += pq.poll();
                    result++;
                }

                if (ms[i].loc > now) {
                    System.out.println(-1);
                    return;
                } else {
                    pq.offer(ms[i].fuel);
                }
            }

            if (now >= goal) {
                System.out.println(result);
                return;
            }
        }
        while(!pq.isEmpty() && goal > now) {
            now += pq.poll();
            result++;

            if (goal <= now) {
                System.out.println(result);
                return;
            }
        }

        System.out.println(-1);
    }
}
