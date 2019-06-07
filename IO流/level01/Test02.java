package level01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test02 {
	/*
	 * 需求说明：字符缓冲区流的特殊功能复制Java文件
	 * */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Test02.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("Test02_copy.txt"));
		
		String len;
		while((len = br.readLine()) != null){
			bw.write(len);
			bw.newLine();
		}
		br.close();
		bw.close();
	}
}
