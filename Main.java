package com.homework.translater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		File fileIn = new File ("c:\\Users\\white\\eclipse-workspace\\prog.kiev.ua\\src\\com\\homework\\translater\\English.in");
		File dic = new File("c:\\Users\\white\\eclipse-workspace\\prog.kiev.ua\\src\\com\\homework\\translater\\Dictionary.txt");
		File fileOut = new File ("c:\\Users\\white\\eclipse-workspace\\prog.kiev.ua\\src\\com\\homework\\translater\\Ukrainian.out");
		Translater translate = new Translater();
		String translated = "";
		try {
			translated = translate.translateThis(dic, fileIn);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			translate.save(translated, fileOut);
			translate.addToDictionary("max", "максимально", dic);
			translate.saveDic();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
