package br.com.udemy.jdbc.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DB {

	
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				throw new DbException(e.getMessage());
			}
		}
	}
	
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				throw new DbException(e.getMessage());
			}
		}
	}
}
