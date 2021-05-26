import java.sql._

class NewUserDb {

  private var con: Connection = null

  private var stmt: PreparedStatement = null

  try {
    Class.forName("com.mysql.cj.jdbc.Driver")
    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "fastag", "root", "root")
  } catch {
    case cfe: ClassNotFoundException => {}

    case e: SQLException => println(e)

  }

  def insertUserDetails(name: String, mail: String, mobile: String, pass:String) = {
    var result:String = null
    try {
      stmt = con.prepareStatement("insert into User_Details (Name,Mail,Mobile,Password) values(?,?,?,?)")
      stmt.setString(1, name)
      stmt.setString(2, mail)
      stmt.setString(3, mobile)
      stmt.setString(4, pass)
      stmt.execute()
      result = "true"   
    } catch {
      case s: SQLException => println(s.getLocalizedMessage)
    }
    result
  }
  def insertVehicleDetails(mail: String, types: String, regNo: String, ownerName:String) = {
    var result:String = null
    var temp: String = types
    var amt:String = null
    temp match {
      case "type1" => amt = "85"
      case "type2" => amt = "135"
      case "type3" => amt = "285"
      case "type4" => amt = "315"
      case "type5" => amt = "450"
      case "type6" => amt = "450"
      case "type7" => amt = "550"
    }
    try {
      stmt = con.prepareStatement("insert into Vehicle_Details values(?,?,?,?,?)")
      stmt.setString(1, mail)
      stmt.setString(2, types)
      stmt.setString(3, regNo)
      stmt.setString(4, ownerName)
      stmt.setString(5, amt)
      stmt.execute()
      result = "true"
    } catch {
      case s: SQLException => println(s.getLocalizedMessage)
    }
    result
  }
  def insertBalanceDetails(mail: String, pin: String) = {
    var result:String = null
    try {
      var availBal:String = "0000"
      stmt = con.prepareStatement("insert into Balance_Details values(?,?,?)")
      stmt.setString(1, mail)
      stmt.setString(2, pin)  
      stmt.setString(3, availBal)    
      stmt.execute()
      result = "true" 
    } catch {
        case s: SQLException => println(s.getLocalizedMessage)
    }
    result
  }
}