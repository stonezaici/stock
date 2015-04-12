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
		
		
		String[][] bigist=new String[3000][25];//�����洢��������
		
		int k=0;
		int i=0;
		ArrayList<String> name=new ArrayList<String>();
		int[] jishu=new int[100];
		float[] pingjunyingli=new float[100];
		
		try {
			filereader = new FileReader("D:\\Internal\\6-24\\�����������\\aaa.txt");
		} catch (FileNotFoundException ee) {
				// TODO Auto-generated catch block
			ee.printStackTrace();
		}
		BufferedReader reader=new BufferedReader(filereader);
		try{
			while((line=reader.readLine())!=null){
				
				if(line.contains("��Ʊ����")) continue;
				if(line=="") continue;
				if(line.contains("			")) continue;
				
				lineafter=line.split("	");
				bigist[i][0]=lineafter[0];//����
				bigist[i][1]=lineafter[8];//����ʱ��
				bigist[i][2]=lineafter[14];//����ʱ��
				bigist[i][3]=lineafter[16];//ӯ��
				if(!name.contains(bigist[i][0])){name.add(bigist[i][0]);}//���name arraylist�в��������룬�򽫴�����뵽name arraylist��
				for(j=0;j<name.size();j++){
					if(bigist[i][0].compareTo(name.get(j))==0){
						jishu[j]++;//��¼ÿֻ��Ʊ���׵Ĵ���
						pingjunyingli[j]=pingjunyingli[j]+Float.valueOf(bigist[i][3]);//��¼�ܹ��Ľ��׶�
					}
					
				}
				i++;
			}
		
		
		}catch (Exception ee) {
			// TODO Auto-generated catch block
		ee.printStackTrace();
		}
		
		for(i=0;i<name.size();i++){
			pingjunyingli[i]=pingjunyingli[i]/jishu[i];//����ƽ��ӯ��  ���׶�/���״���
			System.out.println(name.get(i));
			System.out.println(pingjunyingli[i]);
		}
		
		//������ʱ����������bigist����  ����������ʱ�������Ͻ����й�Ʊ��ÿ�ʽ������п���
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
					String[][][] zhanghu=new String[500][500][5];//�˻�����   ÿ�����Ե����壿����������������������
					String[][] list=new String[3000][25];//  ÿ�����Ե����壿��������������������������������������
					j=0;
					for(i=0;bigist[i][0]!=null;i++){
						if(bigist[i][0].contains(name.get(x1))||bigist[i][0].contains(name.get(x2))||bigist[i][0].contains(name.get(x3))){
							list[j]=bigist[i];//���bigist[]���������ٰ����μ������һ��name�еĹ�Ʊ���룬����֧��Ʊ����Ϣ����������ʱ�䣬ӯ��ʲô�ģ�����list��
							j++;//���õ��μ�����Ĺ�Ʊ����Ϣ
							//���һ����û��ƥ�����أ�����������������������������������������������
						}
					}
					
					
					
					
					zhanghu[0][0]=list[0];
					
					//i�ǵڼ�����Ʊ
					for(i=0;list[i][0]!=null;i++){//dan
					//j�ǵڼ����˻�
						for(j=0;j<500;j++){//zhanghu
							
							//���˻���������һ�ʽ����ǵڼ���
							for(k=0;zhanghu[j][k][0]!=null;k++){///�����˻����飬����һ���м��ʽ���   ��k�����
								//zhanghu[j][k][0]: ��j���˻��ĵ�k�ʽ���
							}
							
							if(k==0) {zhanghu[j][k]=list[i];break;}//����ʲô��˼������������������������������
							
							if(zhanghu[j][k-1][2].compareTo(list[i][1])>=0){//����ʲô���ԵıȽϣ�������������
								continue;
							}
							else {
								zhanghu[j][k]=list[i];
								break;
							}
						}
						
					}
					
					//����ÿ�ʽ��׵ĳֲ�ʱ��
					
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
						
							//ʱ��Ĵ������¿�ܱ�д��
					      //������ʼ���ںͽ���������������
							diff=(et.getTime()-st.getTime())/(24*60*60*1000);
							//System.out.println("���"+diff+"��");
						 //�жϽ���ʱ������ʼʱ��
							int IntStTime=Integer.valueOf(startTime.substring(9,11)+startTime.substring(12,14));//ʱ��ת�������֣���14:00->1400
							int IntEtTime=Integer.valueOf(endTime.substring(9,11)+endTime.substring(12,14));
							double doubleStTime=((double)(IntStTime%100)/60)+(int)IntStTime/100;
							double doubleEtTime=((double)(IntEtTime%100)/60)+(int)IntEtTime/100;
						//����ʱ��>=��ʼʱ��,�������߶�������������������Сʱ����ʱ���ڣ���diff+(����ʱ��-��ʼʱ���Ӧ��Сʱ��)/4,������ǰ�������磬���������磬diff+(����ʱ��-��ʼʱ���Ӧ��Сʱ��-1.5��Сʱ)/4
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
						else //����ʱ��<��ʼʱ��,(����ʱ��-��ʼʱ���Ӧ��Сʱ��)/4,�ͼ���diff-1+(��ʼʱ�����3�����Ϊ����ʱ��εľ���+����ʱ�����9:30�Ľ���ʱ��ʱ��εľ���)/4
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
							write.write("��"+ii+"���˻�");
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
							
							write.write("ƽ������");
							write.write("	");
							write.write(Float.toString(aver));
							write.write("	");
							write.write("ʤ��");
							write.write("	");
							write.write(Float.toString(shenglv));
							write.write("	");
							write.write("����");
							write.write("	");
							write.write(Float.toString(fuli));
							write.write("	");
							write.write("ƽ���ֹ�����");
							write.write("	");
							write.write(Double.toString(holdavg));
							write.write("����Ż�Ч��");
							write.write("	");
							write.write(Float.toString(aver-(pingjunyingli[x1]+pingjunyingli[x2]+pingjunyingli[x3])/3));
							
							write.newLine();
							write.newLine();
						}
						sampleHold=holdtotal/ncount;
						System.out.println("���н���ƽ��ÿ�ʽ��׳ֲ�ʱ��:"+sampleHold);
					
						write.close();
					}catch(Exception ee){
						ee.printStackTrace();
					}
				}
			}
		}
		
	}

	}
