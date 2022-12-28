import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] arr = new int[N];

        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o2.cost, o1.cost);
            }
        });

        for (int i=0;i<arr.length;i++) {
            arr[i] = Integer.parseInt(br.readLine() );
            pq.offer(new Pair(i, arr[i]));
        }


        boolean[] boom = new boolean[N];

        ArrayList<Integer> ans = new ArrayList<>();


        while(!pq.isEmpty()) {
            Pair now = pq.poll();
            if (boom[now.idx]) continue;
            
            boom[now.idx] = true;

            ans.add(now.idx+1);
            
            int limit = now.cost;
            
            for (int i=1 + now.idx;i < arr.length;i++) {
                if (arr[i] >= limit) break;
                limit = arr[i];
                boom[i] = true;
            }

            limit = now.cost;
            for (int i= now.idx-1;i>=0;i--) {
                if (arr[i] >= limit) break;
                limit = arr[i];
                boom[i] = true;
            }
        }

        Collections.sort(ans);
        
        for (int e : ans) System.out.println(e);



    }
}

class Pair {
    int idx, cost;

    Pair (int idx, int cost) {
        this.idx = idx;
        this.cost = cost;
    }
}