import java.io.*;
import java.util.*;

public class Main {
    public static int N, M;
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();

        num = new int[N];
        for (int i=0;i<N;i++) num[i] = sc.nextInt();

        Arrays.sort(num);

        memorize = new int[M];
        recursive(0,0);
        bw.flush();
    }
    public static int[] num;
    public static int[] memorize;
    public static HashSet<String> hs = new HashSet<>();

    public static void recursive (int depth, int start) throws IOException{
        if (depth == M) {
            StringBuilder sb = new StringBuilder();
            for(int e : memorize) sb.append(e+" ");
            if (hs.contains(sb.toString())) return;
            hs.add(sb.toString());

            bw.write(sb+"\n");
            return;
        }

        for (int i=start;i<N;i++) {
            memorize[depth] = num[i];
            recursive(depth+1, i+1);
        }
    }
}