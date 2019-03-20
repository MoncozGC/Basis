package day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test05 {
/*
 * 项目根目录下建立文件： user.txt，文件中存放用户名和登录密码，格式：用户名，密码,如：aaa,123
 *	  3.要求完成如下功能：
 *		程序运行时：控制台提示用户输入注册的用户名和密码；
 * 		验证键盘录入的用户名跟user.txt中已注册的用户名是否重复：
 *  			是：控制台提示：用户名已存在
 *  			否：将键盘录入的用户名及密码写入user.txt文件，并在控制台提示：注册成功； 
 * */
	public static void main(String[] args) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入用户名,密码");
		String str = sc.nextLine();

		String userName = str.split(",")[0];
		BufferedReader br = new BufferedReader(new FileReader("Test05.txt"));
		String len;
		while ((len = br.readLine()) != null) {
			if (list.contains(str)) {
				System.out.println("用户名已经存在");
			} else {
				list.add(len.split(",")[0]);
			}
		}
		br.close();

		if (list.contains(userName)) {
			System.out.println("已经存在");
		} else {
			FileWriter bw = new FileWriter("Test05.txt", true);
			bw.write("\r");
			bw.write(str);
			bw.write("\r\n");
			bw.close();
			System.out.println("注册成功");
		}
	}
}
