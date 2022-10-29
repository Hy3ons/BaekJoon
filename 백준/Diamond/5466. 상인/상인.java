import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Store {
    int day, loc, gain;
    long l, r;
    Store (int day, int loc, int gain) {
        this.day = day;
        this.loc = loc;
        this.gain = gain;
        l = r = Long.MIN_VALUE;
    }
}

class Seg {
    private long[] up, down;
    int start = 1;
    public static long MIN = -100000000000000000L;
    Seg () {
        while(start < 500000) start <<= 1;
        up = new long[start << 1];
        down = new long[start << 1];
        Arrays.fill(up, MIN);
        Arrays.fill(down, MIN);
    }

    void update (int idx, long value, int mode) {
        int i = idx + start - 1;
        if ((mode & 1) == 1)
            up[i] = Math.max(up[i], value - Main.U * idx);
        if((mode & 2) == 2)
            down[i] = Math.max(down[i], value + Main.D * idx);

        while((i >>= 1) != 0) {
            if (((mode & 1) == 1))
                up[i] = Math.max(up[i << 1], up[i << 1 | 1]);
            if (((mode &2) == 2))
                down[i] = Math.max(down[i << 1], down[i << 1 | 1]);
        }
    }

    public long queryUP (int node, int s, int e, int left, int right) {
        if (e < left || right < s) return MIN;

        if (s <= left && right <= e) return up[node];
        int mid = left + right >> 1;

        return Math.max(queryUP(node << 1, s, e, left, mid)
                , queryUP(node << 1 | 1, s, e, mid+1, right));
    }

    public long queryDown (int node, int s, int e, int left, int right) {
        if (e < left || right < s) return MIN;

        if (s <= left && right <= e) return down[node];

        int mid = left + right >> 1;

        return Math.max(queryDown(node << 1, s, e, left, mid)
                , queryDown(node << 1 | 1, s, e, mid+1, right));
    }
}

public class Main {
    public static int MAX = 500500;
    public static long U, D;
    public static StringTokenizer st;
    public static int mki() {
        return Integer.parseInt(st.nextToken());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        int N = mki();
        U = mki(); D = mki();
        int start = mki();

        ArrayList<ArrayList<Store>> stores = new ArrayList<>(MAX);
        for (int i=0;i<MAX;i++) stores.add(new ArrayList<>());

        for (int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            Store store = new Store(mki(), mki(), mki());
            stores.get(store.day).add(store);
        }

        tree.update(start, 0, 3);

        for (ArrayList<Store> al : stores) {
            if (al.isEmpty()) continue;

            al.sort(new Comparator<Store>() {
                @Override
                public int compare(Store o1, Store o2) {
                    return Integer.compare(o1.loc, o2.loc);
                }
            });

            //같은 날 처리를 해야한다.

            al.get(0).l = getAnswer(al.get(0));
            al.get(al.size()-1).r = getAnswer(al.get(al.size()-1));
            for (int i=1;i<al.size();i++) {
                al.get(i).l = Math.max(getAnswer(al.get(i)), al.get(i).gain + al.get(i-1).l
                        - (al.get(i).loc - al.get(i-1).loc) * D);
            }

            for (int i=al.size()-2;i>=0;i--) {
                al.get(i).r = Math.max(getAnswer(al.get(i)), al.get(i).gain + al.get(i+1).r
                        - (al.get(i+1).loc - al.get(i).loc) * U);
            }

            for (int i=0;i<al.size();i++) {
                tree.update(al.get(i).loc, Math.max(al.get(i).l, al.get(i).r), 3);
            }
        }
        System.out.println(getAnswer(new Store(-1, start, 0)));

    }
    public static Seg tree = new Seg();
    public static long getAnswer (Store store) {
        int location = store.loc;
        long findLeft = tree.queryDown(1, 1, location, 1, tree.start);
        long findRight = tree.queryUP(1, location, tree.start, 1, tree.start);

        long nowGain = store.gain + Math.max(findLeft - D * store.loc
                , findRight + U * store.loc);

        return nowGain;
    }
}
