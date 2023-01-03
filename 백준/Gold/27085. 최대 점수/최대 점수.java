import java.io.*;
import java.util.*;

public class Main {

    static class Node {
        long max, left, right, sum;
        public static long MIN = -100000000000000000L;

        Node (long value) {
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

    static class Seg {
        private Node[] tree;
        private int start =1;
        public int result, size;

        Seg(long[] arr) {
            while (start < arr.length) start *= 2;
            size = arr.length;

            tree = new Node[start * 2];
            for (int i=0;i<tree.length;i++) tree[i] = new Node(0);

            for (int i=0;i<arr.length;i++) {
                tree[i + start] = new Node(arr[i]);
            }

            for (int i=start/2;i!=0;i/=2) {
                for (int j=i;j<i*2;j++) {
                    making(tree[j], tree[j*2], tree[j*2+1]);
                }
            }
        }
        public static long max (long a, long b, long c) {
            return Math.max(a, Math.max(b,c));
        }

        public Node find (int left, int right) {
            return query(1, left+1, right+1, 1, start);
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

        public Node query(int node, int s, int e, int left, int right) {
            if (e < left || right < s) return null;

            if (s <= left && right <= e) {
                return tree[node];
            }

            int mid = left + right >> 1;
            Node a = new Node(Node.MIN);
            a = making(a, query(node*2, s, e, left, mid), query(node*2+1, s, e, mid+1, right));
            return a;
        }
        public static Node making (Node result, Node L, Node R) {
            if (L == null) return R;
            else if (R == null) return L;
            result.right = Math.max(R.right, R.sum + L.right);
            result.left = Math.max(L.left, L.sum + R.left);
            result.sum = L.sum + R.sum;
            result.max = max(L.max, R.max, L.right + R.left);
             return result;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcase = 1;

        C: while(testcase-->0){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken()), k = Integer.parseInt(st.nextToken());
            long[] arr = new long[n];
            st = new StringTokenizer(br.readLine());
            for (int i=0;i<arr.length;i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

          
          
            
            Seg tree = new Seg(arr);

            long max =0;
            boolean flag =true;
            int l=k-1,r=k-1;
             for (;flag;) {
                 flag = false;
                 if (l-1>=0) {
                     Node temp = tree.find(l-1, r);
                     if (temp.left >= 0) {
                          l--;
                         flag = true;
                         max = Math.max(max, temp.left);
                     }
                 }
                 
                 if (r+1<n) {
                     Node temp = tree.find(l, r+1);
                     if (temp.right >= 0) {
                          r++;
                         flag = true;
                         max = Math.max(max, temp.right);
                     }
                 }
                 
             }
             Node temp = tree.find(l, k-1);
            Node temp2 = tree.find(k-1, r);
             bw.write(temp.right + temp2.left+"\n");
     



        }
        bw.flush();
    }
}
