package test;

import java.util.Scanner;

public class Test002 {
	//模拟取模运算
	public static void main(String[] args) {
		System.out.println("请输入除数、被除数");
		Scanner sc = new Scanner(System.in);
		int a = Integer.parseInt(sc.next());
		int b = Integer.parseInt(sc.next());
		System.out.println("API计算："+a%b);
		
		/*if(a<b){
			System.out.println("模为："+a);
		}
		else if(a==b){
			System.out.println("模为："+0);
		}
		else{
			while(true){
				a = a-b;
				if(a<b){
					break;
				}
			}
			System.out.println("模为："+a);
		}*/
		int count = 0;
		while(true){
			count++;
			a = a-b;
			if(a<b){
				break;
			}
		}
		System.out.println("模为："+a);
		System.out.println("计数器："+count);
		
	}

}
