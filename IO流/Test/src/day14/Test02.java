package day14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test02 {
	/*
	 * 字符缓冲区流的特殊功能复制Java文件
	 * */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Test01.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("copy_02.txt"));
		
		String len;
		while((len = br.readLine()) != null){
			bw.write(len);
			bw.flush();
		}
		br.close();
		bw.close();
	}
}
