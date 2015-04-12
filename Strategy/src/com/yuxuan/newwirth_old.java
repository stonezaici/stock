package com.yuxuan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class newwirth_old {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileDir = "D:\\Program Files (x86)\\T0002\\export\\";
		String hushengZhiShuPath = "D:\\Program Files (x86)\\T0002\\export\\SZ399300.TXT";
		String zhongxiaoZhiShuPath = "D:\\Program Files (x86)\\T0002\\export\\SZ399005.TXT";
		String chuangyeZhiShuPath = "D:\\Program Files (x86)\\T0002\\export\\SZ399006.TXT";
		int low_k_min_value = 10;//低点之间k线数 最少应满足的数量
		int high_k_min_value = 10;//高点之间k线数 最少应满足的数量
		int AC_Dis = 100; //ac两点之间最大允许的k线的数量
		
		
		String[] sname=null;//股票的文件名
		String line=null;
		String[] lineaftersplit=null;
		String[][] price=new String[10000][8];//股票价格信息
		String[][] pricehusheng=new String[10000][8];//沪深300价格信息
		String[][] pricezhongxiao=new String[10000][8];//中小板价格信息
		String[][] pricechuangye=new String[10000][8];//创业板价格信息
		ArrayList<Float> hight=new ArrayList<Float>();//先找到高点，然后把高点存到这里
		ArrayList<Float> lowt=new ArrayList<Float>();//找到低点，把低点存这里
		ArrayList<String[]> buy=new ArrayList<String[]>();//把买入形态存在这里
		ArrayList<String[]> sell=new ArrayList<String[]>();//把卖出形态存在这里
		float a=0;
		float at=0;
		float b=0;
		float bt=0;
		float c=0;
		float ct=0;
		float d=0;
		float dt=0;
		float m=0;
		float mt=0;
		float e=0;
		float et=0;
		float za=0;
		String za_date=null;
		float zb=0;
		String zb_date=null;
		float zc=0;
		String zc_date=null;
		float zd=0;
		String zd_date=null;
		float zm=0;
		String zm_date=null;//Z加上abcd代表abcd点对应的指数
		float bmark=0;
		float sellmark=0;
		Date startTime=null;
		Date endTime=null;
		float abdiff=(float) 0.1;//ab之间价格 倍数
		//找m点时用到的变量
		float exactBuy;//exactBuy是真的买入点
		float shouPrice;
	    float kaiPrice;
		float lastFiveTrade;
		int buyMark=0;
		//*********//
		File namefile=new File(fileDir) ;//这里可以将文件路径设成全局变量
		sname=namefile.list();
		int i=0;
		int j=0;
		int k=0;
		int z=0;
		int h=0;//循环变量
		
		File fileout =null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowdate=dateFormat.format(new java.util.Date());
		fileout =new File("D:\\newwirthoutput\\"+"日线—"+nowdate+".txt");
		if (!fileout.exists()) {             
			try {
				fileout.createNewFile();
			} catch (IOException e1) {
					// TODO Auto-generated catch block
				e1.printStackTrace();
			}              
			System.out.println(fileout + "已创建！");
		
		}	
			try{
				FileOutputStream fos = new FileOutputStream(fileout,true);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				BufferedWriter write = new BufferedWriter(osw);
			
				write.write("股票代码");//输出表头
				write.write("	");
				
				write.write("买入形态开始");
				write.write("	");
				write.write("A");
				write.write("	");
				write.write("B时间");
				write.write("	");
				write.write("B");
				write.write("	");
				write.write("C时间");
				write.write("	");
				write.write("C");
				write.write("	");
				write.write("D时间");
				write.write("	");
				write.write("D");
				write.write("	");
				write.write("M时间");
				write.write("	");
				write.write("M");
				write.write("	");
				write.write("ABC涨幅");
				write.write("	");
				write.write("BCD涨幅");
				write.write("	");
				write.write("BCM涨幅");
				write.write("	");
				write.write("卖出形态开始");
				write.write("	");
				write.write("A'");
				write.write("	");
				write.write("B'时间");
				write.write("	");
				write.write("B'");
				write.write("	");
				write.write("C'时间");
				write.write("	");
				write.write("C'");
				write.write("	");
				write.write("卖出时间");
				write.write("	");
				write.write("D'");
				write.write("	");
				write.write("ABC'涨幅");
				write.write("	");
				write.write("BCD'涨幅");
				write.write("	");
				write.write("卖出操作");
				write.write("	");
				write.write("盈利");
				write.write("	");
			
//				write.write("买入形态开始");
//				write.write("	");
//				write.write("A");
//				write.write("	");
//				write.write("B");
//				write.write("	");
//				write.write("C");
//				write.write("	");
//				write.write("D");
//				write.write("	");
//				write.write("买入时间");
//				write.write("	");
//				write.write("卖出形态开始");
//				write.write("	");
//			
//				write.write("A’");
//				write.write("	");
//				write.write("B‘");
//				write.write("	");
//				write.write("C’");
//				write.write("	");
//				write.write("D‘");
//				write.write("	");
//				write.write("卖出时间");
//				write.write("	");
//				write.write("指数涨跌");
//				write.write("	");
//			
//				write.write("盈利");
//				write.write("	");
			
				
				write.close();
			}catch(Exception ee){
				ee.printStackTrace();
			}
		//以下分别读入3个指数的数据
		try{	

			FileReader filereader=new FileReader(hushengZhiShuPath);//这里可以将文件路径设成全局变量
			BufferedReader reader=new BufferedReader(filereader);
			
			//		if(sname[i].compareTo("sh600556")<0) continue;
			line=reader.readLine();
			line=reader.readLine();
			j=0;
			while((line=reader.readLine())!=null){
			
				lineaftersplit=line.split(",");
				if(lineaftersplit.length<2) break;
				if(lineaftersplit[1]==lineaftersplit[2]&&lineaftersplit[2]==lineaftersplit[3]&&lineaftersplit[3]==lineaftersplit[4]&&lineaftersplit[5]=="0") break;
				lineaftersplit[0]=lineaftersplit[0].replace("/","");
				pricehusheng[j][0]=Integer.toString(j);//0为序号
				pricehusheng[j][1]=lineaftersplit[0];//1为日期
				pricehusheng[j][2]=lineaftersplit[1];//2为开盘
				pricehusheng[j][3]=lineaftersplit[2];//3为高价
				pricehusheng[j][4]=lineaftersplit[3];//4为低价
				pricehusheng[j][5]=lineaftersplit[4];//5为收盘
				pricehusheng[j][6]=lineaftersplit[5];//6为量
				
				System.out.println(pricehusheng[j][5]);
				j++;
			}
		
			reader.close();
			filereader.close();
		}
		catch(Exception ee) {ee.printStackTrace();}
		
		try{	

			FileReader filereader=new FileReader(zhongxiaoZhiShuPath);//这里可以将文件路径设成全局变量
			BufferedReader reader=new BufferedReader(filereader);
			
			//		if(sname[i].compareTo("sh600556")<0) continue;
			line=reader.readLine();
			line=reader.readLine();
			j=0;
			while((line=reader.readLine())!=null){
			
				lineaftersplit=line.split(",");
				if(lineaftersplit.length<2) break;
				if(lineaftersplit[1]==lineaftersplit[2]&&lineaftersplit[2]==lineaftersplit[3]&&lineaftersplit[3]==lineaftersplit[4]&&lineaftersplit[5]=="0") break;
				lineaftersplit[0]=lineaftersplit[0].replace("/","");
				pricezhongxiao[j][0]=Integer.toString(j);
				pricezhongxiao[j][1]=lineaftersplit[0];
				pricezhongxiao[j][2]=lineaftersplit[1];
				pricezhongxiao[j][3]=lineaftersplit[2];
				pricezhongxiao[j][4]=lineaftersplit[3];
				pricezhongxiao[j][5]=lineaftersplit[4];
				pricezhongxiao[j][6]=lineaftersplit[5];
				
		//		System.out.println(pricezhongxiao[j][6]);
				j++;
			}
			
			reader.close();
			filereader.close();
		}
		catch(Exception ee) {ee.printStackTrace();}
		
		try{	

			FileReader filereader=new FileReader(chuangyeZhiShuPath);//这里可以将文件路径设成全局变量
			BufferedReader reader=new BufferedReader(filereader);
			
			//		if(sname[i].compareTo("sh600556")<0) continue;
			line=reader.readLine();
			line=reader.readLine();
			j=0;
			while((line=reader.readLine())!=null){
			
				lineaftersplit=line.split(",");
				if(lineaftersplit.length<2) break;
				if(lineaftersplit[1]==lineaftersplit[2]&&lineaftersplit[2]==lineaftersplit[3]&&lineaftersplit[3]==lineaftersplit[4]&&lineaftersplit[5]=="0") break;
				lineaftersplit[0]=lineaftersplit[0].replace("/","");
				pricechuangye[j][0]=Integer.toString(j);
				pricechuangye[j][1]=lineaftersplit[0];//riqi
				pricechuangye[j][2]=lineaftersplit[1];//kaipan
				pricechuangye[j][3]=lineaftersplit[2];//zuigao
				pricechuangye[j][4]=lineaftersplit[3];//zuidi
				pricechuangye[j][5]=lineaftersplit[4];//shoupan
				pricechuangye[j][6]=lineaftersplit[5];//liang
				
		//		System.out.println(price[j][6]);
				j++;
			}
		
			reader.close();
			filereader.close();
		}
		catch(Exception ee) {ee.printStackTrace();}
//至此 三种指数的数据读入完毕，分别放入到了pricehusheng pricechuangye pricezhongxiao 三个数组中
		
		for(i=0;i<sname.length;i++)	{//一个股票文件一个股票文件地读入， 处理完以后，所有变量归零，然后处理下一个股票文件
			try{	
				File n=new File(sname[i]);
				
				sname[i]=n.getName();
				System.out.println(sname[i]);
				FileReader filereader=new FileReader(fileDir+sname[i]);
				BufferedReader reader=new BufferedReader(filereader);
				sname[i]=sname[i].substring(0,8);
				//		if(sname[i].compareTo("sh600556")<0) continue;
				line=reader.readLine();
				line=reader.readLine();
				j=0;
				//System.out.println("开始读取股票数据"+ line);
				while((line=reader.readLine())!=null){//循环将股票文件的数据读入price数组中
					System.out.println("开始读取股票数据"+ line);
					lineaftersplit=line.split("	");
					if(lineaftersplit.length<2) break;
					if(lineaftersplit[1]==lineaftersplit[2]&&lineaftersplit[2]==lineaftersplit[3]&&lineaftersplit[3]==lineaftersplit[4]&&lineaftersplit[5]=="0") break;
					lineaftersplit[0]=lineaftersplit[0].replace("/","");
					price[j][0]=Integer.toString(j);//0为序号
					price[j][1]=lineaftersplit[0];//1为日期
					price[j][2]=lineaftersplit[1];//2为开盘
					price[j][3]=lineaftersplit[2];//3为高价
					price[j][4]=lineaftersplit[3];//4为低价
					price[j][5]=lineaftersplit[4];//5为收盘
					price[j][6]=lineaftersplit[5];//6为量
					System.out.println("股票数据读取完毕");
//						System.out.println(price[j][6]);
					j++;
				}//至此，股票文件的数据读取完毕
				
				reader.close();
				filereader.close();
			}
				
			catch(Exception ee) {ee.printStackTrace();}
			
			for(j=10;price[j+10][0]!=null;j++){//低点的定义为高于或等于前后10根k线的最低点
					//		System.out.prjntln(j);
							if(Float.valueOf(price[j][5])<=Float.valueOf(price[j-1][5])
									&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-2][5])
											&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-3][5])&&
											Float.valueOf(price[j][5])<=Float.valueOf(price[j-4][5])
													&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-5][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j-6][5])
															&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-7][5])
																	&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-8][5])&&
																	Float.valueOf(price[j][5])<=Float.valueOf(price[j-9][5])
																			&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-10][5])&&
													Float.valueOf(price[j][5])<=Float.valueOf(price[j+1][5])&&
													Float.valueOf(price[j][5])<=Float.valueOf(price[j+2][5])
															&&Float.valueOf(price[j][5])<=Float.valueOf(price[j+3][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j+4][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j+5][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j+6][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j+7][5])
																	&&Float.valueOf(price[j][5])<=Float.valueOf(price[j+8][5])&&
																	Float.valueOf(price[j][5])<=Float.valueOf(price[j+9][5])&&
																	Float.valueOf(price[j][5])<=Float.valueOf(price[j+10][5])){
								lowt.add(Float.valueOf(price[j][0]));//hight用于存放高点位置，用k线根数表示
								//只将k线的根数（即，第几根k线）放入lowt列表中
						//		System.out.prjntln(date[i]);
						//		System.out.println();
							}
			}
						
			for(j=1;j<lowt.size();j++){//当低点之间相差k线根数小于3时，不要前一个高点，用于防止最大值相等是，存在多个高点的情况
				if(lowt.get(j)-lowt.get(j-1)<=low_k_min_value){//低点之间k线根数之差的最大限度可以设置成全局变量
					lowt.remove(j-1);
					j--;
				}
			}
		
			for(j=10;price[j+10][1]!=null;j++){//高点的定义为高于或等于前后10根k线的最高点
					//		System.out.prjntln(j);
				if(Float.valueOf(price[j][5])>=Float.valueOf(price[j-1][5])
									&&Float.valueOf(price[j][5])>=Float.valueOf(price[j-2][5])
											&&Float.valueOf(price[j][5])>=Float.valueOf(price[j-3][5])&&
											Float.valueOf(price[j][5])>=Float.valueOf(price[j-4][5])
													&&Float.valueOf(price[j][5])>=Float.valueOf(price[j-5][5])&&
															Float.valueOf(price[j][5])>=Float.valueOf(price[j-6][5])
															&&Float.valueOf(price[j][5])>=Float.valueOf(price[j-7][5])
																	&&Float.valueOf(price[j][5])>=Float.valueOf(price[j-8][5])&&
																	Float.valueOf(price[j][5])>=Float.valueOf(price[j-9][5])
																			&&Float.valueOf(price[j][5])>=Float.valueOf(price[j-10][5])&&
													Float.valueOf(price[j][5])>=Float.valueOf(price[j+1][5])&&
													Float.valueOf(price[j][5])>=Float.valueOf(price[j+2][5])
															&&Float.valueOf(price[j][5])>=Float.valueOf(price[j+3][5])&&
															Float.valueOf(price[j][5])>=Float.valueOf(price[j+4][5])&&
															Float.valueOf(price[j][5])>=Float.valueOf(price[j+5][5])&&
															Float.valueOf(price[j][5])>=Float.valueOf(price[j+6][5])&&
																	Float.valueOf(price[j][5])>=Float.valueOf(price[j+7][5])
																	&&Float.valueOf(price[j][5])>=Float.valueOf(price[j+8][5])&&
																	Float.valueOf(price[j][5])>=Float.valueOf(price[j+9][5])&&
																	Float.valueOf(price[j][5])>=Float.valueOf(price[j+10][5])){
					hight.add(Float.valueOf(price[j][0]));//hight用于存放高点位置，用k线根数表示
					    //只将k线的根数（即，第几根k线）放入lowt列表中
				}
			}
						
			for(j=1;j<hight.size();j++){//当高点之间相差k线根数小于3时，不要前一个高点，用于防止最大值相等是，存在多个高点的情况
				if(hight.get(j)-hight.get(j-1)<=high_k_min_value){//高点之间k线根数之差的最大限度可以设置成全局变量
					hight.remove(j-1);
					j--;
				}
			}
				
			for(j=0;j<hight.size()-1;j++){//找买入形态
				//先循环取前后两个高点a 和 c 以及这两点的k线根数 不是日期
				a=Float.valueOf(price[hight.get(j).intValue()][5]);
				at=Float.valueOf(price[hight.get(j).intValue()][0]);
				c=Float.valueOf(price[hight.get(j+1).intValue()][5]);//hight数组中的下一个点  即为下一个高点
				ct=Float.valueOf(price[hight.get(j+1).intValue()][0]);
				
				if(c<a) {
				System.out.println("c点收盘价低于a点");
				continue;
				}
				if(ct-at>AC_Dis) {
					System.out.println("A到C之间的距离>"+AC_Dis+"根k线");
					continue;//形态条件：A到C之间的距离<=ac根
				}
				//b点取为a点所在k线处的下一根k线 注意是price数组中的下一根  不是hight列表中的	
				b=Float.valueOf(price[hight.get(j).intValue()+1][5]);
				bt=Float.valueOf(price[hight.get(j).intValue()+1][0]);//b的k线根数
				//bmark=Float.valueOf(price[hight.get(j).intValue()+1][4]);
				for(k=(int)bt;k<ct;k++){//求得ac之间的最低点b  但是貌似无法保证b是左右10根里面最小的
						
					if(Float.valueOf(price[k][5])<b){
						b=Float.valueOf(price[k][5]);
						bt=k;
						//bmark=Float.valueOf(price[k][4]);
					}
					else if(Float.valueOf(price[k][5])==b){//若相等出现多个低点
						if(k-bt <=5){//这个5可以设置为全局变量
							//若出现多个最低点时，且两者相差<=5，则取后面一个为低点
							b = Float.valueOf(price[k][5]);
							bt=k;
						}
					}
						
				}//至此找到b点所在的k线和b点的收盘价
				
				//若AB之间的价格倍数不足1+abdiff 则 pass
				if(Float.valueOf(price[(int)at][5])<(1+abdiff)*Float.valueOf(price[(int)bt][5]))
				{
					System.out.println("============================a点时间：" + price[(int)at][1]);
					System.out.println("============================b点时间：" + price[(int)bt][1]);
					System.out.println("============================买入条件不符合 A>1.2B");
					continue;//若买入条件：A>1.2B，不满足的话，则继续重新找A
				}
				
				
				
				//至此可确定d点	
				dt=ct+(bt-at);
				System.out.println("dt:" + dt +"at:"+at+"bt:"+bt+"ct:"+ct);
				System.out.println("price的长度："+ price.length);
				System.out.println("d:" + price[73][5]);
				//d=Float.valueOf(price[(int)dt][5]);//d点确定完毕
				if(price[(int)dt][0] != null) {
					d=Float.valueOf(price[(int)dt][5]);//d点确定完毕
					//找M点 
					mt=dt;//初始化m点
					
					exactBuy=mt;
					while(buyMark==0 && price[(int)exactBuy][0] != null)
					{
						shouPrice=Float.valueOf(price[(int)exactBuy][5]);
						kaiPrice=Float.valueOf(price[(int)exactBuy][2]);
						lastFiveTrade=Float.valueOf(price[(int)exactBuy-5][6]);
						if(shouPrice>kaiPrice &&  Float.valueOf(price[(int)exactBuy][6])>lastFiveTrade)//买入需满足的条件：阳线，即收盘>开盘  且当日5日平均成交量>前一日5日平均成交量
							//由于当日和前一日的5日平均成交量 相交叉的中间四天都是相等的 所以只需要比较当天和五天前的成交量即可
						{
							System.out.println("m点k线加一");
							buyMark=1;//标记为买入
							mt=exactBuy;
							m=Float.valueOf(price[(int)exactBuy][5]);//收盘价
							System.out.println("mt:" + mt);
						}
						else
						{
							exactBuy=exactBuy+1;
						}
					}
				}
					
				else break;//如果ABC已生成，但D未生成，没有形成一个买入形态，则看下一个股票的情况
					//下面为找买入形态abcd点对应的指数点
				if(sname[i].substring(0, 5).compareTo("SH600")==0||sname[i].substring(0, 5).compareTo("SZ000")==0){//如果股票的代码以SH600或者SZ000开头
					for(k=(int)at;pricehusheng[k][1]!=null;k++){
							
						if(price[(int)at][1].compareTo(pricehusheng[k][1])==0){//以日期作比较 确定a点的大盘指数
							za=Float.valueOf(pricehusheng[k][5]);
							za_date = pricehusheng[k][1];
							continue;
						}
						
						if(price[(int)bt][1].compareTo(pricehusheng[k][1])==0){//以日期作比较 确定b点的大盘指数
							zb=Float.valueOf(pricehusheng[k][5]);
							zb_date = pricehusheng[k][1];
							continue;
								
						}
						if(price[(int)ct][1].compareTo(pricehusheng[k][1])==0){//以日期作比较 确定c点的大盘指数
							zc=Float.valueOf(pricehusheng[k][5]);
							zc_date = pricehusheng[k][1];
							continue;
						}
							
						if(price[(int)dt][1].compareTo(pricehusheng[k][1])==0){//以日期作比较 确定d点的大盘指数
							zd=Float.valueOf(pricehusheng[k][5]);
							zd_date = pricehusheng[k][1];
							continue;
						}
						if(price[(int)mt][1].compareTo(pricehusheng[k][1])==0){//以日期作比较 确定d点的大盘指数
							zm=Float.valueOf(pricehusheng[k][5]);//****
							zm_date = pricehusheng[k][1];//***
							break;//***
						}
					}
					System.out.println("a点指数："+za + "a点指数日期："+za_date+"a点日期："+price[(int)at][1]);
					System.out.println("b点指数："+zb + "b点指数日期："+zb_date+"b点日期："+price[(int)bt][1]);
					System.out.println("c点指数："+zc + "c点指数日期："+zc_date+"c点日期："+price[(int)ct][1]);
					System.out.println("d点指数："+zd + "d点指数日期："+zd_date+"d点日期："+price[(int)dt][1]);
					System.out.println("m点指数："+zm + "m点指数日期："+zm_date+"m点日期："+price[(int)mt][1]);
				}
					
				else if(sname[i].substring(0, 5).compareTo("SZ002")==0){
					for(k=(int)at;pricezhongxiao[k][1]!=null;k++){
							
						if(price[(int)at][1].compareTo(pricezhongxiao[k][1])==0){
							za=Float.valueOf(pricezhongxiao[k][5]);
							continue;
						}
							
						if(price[(int)bt][1].compareTo(pricezhongxiao[k][1])==0){
							zb=Float.valueOf(pricezhongxiao[k][5]);
							continue;
							
						}
						if(price[(int)ct][1].compareTo(pricezhongxiao[k][1])==0){
							zc=Float.valueOf(pricezhongxiao[k][5]);
							continue;
						}
							
						if(price[(int)dt][1].compareTo(pricezhongxiao[k][1])==0){
							zd=Float.valueOf(pricezhongxiao[k][5]);
							break;
						}
					}
				}
					
				else if(sname[i].substring(0, 5).compareTo("SZ300")==0){
					for(k=(int)at;pricechuangye[k][1]!=null;k++){
							
						if(price[(int)at][1].compareTo(pricechuangye[k][1])==0){
							za=Float.valueOf(pricechuangye[k][5]);
							continue;
						}
							
						if(price[(int)bt][1].compareTo(pricechuangye[k][1])==0){
							zb=Float.valueOf(pricechuangye[k][5]);
							continue;
								
						}
						if(price[(int)ct][1].compareTo(pricechuangye[k][1])==0){
							zc=Float.valueOf(pricechuangye[k][5]);
							continue;
						}
							
						if(price[(int)dt][1].compareTo(pricechuangye[k][1])==0){
							zd=Float.valueOf(pricechuangye[k][5]);
							break;
						}
					}
				}
					
				//if((c-a)<=(zc-za)) continue;
				//if((d-b)<=(zd-zb)) continue;//若abc或bcd中有一个弱于指数，则放弃该买入形态
				//与大盘指数进行比较
				if(((c-a)/a)<=((zc-za)/za)) {
					System.out.println("a:" + a + "b:" + b +"c:"+ c + "d:" + d + "m:"+ m + "za:"+za+"zb:" + zb +"zc:" + zc+"zd:"+zd+"zm:"+zm);
					System.out.println("a点时间：" + price[(int)at][1]);
					System.out.println("b点时间：" + price[(int)bt][1]);
					System.out.println("c点时间：" + price[(int)ct][1]);
					System.out.println("d点时间：" + price[(int)dt][1]);
					System.out.println("m点时间：" + price[(int)mt][1]);
					System.out.println("abc弱于大盘，A点时间：" + price[hight.get(j).intValue()][1]+"--or"+ price[(int)at][1] + "C点时间：" + price[hight.get(j).intValue()][1]);
					continue;
				}
				if(((d-b)/b)<=((zd-zb)/zb)) {
					System.out.println("a:" + a + "b:" + b +"c:"+ c + "d:" + d + "m:"+ m + "za:"+za+"zb:" + zb +"zc:" + zc+"zd:"+zd+"zm:"+zm);
					System.out.println("a点时间：" + price[(int)at][1]);
					System.out.println("b点时间：" + price[(int)bt][1]);
					System.out.println("c点时间：" + price[(int)ct][1]);
					System.out.println("d点时间：" + price[(int)dt][1]);
					System.out.println("m点时间：" + price[(int)mt][1]);
					System.out.println("bcd弱于大盘，b点时间：" + price[(int)bt][1] + "C点时间：" + price[(int)ct][1]);//////改成低点时间
					continue;//若abc或bcd中有一个弱于指数，则放弃该买入形态
				}
				if(((m-b)/b)<=((zm-zb)/zb)) {
					System.out.println("a:" + a + "b:" + b +"c:"+ c + "d:" + d + "m:"+ m + "za:"+za+"zb:" + zb +"zc:" + zc+"zd:"+zd+"zm:"+zm);
					System.out.println("a点时间：" + price[(int)at][1]);
					System.out.println("b点时间：" + price[(int)bt][1]);
					System.out.println("c点时间：" + price[(int)ct][1]);
					System.out.println("d点时间：" + price[(int)dt][1]);
					System.out.println("m点时间：" + price[(int)mt][1]);
					System.out.println("bcm弱于大盘");
					continue;//若bcm弱于指数，则放弃该买入形态
				}
				//经过这三个条件过滤，就剩下合格的了，以下是针对合格股票进行处理
				//至此完成与大盘指数进行比较
				String[] rs=new String[14];
				rs[0]=price[(int)at][5];
				rs[1]=price[(int)bt][5];
				rs[2]=price[(int)ct][5];
				rs[3]=price[(int)dt][5];
				rs[4]=price[(int)at][1];
				rs[5]=price[(int)dt][1];
				rs[6]=Integer.toString((int)dt);//0为a收 1为b收 2为c收 3为d收 4为a日期 5为d日期 6为d序号
				rs[7]=""+(c-a)/a+",,,"+(zc-za)/za;//7为abc期间，股票涨幅和大盘涨幅
				rs[8]=""+(d-b)/b+",,,"+(zd-zb)/zb;//8为bcd期间，股票涨幅和大盘涨幅
				rs[9]=price[(int)bt][1];//9为b日期
				rs[10]=price[(int)ct][1];//10为c日期
				rs[11]=price[(int)mt][1];//11为m点日期
			    //	System.out.println(rs[11]);
				rs[12]=price[(int)mt][5];//12为m收盘价
				rs[13]=""+(m-b)/b+",,,"+(zm-zb)/zb;//13为bcm阶段，股票涨幅和大盘涨幅
				if(rs[11].compareTo("20130703")<0 ){/////7-04 添加时间限制  大于4月30号，小于7月3号
					if( rs[11].compareTo("20130330") > 0 ){
						buy.add(rs);//存储买入形态
						System.out.println("符合买入形态");
					}
				}
					

					
			}
				//以下是找卖出形态
