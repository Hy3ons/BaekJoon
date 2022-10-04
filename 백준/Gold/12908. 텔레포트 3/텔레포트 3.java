import javax.swing.*;
import java.io.*;
import java.util.*;

class Point {
    int x, y, idx;
    Point (int x, int y, int idx) {
        this.x=x;this.y=y;
        this.idx = idx;
    }
}

public class Main{
    public static ArrayList<ArrayList<Point>> load = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st=  new StringTokenizer(br.readLine());
        int node = 0;

        for (int i=0;i<100;i++) load.add(new ArrayList<>());

        Point[] all = new Point[10];

        Point start = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), node++);
        st = new StringTokenizer(br.readLine());
        Point end = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), node++);
        all[start.idx] = start;
        all[end.idx] = end;

        Point[][] tel = new Point[3][2];
        long[][] cost = new long[10][10];
        for (long[] d : cost) Arrays.fill(d, Integer.MAX_VALUE);

        for (int i=0;i<3;i++) {
            st = new StringTokenizer(br.readLine());

            tel[i][0] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), node++);
            tel[i][1] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), node++);

            all[tel[i][0].idx] = tel[i][0];
            all[tel[i][1].idx] = tel[i][1];

            cost[tel[i][1].idx][tel[i][0].idx] = cost[tel[i][0].idx][tel[i][1].idx] = Math.min(10, getDist(tel[i][0], tel[i][1]));
        }

        for (int i=0;i<8;i++) {
            for (int j=0;j<8;j++) {
                cost[i][j] = Math.min(cost[i][j], getDist(all[i], all[j]));
            }
        }


        for(int k = 0; k < 8; k++) {
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    if(cost[i][j] > cost[i][k]+cost[k][j]) {
                        cost[i][j] = cost[i][k]+cost[k][j];
                    }
                }
            }
        }

        System.out.println(cost[0][1]);

    }


    public static int getDist (Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}