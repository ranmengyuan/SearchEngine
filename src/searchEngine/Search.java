package searchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class Search {
	public Search(){
		
	}
	public void Similar(){
		ArrayList<Integer> index=new ArrayList<Integer>();
		ArrayList<String> content=new ArrayList<String>();
		index.add(0);
		content.add("计算机");
		index.add(0);
		content.add("电脑");
		index.add(0);
		content.add("PC");
		index.add(1);
		content.add("重庆");
		index.add(1);
		content.add("渝");
		index.add(2);
		content.add("学校");
		index.add(2);
		content.add("校园");
		index.add(3);
		content.add("我校");
		index.add(3);
		content.add("重庆理工大学");
		Demo_similar retail=new Demo_similar();
		retail.Inter(index, content);
	}
	public void Output() throws IOException{
		Similar();
		System.out.println("请输入要查询的内容：");
		String Concept;
		@SuppressWarnings("resource")
		Scanner in=new Scanner(System.in);
		Concept=in.nextLine();
		Map<String,Integer> title=new HashMap<String,Integer>(formTitle(Concept));
		Map<String,Integer> content=new HashMap<String,Integer>(formConcept(Concept));
		if(title.size()==0&&content.size()==0)
			System.out.println("对不起，没有找到要查找的结果");
		else{
			System.out.println("查到的结果是：");
			for(String key:title.keySet()){
				System.out.println(key);
				Demo_web demoWeb=new Demo_web();
				System.out.println(demoWeb.Look(key));
			}
			for(String key1:content.keySet()){
					System.out.println(key1);
					Demo_web demoWeb=new Demo_web();
					System.out.println(demoWeb.Look(key1));
			}
		}
		
	}
	public static Map<String,Integer> Arrange(Map<String,Integer> from,Map<String,Integer> fromEnd){
		int flag=0;
		for(String key:fromEnd.keySet()){
			flag=0;
			for(String key1:from.keySet()){
				if(key1.equals(key)){
					flag=1;
					from.put(key, fromEnd.get(key1)+from.get(key));
				}
				if(flag==1)
					break;
			}
			if(flag==0){
				from.put(key,fromEnd.get(key));
			}
		}
		return from;
	}
	public int Replace(ArrayList<String> array,String url){
		int flag=0;
		for(int i=0;i<array.size();i++){
			if(url.compareTo(array.get(i))==0){
				flag=1;
				break;
			}
		}
		return flag;
	}
	public Map<String,Integer> formConcept(String Concept) throws IOException{
		Map<String,Integer> Get=new HashMap<String,Integer>();
		IKAnalyzerDemo depart=new IKAnalyzerDemo();
		ArrayList<String> Result=new ArrayList<String>();
		ArrayList<String> fenci;
		Demo concept=new Demo();
		Demo_similar similar=new Demo_similar();
//		System.out.println("hello");
		fenci=new ArrayList<String>(depart.Depart_simple(Concept));
		for(int p=0;p<fenci.size();p++){
			if(similar.LookSimilar(similar.LookIndex(fenci.get(p))).size()!=0)
				Result.addAll(similar.LookSimilar(similar.LookIndex(fenci.get(p))));
			else
				Result.add(fenci.get(p));
		}
		for(int i=0;i<Result.size();i++){
			
			Arrange(Get,concept.Look(Result.get(i)));
		}
		Map<String,Integer> SortedMap;
		if(Get.size()==0){
			SortedMap=new HashMap<String,Integer>();
		}
		else
			SortedMap=new HashMap<String,Integer>(sortMapByValue(Get));
		return SortedMap;
	}
	public Map<String,Integer> formTitle(String Concept)throws IOException{
		Map<String,Integer> GetTitle=new HashMap<String,Integer>();
		ArrayList<String> Result=new ArrayList<String>();
		ArrayList<String> fenci;
		Demo_title title=new Demo_title();
		Demo_similar similar=new Demo_similar();
		IKAnalyzerDemo depart=new IKAnalyzerDemo();
		fenci=new ArrayList<String>(depart.Depart_simple(Concept));
//		System.out.println(Result.size());
		for(int p=0;p<fenci.size();p++){
			if(similar.LookSimilar(similar.LookIndex(fenci.get(p))).size()!=0)
				Result.addAll(similar.LookSimilar(similar.LookIndex(fenci.get(p))));
			else
				Result.add(fenci.get(p));
		}
		for(int i=0;i<Result.size();i++){
			Arrange(GetTitle,title.Look(Result.get(i)));
		}
		Map<String,Integer> SortedMapTitle;
		if(GetTitle.size()==0)
			SortedMapTitle=new HashMap<String,Integer>();
		else
			SortedMapTitle=new HashMap<String,Integer>(sortMapByValue(GetTitle));
		return SortedMapTitle;
	}
	public static Map<String, Integer> sortMapByValue(Map<String, Integer> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();  
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());  
        Collections.sort(entryList, new MapValueComparator());  
        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();  
        Map.Entry<String, Integer> tmpEntry = null;  
        while (iter.hasNext()) {  
            tmpEntry = iter.next();  
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
        }  
        return sortedMap;  
    }  
} 
class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {  
    public int compare(Entry<String,Integer> me1, Entry<String, Integer> me2) {  
        return me1.getValue().compareTo(me2.getValue());  
    }  
}