//			for(j=0;j<lowt.size()-1;j++){
//					
//				a=Float.valueOf(price[lowt.get(j).intValue()][5]);
//				at=Float.valueOf(price[lowt.get(j).intValue()][0]);
//				c=Float.valueOf(price[lowt.get(j+1).intValue()][5]);
//				ct=Float.valueOf(price[lowt.get(j+1).intValue()][0]);
//				
//					
//					
//				b=Float.valueOf(price[lowt.get(j).intValue()+1][5]);
//				bt=Float.valueOf(price[lowt.get(j).intValue()+1][0]);
//				//bmark=Float.valueOf(price[lowt.get(j).intValue()+1][3]);
//					
//				for(k=(int)bt;k<ct;k++){//取得ac之间的最高点b  
//						
//					if(Float.valueOf(price[k][5])>b){
//						b=Float.valueOf(price[k][5]);
//						bt=k;
//						//bmark=Float.valueOf(price[k][3]);
//					}
//						
//				}
//					
//				dt=ct+(bt-at);
//				if(price[(int)dt][0] != null) 
//					d=Float.valueOf(price[(int)dt][5]);
//				else break;
//				
//				
//				if(sname[i].substring(0, 5).compareTo("SH600")==0||sname[i].substring(0, 5).compareTo("SZ000")==0){
//					for(k=(int)at;pricehusheng[k][1]!=null;k++){
//						
//						if(price[(int)at][1].compareTo(pricehusheng[k][1])==0){
//							za=Float.valueOf(pricehusheng[k][5]);
//							continue;
//						}
//							
//						if(price[(int)bt][1].compareTo(pricehusheng[k][1])==0){
//							zb=Float.valueOf(pricehusheng[k][5]);
//							continue;
//								
//						}
//						if(price[(int)ct][1].compareTo(pricehusheng[k][1])==0){
//							zc=Float.valueOf(pricehusheng[k][5]);
//							continue;
//						}
//							
//						if(price[(int)dt][1].compareTo(pricehusheng[k][1])==0){
//							zd=Float.valueOf(pricehusheng[k][5]);
//							break;
//						}
//					}
//						
//				}
//					
//				if(sname[i].substring(0, 5).compareTo("SZ002")==0){
//					for(k=(int)at;pricezhongxiao[k][1]!=null;k++){
//							
//						if(price[(int)at][1].compareTo(pricezhongxiao[k][1])==0){
//							za=Float.valueOf(pricezhongxiao[k][5]);
//							continue;
//						}
//							
//						if(price[(int)bt][1].compareTo(pricezhongxiao[k][1])==0){
//							zb=Float.valueOf(pricezhongxiao[k][5]);
//							continue;
//								
//						}
//						if(price[(int)ct][1].compareTo(pricezhongxiao[k][1])==0){
//							zc=Float.valueOf(pricezhongxiao[k][5]);
//							continue;
//						}
//							
//						if(price[(int)dt][1].compareTo(pricezhongxiao[k][1])==0){
//							zd=Float.valueOf(pricezhongxiao[k][5]);
//							break;
//						}
//					}
//				}
//					
//				if(sname[i].substring(0, 5).compareTo("SZ300")==0){
//					for(k=(int)at;pricechuangye[k][1]!=null;k++){
//							
//						if(price[(int)at][1].compareTo(pricechuangye[k][1])==0){
//							za=Float.valueOf(pricechuangye[k][5]);
//							continue;
//						}
//							
//						if(price[(int)bt][1].compareTo(pricechuangye[k][1])==0){
//							zb=Float.valueOf(pricechuangye[k][5]);
//							continue;
//								
//						}
//						if(price[(int)ct][1].compareTo(pricechuangye[k][1])==0){
//							zc=Float.valueOf(pricechuangye[k][5]);
//							continue;
//						}
//							
//						if(price[(int)dt][1].compareTo(pricechuangye[k][1])==0){
//							zd=Float.valueOf(pricechuangye[k][5]);
//							break;
//						}
//					}
//				}
//				
//				
//				
//				if((c-a)>(zc-za)&&(d-b)>(zd-zb)) continue;//若abc和bcd有一个弱于指数，才卖出。
//					
//				
//				String[] rs=new String[7];
//				rs[0]=price[(int)at][5];
//				rs[1]=price[(int)bt][5];
//				rs[2]=price[(int)ct][5];
//				rs[3]=price[(int)dt][5];
//				rs[4]=price[(int)at][1];
//				rs[5]=price[(int)dt][1];
//				rs[6]=Integer.toString((int)dt);
//				sell.add(rs);
//
//			}//卖出形态寻找完毕
			
			for(j=0;j<buy.size();j++){//更改时间格式
//				buy.get(j)[4]=buy.get(j)[4].substring(0, 4)+"-"+buy.get(j)[4].substring(4,6)+"-"+buy.get(j)[4].substring(6,8);
//				buy.get(j)[5]=buy.get(j)[5].substring(0, 4)+"-"+buy.get(j)[5].substring(4,6)+"-"+buy.get(j)[5].substring(6,8);
				buy.get(j)[4]=buy.get(j)[4].substring(0, 4)+"-"+buy.get(j)[4].substring(4,6)+"-"+buy.get(j)[4].substring(6,8);//a日期
				buy.get(j)[5]=buy.get(j)[5].substring(0, 4)+"-"+buy.get(j)[5].substring(4,6)+"-"+buy.get(j)[5].substring(6,8);//d日期
				buy.get(j)[9]=buy.get(j)[9].substring(0, 4)+"-"+buy.get(j)[9].substring(4,6)+"-"+buy.get(j)[9].substring(6,8);//b日期
				buy.get(j)[10]=buy.get(j)[10].substring(0, 4)+"-"+buy.get(j)[10].substring(4,6)+"-"+buy.get(j)[10].substring(6,8);//c日期
				buy.get(j)[11]=buy.get(j)[11].substring(0, 4)+"-"+buy.get(j)[11].substring(4,6)+"-"+buy.get(j)[11].substring(6,8);//m日期
			}
			
