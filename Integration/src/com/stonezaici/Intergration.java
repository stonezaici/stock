package com.stonezaici;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Intergration {

	/**
	 * @param args
	 */
	public static File newfile = null;
	public static FileWriter fw = null;
	public static String newfilename;
	public static void main(String[] args) {
		for(int i = 600661 ; i < 600662; i++){
			String filename = "D://Program Files (x86)/T0002/export/SH";
			String encoding="GBK";
			String prefix = filename + String.valueOf(i) ;
			filename = prefix +".TXT";
			//System.out.println(filename);
			File file = new File(filename);
			if(file.exists()){
				InputStreamReader read;
				try {
					read = new InputStreamReader(new FileInputStream(file),encoding);
					BufferedReader bufferedReader = new BufferedReader(read);
					String line = null;
					int hang = 0;
					while(( line = bufferedReader.readLine()) != null){
						
						if(hang == 0){
							//System.out.println("第一行的" + line);
							 newfilename = prefix + line.split(" ")[1];
							 newfile = new File(newfilename + ".TXT");
							 fw = new FileWriter(newfilename + ".TXT");
							 fw.write(line);
							 fw.close();
//							 System.out.println("里面的内容" + line);
							System.out.println(newfilename);
							hang++;
							continue;
						}
						if(hang == 1){
							System.out.println("里面的内容" + line);
							fw = new FileWriter(newfilename + ".TXT");
							fw.write(line);
							hang++;
							continue;
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
			}
			
			
		}
	}

}
