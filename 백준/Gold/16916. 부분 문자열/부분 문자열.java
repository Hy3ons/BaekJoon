import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] a = br.readLine().toCharArray(),
                b = br.readLine().toCharArray();

        if (a.length<b.length) {
            System.out.println(0);
            return;
        }

        int[] fail = failureF(b);
        int point = 0;

        K: for (int i=0;i<a.length;i++) {
            if (b[point]==a[i]) {
                for (int j=point;j<b.length;j++) {
                    try {
                        if (b[j]!=a[j+i-point]) {
                            i = j+i-point-2;
                            point = fail[j-1];

                            if (point==-1) {
                                point = 0;
                                i++;
                            }
                            continue K;
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        //여기서 예외가 발생할경우 a 에서 발생한것임. 따라서 탐색범위 끝까지 못찾았다는 것이다.
                        break K;
                    }
                }
                System.out.println(1);
                return;
            }
        }
        System.out.println(0);



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
                            pointer = result[pointer]; // + 1;
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
