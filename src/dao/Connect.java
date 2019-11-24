package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
	
	private Connection connection = null;
	private static Connect instance;
	
	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cnrk?useUnicode=true&use"
					+ "JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connect getInstance()
    {
	if (instance == null) instance = new Connect();
	return instance;
    }

    public Connection getConnection()
    {
	return connection;
    }
}
