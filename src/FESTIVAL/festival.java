package FESTIVAL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class festival {

    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static double C;
    static double N;
    static double[] field;
    static int[] memory;
    static int INF = 987654321;

    public static void main(String[] args) throws IOException {
        int size = Integer.parseInt(br.readLine());
        while (size-- > 0) {
            st = new StringTokenizer(br.readLine());
            C = Double.parseDouble(st.nextToken());
            N = Double.parseDouble(st.nextToken());

            field = new double[(int) C + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i < C + 1; i++) {
                field[i] = Integer.parseInt(st.nextToken()) + field[i - 1];
            }
            double result = field[(int) C] / C;

            for (int i = (int) N; i < C; i++) {
                for (int j = 0; j <= C - i; j++) {
                    result = Math.min(result, (field[j + i] - field[j]) / i);
                }
            }
            bw.write(result + "");
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }

}