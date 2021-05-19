import java.sql._

class returnDb {
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

  def getRecord(mail: String, place: String):String = {
    var result:String = "false"
    try {
      stmt = con.prepareStatement("select Time from Travel_Details where Mail=? and Place=? order by S_No desc limit 1")
      stmt.setString(1, mail)
      stmt.setString(2,place)
      if (stmt.execute()) {
        rs = stmt.getResultSet
        while (rs.next()) result = rs.getString(1)
      } else {
        println("no execution .....")
      }      
    } catch {
        case s: SQLException => println(s.getLocalizedMessage)
    }
    result
  }

  def selectBalance(mail: String):String = {
    var result:String = null
    try {      
        stmt = con.prepareStatement("select Avail_Bal from Balance_Details where Mail=?")
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
  def updateBalance(mail: String, amount:String):String = {
    var result:String = "false"
    try {
      stmt = con.prepareStatement("update Balance_Details set Avail_Bal=? where Mail=?")
      stmt.setString(1, amount)
      stmt.setString(2, mail)
      stmt.executeUpdate()
      result="true"
    } catch {
        case s: SQLException => println(s.getLocalizedMessage)
    }
    result
  }
}