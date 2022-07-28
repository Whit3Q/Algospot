package RATIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class ratio {

    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int size;
    static double game;
    static double winGame;
    static int target;
    static int INF = 2000000000;

    public static long solve(double game, double win) {
        target = (int) (((winGame * 100) / game)) + 1;

        return (long) Math.ceil((((target * game) - (100 * win)) / (100 - target)));
    }

    public static void main(String[] args) throws IOException {
        size = Integer.parseInt(br.readLine());

        while (size-- > 0) {
            st = new StringTokenizer(br.readLine());
            game = Long.parseLong(st.nextToken());
            winGame = Long.parseLong(st.nextToken());

            long result = solve(game, winGame);
            if (result < 0) {
                bw.write("-1");
            } else {
                bw.write(result + "");
            }

            bw.newLine();
        }

        bw.flush();
        bw.close();
    }

}