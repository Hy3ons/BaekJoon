import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[N];

        for (int i=0;i<arr.length;i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        boolean[][] preset = new boolean[N][N];

        for (int i=0;i<arr.length;i++) {
            boolean n = true;
            for (int l = i, r = i;l>=0 && r <arr.length;l--,r++) {
                if (n && arr[l] == arr[r]) {
                    preset[l][r] = n;
                } else {
                    break;
                }
            }
        }

        for (int i=0;i<arr.length-1;i++) {
            boolean n = arr[i] == arr[i+1];
            for (int l = i, r = i+1;l>=0 && r<arr.length;l--,r++) {
                if (n && arr[l] == arr[r]) {
                    preset[l][r] = n;
                } else {
                    break;
                }
            }
        }
        int quer = Integer.parseInt(br.readLine());

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while(quer-->0) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());
            bw.write((preset[n1-1][n2-1] ? 1 : 0)+"\n");
        }
        bw.flush();

    }
}