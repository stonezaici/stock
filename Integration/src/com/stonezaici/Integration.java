package com.stonezaici;

import java.util.Date;

public class Integration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		for(int i = 0; i < 3994; i++){//循环搜索文件名  利用i来确定文件名  假如没有相应文件
			
			//初始化日期date和小时hour
			Date date = new Date();//日期      查一下   date++ 没完成
			Date hour = new Date();//小时      查一下   hour++ 没完成
			int flag = 0;//每六条5分k线构造一条30分k线 flag用来指示是否到达六条
			for(int j = 0; j< 800; j++){//开始读取文件内容 循环 一行一行读
				//先读取内容 得到那一行数据    不拆分行
				String line = null;
				if(j == 0){//j==0 将股票代码 名称写入最终文件
				}else if(j==1){//j==1 将名称属性写入最终文件
				}else if(j >=2){//核心部分
					if( line =="数据来源:通达信") break;//已读到文件尾，换下一个文件
					//将行信息拆分成各个属性
					Date du_date = new Date();//读取的日期      查一下
					Date du_hour = new Date();//读取的小时      查一下
//					date = null;//对date赋值
//					hour = null;//对hour赋值
					String kaipan = null;//开盘价
					String max_value = null;//最高价
					double max_val = 0.00;
					String min_value = null;//最低价
					double min_val = 0.00;
					String shoupan = null;//收盘价
					String chengjiaoliang = null;//成交量
					double cjl_val = 0.00;
					String chengjiaoe = null;//成交额
					double cje_val = 0.00;
					if(du_date != date){
						flag =0;
						//date加一天;
						}
					if(date == du_date){
						if(hour != du_hour){
							//hour加5分钟 如果时间为1125 hour直接设定为1300
							if(flag < 5) flag++;
							if(flag >= 5) flag = 0;
						}
						if(hour == du_hour){
							if(max_val < Double.valueOf(max_value)){
								max_val = Double.valueOf(max_value);//求30分k线的最高点
							}
							if(min_val > Double.valueOf(min_value)){
								min_val = Double.valueOf(min_value);//求30分k线的最低点
							}
							cjl_val += Double.valueOf(chengjiaoliang);//求成交量和
							cje_val += Double.valueOf(chengjiaoe);//求成交额
							if(flag == 0){
								//第一条k线  开盘价定了
							}
							if(flag == 5){
								//第六条k线  闭盘价定了
								//将日期  时间  开盘 最高 最低 闭盘  成交量  成交额 写入到新文件  
								//文件名以代码 + 文件名的格式
								flag = 0;//又重回零点，进行下一次5分k线到30分k线的合成
							}
							//hour加五分钟 如果时间为1125 hour直接设定为1300
							flag++;
						}
						//date加一天
					}
				}
			}
		}
	}

}
