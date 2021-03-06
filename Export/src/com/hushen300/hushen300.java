package com.hushen300;
/**
 * 本程序完成的功能：
 * 1.找到买入形态，以买入价的0.93为止损
 * 2.在买入点以后若出现左右三根的低点且大于原止损价  产生推进止损
 * 3.若收盘价低于止损价 则卖出
 * 4.将相关信息写入文件
 */


//加上画图功能，画图不完善，修改高点为5根K线，低点为5跟K线
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Color;//颜色系统
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.text.SimpleDateFormat;//时间格式
import java.awt.Paint;//画笔系统
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.jfree.data.time.*;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.*;



public class hushen300 extends JFrame {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	
	static  String inputst="2010-07-01";//手动输入数据的起始日期
	static String inputet="2013-07-01";//手动输入数据的结束日期
	public static void main(String[] args) throws ParseException {
		String fileDir = "D:\\export1\\";
		String hushengZhiShuPath = "D:\\export1\\SZ399300.TXT";
		
		int low_k_min_value = 10;//低点之间k线数 最少应满足的数量
		int high_k_min_value = 10;//高点之间k线数 最少应满足的数量
		int AC_Dis = 100; //ac两点之间最大允许的k线的数量
		int same_high_dis = 5;//相同的两个高点之间的距离
		int same_low_dis = 5;//相同的两个低点之间的距离
		
		//下面是画图的定义
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		  final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        final Calendar cal = Calendar.getInstance();
		   
		     //用一个series数组保存所有有符合形态的股票的信息.高开低收数据序列，股票K线图的四个数据，依次是开盘，最高，最低，收盘
		     final OHLCSeries series[]=new  OHLCSeries[2000];
		     int seriesNCount=0;
		     final TimeSeries series2[]=new TimeSeries[2000];//对应时间成交量数据
		     for(int ii=0;ii<2000;ii++)//初始化数组
		     {
		    	 series[ii]=new  OHLCSeries("");
		    	 series2[ii]=new TimeSeries("");
		    	 
		     }
		     
		     final String stockname[]=new String[5500];
		     final String sts[]=new String[5500];//这是什么---------start_times
		     final String ets[]=new String[5500];//这是什么---------end_times
		     final String At[]=new String[5500];
		     final String Bt[]=new String[5500];
		     final String Ct[]=new String[5500];
		     final String Dt[]=new String[5500];
		     final  String Mt[]=new String[5500];
		     final String Ap[]=new String[5500];
		     final String Bp[]=new String[5500];
		     final String Cp[]=new String[5500];
		     final String Dp[]=new String[5500];
		     final String Mp[]=new String[5500];
		     final String AAt[]=new String[5500];
		     final String BBt[]=new String[5500];
		     final  String CCt[]=new String[5500];
		     final  String DDt[]=new String[5500];
		     final  String AAp[]=new String[5500];
		     final  String BBp[]=new String[5500];
		     final String CCp[]=new String[5500];
		     final String DDp[]=new String[5500];
		     final String abcStock[]=new String[5500];
		     final String abcIndex[]=new String[5500];
		     final String bcdStock[]=new String[5500];
		     final String bcdIndex[]=new String[5500];
		     final String bcmStock[]=new String[5500];
		     final String bcmIndex[]=new String[5500];
		     final String SabcStock[]=new String[5500];
		     final  String SabcIndex[]=new String[5500];
		     final String SbcdStock[]=new String[5500];
		     final String SbcdIndex[]=new String[5500];
		   
		 //下面是策略的定义    

		Date inputST=null;//中间计算变量
		Date inputET=null;
	
		String line=null;
		String[] lineaftersplit=null;
		String[][] price=new String[1500][8];//股票价格信息
		String[][] priceshangzheng=new String[1500][8];//沪深300指数
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
		float e=0;//
		float et=0;
		float za=0;
		float zb=0;
		float zc=0;
		float zd=0;
		float zm=0;//Z加上abcdm代表abcdm点对应的指数
		float bmark=0;//
		float sellmark=0;
		File namefile=new File(fileDir) ;
		final String[] sname=namefile.list();//股票的文件名   这个写得好
		int i=0;
		int j=0;
		int k=0;
		int f=0;
		int h=0;//循环变量
		double totalHolddays=0;
		double avgWin=0;
		double avgLoss=0;
		Date startTime=null;
		Date endTime=null;
		int totalholdtime=0;
		
		int zhisunCount=0;//止损交易数
//		int zhiyingCount=0; //止盈交易数
		int normalCount=0;//正常卖出的次数
		int totalCount=0;//总共交易笔数？
		double zhisun=-0.07;
		double zhiying=0.3;
		float zhisun_price = 0;
		double normalRet=0;//正常交易的每笔收益
		int winCount=0;//记录正常交易中win的笔数
		int lossCount=0;//记录正常交易中loss的笔数
		float fee=(float)(2.5/1000);//一次交易的交易费用
		float abdiff=(float) 0.05;//ab之间价格距离
		int ac=100;//ac之间的最大距离是100根K线
		float win_price = 0;//赢的钱的总数
		float loss_price = 0;//亏的钱的总数
		
		
		
		File fileout =null;
	//	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	//以下是创建输出数据
		String nowdate=dateFormat.format(new java.util.Date());//今天的日期
		fileout =new File("D:\\newwirthoutput\\"+"hushen__日线—"+nowdate+".txt");
		if (!fileout.exists()) {             
			try {
				fileout.createNewFile();
				System.out.println(fileout + "已创建！");
			} catch (IOException e1) {
					// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("创建输出文件失败！");
			}              
			
		
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
				write.write("卖出时间");
				write.write("	");
				write.write("卖出收盘价");
//				write.write("卖出形态开始");
//				write.write("	");
//				write.write("A'");
//				write.write("	");
//				write.write("B'时间");
//				write.write("	");
//				write.write("B'");
//				write.write("	");
//				write.write("C'时间");
//				write.write("	");
//				write.write("C'");
//				write.write("	");
//				write.write("卖出时间");
//				write.write("	");
//				write.write("D'");
//				write.write("	");
//				write.write("ABC'涨幅");
//				write.write("	");
//				write.write("BCD'涨幅");
				write.write("	");
				write.write("卖出操作");
				write.write("	");
//				write.write("盈利");
				write.write("	");
			
				
				write.close();
			}catch(Exception ee){
				ee.printStackTrace();
			}
			
			
		//以下读入上证50指数的数据
		try{	

			FileReader filereader=new FileReader(hushengZhiShuPath);//上证50指数数据的存储文件
			BufferedReader reader=new BufferedReader(filereader);
			
			//		if(sname[i].compareTo("sh600556")<0) continue;
			line=reader.readLine();
			line=reader.readLine();
			j=0;
			while((line=reader.readLine())!=null){
			
				lineaftersplit=line.split("	");
				if(lineaftersplit.length<2) break;
				if(lineaftersplit[1]==lineaftersplit[2]&&lineaftersplit[2]==lineaftersplit[3]&&lineaftersplit[3]==lineaftersplit[4]&&lineaftersplit[5]=="0") break;//这是什么情况？？？？？
				lineaftersplit[0]=lineaftersplit[0].replace("/","");
				priceshangzheng[j][0]=Integer.toString(j);
				priceshangzheng[j][1]=lineaftersplit[0];//riqi
				priceshangzheng[j][2]=lineaftersplit[1];//kaipan
				priceshangzheng[j][3]=lineaftersplit[2];//zuigao
				priceshangzheng[j][4]=lineaftersplit[3];//zuidi
				priceshangzheng[j][5]=lineaftersplit[4];//shoupan
				priceshangzheng[j][6]=lineaftersplit[5];//liang
				
		//		System.out.println(price[j][6]);
				j++;
				
			}
		
			reader.close();
			filereader.close();
		}
		catch(Exception ee) {ee.printStackTrace();}

	//		循环读取每一个股票文件然后将相关数据与上证指数相比较
		for(i=0;i<sname.length;i++)	{
			//读取每一个股票文件，并将开 高 低 收 数据存入series数组 将成交量存入series2数组
			try{	
				File n=new File(sname[i]);//这个写的好
				sname[i]=n.getName();
			//	System.out.println(sname[i]);
				FileReader filereader=new FileReader(fileDir+sname[i]);
				BufferedReader reader=new BufferedReader(filereader);
				sname[i]=sname[i].substring(0,8);//取到股票代码，不要后面的文件后缀.TXT
				//		if(sname[i].compareTo("sh600556")<0) continue;
				line=reader.readLine();
				line=reader.readLine();
				j=0;
				while((line=reader.readLine())!=null){
					
					lineaftersplit=line.split("	");
					if(lineaftersplit.length<2) break;
					if(lineaftersplit[1]==lineaftersplit[2]&&lineaftersplit[2]==lineaftersplit[3]&&lineaftersplit[3]==lineaftersplit[4]&&lineaftersplit[5]=="0") break;
					lineaftersplit[0]=lineaftersplit[0].replace("/","");
					price[j][0]=Integer.toString(j);//用于记录数据是来自第几根k线
					price[j][1]=lineaftersplit[0];//riqi
					price[j][2]=lineaftersplit[1];//kaipan
					price[j][3]=lineaftersplit[2];//zuigao
					price[j][4]=lineaftersplit[3];//zuidi
					price[j][5]=lineaftersplit[4];//shoupan
					price[j][6]=lineaftersplit[5];//liang
					 //先添加后面的日期,股票K线图的四个数据，依次是开，高，低，收
						 int year=Integer.valueOf(price[j][1].substring(0, 4));
						 int month;
						 int day;
						 if(price[j][1].substring(4,6).startsWith("0"))
						 {
							 month=Integer.valueOf(price[j][1].substring(5, 6)); 
						 }
						 else
						 {
							 month=Integer.valueOf(price[j][1].substring(4, 6)); 
						 }
						 if(price[j][1].substring(6,8).startsWith("0"))
						 {
							 day=Integer.valueOf(price[j][1].substring(7, 8)); 
						 }
						 else
						 {
							 day=Integer.valueOf(price[j][1].substring(6, 8));
						 }
						 
						 //System.out.println(""+new Day(day, month,year));
//						 System.out.println("2--"+Double.valueOf(price[j][2]));
//						 System.out.println("3--"+Double.valueOf(price[j][3]));
//						 System.out.println("4--"+Double.valueOf(price[j][4]));
//						 System.out.println("5--"+Double.valueOf(price[j][5]));
				     series[i].add(new Day(day, month,year), Double.valueOf(price[j][2]), Double.valueOf(price[j][3]), Double.valueOf(price[j][4]), Double.valueOf(price[j][5]));
				     series2[i].add(new Day(day, month,year), Double.valueOf(price[j][6]));
				     j++;
						
				}
				
				reader.close();
				filereader.close();
			}
				
			catch(Exception ee) {ee.printStackTrace();}
			//找到低点，并将低点存入lowt数组
			for(j=10;price[j+10][0]!=null;j++){//低点的定义为收盘价低于或等于前后5根k线的收盘价
					//		System.out.prjntln(j);
							if(Float.valueOf(price[j][5])<=Float.valueOf(price[j-1][5])
									&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-2][5])
											&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-3][5])&&
											Float.valueOf(price[j][5])<=Float.valueOf(price[j-4][5])
													&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-5][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j-6][5])&&
																	Float.valueOf(price[j][5])<=Float.valueOf(price[j-7][5])
																	  &&Float.valueOf(price[j][5])<=Float.valueOf(price[j-8][5])
																	  &&
																					Float.valueOf(price[j][5])<=Float.valueOf(price[j-9][5])&&
																							Float.valueOf(price[j][5])<=Float.valueOf(price[j-10][5])&&
																							Float.valueOf(price[j][5])<=Float.valueOf(price[j+1][5])&&
													Float.valueOf(price[j][5])<=Float.valueOf(price[j+2][5])
															&&Float.valueOf(price[j][5])<=Float.valueOf(price[j+3][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j+4][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j+5][5])
															&&Float.valueOf(price[j][5])<=Float.valueOf(price[j+6][5])&&
																			Float.valueOf(price[j][5])<=Float.valueOf(price[j+7][5])
																					&&Float.valueOf(price[j][5])<=Float.valueOf(price[j+8][5])
																				&&Float.valueOf(price[j][5])<=Float.valueOf(price[j+9][5])&&
																									Float.valueOf(price[j][5])<=Float.valueOf(price[j+10][5])
															){
								lowt.add(Float.valueOf(price[j][0]));//lowt用于存放低点位置，用k线根数表示
						//		System.out.prjntln(date[i]);
						//		System.out.println();
							}
			}
						
			for(j=1;j<lowt.size();j++){//当低点之间相差k线根数小于10时，不要前一个低点，用于防止最低值相等时，存在多个低点的情况
				if(lowt.get(j)-lowt.get(j-1)<=low_k_min_value){
					lowt.remove(j-1);
					j--;
				}
			}
				
				
			for(j=10;price[j+10][1]!=null;j++){//高点的定义为高于或等于前后10根k线的最高点
					//		System.out.prjntln(j);
				//找到高点，并将高点存入hight数组
				if(Float.valueOf(price[j][5])>=Float.valueOf(price[j-1][5])
									&&Float.valueOf(price[j][5])>=Float.valueOf(price[j-2][5])
											&&Float.valueOf(price[j][5])>=Float.valueOf(price[j-3][5])&&
											Float.valueOf(price[j][5])>=Float.valueOf(price[j-4][5])
													&&Float.valueOf(price[j][5])>=Float.valueOf(price[j-5][5])&&
															Float.valueOf(price[j][5])>=Float.valueOf(price[j-6][5])&&
															Float.valueOf(price[j][5])>=Float.valueOf(price[j-7][5])&&
																	Float.valueOf(price[j][5])>=Float.valueOf(price[j-8][5])&&
																			Float.valueOf(price[j][5])>=Float.valueOf(price[j-9][5])&&
																					Float.valueOf(price[j][5])>=Float.valueOf(price[j-10][5])&&
													Float.valueOf(price[j][5])>=Float.valueOf(price[j+1][5])&&
													Float.valueOf(price[j][5])>=Float.valueOf(price[j+2][5])
															&&Float.valueOf(price[j][5])>=Float.valueOf(price[j+3][5])&&
															Float.valueOf(price[j][5])>=Float.valueOf(price[j+4][5])&&
															Float.valueOf(price[j][5])>=Float.valueOf(price[j+5][5])&&
																	Float.valueOf(price[j][5])>=Float.valueOf(price[j+6][5])&&
																	Float.valueOf(price[j][5])>=Float.valueOf(price[j+7][5])&&
																	Float.valueOf(price[j][5])>=Float.valueOf(price[j+8][5])&&
																	Float.valueOf(price[j][5])>=Float.valueOf(price[j+9][5])&&
																	Float.valueOf(price[j][5])>=Float.valueOf(price[j+10][5])
															){
					hight.add(Float.valueOf(price[j][0]));//hight用于存放高点位置，用k线根数表示
						//		System.out.prjntln(date[i]);
						//		System.out.println();
				}
			}
						
			for(j=1;j<hight.size();j++){//当高点之间相差k线根数小于10时，不要前一个高点，用于防止最大值相等是，存在多个高点的情况
				if(hight.get(j)-hight.get(j-1)<=high_k_min_value){
					hight.remove(j-1);
					j--;
				}
			}
				
			for(j=0;j<hight.size()-1;j++){//找买入形态
				//a c 是两个相邻的高点
				a=Float.valueOf(price[hight.get(j).intValue()][5]);//5是当前处理的这根k线所对应的收盘价
				at=Float.valueOf(price[hight.get(j).intValue()][0]);//0是当前处理的这根k线所对应的序号
				c=Float.valueOf(price[hight.get(j+1).intValue()][5]);
				ct=Float.valueOf(price[hight.get(j+1).intValue()][0]);
				System.out.println("=========================================================================a点时间：" + price[(int)at][1]);
				System.out.println("=========================================================================c点时间：" + price[(int)ct][1]);
				if(price[(int)at][1].compareTo("20120830")==0){
					System.out.println(" ========================================到达20120830");
				}
				if(c<a) {
					System.out.println("a点时间：" + price[(int)at][1]);
					System.out.println("c点时间：" + price[(int)ct][1]);
					System.out.println("c点收盘价比a点收盘价低");
					continue;//形态条件：c点收盘价必须比a点收盘价高
				}
				if(ct-at>ac) {
					System.out.println("A到C之间的距离>=ac根");
					continue;//形态条件：A到C之间的距离<=ac根
				}
				
				//求A、C间最低点B
				b=Float.valueOf(price[hight.get(j).intValue()+1][5]);//从A点所在K线的下一根k线开始找起
				bt=Float.valueOf(price[hight.get(j).intValue()+1][0]);
				bmark=Float.valueOf(price[hight.get(j).intValue()+1][5]);
				float nowbt=bt;//初始化nowbt
				for(k=(int)bt;k<ct;k++){
					if(Float.valueOf(price[k][5])<bmark){//用收盘价进行比较，找出最小值
						b=Float.valueOf(price[k][5]);
						nowbt=k;
						bmark=Float.valueOf(price[k][5]);
					}
					else if(Float.valueOf(price[k][5])==bmark)//若相等出现多个低点
					{
						//若出现多个最低点时，且两者相差<=5，则取后面一个为低点
						if(k-nowbt<=same_low_dis)
						{
							b=Float.valueOf(price[k][5]);
							nowbt=k;
							bmark=Float.valueOf(price[k][5]);	
						}
					}
						
				}
				bt=nowbt;//找到b点所在的k线
				//对买入条件进行判断
				if(Float.valueOf(price[(int)at][5])<(1+abdiff)*Float.valueOf(price[(int)bt][5]))
				{
					System.out.println("============================a点时间：" + price[(int)at][1]);
					System.out.println("============================b点时间：" + price[(int)bt][1]);
					System.out.println("============================买入条件不符合 A>1.2B");
					continue;//若买入条件：A>1.2B，不满足的话，则继续重新找A
				}
				//找D点
				//dt=ct+(bt-at);//2013-7-4之前的策略
				dt = ct + (ct-at)/2;//2013-7-4之后的策略
//				d=Float.valueOf(price[(int)dt][5]);
				float exactBuy;//exactBuy是真的买入点
				float shouPrice;
				float kaiPrice;
				float lastFiveTrade;
				int buyMark=0;
				
				if(price[(int)dt][0] != null) 
				{
					d=Float.valueOf(price[(int)dt][5]);
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
							buyMark=1;//标记为买入
							mt=exactBuy;
							m=Float.valueOf(price[(int)exactBuy][5]);//收盘价
						}
						else
						{
							exactBuy=exactBuy+1;
							
						}
					}
					
					if(buyMark==0)//目前为止出现D点，但未出现真正的M点, 则继续找   这句话的意义？？？？？？？？？？？================
					{
						System.out.println("目前为止出现D点，但未出现真正的M点");
						continue;
					}
				}
				else
				{
					break;//如果ABC已生成，但D未生成，没有形成一个买入形态，则看下一个股票的情况
				}
				
					//下面为找买入形态abcdm点对应的指数点
				if(sname[i].substring(0, 3).compareTo("SH6")==0 || sname[i].substring(0, 3).compareTo("SZ0")==0){
					for(k=(int)at;priceshangzheng[k][1]!=null;k++){
							
						if(price[(int)at][1].compareTo(priceshangzheng[k][1])==0){//a点  用日期进行比较  若相同则知道是当日大盘指数
							za=Float.valueOf(priceshangzheng[k][5]);
							continue;
						}
						
						if(price[(int)bt][1].compareTo(priceshangzheng[k][1])==0){//b点
							zb=Float.valueOf(priceshangzheng[k][5]);
							continue;
								
						}
						if(price[(int)ct][1].compareTo(priceshangzheng[k][1])==0){//c点
							zc=Float.valueOf(priceshangzheng[k][5]);
							continue;
						}
						if(price[(int)dt][1].compareTo(priceshangzheng[k][1])==0){//d点
							zd=Float.valueOf(priceshangzheng[k][5]);
							continue;
						}	
						if(price[(int)mt][1].compareTo(priceshangzheng[k][1])==0){//m点
							zm=Float.valueOf(priceshangzheng[k][5]);
							break;
						}
					}
				}
					
				
					
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
				String[] rs=new String[15];
				rs[0]=price[(int)at][5];
				rs[1]=price[(int)bt][5];
				rs[2]=price[(int)ct][5];
				rs[3]=price[(int)dt][5];
				rs[4]=price[(int)at][1];
				rs[5]=price[(int)dt][1];
				rs[6]=Integer.toString((int)mt);//0为a收 1为b收 2为c收 3为d收 4为a日期 5为d日期 6为M序号
				rs[7]=""+(c-a)/a+",,,"+(zc-za)/za;//7为abc期间，股票涨幅和大盘涨幅
				rs[8]=""+(d-b)/b+",,,"+(zd-zb)/zb;//8为bcd期间，股票涨幅和大盘涨幅
				rs[9]=price[(int)bt][1];//9为b日期
				rs[10]=price[(int)ct][1];//10为c日期
				rs[11]=price[(int)mt][1];//11为m点日期
			//	System.out.println(rs[11]);
				rs[12]=price[(int)mt][5];//12为m收盘价
				rs[13]=""+(m-b)/b+",,,"+(zm-zb)/zb;//13为bcm阶段，股票涨幅和大盘涨幅
				
