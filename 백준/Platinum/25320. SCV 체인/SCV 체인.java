import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Tree {
    private int[] tree;
    private int start = 1;
    private int leftIdx, rightIdx;
    private int result, size;

    Tree (int[] arr) {
        while(start<arr.length) start *= 2;
        tree = new int[start*2];
        size = arr.length;
        rightIdx = size;

        for (int i=0;i<arr.length;i++) {
            tree[start+i] = arr[i];
        }

        for (int i=start/2;i!=0;i/=2) {
            for (int j=i;j<i*2;j++) {
                tree[j] = tree[j*2] + tree[j*2+1];
            }
        }
    }

    private void update (int index) {
        int node = start + index -1;
        tree[node] = 0;
        node/=2 ;

        while(node!=0) {
            tree[node] = tree[node*2] + tree[node*2+1];
            node /= 2;
        }
    }

    public int find (int index) throws Exception {
        leftIdx = index + 1;
        result = -1;
        query(1, 1, start);

        if (result==-1) throw new Exception();

        update(result);
        return result;
    }

    private void query (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return;

        if (tree[node]==0) return;

        if (leftIdx<= left && right <= rightIdx) {
            trace(node, left, right);
            return;
        }

        int mid = (left + right) / 2;
        query(node*2, left, mid);
        if (result==-1)
            query(node*2+1, mid+1, right);

    }

    private void trace (int node, int left, int right) {
        if (node>=start) {
            result = right;
            return;
        }

        int mid = (left + right) / 2;

        if (tree[node*2]!=0) {
            trace(node*2, left, mid);
        } else {
            trace(node*2+1, mid+1, right);
        }
    }

    public int sum (int left, int right) {
        leftIdx = left;
        rightIdx = right;
        int result = suum(1, 1, start);
        rightIdx = size;
        return result;
    }

    private int suum (int node, int left, int right) {
        if (right < leftIdx || rightIdx < left) return 0;

        if (leftIdx<= left && right <= rightIdx) {
            return tree[node];
        }

        int mid = (left + right) / 2;
        return suum(node*2, left, mid) +
            suum(node*2+1, mid+1, right);

    }

    public int getRemain () {
        return tree[1];
    }


}

class IMF {
    char who;
    int value;
    StringBuilder sb = new StringBuilder();
    IMF (char s, int value) {
        who = s;
        this.value = value;

        sb.append(who).append(" BLOCK ").append(value).append('\n');
    }
}

public class Main {
    public static String chain = " CHAIN ";
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int amount = Integer.parseInt(st.nextToken()) * 2;
        int blockAmount = Integer.parseInt(st.nextToken());
        int[] number = new int[amount];
        Arrays.fill(number, 1);

        IMF[] queries = new IMF[blockAmount];
        
        IMF num1 = null;

        for (int i=0;i<blockAmount;i++) {
            String[] arr = br.readLine().split(" ");
            int value = Integer.parseInt(arr[2]);
            queries[i] = new IMF(arr[0].charAt(0), value);

            if (number[value-1]==0) no();

            number[value-1] = 0;
            if (value==1) num1 = queries[i];
        }

        //ordered를 정렬한다.

        if (number[0]==1) no();

        Tree tree = new Tree(number);
        for (int i=0;i<queries.length-1;i++) {
            if (queries[i].who == queries[i+1].who) {
                try {
                    int value = tree.find(queries[i].value);
                    number[value-1] = 0;
                    queries[i].sb.append(change(queries[i].who))
                            .append(chain).append(value).append('\n');
                    queries[i].value = value;
                    queries[i].who = change(queries[i].who);
                } catch (Exception ex) {
                    no();
                }
            }
        }

        int standard = queries[queries.length-1].value;
        int value = -1;

        int remain = tree.getRemain();

        if (remain%2==1) {
            for (int i=standard;i<amount;i++) {
                if(number[i]==1) {
                    value = i;
                    break;
                }
            }

            if (value==-1) no();

            for (int i=0;i<number.length;i++) {
                if (i==value) {
                    queries[queries.length-1].sb.append(change(queries[queries.length-1].who))
                            .append(chain).append(value+1).append('\n');
                    queries[queries.length-1].who = change(queries[queries.length-1].who);
                    continue;
                }

                if (number[i]==1) {
                    char s = change(num1.who);
                    num1.sb.append(s).append(chain).append(i+1).append('\n');
                    num1.who = s;
                }
            }
        } else {
            for (int i=0;i<amount;i++) {
                if (number[i]==1) {
                    char s = change(num1.who);
                    num1.sb.append(s).append(chain).append(i+1).append('\n');
                    num1.who = s;
                }
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write("YES\n");
        for (int i=0;i< queries.length;i++) bw.write(queries[i].sb.toString());
        bw.flush();
    }

    public static char change (char who) {
        return who == 'A' ? 'B' : 'A';
    }

    public static void no () {
        System.out.println("NO");
        System.exit(0);
    }
}