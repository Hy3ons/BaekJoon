import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] ary = br.readLine().toCharArray();

        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> al
                , sum = new ArrayList<>()
                , minus = new ArrayList<>();
        al = sum;

        for (int i=0;i<ary.length;i++) {
            if (ary[i] == '-' || ary[i] == '+') {
                al.add(Integer.parseInt(sb.toString()));
                sb = new StringBuilder();

                if (ary[i] == '-') {
                    al = minus;
                }
                continue;
            }
            sb.append(ary[i]);

        }
        long result = 0;
        al.add(Integer.parseInt(sb.toString()));
        for (int e : sum) {
            result += e;
        }

        for (int e : minus) {
            result -= e;
        }
        System.out.println(result);


    }
}