package com.heima.test;

import java.util.Scanner;

public class Test2_String {
	/*
	 * 需求: 键盘接受学员信息, 格式为: 数学成绩,英语成绩,语文成绩
	 * 
	 * 将数据接收完毕之后, 封装成成绩对象
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入成绩,格式为:数学成绩,英语成绩,语文成绩");
		String s = sc.nextLine();
		
		String[] sArr = s.split(",");		// sArr[0]  sArr[1]  sArr[2]
		
		int math = Integer.parseInt(sArr[0]);
		int english = Integer.parseInt(sArr[1]);
		int chinese = Integer.parseInt(sArr[2]);
		
		Score ss = new Score(math, english, chinese);
		System.out.println(ss);
		
	}
}

class Score {
	private int math;
	private int english;
	private int chinese;

	public Score(int math, int english, int chinese) {
		super();
		this.math = math;
		this.english = english;
		this.chinese = chinese;
	}

	public Score() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getEnglish() {
		return english;
	}

	public void setEnglish(int english) {
		this.english = english;
	}

	public int getChinese() {
		return chinese;
	}

	public void setChinese(int chinese) {
		this.chinese = chinese;
	}

	@Override
	public String toString() {
		return "Score [math=" + math + ", english=" + english + ", chinese=" + chinese + "]";
	}
	
	

}