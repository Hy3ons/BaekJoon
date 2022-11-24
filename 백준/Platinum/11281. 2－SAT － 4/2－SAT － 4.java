import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class Main {
    public static int testcase;
    public static ArrayList<ArrayList<Integer>> load, SSC, connected;
    public static int[] setInt;


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

        //SSC를 만들때 애초에 만들 수 없는 경우, 예외가 던져진다.
        try {
            SSC = makeSSC();
        } catch (Exception ex) {
            System.out.println(0);
            return;
        }

        indegree = new int[SSC.size()];
        setInt = new int[testcase*2+1];

        for (int i=0;i<SSC.size();i++) {
            for (int j=0;j<SSC.get(i).size();j++) {
                int element = SSC.get(i).get(j);

                setInt[valueChanger(element)] = i;
            }
        }

        connected = new ArrayList<>(SSC.size());
        for (int i=0;i < SSC.size();i++) connected.add(new ArrayList<>());

        for (int i=0;i<SSC.size();i++) {
            for (int j=0;j<SSC.get(i).size();j++) {
                int element = valueChanger(SSC.get(i).get(j));

                for (int k=0;k<load.get(element).size();k++) {
                    int go = setInt[load.get(element).get(k)];

                    if (i==go) continue;
                    connected.get(i).add(go);
                }

            }
        }

        for (int i=0;i<connected.size();i++) {
            HashSet<Integer> hs = new HashSet<>(connected.get(i));
            connected.set(i, new ArrayList<>(hs));
        }

        for (ArrayList<Integer> al : connected) {
            for (int k : al) {
                indegree[k]++;
            }
        }

        boolean[] visited = new boolean[SSC.size()];
        int[] result = new int[testcase+1];

        for (int i=0;i< SSC.size();i++) {
            if (indegree[i]==0&&!visited[i]) {
                boolean s = false;
                visited[i] = true;

                if (result[vlc(SSC.get(i).get(0))]!=0) s = result[vlc(SSC.get(i).get(0))] > 0;

                dfs3(i, visited, result, s);
            }
        }

        //정답 출력부분
        StringBuilder sb = new StringBuilder();
        System.out.println(1);

        for (int i=1;i<result.length;i++) {
            if (result[i]>0) sb.append("1 ");
            else sb.append("0 ");
        }
        System.out.println(sb);

    }

    public static int[] indegree;

    public static void dfs3 (int ssc, boolean[] visited, int[] result, boolean firstSscCheck) {
        if (firstSscCheck) {
            for (int element : SSC.get(ssc)) {
                int temp = vlc(element);

                if (element<0) {
                    result[temp] = -1;
                } else {
                    result[temp] = 1;
                }

            }

        } else {
            for (int element : SSC.get(ssc)) {
                int temp = vlc(element);

                if (result[temp]!=0) continue;

                if (result[temp]==0) {
                    if (element<0) {
                        result[temp] = 1;
                    } else {
                        result[temp] = -1;
                    }
                }

            }

        }



        for (int go : connected.get(ssc)) {

            if (!visited[go]&&--indegree[go]==0) {
                visited[go] = true;
                dfs3(go, visited, result, firstSscCheck);
            }

        }
    }

    public static int vlc (int e) {
        if (e<0) return e*-1;
        return e;
    }

    public static int valueChanger (int value) {
        if (value<0) {
            value *= -1;
            return value+testcase;
        } else {
            return value;
        }
    }

    public static ArrayList<ArrayList<Integer>> makeSSC () throws Exception {
        ArrayList<ArrayList<Integer>> al = new ArrayList<>();
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
                dfs2(temp, reversedLoad, node, visited);

                al.add(new ArrayList<>(temp));
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