import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class SegTree {
    private int[] tree;
    private int start = 1, value;
    private int leftIdx, rightIdx, idx;
    private boolean answer;
    private boolean[] visited;

    SegTree (int N) {
        while(start<N) start *= 2;
        leftIdx = 1;
        rightIdx = N;

        visited = new boolean[N+1];
        tree = new int[start*2];
        for (int i=0;i<N;i++)
            tree[start+i] = 1;

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<i*2;j++) {
                tree[j] = tree[j*2] + tree[j*2+1];
            }
        }
    }

    private void setValue (int value) {
        this.value = value;
    }

    private void update (int index) {
        int node = index+start;
        tree[node] = 0;
        node /= 2;
        while(node!=0) {
            tree[node] = tree[node*2] + tree[node*2+1];
            node /= 2;
        }

    }

    public int[] answer (int[] table) {
        int[] result = new int[table.length];
        for (int i=result.length-1;i>=0;i--) {
            answer = false;
            setValue(table[i]);
            int sum = query(1, 1, start, 0);
            result[i] = idx-1;
            update(idx-1);
        }

        return result;
    }

    private int query (int node, int leftIdx, int rightIdx, int sum) {
        if (rightIdx<this.leftIdx || this.rightIdx < leftIdx) return sum;

        int mid = (leftIdx + rightIdx) / 2;

        if (this.leftIdx <= leftIdx && rightIdx <= this.rightIdx) {
            if (node>=start) {
                int temp = sum+tree[node];
                if (temp==value) {
                    if (!answer) {
                        answer= true;
                        if (!visited[rightIdx]) {
                            visited[rightIdx] = true;
                            idx = rightIdx;
                        } else {
                            traceAnswer(node, leftIdx, rightIdx);
                        }
                    }
                }
                return temp;
            }

            int temp = sum + tree[node];

            if (temp<=value) {
                if (temp==value&&!answer) {
                    answer = true;

                    if (!visited[rightIdx]) {
                        visited[rightIdx] = true;
                        idx = rightIdx;
                    } else {
                        traceAnswer(node, leftIdx, rightIdx);
                    }
                }
                return temp;
            } else {
                int vars = query(node*2, leftIdx, mid, sum);
                if (vars<value) {
                    return query(node*2+1, mid+1, rightIdx, vars);
                } else if (vars == value) {
                    return value;
                }
            }
        }

        int v = query(node*2, leftIdx, mid, sum);
        if (v==value) return value;
        return query(node*2+1, mid+1, rightIdx, v);
    }

    private boolean traceAnswer (int node, int left, int right) {
        if (node>=start) {
            if (tree[node]==1) {
                idx = right;
                visited[right] = true;
                return true;
            }
            return false;
        }
        if (tree[node]==0) return false;

        int mid = (left + right) / 2;

        if (!visited[right]) {
            visited[right] = true;
            idx = right;
            return true;
        }

        if (traceAnswer(node*2+1, mid+1, right)) {
            return true;
        } else {
            return traceAnswer(node*2, left, mid);
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] arr = new int[N];
        int[] table = new int[N];

        for (int i=0;i<N;i++)
            arr[i] = Integer.parseInt(br.readLine());

        Arrays.sort(arr);
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i=0;i<N;i++)
            table[i] = Integer.parseInt(st.nextToken()) + 1;

        SegTree tree = new SegTree(N);
        int[] result = tree.answer(table);

        StringBuilder sb = new StringBuilder();
        for (int i=0;i<result.length;i++)
            sb.append(arr[result[i]]+"\n");
        System.out.println(sb);
    }
}
