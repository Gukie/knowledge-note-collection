package com.lokia.demo.generator;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import com.lokia.demo.util.StringUtils;

/**
 * @author gushu
 * @date 2018/02/08
 */
public class StringGenerator {

	
	private int stringNum;
	
	private String savedFileName;
	
	private String savedFileDir;
	
	public StringGenerator(int stringNum,String fileDir,String fileName) {
		setStringNum(stringNum);
		setSavedFileDir(fileDir);
		setSavedFileName(fileName);
	}

	public int getStringNum() {
		return stringNum;
	}

	public void setStringNum(int stringNum) {
		this.stringNum = stringNum;
	}

	
	public String getSavedFileName() {
		return savedFileName;
	}

	public void setSavedFileName(String savedFileName) {
		this.savedFileName = savedFileName;
	}

	public String getSavedFileDir() {
		return savedFileDir;
	}

	public void setSavedFileDir(String savedFileDir) {
		this.savedFileDir = savedFileDir;
	}

	public void start() {
		
		OutputStream outputStream = getOutStream();
		for(int i = 0;i<stringNum;i++){
			String item = generateStrItem();
			try {
				outputStream.write(item.getBytes());
				outputStream.write("\n".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	private String generateStrItem() {
		char[] chars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q'
				,'r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0'};
		char[] tmpChar = new char[8];
		String string = null;
		Random random = new Random();
		for(int i = 0;i<tmpChar.length;i++){
			int charsIndex = random.nextInt(chars.length);
			tmpChar[i] = chars[charsIndex];
			string = new String(tmpChar);
			
		}
		return string;
	}

	private OutputStream getOutStream() {
		if(StringUtils.isBlank(savedFileName)){
			return System.out;
		}
		try {
			Path path = FileSystems.getDefault().getPath(savedFileDir, savedFileName);
			OutputStream  outStream = Files.newOutputStream(path);
			return outStream;
		} catch ( IOException e) {
			e.printStackTrace();
		}
		return System.out;
	}
	
	
	public static void main(String[] args) {
		
		StringGenerator generator = new StringGenerator(100, "D:\\tmp", "string.txt");
		generator.start();
		
//		FileSystems.getDefault().getPath("D:\\tmp", "string.txt");
	}
}
