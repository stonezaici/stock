package com.luyi;

//���ϻ�ͼ���ܣ���ͼ�����ƣ��޸ĸߵ�Ϊ5��K�ߣ��͵�Ϊ5��K��
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
import java.awt.Color;//��ɫϵͳ
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.text.SimpleDateFormat;//ʱ���ʽ
import java.awt.Paint;//����ϵͳ
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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



public class chuangyeban extends JFrame {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	
	static  String inputst="2010-01-04";//�ֶ��������ݵ���ʼ����
	static String inputet="2013-06-20";//�ֶ��������ݵĽ�������
	public static void main(String[] args) throws ParseException {
		
		//�����ǻ�ͼ�Ķ���
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		  final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        final Calendar cal = Calendar.getInstance();
		   
		     //��һ��series���鱣�������з�����̬�Ĺ�Ʊ����Ϣ.�߿������������У���ƱK��ͼ���ĸ����ݣ������ǿ��̣���ߣ���ͣ�����
		     final OHLCSeries series[]=new  OHLCSeries[2000];
		     int seriesNCount=0;
		     final TimeSeries series2[]=new TimeSeries[2000];//��Ӧʱ��ɽ�������
		     for(int ii=0;ii<2000;ii++)//��ʼ������
		     {
		    	 series[ii]=new  OHLCSeries("");
		    	 series2[ii]=new TimeSeries("");
		    	 
		     }
		     
		     final String stockname[]=new String[1000];
		     final String sts[]=new String[1000];//����ʲô---------start_times
		     final String ets[]=new String[1000];//����ʲô---------end_times
		     final String At[]=new String[1000];
		     final String Bt[]=new String[1000];
		     final String Ct[]=new String[1000];
		     final String Dt[]=new String[1000];
		     final  String Mt[]=new String[1000];
		     final String Ap[]=new String[1000];
		     final String Bp[]=new String[1000];
		     final String Cp[]=new String[1000];
		     final String Dp[]=new String[1000];
		     final String Mp[]=new String[1000];
		     final String AAt[]=new String[1000];
		     final String BBt[]=new String[1000];
		     final  String CCt[]=new String[1000];
		     final  String DDt[]=new String[1000];
		     final  String AAp[]=new String[1000];
		     final  String BBp[]=new String[1000];
		     final String CCp[]=new String[1000];
		     final String DDp[]=new String[1000];
		     final String abcStock[]=new String[1000];
		     final String abcIndex[]=new String[1000];
		     final String bcdStock[]=new String[1000];
		     final String bcdIndex[]=new String[1000];
		     final String bcmStock[]=new String[1000];
		     final String bcmIndex[]=new String[1000];
		     final String SabcStock[]=new String[1000];
		     final  String SabcIndex[]=new String[1000];
		     final String SbcdStock[]=new String[1000];
		     final String SbcdIndex[]=new String[1000];
		   
		 //�����ǲ��ԵĶ���    

		
		
		Date inputST=null;//�м�������
		Date inputET=null;
	
		String line=null;
		String[] lineaftersplit=null;
		String[][] price=new String[1000][8];//��Ʊ�۸���Ϣ
		String[][] priceshangzheng=new String[1000][8];//����300ָ��
		ArrayList<Float> hight=new ArrayList<Float>();//���ҵ��ߵ㣬Ȼ��Ѹߵ�浽����
		ArrayList<Float> lowt=new ArrayList<Float>();//�ҵ��͵㣬�ѵ͵������
		ArrayList<String[]> buy=new ArrayList<String[]>();//��������̬��������
		ArrayList<String[]> sell=new ArrayList<String[]>();//��������̬��������
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
		float zm=0;//Z����abcdm����abcdm���Ӧ��ָ��
		float bmark=0;//
		float sellmark=0;
		File namefile=new File("D:\\Program Files (x86)\\T0002\\export\\") ;
		final String[] sname=namefile.list();//��Ʊ���ļ���   ���д�ú�
		int i=0;
		int j=0;
		int k=0;
		int f=0;
		int h=0;//ѭ������
		double totalHolddays=0;
		double avgWin=0;
		double avgLoss=0;
		Date startTime=null;
		Date endTime=null;
		int totalholdtime=0;
		
		int zhisunCount=0;
		int zhiyingCount=0;
		int normalCount=0;//���������Ĵ���
		int totalCount=0;//�ܹ����ױ�����
		double zhisun=-0.1;
		double zhiying=0.3;
		double normalRet=0;//�������׵�ÿ������
		int winCount=0;//��¼����������win�ı���
		int lossCount=0;//��¼����������loss�ı���
		float fee=(float)(2.5/1000);//һ�ν��׵Ľ��׷���
		float abdiff=(float) 0.1;//ab֮��۸����
		int ac=100;//ac֮�����������100��K��
		
		File fileout =null;
	//	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	//�����Ǵ����������
		String nowdate=dateFormat.format(new java.util.Date());//���������
		fileout =new File("D:\\newwirthoutput\\"+"��ҵ�����ߡ�"+nowdate+".txt");
		if (!fileout.exists()) {             
			try {
				fileout.createNewFile();
				System.out.println(fileout + "�Ѵ�����");
			} catch (IOException e1) {
					// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("��������ļ�ʧ�ܣ�");
			}              
			
		
		}	
			try{
				FileOutputStream fos = new FileOutputStream(fileout,true);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				BufferedWriter write = new BufferedWriter(osw);
			
				write.write("��Ʊ����");//�����ͷ
				write.write("	");
			
				write.write("������̬��ʼ");
				write.write("	");
				write.write("A");
				write.write("	");
				write.write("Bʱ��");
				write.write("	");
				write.write("B");
				write.write("	");
				write.write("Cʱ��");
				write.write("	");
				write.write("C");
				write.write("	");
				write.write("Dʱ��");
				write.write("	");
				write.write("D");
				write.write("	");
				write.write("Mʱ��");
				write.write("	");
				write.write("M");
				write.write("	");
				write.write("ABC�Ƿ�");
				write.write("	");
				write.write("BCD�Ƿ�");
				write.write("	");
				write.write("BCM�Ƿ�");
				write.write("	");
				write.write("������̬��ʼ");
				write.write("	");
				write.write("A'");
				write.write("	");
				write.write("B'ʱ��");
				write.write("	");
				write.write("B'");
				write.write("	");
				write.write("C'ʱ��");
				write.write("	");
				write.write("C'");
				write.write("	");
				write.write("����ʱ��");
				write.write("	");
				write.write("D'");
				write.write("	");
				write.write("ABC'�Ƿ�");
				write.write("	");
				write.write("BCD'�Ƿ�");
				write.write("	");
				write.write("��������");
				write.write("	");
				write.write("ӯ��");
				write.write("	");
			
				
				write.close();
			}catch(Exception ee){
				ee.printStackTrace();
			}
			
			
		//���¶�����֤50ָ��������
		try{	

			FileReader filereader=new FileReader("D:\\Program Files (x86)\\T0002\\export\\SZ395004.TXT");//��֤50ָ�����ݵĴ洢�ļ�
			BufferedReader reader=new BufferedReader(filereader);
			
			//		if(sname[i].compareTo("sh600556")<0) continue;
			line=reader.readLine();
			line=reader.readLine();
			j=0;
			while((line=reader.readLine())!=null){
			
				lineaftersplit=line.split("	");
				if(lineaftersplit.length<2) break;
				if(lineaftersplit[1]==lineaftersplit[2]&&lineaftersplit[2]==lineaftersplit[3]&&lineaftersplit[3]==lineaftersplit[4]&&lineaftersplit[5]=="0") break;//����ʲô�������������
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

	//		ѭ����ȡÿһ����Ʊ�ļ�Ȼ�������������ָ֤����Ƚ�
		for(i=0;i<sname.length;i++)	{
			//��ȡÿһ����Ʊ�ļ��������� �� �� �� ���ݴ���series���� ���ɽ�������series2����
			try{	
				File n=new File(sname[i]);//���д�ĺ�
				sname[i]=n.getName();
			//	System.out.println(sname[i]);
				FileReader filereader=new FileReader("D:\\Program Files (x86)\\T0002\\export\\"+sname[i]);
				BufferedReader reader=new BufferedReader(filereader);
				sname[i]=sname[i].substring(0,8);//ȡ����Ʊ���룬��Ҫ������ļ���׺.TXT
				//		if(sname[i].compareTo("sh600556")<0) continue;
				line=reader.readLine();
				line=reader.readLine();
				j=0;
				while((line=reader.readLine())!=null){
					
					lineaftersplit=line.split("	");
					if(lineaftersplit.length<2) break;
					if(lineaftersplit[1]==lineaftersplit[2]&&lineaftersplit[2]==lineaftersplit[3]&&lineaftersplit[3]==lineaftersplit[4]&&lineaftersplit[5]=="0") break;
					lineaftersplit[0]=lineaftersplit[0].replace("/","");
					price[j][0]=Integer.toString(j);//���ڼ�¼���������Եڼ���k��
					price[j][1]=lineaftersplit[0];//riqi
					price[j][2]=lineaftersplit[1];//kaipan
					price[j][3]=lineaftersplit[2];//zuigao
					price[j][4]=lineaftersplit[3];//zuidi
					price[j][5]=lineaftersplit[4];//shoupan
					price[j][6]=lineaftersplit[5];//liang
					 //����Ӻ��������,��ƱK��ͼ���ĸ����ݣ������ǿ����ߣ��ͣ���
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
						 
					//	 System.out.println(""+new Day(day, month,year));
				     series[i].add(new Day(day, month,year), Double.valueOf(price[j][2]), Double.valueOf(price[j][3]), Double.valueOf(price[j][4]), Double.valueOf(price[j][5]));
				     series2[i].add(new Day(day, month,year), Double.valueOf(price[j][6]));
				     j++;
						
				}
				
				reader.close();
				filereader.close();
			}
				
			catch(Exception ee) {ee.printStackTrace();}
			//�ҵ��͵㣬�����͵����lowt����
			for(j=10;price[j+10][0]!=null;j++){//�͵�Ķ���Ϊ���̼۵��ڻ����ǰ��5��k�ߵ����̼�
					//		System.out.prjntln(j);
							if(Float.valueOf(price[j][5])<=Float.valueOf(price[j-1][5])
									&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-2][5])
											&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-3][5])&&
											Float.valueOf(price[j][5])<=Float.valueOf(price[j-4][5])
													&&Float.valueOf(price[j][5])<=Float.valueOf(price[j-5][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j-6][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j-7][5])&&
																	Float.valueOf(price[j][5])<=Float.valueOf(price[j-8][5])&&
																			Float.valueOf(price[j][5])<=Float.valueOf(price[j-9][5])&&
																					Float.valueOf(price[j][5])<=Float.valueOf(price[j-10][5])&&
													Float.valueOf(price[j][5])<=Float.valueOf(price[j+1][5])&&
													Float.valueOf(price[j][5])<=Float.valueOf(price[j+2][5])
															&&Float.valueOf(price[j][5])<=Float.valueOf(price[j+3][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j+4][5])&&
															Float.valueOf(price[j][5])<=Float.valueOf(price[j+5][5])&&
																	Float.valueOf(price[j][5])<=Float.valueOf(price[j+6][5])&&
																	Float.valueOf(price[j][5])<=Float.valueOf(price[j+7][5])&&
																			Float.valueOf(price[j][5])<=Float.valueOf(price[j+8][5])&&
																					Float.valueOf(price[j][5])<=Float.valueOf(price[j+9][5])&&
																							Float.valueOf(price[j][5])<=Float.valueOf(price[j+10][5])
															){
								lowt.add(Float.valueOf(price[j][0]));//lowt���ڴ�ŵ͵�λ�ã���k�߸�����ʾ
						//		System.out.prjntln(date[i]);
						//		System.out.println();
							}
			}
						
