package searchEngine;
import java.sql.ResultSet;  
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
  
public class Demo_title {  
	static String sql = null;  
    static DBHelper db1 = null;  
    static ResultSet ret = null;
    static java.sql.Statement stmt = null;
    
    public Demo_title() {  
        
    }  
    public Map<String,Integer> Look(String Input){//查看数据口方法，将与查询的词相同的词和频率存在一个图中，并返回
    	sql = "select URL,Rate from Table_title where Content=?";//SQL语句  
        db1 = new DBHelper(sql);//创建DBHelper对象  
        String uid;
        int rate;
        Map<String,Integer> suit=new HashMap<String,Integer>();
        try {  
        	db1.pst.setString(1, Input);
            ret = db1.pst.executeQuery();//执行语句，得到结果集  
            while (ret.next()) {  
                 uid = ret.getString("URL");     
                 rate=ret.getInt("Rate"); 
                	 suit.put(uid, rate);
//                	 System.out.println(uid+ufname+rate);
            }//将跟搜索的内容相同的关键字出现的网页和频率放入一个图中              
            ret.close(); 
            db1.close();//关闭连接  
        } catch (SQLException e) {  
        	System.out.println("数据库连接失败");    
        }  
        return suit;
    }
    public int Size(){//统计数据库中的数据个数
    	int legth=0;
    	sql = "select *from Table_title";//SQL语句  
        db1 = new DBHelper(sql);//创建DBHelper对象  
  
        try {  
            ret = db1.pst.executeQuery();//执行语句，得到结果集  
            while (ret.next()) { 
            	legth++;
            }              
            ret.close(); 
            db1.close();//关闭连接  
        } catch (SQLException e) {  
        	System.out.println("数据库连接失败");   
        }  
    	return legth;
    }
    public  void Inter(ArrayList<String> URL,ArrayList<String>Content,ArrayList<Integer>Rate){//将传入方法的数据加到数据库中
    	  	sql = "select *from Table_title";//SQL语句  
	        db1 = new DBHelper(sql);//创建DBHelper对象
	        int i=0;
	        try {  
		        	stmt=db1.conn.createStatement();
		        	 for(i=0;i<URL.size();i++){
		        		 if(i<5||(i<Rate.size()-1&&Rate.get(i)==Rate.get(4)))
		        		 stmt.executeUpdate("INSERT INTO Table_title VALUES('"+URL.get(i)+"','"+Content.get(i)+"','"+Rate.get(i)+"')");  //添加一条记录
		        	 }
		            ret = db1.pst.executeQuery();//执行语句，得到结果集  
		            stmt.close();
		            db1.close();//关闭连接  
		     } catch (SQLException e) {  
		    	 System.out.println("数据库连接失败");    
		     }  
	 
    }
    public  void Delete(){//删除数据库中的所有内容
    	  
		 sql = "select *from Table_title";//SQL语句  
	        db1 = new DBHelper(sql);//创建DBHelper对象  
	  
	        try {  
	        	 stmt=db1.conn.createStatement();
	        	 stmt.executeUpdate("DELETE FROM Table_title WHERE 1=1"); 
	            ret = db1.pst.executeQuery();//执行语句，得到结果集                 
	            ret.close(); 
	            stmt.close();
	            db1.close();//关闭连接  
	        } catch (SQLException e) {  
	        	System.out.println("数据库连接失败");    
	        }  
	 
    }
  
}  


