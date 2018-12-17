package com.homework.translater;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Translater implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map<String,String> translate = new HashMap<>();
	public Translater(Map<String, String> translate) {
		super();
		this.translate = translate;
	}
	public Translater() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Map<String,String> dictionary (File dic) throws FileNotFoundException {

		String r = "";
		FileInputStream file = new FileInputStream(dic);
		try (BufferedReader buf = new BufferedReader(new InputStreamReader(file,"UTF-8"))) {
			while ((r=buf.readLine()) != null) {
				String[] out = r.split(" ");
				String eng = "";
				String ukr = "";			
				for (String string : out) {
					if (string.matches("to")) {
						eng += " " + string;
					} else 
					if (string.matches("^[a-zA-Z]+")) {
						eng += string;
					} else if (string.matches("[à-ÿÀ-ß]+")){
						ukr +=string;
					}
					

				}
				if (!translate.containsKey(eng)) {
				translate.put(eng, ukr);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return translate;
	}
	public String translateThis (File dic, File text) throws FileNotFoundException{
		Map<String,String> dictionaryMap = dictionary(dic);
		String translated = "";
		String r = "";		
		try (BufferedReader buf = new BufferedReader(new FileReader(text))) {
			while((r = buf.readLine()) != null) {		
				String[] out = r.split(" ");
				for (int i=0;i<out.length;i++) {
					String temp = "";
					if (out[i].matches("to")) {
						temp +=out[i-1] + " " + out[i];
					} else {
						temp +=out[i];
					}
					if (dictionaryMap.containsKey(temp.toLowerCase())) {
						translated += dictionaryMap.get(temp.toLowerCase()) + " ";
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		translated = translated.substring(0,1).toUpperCase() + translated.substring(1,translated.length());
		return translated;
	}
	public void save (String text, File output) throws IOException {
		try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8)) {
			writer.write(text);
			writer.flush();
			writer.close();
		}
	}
	public void addToDictionary (String eng,String ukr,File dic) throws IOException {
		if (!translate.containsKey(eng)) {
			translate.put(eng, ukr);
			dicSave(dic);
		}
		
	}
	private void dicSave (File dic) throws FileNotFoundException, IOException {
		try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(dic), StandardCharsets.UTF_8)) {
		for (Entry<String, String> element : translate.entrySet()) {
			String text = element.getKey() + " " + element.getValue();
				writer.write(text + "\n");
			}
		writer.flush();
		writer.close();
		}
	}
	public void saveDic() throws FileNotFoundException, IOException{
		File dic = new File("c:\\Users\\white\\eclipse-workspace\\prog.kiev.ua\\src\\com\\homework\\translater\\Dictionary.dic");
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(dic))) {
			writer.writeObject(translate);
		}
	}
	
} 
