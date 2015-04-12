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
		//�������ڴ洢���ֵ��list  һ������11��Ԫ�أ�����Ѱ�Ҹߵ�
		ArrayList list = new ArrayList();
		ArrayList date = new ArrayList();//����ʱ������  ��֤ �������˳�򲻷�������
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
		double buyprice = 0.0;//����۸�
		double saleprice = 0.0;//�����۸�
		double jiaoyi_count = 0.0;//���״���
		double success_count = 0.0;//ӯ������
		double yinglv = 0.0;//ӯ��
		double shenglv = 0.0;//ʤ��
		boolean buy = false;//��ʶ��Ʊ��û����
		boolean A_point_exist = false;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"GBK");
			BufferedReader bf = new BufferedReader(isr);
			while((line = bf.readLine()) != null){
				flag = 0;//A���־λ   ����־λ����
				flag2 = 0;//D���־λ
				hang++;//���� ��
				if(hang ==1&& line != null){//ȡ�ù�Ʊ���ֺʹ���
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
					//System.out.println("���ֵ��" + max_value);
					list.remove(0); //ѭ��ȡ�� ���+-5������   ����+-3������ ֱ����list��������߸����ݾͿ�����
					date.remove(0);
					list.add(max_value); 
					date.add(line.split("	  ")[0]);//����
					for(int i = 0; i < 5; i++){//��ǰ���k�߱Ƚ�
						if((double)list.get(5) > (double)list.get(i)){
							flag++;
							//System.out.println("ǰ5flag:" + flag);
						}
					}
					for(int j = 6; j< 11; j++){//������k�߱Ƚ�
						if((double)list.get(5) > (double)list.get(j)){
							flag++;
							//System.out.println("��5flag:" + flag);
						}
					}
					if(flag == 10 && buy == false){//�ҵ�Apoint
						A_point = (double)list.get(5);
						A_point_exist = true;
						//System.out.println("A��" + A_point + "�����У�" + hang );
						lossprice = A_point * 0.95;
					}
					if(max_value > A_point && buy == false && (double)list.get(0)!=0.0 && A_point_exist == true){//�ҵ�C��
						C_point = max_value;
						C_point_date = line.split("	  ")[0];
						//System.out.println("C���ҵ���" + C_point + "����"+ "�����У�" + hang);
						buy = true;
						buyprice = Double.valueOf(((String)line.split("	  ")[4]));
						System.out.println("����۸�" + buyprice);
					}
					if(max_value < lossprice && buy == true ){
						//System.out.println("ֹ����ҵ���" + max_value +"ֹ������"+ "�����У�" + hang);
						buy = false;
						A_point_exist = false;
						//���һ�ν���
						saleprice = Double.valueOf((String)line.split("	  ")[4]);
						System.out.println("�����ۣ�" + saleprice);
						jiaoyi_count++;
						yinglv = buyprice/saleprice -1;
						if((buyprice - saleprice)>0){//���ӯ��  ӯ��������һ
							success_count++;
						}
						System.out.println("ӯ�ʣ�" + yinglv);
					}
					//��D��
					for(int i = 4;i < 7;i++){//��ǰ����k�߱Ƚ�
						if((double)list.get(7) < (double)list.get(i)){
							flag2++;
						}
					}
					for(int j = 8; j < 11; j++){//�������k�߱Ƚ�
						
						if((double)list.get(7) < (double)list.get(j) ){
							flag2++;
						}
					}
					
					if(flag2 == 6 && buy == true && ((String) date.get(7)).compareTo(C_point_date)>0){//�豣֤D�������Ҫ��C��֮��
						D_point = (double)list.get(7);
						//System.out.println("D��"+ D_point+ "�����У�" + hang);
						if(D_point > A_point){
							lossprice = D_point;
							//System.out.println("�����ƽ�ֹ��۸�" + D_point+ "�����У�" + hang);
						}
					}
					if(max_value < lossprice && buy == true){
						//System.out.println("ֹ����ҵ���" + max_value +"ֹ������"+ "�����У�" + hang);
						buy = false;
						A_point_exist = false;
						//���һ�ν���
						saleprice = Double.valueOf((String)line.split("	  ")[4]);
						System.out.println("�����ۣ�" + saleprice);
						jiaoyi_count++;
						yinglv = buyprice/saleprice -1;
						if((buyprice - saleprice)>0){//���ӯ��  ӯ��������һ
							success_count++;
						}
						System.out.println("ӯ�ʣ�" + yinglv);
					}
					
				}////hang > 5 �����������
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
		System.out.println("�ܽ��״�����" + jiaoyi_count);
		System.out.println("ӯ��������" + success_count);
		shenglv = success_count/jiaoyi_count;
		System.out.println("ʤ�ʣ�" + shenglv );
	}

}
