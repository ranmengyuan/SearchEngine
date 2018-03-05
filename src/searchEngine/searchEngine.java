package searchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class searchEngine {
	public static void main(String[] args) throws IOException{
		ExecutorService executor=Executors.newFixedThreadPool(2);//运用多线程提高运行效率
//		executor.execute(new IndexRegion());//获取网页内容，构成倒排索引
		executor.execute(new toSearch());//进行查询的操作
		executor.shutdown();
	}
}
class toSearch implements Runnable{
	public void run(){
		Search search=new Search();
		try {
			search.Output();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("查询失败");
		}
	}
}
 class IndexRegion implements Runnable{//获取网页内容（与获取网页标题的方式相同）
	@SuppressWarnings("static-access")
	public void run(){
		Demo_web demoWeb=new Demo_web();
		demoWeb.Delete();
		for(int layer=0;layer<3;layer++){
			Spider spider=new Spider();
			String address = null;
			Map<String,String> content;
			Map<String,String> title;
			ArrayList<String> URL=new ArrayList<String>();//存放网址
			ArrayList<String> Concept=new ArrayList<String>();//存放分词后的网页内容
			ArrayList<Integer> Rate=new ArrayList<Integer>();//存放词频
			ArrayList<String> URLTitle=new ArrayList<String>();//存放网页题目的网址
			ArrayList<String> ConceptTitle=new ArrayList<String>();//存放网页题目的分词
			ArrayList<Integer> RateTitle=new ArrayList<Integer>();//存放网页题目的词频
//			System.out.println("arry.size"+spider.arry.size());
			if(spider.arry.size()==0){//判断是否是第一层
				address="http://www.cqut.edu.cn/";
				spider.get(address);//抓取网页的内容
				content=new HashMap<String,String>(spider.getConcept());//用来存取抓取的网页内容和词频
				title=new HashMap<String,String>(spider.getTitle());//用来存取网页题目和词频
				spider.getConcept().clear();//使用后，清空抓取的内容
				spider.getTitle().clear();//使用后，清空抓取的题目
				demoWeb.Inter(title);//创建一个表格存网址和题目
				for (String key : content.keySet()) {//将网址，关键词，词频，依次放到三个线性表中
					 IKAnalyzerDemo Depart=new IKAnalyzerDemo();
					  Map<String, Integer> content1;
					try {
						content1 = new  HashMap<String,Integer>(Depart.getTextDef(content.get(key)));
						for(String key1:content1.keySet()){
						  URL.add(key);
						  Concept.add(key1);
						  Rate.add(content1.get(key1));
					  }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						continue;
					}
				}
				for (String key2 : title.keySet()) {
					 IKAnalyzerDemo Depart=new IKAnalyzerDemo();
					  Map<String, Integer> title1;
					try {
						title1 = new  HashMap<String,Integer>(Depart.getTextDef(title.get(key2)));
						for(String key3:title1.keySet()){
						  URLTitle.add(key2);
						  ConceptTitle.add(key3);
						  RateTitle.add(title1.get(key3));
					  }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						continue;
					}
				}
			 int j=0;//将信息根据词频排序
			 int i=0;
			 String replace1;
			 String replace2;
			 Integer replace;
			 for(i=0;i<URL.size()-1;i++){
				 for(j=1;j<URL.size()-i;j++){
					 if(Rate.get(j-1).compareTo(Rate.get(j))<0){
						 replace=Rate.get(j-1);
						 replace1=URL.get(j-1);
						 replace2=Concept.get(j-1);
						 Rate.set(j-1,Rate.get(j));
						 URL.set(j-1,URL.get(j));
						 Concept.set(j-1, Concept.get(j));
						 Rate.set(j, replace);
						 URL.set(j, replace1);
						 Concept.set(j, replace2);
					 }
				 }
			 }
			 i=0;
			 j=0;
			 for(i=0;i<URLTitle.size()-1;i++){
				 for(j=1;j<URLTitle.size()-i;j++){
					 if(RateTitle.get(j-1).compareTo(RateTitle.get(j))<0){
						 replace=RateTitle.get(j-1);
						 replace1=URLTitle.get(j-1);
						 replace2=ConceptTitle.get(j-1);
						 RateTitle.set(j-1,RateTitle.get(j));
						 URLTitle.set(j-1,URLTitle.get(j));
						 ConceptTitle.set(j-1, ConceptTitle.get(j));
						 RateTitle.set(j, replace);
						 URLTitle.set(j, replace1);
						 ConceptTitle.set(j, replace2);
					 }
				 }
			 }
			Demo_title demoTitle=new Demo_title();
			Demo demo=new Demo();
			demo.Delete();
			demoTitle.Delete();
			demo.Inter(URL,Concept,Rate);//将数据存入数据库中
			demoTitle.Inter(URLTitle, ConceptTitle, RateTitle);
			URL.clear();//记得将用过的线性表清空
			Concept.clear();
			Rate.clear();
			URLTitle.clear();
			ConceptTitle.clear();
			RateTitle.clear();
//			System.out.println("URL:");
//			Read(URL);
//			System.out.println("Concept:");
			}
			else{//不是第一层的抓取
				int N=0;
				ArrayList<String> Address=new ArrayList<String>(spider.arry);	
//				for(int m=0;m<spider.arry.size();m++){
//					System.out.println("sda    "+""+spider.arry.get(m));
//				}
				spider.arry.clear();
			
				for(N=0;N<Address.size();N++){
					address=Address.get(N);
					spider.get(address);
					content=new HashMap<String,String>(spider.getConcept());
					title=new HashMap<String,String>(spider.getTitle());
					spider.getConcept().clear();
					spider.getTitle().clear();
					demoWeb.Inter(title);
//					System.out.println("address:");
//					System.out.println(address);
					for (String key : content.keySet()) {
//						System.out.println("address:");
//						System.out.println(key);
						 IKAnalyzerDemo Depart=new IKAnalyzerDemo();
						  Map<String, Integer> content1;
						try {
							content1 = new  HashMap<String,Integer>(Depart.getTextDef(content.get(key)));
//							System.out.println(content.get(key));
							for(String key1:content1.keySet()){
							  URL.add(key);
//							  System.out.println("URL:");
//							  System.out.println(key);
							  Concept.add(key1);
//							  System.out.println("Concept:");
//							  System.out.println(key1);
							  Rate.add(content1.get(key1));
						  }
						} catch (IOException e) {
							// TODO Auto-generated catch block
							continue;
						}
						  
				   }
					for (String key2 : title.keySet()) {
						 IKAnalyzerDemo Depart=new IKAnalyzerDemo();
						  Map<String, Integer> title1;
						try {
							title1 = new  HashMap<String,Integer>(Depart.getTextDef(title.get(key2)));
							for(String key3:title1.keySet()){
							  URLTitle.add(key2);
							  ConceptTitle.add(key3);
							  RateTitle.add(title1.get(key3));
						  }
						} catch (IOException e) {
							// TODO Auto-generated catch block
							continue;
						}
					}
				 int j=0;
				 int i=0;
				 String replace1;
				 String replace2;
				 Integer replace;
				 for(i=0;i<URL.size()-1;i++){
					 for(j=1;j<URL.size()-i;j++){
						 if(Rate.get(j-1).compareTo(Rate.get(j))<0){
							 replace=Rate.get(j-1);
							 replace1=URL.get(j-1);
							 replace2=Concept.get(j-1);
							 Rate.set(j-1,Rate.get(j));
							 URL.set(j-1,URL.get(j));
							 Concept.set(j-1, Concept.get(j));
							 Rate.set(j, replace);
							 URL.set(j, replace1);
							 Concept.set(j, replace2);
						 }
					 }
				 }
				 i=0;
				 j=0;
				 for(i=0;i<URLTitle.size()-1;i++){
					 for(j=1;j<URLTitle.size()-i;j++){
						 if(RateTitle.get(j-1).compareTo(RateTitle.get(j))<0){
							 replace=RateTitle.get(j-1);
							 replace1=URLTitle.get(j-1);
							 replace2=ConceptTitle.get(j-1);
							 RateTitle.set(j-1,RateTitle.get(j));
							 URLTitle.set(j-1,URLTitle.get(j));
							 ConceptTitle.set(j-1, ConceptTitle.get(j));
							 RateTitle.set(j, replace);
							 URLTitle.set(j, replace1);
							 ConceptTitle.set(j, replace2);
						 }
					 }
				 }
				Demo_title demoTitle=new Demo_title();
				Demo demo=new Demo();
				demo.Inter(URL,Concept,Rate);
				demoTitle.Inter(URLTitle, ConceptTitle, RateTitle);
				URL.clear();
				Concept.clear();
				Rate.clear();
				URLTitle.clear();
				ConceptTitle.clear();
				RateTitle.clear();
//				System.out.println("URL:");
//				Read(URL);
//				System.out.println("Concept:");
//				Read(Concept);
//					for(int k=0;k<Rate.size();k++){
//					System.out.println(" concept:  "+URL.get(k)+"  "+Concept.get(k)+"  "+Rate.get(k));
//				}
				}
			}
	//		for(int i=0;i<URL.size();i++){
	//			System.out.println(URL.get(i)+"\t"+Concept.get(i)+"\t"+Rate.get(i));
	//		}
		}
	}
}
