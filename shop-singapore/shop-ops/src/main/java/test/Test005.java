package test;

import java.util.Arrays;

public class Test005 {

	private static int[] sort(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i] > array[j]) {
					int temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
		return array;
	}
	
	public static void main(String[] args) {
		int[] array = new int[]{12,45,51,2215,114,0,1,2,4,3};
		sort(array);
		System.out.println(Arrays.toString(array));
	}

}
