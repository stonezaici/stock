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
		int low_k_min_value = 10;//�͵�֮��k���� ����Ӧ���������
		int high_k_min_value = 10;//�ߵ�֮��k���� ����Ӧ���������
		int AC_Dis = 100; //ac����֮����������k�ߵ�����
		
		
		String[] sname=null;//��Ʊ���ļ���
		String line=null;
		String[] lineaftersplit=null;
		String[][] price=new String[10000][8];//��Ʊ�۸���Ϣ
		String[][] pricehusheng=new String[10000][8];//����300�۸���Ϣ
		String[][] pricezhongxiao=new String[10000][8];//��С��۸���Ϣ
		String[][] pricechuangye=new String[10000][8];//��ҵ��۸���Ϣ
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
		String zm_date=null;//Z����abcd����abcd���Ӧ��ָ��
		float bmark=0;
		float sellmark=0;
		Date startTime=null;
		Date endTime=null;
		float abdiff=(float) 0.1;//ab֮��۸� ����
		//��m��ʱ�õ��ı���
		float exactBuy;//exactBuy����������
		float shouPrice;
	    float kaiPrice;
		float lastFiveTrade;
		int buyMark=0;
		//*********//
		File namefile=new File(fileDir) ;//������Խ��ļ�·�����ȫ�ֱ���
		sname=namefile.list();
		int i=0;
		int j=0;
		int k=0;
		int z=0;
		int h=0;//ѭ������
		
		File fileout =null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String nowdate=dateFormat.format(new java.util.Date());
		fileout =new File("D:\\newwirthoutput\\"+"���ߡ�"+nowdate+".txt");
		if (!fileout.exists()) {             
			try {
				fileout.createNewFile();
			} catch (IOException e1) {
					// TODO Auto-generated catch block
				e1.printStackTrace();
			}              
			System.out.println(fileout + "�Ѵ�����");
		
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
			
//				write.write("������̬��ʼ");
//				write.write("	");
//				write.write("A");
//				write.write("	");
//				write.write("B");
//				write.write("	");
//				write.write("C");
//				write.write("	");
//				write.write("D");
//				write.write("	");
//				write.write("����ʱ��");
//				write.write("	");
//				write.write("������̬��ʼ");
//				write.write("	");
//			
//				write.write("A��");
//				write.write("	");
//				write.write("B��");
//				write.write("	");
//				write.write("C��");
//				write.write("	");
//				write.write("D��");
//				write.write("	");
//				write.write("����ʱ��");
//				write.write("	");
//				write.write("ָ���ǵ�");
//				write.write("	");
//			
//				write.write("ӯ��");
//				write.write("	");
			
				
				write.close();
			}catch(Exception ee){
				ee.printStackTrace();
			}
		//���·ֱ����3��ָ��������
		try{	

			FileReader filereader=new FileReader(hushengZhiShuPath);//������Խ��ļ�·�����ȫ�ֱ���
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
				pricehusheng[j][0]=Integer.toString(j);//0Ϊ���
				pricehusheng[j][1]=lineaftersplit[0];//1Ϊ����
				pricehusheng[j][2]=lineaftersplit[1];//2Ϊ����
				pricehusheng[j][3]=lineaftersplit[2];//3Ϊ�߼�
				pricehusheng[j][4]=lineaftersplit[3];//4Ϊ�ͼ�
				pricehusheng[j][5]=lineaftersplit[4];//5Ϊ����
				pricehusheng[j][6]=lineaftersplit[5];//6Ϊ��
				
				System.out.println(pricehusheng[j][5]);
				j++;
			}
		
			reader.close();
			filereader.close();
		}
		catch(Exception ee) {ee.printStackTrace();}
		
		try{	

			FileReader filereader=new FileReader(zhongxiaoZhiShuPath);//������Խ��ļ�·�����ȫ�ֱ���
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

			FileReader filereader=new FileReader(chuangyeZhiShuPath);//������Խ��ļ�·�����ȫ�ֱ���
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
//���� ����ָ�������ݶ�����ϣ��ֱ���뵽��pricehusheng pricechuangye pricezhongxiao ����������
		
		for(i=0;i<sname.length;i++)	{//һ����Ʊ�ļ�һ����Ʊ�ļ��ض��룬 �������Ժ����б������㣬Ȼ������һ����Ʊ�ļ�
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
				//System.out.println("��ʼ��ȡ��Ʊ����"+ line);
				while((line=reader.readLine())!=null){//ѭ������Ʊ�ļ������ݶ���price������
					System.out.println("��ʼ��ȡ��Ʊ����"+ line);
					lineaftersplit=line.split("	");
					if(lineaftersplit.length<2) break;
					if(lineaftersplit[1]==lineaftersplit[2]&&lineaftersplit[2]==lineaftersplit[3]&&lineaftersplit[3]==lineaftersplit[4]&&lineaftersplit[5]=="0") break;
					lineaftersplit[0]=lineaftersplit[0].replace("/","");
					price[j][0]=Integer.toString(j);//0Ϊ���
					price[j][1]=lineaftersplit[0];//1Ϊ����
					price[j][2]=lineaftersplit[1];//2Ϊ����
					price[j][3]=lineaftersplit[2];//3Ϊ�߼�
					price[j][4]=lineaftersplit[3];//4Ϊ�ͼ�
					price[j][5]=lineaftersplit[4];//5Ϊ����
					price[j][6]=lineaftersplit[5];//6Ϊ��
					System.out.println("��Ʊ���ݶ�ȡ���");
//						System.out.println(price[j][6]);
					j++;
				}//���ˣ���Ʊ�ļ������ݶ�ȡ���
				
				reader.close();
				filereader.close();
			}
				
			catch(Exception ee) {ee.printStackTrace();}
			
			for(j=10;price[j+10][0]!=null;j++){//�͵�Ķ���Ϊ���ڻ����ǰ��10��k�ߵ���͵�
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
								lowt.add(Float.valueOf(price[j][0]));//hight���ڴ�Ÿߵ�λ�ã���k�߸�����ʾ
								//ֻ��k�ߵĸ����������ڼ���k�ߣ�����lowt�б���
						//		System.out.prjntln(date[i]);
						//		System.out.println();
							}
			}
						
			for(j=1;j<lowt.size();j++){//���͵�֮�����k�߸���С��3ʱ����Ҫǰһ���ߵ㣬���ڷ�ֹ���ֵ����ǣ����ڶ���ߵ�����
				if(lowt.get(j)-lowt.get(j-1)<=low_k_min_value){//�͵�֮��k�߸���֮�������޶ȿ������ó�ȫ�ֱ���
					lowt.remove(j-1);
					j--;
				}
			}
		
			for(j=10;price[j+10][1]!=null;j++){//�ߵ�Ķ���Ϊ���ڻ����ǰ��10��k�ߵ���ߵ�
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
					hight.add(Float.valueOf(price[j][0]));//hight���ڴ�Ÿߵ�λ�ã���k�߸�����ʾ
					    //ֻ��k�ߵĸ����������ڼ���k�ߣ�����lowt�б���
				}
			}
						
			for(j=1;j<hight.size();j++){//���ߵ�֮�����k�߸���С��3ʱ����Ҫǰһ���ߵ㣬���ڷ�ֹ���ֵ����ǣ����ڶ���ߵ�����
				if(hight.get(j)-hight.get(j-1)<=high_k_min_value){//�ߵ�֮��k�߸���֮�������޶ȿ������ó�ȫ�ֱ���
					hight.remove(j-1);
					j--;
				}
			}
				
			for(j=0;j<hight.size()-1;j++){//��������̬
				//��ѭ��ȡǰ�������ߵ�a �� c �Լ��������k�߸��� ��������
				a=Float.valueOf(price[hight.get(j).intValue()][5]);
				at=Float.valueOf(price[hight.get(j).intValue()][0]);
				c=Float.valueOf(price[hight.get(j+1).intValue()][5]);//hight�����е���һ����  ��Ϊ��һ���ߵ�
				ct=Float.valueOf(price[hight.get(j+1).intValue()][0]);
				
				if(c<a) {
				System.out.println("c�����̼۵���a��");
				continue;
				}
				if(ct-at>AC_Dis) {
					System.out.println("A��C֮��ľ���>"+AC_Dis+"��k��");
					continue;//��̬������A��C֮��ľ���<=ac��
				}
				//b��ȡΪa������k�ߴ�����һ��k�� ע����price�����е���һ��  ����hight�б��е�	
				b=Float.valueOf(price[hight.get(j).intValue()+1][5]);
				bt=Float.valueOf(price[hight.get(j).intValue()+1][0]);//b��k�߸���
				//bmark=Float.valueOf(price[hight.get(j).intValue()+1][4]);
				for(k=(int)bt;k<ct;k++){//���ac֮�����͵�b  ����ò���޷���֤b������10��������С��
						
					if(Float.valueOf(price[k][5])<b){
						b=Float.valueOf(price[k][5]);
						bt=k;
						//bmark=Float.valueOf(price[k][4]);
					}
					else if(Float.valueOf(price[k][5])==b){//����ȳ��ֶ���͵�
						if(k-bt <=5){//���5��������Ϊȫ�ֱ���
							//�����ֶ����͵�ʱ�����������<=5����ȡ����һ��Ϊ�͵�
							b = Float.valueOf(price[k][5]);
							bt=k;
						}
					}
						
				}//�����ҵ�b�����ڵ�k�ߺ�b������̼�
				
				//��AB֮��ļ۸�������1+abdiff �� pass
				if(Float.valueOf(price[(int)at][5])<(1+abdiff)*Float.valueOf(price[(int)bt][5]))
				{
					System.out.println("============================a��ʱ�䣺" + price[(int)at][1]);
					System.out.println("============================b��ʱ�䣺" + price[(int)bt][1]);
					System.out.println("============================�������������� A>1.2B");
					continue;//������������A>1.2B��������Ļ��������������A
				}
				
				
				
				//���˿�ȷ��d��	
				dt=ct+(bt-at);
				System.out.println("dt:" + dt +"at:"+at+"bt:"+bt+"ct:"+ct);
				System.out.println("price�ĳ��ȣ�"+ price.length);
				System.out.println("d:" + price[73][5]);
				//d=Float.valueOf(price[(int)dt][5]);//d��ȷ�����
				if(price[(int)dt][0] != null) {
					d=Float.valueOf(price[(int)dt][5]);//d��ȷ�����
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
							System.out.println("m��k�߼�һ");
							buyMark=1;//���Ϊ����
							mt=exactBuy;
							m=Float.valueOf(price[(int)exactBuy][5]);//���̼�
							System.out.println("mt:" + mt);
						}
						else
						{
							exactBuy=exactBuy+1;
						}
					}
				}
					
				else break;//���ABC�����ɣ���Dδ���ɣ�û���γ�һ��������̬������һ����Ʊ�����
					//����Ϊ��������̬abcd���Ӧ��ָ����
				if(sname[i].substring(0, 5).compareTo("SH600")==0||sname[i].substring(0, 5).compareTo("SZ000")==0){//�����Ʊ�Ĵ�����SH600����SZ000��ͷ
					for(k=(int)at;pricehusheng[k][1]!=null;k++){
							
						if(price[(int)at][1].compareTo(pricehusheng[k][1])==0){//���������Ƚ� ȷ��a��Ĵ���ָ��
							za=Float.valueOf(pricehusheng[k][5]);
							za_date = pricehusheng[k][1];
							continue;
						}
						
						if(price[(int)bt][1].compareTo(pricehusheng[k][1])==0){//���������Ƚ� ȷ��b��Ĵ���ָ��
							zb=Float.valueOf(pricehusheng[k][5]);
							zb_date = pricehusheng[k][1];
							continue;
								
						}
						if(price[(int)ct][1].compareTo(pricehusheng[k][1])==0){//���������Ƚ� ȷ��c��Ĵ���ָ��
							zc=Float.valueOf(pricehusheng[k][5]);
							zc_date = pricehusheng[k][1];
							continue;
						}
							
						if(price[(int)dt][1].compareTo(pricehusheng[k][1])==0){//���������Ƚ� ȷ��d��Ĵ���ָ��
							zd=Float.valueOf(pricehusheng[k][5]);
							zd_date = pricehusheng[k][1];
							continue;
						}
						if(price[(int)mt][1].compareTo(pricehusheng[k][1])==0){//���������Ƚ� ȷ��d��Ĵ���ָ��
							zm=Float.valueOf(pricehusheng[k][5]);//****
							zm_date = pricehusheng[k][1];//***
							break;//***
						}
					}
					System.out.println("a��ָ����"+za + "a��ָ�����ڣ�"+za_date+"a�����ڣ�"+price[(int)at][1]);
					System.out.println("b��ָ����"+zb + "b��ָ�����ڣ�"+zb_date+"b�����ڣ�"+price[(int)bt][1]);
					System.out.println("c��ָ����"+zc + "c��ָ�����ڣ�"+zc_date+"c�����ڣ�"+price[(int)ct][1]);
					System.out.println("d��ָ����"+zd + "d��ָ�����ڣ�"+zd_date+"d�����ڣ�"+price[(int)dt][1]);
					System.out.println("m��ָ����"+zm + "m��ָ�����ڣ�"+zm_date+"m�����ڣ�"+price[(int)mt][1]);
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
				//if((d-b)<=(zd-zb)) continue;//��abc��bcd����һ������ָ�����������������̬
				//�����ָ�����бȽ�
				if(((c-a)/a)<=((zc-za)/za)) {
					System.out.println("a:" + a + "b:" + b +"c:"+ c + "d:" + d + "m:"+ m + "za:"+za+"zb:" + zb +"zc:" + zc+"zd:"+zd+"zm:"+zm);
					System.out.println("a��ʱ�䣺" + price[(int)at][1]);
					System.out.println("b��ʱ�䣺" + price[(int)bt][1]);
					System.out.println("c��ʱ�䣺" + price[(int)ct][1]);
					System.out.println("d��ʱ�䣺" + price[(int)dt][1]);
					System.out.println("m��ʱ�䣺" + price[(int)mt][1]);
					System.out.println("abc���ڴ��̣�A��ʱ�䣺" + price[hight.get(j).intValue()][1]+"--or"+ price[(int)at][1] + "C��ʱ�䣺" + price[hight.get(j).intValue()][1]);
					continue;
				}
				if(((d-b)/b)<=((zd-zb)/zb)) {
					System.out.println("a:" + a + "b:" + b +"c:"+ c + "d:" + d + "m:"+ m + "za:"+za+"zb:" + zb +"zc:" + zc+"zd:"+zd+"zm:"+zm);
					System.out.println("a��ʱ�䣺" + price[(int)at][1]);
					System.out.println("b��ʱ�䣺" + price[(int)bt][1]);
					System.out.println("c��ʱ�䣺" + price[(int)ct][1]);
					System.out.println("d��ʱ�䣺" + price[(int)dt][1]);
					System.out.println("m��ʱ�䣺" + price[(int)mt][1]);
					System.out.println("bcd���ڴ��̣�b��ʱ�䣺" + price[(int)bt][1] + "C��ʱ�䣺" + price[(int)ct][1]);//////�ĳɵ͵�ʱ��
					continue;//��abc��bcd����һ������ָ�����������������̬
				}
				if(((m-b)/b)<=((zm-zb)/zb)) {
					System.out.println("a:" + a + "b:" + b +"c:"+ c + "d:" + d + "m:"+ m + "za:"+za+"zb:" + zb +"zc:" + zc+"zd:"+zd+"zm:"+zm);
					System.out.println("a��ʱ�䣺" + price[(int)at][1]);
					System.out.println("b��ʱ�䣺" + price[(int)bt][1]);
					System.out.println("c��ʱ�䣺" + price[(int)ct][1]);
					System.out.println("d��ʱ�䣺" + price[(int)dt][1]);
					System.out.println("m��ʱ�䣺" + price[(int)mt][1]);
					System.out.println("bcm���ڴ���");
					continue;//��bcm����ָ�����������������̬
				}
				//�����������������ˣ���ʣ�ºϸ���ˣ���������Ժϸ��Ʊ���д���
				//������������ָ�����бȽ�
				String[] rs=new String[14];
				rs[0]=price[(int)at][5];
				rs[1]=price[(int)bt][5];
				rs[2]=price[(int)ct][5];
				rs[3]=price[(int)dt][5];
				rs[4]=price[(int)at][1];
				rs[5]=price[(int)dt][1];
				rs[6]=Integer.toString((int)dt);//0Ϊa�� 1Ϊb�� 2Ϊc�� 3Ϊd�� 4Ϊa���� 5Ϊd���� 6Ϊd���
				rs[7]=""+(c-a)/a+",,,"+(zc-za)/za;//7Ϊabc�ڼ䣬��Ʊ�Ƿ��ʹ����Ƿ�
				rs[8]=""+(d-b)/b+",,,"+(zd-zb)/zb;//8Ϊbcd�ڼ䣬��Ʊ�Ƿ��ʹ����Ƿ�
				rs[9]=price[(int)bt][1];//9Ϊb����
				rs[10]=price[(int)ct][1];//10Ϊc����
				rs[11]=price[(int)mt][1];//11Ϊm������
			    //	System.out.println(rs[11]);
				rs[12]=price[(int)mt][5];//12Ϊm���̼�
				rs[13]=""+(m-b)/b+",,,"+(zm-zb)/zb;//13Ϊbcm�׶Σ���Ʊ�Ƿ��ʹ����Ƿ�
				if(rs[11].compareTo("20130703")<0 ){/////7-04 ���ʱ������  ����4��30�ţ�С��7��3��
					if( rs[11].compareTo("20130330") > 0 ){
						buy.add(rs);//�洢������̬
						System.out.println("����������̬");
					}
				}
					

					
			}
				//��������������̬
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
//				for(k=(int)bt;k<ct;k++){//ȡ��ac֮�����ߵ�b  
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
//				if((c-a)>(zc-za)&&(d-b)>(zd-zb)) continue;//��abc��bcd��һ������ָ������������
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
//			}//������̬Ѱ�����
			
			for(j=0;j<buy.size();j++){//����ʱ���ʽ
//				buy.get(j)[4]=buy.get(j)[4].substring(0, 4)+"-"+buy.get(j)[4].substring(4,6)+"-"+buy.get(j)[4].substring(6,8);
//				buy.get(j)[5]=buy.get(j)[5].substring(0, 4)+"-"+buy.get(j)[5].substring(4,6)+"-"+buy.get(j)[5].substring(6,8);
				buy.get(j)[4]=buy.get(j)[4].substring(0, 4)+"-"+buy.get(j)[4].substring(4,6)+"-"+buy.get(j)[4].substring(6,8);//a����
				buy.get(j)[5]=buy.get(j)[5].substring(0, 4)+"-"+buy.get(j)[5].substring(4,6)+"-"+buy.get(j)[5].substring(6,8);//d����
				buy.get(j)[9]=buy.get(j)[9].substring(0, 4)+"-"+buy.get(j)[9].substring(4,6)+"-"+buy.get(j)[9].substring(6,8);//b����
				buy.get(j)[10]=buy.get(j)[10].substring(0, 4)+"-"+buy.get(j)[10].substring(4,6)+"-"+buy.get(j)[10].substring(6,8);//c����
				buy.get(j)[11]=buy.get(j)[11].substring(0, 4)+"-"+buy.get(j)[11].substring(4,6)+"-"+buy.get(j)[11].substring(6,8);//m����
			}
			
