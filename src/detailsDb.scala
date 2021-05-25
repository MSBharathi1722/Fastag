import java.sql._

class detailsDb {

  private var con: Connection = null

  private var stmt: PreparedStatement = null

  private var rs: ResultSet = null

  try {
    Class.forName("com.mysql.cj.jdbc.Driver")
    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "fastag", "root", "root")
  }
  catch {
    case cfe: ClassNotFoundException => println(cfe)
    case e: SQLException => println(e)
  }

  def userQuery(mail: String,option:String):String = {
    var result:String = null
    try {      
        stmt = con.prepareStatement("select "+option+" from User_Details where Mail=?")
        stmt.setString(1,mail);
      if (stmt.execute()) {
        rs = stmt.getResultSet
        while (rs.next()) {
          result = rs.getString(1)
        }
      } else {
        println("no execution .....")
      }
    } catch {
      case ee: Exception => {
        println("Error while executing the query ......" + ee.getMessage)
        result = "Err"
      }

    }
    result
  }
  def vehicleQuery(mail: String,option:String):String = {
    var result:String = null
    try {      
        stmt = con.prepareStatement("select "+option+" from Vehicle_Details where Mail=?")
        stmt.setString(1,mail);
      if (stmt.execute()) {
        rs = stmt.getResultSet
        while (rs.next()) {
          result = rs.getString(1)
        }
      } else {
        println("no execution .....")
      }
    } catch {
      case ee: Exception => {
        println("Error while executing the query ......" + ee.getMessage)
        result = "Err"
      }

    }
    result
  }
  def selectQuery(mail: String, option: String):String = {
    var result:String = null
    try {      
        stmt = con.prepareStatement("select "+option+" from Balance_Details where Mail=?")
        stmt.setString(1,mail);
      if (stmt.execute()) {
        rs = stmt.getResultSet
        while (rs.next()) {
          result = rs.getString(1)
        }
      } else {
        println("no execution .....")
      }
    } catch {
      case ee: Exception => {
        println("Error while executing the query ......" + ee.getMessage)
        result = "Err"
      }

    }
    result
  }
}
