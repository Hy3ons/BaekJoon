import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HashSet<Integer> hs = new HashSet<>();
        for (int i=0;i<10;i++) hs.add(Integer.parseInt(br.readLine())%42);
        System.out.println(hs.size());
    }
}
