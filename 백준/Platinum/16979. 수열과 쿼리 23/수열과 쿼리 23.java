import java.io.*;
import java.util.*;

class Query {
    int left, right, sqrt;
    long answer;
    Query(int left, int right) {
        this.left =left;
        this.right = right;
        sqrt = left / 400;
    }
}

class Seg {
    private int[] tree;
    private int start = 1, leftIdx, rightIdx;
    Seg () {
        while (start < 100001) start <<= 1;
        tree = new int[start * 2];
    }

    public void clear () {
        Arrays.fill(tree, 0);
    }

    public int findUp (int value) {
        leftIdx = value + 1;
        rightIdx = 100000;
        return query(1, 1, start);
    }

    public int findDown (int value) {
        leftIdx = 1;
        rightIdx = value - 1;
        return query(1, 1, start);
    }

    private int query (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (leftIdx <= left && right <= rightIdx) return tree[node];

        int mid = left + right >> 1;
        return query(node*2, left, mid) + query(node*2+1, mid+1, right);
    }

    public void update (int value, int d) {
        int node = value + start - 1;
        tree[node] += d;
        while((node >>= 1) != 0)
            tree[node] = tree[node*2] + tree[node*2+1];
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()), amount = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i=0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());
        compress(arr);

        Query[] queries = new Query[amount];
        for (int i=0;i<queries.length;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            queries[i] = new Query(n1, n2);
        }

        Query[] sorts = queries.clone();
        Arrays.sort(sorts, new Comparator<Query>() {
            @Override
            public int compare(Query o1, Query o2) {
                if (o1.sqrt == o2.sqrt) return Integer.compare(o1.right, o2.right);
                return Integer.compare(o1.sqrt, o2.sqrt);
            }
        });

        int nowSqrt = -1, left = -1, right = -1;
        long result = 0;
        Seg tree = new Seg();

        for (int i=0;i< sorts.length;i++) {
            if (nowSqrt != sorts[i].sqrt) {
                nowSqrt = sorts[i].sqrt;
                left = sorts[i].left;
                right = sorts[i].right;
                tree.clear();
                result = 0;

                for (int j=left-1;j<right;j++) {
                    tree.update(arr[j], 1);
                    result += tree.findUp(arr[j]);
                }
            } else {
                for (int j=right;j<sorts[i].right;j++) {
                    tree.update(arr[j], 1);
                    result += tree.findUp(arr[j]);
                }

                if (left < sorts[i].left) {
                    for (int j=left-1;j<sorts[i].left-1;j++) {
                        result -= tree.findDown(arr[j]);
                        tree.update(arr[j], -1);
                    }
                } else {
                    for (int j=left-2;j>=sorts[i].left-1;j--) {
                        result += tree.findDown(arr[j]);
                        tree.update(arr[j], 1);
                    }
                }
            }
            left = sorts[i].left;
            right = sorts[i].right;
            sorts[i].answer = result;
        }



        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (Query q : queries) {
            bw.write(q.answer+"\n");
        }
        bw.flush();
    }

    public static void compress (int[] arr) {
        HashSet<Integer> hs = new HashSet<>();
        for (int e : arr) hs.add(e);
        ArrayList<Integer> al = new ArrayList<>(hs);
        Collections.sort(al);
        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int i=0;i<al.size();i++) {
            hm.put(al.get(i), i+1);
        }

        for (int i=0;i<arr.length;i++) {
            arr[i] = hm.get(arr[i]);
        }
    }
}