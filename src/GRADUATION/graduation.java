package GRADUATION;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class graduation {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static int INF = 987654321;
    static int T;
    static int N;
    static int K;
    static int M;
    static int L;
    static ArrayList<Subject> subjects; // 과목리스트
    static ArrayList<ArrayList<Integer>> term; // 학기 열리는 과목 리스트
    static boolean[] finishSubjects; // 수료한 과목 리스트

    public static int recursive(int termNum, int index, int count, int total) {
        if (termNum > M) {
            return INF;
        }

        if(index >= term.get(termNum).size()) {
            return INF;
        }

        int result = INF;
        for (int i = index + 1; i < term.get(termNum).size(); i++) {
            if (subjects.get(term.get(termNum).get(i)).check() && !finishSubjects[i]) {
                finishSubjects[i] = true;
                result = Math.min(result, recursive(termNum, i, count + 1, total));
                finishSubjects[i] = false;
                result = Math.min(result, recursive(termNum, i, count, total));
            }
        }

        return result;
    }


    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            subjects = new ArrayList<>(N + 1);

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int num = Integer.parseInt(st.nextToken());
                subjects.add(new Subject(num));
                for (int j = 0; j < num; j++) {
                    subjects.get(i).add(Integer.parseInt(st.nextToken()));
                }
            }

            term = new ArrayList<>();
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int num = Integer.parseInt(st.nextToken());
                term.add(new ArrayList<>());
                for (int j = 0; j < num; j++) {
                    term.get(i).add(Integer.parseInt(st.nextToken()));
                }
            }
            finishSubjects = new boolean[N];
            bw.write(String.valueOf(recursive(0, -1, 0, 0)));
            bw.newLine();
        }


        bw.flush();
        bw.close();
    }

    static class Point {
        int T;
        int P;

        public Point(int T, int P) {
            this.T = T;
            this.P = P;
        }
    }

    static class Subject {
        int size;
        HashSet<Integer> list; // 선수과목 리스트

        public Subject(int size) {
            list = new HashSet<>();
            this.size = size;
        }

        public boolean add(int num) {
            return list.add(num);
        }

        public boolean check() {
            for (int num : list) {
                if (!finishSubjects[num]) {
                    return false;
                }
            }
            return true;
        }
    }
}