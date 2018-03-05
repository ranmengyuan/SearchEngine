package searchEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements; 

public class Spider {
	public static ArrayList<String> arry=new ArrayList<String>(); 
	public static ArrayList<String> noArry=new ArrayList<String>();
	private Map<String,String> concept=new HashMap<String,String>();
	private Map<String,String> title=new HashMap<String,String>();
	private static int sum=0;//统计抓取个数
	public Spider(){
		
	}
	public int Replay(String address){//判断连接是否重复
		int flag=1;
		int i=0;
		for(i=0;i<arry.size();i++){
			if(address.equals(arry.get(i))==true){
				flag=0;
				break;
			}
		}
		return flag;
	}
//	public int Judge(String address){//判断是否为合法连接
//		try{
//			Jsoup.connect(address).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
//			.timeout(5000).get();
//			return 1; 
//		}
//		catch(Exception e){
//			return 0;
//		}
//	}
	public void Catch(String address){//抓取连接和网页的内容以及标题,如果存在异常将提至存入一个存非法连接的线性表
		try {
			if(Legal(address)==1){
				Document doc = Jsoup.connect(address)
								.userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
								.timeout(5000).get();
				Elements hrefs = doc.select("a[href]");
				String text=doc.body().text();
				String text1=doc.title();
				concept.put(address, text);
				title.put(address, text1);
				for(Element elem:hrefs){
					if(elem.attr("abs:href").length()!=0&&Replay(elem.attr("abs:href"))==1){
	//					System.out.println(elem.attr("abs:href"));
						arry.add(elem.attr("abs:href"));
						sum++;
					}
					
				}
			}
		} catch (Exception e) {
			noArry.add(address);
			return;
		}
	}
	public int Legal(String address){//判断网页是否合法
		int flag=1;
		for(int i=0;i<noArry.size();i++){
			if(address.compareTo(noArry.get(i))==0){
				flag=0;
				break;
			}
		}
		return flag;
	}
	 public  void get(String URL){//返回获取的内容
				Catch(URL);
				System.out.println(sum);
			 
	}
	 public  Map<String,String> getConcept(){
		 return concept;
	 }
	 public Map<String,String> getTitle(){
		 return title;
	 }	 
}
