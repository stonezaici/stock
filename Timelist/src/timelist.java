import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class timelist {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		FileReader filereader = null;
		int j = 0;
		String line=null;
		String[] lineafter=null;
		
		
		String[][] bigist=new String[3000][25];//用来存储输入数据
		
		int k=0;
		int i=0;
		ArrayList<String> name=new ArrayList<String>();
		int[] jishu=new int[100];
		float[] pingjunyingli=new float[100];
		
		try {
			filereader = new FileReader("D:\\Internal\\6-24\\程序测试数据\\aaa.txt");
		} catch (FileNotFoundException ee) {
				// TODO Auto-generated catch block
			ee.printStackTrace();
		}
		BufferedReader reader=new BufferedReader(filereader);
		try{
			while((line=reader.readLine())!=null){
				
				if(line.contains("股票代码")) continue;
				if(line=="") continue;
				if(line.contains("			")) continue;
				
				lineafter=line.split("	");
				bigist[i][0]=lineafter[0];//代码
				bigist[i][1]=lineafter[8];//买入时间
				bigist[i][2]=lineafter[14];//卖出时间
				bigist[i][3]=lineafter[16];//盈亏
				if(!name.contains(bigist[i][0])){name.add(bigist[i][0]);}//如果name arraylist中不包含代码，则将代码加入到name arraylist中
				for(j=0;j<name.size();j++){
					if(bigist[i][0].compareTo(name.get(j))==0){
						jishu[j]++;//记录每只股票交易的次数
						pingjunyingli[j]=pingjunyingli[j]+Float.valueOf(bigist[i][3]);//记录总共的交易额
					}
					
				}
				i++;
			}
		
		
		}catch (Exception ee) {
			// TODO Auto-generated catch block
		ee.printStackTrace();
		}
		
		for(i=0;i<name.size();i++){
			pingjunyingli[i]=pingjunyingli[i]/jishu[i];//计算平均盈利  交易额/交易次数
			System.out.println(name.get(i));
			System.out.println(pingjunyingli[i]);
		}
		
		//按买入时间升序排列bigist数组  这样就是在时间序列上将所有股票的每笔交易排列开来
		for(i=0;bigist[i][0]!=null;i++){
			for(j=i+1;bigist[j][0]!=null;j++){
				String[] temp=null;
				if(bigist[j][1].compareTo(bigist[i][1])<0){
					temp=bigist[j];
					bigist[j]=bigist[i];
					bigist[i]=temp;
					
				}
				
			}
			
		}
		int x1=0;
		int x2=0;
		int x3=0;
		
		for(x1=0;x1<name.size();x1++){
			for(x2=x1+1;x2<name.size();x2++){
				for(x3=x2+1;x3<name.size();x3++){
					String[][][] zhanghu=new String[500][500][5];//账户数组   每个属性的意义？？？？？？？？？？？？
					String[][] list=new String[3000][25];//  每个属性的意义？？？？？？？？？？？？？？？？？？？？
					j=0;
					for(i=0;bigist[i][0]!=null;i++){
						if(bigist[i][0].contains(name.get(x1))||bigist[i][0].contains(name.get(x2))||bigist[i][0].contains(name.get(x3))){
							list[j]=bigist[i];//如果bigist[]数组中至少包含参加运算的一个name中的股票代码，将这支股票的信息（买入卖出时间，盈利什么的）放入list中
							j++;//即得到参加运算的股票的信息
							//如果一个都没有匹配上呢？？？？？？？？？？？？？？？？？？？？？？？、
						}
					}
					
					
					
					
					zhanghu[0][0]=list[0];
					
					//i是第几个股票
					for(i=0;list[i][0]!=null;i++){//dan
					//j是第几个账户
						for(j=0;j<500;j++){//zhanghu
							
							//找账户里面的最后一笔交易是第几笔
							for(k=0;zhanghu[j][k][0]!=null;k++){///遍历账户数组，看看一共有几笔交易   用k来标记
								//zhanghu[j][k][0]: 第j个账户的第k笔交易
							}
							
							if(k==0) {zhanghu[j][k]=list[i];break;}//这是什么意思？？？？？？？？？？？？？？？
							
							if(zhanghu[j][k-1][2].compareTo(list[i][1])>=0){//这是什么属性的比较？？？？？？？
								continue;
							}
							else {
								zhanghu[j][k]=list[i];
								break;
							}
						}
						
					}
					
					//计算每笔交易的持仓时间
					
				String 	[][] holdTime=new String[500][500];
				String startTime=null;
				String endTime=null;
				String timesplits;
				double diff;
				double holdtimeNow;
				String s0,s1,s2;
				double sampleHold=0;
				double holdtotal=0;
				int ncount=0;
					for(i=0;zhanghu[i][0][0]!=null;i++){
									
						for(j=0;zhanghu[i][j][0]!=null;j++){
						
							
							
							lineafter=zhanghu[i][j][1].split("-");
							startTime=lineafter[0]+lineafter[1]+lineafter[2];//
							lineafter=zhanghu[i][j][2].split("-");
							endTime=lineafter[0]+lineafter[1]+lineafter[2];//
							
							String stchange=startTime.substring(0,4)+"-"+startTime.substring(4,6)+"-"+startTime.substring(6,8);
							String etchange=endTime.substring(0,4)+"-"+endTime.substring(4,6)+"-"+endTime.substring(6,8);
							Date st = new SimpleDateFormat("yyyy-MM-dd").parse(stchange); 
							Date et = new SimpleDateFormat("yyyy-MM-dd").parse(etchange);
						
							//时间的处理按如下框架编写。
					      //计算起始日期和结束日期相差的天数
							diff=(et.getTime()-st.getTime())/(24*60*60*1000);
							//System.out.println("相差"+diff+"天");
						 //判断结束时点与起始时点
							int IntStTime=Integer.valueOf(startTime.substring(9,11)+startTime.substring(12,14));//时点转换成数字，如14:00->1400
							int IntEtTime=Integer.valueOf(endTime.substring(9,11)+endTime.substring(12,14));
							double doubleStTime=((double)(IntStTime%100)/60)+(int)IntStTime/100;
							double doubleEtTime=((double)(IntEtTime%100)/60)+(int)IntEtTime/100;
						//结束时点>=起始时点,且若两者都在上午或都在下午的那两小时交易时间内，则diff+(结束时点-起始时点对应的小时数)/4,否则：若前者在上午，后者在下午，diff+(结束时点-起始时点对应的小时数-1.5个小时)/4
						if(IntEtTime>IntStTime)
						{
							if((IntEtTime<1300 && IntStTime<1300) || (IntEtTime>=1300 && IntStTime>=1300) )
							{
								holdtimeNow=diff+(doubleEtTime-doubleStTime)/4;
							}
							else
							{
								holdtimeNow=diff+(doubleEtTime-doubleStTime-1.5)/4;
							}
							
						}
						else if(IntEtTime==IntStTime)
						{
							holdtimeNow=diff;
						}
						else //结束时点<起始时点,(结束时点-起始时点对应的小时数)/4,就计算diff-1+(起始时点距离3点的且为交易时间段的距离+结束时间距离9:30的交易时间时间段的距离)/4
						{
							double temp1,temp2;
							if(IntStTime<1300)
								temp1=(13.5-doubleStTime);//15:00-doubleStTime-1:30
							else
								temp1=(15-doubleStTime);
							if(IntEtTime<1300)
								temp2=(doubleEtTime-9.5);
							else
								temp2=(doubleEtTime-11);//doubleEtTime-9:30-1:30

							holdtimeNow=diff-1+(temp1+temp2)/4;
						}
						
						zhanghu[i][j][4]=holdtimeNow+"";
							
						}
					}
						
						
					String fileout =null;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
					String nowdate=dateFormat.format(new java.util.Date());
						fileout ="D:\\Internal\\"+"timelist"+nowdate+".txt";

					try{
						FileOutputStream fos = new FileOutputStream(fileout,true);
						OutputStreamWriter osw = new OutputStreamWriter(fos);
						BufferedWriter write = new BufferedWriter(osw);
						
						
						for(i=0;i<1;i++){
							int ii=i+1;
							write.write("第"+ii+"个账户");
							write.newLine();
							float shenglv=0;
							float aver=0;
							float fuli=1;
							double hold=0;
							double holdavg;
						
							
							for(j=0;zhanghu[i][j][0]!=null;j++){
							
								write.write(zhanghu[i][j][0]);
								write.write("	");
								write.write(zhanghu[i][j][1]);
								write.write("	");
								write.write(zhanghu[i][j][2]);
								write.write("	");
								write.write(zhanghu[i][j][3]);
								write.write("	");
								write.write(zhanghu[i][j][4]);
								write.write("	");
								write.newLine();
								aver=Float.valueOf(zhanghu[i][j][3])+aver;
								fuli=fuli*(Float.valueOf(zhanghu[i][j][3])+1);
								if(Float.valueOf(zhanghu[i][j][3])>0) shenglv++;
								hold+=Double.valueOf(zhanghu[i][j][4]);
								ncount++;
								
							}
							aver=aver/j;
							shenglv=shenglv/j;
							holdavg=hold/j;
							holdtotal+=hold;
							
							write.write("平均收益");
							write.write("	");
							write.write(Float.toString(aver));
							write.write("	");
							write.write("胜率");
							write.write("	");
							write.write(Float.toString(shenglv));
							write.write("	");
							write.write("复利");
							write.write("	");
							write.write(Float.toString(fuli));
							write.write("	");
							write.write("平均持股天数");
							write.write("	");
							write.write(Double.toString(holdavg));
							write.write("组合优化效率");
							write.write("	");
							write.write(Float.toString(aver-(pingjunyingli[x1]+pingjunyingli[x2]+pingjunyingli[x3])/3));
							
							write.newLine();
							write.newLine();
						}
						sampleHold=holdtotal/ncount;
						System.out.println("所有交易平均每笔交易持仓时间:"+sampleHold);
					
						write.close();
					}catch(Exception ee){
						ee.printStackTrace();
					}
				}
			}
		}
		
	}

	}
