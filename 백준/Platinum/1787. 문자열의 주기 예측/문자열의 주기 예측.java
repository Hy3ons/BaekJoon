import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testcase = Integer.parseInt(br.readLine());
        char[] s = br.readLine().toCharArray();
        int[] fail = failure(s);

//        Arrays.stream(fail).forEach(a -> System.out.print(a+" "));
//        System.out.println();
//        if (true) return;

        long result = 0;

        for (int i=1;i<fail.length;i++) {
            if (fail[i]==0) continue;

            int reW = i+1-fail[i];
            result += reW;
//            System.out.printf("%d 번째에서 %d 더함\n", i+1, reW);
        }

        System.out.println(result);

    }

    public static int[] failure (char[] arr) {
        int[] fail = new int[arr.length];
        int[] result = new int[arr.length];

        int j = 0;
        for (int i=1;i<arr.length;i++) {
            while(j > 0 && arr[i]!=arr[j]) {
                j = fail[j-1];
            }

            if (arr[i]==arr[j]) {
                fail[i] = ++j;
            }
        }

        for (int i=1;i<arr.length;i++) {
            if (fail[i]==0) continue;

                /*
                미리 최소 접두사로 가는 인덱스를 보유 하고 있다면 그 전 인덱스로 돌아가 그 인덱스의 접두사가
                가지고 있는 인덱스를 사용 하는 것으로 동일 한 계산을 여러번 하지 않아도 된다.
                즉 i는 아래서 부터 시작해야한다.

                즉 result 함수에 최소접두사의 길이를 넣어놓으면 나중에 계산할때 해당 단어 길이에서  result
                함수를 빼 반복하는 부분을 제외한 최대 남은 길이 단어가 반복될 수 있는 최대의 단어가 된다.

                특정 i 인덱스에서 fail함수 전으로 한번 재귀하여 돌아가면, 전에 반복 되었던 인덱스의 동일한
                char 값의 인덱스가 나오는데, 이때 0 일경우는 실패함수 그자체를 넣어주어야 한다.

                처음 나오는 단어일 경우 fail[i]는 0이 되는데 fail[0] 은 0 이므로 result[i] = fail[i]를
                넣어주어 괜찮다.

                 */
            if (fail[fail[i]-1]!=0) {
                result[i] = result[fail[i]-1];
            } else {
                result[i] = fail[i];
            }
        }


        return result;
    }
}