//			for(j=0;j<sell.size();j++){
//				sell.get(j)[4]=sell.get(j)[4].substring(0,4)+"-"+sell.get(j)[4].substring(4,6)+"-"+sell.get(j)[4].substring(6,8);
//				sell.get(j)[5]=sell.get(j)[5].substring(0,4)+"-"+sell.get(j)[5].substring(4,6)+"-"+sell.get(j)[5].substring(6,8);
//
//			}
			
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
//					for(k=0;k<=sell.size()-1;k++){//����ʱ�������ʱ����бȽ� ���
//						if(sell.get(k)[5].compareTo(buy.get(j)[5])>0){
//							sellmark=k;
//			//				System.out.println(sellmark);
//							break;
//						}
//					}
			//		System.out.println(sellmark);
					
					
					
					k=(int) sellmark;
					//����������״̬�����ж� �Ӷ��ó��Ǹ����뻹������
//					if(k==0) { //k=0����ֻ���룬δ��������������   ������̬û���γ�    ���Ľ����ֹӯ���� �� ֹ������
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
//								write.write("ֹӯ����");
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
//								write.write("ֹ������");
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
//					if (k!=0) {//�����룬Ҳ������
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
//								write.write("ֹӯ����");
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
//								write.write("ֹ������");
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
//					}////������k!= 0    �����룬Ҳ������  ���������Ͻ���Ϣд���ļ���
					//����д�� ���������������Ϣ
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
//					write.write("ָ���ǵ�");
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
		
		
		


