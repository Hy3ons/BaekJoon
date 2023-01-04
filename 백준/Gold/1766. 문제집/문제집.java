import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st =new StringTokenizer(br.readLine());

        int testcase = Integer.parseInt(st.nextToken());
        int loadLength = Integer.parseInt(st.nextToken());

        int[] targeted = new int[testcase];
        ArrayList<ArrayList<Integer>> al = new ArrayList<>(testcase);
        for (int i=0;i<testcase;i++) al.add(new ArrayList<>());

        for (int i=0;i<loadLength;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken())-1;
            int to = Integer.parseInt(st.nextToken())-1;

            al.get(from).add(to);
            targeted[to]++;
        }

        //0부터 testcase-1까지의 노드들이 존재.

        boolean[] solved = new boolean[testcase];
        boolean[] noqu = new boolean[testcase];
        PriorityQueue<Integer> qu = new PriorityQueue<>(Integer::compareTo);

        for (int i=0;i<testcase;i++) {
            if (!solved[i]&&targeted[i]==0) {
                solved[i] = true;


                qu.offer(i);

                while(!qu.isEmpty()) {
                    int from = qu.poll();
                    bw.write(from+1+" ");
                    
                    for (int k=0;k<al.get(from).size();k++) {
                        int to = al.get(from).get(k);

                        targeted[to]--;
                        if (!solved[to]&&targeted[to]==0) {
                            solved[to] = true;
                            qu.offer(to);
                        }
                    }
                }
            }
        }
        bw.flush();

    }
}