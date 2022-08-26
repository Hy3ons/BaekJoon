import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()), K = Integer.parseInt(br.readLine());
        HashSet<Integer> hs = new HashSet<>();
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i=0;i<N;i++) hs.add(Integer.parseInt(st.nextToken()));
        ArrayList<Integer> al = new ArrayList<>(hs);
        Collections.sort(al);

        ArrayList<Integer> arr = new ArrayList<>();
        for (int i=1;i<al.size();i++) {
            arr.add(Math.abs(al.get(i-1) - al.get(i)));
        }

        Collections.sort(arr, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        });

        int result = 0;
        for (int i=0;i<K-1 && i < arr.size();i++) {
            result += arr.get(i);
        }

        System.out.println(al.get(al.size()-1) - al.get(0) - result);
    }
}