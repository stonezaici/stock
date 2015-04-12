package com.stonezaici;

import java.util.ArrayList;
import java.util.List;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i <10; i++){
			System.out.println("i :"+ i);
			for(int j = 0; j<5; j++){
				if (j>3) break;
				System.out.println("j:" + j);
				
			}
			System.out.println("aiyouyou");
		}
		
		
//		ArrayList<Float> test=new ArrayList<Float>();
//		test.add((float) 31);
//		test.add((float) 32);
//		test.add((float) 33);
//		test.add((float) 34);
//		test.add((float) 35);
//	
//		for(int j= 0; j<4; j++){
//		System.out.println("j在里面"+test.get(j+1).intValue());
//		System.out.println("j在外面"+(test.get(j).intValue()+1));
//		}
	}

}
