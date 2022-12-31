import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        int[][] arr = new int[3][N];

        for (int i=0;i<3;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j=0;j<N;j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int a = N-1, b = N-1, c = N-1;

        ArrayList<Integer> al = new ArrayList<>();

        boolean[] used = new boolean[N+1];

        for (int i=0;i<N;i++) {
            while(used[arr[0][a]]) a--;
            while(used[arr[1][b]]) b--;
            while(used[arr[2][c]]) c--;

            if (arr[0][a] == arr[1][b] && arr[1][b] == arr[2][c]) {
                al.add(arr[0][a]);
                used[arr[0][a]] = true;
                a--;
                b--;
                c--;
            } else if (arr[0][a] == arr[1][b]) {
                al.add(arr[0][a]);
                used[arr[0][a]] = true;
                a--;
                b--;
            } else if (arr[1][b] == arr[2][c]) {
                al.add(arr[2][c]);
                used[arr[2][c]] = true;
                b--;
                c--;
            } else if (arr[0][a] == arr[2][c]) {
                al.add(arr[0][a]);
                used[arr[0][a]] = true;
                a--;
                c--;
            }


        }

        Collections.reverse(al);
        for (int e : al) bw.write(e+" ");
        bw.flush();
    }
}