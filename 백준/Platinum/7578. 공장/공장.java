import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Treeseg {
    private int start = 1;
    private int[] tree;
    private int leftIdx, rightIdx;

    Treeseg (int[] arr) {
        while(start<arr.length) start *=2;
        tree = new int[start*2];

        rightIdx = arr.length;
    }

    private int query (int node, int left, int right) {
        if (right<leftIdx || rightIdx < left ) return 0;

        if (leftIdx <= left && right <= rightIdx) {
            return tree[node];
        }

        int mid = (left + right) / 2;

        return query(node*2, left, mid)
                + query(node*2+1, mid+1, right);

    }

    private void update (int index) {
        index += start -1;
        tree[index] = 1;
        index /= 2;

        while(index!=0) {
            tree[index] = tree[index*2] + tree[index*2+1];
            index /= 2;
        }
    }

    public int result (int setValue) {
        this.leftIdx = setValue+1;
        int result = query(1,1,start);
        update(setValue);
        return result;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        int[] change = new int[1000001];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i=0;i<testcase;i++) {
            change[Integer.parseInt(st.nextToken())] = i;
        }

        int[] arr = new int[testcase];
        st = new StringTokenizer(br.readLine());
        for (int i=1;i<=arr.length;i++) {
            arr[change[Integer.parseInt(st.nextToken())]] = i;
        }

        Treeseg tree = new Treeseg(arr);
        long result = 0;
        for (int i=0;i<arr.length;i++)
            result += tree.result(arr[i]);

        System.out.println(result);
    }
}
