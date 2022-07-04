import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Pair {
    int loc, value;

    Pair (int loc, int value) {
        this.loc = loc;
        this.value = value;
    }
}

public class Main {
    public static int mki(StringTokenizer s) {return Integer.parseInt(s.nextToken());}

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        ArrayList<ArrayList<Pair>> load = new ArrayList<>(testcase+1);
        for(int i=0;i<=testcase;i++) load.add(new ArrayList<>());

        for (int i=1;i<testcase;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n1 = mki(st);
            int n2 = mki(st);

            int value = mki(st);
            load.get(n1).add(new Pair(n2, value));
            load.get(n2).add(new Pair(n1, value));
        }

        Queue<Pair> qu = new LinkedList<>();
        boolean[] visited = new boolean[testcase+1];
        visited[1] = true;

        qu.offer(new Pair(1, 0));
        Pair max = new Pair(-1, -1);


        while(!qu.isEmpty()) {
            Pair now = qu.poll();

            if (max.value < now.value)
                max = now;

            for (Pair go : load.get(now.loc)) {
                if (!visited[go.loc]) {
                    visited[go.loc] = true;
                    qu.offer(new Pair(go.loc, now.value + go.value));
                }
            }
        }
        Arrays.fill(visited, false);

        max.value = 0;
        qu.offer(max);
        visited[max.loc] = true;

        while(!qu.isEmpty()) {
            Pair now = qu.poll();

            if (max.value < now.value)
                max = now;

            for (Pair go : load.get(now.loc)) {
                if (!visited[go.loc]) {
                    visited[go.loc] = true;
                    qu.offer(new Pair(go.loc, now.value + go.value));
                }
            }
        }
        System.out.println(max.value);
    }
}