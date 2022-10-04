import javax.swing.*;
import java.io.*;
import java.util.*;

class Bus {
    int l, r, cost;
    Bus (int l, int r, int cost) {
        this.l = l;
        this.r = r;
        this.cost = cost;
    }
}

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken()), r = Integer.parseInt(st.nextToken());
        nn = r - l - 1;
        arr = new int[N*2];
        arr[l-1] = arr[r-1] = 1;

        recur(0, N);
        System.out.println(res);
    }
    public static int[] arr;
    public static int res, N, nn;

    public static void recur(int depth, int num) {
        if (num == nn) {
            recur(depth+1, num-1);
            return;
        }
        if (depth == N) {
            res++;
            return;
        }

        for (int i=0;i+num+1<arr.length;i++) {
            if (arr[i] == 0 && arr[i+num+1] == 0) {
                arr[i] = arr[i+num+1] = 1;
                recur(depth+1, num-1);
                arr[i] = arr[i+num+1] = 0;
            }
        }
    }
}