			for(j=1;j<lowt.size();j++){//���͵�֮�����k�߸���С��5ʱ����Ҫǰһ���͵㣬���ڷ�ֹ���ֵ���ʱ�����ڶ���͵�����
				if(lowt.get(j)-lowt.get(j-1)<=10){
					lowt.remove(j-1);
					j--;
				}
			}
				
				
			for(j=10;price[j+10][1]!=null;j++){//�ߵ�Ķ���Ϊ���ڻ����ǰ��10��k�ߵ���ߵ�/////////////**********************
					//		System.out.prjntln(j);
				//�ҵ��ߵ㣬�����ߵ����hight����
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
					hight.add(Float.valueOf(price[j][0]));//hight���ڴ�Ÿߵ�λ�ã���k�߸�����ʾ
						//		System.out.prjntln(date[i]);
						//		System.out.println();
				}
			}
						
			for(j=1;j<hight.size();j++){//���ߵ�֮�����k�߸���С��10ʱ����Ҫǰһ���ߵ㣬���ڷ�ֹ���ֵ����ǣ����ڶ���ߵ�����
				if(hight.get(j)-hight.get(j-1)<=10){//////////******************
					hight.remove(j-1);
					j--;
				}
			}
				
			for(j=0;j<hight.size()-1;j++){//��������̬
				//a c ���������ڵĸߵ�
				a=Float.valueOf(price[hight.get(j).intValue()][5]);//5�ǵ�ǰ��������k������Ӧ�����̼�
				at=Float.valueOf(price[hight.get(j).intValue()][0]);//0�ǵ�ǰ��������k������Ӧ�����
				c=Float.valueOf(price[hight.get(j+1).intValue()][5]);
				ct=Float.valueOf(price[hight.get(j+1).intValue()][0]);
				
				if(c<a) continue;//��̬������c�����̼۱����a�����̼۸�
				if(ct-at>ac) continue;//��̬������A��C֮��ľ���<=ac��
				
				//��A��C����͵�B
				b=Float.valueOf(price[hight.get(j).intValue()+1][5]);//��A������K�ߵ���һ��k�߿�ʼ����
				bt=Float.valueOf(price[hight.get(j).intValue()+1][0]);
				bmark=Float.valueOf(price[hight.get(j).intValue()+1][5]);
				float nowbt=bt;//��ʼ��nowbt
				for(k=(int)bt;k<ct;k++){
						
					if(Float.valueOf(price[k][5])<bmark){//�����̼۽��бȽϣ��ҳ���Сֵ
						b=Float.valueOf(price[k][5]);
						nowbt=k;
						bmark=Float.valueOf(price[k][5]);
					}
					else if(Float.valueOf(price[k][5])==bmark)//����ȳ��ֶ���͵�
					{
						//�����ֶ����͵�ʱ�����������<=5����ȡ����һ��Ϊ�͵�
						if(k-nowbt<=5)
						{
							b=Float.valueOf(price[k][5]);
							nowbt=k;
							bmark=Float.valueOf(price[k][5]);	
						}
					}
						
				}
				bt=nowbt;//�ҵ�b�����ڵ�k��
				//���������������ж�
				if(Float.valueOf(price[(int)at][5])<(1+abdiff)*Float.valueOf(price[(int)bt][5]))
				{
					continue;//������������A>1.2B��������Ļ��������������A
				}
				//��D��
				dt=ct+(bt-at);
				float exactBuy;//exactBuy����������
				float shouPrice;
				float kaiPrice;
				float lastFiveTrade;
				int buyMark=0;
				
				if(price[(int)dt][0] != null) 
					
				{
					//��M�� 
					mt=dt;//��ʼ��m��
					exactBuy=mt;
					while(buyMark==0 && price[(int)exactBuy][0] != null)
					{
						shouPrice=Float.valueOf(price[(int)exactBuy][5]);
						kaiPrice=Float.valueOf(price[(int)exactBuy][2]);
						lastFiveTrade=Float.valueOf(price[(int)exactBuy-5][6]);
						if(shouPrice>kaiPrice &&  Float.valueOf(price[(int)exactBuy][6])>lastFiveTrade)//��������������������ߣ�������>����  �ҵ���5��ƽ���ɽ���>ǰһ��5��ƽ���ɽ���
							//���ڵ��պ�ǰһ�յ�5��ƽ���ɽ��� �ཻ����м����춼����ȵ� ����ֻ��Ҫ�Ƚϵ��������ǰ�ĳɽ�������
						{
							buyMark=1;//���Ϊ����
							mt=exactBuy;
							m=Float.valueOf(price[(int)exactBuy][5]);//���̼�
						}
						else
						{
							exactBuy=exactBuy+1;
							
						}
					}
					
					if(buyMark==0)//ĿǰΪֹ����D�㣬��δ����������M��, �������
					{
						continue;
					}
				}
				else
				{
					break;//���ABC�����ɣ���Dδ���ɣ�û���γ�һ��������̬������һ����Ʊ�����
				}
				
					//����Ϊ��������̬abcdm���Ӧ��ָ����
				if(sname[i].substring(0, 3).compareTo("SZ300")==0 || sname[i].substring(0, 3).compareTo("SZ300")==0){
					for(k=(int)at;priceshangzheng[k][1]!=null;k++){
							
						if(price[(int)at][1].compareTo(priceshangzheng[k][1])==0){//a��  �����ڽ��бȽ�  ����ͬ��֪���ǵ��մ���ָ��
							za=Float.valueOf(priceshangzheng[k][5]);
							continue;
						}
						
						if(price[(int)bt][1].compareTo(priceshangzheng[k][1])==0){//b��
							zb=Float.valueOf(priceshangzheng[k][5]);
							continue;
								
						}
						if(price[(int)ct][1].compareTo(priceshangzheng[k][1])==0){//c��
							zc=Float.valueOf(priceshangzheng[k][5]);
							continue;
						}
						if(price[(int)dt][1].compareTo(priceshangzheng[k][1])==0){//d��
							zd=Float.valueOf(priceshangzheng[k][5]);
							continue;
						}	
						if(price[(int)mt][1].compareTo(priceshangzheng[k][1])==0){//m��
							zm=Float.valueOf(priceshangzheng[k][5]);
							break;
						}
					}
				}
					
				
					
				if(((c-a)/a)<=((zc-za)/za)) continue;
				if(((d-b)/b)<=((zd-zb)/zb)) continue;//��abc��bcd����һ������ָ�����������������̬
				if(((m-b)/b)<=((zm-zb)/zb)) continue;//��bcm����ָ�����������������̬
				//�����������������ˣ���ʣ�ºϸ���ˣ���������Ժϸ��Ʊ���д���	
				String[] rs=new String[15];
				rs[0]=price[(int)at][5];
				rs[1]=price[(int)bt][5];
				rs[2]=price[(int)ct][5];
				rs[3]=price[(int)dt][5];
				rs[4]=price[(int)at][1];
				rs[5]=price[(int)dt][1];
				rs[6]=Integer.toString((int)mt);//0Ϊa�� 1Ϊb�� 2Ϊc�� 3Ϊd�� 4Ϊa���� 5Ϊd���� 6ΪM���
				rs[7]=""+(c-a)/a+",,,"+(zc-za)/za;//7Ϊabc�ڼ䣬��Ʊ�Ƿ��ʹ����Ƿ�
				rs[8]=""+(d-b)/b+",,,"+(zd-zb)/zb;//8Ϊbcd�ڼ䣬��Ʊ�Ƿ��ʹ����Ƿ�
				rs[9]=price[(int)bt][1];//9Ϊb����
				rs[10]=price[(int)ct][1];//10Ϊc����
				rs[11]=price[(int)mt][1];//11Ϊm������
				rs[12]=price[(int)mt][5];//12Ϊm���̼�
				rs[13]=""+(m-b)/b+",,,"+(zm-zb)/zb;//13Ϊbcm�׶Σ���Ʊ�Ƿ��ʹ����Ƿ�
				if(rs[11].compareTo("20130701")<0 ){/////7-02 ���ʱ������  ����4��30�ţ�С��7��1��
					if( rs[11].compareTo("20130330") > 0 ){
						buy.add(rs);//�洢������̬
					}
				}
					

					
			}
				//��������������̬
			for(j=0;j<lowt.size()-1;j++){
					
				a=Float.valueOf(price[lowt.get(j).intValue()][5]);
				at=Float.valueOf(price[lowt.get(j).intValue()][0]);
				c=Float.valueOf(price[lowt.get(j+1).intValue()][5]);
				ct=Float.valueOf(price[lowt.get(j+1).intValue()][0]);
				
					
				//��a��c֮�����ߵ�b	
				b=Float.valueOf(price[lowt.get(j).intValue()+1][5]);
				bt=Float.valueOf(price[lowt.get(j).intValue()+1][0]);
				bmark=Float.valueOf(price[lowt.get(j).intValue()+1][5]);
				float nowbt=bt;
				for(k=(int)bt;k<ct;k++){
						
					if(Float.valueOf(price[k][5])>bmark){
						b=Float.valueOf(price[k][5]);
						nowbt=k;
						bmark=Float.valueOf(price[k][5]);
					}
					else if(Float.valueOf(price[k][5])==bmark)
					{
						//�����ֶ����ߵ�ʱ�����������<=5����ȡ����һ��Ϊ��ߵ�
						if(k-nowbt<=5)
						{
							b=Float.valueOf(price[k][5]);
							nowbt=k;
							bmark=Float.valueOf(price[k][5]);	
						}
					}	
				}
				
				bt=nowbt;
				dt=ct+(bt-at);
				if(price[(int)dt][0] != null)
					d=Float.valueOf(price[(int)dt][5]);
				else break;
				
				
				if(sname[i].substring(0, 3).compareTo("SZ300")==0 || sname[i].substring(0, 3).compareTo("SZ300")==0){
					for(k=(int)at;priceshangzheng[k][1]!=null;k++){
						
						if(price[(int)at][1].compareTo(priceshangzheng[k][1])==0){
							za=Float.valueOf(priceshangzheng[k][5]);
							continue;
						}
							
						if(price[(int)bt][1].compareTo(priceshangzheng[k][1])==0){
							zb=Float.valueOf(priceshangzheng[k][5]);
							continue;
								
						}
						if(price[(int)ct][1].compareTo(priceshangzheng[k][1])==0){
							zc=Float.valueOf(priceshangzheng[k][5]);
							continue;
						}
							
						if(price[(int)dt][1].compareTo(priceshangzheng[k][1])==0){
							zd=Float.valueOf(priceshangzheng[k][5]);
							break;
						}
					}
						
				}
					
				
				
				
				
				if( ((c-a)/a)>((zc-za)/za) && ( (d-b)/b > (zd-zb)/zb) ) continue;//��abc��bcd��ֻҪ��һ������ָ������������
				
				String[] rs=new String[12];
				rs[0]=price[(int)at][5];
				rs[1]=price[(int)bt][5];
				rs[2]=price[(int)ct][5];
				rs[3]=price[(int)dt][5];
				rs[4]=price[(int)at][1];
				rs[5]=price[(int)dt][1];
				rs[6]=Integer.toString((int)dt);
				rs[7]=""+(c-a)/a+",,,"+(zc-za)/za;//7Ϊabc�ڼ䣬��Ʊ�Ƿ��ʹ���ָ���Ƿ�
				rs[8]=""+(d-b)/b+",,,"+(zd-zb)/zb;//8Ϊbcd�ڼ䣬��Ʊ�Ƿ��ʹ���ָ���Ƿ�
				rs[9]=price[(int)bt][1];//9Ϊb����
				rs[10]=price[(int)ct][1];//10Ϊc����
				sell.add(rs);

			}
			
			for(j=0;j<buy.size();j++){//����ʱ���ʽ
				buy.get(j)[4]=buy.get(j)[4].substring(0, 4)+"-"+buy.get(j)[4].substring(4,6)+"-"+buy.get(j)[4].substring(6,8);//a����
				buy.get(j)[5]=buy.get(j)[5].substring(0, 4)+"-"+buy.get(j)[5].substring(4,6)+"-"+buy.get(j)[5].substring(6,8);//d����
				buy.get(j)[9]=buy.get(j)[9].substring(0, 4)+"-"+buy.get(j)[9].substring(4,6)+"-"+buy.get(j)[9].substring(6,8);//b����
				buy.get(j)[10]=buy.get(j)[10].substring(0, 4)+"-"+buy.get(j)[10].substring(4,6)+"-"+buy.get(j)[10].substring(6,8);//c����
				buy.get(j)[11]=buy.get(j)[11].substring(0, 4)+"-"+buy.get(j)[11].substring(4,6)+"-"+buy.get(j)[11].substring(6,8);//m����
			}
			for(j=0;j<sell.size();j++){//����ʱ���ʽ
				sell.get(j)[4]=sell.get(j)[4].substring(0,4)+"-"+sell.get(j)[4].substring(4,6)+"-"+sell.get(j)[4].substring(6,8);
				sell.get(j)[5]=sell.get(j)[5].substring(0,4)+"-"+sell.get(j)[5].substring(4,6)+"-"+sell.get(j)[5].substring(6,8);
				sell.get(j)[9]=sell.get(j)[9].substring(0,4)+"-"+sell.get(j)[9].substring(4,6)+"-"+sell.get(j)[9].substring(6,8);
				sell.get(j)[10]=sell.get(j)[10].substring(0,4)+"-"+sell.get(j)[10].substring(4,6)+"-"+sell.get(j)[10].substring(6,8);
			}
			
			/**
			 * 7-2 edit		
			 */
