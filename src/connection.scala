import java.sql._
 
class connection{
	
	private var con: Connection = null

  	private var stmt: PreparedStatement = null

  	private var rs: ResultSet = null

  	try {
    	Class.forName("com.mysql.cj.jdbc.Driver")
    	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "fastag", "root", "root")
  	} catch {
    	case cfe: ClassNotFoundException => {}

    	case e: SQLException => println(e)
	}
}