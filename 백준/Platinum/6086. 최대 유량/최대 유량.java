import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static int mki (String a) {
        return Integer.parseInt(a);
    }

    public static int charChange (char a) {
        if (a>='A'&&a<='Z') {
            return (a-'A');
        }

        if (a>='a'&&a<='z') {
            return (a-'a') + 26;
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = mki(br.readLine());
        al = new ArrayList<>();

        for (int i=0;i<52;i++) al.add(new ArrayList<Integer>());
        StringTokenizer st;

        for (int i=0;i<testcase;i++) {
            st = new StringTokenizer(br.readLine()," ");
            int from = charChange(st.nextToken().charAt(0));
            int to = charChange(st.nextToken().charAt(0));
            int pipe = mki(st.nextToken());


            al.get(from).add(to);
            al.get(to).add(from);
            load[from][to] += pipe;
            load[to][from] += pipe;

        }

        //A 는 0 이고 Z는 25번지로 변경하여 문제를 해결 너무 햇갈림. 0부터 51까지 노드 존재.
        function();

        System.out.println(result);

    }

    public static int[] re = new int[52] , station = new int[52];
    public static int[][] nowUsing = new int[52][52], load = new int[52][52];
    public static int result;
    public static ArrayList<ArrayList<Integer>> al;

    public static void function () {
        while (true) {
            Queue<Integer> qu = new LinkedList<>();
            qu.offer(0); //A를 박는거임.
            Arrays.fill(re, -1);

            while(!qu.isEmpty()) {
                int q = qu.poll();
                //q에서  i번째 노드로 가는거니까  temp로 가는거임... 제발
                for (int i=0;i<al.get(q).size();i++) {
                    int temp = al.get(q).get(i);
                    if (load[q][temp] - nowUsing[q][temp] > 0 && re[temp] == -1) {
                        qu.offer(temp);
                        re[temp] = q;

                        if (temp==25) break;
                    }
                }
            }

            if (re[25]== -1 ) return;

            int resultTemp = Integer.MAX_VALUE;

            //여기서 부터 백탐색 끝까지 도착을했다는거니까 음수의 유량도 체크해야함 미리 re에 기록해둠.
            for (int i=25;i!=0;i=re[i]) {
                // re[i]에서 i로 오는거임.왜냐면 내가 있던위치를 내가 간곳에 저장을 한게 re 니까
                resultTemp = Math.min(resultTemp, load[re[i]][i] - nowUsing[re[i]][i]);
            }

            for (int i=25;i!=0;i=re[i]) {
                //re[i]란.. 이전위치다.
                nowUsing[re[i]][i] += resultTemp;
                nowUsing[i][re[i]] -= resultTemp;

            }

            result += resultTemp;

        }
    }

}
