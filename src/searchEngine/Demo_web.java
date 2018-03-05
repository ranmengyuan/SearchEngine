package searchEngine;
import java.sql.ResultSet;  
import java.sql.SQLException;
import java.util.Map;
  
public class Demo_web {  
	static String sql = null;  
    static DBHelper db1 = null;  
    static ResultSet ret = null;
    static java.sql.Statement stmt = null;
    
    public Demo_web() {  
        
    }  
    public String Look(String Input){//查看数据口方法，将与查询的词相同的词和频率存在一个图中，并返回
    	sql = "select Title from Table_web where URL=?";//SQL语句  
        db1 = new DBHelper(sql);//创建DBHelper对象
        String title=null;  
        try { 
        	db1.pst.setString(1, Input);
            ret = db1.pst.executeQuery();//执行语句，得到结果集 
            while (ret.next()) {   
                String ufname = ret.getString("Title");   
                	title=ufname;          
            }//显示数据               
            ret.close(); 
            db1.close();//关闭连接  
        } catch (SQLException e) {  
        	System.out.println("数据库连接失败");  
        }  
        return title;
    }
    public int Size(){//统计数据库中的数据个数
    	int legth=0;
    	sql = "select *from Table_web";//SQL语句  
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
    public  void Inter(Map<String,String> Title){//将传入方法的数据加到数据库中
    	  	sql = "select *from Table_web";//SQL语句  
	        db1 = new DBHelper(sql);//创建DBHelper对象
	        try {  
		        	stmt=db1.conn.createStatement();
		        	 for(String key:Title.keySet()){
		        		 stmt.executeUpdate("INSERT INTO Table_web VALUES('"+key+"','"+Title.get(key)+"')");  //添加一条记录
		        	 }
		            ret = db1.pst.executeQuery();//执行语句，得到结果集  
		            stmt.close();
		            db1.close();//关闭连接  
		     } catch (SQLException e) {  
		    	 System.out.println("数据库连接失败");   
		     }  
	 
    }
    public  void Delete(){//删除数据库中的所有内容
    	  
		 sql = "select *from Table_web";//SQL语句  
	        db1 = new DBHelper(sql);//创建DBHelper对象  
	  
	        try {  
	        	 stmt=db1.conn.createStatement();
	        	 stmt.executeUpdate("DELETE FROM Table_web WHERE 1=1"); 
	            ret = db1.pst.executeQuery();//执行语句，得到结果集                 
	            ret.close(); 
	            stmt.close();
	            db1.close();//关闭连接  
	        } catch (SQLException e) {  
	        	System.out.println("数据库连接失败");    
	        }  
	 
    }
  
}  



