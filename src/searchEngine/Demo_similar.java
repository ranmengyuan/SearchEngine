package searchEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Demo_similar {static String sql = null;  
static DBHelper db1 = null;  
static ResultSet ret = null;
static java.sql.Statement stmt = null;

public Demo_similar() {  
    
}  
public int LookIndex(String Input){//查看内容的索引
	sql = "select Indexl from Table_similar where Content=?";//SQL语句  
    db1 = new DBHelper(sql);//创建DBHelper对象  
    int uid=-1;
    try {  
    	db1.pst.setString(1, Input);
        ret = db1.pst.executeQuery();//执行语句，得到结果集  
        while (ret.next()) {  
             uid = ret.getInt("Indexl");     

        }              
        ret.close(); 
        db1.close();//关闭连接  
    } catch (SQLException e) {  
    	System.out.println("数据库连接失败");    
    }  
    return uid;
}
public ArrayList<String> LookSimilar(int index){ 
	ArrayList<String> similar=new ArrayList<String>();
	if(index!=-1){
		sql = "select Content from Table_similar where Indexl=?";//SQL语句  
	    db1 = new DBHelper(sql);//创建DBHelper对象  
	    try {  
	    	db1.pst.setInt(1, index);
	        ret = db1.pst.executeQuery();//执行语句，得到结果集  
	        while (ret.next()) {  
	             similar.add(ret.getString("Content"));     
	
	        }              
	        ret.close(); 
	        db1.close();//关闭连接  
	    }catch (SQLException e) {  
	    	System.out.println("数据库连接失败");    
	    }  
	}
    return similar;
}
public int Size(){//统计数据库中的数据个数
	int legth=0;
	sql = "select *from Table_similar";//SQL语句  
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
public  void Inter(ArrayList<Integer> Index,ArrayList<String>Content){//将传入方法的数据加到数据库中
	  	sql = "select *from Table_similar";//SQL语句  
        db1 = new DBHelper(sql);//创建DBHelper对象
        int i=0;
        try {  
	        	stmt=db1.conn.createStatement();
	        	 for(i=0;i<Index.size();i++){
	        		 stmt.executeUpdate("INSERT INTO Table_similar VALUES('"+Index.get(i)+"','"+Content.get(i)+"')");  //添加一条记录
	        	 }
	            ret = db1.pst.executeQuery();//执行语句，得到结果集  
	            stmt.close();
	            db1.close();//关闭连接  
	     } catch (SQLException e) {  
	    	 e.printStackTrace();   
	     }  
 
}
public  void Delete(){//删除数据库中的所有内容
	  
	 sql = "select *from Table_similar";//SQL语句  
        db1 = new DBHelper(sql);//创建DBHelper对象  
  
        try {  
        	 stmt=db1.conn.createStatement();
        	 stmt.executeUpdate("DELETE FROM Table_similar WHERE 1=1"); 
            ret = db1.pst.executeQuery();//执行语句，得到结果集                 
            ret.close(); 
            stmt.close();
            db1.close();//关闭连接  
        } catch (SQLException e) {  
        	System.out.println("数据库连接失败");    
        }  
 
}


}
