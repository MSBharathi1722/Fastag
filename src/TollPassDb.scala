import java.sql._

class TollPassDb {

  private var con: Connection = null

  private var stmt: PreparedStatement = null

  try {
    Class.forName("com.mysql.cj.jdbc.Driver")
    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "fastag", "root", "root")
  } catch {
    case cfe: ClassNotFoundException => {}

    case e: SQLException => println(e)

  }

  def insertTravelDetails(mail: String, place: String, time: String, rtime: String):String = {
    var result:String = "false"
    try {
      stmt = con.prepareStatement("insert into Travel_Details (Mail,Place,Time,Rtime) values(?,?,?,?)")
      stmt.setString(1, mail)
      stmt.setString(2, place)  
      stmt.setString(3, time)
      stmt.setString(4, rtime)
      stmt.execute()
      result = "true"      
    } catch {
        case s: SQLException => println(s.getLocalizedMessage)
    }
    result
  }
}