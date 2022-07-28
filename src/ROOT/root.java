package ROOT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class root {

    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;
    static Integer degree;
    static BigDecimal INF = new BigDecimal(2000000000);
    static BigDecimal TWO = new BigDecimal(2);
    static BigDecimal FOUR = new BigDecimal(4);
    static BigDecimal minusOne = new BigDecimal(-1);

    public static ArrayList<BigDecimal> formula(int degree, ArrayList<BigDecimal> coefficient) { // 근 구하기
        ArrayList<BigDecimal> result = new ArrayList<>();
        if (degree == 1) {
            BigDecimal a = coefficient.get(0);
            BigDecimal b = coefficient.get(1);
            result.add(b.divide(a,10,BigDecimal.ROUND_DOWN).multiply(minusOne));
            return result;
        }
        if (degree == 2) {
            BigDecimal a = coefficient.get(0);
            BigDecimal b = coefficient.get(1);
            BigDecimal c = coefficient.get(2);


            MathContext mc = new MathContext(2);
//            BigDecimal sqrt = b.multiply(b).subtract(a.multiply(c).multiply(FOUR)).sqrt(mc);
            BigDecimal sqrt = b.multiply(b).subtract(a.multiply(c).multiply(FOUR));
            double tmp = Math.sqrt(sqrt.doubleValue());
            sqrt = new BigDecimal(tmp);
            BigDecimal result1 = b.multiply(minusOne).add(sqrt).divide(a.multiply(TWO),10,BigDecimal.ROUND_DOWN);
            BigDecimal result2 = b.multiply(minusOne).subtract(sqrt).divide(a.multiply(TWO),10,BigDecimal.ROUND_DOWN);

            result.add(result1);
            result.add(result2);
            Collections.sort(result);
            return result;
        }

        ArrayList<BigDecimal> diff = differential(coefficient); // 미분
        ArrayList<BigDecimal> inclinationZero = formula(degree - 1, diff); // 미분한 근 구하기 - 기울기가 0인 포인트

        //한계 범위 설정
        BigDecimal L = inclinationZero.get(0).subtract(new BigDecimal(25));
        inclinationZero.add(0,L);
        L = inclinationZero.get(inclinationZero.size() - 1).add(new BigDecimal(25));
        inclinationZero.add(L);

        for (int i = 0; i < inclinationZero.size() - 1; i++) {
            BigDecimal tmp = binarySearch(inclinationZero.get(i), inclinationZero.get(i+1), coefficient,0);
            if(tmp.compareTo(INF) != 0) {
                result.add(tmp);
            }
        }

        return result;
    }

    public static ArrayList<BigDecimal> differential(ArrayList<BigDecimal> coefficient) { // 미분
        ArrayList<BigDecimal> tmp = new ArrayList<>();
        int length = coefficient.size();
        for (int i = 0; i < length - 1; i++) {
            tmp.add(coefficient.get(i).multiply(new BigDecimal(length-i-1)));
        }

        return tmp;
    }

    private static void swap(BigDecimal localA, BigDecimal localB) {
        BigDecimal tmp = localA;
        localA = localB;
        localB = tmp;
    }

    public static BigDecimal binarySearch(BigDecimal start, BigDecimal end, ArrayList<BigDecimal> coefficient, int count) { //binarySearch
        if(count == 100) {
            return start.add(end).divide(TWO,12,BigDecimal.ROUND_DOWN);
        }

        BigDecimal tmp1 = function(start, coefficient);
        BigDecimal tmp2 = function(end, coefficient);

        if(tmp1.compareTo(tmp2) > 0 ) {
            swap(start, end);
            swap(tmp1, tmp2);
        }

        //오류 수정
        if(tmp1.multiply(tmp2).compareTo(BigDecimal.ZERO) > 0) {
            return INF;
        }


//        if(start <= 0 && end >= 0) {
//            if(function(0,coefficient) == 0) {
//                return 0.0;
//            }
//        }

//        BigDecimal mid = Math.(start + end) / 2.0D;
        BigDecimal mid = start.add(end).divide(TWO,12,BigDecimal.ROUND_DOWN);
//        BigDecimal midValue = function(mid, coefficient);
        BigDecimal midValue = function(mid, coefficient);

        if (midValue.compareTo(BigDecimal.ZERO) == 0) {
            return mid;
        }

        if (tmp1.multiply(midValue).compareTo(BigDecimal.ZERO) > 0) {
            return binarySearch(mid, end, coefficient,count+1);
        } else {
            return binarySearch(start, mid, coefficient,count+1);
        }
    }


    public static BigDecimal function(BigDecimal num, ArrayList<BigDecimal> coefficient) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < coefficient.size(); i++) {
            result = result.add(coefficient.get(i).multiply(num.pow(coefficient.size()-i-1)));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        while (N-- > 0) {
            ArrayList<BigDecimal> coefficient = new ArrayList<>();
            degree = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            while (st.hasMoreTokens()) {
                coefficient.add(new BigDecimal(st.nextToken()));
            }
            coefficient = formula(degree, coefficient);
            Collections.sort(coefficient);
            for (BigDecimal num : coefficient) {
                bw.write(num.toPlainString() + " ");
            }
            bw.newLine();
        }


        bw.flush();
        bw.close();
    }

}