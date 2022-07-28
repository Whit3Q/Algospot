package FOSIL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class fosil {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static ArrayList<Point> hull1, hull2;
    static ArrayList<Line> upper, lower;
    static int T;

    //i와 i+1의 Point를 확인하면서 아래 껍질과 윗 껍질로 나누기
    static void decompose(ArrayList<Point> hull) {
        int n = hull.size();
        for (int i = 0; i < n; i++) {
            if (hull.get(i).x < hull.get((i + 1) % n).x) {
                lower.add(new Line(hull.get(i), hull.get((i + 1) % n)));
            } else if (hull.get(i).x > hull.get((i + 1) % n).x) {
                upper.add(new Line(hull.get(i), hull.get((i + 1) % n)));
            }
        }
    }

    //x가 [a.x, b.x] 사이의 구간에 포함되는지 확인한다
    static boolean between(Point a, Point b, double x) {
        return (a.x <= x && x <= b.x) || (b.x <= x && x <= a.x);
    }

    //a,b 선분과 주어진 위치의 수직선이 교차하는 위치를 반환
    static double at(Point a, Point b, double x) {
        double dy = b.y - a.y; //기울기를 구하기 위해 dy
        double dx = b.x - a.x; //dx 계산
        return a.y + dy * (x - a.x) / dx; //두 점으로 그래프 그려서 대입
    }

    //두 다각형의 교집합을 수직선으로 잘랐을 때 단면의 길이를 반환한다.
    static double vertical(double x) {
        double minUp = 1e20;
        double maxLow = -1e20;
        //윗 껍질을 순회하며 최소 y좌표를 찾는다.
        for (int i = 0; i < upper.size(); i++) {
            if (between(upper.get(i).first, upper.get(i).second, x)) {
                minUp = Math.min(minUp, at(upper.get(i).first, upper.get(i).second, x));
            }
        }
        for (int i = 0; i < lower.size(); i++) {
            if (between(lower.get(i).first, lower.get(i).second, x)) {
                maxLow = Math.max(maxLow, at(lower.get(i).first, lower.get(i).second, x));
            }
        }

        return minUp - maxLow;
    }

    //가장 작은 x값 반환
    static double minX(ArrayList<Point> hull) {
        double x = Double.MAX_VALUE;
        for (int i = 0; i < hull.size(); i++) {
            x = Math.min(x, hull.get(i).x);
        }
        return x;
    }

    //가장 큰 x값 반환
    static double maxX(ArrayList<Point> hull) {
        double x = Double.MIN_VALUE;
        for (int i = 0; i < hull.size(); i++) {
            x = Math.max(x, hull.get(i).x);
        }
        return x;
    }

    static double solve() {
        double lo = Math.max(minX(hull1), minX(hull2));
        double hi = Math.min(maxX(hull1), maxX(hull2));

        if (hi < lo) {
            return 0;
        }

        for (int i = 0; i < 100; i++) {

            double aab = ((lo * 2.0) + hi) / 3.0;
            double abb = (lo + (2.0 * hi)) / 3.0;

            if (vertical(aab) < vertical(abb)) {
                lo = aab;
            } else {
                hi = abb;
            }
        }

        return Math.max(0.0, vertical(hi));
    }

    public static void main(String[] args) throws IOException {

        T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            hull1 = new ArrayList<>();
            hull2 = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                double x = Double.parseDouble(st.nextToken());
                double y = Double.parseDouble(st.nextToken());
                hull1.add(new Point(y, x));
            }
            upper = new ArrayList<>();
            lower = new ArrayList<>();
            decompose(hull1);
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < m; i++) {
                double x = Double.parseDouble(st.nextToken());
                double y = Double.parseDouble(st.nextToken());
                hull2.add(new Point(y, x));
            }
            decompose(hull2);
            double result = solve();
            if (result < 0.0) {
                bw.write("0.000000\n");
            } else {
                bw.write(String.format("%.10f", result) + "\n");
            }
        }


        bw.flush();
        bw.close();
    }

    static class Point {
        double x;
        double y;

        public Point(double y, double x) {
            this.x = x;
            this.y = y;
        }

    }

    static class Line {
        Point first;
        Point second;

        public Line(Point first, Point second) {
            this.first = first;
            this.second = second;
        }
    }

}