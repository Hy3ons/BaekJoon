import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine())
                , st2 = new StringTokenizer(br.readLine());

        int[] ary1 = new int[testcase+1], ary2= new int[testcase];

        for (int i=0;i<testcase;i++) {
            ary1[Integer.parseInt(st.nextToken())] = i+1;
            ary2[i] = Integer.parseInt(st2.nextToken());
        }

        for (int i=0;i<ary2.length;i++) {
            int index = low_bound(ary1[ary2[i]]);

            if (index==al.size()) {
              al.add(ary1[ary2[i]]);
            } else {
                al.set(index, ary1[ary2[i]]);
            }
        }
        System.out.println(al.size());


    }
    public static ArrayList<Integer> al = new ArrayList<>();

    public static int low_bound (int goal) {
        if (al.size()==0) return 0;

        int left = -1;
        int right = al.size();
        while(true) {
            int mid = (left+right)/2;

            if (al.get(mid)>=goal) {
                right = mid;
            } else left = mid;

            if (Math.abs(right-left)==1) {
                return right;
            }
        }
    }
}
