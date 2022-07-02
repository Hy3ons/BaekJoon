import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        ArrayList<ArrayList<Integer>> al = new ArrayList<>(testcase+1);

        for (int i=0;i<=testcase;i++) al.add(new ArrayList<>());

        for (int i=1;i<testcase;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            al.get(n1).add(n2);
            al.get(n2).add(n1);
        }

        int[] father = new int[testcase+1];
        boolean[] visited = new boolean[testcase+1];

        Queue<Integer> qu = new LinkedList<>();
        qu.offer(1);

        while(!qu.isEmpty()) {
            int now = qu.poll();

            for (int go : al.get(now)) {
                if (!visited[go]) {
                    visited[go] = true;
                    father[go] = now;
                    qu.offer(go);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i=2;i<=testcase;i++) {
            sb.append(father[i]).append('\n');
        }
        System.out.println(sb);
    }
}