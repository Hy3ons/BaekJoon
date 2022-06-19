import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class Main {
    public static int testcase;
    public static ArrayList<ArrayList<Integer>> load;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        testcase = Integer.parseInt(st.nextToken());

        int queryAmount = Integer.parseInt(st.nextToken());
        load = new ArrayList<>(testcase*2+1);

        for (int i=0;i<testcase*2+1;i++) load.add(new ArrayList<>());

        for (int i=0;i<queryAmount;i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            load.get(valueChanger(-a)).add(valueChanger(b));
            load.get(valueChanger(-b)).add(valueChanger(a));
        }
        ArrayList<HashSet<Integer>> SSC = null;

        try {
            SSC = makeSSC();
        } catch (Exception ex) {
            System.out.println(0);
            return;
        }

        System.out.println(1);

    }

    public static int valueChanger (int value) {
        if (value<0) {
            value *= -1;
            return value+testcase;
        } else {
            return value;
        }
    }

    public static ArrayList<HashSet<Integer>> makeSSC () throws Exception {
        ArrayList<HashSet<Integer>> al = new ArrayList<>();
        boolean[] visited = new boolean[testcase*2+1];
        ArrayList<Integer> re = new ArrayList<>();

        for (int i=1;i<load.size();i++) {
            if(!visited[i]) {
                visited[i] = true;
                dfs(re, i, visited);
            }
        }
        ArrayList<ArrayList<Integer>> reversedLoad = new ArrayList<>(load.size());
        for (int i=0;i<load.size();i++) reversedLoad.add(new ArrayList<>());

        for (int i=0;i<load.size();i++) {
            for (int j=0;j<load.get(i).size();j++) {
                reversedLoad.get(load.get(i).get(j)).add(i);
            }
        }

        Arrays.fill(visited, false);
        for (int i=re.size()-1;i>=0;i--) {
            int node = re.get(i);

            if (!visited[node]) {
                HashSet<Integer> temp = new HashSet<>();
                if (node>testcase) {
                    temp.add(testcase-node);
                } else {
                    temp.add(node);
                }

                visited[node] = true;
                try {
                    dfs2(temp, reversedLoad, node, visited);
                } catch (Exception ex) {
                    throw ex;
                }

                al.add(temp);
            }
        }

        return al;

    }


    public static void dfs (ArrayList<Integer> re, int node, boolean[] visited) {
        for (int i=0;i<load.get(node).size();i++) {
            int go = load.get(node).get(i);

            if (!visited[go]) {
                visited[go] = true;
                dfs(re, go, visited);
            }
        }

        re.add(node);
    }

    public static void dfs2 (HashSet<Integer> ssc
            , ArrayList<ArrayList<Integer>> reversed , int node, boolean[] visited) throws Exception {
        for (int i=0;i<reversed.get(node).size();i++) {
            int go = reversed.get(node).get(i);

            if (!visited[go]) {
                visited[go] = true;
                if (go>testcase) {
                    if (ssc.contains(go-testcase)) throw new Exception();
                    ssc.add(testcase-go);
                } else {
                    if (ssc.contains(-go)) throw new Exception();
                    ssc.add(go);
                }

                dfs2(ssc, reversed, go, visited);
            }
        }
    }
}