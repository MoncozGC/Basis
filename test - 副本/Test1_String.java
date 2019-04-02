package com.heima.test;

import java.util.Scanner;

public class Test1_String {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请发言:");
		String s = sc.nextLine();
		
		// 1. 判断字符串中是否包含敏感词
		if(s.contains("TMD")){
			// 2. 处理  你TMD
			int index = s.indexOf("TMD");
			String newStr = s.substring(0, index);
			System.out.println(newStr + "***");
		}else{
			System.out.println(s);
		}
		
	}

	public static void method2() {
		String s = "itheima";
		// subString : 截取字符串, 从开始索引到结束索引, 包含头不包含尾
		String newStr = s.substring(0, 2);
		System.out.println(newStr);
		
		System.out.println("----------------");
		
		// subString : 从传入的索引位置开始截取, 截取到末尾
		String newStr2 = s.substring(2);
		System.out.println(newStr2);
	}
	
}
