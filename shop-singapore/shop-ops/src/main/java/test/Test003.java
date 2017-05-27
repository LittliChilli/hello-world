package test;

import java.math.BigDecimal;

public class Test003 {
	public static void main(String[] args) {
		BigDecimal i  = new BigDecimal(0);
		BigDecimal t  = new BigDecimal(1);
		long preTime = System.currentTimeMillis();
		while(true){
			i = i.add(t);
			if(i.longValue() ==10000000000L){
				break;
			}
		}
		long lateTime = System.currentTimeMillis();
		System.out.println(lateTime - preTime);
		
		/*Double b = 0d;
		long preTime = System.currentTimeMillis();
		while(true){
			b++;
			if(b == 10000000000d){
				break;
			}
		}
		
		long lateTime = System.currentTimeMillis();
		System.out.println(lateTime - preTime);*/
		
		
		/*int i = 0;
		long preTime = System.currentTimeMillis();
		while(true){
			i++;
			if(1==1000000000){
				break;
			}
		}
		long lateTime = System.currentTimeMillis();
		System.out.println(lateTime - preTime);*/
		
	}
}
