import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        length = sc.nextInt();
        visited = new boolean[N];
        memori = new int[length];

        dfs(0);
    }

    public static int N, length;
    public static boolean[] visited;
    public static int[] memori;
    public static void dfs (int depth) {
        if (depth==length) {
            StringBuilder sb = new StringBuilder();

            for (int i=0;i< memori.length;i++)
                sb.append(memori[i]+1+" ");

            System.out.println(sb);
            return;
        }

        for (int i=0;i<N;i++) {
            if (!visited[i]) {
                visited[i] = true;
                memori[depth] = i;
                dfs(depth+1);
                visited[i] = false;
            }
        }
    }
}