//			for(int i1 = 0;i1 < buy.size();i1++){
//				for(int s = 0; s < 15; s++){
//					System.out.println(buy.get(i1)[j]);
//				}
//			}
					
					
					
			/**
			 * 7-2 edit					
			 */
			
			
			//���������̬����������̬��������̬����ƥ�䣬����ѭ����������̬һ�㣬������̬һ��
			try{
						FileOutputStream fos = new FileOutputStream(fileout,true);
						OutputStreamWriter osw = new OutputStreamWriter(fos);
						BufferedWriter write = new BufferedWriter(osw);	
				
					for(j=0;j<=buy.size()-1;j++){
					
				
					write.newLine();
					write.write(sname[i]);
					write.write("	");
				
					
					write.write(buy.get(j)[4]);//Aʱ��
					write.write("	");
					write.write(buy.get(j)[0]);//A���̼�
					write.write("	");
					write.write(buy.get(j)[9]);//Bʱ��
					write.write("	");
					write.write(buy.get(j)[1]);//B���̼�
					write.write("	");
					write.write(buy.get(j)[10]);//Cʱ��
					write.write("	");
					write.write(buy.get(j)[2]);//C���̼�
					write.write("	");
					write.write(buy.get(j)[5]);//Dʱ��
					
					write.write("	");
					write.write(buy.get(j)[3]);//D���̼�
					
					write.write("	");
					write.write(buy.get(j)[11]);//Mʱ��
					
					write.write("	");
					write.write(buy.get(j)[12]);//M���̼�
					write.write("	");
					startTime = new SimpleDateFormat("yyyy-MM-dd").parse(buy.get(j)[11]); 
					write.write(buy.get(j)[7]);//ABC �Ƿ�
					write.write("	");
					write.write(buy.get(j)[8]);//BCD�Ƿ�
					write.write("	");
					write.write(buy.get(j)[13]);//BCM�Ƿ�
					
					write.write("	");
					
				    At[seriesNCount]=buy.get(j)[4];	//Aʱ��
				    Ap[seriesNCount]=buy.get(j)[0]; //A���̼�
				    Bt[seriesNCount]=buy.get(j)[9];	//Bʱ��
				    Bp[seriesNCount]=buy.get(j)[1]; //B���̼�
				    Ct[seriesNCount]=buy.get(j)[10];//Cʱ��
				    Cp[seriesNCount]=buy.get(j)[2];//C���̼�
				    Dt[seriesNCount]=buy.get(j)[5];//Dʱ��
				    Dp[seriesNCount]=buy.get(j)[3];//D���̼�
				    Mt[seriesNCount]=buy.get(j)[11];//Mʱ��
				    Mp[seriesNCount]=buy.get(j)[12]; //M���̼� 
				     abcStock[seriesNCount]=buy.get(j)[7];//ABC �Ƿ�
				     abcIndex[seriesNCount]="";
				     bcdStock[seriesNCount]=buy.get(j)[8];//BCD�Ƿ�
				     bcdIndex[seriesNCount]="";
				     bcmStock[seriesNCount]=buy.get(j)[13];//BCM�Ƿ�
				     bcmIndex[seriesNCount]="";
		
					sellmark=0;
					for(k=0;k<=sell.size()-1;k++){
						if(sell.get(k)[0].compareTo(buy.get(j)[11])>0){//������A'���ڱ�����M���棬�����������Գɹ�
							sellmark=k;
						//	System.out.println(sell.get(k)[5]+"�Ƿ����"+buy.get(j)[5]);
							break;
						}
					}//���ţ����������ѭ������
			//		System.out.println(sellmark);
			/**
			 * 7-2 edit		
			 */
					
					
					
					
					
			/**
			 * 7-2 edit					
			 */
					
					
					k=(int) sellmark;
					float push=0;
					if(k==0) {//k=0����ֻ���룬δ��������������   ������̬û���γ�    ���Ľ����ֹӯ���� �� ֹ������
						h=Integer.valueOf(buy.get(j)[6])+1;   //m��ż�1����m���һ��k�߿�ʼ��  �Ƿ����
						push=(float) (Float.valueOf(price[h][3])*(1+zhiying));//����ƽ�ֹӯ��
						for(;price[h][0]!=null && price[h+1][0]!=null && price[h+2][0]!=null && price[h+3][0]!=null;h++){
							//����Ĺ���> ȫ���ĳ� < �ˣ��ҳ�ǰ������k�ߵ���͵�  �� �����µ��ƽ�ֹ���
							if(Float.valueOf(price[h][3])<Float.valueOf(price[h-1][3])
									&&Float.valueOf(price[h][3])<Float.valueOf(price[h-2][3])
											&&Float.valueOf(price[h][3])<Float.valueOf(price[h-3][3])
													&&Float.valueOf(price[h][3])<Float.valueOf(price[h+1][3])
													&&Float.valueOf(price[h][3])<Float.valueOf(price[h+2][3])
															&&Float.valueOf(price[h][3])<Float.valueOf(price[h+3][3])
													)
							{
								if(Float.valueOf(price[h][3])>push) 
									push=Float.valueOf(price[h][3]);//��ֹӯ�ƽ����µĸߵ�
							}
							if(Float.valueOf(price[h][3])>push) {//������͵㣬���Ǵ���push
								
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
								
								endTime = new SimpleDateFormat("yyyy-MM-dd").parse(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
								int diff=(int) ((endTime.getTime()-startTime.getTime())/(24*60*60*1000));//������ʲô�أ�������������������
							//	System.out.println(endTime.getTime()+"��ȥ"+startTime.getTime()+" "+diff);
								totalholdtime=totalholdtime+diff;
								
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("ֹӯ����");
								write.write("	");
								
								write.write(""+(Float.valueOf(price[h][3])/Float.valueOf(buy.get(j)[3])-1-fee));//ֹӯ�����Ļ�����������Ϊ0.3   0.3�������ʲô��ϵ������������
								write.write("	");
								
								zhiyingCount++;
								avgWin=avgWin+Float.valueOf(price[h][3])/Float.valueOf(buy.get(j)[3])-1-fee;
				//				write.newLine();							
//								stockname[seriesNCount]=sname[i];
//								ets[seriesNCount]=price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8);
								stockname[seriesNCount]=sname[i];
							    ets[seriesNCount]=price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8);
							     SabcStock[seriesNCount]="";
							     SabcIndex[seriesNCount]="";
							     SbcdStock[seriesNCount]="";
							     SbcdIndex[seriesNCount]="";
							     AAt[seriesNCount]="";
							     BBt[seriesNCount]="";
							     CCt[seriesNCount]="";
							     DDt[seriesNCount]=ets[seriesNCount];
							     AAp[seriesNCount]="";
							     BBp[seriesNCount]="";
							     CCp[seriesNCount]="";
							     DDp[seriesNCount]=""+(Float.valueOf(price[h][3])/Float.valueOf(buy.get(j)[3])-1-fee);  
								seriesNCount++;
								break;
							}
							if(Float.valueOf(price[h][4])<Float.valueOf(buy.get(j)[12])*(1+zhisun)){//��ĳ�����ͼۡ����뵱�����̼۵�0.9 ����ֹ������ 
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
								endTime = new SimpleDateFormat("yyyy-MM-dd").parse(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));	
								int diff=(int) ((endTime.getTime()-startTime.getTime())/(24*60*60*1000));
							//	System.out.println(endTime.getTime()+"��ȥ"+startTime.getTime()+" "+diff);
								totalholdtime=totalholdtime+diff;
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("ֹ������");
								write.write("	");
								
								write.write(""+(zhisun-fee));
								write.write("	");
								zhisunCount++;
								avgLoss=avgLoss+zhisun-fee;  //zhisun��һ������  fee��float fee=(float)(2.5/1000)   �����ô���أ���������������������������
			//					write.newLine();
								stockname[seriesNCount]=sname[i];
							     ets[seriesNCount]=price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8);
							     SabcStock[seriesNCount]="";
							     SabcIndex[seriesNCount]="";
							     SbcdStock[seriesNCount]="";
							     SbcdIndex[seriesNCount]="";
							     AAt[seriesNCount]="";
							     BBt[seriesNCount]="";
							     CCt[seriesNCount]="";
							     DDt[seriesNCount]=ets[seriesNCount];
							     AAp[seriesNCount]="";
							     BBp[seriesNCount]="";
							     CCp[seriesNCount]="";
							     DDp[seriesNCount]=""+(zhisun-fee);     
							    seriesNCount++;
									
								break;
							}
						}
			//			write.newLine();
						continue;
					}
					if (k!=0) {//�����룬Ҳ������
						int mark=0;
						h=Integer.valueOf(buy.get(j)[6])+1;
						push=(float) (Float.valueOf(price[h][3])*(1+zhiying));
						for(;h<=Integer.valueOf(sell.get(k)[6]);h++){//�����뵽����֮�䣬������ֹӯ����ֹ��������������
							if(Float.valueOf(price[h][3])>Float.valueOf(price[h-1][3])
									&&Float.valueOf(price[h][3])>Float.valueOf(price[h-2][3])
											&&Float.valueOf(price[h][3])>Float.valueOf(price[h-3][3])
													&&Float.valueOf(price[h][3])>Float.valueOf(price[h+1][3])
													&&Float.valueOf(price[h][3])>Float.valueOf(price[h+2][3])
															&&Float.valueOf(price[h][3])>Float.valueOf(price[h+3][3])
													)
							{
								if(Float.valueOf(price[h][3])>push) 
									push=Float.valueOf(price[h][3]);//��ֹӯ�ƽ����µĸߵ㣿��������������������
							}
							if(Float.valueOf(price[h][3])>push) {
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
								endTime = new SimpleDateFormat("yyyy-MM-dd").parse(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
								int diff=(int) ((endTime.getTime()-startTime.getTime())/(24*60*60*1000));
							//	System.out.println(endTime.getTime()+"��ȥ"+startTime.getTime()+" "+diff);
								totalholdtime=totalholdtime+diff;
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("����������̬����������ǰֹӯ����");
								write.write("	");
								
								write.write(""+(Float.valueOf(price[h][3])/Float.valueOf(buy.get(j)[3])-1-fee));
								write.write("	");
								avgWin=avgWin+Float.valueOf(price[h][3])/Float.valueOf(buy.get(j)[3])-1-fee;
				//				write.newLine();
								mark=1;
								zhiyingCount++;
								
								stockname[seriesNCount]=sname[i];
							    ets[seriesNCount]=price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8);
							    SabcStock[seriesNCount]="";
							    SabcIndex[seriesNCount]="";
							    SbcdStock[seriesNCount]="";
							    SbcdIndex[seriesNCount]="";
							    AAt[seriesNCount]="";
							    BBt[seriesNCount]="";
							    CCt[seriesNCount]="";
							    DDt[seriesNCount]=ets[seriesNCount];
							    AAp[seriesNCount]="";
							    BBp[seriesNCount]="";
							    CCp[seriesNCount]="";
							    DDp[seriesNCount]=""+(Float.valueOf(price[h][3])/Float.valueOf(buy.get(j)[3])-1-fee);
								seriesNCount++;
								
								break;
							}
							if(Float.valueOf(price[h][4])<Float.valueOf(buy.get(j)[12])*(1+zhisun)){  //������������̼۵�0.9  ���Ӧ����ֹ������
								write.write("");
								write.write("	");
								
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
								endTime = new SimpleDateFormat("yyyy-MM-dd").parse(price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8));
								int diff=(int) ((endTime.getTime()-startTime.getTime())/(24*60*60*1000));	
						//		System.out.println(endTime.getTime()+"��ȥ"+startTime.getTime()+" "+diff);
								totalholdtime=totalholdtime+diff;
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("");
								write.write("	");
								write.write("����������̬����������ǰֹ������");
								write.write("	");
	
								
								write.write(""+(zhisun-fee));//��������Ϊ-0.1
								write.write("	");
								avgLoss=avgLoss+zhisun-fee;//
				//				write.newLine();
								mark=1;
								zhisunCount++;
								
								stockname[seriesNCount]=sname[i];
								ets[seriesNCount]=price[h][1].substring(0,4)+"-"+price[h][1].substring(4,6)+"-"+price[h][1].substring(6,8);
							    SabcStock[seriesNCount]="";
							    SabcIndex[seriesNCount]="";
							    SbcdStock[seriesNCount]="";
							    SbcdIndex[seriesNCount]="";
							    AAt[seriesNCount]="";
							    BBt[seriesNCount]="";
							    CCt[seriesNCount]="";
							    DDt[seriesNCount]=ets[seriesNCount];
							    AAp[seriesNCount]="";
							    BBp[seriesNCount]="";
							    CCp[seriesNCount]="";
							    DDp[seriesNCount]=""+(zhisun-fee);
								seriesNCount++;
								     
								break;
							}
						}
						if(mark==1) continue;//mark==1 ��ʾ������̬�������ʱ����Ϊֹӯ��ֹ��ԭ����ǰ��������������������������ʲô������ǰ�������������������������ʲôʱ������������
					}//������k��=0    �����룬Ҳ������
					
					write.write(sell.get(k)[4]);//Aʱ��
					write.write("	");
					write.write(sell.get(k)[0]);//A���̼�
					write.write("	");
					write.write(sell.get(k)[9]);//Bʱ��
					write.write("	");
					write.write(sell.get(k)[1]);//B���̼�
					write.write("	");
					write.write(sell.get(k)[10]);//Cʱ��
					write.write("	");
					write.write(sell.get(k)[2]);//C���̼�
					write.write("	");
					write.write(sell.get(k)[5]);//Dʱ��
					endTime = new SimpleDateFormat("yyyy-MM-dd").parse(sell.get(k)[5]);
					int diff=(int) ((endTime.getTime()-startTime.getTime())/(24*60*60*1000));
					//System.out.println(endTime.getTime()+"��ȥ"+startTime.getTime()+" "+diff);
					totalholdtime=totalholdtime+diff;
					write.write("	");
					write.write(sell.get(k)[3]);//D���̼�
					write.write("	");
					write.write(sell.get(k)[7]);//ABC �Ƿ�
					write.write("	");
					write.write(sell.get(k)[8]);//BCD�Ƿ�
					write.write("	");
					
					write.write("��������");
					write.write("	");
					
					write.write(Float.toString((Float.valueOf(sell.get(k)[3])-Float.valueOf(buy.get(j)[3]))/Float.valueOf(buy.get(j)[3])-fee));//���������������
					
					write.write("	");
					normalCount++;
					normalRet=normalRet+(Float.valueOf(sell.get(k)[3])-Float.valueOf(buy.get(j)[3]))/(Float.valueOf(buy.get(j)[3])-fee);
					if((Float.valueOf(sell.get(k)[3])-Float.valueOf(buy.get(j)[3]))/(Float.valueOf(buy.get(j)[3])-fee)>0)//���������ӯ���𣿣���������ӯ�ʴ���0
					{
						winCount++;
						avgWin=avgWin+(Float.valueOf(sell.get(k)[3])-Float.valueOf(buy.get(j)[3]))/(Float.valueOf(buy.get(j)[3])-fee);
					}
					else
					{
						lossCount++;
						avgLoss=avgLoss+(Float.valueOf(sell.get(k)[3])-Float.valueOf(buy.get(j)[3]))/(Float.valueOf(buy.get(j)[3])-fee);
					}
					
				    stockname[seriesNCount]=sname[i];
					ets[seriesNCount]=sell.get(k)[5];
					SabcStock[seriesNCount]=sell.get(k)[7];
				    SabcIndex[seriesNCount]="";
				    SbcdStock[seriesNCount]=sell.get(k)[8];
				    SbcdIndex[seriesNCount]="";
				    AAt[seriesNCount]=sell.get(k)[4];
				    BBt[seriesNCount]=sell.get(k)[9];
				    CCt[seriesNCount]=sell.get(k)[10];
				    DDt[seriesNCount]=ets[seriesNCount];
				    AAp[seriesNCount]=sell.get(k)[0];
				    BBp[seriesNCount]=sell.get(k)[1];
				    CCp[seriesNCount]=sell.get(k)[2];
				    DDp[seriesNCount]=sell.get(k)[3];
				    seriesNCount++;
				
					//		write.newLine();
				}//��������ǣ�������ǲ�ѭ������
					write.close();
				}catch(Exception ee){
					ee.printStackTrace();
				
				
				
			}
				
		//ÿֻ��Ʊ��������󣬽��и��ֳ�ʼ��
	
		buy.clear();
		sell.clear();
		hight.clear();
		lowt.clear();
		for(h=0;h<1000;h++)
			for(f=0;f<8;f++){
				price[h][f]=null;
			}
		
		}	
		
	System.out.println("ֹ����������:"+zhisunCount+"\tֹӯ��������:"+zhiyingCount+"\t������������:"+normalCount);	
	System.out.println("����������ƽ��������"+(normalRet/normalCount));	
	totalCount=zhisunCount+zhiyingCount+normalCount;
	double totalRet=0;
	System.out.println("���н��׵�ƽ��������"+((normalRet+(0.3-2.5/1000)*zhiyingCount+(-0.1-2.5/1000)*zhisunCount)/totalCount));
	
	System.out.println("�ܽ��״���:"+totalCount);
	System.out.println("����������ʤ��:"+((float)winCount/normalCount));
	System.out.println("���н�����ʤ��:"+((float)(winCount+zhiyingCount)/totalCount));
	System.out.println("���н��׵�ʤ������"+(winCount+zhiyingCount));
	System.out.println("���н��׵�ʧ�ܴ���"+(lossCount+zhisunCount));
	avgWin=avgWin/(winCount+zhiyingCount);
	avgLoss=avgLoss/(lossCount+zhisunCount);
	System.out.println("���н��׵�ƽ��ӯ��:"+(-avgWin/avgLoss));
	System.out.println("���н��׵�ƽ���ֲ�ʱ��"+((float)totalholdtime/totalCount));
	inputST=new SimpleDateFormat("yyyy-MM-dd").parse(inputst);
	inputET = new SimpleDateFormat("yyyy-MM-dd").parse(inputet);
	int totaldiff=(int) ((inputET.getTime()-inputST.getTime())/(24*60*60*1000));
	System.out.println("���ݳ��е�ʱ���Ϊ"+inputst+"��"+inputet+","+((float)totaldiff/365));
	System.out.println("���н��׵�ƽ������Ƶ��:"+((float)totalholdtime/totalCount)/((float)totaldiff/365*250));
	System.out.println("seriesNCount:"+seriesNCount); 
	
	
	
	
		  
		final JFrame jf=new JFrame("xxxϵͳ");	
		Container con=jf.getContentPane();
		jf.setLayout(new BorderLayout());
		
		Container con2=new Container();
		Container con3=new Container();
		con2.setLayout(new BorderLayout());
		con3.setLayout(new GridLayout(18,2,1,1));
		con3.add(new Label("�����ǵڼ�������"));
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
		final JButton jb1=new JButton("ʱ��ǰ��");
		final JButton jb2=new JButton("ʱ�����");
		final JButton jb=new JButton("���ͼ��");
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
			  double highValue = Double.MIN_VALUE;//����K�����ݵ��е����ֵ
			  double minValue = Double.MAX_VALUE;//����K�����ݵ��е���Сֵ
			  double high2Value = Double.MIN_VALUE;//���óɽ��������ֵ
			  double min2Value = Double.MAX_VALUE;//���óɽ��������ֵ
			  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
			  for(int q=0;q<sname.length;q++)
			  {
				  if(sname[q].equals(stockname[num]))
				  {
					numm=q;
				  }
			  }
			
			//����K�����ݵ����ݼ�����������Ϊfinal������Ҫ�������ڲ��������õ�
			   final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();
			   seriesCollection.addSeries(series[numm]);
			//�����ɽ������ݵļ���
			TimeSeriesCollection timeSeriesCollection=new TimeSeriesCollection();
			timeSeriesCollection.addSeries(series2[numm]);
			System.out.println("num"+num);
			System.out.println("stTime"+sts[num]);
			System.out.println("etTime"+ets[num]);
			//��ȡK�����ݵ����ֵ�����ֵ
			int seriesCount = seriesCollection.getSeriesCount();//һ���ж��ٸ����У�ĿǰΪһ��
			for (int ii = 0; ii < seriesCount; ii++) 
			{
			     int itemCount = seriesCollection.getItemCount(ii);//ÿһ�������ж��ٸ�������
			     System.out.println("itemCount"+itemCount);
			     for (int j = 0; j < itemCount; j++)
			     {
				       if (highValue < seriesCollection.getHighValue(ii, j)) 
				       {//highValue��ȡ��i�������еĵ�j������������ֵ
				         highValue = seriesCollection.getHighValue(ii, j);
				       }
				       if (minValue > seriesCollection.getLowValue(ii, j)) 
				       {//minVaule��ȡ��i�������еĵ�j�����������Сֵ
				        minValue = seriesCollection.getLowValue(ii, j);
				       }
			     }

			}
			//��ȡ�ɽ������ֵ�����ֵ
			int seriesCount2 = timeSeriesCollection.getSeriesCount();//һ���ж��ٸ����У�ĿǰΪһ��
			for (int ii = 0; ii < seriesCount2; ii++)
			{
			     int itemCount = timeSeriesCollection.getItemCount(ii);//ÿһ�������ж��ٸ�������
			     for (int j = 0; j < itemCount; j++) 
			     {
				       if (high2Value < timeSeriesCollection.getYValue(ii,j)) 
				       {//ȡ��i�������еĵ�j���������ֵ
				        high2Value = timeSeriesCollection.getYValue(ii,j);
				       }
				       if (min2Value > timeSeriesCollection.getYValue(ii, j)) 
				       {//ȡ��i�������еĵ�j���������ֵ
				        min2Value = timeSeriesCollection.getYValue(ii, j);
				       }
			     }

			}
			final CandlestickRenderer candlestickRender=new CandlestickRenderer();//����K��ͼ�Ļ�ͼ������������Ϊfinal������Ҫ�������ڲ��������õ�
			candlestickRender.setUseOutlinePaint(true); //�����Ƿ�ʹ���Զ���ı߿��ߣ������Դ��ı߿��ߵ���ɫ�������й���Ʊ�г���ϰ��
			candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//������ζ�K��ͼ�Ŀ�Ƚ����趨
			candlestickRender.setAutoWidthGap(0.001);//���ø���K��ͼ֮��ļ��
			candlestickRender.setUpPaint(Color.RED);//���ù�Ʊ���ǵ�K��ͼ��ɫ
			candlestickRender.setDownPaint(Color.GREEN);//���ù�Ʊ�µ���K��ͼ��ɫ
			//����x�ᣬҲ����ʱ����
			DateAxis x1Axis=new DateAxis();
			//���ò������Զ�����ʱ�䷶Χ
			x1Axis.setAutoRange(false);
			try{
				//����ʱ�䷶Χ��ע��ʱ������ֵҪ�����е�ʱ�����ֵҪ��һ��
			   //Ҫ�޸�ʱ�䷶Χ
			  inputst=At[num];
			  cal.setTime(sdf.parse(inputst));
		      cal.add(Calendar.DATE, 30);
		      inputet=sdf.format(cal.getTime());
		  	  x1Axis.setRange(dateFormat.parse(inputst),dateFormat.parse(inputet));
			}catch(Exception e){
			 e.printStackTrace();
			}
			x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//����ʱ������ʾ�Ĺ���������������������������������Щû�н��׵�����(�ܶ��˶���֪���д˷���)��ʹͼ�ο���ȥ����
			x1Axis.setAutoTickUnitSelection(false);//���ò������Զ�ѡ��̶�ֵ
			x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//���ñ�ǵ�λ��
			x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//���ñ�׼��ʱ��̶ȵ�λ
			x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));//����ʱ��̶ȵļ����һ������Ϊ��λ
			x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//������ʾʱ��ĸ�ʽ
			NumberAxis y1Axis=new NumberAxis();//�趨y�ᣬ����������
			y1Axis.setAutoRange(false);//��ʹ���Զ��趨��Χ
			y1Axis.setRange(minValue*0.9, highValue*1.1);//�趨y��ֵ�ķ�Χ�������ֵҪ��һЩ�������ֵҪ��һЩ������ͼ�ο�����������Щ
			y1Axis.setTickUnit(new NumberTickUnit((highValue*1.1-minValue*0.9)/10));//���ÿ̶���ʾ���ܶ�
			XYPlot plot1=new XYPlot(seriesCollection,x1Axis,y1Axis,candlestickRender);//���û�ͼ�������

			XYBarRenderer xyBarRender=new XYBarRenderer(){
			private static final long serialVersionUID = 1L;//Ϊ�˱�����־�����Ϣ�����趨��ֵ
			public Paint getItemPaint(int ii, int j){//�����ڲ������������յĳɽ�������ͼ����ɫ��K��ͼ����ɫ����һ��
			  if(seriesCollection.getCloseValue(ii,j)>seriesCollection.getOpenValue(ii,j)){//���̼۸��ڿ��̼ۣ���Ʊ���ǣ�ѡ�ù�Ʊ���ǵ���ɫ
			   return candlestickRender.getUpPaint();
			  }else{
			   return candlestickRender.getDownPaint();
			  }
			}};
			xyBarRender.setMargin(0.1);//��������ͼ֮��ļ��
			NumberAxis y2Axis=new NumberAxis();//����Y�ᣬΪ��ֵ,��������ã��ο������y������
			y2Axis.setAutoRange(false);
			y2Axis.setRange(min2Value*0.9, high2Value*1.1);
			y2Axis.setTickUnit(new NumberTickUnit((high2Value*1.1-min2Value*0.9)/4));
			XYPlot plot2=new XYPlot(timeSeriesCollection,null,y2Axis,xyBarRender);//�����ڶ�����ͼ���������Ҫ��ʱ��x����Ϊ��nullֵ����ΪҪ���һ����ͼ���������x��
			CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//����һ��ǡ��������ͼ�����������x��Ϊ������
			combineddomainxyplot.add(plot1, 2);//���ͼ��������󣬺���������Ǽ�������������Ӧ��ռ�ݶ�������2/3
			combineddomainxyplot.add(plot2, 1);//���ͼ��������󣬺���������Ǽ�������������Ӧ��ռ�ݶ�������1/3
			combineddomainxyplot.setGap(10);//��������ͼ���������֮��ļ���ռ�
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
			con3.add(new Label("�����ǵڼ�������"));
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
			  double highValue = Double.MIN_VALUE;//����K�����ݵ��е����ֵ
			  double minValue = Double.MAX_VALUE;//����K�����ݵ��е���Сֵ
			  double high2Value = Double.MIN_VALUE;//���óɽ��������ֵ
			  double min2Value = Double.MAX_VALUE;//���óɽ��������ֵ
			  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
			  for(int q=0;q<sname.length;q++)
			  {
				  if(sname[q].equals(stockname[num]))
				  {
					numm=q;
				  }
			  }
			
			//����K�����ݵ����ݼ�����������Ϊfinal������Ҫ�������ڲ��������õ�
			   final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();
			   seriesCollection.addSeries(series[numm]);
			//�����ɽ������ݵļ���
			TimeSeriesCollection timeSeriesCollection=new TimeSeriesCollection();
			timeSeriesCollection.addSeries(series2[numm]);
			System.out.println("num"+num);
			System.out.println("stTime"+sts[num]);
			System.out.println("etTime"+ets[num]);
			//��ȡK�����ݵ����ֵ�����ֵ
			int seriesCount = seriesCollection.getSeriesCount();//һ���ж��ٸ����У�ĿǰΪһ��
			for (int ii = 0; ii < seriesCount; ii++) 
			{
			     int itemCount = seriesCollection.getItemCount(ii);//ÿһ�������ж��ٸ�������
			     System.out.println("itemCount"+itemCount);
			     for (int j = 0; j < itemCount; j++)
			     {
				       if (highValue < seriesCollection.getHighValue(ii, j)) 
				       {//highValue��ȡ��i�������еĵ�j������������ֵ
				         highValue = seriesCollection.getHighValue(ii, j);
				       }
				       if (minValue > seriesCollection.getLowValue(ii, j)) 
				       {//minVaule��ȡ��i�������еĵ�j�����������Сֵ
				        minValue = seriesCollection.getLowValue(ii, j);
				       }
			     }

			}
			//��ȡ�ɽ������ֵ�����ֵ
			int seriesCount2 = timeSeriesCollection.getSeriesCount();//һ���ж��ٸ����У�ĿǰΪһ��
			for (int ii = 0; ii < seriesCount2; ii++)
			{
			     int itemCount = timeSeriesCollection.getItemCount(ii);//ÿһ�������ж��ٸ�������
			     for (int j = 0; j < itemCount; j++) 
			     {
				       if (high2Value < timeSeriesCollection.getYValue(ii,j)) 
				       {//ȡ��i�������еĵ�j���������ֵ
				        high2Value = timeSeriesCollection.getYValue(ii,j);
				       }
				       if (min2Value > timeSeriesCollection.getYValue(ii, j)) 
				       {//ȡ��i�������еĵ�j���������ֵ
				        min2Value = timeSeriesCollection.getYValue(ii, j);
				       }
			     }

			}
			final CandlestickRenderer candlestickRender=new CandlestickRenderer();//����K��ͼ�Ļ�ͼ������������Ϊfinal������Ҫ�������ڲ��������õ�
			candlestickRender.setUseOutlinePaint(true); //�����Ƿ�ʹ���Զ���ı߿��ߣ������Դ��ı߿��ߵ���ɫ�������й���Ʊ�г���ϰ��
			candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//������ζ�K��ͼ�Ŀ�Ƚ����趨
			candlestickRender.setAutoWidthGap(0.001);//���ø���K��ͼ֮��ļ��
			candlestickRender.setUpPaint(Color.RED);//���ù�Ʊ���ǵ�K��ͼ��ɫ
			candlestickRender.setDownPaint(Color.GREEN);//���ù�Ʊ�µ���K��ͼ��ɫ
			//����x�ᣬҲ����ʱ����
			DateAxis x1Axis=new DateAxis();
			//���ò������Զ�����ʱ�䷶Χ
			x1Axis.setAutoRange(false);
			try{
				//����ʱ�䷶Χ��ע��ʱ������ֵҪ�����е�ʱ�����ֵҪ��һ��
			   //Ҫ�޸�ʱ�䷶Χ	
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
			x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//����ʱ������ʾ�Ĺ���������������������������������Щû�н��׵�����(�ܶ��˶���֪���д˷���)��ʹͼ�ο���ȥ����
			x1Axis.setAutoTickUnitSelection(false);//���ò������Զ�ѡ��̶�ֵ
			x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//���ñ�ǵ�λ��
			x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//���ñ�׼��ʱ��̶ȵ�λ
			x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));//����ʱ��̶ȵļ����һ������Ϊ��λ
			x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//������ʾʱ��ĸ�ʽ
			NumberAxis y1Axis=new NumberAxis();//�趨y�ᣬ����������
			y1Axis.setAutoRange(false);//��ʹ���Զ��趨��Χ
			y1Axis.setRange(minValue*0.9, highValue*1.1);//�趨y��ֵ�ķ�Χ�������ֵҪ��һЩ�������ֵҪ��һЩ������ͼ�ο�����������Щ
			y1Axis.setTickUnit(new NumberTickUnit((highValue*1.1-minValue*0.9)/10));//���ÿ̶���ʾ���ܶ�
			XYPlot plot1=new XYPlot(seriesCollection,x1Axis,y1Axis,candlestickRender);//���û�ͼ�������

			XYBarRenderer xyBarRender=new XYBarRenderer(){
			private static final long serialVersionUID = 1L;//Ϊ�˱�����־�����Ϣ�����趨��ֵ
			public Paint getItemPaint(int ii, int j){//�����ڲ������������յĳɽ�������ͼ����ɫ��K��ͼ����ɫ����һ��
			  if(seriesCollection.getCloseValue(ii,j)>seriesCollection.getOpenValue(ii,j)){//���̼۸��ڿ��̼ۣ���Ʊ���ǣ�ѡ�ù�Ʊ���ǵ���ɫ
			   return candlestickRender.getUpPaint();
			  }else{
			   return candlestickRender.getDownPaint();
			  }
			}};
			xyBarRender.setMargin(0.1);//��������ͼ֮��ļ��
			NumberAxis y2Axis=new NumberAxis();//����Y�ᣬΪ��ֵ,��������ã��ο������y������
			y2Axis.setAutoRange(false);
			y2Axis.setRange(min2Value*0.9, high2Value*1.1);
			y2Axis.setTickUnit(new NumberTickUnit((high2Value*1.1-min2Value*0.9)/4));
			XYPlot plot2=new XYPlot(timeSeriesCollection,null,y2Axis,xyBarRender);//�����ڶ�����ͼ���������Ҫ��ʱ��x����Ϊ��nullֵ����ΪҪ���һ����ͼ���������x��
			CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//����һ��ǡ��������ͼ�����������x��Ϊ������
			combineddomainxyplot.add(plot1, 2);//���ͼ��������󣬺���������Ǽ�������������Ӧ��ռ�ݶ�������2/3
			combineddomainxyplot.add(plot2, 1);//���ͼ��������󣬺���������Ǽ�������������Ӧ��ռ�ݶ�������1/3
			combineddomainxyplot.setGap(10);//��������ͼ���������֮��ļ���ռ�
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
			  double highValue = Double.MIN_VALUE;//����K�����ݵ��е����ֵ
			  double minValue = Double.MAX_VALUE;//����K�����ݵ��е���Сֵ
			  double high2Value = Double.MIN_VALUE;//���óɽ��������ֵ
			  double min2Value = Double.MAX_VALUE;//���óɽ��������ֵ
			  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
			  for(int q=0;q<sname.length;q++)
			  {
				  if(sname[q].equals(stockname[num]))
				  {
					numm=q;
				  }
			  }
			
			//����K�����ݵ����ݼ�����������Ϊfinal������Ҫ�������ڲ��������õ�
			   final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();
			   seriesCollection.addSeries(series[numm]);
			//�����ɽ������ݵļ���
			TimeSeriesCollection timeSeriesCollection=new TimeSeriesCollection();
			timeSeriesCollection.addSeries(series2[numm]);
			System.out.println("num"+num);
			System.out.println("stTime"+sts[num]);
			System.out.println("etTime"+ets[num]);
			//��ȡK�����ݵ����ֵ�����ֵ
			int seriesCount = seriesCollection.getSeriesCount();//һ���ж��ٸ����У�ĿǰΪһ��
			for (int ii = 0; ii < seriesCount; ii++) 
			{
			     int itemCount = seriesCollection.getItemCount(ii);//ÿһ�������ж��ٸ�������
			     System.out.println("itemCount"+itemCount);
			     for (int j = 0; j < itemCount; j++)
			     {
				       if (highValue < seriesCollection.getHighValue(ii, j)) 
				       {//highValue��ȡ��i�������еĵ�j������������ֵ
				         highValue = seriesCollection.getHighValue(ii, j);
				       }
				       if (minValue > seriesCollection.getLowValue(ii, j)) 
				       {//minVaule��ȡ��i�������еĵ�j�����������Сֵ
				        minValue = seriesCollection.getLowValue(ii, j);
				       }
			     }

			}
			//��ȡ�ɽ������ֵ�����ֵ
			int seriesCount2 = timeSeriesCollection.getSeriesCount();//һ���ж��ٸ����У�ĿǰΪһ��
			for (int ii = 0; ii < seriesCount2; ii++)
			{
			     int itemCount = timeSeriesCollection.getItemCount(ii);//ÿһ�������ж��ٸ�������
			     for (int j = 0; j < itemCount; j++) 
			     {
				       if (high2Value < timeSeriesCollection.getYValue(ii,j)) 
				       {//ȡ��i�������еĵ�j���������ֵ
				        high2Value = timeSeriesCollection.getYValue(ii,j);
				       }
				       if (min2Value > timeSeriesCollection.getYValue(ii, j)) 
				       {//ȡ��i�������еĵ�j���������ֵ
				        min2Value = timeSeriesCollection.getYValue(ii, j);
				       }
			     }

			}
			final CandlestickRenderer candlestickRender=new CandlestickRenderer();//����K��ͼ�Ļ�ͼ������������Ϊfinal������Ҫ�������ڲ��������õ�
			candlestickRender.setUseOutlinePaint(true); //�����Ƿ�ʹ���Զ���ı߿��ߣ������Դ��ı߿��ߵ���ɫ�������й���Ʊ�г���ϰ��
			candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//������ζ�K��ͼ�Ŀ�Ƚ����趨
			candlestickRender.setAutoWidthGap(0.001);//���ø���K��ͼ֮��ļ��
			candlestickRender.setUpPaint(Color.RED);//���ù�Ʊ���ǵ�K��ͼ��ɫ
			candlestickRender.setDownPaint(Color.GREEN);//���ù�Ʊ�µ���K��ͼ��ɫ
			//����x�ᣬҲ����ʱ����
			DateAxis x1Axis=new DateAxis();
			//���ò������Զ�����ʱ�䷶Χ
			x1Axis.setAutoRange(false);
			try{
				//����ʱ�䷶Χ��ע��ʱ������ֵҪ�����е�ʱ�����ֵҪ��һ��
			   //Ҫ�޸�ʱ�䷶Χ
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
			x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//����ʱ������ʾ�Ĺ���������������������������������Щû�н��׵�����(�ܶ��˶���֪���д˷���)��ʹͼ�ο���ȥ����
			x1Axis.setAutoTickUnitSelection(false);//���ò������Զ�ѡ��̶�ֵ
			x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//���ñ�ǵ�λ��
			x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//���ñ�׼��ʱ��̶ȵ�λ
			x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,7));//����ʱ��̶ȵļ����һ������Ϊ��λ
			x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//������ʾʱ��ĸ�ʽ
			NumberAxis y1Axis=new NumberAxis();//�趨y�ᣬ����������
			y1Axis.setAutoRange(false);//��ʹ���Զ��趨��Χ
			y1Axis.setRange(minValue*0.9, highValue*1.1);//�趨y��ֵ�ķ�Χ�������ֵҪ��һЩ�������ֵҪ��һЩ������ͼ�ο�����������Щ
			y1Axis.setTickUnit(new NumberTickUnit((highValue*1.1-minValue*0.9)/10));//���ÿ̶���ʾ���ܶ�
			XYPlot plot1=new XYPlot(seriesCollection,x1Axis,y1Axis,candlestickRender);//���û�ͼ�������

			XYBarRenderer xyBarRender=new XYBarRenderer(){
			private static final long serialVersionUID = 1L;//Ϊ�˱�����־�����Ϣ�����趨��ֵ
			public Paint getItemPaint(int ii, int j){//�����ڲ������������յĳɽ�������ͼ����ɫ��K��ͼ����ɫ����һ��
			  if(seriesCollection.getCloseValue(ii,j)>seriesCollection.getOpenValue(ii,j)){//���̼۸��ڿ��̼ۣ���Ʊ���ǣ�ѡ�ù�Ʊ���ǵ���ɫ
			   return candlestickRender.getUpPaint();
			  }else{
			   return candlestickRender.getDownPaint();
			  }
			}};
			xyBarRender.setMargin(0.1);//��������ͼ֮��ļ��
			NumberAxis y2Axis=new NumberAxis();//����Y�ᣬΪ��ֵ,��������ã��ο������y������
			y2Axis.setAutoRange(false);
			y2Axis.setRange(min2Value*0.9, high2Value*1.1);
			y2Axis.setTickUnit(new NumberTickUnit((high2Value*1.1-min2Value*0.9)/4));
			XYPlot plot2=new XYPlot(timeSeriesCollection,null,y2Axis,xyBarRender);//�����ڶ�����ͼ���������Ҫ��ʱ��x����Ϊ��nullֵ����ΪҪ���һ����ͼ���������x��
			CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//����һ��ǡ��������ͼ�����������x��Ϊ������
			combineddomainxyplot.add(plot1, 2);//���ͼ��������󣬺���������Ǽ�������������Ӧ��ռ�ݶ�������2/3
			combineddomainxyplot.add(plot2, 1);//���ͼ��������󣬺���������Ǽ�������������Ӧ��ռ�ݶ�������1/3
			combineddomainxyplot.setGap(10);//��������ͼ���������֮��ļ���ռ�
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
			con3.add(new Label("�����ǵڼ�������"));
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
		
	
 