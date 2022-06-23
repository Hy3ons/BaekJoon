import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        HashSet<String> hs = new HashSet<>();
        for (int i=0;i<testcase;i++) {
            hs.add(br.readLine());
        }

        ArrayList<String> al = new ArrayList<>(hs);

        Collections.sort(al, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length()==o2.length()) {
                    return o1.compareTo(o2);
                } else {
                    return o1.length()-o2.length();
                }
            }
        });

        for (String s : al) {
            System.out.println(s);
        }
    }
}
