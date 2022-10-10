import java.io.*;
import java.util.*;

class Node {
    long max, left, right, sum;
    public static int MIN = -2000000000;

    Node (int value) {
        max = left = right = sum = value;
    }
    public void set(long value) {
        max = left = right = sum = value;
    }
    public void clear () {
        max = Long.MIN_VALUE;
        left = right = sum = 0;
    }
}

class Segment {
    private Node[] tree;
    private int start =1;
    public int result;

    Segment(int size) {
        while (start < size) start *= 2;

        tree = new Node[start * 2];
        for (int i=0;i<tree.length;i++) tree[i] = new Node(0);
    }
    public static long max (long a, long b, long c) {
        return Math.max(a, Math.max(b,c));
    }

    public long find () {
        return tree[1].max;
    }
    public void clear () {
        for (Node node : tree) node.clear();
    }

    public void update (int idx, int value) {
        idx += start - 1;
        tree[idx].set(tree[idx].sum  + value);
        idx /= 2;

        while(idx !=0) {
            making(tree[idx], tree[idx*2], tree[idx*2+1]);
            idx /= 2;
        }
    }
    public static void making (Node result, Node L, Node R) {
        result.right = Math.max(R.right, R.sum + L.right);
        result.left = Math.max(L.left, L.sum + R.left);
        result.sum = L.sum + R.sum;
        result.max = max(L.max, R.max, L.right + R.left);
    }
}

class Pair {
    int x, y;
    int weight;
    Pair (int x, int y, int weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int size = Integer.parseInt(br.readLine());

        ArrayList<Pair> pairs = new ArrayList<>(size);
        HashSet<Integer> xPress = new HashSet<>()
                , yPress = new HashSet<>();
        for (int i=0;i<size;i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            xPress.add(x);
            yPress.add(y);

            pairs.add(new Pair(x,y,value));
        }
        HashMap<Integer, Integer> xChanger = new HashMap<>()
                ,yChanger = new HashMap<>();
        ArrayList<Integer> sort = new ArrayList<>(xPress);
        Collections.sort(sort);

        Segment tree = new Segment(xPress.size());
        tree.clear();

        for (int i=0;i<sort.size();i++) xChanger.put(sort.get(i), i+1);
        sort = new ArrayList<>(yPress);
        Collections.sort(sort);

        for (int i=0;i<sort.size();i++) yChanger.put(sort.get(i), i+1);
        ArrayList<ArrayList<Pair>> arr = new ArrayList<>(yPress.size()+1);

        for (int i=0;i<= yPress.size();i++) arr.add(new ArrayList<>());
        for (int i=0;i<pairs.size();i++) {
            arr.get(yChanger.get(pairs.get(i).y)).add(pairs.get(i));
        }


        long result = 0;

        for (int i=1;i<arr.size();i++) {
            for (int j=i;j<arr.size();j++) {
                for (int k=0;k<arr.get(j).size();k++) {
                    Pair p = arr.get(j).get(k);

                    tree.update(xChanger.get(p.x), p.weight);
                }
                result = Math.max(result, tree.find());
            }
            tree.clear();
        }
        System.out.println(result);

    }
}
