import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int tryTime = Integer.parseInt(br.readLine());

        for (int T=0;T<tryTime;T++) {
            if (T!=0) br.readLine();

            StringTokenizer st = new StringTokenizer(br.readLine());

            int testcase = Integer.parseInt(st.nextToken());
            int loadAmount = Integer.parseInt(st.nextToken());

            original = new ArrayList<>(testcase+1);
            reversed = new ArrayList<>(testcase+1);
            re = new ArrayList<>();

            for (int i=0;i<=testcase;i++) {
                original.add(new ArrayList<>());
                reversed.add(new ArrayList<>());
            }

            for (int i=0;i<loadAmount;i++) {
                st = new StringTokenizer(br.readLine());
                if(!st.hasMoreTokens()) break;

                int from = Integer.parseInt(st.nextToken());
                int go = Integer.parseInt(st.nextToken());

                original.get(from).add(go);
                reversed.get(go).add(from);
            }

            visited = new boolean[testcase];
            ssc  = new boolean[testcase];

            for (int i=0;i<testcase;i++) {
                if(!visited[i]) {
                    visited[i] = true;
                    dfs(i);
                }
            }

            SSC = new ArrayList<>();

            for (int i=re.size()-1;i>=0;i--) {
                int go = re.get(i);
                if (!ssc[go]) {

                    ssc[go] = true;
                    ArrayList<Integer> set = new ArrayList<>();
                    set.add(go);

                    dfs2(go, set);
                    SSC.add(set);
                }
            }
            //SSC 배열에 각각의 ssc가 묵여있다.

            testing = new boolean[SSC.size()];
            setInt = new int[testcase];

            for (int i=0;i<SSC.size();i++) {

                for (int k=0;k<SSC.get(i).size();k++) {
                    setInt[SSC.get(i).get(k)] = i;
                }
            }

            try {
                func();
            } catch (Exception ex) {
                bw.write("Confused\n\n");
                continue;
            }

            bw.newLine();
        }

        bw.flush();
    }
    public static ArrayList<ArrayList<Integer>> original, SSC, reversed;
    public static boolean[] visited, ssc, testing;
    public static ArrayList<Integer> re;
    public static int[] setInt;

    public static void dfs (int node) {

        for (int i=0;i<original.get(node).size();i++) {
            int go = original.get(node).get(i);

            if (!visited[go]) {
                visited[go] = true;
                dfs(go);
            }
        }

        re.add(node);
    }

    public static void dfs2 (int node, ArrayList<Integer> hs) {

        for (int i=0;i<reversed.get(node).size();i++) {
            int go = reversed.get(node).get(i);

            if (!ssc[go]) {
                ssc[go] = true;
                hs.add(go);
                dfs2(go, hs);
            }
        }

    }

    public static void func () throws Exception {

        for (int i=0;i<SSC.size();i++) {
            for (int k=0;k<SSC.get(i).size();k++) {
                int from = SSC.get(i).get(k);

                for (int j=0;j<original.get(from).size();j++) {
                    int go = setInt[original.get(from).get(j)];
                    
                    if (go==i) continue;
                    testing[go] = true;
                }
            }
        }

        int count = 0;
        int index = -1;

        for (int i=0;i<testing.length;i++) {
            if (!testing[i]) {
                index = i;
                count++;

                if (count==2) throw new Exception();
            }
        }
        if (index==-1) throw new Exception();

        Collections.sort(SSC.get(index));
        for (int i : SSC.get(index)) {
            bw.write(i+"\n");
        }

    }
}
