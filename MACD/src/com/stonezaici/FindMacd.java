package com.stonezaici;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FindMacd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = null;
		InputStreamReader isr = null;
		BufferedReader bf = null;
		String line = null;
		int hang = 0;
		String stockname = null;
		String stockcode = null;
		String tmp = null;
		ArrayList<String> ema26List = new ArrayList<String>();
		ArrayList<String> ema12List = new ArrayList<String>();
		
		String bipan_init = null;
		double bipan_init_cal = 0.0;//收盘初始化值
		double EMA12 = 0.0;
		double EMA26 = 0.0;
		double bipan_today = 0.0;//今日收盘价
		double DIF = 0.0;
		double DEA = 0.0;
		double MACD = 0.1;
		double buyprice = 0.0;//买入价格
		double saleprice = 0.0;//卖出价格
		double jiaoyi_count = 0.0;//交易次数
		double success_count = 0.0;//盈利次数
		double yinglv = 0.0;//盈率
		double shenglv = 0.0;//胜率
		boolean buy = false;//标识股票买没买入
		String date = null;
		try {
			 file = new File ("D://Program Files (x86)/T0002/export/600315.TXT");
			 isr = new InputStreamReader(new FileInputStream(file),"GBK");
			 bf = new BufferedReader(isr);
			 
			 while((line = bf.readLine()) != null && hang<425){
					hang++;
					if(hang ==1 && line != null){//获得股票代码和名称
						//System.out.println(line);
						tmp = line.split("                  ")[1];
						stockname = tmp.split(" ")[0];
						//System.out.println("stockname:" + stockname);
						stockcode = tmp.split(" ")[1];
						//System.out.println("stockcode:" + stockcode);
					}
					if(hang == 6){//开始进行EMA12 和 EMA26 计算 
						bipan_init = line.split("	  ")[4];
						//System.out.println(bipan_init);
						bipan_init_cal = Double.valueOf(bipan_init);
					}
					if(hang >6){
						//System.out.println(hang);
						//System.out.println(line);
						tmp = line.split("	  ")[4];
						bipan_today = Double.valueOf(tmp);
						//System.out.println("bipan_today:" + bipan_today);
						
					}
					
					if (hang == 7){//算是初始化
						EMA12 = bipan_init_cal * 11/13 + bipan_today * 2/13;
						EMA26 = bipan_init_cal * 25/27 + bipan_today * 2/27;
						DIF = EMA12 - EMA26;
						DEA = DIF;
					}
					if (hang >7){
						EMA12 = EMA12 * 11/13 + bipan_today * 2/13;
						EMA26 = EMA26 * 25/27 + bipan_today * 2/27;
						DIF = EMA12 - EMA26;
						DEA = DEA * 8/10 + DIF*2/10;
						date = line.split("	  ")[0];
						
							if(MACD > 0){
								MACD = DIF - DEA;
								if(MACD < 0 && buy == true){
									System.out.println(date + "卖出");
									buy = false;
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
							}
							if(MACD < 0 ){
								MACD = DIF - DEA;
								if(MACD > 0&& buy == false){
									System.out.println(date + "买入");
									buy = true;
									buyprice = Double.valueOf(((String)line.split("	  ")[4]));
									System.out.println("买入价格：" + buyprice);
								}
								
							}
					}
				}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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



