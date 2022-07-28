package POTION;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class potion {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static int T;
    static int N;

    static int gcd(int p, int q) {
        if (q == 0) return p;
        else return gcd(q, p % q);
    }

    //분수 a/b보다 같거나 큰 최소의 자연수를 반환한다.
    static int ceil(int a, int b) {
        return (a + b - 1) / b;
    }

    static ArrayList<Integer> solve(ArrayList<Integer> recipe, ArrayList<Integer> put) {
        int n = recipe.size();
        //모든 recipe의 최대공약수를 구한다.
        int b = recipe.get(0);
        for (int i = 1; i < n; i++) {
            b = gcd(recipe.get(i), b);
        }
        //최소한 b/b = 1배는 만들어야 하므로, a의 초기 값을 b로 둔다.
        int a = b;
        //X배를 직접 구하는 대신 ceil(put.get(i) * b , recipe.get(i))의 최대값을 구한다
        for (int i = 0; i < n; i++) {
            a = Math.max(a, ceil(put.get(i) * b, recipe.get(i)));
        }

        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(recipe.get(i) * a / b - put.get(i));
        }

        return result;
    }

    public static void main(String[] args) throws IOException {

        T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            N = Integer.parseInt(br.readLine());
            ArrayList<Integer> recipe = new ArrayList<>();//병에 필요한 약의 비율
            st = new StringTokenizer(br.readLine()); // 1병에 필요한 약의 비율
            while (st.hasMoreTokens()) {
                recipe.add(Integer.parseInt(st.nextToken()));
            }
            ArrayList<Integer> put = new ArrayList<>();
            st = new StringTokenizer(br.readLine()); // 1병에 필요한 약의 비율
            while (st.hasMoreTokens()) {
                put.add(Integer.parseInt(st.nextToken()));
            }

            ArrayList<Integer> result = solve(recipe, put);

            for (int num : result) {
                bw.write(num + " ");
            }
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }
}