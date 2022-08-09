import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int egg = Integer.parseInt(st.nextToken());
        int people = Integer.parseInt(st.nextToken());

        ArrayList<Integer> arr = new ArrayList<>(people);

        for (int i=0;i<people;i++) arr.add(Integer.parseInt(br.readLine()));

        Collections.sort(arr, Comparator.reverseOrder());
        int max = 0;
        int persons = 0, price = 0;


        for (int i=0;i<arr.size();i++) {
            if (i >= egg) break;

            if (max <= (i+1) * arr.get(i) ) {
                max = (i+1) * arr.get(i);
                persons = i+1;
                price = arr.get(i);
            }
        }

        System.out.println(price + " "+(long) persons * price);
    }
}