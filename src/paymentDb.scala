import java.sql._

class paymentDb {

  private var con: Connection = null

  private var stmt: PreparedStatement = null

  try {
    Class.forName("com.mysql.cj.jdbc.Driver")
    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "fastag", "root", "root")
  } catch {
    case cfe: ClassNotFoundException => {}

    case e: SQLException => println(e)

  }

  def updateBalance(mail:String , amount:String):String = {
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