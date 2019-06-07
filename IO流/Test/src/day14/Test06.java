package day14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Test06 {
	/*需求 : 项目根路径下有text.txt文件，内容如下
		我爱黑马
		123456
		
		利用IO流的知识读取text.txt文件的内容反转后写入text.txt文件中
		654321
		马黑爱我
*/
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Test06.txt"));
		ArrayList<String> list = new ArrayList<>();
		String len;
		while((len = br.readLine()) != null){
			String s = new StringBuilder(len).reverse().toString();
			list.add(s);
		}
		br.close();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("Test06.txt"));
		for(int i = list.size()-1;i>=0;i--){
			bw.write(list.get(i));
			bw.newLine();//换行
		}
		bw.close();
		System.out.println(list);
	}
}










