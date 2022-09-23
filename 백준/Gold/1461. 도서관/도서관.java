import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()), m = Integer.parseInt(st.nextToken());

        ArrayList<Integer> mins = new ArrayList<>(), plus = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for (int i=0;i<n;i++) {
            int temp = Integer.parseInt(st.nextToken());
            if(temp<0) {
                mins.add(-temp);
            } else {
                plus.add(temp);
            }
        }

        plus.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        });

        mins.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        });


        long res = 0;
        ArrayList<Integer> ansP = new ArrayList<>(), ansM = new ArrayList<>();
        for (int i=0;i<plus.size();i+=m) {
            ansP.add(plus.get(i));
        }

        for (int i=0;i<mins.size();i+=m) {
            ansM.add(mins.get(i));
        }
        Collections.sort(ansP);
        Collections.sort(ansM);

        if (ansP.size() == 0) {
            for (int i=0;i< ansM.size()-1;i++) {
                res += ansM.get(i) << 1;
            }
            res += last(ansM);
            System.out.println(res);
            return;
        } else if (ansM.size() == 0) {
            for (int i=0;i< ansP.size()-1;i++) {
                res += ansP.get(i) << 1;
            }
            res += last(ansP);
            System.out.println(res);
            return;
        }

        res += Math.min(ansM.get(ansM.size()-1), ansP.get(ansP.size()-1)) << 1;
        for (int i=0;i< ansM.size()-1;i++) {
            res += ansM.get(i) << 1;
        }
        for (int i=0;i < ansP.size()-1;i++) {
            res += ansP.get(i) << 1;
        }


        res += Math.max(last(ansM), last(ansP));
        System.out.println(res);
    }
    public static int last (ArrayList<Integer> a) {
        return a.get(a.size()-1);
    }
}