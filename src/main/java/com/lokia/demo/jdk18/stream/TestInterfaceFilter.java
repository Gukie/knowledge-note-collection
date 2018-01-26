package com.lokia.demo.jdk18.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSONArray;

/**
 * @author gushu
 * @date 2018/01/25
 */
public class TestInterfaceFilter {

	public static void main(String[] args) {

		filterCampusHsfInterface();
	}

	
	public static void filterCampusHsfInterface() {

		URL url;
		try {
			url = new URL("http://xxx.xxx.com/hsfConfigInfo.json");
			InputStream stream = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuilder data = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				data.append(line);
			}
			System.err.println(data.toString());
			List<HsfInterfaceInfo> hsfInterfaceInfos = JSONArray.parseArray(data.toString(), HsfInterfaceInfo.class);
			if(hsfInterfaceInfos==null || hsfInterfaceInfos.isEmpty()){
				System.err.println("no hsf interafce data");
				return;
			}
			Stream<HsfInterfaceInfo> stream3 = hsfInterfaceInfos.stream().filter(item->item.service.startsWith("com.xxx.xxx.xxx.remote.workbench"));
			AtomicInteger atomicIntegerSize = new AtomicInteger();
			
			IncreamentCls increamentCls = new IncreamentCls();
			int size = 0;
			stream3.forEach((item)->{
//				System.out.println(item.key+"-"+item.service+"-"+item.method);
				atomicIntegerSize.incrementAndGet();
				increamentCls.increase();
//				size++; // this is not allowed,since the raw type is not allowed to do basic operator.
			}
			);
			System.err.println("matched data size 1:"+increamentCls.getSize());
			System.err.println("matched data size 2:"+atomicIntegerSize.get());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	static class IncreamentCls{
		int size;
		
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		
		public void increase() {
			size++;
		}
	}
	
static	class HsfInterfaceInfo{
		private String key;
		private String method;
		private String name;
		private String service;
		private List<String> type;
		private String url;
		private String version;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getService() {
			return service;
		}
		public void setService(String service) {
			this.service = service;
		}
		public List<String> getType() {
			return type;
		}
		public void setType(List<String> type) {
			this.type = type;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
	}
	

}
