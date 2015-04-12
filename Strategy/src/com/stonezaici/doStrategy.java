package com.stonezaici;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class doStrategy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File file = new File("D://Program Files (x86)/T0002/export/600315.TXT");
		String line = null;
		String stockname = null;
		String stockcode = null;
		String tmp = null;
		double max_value = 0.0;
		int hang = 0;
		double A_point = 0.0;
		double D_point = 0.0;
		double C_point = 0.0;
		String C_point_date = null;
		//String D_point_date = null;
		//建立用于存储最大值的list  一共加入11个元素，用于寻找高点
		ArrayList list = new ArrayList();
		ArrayList date = new ArrayList();//创建时间序列  保证 各个点的顺序不发生混乱
		list.add(0.0); date.add("");
		list.add(0.0); date.add("");
		list.add(0.0); date.add("");
		list.add(0.0); date.add("");
		list.add(0.0); date.add("");
		list.add(0.0); date.add("");
		list.add(0.0); date.add("");
		list.add(0.0); date.add("");
		list.add(0.0); date.add("");
		list.add(0.0); date.add("");
		list.add(0.0); date.add("");
		int flag = 0;
		int flag2 = 0;
		double lossprice = 0.0;
		double buyprice = 0.0;//买入价格
		double saleprice = 0.0;//卖出价格
		double jiaoyi_count = 0.0;//交易次数
		double success_count = 0.0;//盈利次数
		double yinglv = 0.0;//盈率
		double shenglv = 0.0;//胜率
		boolean buy = false;//标识股票买没买入
		boolean A_point_exist = false;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"GBK");
			BufferedReader bf = new BufferedReader(isr);
			while((line = bf.readLine()) != null){
				flag = 0;//A点标志位   将标志位归零
				flag2 = 0;//D点标志位
				hang++;//代表 行
				if(hang ==1&& line != null){//取得股票名字和代码
					//System.out.println(line);
					tmp = line.split("                  ")[1];
					stockname = tmp.split(" ")[0];
					//System.out.println("stockname:" + stockname);
					stockcode = tmp.split(" ")[1];
					//System.out.println("stockcode:" + stockcode);
				}
				if(hang > 5 && hang < 426){
//					System.out.println(line);
					tmp = line.split("	  ")[2];
					max_value = Double.valueOf(tmp);
					//System.out.println("最大值：" + max_value);
					list.remove(0); //循环取数 存放+-5个数据   至于+-3个数据 直接用list中最后面七个数据就可以了
					date.remove(0);
					list.add(max_value); 
					date.add(line.split("	  ")[0]);//日期
					for(int i = 0; i < 5; i++){//与前五根k线比较
						if((double)list.get(5) > (double)list.get(i)){
							flag++;
							//System.out.println("前5flag:" + flag);
						}
					}
					for(int j = 6; j< 11; j++){//与后五根k线比较
						if((double)list.get(5) > (double)list.get(j)){
							flag++;
							//System.out.println("后5flag:" + flag);
						}
					}
					if(flag == 10 && buy == false){//找到Apoint
						A_point = (double)list.get(5);
						A_point_exist = true;
						//System.out.println("A点" + A_point + "所在行：" + hang );
						lossprice = A_point * 0.95;
					}
					if(max_value > A_point && buy == false && (double)list.get(0)!=0.0 && A_point_exist == true){//找到C点
						C_point = max_value;
						C_point_date = line.split("	  ")[0];
						//System.out.println("C点找到！" + C_point + "买入"+ "所在行：" + hang);
						buy = true;
						buyprice = Double.valueOf(((String)line.split("	  ")[4]));
						System.out.println("买入价格：" + buyprice);
					}
					if(max_value < lossprice && buy == true ){
						//System.out.println("止损点找到：" + max_value +"止损卖出"+ "所在行：" + hang);
						buy = false;
						A_point_exist = false;
						//完成一次交易
						saleprice = Double.valueOf((String)line.split("	  ")[4]);
						System.out.println("卖出价：" + saleprice);
						jiaoyi_count++;
						yinglv = buyprice/saleprice -1;
						if((buyprice - saleprice)>0){//如果盈利  盈利次数加一
							success_count++;
						}
						System.out.println("盈率：" + yinglv);
					}
					//找D点
					for(int i = 4;i < 7;i++){//与前三根k线比较
						if((double)list.get(7) < (double)list.get(i)){
							flag2++;
						}
					}
					for(int j = 8; j < 11; j++){//与后三根k线比较
						
						if((double)list.get(7) < (double)list.get(j) ){
							flag2++;
						}
					}
					
					if(flag2 == 6 && buy == true && ((String) date.get(7)).compareTo(C_point_date)>0){//需保证D点的日期要在C点之后
						D_point = (double)list.get(7);
						//System.out.println("D点"+ D_point+ "所在行：" + hang);
						if(D_point > A_point){
							lossprice = D_point;
							//System.out.println("产生推进止损价格：" + D_point+ "所在行：" + hang);
						}
					}
					if(max_value < lossprice && buy == true){
						//System.out.println("止损点找到：" + max_value +"止损卖出"+ "所在行：" + hang);
						buy = false;
						A_point_exist = false;
						//完成一次交易
						saleprice = Double.valueOf((String)line.split("	  ")[4]);
						System.out.println("卖出价：" + saleprice);
						jiaoyi_count++;
						yinglv = buyprice/saleprice -1;
						if((buyprice - saleprice)>0){//如果盈利  盈利次数加一
							success_count++;
						}
						System.out.println("盈率：" + yinglv);
					}
					
				}////hang > 5 最外面的括号
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("总交易次数：" + jiaoyi_count);
		System.out.println("盈利次数：" + success_count);
		shenglv = success_count/jiaoyi_count;
		System.out.println("胜率：" + shenglv );
	}

}
