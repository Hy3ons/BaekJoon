import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(sc.nextLine());
        int amount = Integer.parseInt(st.nextToken());
        days = Integer.parseInt(st.nextToken());

        ArrayList<ArrayList<Integer>> load = new ArrayList<>(days *2+1);
        for (int i = 0; i< days *2+1; i++) load.add(new ArrayList<>());

        for (int i=0;i<amount;i++) {
            st = new StringTokenizer(sc.nextLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            load.get(encode(-a)).add(encode(b));
            load.get(encode(-b)).add(encode(a));
        }

        try {
            SSC = makeSSC(load);
        } catch (Exception ex) {
            bw.write(ex.getMessage());
            bw.flush();
            return;
        }
        bw.write("^_^\n");
        bw.flush();
    }

    public static void throwEx () throws Exception {
        throw new Exception("OTL\n");
    }
    public static int days, oneSSC;
    public static boolean[] visited;
    public static ArrayList<HashSet<Integer>> SSChash;

    public static ArrayList<ArrayList<Integer>> connected, SSC;

    public static int encode(int v) {
        if (v<0) {
            return days + (v * -1);
        } else {
            return v;
        }
    }

    public static int decode (int v) {
        if (v> days) {
            return days - v;
        } else {
            return v;
        }
    }

    public static ArrayList<ArrayList<Integer>> makeSSC (ArrayList<ArrayList<Integer>> load) throws Exception {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        SSChash = new ArrayList<>();

        ArrayList<Integer> re = new ArrayList<>();
        visited = new boolean[days *2+1];

        for (int i = 1; i< days *2+1; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(re, load, i);
            }
        }

        ArrayList<ArrayList<Integer>> reverse = new ArrayList<>();
        for (int i = 0; i< days *2+1; i++) reverse.add(new ArrayList<>());

        for (int i=0;i<load.size();i++) {
            for (int j=0;j<load.get(i).size();j++) {
                reverse.get(load.get(i).get(j)).add(i);
            }
        }

        Arrays.fill(visited, false);

        for (int i=re.size()-1;i>=0;i--) {
            if (!visited[re.get(i)]) {
                visited[re.get(i)] = true;
                HashSet<Integer> temp = new HashSet<>();
                temp.add(decode(re.get(i)));

                search(reverse, temp, re.get(i));
                if (temp.contains(1)) oneSSC = result.size();
                SSChash.add(temp);
                result.add(new ArrayList<>(temp));
            }
        }

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



    public static void search (ArrayList<ArrayList<Integer>> reverse, HashSet<Integer> bucket, int node)
            throws Exception {
        for (int i=0;i<reverse.get(node).size();i++) {
            int go = reverse.get(node).get(i);

            if (!visited[go]) {
                visited[go] = true;
                int realValue = decode(go);

                if (bucket.contains(-realValue)) throwEx();
                else bucket.add(realValue);

                search(reverse, bucket, go);
            }
        }
    }
}