//				buy.add(rs);//存储买入形态  //这是2013-7-4之前的策略
//				System.out.println("符合买入形态");
				if((mt-ct)<(ct-at)){//这是2013-7-4之后的策略
					buy.add(rs);//存储买入形态
					System.out.println("符合买入形态");
				}
//				if(rs[11].compareTo("20130701")<0 ){/////7-02 添加时间限制  大于4月30号，小于7月1号
//					if( rs[11].compareTo("20130330") > 0 ){
//						buy.add(rs);//存储买入形态
//						System.out.println("符合买入形态");
//					}
//				}
			}
				
			
			for(j=0;j<buy.size();j++){//更改时间格式
				buy.get(j)[4]=buy.get(j)[4].substring(0, 4)+"-"+buy.get(j)[4].substring(4,6)+"-"+buy.get(j)[4].substring(6,8);//a日期
				buy.get(j)[5]=buy.get(j)[5].substring(0, 4)+"-"+buy.get(j)[5].substring(4,6)+"-"+buy.get(j)[5].substring(6,8);//d日期
				buy.get(j)[9]=buy.get(j)[9].substring(0, 4)+"-"+buy.get(j)[9].substring(4,6)+"-"+buy.get(j)[9].substring(6,8);//b日期
				buy.get(j)[10]=buy.get(j)[10].substring(0, 4)+"-"+buy.get(j)[10].substring(4,6)+"-"+buy.get(j)[10].substring(6,8);//c日期
				buy.get(j)[11]=buy.get(j)[11].substring(0, 4)+"-"+buy.get(j)[11].substring(4,6)+"-"+buy.get(j)[11].substring(6,8);//m日期
			}

			
			//输出各种形态，用买入形态和卖出形态进行匹配，两重循环，买入形态一层，卖出形态一层
			try{
						FileOutputStream fos = new FileOutputStream(fileout,true);
						OutputStreamWriter osw = new OutputStreamWriter(fos);
						BufferedWriter write = new BufferedWriter(osw);	
				
					for(j=0;j<=buy.size()-1;j++){  //对买入列表进行全循环
					
					write.newLine();
					write.write(sname[i]);
					System.out.println("股票代码：" + sname[i]);
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
				    At[seriesNCount]=buy.get(j)[4];	//A时间
				    Ap[seriesNCount]=buy.get(j)[0]; //A收盘价
				    Bt[seriesNCount]=buy.get(j)[9];	//B时间
				    Bp[seriesNCount]=buy.get(j)[1]; //B收盘价
				    Ct[seriesNCount]=buy.get(j)[10];//C时间
				    Cp[seriesNCount]=buy.get(j)[2];//C收盘价
				    Dt[seriesNCount]=buy.get(j)[5];//D时间
				    Dp[seriesNCount]=buy.get(j)[3];//D收盘价
				    Mt[seriesNCount]=buy.get(j)[11];//M时间
				    Mp[seriesNCount]=buy.get(j)[12]; //M收盘价 
				     abcStock[seriesNCount]=buy.get(j)[7];//ABC 涨幅
				     abcIndex[seriesNCount]="";
				     bcdStock[seriesNCount]=buy.get(j)[8];//BCD涨幅
				     bcdIndex[seriesNCount]="";
				     bcmStock[seriesNCount]=buy.get(j)[13];//BCM涨幅
				     bcmIndex[seriesNCount]="";
		
					sellmark=0;

					k=(int) sellmark;
					float push=0;
//					if(k==0) {//k=0表明只买入，未卖出，继续持有   卖出形态没有形成    最后的结果是止盈卖出 和 止损卖出
						h=Integer.valueOf(buy.get(j)[6])+1;   //m序号加1，从m点后一根k线开始找卖出点  是否存在
						zhisun_price = (float)( Float.valueOf(price[h-1][5]) * (1+zhisun) );//求出止损价买入点的价格*止损率
						System.out.println("止损价格：" + zhisun_price);
						System.out.println("m的价格："+price[h-1][5]);
						System.out.println("h初始化时间：" + price[h][1]+"h初始化收盘价："+price[h][5]);
						for(;price[h][0]!=null && price[h+1][0]!=null && price[h+2][0]!=null && price[h+3][0]!=null;){
						System.out.println("h所在的k线日期：" + price[h][1]);
							if( Float.valueOf(price[h][4]) < zhisun_price){//仅仅只用price[h][5]比较即可   加入止损价格 若跌破0.93的买入价  止损  //////7-8 改为当 当日的最低价低于止损，以当日收盘价卖出
								System.out.println("跌破止损价格！！止损卖出 止损价格：" + zhisun_price);
								System.out.println("时间：" + price[h][1]+"收盘价："+price[h][5]);
								//止损以后的操作...~~~~~~~~~~~~~~~~~~~~~~~~~~~从这里开始写吧
								//止损交易次数加一
								zhisunCount++;
								//计算买入卖出价格差  
								normalRet=normalRet+(Float.valueOf(price[h][5])-Float.valueOf(buy.get(j)[12]))/(Float.valueOf(buy.get(j)[12])-fee);
								if((Float.valueOf(price[h][5])-Float.valueOf(buy.get(j)[12]))/(Float.valueOf(buy.get(j)[12])-fee)>0)//如果卖出时的价格挣钱了
								{
									win_price = win_price + (Float.valueOf(price[h][5])-Float.valueOf(buy.get(j)[12])) - fee ; //赢的钱减去每次交易的费用
									System.out.println("卖出时挣钱了");
									winCount++;
									avgWin=avgWin+(Float.valueOf(price[h][5])-Float.valueOf(buy.get(j)[12]))/(Float.valueOf(buy.get(j)[12])-fee);
									//将卖出时间  卖出价格 计算盈率和胜率  等属性写入文件
									write.write(price[h][1]);//卖出时间
									write.write("	");
									write.write(price[h][5]);//卖出收盘价
									write.write("	");
									write.write("止损卖出");
									write.write("	");
									//write.write(String.valueOf(win_price));//写入winprice
								}
								else//如果卖出时的价格亏钱了
								{
									loss_price = loss_price + (Float.valueOf(price[h][5])-Float.valueOf(buy.get(j)[12])) - fee ;//亏的钱减去每次交易的费用
									System.out.println("卖出时亏钱了");
									lossCount++;
									avgLoss=avgLoss+(Float.valueOf(price[h][5])-Float.valueOf(buy.get(j)[12]))/(Float.valueOf(buy.get(j)[12])-fee);
									//将卖出时间  卖出价格  计算盈率和胜率  等属性写入文件
									write.write(price[h][1]);//卖出时间
									write.write("	");
									write.write(price[h][5]);//卖出收盘价
									write.write("	");
									write.write("止损卖出");
									write.write("	");
									//write.write(String.valueOf(loss_price));//写入winprice
								}
								break;//break 匹配下一个买入点
							}
							//这里改过，> 全部改成 < 了，找出前后三根k线的最低点  即 产生新的推进止损点
							if(Float.valueOf(price[h][5]) > zhisun_price && Float.valueOf(price[h][5])<Float.valueOf(price[h-1][5])
									&&Float.valueOf(price[h][5])<Float.valueOf(price[h-2][5])
											&&Float.valueOf(price[h][5])<Float.valueOf(price[h-3][5])
													&&Float.valueOf(price[h][5])<Float.valueOf(price[h+1][5])
													&&Float.valueOf(price[h][5])<Float.valueOf(price[h+2][5])
															&&Float.valueOf(price[h][5])<Float.valueOf(price[h+3][5])
													)//如果在m点后产生了前后三根的低点
							{
								//if( Float.valueOf(price[h][5]) > zhisun_price ) //如果这个低点大于止损价
									zhisun_price=Float.valueOf(price[h][5]);//将止损推进到新的高点
								System.out.println("产生新的止损价格：" + zhisun_price);
							}
							h++;
						}
						System.out.println(" break 以后的 匹配下一个买入点");
			
				}//这个括号是：买入的那层循环结束
					write.close();
				}catch(Exception ee){
					ee.printStackTrace();
			}
				
		//每只股票输出结束后，进行各种初始化
	
		buy.clear();
		sell.clear();
		hight.clear();
		lowt.clear();
		for(h=0;h<1000;h++)
			for(f=0;f<8;f++){
				price[h][f]=null;
			}
		
		}	
		
	//System.out.println("止损卖出次数:"+zhisunCount+"\t止盈卖出次数:"+zhiyingCount+"\t正常卖出次数:"+normalCount);	
