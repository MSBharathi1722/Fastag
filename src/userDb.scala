import java.sql._

class userDb {

  private var con: Connection = null

  private var stmt: PreparedStatement = null

  private var rs: ResultSet = null

  try {
    Class.forName("com.mysql.cj.jdbc.Driver")
    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "fastag", "root", "root")
  }
  catch {
    case cfe: ClassNotFoundException => {}
    case e: SQLException => println(e)
  }

  def selectQuery(mail: String): String = {
    var result: String = null
    try {
        stmt = con.prepareStatement("select Password from User_Details where Mail=?")
        stmt.setString(1, mail)
      
      if (stmt.execute()) {
        rs = stmt.getResultSet
        while (rs.next()) result = rs.getString(1)
      } else {
        println("no execution .....")
      }
    } catch {
      case ee: Exception => {
        println("Error while executing the query ......" + ee.getMessage)
        result = "Error "
      }

    }
    result
  }
/*  def selectQuery(mail: String): String = {
    var result: String = null
    try {
      stmt = con.prepareStatement("select Password from User_Details where mail=?")
      stmt.setString(1,mail)
      if(stmt.execute()) {
        rs = stmt.getResultSet()
        while (rs.next()) {
          result =rs.getString(1)
        }
      }
      else{
        //out.println("no execution .....")
      }
    }
    catch{
      case cfe: ClassNotFoundException => {}
    case e: SQLException => println(e)
    }
    result
  }*/
}
