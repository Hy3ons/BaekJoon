import java.io.*;
import java.util.*;

class Node {
    Node left, right;
    int value;
    Node (int value) {
        this.value = value;
    }

    Node (Node left, Node right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }
}

class SegP {
    private Node[] starts;
    private int size, start = 1, leftIdx, rightIdx;
    private int nowCapacity;

    SegP(int[] arr, int size) {
        this.size = arr.length;
        while(start < arr.length) start <<= 1;

        starts = new Node[arr.length+1];
        Node[] nodes = new Node[start * 2];

        for (int i=0;i<nodes.length;i++) nodes[i] = new Node(0);

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<i*2;j++) {
                nodes[j].left = nodes[j*2];
                nodes[j].right = nodes[j*2+1];
            }
        }
        starts[0] = nodes[1];

        int[] prev = new int[size+1];
        Arrays.fill(prev, -1);

        for (int i=0;i<arr.length;i++) {
            if (prev[arr[i]]==-1) {
                prev[arr[i]] = i+1;
                starts[i+1] = sUpdate(starts[i], i+1, 1);
            } else {
                starts[i+1] = sUpdate(starts[i], i+1, 1);
                starts[i+1] = sUpdate(starts[i+1], prev[arr[i]], -1);
                prev[arr[i]] = i+1;
            }
        }
    }

    public Node sUpdate (Node base, int idx, int change) {
        leftIdx = rightIdx = idx;
        return update(base, 1, start, change);
    }

    public int answer (int left, int right) {
        leftIdx = left;
        rightIdx = right;
        return sumQuery(starts[right], 1, start);
    }


    private int sumQuery (Node now, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (leftIdx <= left && right <= rightIdx) {
            return now.value;
        }

        int mid = left + right >> 1;

        return sumQuery(now.left, left, mid)
                + sumQuery(now.right, mid+1, right);
    }

    private Node update (Node node, int left, int right, int value) {
        if (right < leftIdx || rightIdx < left) return node;

        if (leftIdx <= left && right <= rightIdx) {
            return new Node(value + node.value);
        }

        int mid = left + right >> 1;

        Node left_leaf = update(node.left, left, mid, value);
        Node right_leaf = update(node.right, mid+1, right, value);
        return new Node(left_leaf, right_leaf, left_leaf.value+ right_leaf.value);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int length = Integer.parseInt(br.readLine());
        int[] arr = new int[length];
        StringTokenizer st =new StringTokenizer(br.readLine());
        HashSet<Integer> hs = new HashSet<>();

        for (int i=0;i<arr.length;i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            hs.add(arr[i]);
        }

        ArrayList<Integer> sort = new ArrayList<>(hs);
        Collections.sort(sort);

        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int i=0;i<sort.size();i++) hm.put(sort.get(i), i+1);

        for (int i=0;i<arr.length;i++) arr[i] = hm.get(arr[i]);

        SegP tree = new SegP(arr, sort.size());

        int queries = Integer.parseInt(br.readLine());
        int answer = 0;

        while(queries-->0) {
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken()) + answer;
            int right = Integer.parseInt(st.nextToken());

            answer = tree.answer(left, right);
            bw.write(answer+"\n");
        }
        bw.flush();
    }

}
