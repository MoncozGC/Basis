package level01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Test03 {
	/*	a)	将集合中Student对象的信息写入当前项目下的stuInfo.txt当中
	b)	再读取stuInfo.txt的内容到集合中，遍历集合打印数据
	二、	补充说明：
	a)	写出信息的格式： it001,张曼玉,35,北京
	b)	输出信息的格式： it001,张曼玉,35,北京
*/
	public static void main(String[] args) throws IOException {
		ArrayList<Student> list = new ArrayList<>();
		list.add(new Student("it001", "张曼玉", 35, "北京"));
		list.add(new Student("it002", "张曼玉", 35, "北京"));
		list.add(new Student("it003", "张曼玉", 35, "北京"));

		BufferedWriter bw = new BufferedWriter(new FileWriter("Test03_1.txt"));
		
		for (Student s : list) {
			StringBuilder sb = new StringBuilder();
			sb.append(s.getId()).append(",").append(s.getName()).append(",").append(s.getAge()).append(",").append(s.getCity());
			bw.write(sb.toString());
			bw.newLine();
			bw.flush();
		}
		bw.close();
		list.clear();//集合的数据已经传了过去,所以可以清空一下 否则等会打印会出现两次
		BufferedReader br = new BufferedReader(new FileReader("Test03_1.txt"));
		String len;
		while((len = br.readLine()) != null){
			String[] arr = len.split(",");
			Student s = new Student();
			s.setId(arr[0]);
			s.setName(arr[1]);
			s.setAge(Integer.parseInt(arr[2]));
			s.setCity(arr[3]);
			list.add(s);
		}
		br.close();
		
		for (Student s : list) {
			System.out.println(s.getId()+","+s.getName()+","+s.getAge()+","+s.getCity());
		}
	}
}





