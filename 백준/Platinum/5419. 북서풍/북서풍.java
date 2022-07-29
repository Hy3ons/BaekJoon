import java.io.*;
import java.util.*;

class Pair {
    int x, y;
    Pair (int x, int y) {
        this.x= x;
        this.y= y;
    }
}

class Segment {
    private int[] tree;
    private int start = 1, leftIdx = 1, rightIdx;

    Segment (int size) {
        while (start < size) start *= 2;
        tree = new int[start * 2];
    }

    public void update (int value) {
        int node = start + value - 1;
        tree[node]++;
        node /= 2;

        while (node != 0) {
            tree[node]++;
            node /= 2;
        }
    }
    public void clear () {
        Arrays.fill(tree, 0);
    }

    public int find (int value) {
        rightIdx = value;
        return query(1, 1, start);
    }
    private int query (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (leftIdx <= left && right <= rightIdx) return tree[node];

        int mid = (left + right ) / 2;

        return query(node*2, left, mid) + query(node*2+1, mid+1, right);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int testcase = Integer.parseInt(br.readLine());
        Segment tree = new Segment(75000);

        while (testcase-- >0) {
            tree.clear();
            int number = Integer.parseInt(br.readLine() );

            ArrayList<Pair> pairs = new ArrayList<>(number);
            HashSet<Integer> compress = new HashSet<>();

            for (int i=0;i<number ;i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                compress.add(y);
                pairs.add(new Pair(x,y));
            }
            ArrayList<Integer> sort = new ArrayList<>(compress);
            Collections.sort(sort);

            Collections.sort(pairs, new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    if (o1.x == o2.x) return Integer.compare(o1.y, o2.y);
                    return Integer.compare(o2.x, o1.x);
                }
            });

            long result = 0;


            for (Pair p : pairs) {
                int y = low_bound(sort, p.y);
                result += tree.find(y);
                tree.update(y);
            }
            bw.write(result+"\n");
        }
        bw.flush();

    }
    public static int low_bound (ArrayList<Integer> list, int goal) {
        int left = -1;
        int right = list.size();

        while (true) {
            int mid = (left + right) / 2;

            if (list.get(mid) > goal) {
                right = mid;
            } else {
                left = mid;
            }

            if (right - left == 1) return right;
        }
    }
}