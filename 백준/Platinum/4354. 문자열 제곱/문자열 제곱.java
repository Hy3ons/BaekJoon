import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            char[] a = br.readLine().toCharArray();
            if (a[0]=='.') break;

            int[] fail = failureF(a);
            if (fail[a.length-1]==-1) {
                bw.write("1\n");
                continue;
            }

            for (int i=0;i<fail.length;i++) fail[i]++;

            K: for (int i=0;i<fail.length;i++) {
                if (fail[i]==1) {
                    int stack = i;
                    int max = Integer.MIN_VALUE;
                    for (int j=i;j<fail.length;j++) {
                        if (max<fail[j]) {
                            max = fail[j];
                        } else {
                            i = j-1;
                            continue K;
                        }
                    }

                    if (max%stack==0) {
                        int result = max/stack + 1;
                        bw.write(result+"\n");
                    } else {
                        bw.write("1\n");
                    }
                    break;
                }
            }

        }
        bw.flush();
    }

    public static int[] failureF (char[] arr) {
        int[] result = new int[arr.length];
        result[0] = -1;
        int pointer = 0;

        for (int i=1;i<arr.length;i++) {
            if (arr[pointer]==arr[i]) {
                result[i] = pointer++;
            } else {
                try {
                    pointer = result[pointer-1] + 1;

                    while (true) {
                        if (arr[pointer]==arr[i]) {
                            result[i] = pointer++;
                            break;
                        } else {
                            pointer = result[pointer];
                        }
                    }

                } catch (ArrayIndexOutOfBoundsException ex) {
                    pointer = 0;
                    result[i] = -1;
                }

            }
        }
        return result;
    }
}
