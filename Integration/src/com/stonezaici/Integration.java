package com.stonezaici;

import java.util.Date;

public class Integration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		for(int i = 0; i < 3994; i++){//ѭ�������ļ���  ����i��ȷ���ļ���  ����û����Ӧ�ļ�
			
			//��ʼ������date��Сʱhour
			Date date = new Date();//����      ��һ��   date++ û���
			Date hour = new Date();//Сʱ      ��һ��   hour++ û���
			int flag = 0;//ÿ����5��k�߹���һ��30��k�� flag����ָʾ�Ƿ񵽴�����
			for(int j = 0; j< 800; j++){//��ʼ��ȡ�ļ����� ѭ�� һ��һ�ж�
				//�ȶ�ȡ���� �õ���һ������    �������
				String line = null;
				if(j == 0){//j==0 ����Ʊ���� ����д�������ļ�
				}else if(j==1){//j==1 ����������д�������ļ�
				}else if(j >=2){//���Ĳ���
					if( line =="������Դ:ͨ����") break;//�Ѷ����ļ�β������һ���ļ�
					//������Ϣ��ֳɸ�������
					Date du_date = new Date();//��ȡ������      ��һ��
					Date du_hour = new Date();//��ȡ��Сʱ      ��һ��
//					date = null;//��date��ֵ
//					hour = null;//��hour��ֵ
					String kaipan = null;//���̼�
					String max_value = null;//��߼�
					double max_val = 0.00;
					String min_value = null;//��ͼ�
					double min_val = 0.00;
					String shoupan = null;//���̼�
					String chengjiaoliang = null;//�ɽ���
					double cjl_val = 0.00;
					String chengjiaoe = null;//�ɽ���
					double cje_val = 0.00;
					if(du_date != date){
						flag =0;
						//date��һ��;
						}
					if(date == du_date){
						if(hour != du_hour){
							//hour��5���� ���ʱ��Ϊ1125 hourֱ���趨Ϊ1300
							if(flag < 5) flag++;
							if(flag >= 5) flag = 0;
						}
						if(hour == du_hour){
							if(max_val < Double.valueOf(max_value)){
								max_val = Double.valueOf(max_value);//��30��k�ߵ���ߵ�
							}
							if(min_val > Double.valueOf(min_value)){
								min_val = Double.valueOf(min_value);//��30��k�ߵ���͵�
							}
							cjl_val += Double.valueOf(chengjiaoliang);//��ɽ�����
							cje_val += Double.valueOf(chengjiaoe);//��ɽ���
							if(flag == 0){
								//��һ��k��  ���̼۶���
							}
							if(flag == 5){
								//������k��  ���̼۶���
								//������  ʱ��  ���� ��� ��� ����  �ɽ���  �ɽ��� д�뵽���ļ�  
								//�ļ����Դ��� + �ļ����ĸ�ʽ
								flag = 0;//���ػ���㣬������һ��5��k�ߵ�30��k�ߵĺϳ�
							}
							//hour������� ���ʱ��Ϊ1125 hourֱ���趨Ϊ1300
							flag++;
						}
						//date��һ��
					}
				}
			}
		}
	}

}
