package com.jpz.dcim.modeling.init;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.jpz.dcim.modeling.ContextHolder;

public class SqlInit {
	public static void main(String[] args){
		DataSource ds = getDatasource();
		Connection conn = null;
		try{
			conn = ds.getConnection();
			conn.setAutoCommit(true);
			SqlFileExecutor executor = new SqlFileExecutor();
			for(String arg :args){
				executor.execute(conn, getInitFilePath(arg));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static DataSource getDatasource(){
		try{
			Object obj = ContextHolder.getContext().getBean("dataSource");
			DataSource ds = (DataSource) obj;
			return ds;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getInitFilePath(String fileName){
		System.out.println(fileName);
		URL url = SqlInit.class.getClassLoader().getResource(fileName);		
		return url.getFile().toString();
	}
}	