//	System.out.println("正常卖出的平均收益是"+(normalRet/normalCount));
	System.out.println("止损（包含推进止损）卖出的平均收益（买入卖出价格差之和/总交易次数）是"+(normalRet/zhisunCount));
	//totalCount=zhisunCount+zhiyingCount+normalCount;
	totalCount=normalCount;
	double totalRet=0;
	//System.out.println("所有交易的平均收益是"+((normalRet+(0.3-2.5/1000)*zhiyingCount+(-0.1-2.5/1000)*zhisunCount)/totalCount));
	
	//System.out.println("总交易次数:"+totalCount);
	System.out.println("总交易次数:"+zhisunCount);
	//System.out.println("正常交易中胜率:"+((float)winCount/normalCount));
	System.out.println("止损交易中（包含推进止损）胜率:"+((float)winCount/zhisunCount));
	//System.out.println("所有交易中胜率:"+((float)(winCount+zhiyingCount)/totalCount));
	//System.out.println("所有交易的胜利次数"+(winCount+zhiyingCount));
	System.out.println("所有交易的胜利次数"+(winCount));
	//System.out.println("所有交易的失败次数"+(lossCount+zhisunCount));
	System.out.println("所有交易的失败次数"+(lossCount));
	//avgWin=avgWin/(winCount+zhiyingCount);
	avgWin=avgWin/(winCount);
	System.out.println("avgWin:" + avgWin);
	//avgLoss=avgLoss/(lossCount+zhisunCount);
	avgLoss=avgLoss/(lossCount);
	System.out.println("avgLoss:" + avgLoss); //为负，所以计算盈率的时候需要加负号
	System.out.println("所有交易的平均盈率（avgWin/avgLoss）:"+(-avgWin/avgLoss));
	//System.out.println("所有交易的平均持仓时间(单位：h)：(总共持仓小时/总共交易次数)"+((float)totalholdtime/totalCount));
	inputST=new SimpleDateFormat("yyyy-MM-dd").parse(inputst);
	inputET = new SimpleDateFormat("yyyy-MM-dd").parse(inputet);
	int totaldiff=(int) ((inputET.getTime()-inputST.getTime())/(24*60*60*1000));
	//System.out.println("数据持有的时间段为"+inputst+"到"+inputet+","+((float)totaldiff/365));
	//System.out.println("所有交易的平均交易频率:"+((float)totalholdtime/totalCount)/((float)totaldiff/365*250));
	System.out.println("seriesNCount:"+seriesNCount); 
	FileOutputStream fos;
	try {
		fos = new FileOutputStream(fileout,true);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter write = new BufferedWriter(osw);	
		write.newLine();
		write.write("止损（包含推进止损）卖出的平均收益（买入卖出价格差之和/总交易次数）是 "+(normalRet/zhisunCount)+"	");
		write.newLine();
		write.write("总交易次数:"+zhisunCount + "	");
		write.newLine();
		write.write("所有交易中胜率:"+((float)winCount/zhisunCount) + "	");
		write.newLine();
		write.write("所有交易的胜利次数"+(winCount) + "	");
		write.newLine();
		write.write("所有交易的失败次数"+(lossCount) + "	");
		write.newLine();
		write.write("avgWin:" + avgWin + "	");
		write.newLine();
		write.write("avgLoss:" + avgLoss + "	");
		write.newLine();
		write.write("所有交易的平均盈率（avgWin/avgLoss）:"+(-avgWin/avgLoss) + "	");
		write.newLine();
//		write.write("所有交易的平均持仓时间"+((float)totalholdtime/totalCount) + "	");
//		write.newLine();
//		write.write("数据持有的时间段为"+inputst+"到"+inputet+","+((float)totaldiff/365) + "	");
//		write.newLine();
//		//write.write("所有交易的平均交易频率:"+((float)totalholdtime/totalCount)/((float)totaldiff/365*250) + "	");
//		write.newLine();
		write.write("seriesNCount:"+seriesNCount + "	");
		write.close();
		
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 
		final JFrame jf=new JFrame("渔夫系统");	
		Container con=jf.getContentPane();
		jf.setLayout(new BorderLayout());
		
		jf.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);//点击关闭按钮 退出系统
			}
		});
		
		Container con2=new Container();
		Container con3=new Container();
		con2.setLayout(new BorderLayout());
		con3.setLayout(new GridLayout(18,2,1,1));
		con3.add(new Label("输入是第几个样本"));
		final JTextField jta1=new JTextField("");	
		con3.add(jta1);
		con3.add(new Label(""));
		con3.add(new Label("A:"));
		final JTextField JAt=new JTextField("");
		final JTextField JAp=new JTextField("");
		con3.add(JAt);
		con3.add(JAp);
		con3.add(new Label("B:"));
		final JTextField JBt=new JTextField("");
		final JTextField JBp=new JTextField("");
		con3.add(JBt);
		con3.add(JBp);
		con3.add(new Label("C:"));
		final JTextField JCt=new JTextField("");
		final JTextField JCp=new JTextField("");
		con3.add(JCt);
		con3.add(JCp);
		con3.add(new Label("D:"));
		final JTextField JDt=new JTextField("");
		final JTextField JDp=new JTextField("");
		con3.add(JDt);
		con3.add(JDp);
		con3.add(new Label("M:"));
		final JTextField JMt=new JTextField("");
		final JTextField JMp=new JTextField("");
		con3.add(JMt);
		con3.add(JMp);
		
		con3.add(new Label("ABC:"));
		final JTextField JabcStock=new JTextField("");
		final JTextField JabcIndex=new JTextField("");
		con3.add(JabcStock);
		con3.add(JabcIndex);
		con3.add(new Label("BCD:"));
		final JTextField JbcdStock=new JTextField("");
		final JTextField JbcdIndex=new JTextField("");
		con3.add(JbcdStock);
		con3.add(JbcdIndex);
		con3.add(new Label("BCM:"));
		final JTextField JbcmStock=new JTextField("");
		final JTextField JbcmIndex=new JTextField("");
		con3.add(JbcmStock);
		con3.add(JbcmIndex);
		
		con3.add(new Label("A':"));
		final JTextField JAAt=new JTextField("");
		final JTextField JAAp=new JTextField("");
		con3.add(JAAt);
		con3.add(JAAp);
		con3.add(new Label("B':"));
		final JTextField JBBt=new JTextField("");
		final JTextField JBBp=new JTextField("");
		con3.add(JBBt);
		con3.add(JBBp);
		con3.add(new Label("C':"));
		final JTextField JCCt=new JTextField("");
		final JTextField JCCp=new JTextField("");
		con3.add(JCCt);
		con3.add(JCCp);
		con3.add(new Label("D':"));
		final JTextField JDDt=new JTextField("");
		final JTextField JDDp=new JTextField("");
		con3.add(JDDt);
		con3.add(JDDp);
		
		con3.add(new Label("A'B'C':"));
		final JTextField JSabcStock=new JTextField("");
		final JTextField JSabcIndex=new JTextField("");
		con3.add(JSabcStock);
		con3.add(JSabcIndex);
		con3.add(new Label("B'C'D':"));
		final JTextField JSbcdStock=new JTextField("");
		final JTextField JSbcdIndex=new JTextField("");
		con3.add(JSbcdStock);
		con3.add(JSbcdIndex);
		
		con2.add(con3,BorderLayout.NORTH);
		final JButton jb1=new JButton("时间前移");
		final JButton jb2=new JButton("时间后移");
		final JButton jb=new JButton("输出图形");
		con2.add(jb1,BorderLayout.WEST);
		con2.add(jb,BorderLayout.CENTER);
		con2.add(jb2,BorderLayout.EAST);
		//con.add(cp,BorderLayout.CENTER);
		con.add(con2,BorderLayout.EAST);
		
		jf.setSize(800,500);
		jf.pack();
		jf.setVisible(true);
		
		
	jb.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
		//	jta1.setText("good!");
			jf.setVisible(false);
			System.out.println("jb");
			  int num=Integer.valueOf(jta1.getText());
			  int numm=0;
			  double highValue = Double.MIN_VALUE;//设置K线数据当中的最大值
			  double minValue = Double.MAX_VALUE;//设置K线数据当中的最小值
			  double high2Value = Double.MIN_VALUE;//设置成交量的最大值
			  double min2Value = Double.MAX_VALUE;//设置成交量的最低值
			  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			  for(int q=0;q<sname.length;q++)
			  {
				  if(sname[q].equals(stockname[num]))
				  {
					numm=q;
				  }
			  }
			
			//保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
			   final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();
			   seriesCollection.addSeries(series[numm]);
			//保留成交量数据的集合
			TimeSeriesCollection timeSeriesCollection=new TimeSeriesCollection();
			timeSeriesCollection.addSeries(series2[numm]);
			System.out.println("num"+num);
			System.out.println("stTime"+sts[num]);
			System.out.println("etTime"+ets[num]);
			//获取K线数据的最高值和最低值
			int seriesCount = seriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
			for (int ii = 0; ii < seriesCount; ii++) 
			{
			     int itemCount = seriesCollection.getItemCount(ii);//每一个序列有多少个数据项
			     System.out.println("itemCount"+itemCount);
			     for (int j = 0; j < itemCount; j++)
			     {
				       if (highValue < seriesCollection.getHighValue(ii, j)) 
				       {//highValue是取第i个序列中的第j个数据项的最大值
				         highValue = seriesCollection.getHighValue(ii, j);
				       }
				       if (minValue > seriesCollection.getLowValue(ii, j)) 
				       {//minVaule是取第i个序列中的第j个数据项的最小值
				        minValue = seriesCollection.getLowValue(ii, j);
				       }
			     }

			}
			//获取成交量最高值和最低值
			int seriesCount2 = timeSeriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
			for (int ii = 0; ii < seriesCount2; ii++)
			{
			     int itemCount = timeSeriesCollection.getItemCount(ii);//每一个序列有多少个数据项
			     for (int j = 0; j < itemCount; j++) 
			     {
				       if (high2Value < timeSeriesCollection.getYValue(ii,j)) 
				       {//取第i个序列中的第j个数据项的值
				        high2Value = timeSeriesCollection.getYValue(ii,j);
				       }
				       if (min2Value > timeSeriesCollection.getYValue(ii, j)) 
				       {//取第i个序列中的第j个数据项的值
				        min2Value = timeSeriesCollection.getYValue(ii, j);
				       }
			     }

			}
			final CandlestickRenderer candlestickRender=new CandlestickRenderer();//设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
			candlestickRender.setUseOutlinePaint(true); //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
			candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//设置如何对K线图的宽度进行设定
			candlestickRender.setAutoWidthGap(0.001);//设置各个K线图之间的间隔
			candlestickRender.setUpPaint(Color.RED);//设置股票上涨的K线图颜色
			candlestickRender.setDownPaint(Color.GREEN);//设置股票下跌的K线图颜色
			//设置x轴，也就是时间轴
			DateAxis x1Axis=new DateAxis();
			//设置不采用自动设置时间范围
			x1Axis.setAutoRange(false);
			try{
				//设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
			   //要修改时间范围
			  inputst=At[num];
			  cal.setTime(sdf.parse(inputst));
		      cal.add(Calendar.DATE, 30);
		      inputet=sdf.format(cal.getTime());
		  	  x1Axis.setRange(dateFormat.parse(inputst),dateFormat.parse(inputet));
			}catch(Exception e){
			 e.printStackTrace();
			}
			x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
			x1Axis.setAutoTickUnitSelection(false);//设置不采用自动选择刻度值
			x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
			x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
			x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));//设置时间刻度的间隔，一般以周为单位
			x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//设置显示时间的格式
			NumberAxis y1Axis=new NumberAxis();//设定y轴，就是数字轴
			y1Axis.setAutoRange(false);//不使用自动设定范围
			y1Axis.setRange(minValue*0.9, highValue*1.1);//设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
			y1Axis.setTickUnit(new NumberTickUnit((highValue*1.1-minValue*0.9)/10));//设置刻度显示的密度
			XYPlot plot1=new XYPlot(seriesCollection,x1Axis,y1Axis,candlestickRender);//设置画图区域对象

			XYBarRenderer xyBarRender=new XYBarRenderer(){
			private static final long serialVersionUID = 1L;//为了避免出现警告消息，特设定此值
			public Paint getItemPaint(int ii, int j){//匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
			  if(seriesCollection.getCloseValue(ii,j)>seriesCollection.getOpenValue(ii,j)){//收盘价高于开盘价，股票上涨，选用股票上涨的颜色
			   return candlestickRender.getUpPaint();
			  }else{
			   return candlestickRender.getDownPaint();
			  }
			}};
			xyBarRender.setMargin(0.1);//设置柱形图之间的间隔
			NumberAxis y2Axis=new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的y轴设置
			y2Axis.setAutoRange(false);
			y2Axis.setRange(min2Value*0.9, high2Value*1.1);
			y2Axis.setTickUnit(new NumberTickUnit((high2Value*1.1-min2Value*0.9)/4));
			XYPlot plot2=new XYPlot(timeSeriesCollection,null,y2Axis,xyBarRender);//建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
			CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//建立一个恰当的联合图形区域对象，以x轴为共享轴
			combineddomainxyplot.add(plot1, 2);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
			combineddomainxyplot.add(plot2, 1);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
			combineddomainxyplot.setGap(10);//设置两个图形区域对象之间的间隔空间
			String stocknames=stockname[num];
			JFreeChart chart = new JFreeChart(stocknames, JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
			ChartPanel cp=new ChartPanel(chart); 
			
			
			 Container con=jf.getContentPane();
			 con.removeAll();
			 jf.setLayout(new BorderLayout());
			 Container con2=new Container();
			 Container con3=new Container();
			con2.setLayout(new BorderLayout());
			con3.setLayout(new GridLayout(18,2,1,1));
			con3.add(new Label("输入是第几个样本"));
			con3.add(jta1);
			con3.add(new Label(""));
			con3.add(new Label("A:"));
			final JTextField JAt=new JTextField("");
			final JTextField JAp=new JTextField("");
			con3.add(JAt);
			con3.add(JAp);
			con3.add(new Label("B:"));
			final JTextField JBt=new JTextField("");
			final JTextField JBp=new JTextField("");
			con3.add(JBt);
			con3.add(JBp);
			con3.add(new Label("C:"));
			final JTextField JCt=new JTextField("");
			final JTextField JCp=new JTextField("");
			con3.add(JCt);
			con3.add(JCp);
			con3.add(new Label("D:"));
			final JTextField JDt=new JTextField("");
			final JTextField JDp=new JTextField("");
			con3.add(JDt);
			con3.add(JDp);
			con3.add(new Label("M:"));
			final JTextField JMt=new JTextField("");
			final JTextField JMp=new JTextField("");
			con3.add(JMt);
			con3.add(JMp);
			con3.add(new Label("ABC:"));
			final JTextField JabcStock=new JTextField("");
			final JTextField JabcIndex=new JTextField("");
			con3.add(JabcStock);
			con3.add(JabcIndex);
			con3.add(new Label("BCD:"));
			final JTextField JbcdStock=new JTextField("");
			final JTextField JbcdIndex=new JTextField("");
			con3.add(JbcdStock);
			con3.add(JbcdIndex);
			con3.add(new Label("BCM:"));
			final JTextField JbcmStock=new JTextField("");
			final JTextField JbcmIndex=new JTextField("");
			con3.add(JbcmStock);
			con3.add(JbcmIndex);
			con3.add(new Label("A':"));
			final JTextField JAAt=new JTextField("");
			final JTextField JAAp=new JTextField("");
			con3.add(JAAt);
			con3.add(JAAp);
			con3.add(new Label("B':"));
			final JTextField JBBt=new JTextField("");
			final JTextField JBBp=new JTextField("");
			con3.add(JBBt);
			con3.add(JBBp);
			con3.add(new Label("C':"));
			final JTextField JCCt=new JTextField("");
			final JTextField JCCp=new JTextField("");
			con3.add(JCCt);
			con3.add(JCCp);
			con3.add(new Label("D':"));
			final JTextField JDDt=new JTextField("");
			final JTextField JDDp=new JTextField("");
			con3.add(JDDt);
			con3.add(JDDp);
			
			con3.add(new Label("A'B'C':"));
			final JTextField JSabcStock=new JTextField("");
			final JTextField JSabcIndex=new JTextField("");
			con3.add(JSabcStock);
			con3.add(JSabcIndex);
			con3.add(new Label("B'C'D':"));
			final JTextField JSbcdStock=new JTextField("");
			final JTextField JSbcdIndex=new JTextField("");
			con3.add(JSbcdStock);
			con3.add(JSbcdIndex);
			
			con2.add(con3,BorderLayout.NORTH);
			con2.add(jb1,BorderLayout.WEST);
			con2.add(jb,BorderLayout.CENTER);
			con2.add(jb2,BorderLayout.EAST);
			con.add(cp,BorderLayout.CENTER);
			con.add(con2,BorderLayout.EAST);
			JAt.setText(At[num]);
			JBt.setText(Bt[num]);
			JCt.setText(Ct[num]);
			JDt.setText(Dt[num]);
			JMt.setText(Mt[num]);
			JAp.setText(Ap[num]);
			JBp.setText(Bp[num]);
			JCp.setText(Cp[num]);
			JDp.setText(Dp[num]);
			JMp.setText(Mp[num]);
			JAAt.setText(AAt[num]);
			JBBt.setText(BBt[num]);
			JCCt.setText(CCt[num]);
			JDDt.setText(DDt[num]);
			JAAp.setText(AAp[num]);
			JBBp.setText(BBp[num]);
			JCCp.setText(CCp[num]);
			JDDp.setText(DDp[num]);
			String s1[]=new String[3];
			s1=abcStock[num].split(",,,");
			JabcStock.setText(s1[0]);
			JabcIndex.setText(s1[1]);
			s1=bcdStock[num].split(",,,");
			JbcdStock.setText(s1[0]);
			JbcdIndex.setText(s1[1]);
			s1=bcmStock[num].split(",,,");
			JbcmStock.setText(s1[0]);
			JbcmIndex.setText(s1[1]);
			s1=SabcStock[num].split(",,,");
			if(s1.length!=1)
			{
				JSabcStock.setText(s1[0]);
				JSabcIndex.setText(s1[1]);
				s1=SbcdStock[num].split(",,,");
				JSbcdStock.setText(s1[0]);
				JSbcdIndex.setText(s1[1]);
				
			}
			
			
			
			jf.setSize(800,500);
			jf.pack();
			jf.setVisible(true);
			
		}
	});
		

	jb1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
		//	jta1.setText("good!");
			jf.setVisible(false);
			  int num=Integer.valueOf(jta1.getText());
			  int numm=0;
			  double highValue = Double.MIN_VALUE;//设置K线数据当中的最大值
			  double minValue = Double.MAX_VALUE;//设置K线数据当中的最小值
			  double high2Value = Double.MIN_VALUE;//设置成交量的最大值
			  double min2Value = Double.MAX_VALUE;//设置成交量的最低值
			  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			  for(int q=0;q<sname.length;q++)
			  {
				  if(sname[q].equals(stockname[num]))
				  {
					numm=q;
				  }
			  }
			
			//保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
			   final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();
			   seriesCollection.addSeries(series[numm]);
			//保留成交量数据的集合
			TimeSeriesCollection timeSeriesCollection=new TimeSeriesCollection();
			timeSeriesCollection.addSeries(series2[numm]);
			System.out.println("num"+num);
			System.out.println("stTime"+sts[num]);
			System.out.println("etTime"+ets[num]);
			//获取K线数据的最高值和最低值
			int seriesCount = seriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
			for (int ii = 0; ii < seriesCount; ii++) 
			{
			     int itemCount = seriesCollection.getItemCount(ii);//每一个序列有多少个数据项
			     System.out.println("itemCount"+itemCount);
			     for (int j = 0; j < itemCount; j++)
			     {
				       if (highValue < seriesCollection.getHighValue(ii, j)) 
				       {//highValue是取第i个序列中的第j个数据项的最大值
				         highValue = seriesCollection.getHighValue(ii, j);
				       }
				       if (minValue > seriesCollection.getLowValue(ii, j)) 
				       {//minVaule是取第i个序列中的第j个数据项的最小值
				        minValue = seriesCollection.getLowValue(ii, j);
				       }
			     }

			}
			//获取成交量最高值和最低值
			int seriesCount2 = timeSeriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
			for (int ii = 0; ii < seriesCount2; ii++)
			{
			     int itemCount = timeSeriesCollection.getItemCount(ii);//每一个序列有多少个数据项
			     for (int j = 0; j < itemCount; j++) 
			     {
				       if (high2Value < timeSeriesCollection.getYValue(ii,j)) 
				       {//取第i个序列中的第j个数据项的值
				        high2Value = timeSeriesCollection.getYValue(ii,j);
				       }
				       if (min2Value > timeSeriesCollection.getYValue(ii, j)) 
				       {//取第i个序列中的第j个数据项的值
				        min2Value = timeSeriesCollection.getYValue(ii, j);
				       }
			     }

			}
			final CandlestickRenderer candlestickRender=new CandlestickRenderer();//设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
			candlestickRender.setUseOutlinePaint(true); //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
			candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//设置如何对K线图的宽度进行设定
			candlestickRender.setAutoWidthGap(0.001);//设置各个K线图之间的间隔
			candlestickRender.setUpPaint(Color.RED);//设置股票上涨的K线图颜色
			candlestickRender.setDownPaint(Color.GREEN);//设置股票下跌的K线图颜色
			//设置x轴，也就是时间轴
			DateAxis x1Axis=new DateAxis();
			//设置不采用自动设置时间范围
			x1Axis.setAutoRange(false);
			try{
				//设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
			   //要修改时间范围	
				    cal.setTime(sdf.parse(inputet));
			        cal.add(Calendar.DATE, -10);
			        inputet=sdf.format(cal.getTime());
			        cal.setTime(sdf.parse(inputet));
			        cal.add(Calendar.DATE, -10);
			        inputst=sdf.format(cal.getTime());
			 x1Axis.setRange(dateFormat.parse(inputst),dateFormat.parse(inputet));
			}catch(Exception e){
			 e.printStackTrace();
			}
			x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
			x1Axis.setAutoTickUnitSelection(false);//设置不采用自动选择刻度值
			x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
			x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
			x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));//设置时间刻度的间隔，一般以周为单位
			x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//设置显示时间的格式
			NumberAxis y1Axis=new NumberAxis();//设定y轴，就是数字轴
			y1Axis.setAutoRange(false);//不使用自动设定范围
			y1Axis.setRange(minValue*0.9, highValue*1.1);//设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
			y1Axis.setTickUnit(new NumberTickUnit((highValue*1.1-minValue*0.9)/10));//设置刻度显示的密度
			XYPlot plot1=new XYPlot(seriesCollection,x1Axis,y1Axis,candlestickRender);//设置画图区域对象

			XYBarRenderer xyBarRender=new XYBarRenderer(){
			private static final long serialVersionUID = 1L;//为了避免出现警告消息，特设定此值
			public Paint getItemPaint(int ii, int j){//匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
			  if(seriesCollection.getCloseValue(ii,j)>seriesCollection.getOpenValue(ii,j)){//收盘价高于开盘价，股票上涨，选用股票上涨的颜色
			   return candlestickRender.getUpPaint();
			  }else{
			   return candlestickRender.getDownPaint();
			  }
			}};
			xyBarRender.setMargin(0.1);//设置柱形图之间的间隔
			NumberAxis y2Axis=new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的y轴设置
			y2Axis.setAutoRange(false);
			y2Axis.setRange(min2Value*0.9, high2Value*1.1);
			y2Axis.setTickUnit(new NumberTickUnit((high2Value*1.1-min2Value*0.9)/4));
			XYPlot plot2=new XYPlot(timeSeriesCollection,null,y2Axis,xyBarRender);//建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
			CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//建立一个恰当的联合图形区域对象，以x轴为共享轴
			combineddomainxyplot.add(plot1, 2);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
			combineddomainxyplot.add(plot2, 1);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
			combineddomainxyplot.setGap(10);//设置两个图形区域对象之间的间隔空间
			String stocknames=stockname[num];
			JFreeChart chart = new JFreeChart(stocknames, JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
			ChartPanel cp=new ChartPanel(chart); 
			
			
			Container con=jf.getContentPane();
			jf.getContentPane().removeAll();
			jf.setLayout(new BorderLayout());
			
			Container con2=new Container();
			Container con3=new Container();
			con2.setLayout(new BorderLayout());
			con3.setLayout(new GridLayout(17,2,1,1));
			con3.add(new Label("A:"));
			final JTextField JAt=new JTextField("");
			final JTextField JAp=new JTextField("");
			con3.add(JAt);
			con3.add(JAp);
			con3.add(new Label("B:"));
			final JTextField JBt=new JTextField("");
			final JTextField JBp=new JTextField("");
			con3.add(JBt);
			con3.add(JBp);
			con3.add(new Label("C:"));
			final JTextField JCt=new JTextField("");
			final JTextField JCp=new JTextField("");
			con3.add(JCt);
			con3.add(JCp);
			con3.add(new Label("D:"));
			final JTextField JDt=new JTextField("");
			final JTextField JDp=new JTextField("");
			con3.add(JDt);
			con3.add(JDp);
			con3.add(new Label("M:"));
			final JTextField JMt=new JTextField("");
			final JTextField JMp=new JTextField("");
			con3.add(JMt);
			con3.add(JMp);
			
			con3.add(new Label("ABC:"));
			final JTextField JabcStock=new JTextField("");
			final JTextField JabcIndex=new JTextField("");
			con3.add(JabcStock);
			con3.add(JabcIndex);
			con3.add(new Label("BCD:"));
			final JTextField JbcdStock=new JTextField("");
			final JTextField JbcdIndex=new JTextField("");
			con3.add(JbcdStock);
			con3.add(JbcdIndex);
			con3.add(new Label("BCM:"));
			final JTextField JbcmStock=new JTextField("");
			final JTextField JbcmIndex=new JTextField("");
			con3.add(JbcmStock);
			con3.add(JbcmIndex);
			
			con3.add(new Label("A':"));
			final JTextField JAAt=new JTextField("");
			final JTextField JAAp=new JTextField("");
			con3.add(JAAt);
			con3.add(JAAp);
			con3.add(new Label("B':"));
			final JTextField JBBt=new JTextField("");
			final JTextField JBBp=new JTextField("");
			con3.add(JBBt);
			con3.add(JBBp);
			con3.add(new Label("C':"));
			final JTextField JCCt=new JTextField("");
			final JTextField JCCp=new JTextField("");
			con3.add(JCCt);
			con3.add(JCCp);
			con3.add(new Label("D':"));
			final JTextField JDDt=new JTextField("");
			final JTextField JDDp=new JTextField("");
			con3.add(JDDt);
			con3.add(JDDp);
			
			con3.add(new Label("A'B'C':"));
			final JTextField JSabcStock=new JTextField("");
			final JTextField JSabcIndex=new JTextField("");
			con3.add(JSabcStock);
			con3.add(JSabcIndex);
			con3.add(new Label("B'C'D':"));
			final JTextField JSbcdStock=new JTextField("");
			final JTextField JSbcdIndex=new JTextField("");
			con3.add(JSbcdStock);
			con3.add(JSbcdIndex);
			
			con2.add(con3,BorderLayout.NORTH);
			con2.add(jb1,BorderLayout.WEST);
			con2.add(jb,BorderLayout.CENTER);
			con2.add(jb2,BorderLayout.EAST);
			con.add(cp,BorderLayout.CENTER);
			con.add(con2,BorderLayout.EAST);
			JAt.setText(At[num]);
			JBt.setText(Bt[num]);
			JCt.setText(Ct[num]);
			JDt.setText(Dt[num]);
			JMt.setText(Mt[num]);
			JAp.setText(Ap[num]);
			JBp.setText(Bp[num]);
			JCp.setText(Cp[num]);
			JDp.setText(Dp[num]);
			JMp.setText(Mp[num]);
			JAAt.setText(AAt[num]);
			JBBt.setText(BBt[num]);
			JCCt.setText(CCt[num]);
			JDDt.setText(DDt[num]);
			JAAp.setText(AAp[num]);
			JBBp.setText(BBp[num]);
			JCCp.setText(CCp[num]);
			JDDp.setText(DDp[num]);
			String s1[]=new String[3];
			s1=abcStock[num].split(",,,");
			JabcStock.setText(s1[0]);
			JabcIndex.setText(s1[1]);
			s1=bcdStock[num].split(",,,");
			JbcdStock.setText(s1[0]);
			JbcdIndex.setText(s1[1]);
			s1=bcmStock[num].split(",,,");
			JbcmStock.setText(s1[0]);
			JbcmIndex.setText(s1[1]);
			s1=SabcStock[num].split(",,,");
			if(s1.length!=1)
			{
				JSabcStock.setText(s1[0]);
				JSabcIndex.setText(s1[1]);
				s1=SbcdStock[num].split(",,,");
				JSbcdStock.setText(s1[0]);
				JSbcdIndex.setText(s1[1]);
				
			}
			
			jf.setSize(800,500);
			jf.pack();
			jf.setVisible(true);
		}
	});

	jb2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
		//	jta1.setText("good!");
			jf.setVisible(false);
			  int num=Integer.valueOf(jta1.getText());
			  int numm=0;
			  double highValue = Double.MIN_VALUE;//设置K线数据当中的最大值
			  double minValue = Double.MAX_VALUE;//设置K线数据当中的最小值
			  double high2Value = Double.MIN_VALUE;//设置成交量的最大值
			  double min2Value = Double.MAX_VALUE;//设置成交量的最低值
			  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			  for(int q=0;q<sname.length;q++)
			  {
				  if(sname[q].equals(stockname[num]))
				  {
					numm=q;
				  }
			  }
			
			//保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
			   final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();
			   seriesCollection.addSeries(series[numm]);
			//保留成交量数据的集合
			TimeSeriesCollection timeSeriesCollection=new TimeSeriesCollection();
			timeSeriesCollection.addSeries(series2[numm]);
			System.out.println("num"+num);
			System.out.println("stTime"+sts[num]);
			System.out.println("etTime"+ets[num]);
			//获取K线数据的最高值和最低值
			int seriesCount = seriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
			for (int ii = 0; ii < seriesCount; ii++) 
			{
			     int itemCount = seriesCollection.getItemCount(ii);//每一个序列有多少个数据项
			     System.out.println("itemCount"+itemCount);
			     for (int j = 0; j < itemCount; j++)
			     {
				       if (highValue < seriesCollection.getHighValue(ii, j)) 
				       {//highValue是取第i个序列中的第j个数据项的最大值
				         highValue = seriesCollection.getHighValue(ii, j);
				       }
				       if (minValue > seriesCollection.getLowValue(ii, j)) 
				       {//minVaule是取第i个序列中的第j个数据项的最小值
				        minValue = seriesCollection.getLowValue(ii, j);
				       }
			     }

			}
			//获取成交量最高值和最低值
			int seriesCount2 = timeSeriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
			for (int ii = 0; ii < seriesCount2; ii++)
			{
			     int itemCount = timeSeriesCollection.getItemCount(ii);//每一个序列有多少个数据项
			     for (int j = 0; j < itemCount; j++) 
			     {
				       if (high2Value < timeSeriesCollection.getYValue(ii,j)) 
				       {//取第i个序列中的第j个数据项的值
				        high2Value = timeSeriesCollection.getYValue(ii,j);
				       }
				       if (min2Value > timeSeriesCollection.getYValue(ii, j)) 
				       {//取第i个序列中的第j个数据项的值
				        min2Value = timeSeriesCollection.getYValue(ii, j);
				       }
			     }

			}
			final CandlestickRenderer candlestickRender=new CandlestickRenderer();//设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
			candlestickRender.setUseOutlinePaint(true); //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
			candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//设置如何对K线图的宽度进行设定
			candlestickRender.setAutoWidthGap(0.001);//设置各个K线图之间的间隔
			candlestickRender.setUpPaint(Color.RED);//设置股票上涨的K线图颜色
			candlestickRender.setDownPaint(Color.GREEN);//设置股票下跌的K线图颜色
			//设置x轴，也就是时间轴
			DateAxis x1Axis=new DateAxis();
			//设置不采用自动设置时间范围
			x1Axis.setAutoRange(false);
			try{
				//设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
			   //要修改时间范围
				 cal.setTime(sdf.parse(inputst));
			        cal.add(Calendar.DATE, 10);
			        inputst=sdf.format(cal.getTime());
			        cal.setTime(sdf.parse(inputst));
			        cal.add(Calendar.DATE, 10);
			        inputet=sdf.format(cal.getTime());
			 x1Axis.setRange(dateFormat.parse(inputst),dateFormat.parse(inputet));
			}catch(Exception e){
			 e.printStackTrace();
			}
			x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
			x1Axis.setAutoTickUnitSelection(false);//设置不采用自动选择刻度值
			x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
			x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
			x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));//设置时间刻度的间隔，一般以周为单位
			x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//设置显示时间的格式
			NumberAxis y1Axis=new NumberAxis();//设定y轴，就是数字轴
			y1Axis.setAutoRange(false);//不使用自动设定范围
			y1Axis.setRange(minValue*0.9, highValue*1.1);//设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
			y1Axis.setTickUnit(new NumberTickUnit((highValue*1.1-minValue*0.9)/10));//设置刻度显示的密度
			XYPlot plot1=new XYPlot(seriesCollection,x1Axis,y1Axis,candlestickRender);//设置画图区域对象

			XYBarRenderer xyBarRender=new XYBarRenderer(){
			private static final long serialVersionUID = 1L;//为了避免出现警告消息，特设定此值
			public Paint getItemPaint(int ii, int j){//匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
			  if(seriesCollection.getCloseValue(ii,j)>seriesCollection.getOpenValue(ii,j)){//收盘价高于开盘价，股票上涨，选用股票上涨的颜色
			   return candlestickRender.getUpPaint();
			  }else{
			   return candlestickRender.getDownPaint();
			  }
			}};
			xyBarRender.setMargin(0.1);//设置柱形图之间的间隔
			NumberAxis y2Axis=new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的y轴设置
			y2Axis.setAutoRange(false);
			y2Axis.setRange(min2Value*0.9, high2Value*1.1);
			y2Axis.setTickUnit(new NumberTickUnit((high2Value*1.1-min2Value*0.9)/4));
			XYPlot plot2=new XYPlot(timeSeriesCollection,null,y2Axis,xyBarRender);//建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
			CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//建立一个恰当的联合图形区域对象，以x轴为共享轴
			combineddomainxyplot.add(plot1, 2);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
			combineddomainxyplot.add(plot2, 1);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
			combineddomainxyplot.setGap(10);//设置两个图形区域对象之间的间隔空间
			String stocknames=stockname[num];
			JFreeChart chart = new JFreeChart(stocknames, JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
			ChartPanel cp=new ChartPanel(chart); 
			
			 //JFrame jf=new JFrame();
			 Container con=jf.getContentPane();
			 con.removeAll();
			 jf.setLayout(new BorderLayout());
			 Container con2=new Container();
			 Container con3=new Container();
			con2.setLayout(new BorderLayout());
			con3.setLayout(new GridLayout(18,2,1,1));
			con3.add(new Label("输入是第几个样本"));
			con3.add(jta1);
			con3.add(new Label(""));

			con3.add(new Label("A:"));
			
			con3.add(JAt);
			con3.add(JAp);
			con3.add(new Label("B:"));
		
			con3.add(JBt);
			con3.add(JBp);
			con3.add(new Label("C:"));
			
			con3.add(JCt);
			con3.add(JCp);
			con3.add(new Label("D:"));
			
			con3.add(JDt);
			con3.add(JDp);
			con3.add(new Label("M:"));
			
			con3.add(JMt);
			con3.add(JMp);
			
			con3.add(new Label("ABC:"));
			
			con3.add(JabcStock);
			con3.add(JabcIndex);
			con3.add(new Label("BCD:"));
		
			con3.add(JbcdStock);
			con3.add(JbcdIndex);
			con3.add(new Label("BCM:"));
		
			con3.add(JbcmStock);
			con3.add(JbcmIndex);
			
			con3.add(new Label("A':"));
			
			con3.add(JAAt);
			con3.add(JAAp);
			con3.add(new Label("B':"));
			
			con3.add(JBBt);
			con3.add(JBBp);
			con3.add(new Label("C':"));
		
			con3.add(JCCt);
			con3.add(JCCp);
			con3.add(new Label("D':"));
		
			con3.add(JDDt);
			con3.add(JDDp);
			
			con3.add(new Label("A'B'C':"));
		
			con3.add(JSabcStock);
			con3.add(JSabcIndex);
			con3.add(new Label("B'C'D':"));
		
			con3.add(JSbcdStock);
			con3.add(JSbcdIndex);
			
			con2.add(con3,BorderLayout.NORTH);
			con2.add(jb1,BorderLayout.WEST);
			con2.add(jb,BorderLayout.CENTER);
			con2.add(jb2,BorderLayout.EAST);
			con.add(cp,BorderLayout.CENTER);
			con.add(con2,BorderLayout.EAST);
			JAt.setText(At[num]);
			JBt.setText(Bt[num]);
			JCt.setText(Ct[num]);
			JDt.setText(Dt[num]);
			JMt.setText(Mt[num]);
			JAp.setText(Ap[num]);
			JBp.setText(Bp[num]);
			JCp.setText(Cp[num]);
			JDp.setText(Dp[num]);
			JMp.setText(Mp[num]);
			JAAt.setText(AAt[num]);
			JBBt.setText(BBt[num]);
			JCCt.setText(CCt[num]);
			JDDt.setText(DDt[num]);
			JAAp.setText(AAp[num]);
			JBBp.setText(BBp[num]);
			JCCp.setText(CCp[num]);
			JDDp.setText(DDp[num]);
			String s1[]=new String[3];
			s1=abcStock[num].split(",,,");
			JabcStock.setText(s1[0]);
			JabcIndex.setText(s1[1]);
			s1=bcdStock[num].split(",,,");
			JbcdStock.setText(s1[0]);
			JbcdIndex.setText(s1[1]);
			s1=bcmStock[num].split(",,,");
			JbcmStock.setText(s1[0]);
			JbcmIndex.setText(s1[1]);
			s1=SabcStock[num].split(",,,");
			if(s1.length!=1)
			{
				JSabcStock.setText(s1[0]);
				JSabcIndex.setText(s1[1]);
				s1=SbcdStock[num].split(",,,");
				JSbcdStock.setText(s1[0]);
				JSbcdIndex.setText(s1[1]);
				
			}
			
			
			
			jf.setSize(800,500);
			jf.pack();
			jf.setVisible(true);
			
		}
	});
		


	}	
		
	}
		
	
