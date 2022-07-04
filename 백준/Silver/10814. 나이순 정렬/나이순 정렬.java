import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class inf {
    int age;
    String name;
    inf (int age, String name) {
        this.age =age;
        this.name = name;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());

        inf[] im = new inf[testcase];


        for (int i=0;i<testcase;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            im[i] = new inf(Integer.parseInt(st.nextToken()), st.nextToken());
        }

        Arrays.sort(im, new Comparator<inf>() {
            @Override
            public int compare(inf o1, inf o2) {
                if (o1.age>o2.age) {
                    return 1;
                } else if (o1.age<o2.age) {
                    return -1;
                }else {
                    return 0;
                }
            }
        });

        StringBuilder sb = new StringBuilder();
        for (int i=0;i<testcase;i++) {
            sb.append(im[i].age+" "+im[i].name+"\n");
        }
        System.out.println(sb.toString());

    }
}
