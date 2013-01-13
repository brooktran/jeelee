package olts.database.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单连接池
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:brook.tran.c@gmail.com"/>brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-1-3 新建
 * @since OnlineTest Ver 1.0
 * 
 */
public class ConnectionPool {
	private static List<Connection> pool;// 池

	private static int MAX_SIZE;// 池最大连接数

	static {
		pool = new ArrayList<Connection>();
		MAX_SIZE = 25;
	}

	/**
	 * 作用:获取池中的连接
	 * 
	 * @return Connection
	 * @throws SQLException 无法连接数据库
	 * @throws ClassNotFoundException 驱动程序不存在
	 */
	public Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Connection conn = null;
		try {
			conn = pool.get(pool.size() - 1);
			synchronized (pool) {
				pool.remove(pool.size() - 1);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			conn = createConnection();
		}
		ConnectionProxy proxy = new ConnectionProxy(this);
		return proxy.getConnection(conn);
	}

	/**
	 * 作用:创建一个连接
	 * 
	 * @return connection 连接
	 * @throws SQLException
	 *             无法连接数据库
	 * @throws ClassNotFoundException
	 *             驱动程序不存在
	 */
	public Connection createConnection() throws SQLException,
			ClassNotFoundException {
		Connection conn = null;
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		conn = DriverManager.getConnection("jdbc:odbc:olts");
		return conn;
	}

	/**
	 * 作用:释放一个连接
	 * 
	 * @param conn
	 *            要释放的连接
	 * @throws SQLException
	 *             无法关闭连接
	 */
	protected void releaseConnection(Connection conn)
			throws SQLException {
		if (isFull()) {
			// 这里可添加清理代码
			conn.close();
		} else {
			synchronized (pool) {
				pool.add(conn);
			}
		}
	}

	/**
	 * 作用:检查池是否满
	 */
	public boolean isFull() {
		return pool.size() < MAX_SIZE;
	}
}
