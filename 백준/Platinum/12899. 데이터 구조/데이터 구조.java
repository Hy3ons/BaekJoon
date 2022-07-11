import java.io.*;
import java.util.StringTokenizer;

class Tree {
    private int[] tree;
    private int start = 1, goal, result, leftIdx, rightIdx;

    Tree () {
        while (start < 2000000) start *= 2;
        tree = new int[start*2];
        leftIdx = 1;
        rightIdx = 2000000;
    }

    public void update (int index) {
        index += start - 1;
        while(index!=0) {
            tree[index]++;
            index /= 2;
        }

    }
    private void remove (int index) {
        index += start - 1;
        while(index!=0) {
            tree[index]--;
            index /= 2;
        }
    }
    public int find (int value) {
        this.goal = value;
        query(1,1,start, 0);
        remove(result);
        return result;
    }

    private void backTrace (int node, int left, int right) {
        if (tree[start + right - 1]!=0) {
            result = right;
            return;
        }

        int mid = (left + right) / 2;

        if (tree[node*2+1]!=0) {
            backTrace(node*2+1, mid+1, right);
        } else if (tree[node*2]!=0) {
            backTrace(node*2, left, mid);
        }
    }
    private int query (int node, int left, int right, int value) {
        if (right < leftIdx || rightIdx < left) return value;

        int mid = (left+ right) / 2;

        if (leftIdx <= left && right <= rightIdx) {
            int temp = tree[node] + value;
            if (node >= start) {
                if (temp < goal) {
                    return temp;
                } else {
                    result = right;
                    return goal;
                }
            }

            if (temp < goal) {
                return temp;
            } else if (temp > goal) {
                int temp2 = query(node*2, left, mid, value);
                if (temp2!=goal)
                    query(node*2+1, mid+1, right, temp2);
            } else {
                backTrace(node, left, right);
            }
            return goal;
        }

        int temp = query(node*2, left, mid, value);
        if (temp!= goal)
            query(node*2+1, mid+1, right, temp);
        return goal;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = Integer.parseInt(br.readLine());
        Tree tree = new Tree();

        while (testcase-->0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int method = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            switch (method) {
                case 1 :
                    tree.update(value);
                    break;
                case 2 :
                    bw.write(tree.find(value)+"\n");
                    break;
            }
        }
        bw.flush();
    }
}
