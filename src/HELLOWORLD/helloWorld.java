package HELLOWORLD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class helloWorld {

    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int size;

    public static void main(String[] args) throws IOException {

        size = Integer.parseInt(br.readLine());

        while (size-- > 0) {
            String str = br.readLine();

            bw.write("Hello, ");
            bw.write(str);
            bw.write("!");
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }
}