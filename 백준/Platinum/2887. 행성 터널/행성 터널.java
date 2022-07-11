import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Load {
    long value;
    int p1, p2;
    Load (Point pp1, Point pp2) {
        value = getValue(Math.abs(pp1.x - pp2.x), Math.abs(pp2.y - pp1.y), Math.abs(pp2.z - pp1.z));
        this.p1 = pp1.index;
        this.p2 = pp2.index;
    }

    static long getValue (long n1, long n2, long n3) {
        return Math.min(Math.min(n1, n2), n3);
    }
}

class Point {
    ArrayList<Point> rival = new ArrayList<>();
    int x, y, z, index;
    Point(int x, int y, int z, int index) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.index = index;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        final int V = Integer.parseInt(br.readLine());

        Point[] pointers = new Point[V];
        ArrayList<Load> loads = new ArrayList<>();

        for (int i=0;i<V;i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int n3 = Integer.parseInt(st.nextToken());

            pointers[i] = new Point(n1, n2, n3, i);
        }

        Point[] xSort = pointers.clone()
                ,ySort = pointers.clone()
                ,zSort = pointers.clone();

        Arrays.sort(xSort, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Integer.compare(o1.x, o2.x);
            }
        });
        Arrays.sort(ySort, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Integer.compare(o1.y, o2.y);
            }
        });
        Arrays.sort(zSort, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Integer.compare(o1.z, o2.z);
            }
        });

        for (int i=0;i< pointers.length;i++) {
            if (i != pointers.length-1) {
                xSort[i].rival.add(xSort[i+1]);
                ySort[i].rival.add(ySort[i+1]);
                zSort[i].rival.add(zSort[i+1]);
            }
            if (i!=0) {
                xSort[i].rival.add(xSort[i-1]);
                ySort[i].rival.add(ySort[i-1]);
                zSort[i].rival.add(zSort[i-1]);
            }
        }

        for (int i=0;i< pointers.length;i++) {
            for (int j=0;j<pointers[i].rival.size();j++) {
                loads.add(new Load(pointers[i], pointers[i].rival.get(j)));
            }
        }

        int[] parent = new int[V];
        for (int i=1;i<parent.length;i++) parent[i] = i;
        long result = 0;

        Collections.sort(loads, new Comparator<Load>() {
            @Override
            public int compare(Load o1, Load o2) {
                return Long.compare(o1.value, o2.value);
            }
        });

        for (int i=0;i<loads.size();i++) {
            if (find(parent, loads.get(i).p1) == find(parent, loads.get(i).p2)) continue;

            union(parent, loads.get(i).p1, loads.get(i).p2);
            result += loads.get(i).value;
        }
        System.out.println(result);

    }
    public static int find (int[] parent, int node) {
        if (parent[node]==node) return node;
        return parent[node] = find(parent, parent[node]);
    }
    public static void union (int[] parent, int n1, int n2) {
        int p1 = find(parent, n1);
        int p2 = find(parent, n2);

        if (p1 < p2) {
            parent[p2] = p1;
        } else {
            parent[p1] = p2;
        }
    }

}
