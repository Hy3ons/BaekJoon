import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] a = br.readLine().split(" ");
		
		long min = Math.min(Long.parseLong(a[0]), Long.parseLong(a[1]));
		long max = Math.max(Long.parseLong(a[0]), Long.parseLong(a[1]));
		
		int result = 0;
		
		int ary = 3;
		long[] pn = new long[200000];
		
		Arrays.fill(pn, 0);
		pn[0] = 4;
		pn[1] = 9;
		pn[2] = 25;
		G: for (long i=7;i<=Math.pow(max, 0.5)+5;i+=2) {
			for (int j=3; j<=Math.sqrt(i);j+=2) {
				if (i%j==0) {
					continue G;
				}
			}
			pn[ary] = i*i;
			ary++;
		}
		
		boolean[] eratos = new boolean[(int)(max-min)+1];
		Arrays.fill(eratos, true);
		
		G:for (int i=0; i<ary; i++) {
			
			long beta = pn[i]-(min%pn[i]);
			
			if (beta==pn[i]) {
				eratos[0] = false;
			}
			for (int j=0 ;(beta+j*pn[i])<eratos.length;j++) {
				eratos[(int)(beta+j*pn[i])] = false;
			}
		}
		
		
		for (boolean g : eratos) {
			if (g==true) {
				result++;
			}
		}
		
		System.out.println(result);
	}
}