//			for(j=0;j<sell.size();j++){
//				sell.get(j)[4]=sell.get(j)[4].substring(0,4)+"-"+sell.get(j)[4].substring(4,6)+"-"+sell.get(j)[4].substring(6,8);
//				sell.get(j)[5]=sell.get(j)[5].substring(0,4)+"-"+sell.get(j)[5].substring(4,6)+"-"+sell.get(j)[5].substring(6,8);
//
//			}
			
			//输出各种形态，用买入形态和卖出形态进行匹配，两重循环，买入形态一层，卖出形态一层
			try{
						FileOutputStream fos = new FileOutputStream(fileout,true);
						OutputStreamWriter osw = new OutputStreamWriter(fos);
						BufferedWriter write = new BufferedWriter(osw);	
				
					for(j=0;j<=buy.size()-1;j++){
					
				
					write.newLine();
					write.write(sname[i]);
					
					write.write("	");
					write.write(buy.get(j)[4]);//A时间
					write.write("	");
					write.write(buy.get(j)[0]);//A收盘价
					write.write("	");
					write.write(buy.get(j)[9]);//B时间
					write.write("	");
					write.write(buy.get(j)[1]);//B收盘价
					write.write("	");
					write.write(buy.get(j)[10]);//C时间
					write.write("	");
					write.write(buy.get(j)[2]);//C收盘价
					write.write("	");
					write.write(buy.get(j)[5]);//D时间
					write.write("	");
					write.write(buy.get(j)[3]);//D收盘价
					write.write("	");
					write.write(buy.get(j)[11]);//M时间
					write.write("	");
					write.write(buy.get(j)[12]);//M收盘价
					write.write("	");
					startTime = new SimpleDateFormat("yyyy-MM-dd").parse(buy.get(j)[11]); 
					write.write(buy.get(j)[7]);//ABC 涨幅
					write.write("	");
					write.write(buy.get(j)[8]);//BCD涨幅
					write.write("	");
					write.write(buy.get(j)[13]);//BCM涨幅
					write.write("	");
//					write.write("	");
//					write.write(buy.get(j)[4]);
//					write.write("	");
//					write.write(buy.get(j)[0]);
//					write.write("	");
//					write.write(buy.get(j)[1]);
//					write.write("	");
//					write.write(buy.get(j)[2]);
//					write.write("	");
//					write.write(buy.get(j)[3]);
//					write.write("	");
//					write.write(buy.get(j)[5]);
//					write.write("	");
					sellmark=0;
//					for(k=0;k<=sell.size()-1;k++){//买入时间和卖出时间进行比较 配对
//						if(sell.get(k)[5].compareTo(buy.get(j)[5])>0){
//							sellmark=k;
//			//				System.out.println(sellmark);
//							break;
//						}
//					}
			//		System.out.println(sellmark);
					
					
					
					k=(int) sellmark;
					//对买入卖出状态进行判断 从而得出是该买入还是卖出
//					if(k==0) { //k=0表明只买入，未卖出，继续持有   卖出形态没有形成    最后的结果是止盈卖出 和 止损卖出
//						for(h=Integer.valueOf(buy.get(j)[6])+1;price[h][0]!=null;h++){
//							if(Float.valueOf(price[h][3])>Float.valueOf(buy.get(j)[3])*1.3){
//								write.write("");
//								write.write("	");
//								
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
//								write.write("	");
//								write.write("止盈卖出");
//								write.write("	");
//								
//								write.write("0.3");
//								write.write("	");
//				//				write.newLine();
//								break;
//							}
//							if(Float.valueOf(price[h][4])<Float.valueOf(buy.get(j)[3])*0.9){
//								write.write("");
//								write.write("	");
//								
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
//								write.write("	");
//								write.write("止损卖出");
//								write.write("	");
//								
//								write.write("-0.1");
//								write.write("	");
//			//					write.newLine();
//								break;
//							}
//						}
//			//			write.newLine();
//						continue;
//					}
//					if (k!=0) {//有买入，也有卖出
//						int mark=0;
//						for(h=Integer.valueOf(buy.get(j)[6])+1;h<=Integer.valueOf(sell.get(k)[6]);h++){
//							if(Float.valueOf(price[h][3])>Float.valueOf(buy.get(j)[3])*1.3){
//								write.write("");
//								write.write("	");
//								
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
//								write.write("	");
//								write.write("止盈卖出");
//								write.write("	");
//								
//								write.write("0.3");
//								write.write("	");
//				//				write.newLine();
//								mark=1;
//								break;
//							}
//							if(Float.valueOf(price[h][4])<Float.valueOf(buy.get(j)[3])*0.9){
//								write.write("");
//								write.write("	");
//								
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write("");
//								write.write("	");
//								write.write(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
//								write.write("	");
//								write.write("止损卖出");
//								write.write("	");
//								
//								write.write("-0.1");
//								write.write("	");
//				//				write.newLine();
//								mark=1;
//								break;
//							}
//						}
//						if(mark==1) continue;
//					}////以上是k!= 0    有买入，也有卖出  至此配对完毕将信息写入文件中
					//以下写入 正常卖出的相关信息
//					write.write(sell.get(k)[4]);
//					write.write("	");
//					write.write(sell.get(k)[0]);
//					write.write("	");
//					write.write(sell.get(k)[1]);
//					write.write("	");
//					write.write(sell.get(k)[2]);
//					write.write("	");
//					write.write(sell.get(k)[3]);
//					write.write("	");
//					write.write(sell.get(k)[5]);
//					write.write("	");
//					write.write("指数涨跌");
//					write.write("	");
//					
//					write.write(Float.toString((Float.valueOf(sell.get(k)[3])-Float.valueOf(buy.get(j)[3]))/Float.valueOf(buy.get(j)[3])));
//					write.write("	");
			//		write.newLine();
				}
					write.close();
				}catch(Exception ee){
					ee.printStackTrace();
				
				
				
			}
				
		
		buy.clear();
		sell.clear();
		hight.clear();
		lowt.clear();
		for(h=0;h<1000;h++)
			for(z=0;z<8;z++){
				price[h][z]=null;
			}
		}	
				
	}	
		
	}
		
		
		


