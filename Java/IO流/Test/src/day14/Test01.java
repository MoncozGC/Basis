package day14;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Test01 {
	/*
	 * 一、	需求说明：以UTF-8编码的格式写出 “你好”到文件中, 并将数据正确的读取出来打印在控制台
	 * */
	public static void main(String[] args) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("Test01.txt"),"UTF-8");
		osw.write("你好");
		osw.close();
		InputStreamReader isw = new InputStreamReader(new FileInputStream("Test01.txt"),"UTF-8");
		int len;
		while((len = isw.read()) != -1){
			System.out.print((char)len);
		}
		isw.close();
	}
}
