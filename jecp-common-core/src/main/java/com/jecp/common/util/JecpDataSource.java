package com.jecp.common.util;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;


/** 
 * 编写标准的数据源：
 * 1、实现DataSource 接口
 * 2、获取在实现类的构造方法中批量获取Connection 对象，并将这些Connection 存储
 * 在LinkedList 容器中。
 * 3、实现getConnection() 方法，调用时返回LinkedList容器的Connection对象给用户。
 *
 */
public class JecpDataSource implements DataSource{
    private static String url = null;
    private static String password = null;
    private static String user = null ;
    private static String DriverClass = null;
    private static LinkedList<Connection> pool = new LinkedList<Connection>() ;
    public static void main(String[] args) throws Exception {
    	JecpDataSource myDataSource=new JecpDataSource();
    	Connection c=myDataSource.getConnection();
//    	c.close();
    	System.out.println(c);
    	System.out.println(pool.size());
    }
    //    注册数据库驱动
    static {
        try {  
//            ResourceBundle rb = ResourceBundle.getBundle("db") ;
            url = "jdbc:nds://192.168.102.35:18600/v_qyzhfwsjAPP?appname=APP_qyzhfwsjAPP" ; 
            password = "Jna.com!2" ; 
            user = "jecp"; 
            DriverClass = "sgcc.nds.jdbc.driver.NdsDriver";
            Class.forName(DriverClass) ;  
            
            //初始化建立数据连接池
            for(int i = 0 ; i < 100 ; i ++) {
                Connection conn = DriverManager.getConnection(url, user, password) ;
                pool.add(conn) ;
            }
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }  
    }
    public JecpDataSource ()  { 
        
    }
    
    //、从连接池获取连接:通过动态代理
    public Connection getConnection() throws SQLException {
        if (pool.size() > 0) {
            final Connection conn  = pool.remove() ; 
            Connection proxyCon = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), new Class[] { Connection.class }, 
                    new InvocationHandler() {
                        //策略设计模式：
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args)
                                throws Throwable {
                            if("close".equals(method.getName())){
                                //谁调用，
                                return pool.add(conn);//当调用close方法时，拦截了，把链接放回池中了
                            }else{
                                return method.invoke(conn, args);
                            } 
                        }
                    });
          return proxyCon ;
        }else {
            throw new RuntimeException("服务器繁忙！"); 
        }
    } 
    
    public int getLength() {
        return pool.size() ;
    }
    
    
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }
    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        
    }
    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        
    }
    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return null;
    }

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	} 
}