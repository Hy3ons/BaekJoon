import java.util.Scanner;
import java.util.StringJoiner;

public class Main {
    static void backTrace(int node, int left, int right, int tree[]) {
        if (node>=start) {
            result = right;
            return;
        }

        int mid = (left + right)/ 2;

        if (tree[start + right - 1] ==1) {
            result = right;
            return;
        }


        if (tree[node*2+1]!=0) {
            backTrace (node*2+1, mid+1, right, tree);
        } else if (tree[node*2]!=0) {
            backTrace(node*2, left, mid, tree);
        }


    }

    static int query(int node, int left, int right, int tree[], int value) {
        if (right < leftIdx || rightIdx < left) return value;

        int mid = (left + right) / 2;

        if (leftIdx <= left && right <= rightIdx) {
            if (node >= start) {
                int temp = value + tree[node];

                if (temp < goal) {
                    return temp;
                } else {
                    result = right;
                    return goal;
                }
            }


            int temp = value + tree[node];
            if (temp > goal) {
                int temp2 = query(node*2, left, mid, tree, value);
                if (temp2!=goal)
                    query(node*2+1, mid+1, right, tree, temp2);
                return goal;
            } else if (temp == goal) {
                if (tree[start+right-1]==0)
                    backTrace(node, left, right, tree);
                else
                    result = right;

                return goal;
            } else {
                return temp;
            }
        }

        int temp = query(node*2, left, mid, tree, value);
        if (temp==goal) return goal;
        return query(node*2+1, mid+1, right, tree, temp);
    }

    static void update(int index, int tree[]) {
        index += start -1;
        tree[index] = 0;
        index /= 2;

        while(index!=0) {
            tree[index] = tree[index*2] + tree[index*2+1];
            index /=2;
        }
    }
    public static int N, K, start, rightIdx, leftIdx, result, goal;
    public static int[] tree;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();

        StringJoiner sj = new StringJoiner(", ", "<", ">");

        start = 1;
        while (start < N) start *= 2;
        tree = new int[start*2];

        for (int i=0;i<N;i++) {
            tree[start+i] = 1;
        }

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<i*2;j++) {
                tree[j] = tree[j*2] + tree[j*2+1];
            }
        }
        rightIdx = N;

        int lastIdx = 1;

        while (tree[1]!=0) {
            int temp = 0;
            result = -1;

            if (K>tree[1]) {
                if (K % tree[1] == 0) {
                    goal = tree[1];
                } else {
                    goal = K % tree[1];
                }
            } else {
                goal = K;
            }

            while (true) {
                leftIdx = lastIdx;
                rightIdx = N;

                temp = query(1, 1, start, tree, temp);
                if (result!=-1) break;
                leftIdx = 1;
                rightIdx = lastIdx;

                temp = query(1, 1, start, tree, temp);
                if (result!=-1) break;
            }

            sj.add(result+"");
            lastIdx = result;
            update(result, tree);
        }
        System.out.println(sj.toString());
    }
}
