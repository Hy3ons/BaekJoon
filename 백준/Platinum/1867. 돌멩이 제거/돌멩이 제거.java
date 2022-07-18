import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int size = Integer.parseInt(st.nextToken());
        int amount = Integer.parseInt(st.nextToken());

        takes = new int[size+1];
        visited = new int[size+1];

        hope = new ArrayList<>(size+1);
        for (int i=0;i<=size;i++) hope.add(new ArrayList<>());

        for (int i=0;i<amount;i++) {
            st = new StringTokenizer(br.readLine());

            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            hope.get(n1).add(n2);
        }
        int result = 0;

        for (int i=1;i<=size;i++) {
            count = i;

            for (int row : hope.get(i)) {
                if (visited[row] == count) continue;
                visited[row] = count;

                if (takes[row] == 0 || dfs(takes[row])) {
                    takes[row] = i;
                    result++;
                    break;
                }
            }
        }
        System.out.println(result);
    }
    public static ArrayList<ArrayList<Integer>> hope;
    public static int[] takes, visited;
    public static int count;
    public static boolean dfs (int hang) {
        for (int row : hope.get(hang)) {
            if (visited[row] == count) continue;
            visited[row] = count;

            if (takes[row] == 0 || dfs (takes[row])) {
                takes[row] = hang;
                return true;
            }
        }
        return false;
    }
}