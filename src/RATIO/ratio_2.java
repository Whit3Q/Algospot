package RATIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class ratio_2 {

    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int size;
    static double game;
    static double winGame;
    static int target;
    static int INF = 2000000000;

    public static long solve(long start, long end) {
        if (start >= end) {
            return end;
        }
        long mid = (start + end) / 2;

        long ratio = (long) ((((winGame + mid) * 100.0) / (game + mid)));

        if (ratio >= target) {
            return solve(start, mid);
        } else {
            return solve(mid + 1, end);
        }
    }

    public static void main(String[] args) throws IOException {
        size = Integer.parseInt(br.readLine());

        while (size-- > 0) {
            st = new StringTokenizer(br.readLine());
            game = Double.parseDouble(st.nextToken());
            winGame = Double.parseDouble(st.nextToken());

            target = (int) (((winGame * 100) / game)) + 1;

            if (((long) ((winGame + INF) / (game + INF) * 100)) < target) {
                bw.write("-1");
                bw.newLine();
                continue;
            }

            bw.write(solve(1, INF - 1) + "");
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }

}