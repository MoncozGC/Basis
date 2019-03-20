package level02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Test03 {
	/*	需求 : 项目根路径下有text.txt文件，内容如下
		我爱黑马
		123456
		
		利用IO流的知识读取text.txt文件的内容反转后写入text.txt文件中
		654321
		马黑爱我
	 * */
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("Test03.txt"));
		ArrayList<String> list = new ArrayList<>();

		String str;
		while ((str = br.readLine()) != null) {
			String s = new StringBuffer(str).reverse().toString();
			list.add(s);
		}
		// System.out.println(list);
		BufferedWriter bw = new BufferedWriter(new FileWriter("Test03.txt"));

		for (int i = list.size() - 1; i >= 0; i--) {
			bw.write(list.get(i));
			bw.newLine();
		}

		bw.close();
		br.close();
	}
}
