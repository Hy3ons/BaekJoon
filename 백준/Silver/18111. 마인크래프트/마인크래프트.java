import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        final int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
        int inventory = Integer.parseInt(st.nextToken());
        
        int[] height = new int[a*b];
        
        for (int i=0;i<a;i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<b;j++) {
                height[i*b+j] = Integer.parseInt(st.nextToken());
            }
        }
        int time = Integer.MAX_VALUE;
        int ans = 0;
        
        for (int h=0;h<257;h++) {
            int nowTime = 0;
            int inv = inventory, air = 0;
            for (int e : height) {
                if (e < h) {
                    air += h-e;
                    nowTime += h - e;
                } else {
                    inv += e-h;
                    nowTime += e-h << 1;
                }
            }
            
            if (air <= inv && nowTime <= time) {
                time = nowTime;
                ans = h;
            }
        }
        
        System.out.println(time+" "+ans);
    }
}