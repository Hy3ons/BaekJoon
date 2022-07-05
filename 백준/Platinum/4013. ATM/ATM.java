import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        testcase = Integer.parseInt(st.nextToken());
        int amount = Integer.parseInt(st.nextToken());

        ArrayList<ArrayList<Integer>> load = new ArrayList<>(testcase +1);
        reverse = new ArrayList<>(testcase+1);
        for(int i = 0; i<= testcase; i++) {
            load.add(new ArrayList<>());
            reverse.add(new ArrayList<>());
        }

        for (int i=0;i<amount;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            load.get(from).add(to);
            reverse.get(to).add(from);
        }
        int[] atm = new int[testcase+1];

        for (int i=1;i<=testcase;i++)
            atm[i] = Integer.parseInt(br.readLine());


        int[] setInt = new int[testcase+1];

        Arrays.fill(setInt, -1);

        st = new StringTokenizer(br.readLine());
        realStart = Integer.parseInt(st.nextToken());
        int restaurant = Integer.parseInt(st.nextToken());

        ArrayList<ArrayList<Integer>> SSC = makeSSC(load);
        long[] sscValue = new long[SSC.size()];

        for (int i=0;i< SSC.size();i++) {
            for (int j=0;j< SSC.get(i).size();j++) {
                setInt[SSC.get(i).get(j)] = i;
            }
        }



        for (int i=0;i< SSC.size();i++) {
            for (int j=0;j< SSC.get(i).size();j++) {
                sscValue[i] += atm[SSC.get(i).get(j)];
            }
        }

        // setInt 는 해당 노드가 속한 SSC의 노드를 의미한다.
        // sccValue 는 ssc의 해당노드가 속한 atm기의 전체 합.


        st = new StringTokenizer(br.readLine());
        boolean[] rest = new boolean[SSC.size()];

        for (int i=0;i<restaurant;i++) {
            int loc = setInt[Integer.parseInt(st.nextToken())];
            if (loc==-1) continue;
            rest[loc] = true;
        }


        ArrayList<ArrayList<Integer>> sscLoad = new ArrayList<>(SSC.size());

        for (int i=0;i< SSC.size();i++) {
            HashSet<Integer> hs = new HashSet<>();

            for (int j=0;j< SSC.get(i).size();j++) {
                int now = SSC.get(i).get(j);

                for (int k=0;k<load.get(now).size();k++) {
                    int go = load.get(now).get(k);
                    hs.add(setInt[go]);
                }
            }
            hs.remove(i);
            sscLoad.add(new ArrayList<>(hs));
        }
        int[] indegree = new int[SSC.size()];
        Queue<Integer> qu = new LinkedList<>();
        visited = new boolean[SSC.size()];

        qu.offer(setInt[realStart]);
        while(!qu.isEmpty()) {
            int now = qu.poll();

            for (int go : sscLoad.get(now)) {
                if (!visited[go]) {
                    visited[go] = true;
                    qu.offer(go);
                }

                indegree[go]++;
            }
        }


        long[] dp = new long[SSC.size()];

        qu.offer(setInt[realStart]);
        dp[setInt[realStart]] = sscValue[setInt[realStart]];

        while(!qu.isEmpty() ) {
            int now = qu.poll();

            for (int go : sscLoad.get(now)) {
                dp[go] = Math.max(dp[now] + sscValue[go], dp[go]);

                if (--indegree[go]==0) {
                    qu.offer(go);
                }
            }
        }

        result = 0;
        for (int i=0;i<rest.length;i++) {
            if (rest[i]) {
                result = Math.max(result, dp[i]);
            }
        }
        System.out.println(result);
    }

    public static int testcase, realStart;
    public static long result;
    public static boolean[] visited;
    public static ArrayList<ArrayList<Integer>> reverse;

    public static ArrayList<ArrayList<Integer>> makeSSC (ArrayList<ArrayList<Integer>> load) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        ArrayList<Integer> re = new ArrayList<>(testcase+1);
        visited = new boolean[testcase+1];

        for (int i=1;i<=testcase;i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(re, load, i);
            }
        }

//        visited[realStart] = true;
//        dfs(re, load, realStart);

        Arrays.fill(visited, false);

        for (int i=re.size()-1;i>=0;i--) {
            if (!visited[re.get(i)]) {
                visited[re.get(i)] = true;
                ArrayList<Integer> temp = new ArrayList<>();

                search(reverse, temp, re.get(i));
                result.add(temp);
            }
        }
//        for (int i=0;i<result.size();i++) {
//            System.out.print(i+1+"번째 : ");
//            for (int j=0;j<result.get(i).size();j++) {
//                System.out.print(result.get(i).get(j)+" ");
//            }
//            System.out.println();
//        }

        return result;
    }

    public static void dfs (ArrayList<Integer> re, ArrayList<ArrayList<Integer>> load, int node) {
        for (int i = 0; i < load.get(node).size(); i++) {
            int go = load.get(node).get(i);

            if (!visited[go]) {
                visited[go] = true;
                dfs(re, load, go);
            }
        }
        re.add(node);
    }



    public static void search (ArrayList<ArrayList<Integer>> reverse, ArrayList<Integer> bucket, int node) {
        for (int i=0;i<reverse.get(node).size();i++) {
            int go = reverse.get(node).get(i);

            if (!visited[go]) {
                visited[go] = true;
                search(reverse, bucket, go);
            }
        }
        bucket.add(node);
    }
}
