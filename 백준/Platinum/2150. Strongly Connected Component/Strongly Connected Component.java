import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testcase = Integer.parseInt(st.nextToken());
        int loadAmount = Integer.parseInt(st.nextToken());

        al = new ArrayList<>(testcase+1);
        reversed = new ArrayList<>(testcase+1);

        for (int i=0;i<=testcase;i++) {
            al.add(new ArrayList<>());
            reversed.add(new ArrayList<>());
        }

        for (int i=0;i<loadAmount;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int go = Integer.parseInt(st.nextToken());

            al.get(from).add(go);
            reversed.get(go).add(from);
        }

        visited = new boolean[testcase+1];
        ssc  = new boolean[testcase+1];

        for (int i=1;i<=testcase;i++) {
            if(!visited[i]) {
                visited[i] = true;
                dfs(i);
            }
        }

        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();

        for (int i=re.size()-1;i>=0;i--) {
            int go = re.get(i);

            if (!ssc[go]) {

                ssc[go] = true;
                ArrayList<Integer> al = new ArrayList<>();
                al.add(go);

                dfs2(go, al);

                answer.add(al);
            }
        }

        for (ArrayList<Integer> al : answer) Collections.sort(al);
        Collections.sort(answer, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return o1.get(0)-o2.get(0);
            }
        });


        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(answer.size()+"\n");

        for (int i=0;i<answer.size();i++) {

            for (int k=0;k<answer.get(i).size();k++) bw.write(answer.get(i).get(k)+" ");

            bw.write("-1\n");
        }


        bw.flush();

    }
    public static ArrayList<ArrayList<Integer>> al;
    public static ArrayList<ArrayList<Integer>> reversed;
    public static boolean[] visited, ssc;
    public static ArrayList<Integer> re = new ArrayList<>();

    public static void dfs (int node) {

        for (int i=0;i<al.get(node).size();i++) {
            int go = al.get(node).get(i);

            if (!visited[go]) {
                visited[go] = true;
                dfs(go);
            }
        }

        re.add(node);
    }

    public static void dfs2 (int node, ArrayList<Integer> al) {

        for (int i=0;i<reversed.get(node).size();i++) {
            int go = reversed.get(node).get(i);

            if (!ssc[go]) {
                ssc[go] = true;
                al.add(go);
                dfs2(go, al);
            }
        }

    }
}
