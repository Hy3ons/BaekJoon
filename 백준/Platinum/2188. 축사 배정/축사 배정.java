import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int cow = Integer.parseInt(st.nextToken());
        int room = Integer.parseInt(st.nextToken());

        hope = new ArrayList<>(cow+1);
        for (int i=0;i<=cow;i++) hope.add(new ArrayList<>());
        visited = new int[room+1];
        takes = new int[room+1];

        for (int i=1;i<=cow;i++) {
            st = new StringTokenizer(br.readLine());
            int length = Integer.parseInt(st.nextToken());

            while (length-->0)
                hope.get(i).add(Integer.parseInt(st.nextToken()));

        }
        int result = 0;

        for (int i=1;i<=cow;i++) {
            count = i;
            for (int rooms : hope.get(i)) {
                if (visited[rooms]==count) continue;
                visited[rooms] = count;

                if (takes[rooms] == 0 || dfs(takes[rooms])) {
                    takes[rooms] = i;
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
    public static boolean dfs (int cow) {
        for (int room : hope.get(cow)) {
            if (visited[room] == count) continue;
            visited[room] = count;

            if (takes[room] == 0 || dfs (takes[room])) {
                takes[room] = cow;
                return true;
            }
        }
        return false;
    }
}