import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static int mki(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }
    public static ArrayList<ArrayList<Integer>> al;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int people = mki(st);
        int amount = mki(st);
        al = new ArrayList<>(people+1);
        for (int i=0;i<=people;i++) al.add(new ArrayList<>());

        for (int i=1;i<=people;i++) {
            st = new StringTokenizer(br.readLine());
            int temp = mki(st);

            while (temp-->0) {
                int work = mki(st);

                al.get(i).add(work);
            }
        }
        take = new int[amount+1];
        visited = new int[amount+1];
        int count = 0;

        for (stack=1;stack<=people;stack++) {

            for (int j=0;j<al.get(stack).size();j++) {
                int work = al.get(stack).get(j);

                if (visited[work]==stack) continue;
                visited[work] = stack;

                if (take[work] == 0 || canAnother(take[work])) {
                    take[work] = stack;
                    count++;
                    break;
                }
            }
        }
        System.out.println(count);

    }
    public static int stack;
    public static int[] take, visited;
    public static boolean canAnother (int person) {
        for (int i=0;i<al.get(person).size();i++) {
            int work = al.get(person).get(i);

            if (visited[work]==stack) continue;
            visited[work] = stack;

            if (take[work] == 0 || canAnother(take[work])) {
                take[work] = person;
                return true;
            }
        }
        return false;
    }
}
