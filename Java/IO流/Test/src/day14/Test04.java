package day14;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class Test04 {
	/*
	 * 实现一个验证码小程序，要求如下： 1.
	 * 在项目根目录下新建一个文件：data.txt,键盘录入3个字符串验证码，并存入data.txt中，要求一个验证码占一行； 2.
	 * 键盘录入一个需要被校验的验证码，如果输入的验证码在data.txt中存在：在控制台提示验证成功，如果不存在控制台提示验证失败
	 */
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		LinkedHashSet<String> lhs = new LinkedHashSet<>();

		while (true) {
			System.out.println("请输入验证码:");
			String str = sc.nextLine();
			if (lhs.contains(str)) {
				System.out.println("已经存在");
			} else {
				lhs.add(str);
				if (lhs.size() == 3) {
					break;
				}
			}
		}
		FileWriter fw = new FileWriter("Test04.txt");
		for (String s : lhs) {
			fw.write(s);
			fw.write("\r\n");
			fw.flush();
		}
		fw.close();

		System.out.println("请输入校验的验证码是否存在:");
		String strs = sc.nextLine();
		BufferedReader br = new BufferedReader(new FileReader("Test04.txt"));
		ArrayList<String> list = new ArrayList<>();
		String len;
		while ((len = br.readLine()) != null) {
			list.add(len);
		}

		if (list.contains(strs)) {
			System.out.println("已经存在");
		} else {
			System.out.println("不存在");
		}
	}
}
