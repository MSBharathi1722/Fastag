import java.sql._
import scala.collection.mutable._

class DBHandler{

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

  	/*def getUserRecord(mail: String):Map[String,String] = {
	    var user_record = Map[String,String]()
	    try {      
	        stmt = con.prepareStatement("select * from user_detail where Mail=?")
	        stmt.setString(1,mail);
	      	if (stmt.execute()) {
	        	rs = stmt.getResultSet
	        	while (rs.next()) {
	        		user_record  = Map("id"->"1","name"->rs.getString(2),"mail"->rs.getString(3),"mobile"->rs.getInt(4).toString())
	        	}
	      	}else {
	        	println("no execution .....")
	      	}
	    }catch {
	      	case ee: Exception => {
	       		println("Error while executing the query ......" + ee.getMessage)
	     	}
	    }
	    user_record
	}

	def getVehicleRecord(mail: String):Map[String,String] = {
	    var vehicle_record = Map[String,String]()
	    try {      
	        stmt = con.prepareStatement("select * from vehicle_detail where mail=?")
	        stmt.setString(1,mail);
	      	if (stmt.execute()) {
	       		rs = stmt.getResultSet
	        	while (rs.next()) {
	        		vehicle_record = Map("reg_no"->rs.getString(4),"owner_name"->rs.getString(5),"amount"->rs.getInt(6).toString())
	        	}
	      	} else {
	        	println("no execution .....")
	      	}
	    }catch {
	     	case ee: Exception => {
	        	println("Error while executing the query ......" + ee.getMessage)
		    }
	    }
	    vehicle_record
	}

	def getBalanceRecord(mail: String):Map[String,String] = {
	    var balance_record = Map[String,String]()
	    try {      
	       	stmt = con.prepareStatement("select * from balance_detail where mail=?")
	       	stmt.setString(1,mail);
	       	if (stmt.execute()) {
	       		rs = stmt.getResultSet
	       		while (rs.next()) {
	        		balance_record = Map("pin"->rs.getInt(2).toString(),"balance"->rs.getInt(3).toString())
	       		}
	     	} else {
	        	println("no execution .....")
	      	}
	    }catch {
	    	case ee: Exception => {
	    		println("Error while executing the query ......" + ee.getMessage)
           	}
	    }
	    balance_record
	}*/
	def insertUser(mail:String) = {
		var result:String = null
    	try {
    		stmt = con.prepareStatement("insert into user (mail) values(?)")
        	stmt.setString(1, mail)      		
      		stmt.execute()
      		result = "true"   
    	} catch {
      		case s: SQLException => println(s.getLocalizedMessage)
    	}
    	result
	}
	def getUserId(mail:String):String = {
		var result: String = null
    	try {
    	    stmt = con.prepareStatement("select user_id from user where mail=?")
     	    stmt.setString(1, mail)
          	if (stmt.execute()) {
        		rs = stmt.getResultSet
        		while (rs.next()) result = (rs.getInt(1)).toString()
      		}else {
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
	def insertUserRecord(name: String, mail: String, mobile: String, pass:String) = {
    	var result:String = null
    	try {
    		stmt = con.prepareStatement("insert into user_detail (name,mail,mobile_number,password) values(?,?,?,?)")
        	stmt.setString(1, name)
      		stmt.setString(2, mail)
      		stmt.setString(3, mobile)
      		stmt.setString(4, pass)
      		stmt.execute()
      		result = "true"   
    	} catch {
      		case s: SQLException => result = (s.getLocalizedMessage)
    	}
    	result
  	}
  	def insertVehicleRecord(id:Int, types: String, regNo: String) = {
    	var result:String = null
    	var temp: String = types
    	var amt:Int = 0
    	temp match {
    		case "type1" => amt = 85
      		case "type2" => amt = 135
      		case "type3" => amt = 285
      		case "type4" => amt = 315
      		case "type5" => amt = 450
      		case "type6" => amt = 550
    	}
    	try {
      		stmt = con.prepareStatement("insert into vehicle_detail(user_id,reg_no,amount) values(?,?,?)")
      		stmt.setInt(1, id)
      		stmt.setString(2, regNo)
      		stmt.setInt(3, amt)
      		stmt.execute()
      		result = "true"
    	} catch {
      		case s: SQLException => result = (s.getLocalizedMessage)
    	}
    	result
  	}
	def insertBalanceRecord(id: Int, pin: String) = {
	    var result:String = null
	    try {
	      var availBal:Int = 0
	      stmt = con.prepareStatement("insert into balance_detail(user_id,pin,balance) values(?,?,?)")
	      stmt.setInt(1, id)
	      stmt.setString(2, pin)  
	      stmt.setInt(3, availBal)    
	      stmt.execute()
	      result = "true" 
	    } catch {
	        case s: SQLException => result = (s.getLocalizedMessage)
	    }
	    result
	}
	def updateBalance(id:Int , amount:String):String = {
	    var result:String = "false"
	    try {
	      stmt = con.prepareStatement("update balance_detail set balance=? where id=?")
	      stmt.setString(1, amount)
	      stmt.setInt(2, id)
	      stmt.executeUpdate()
	      result="true"
	    } catch {
	        case s: SQLException => println(s.getLocalizedMessage)
	    }
	    result
	}

	def getUserRecord(mail: String,option:String):String = {
    	var result:String = null
    	try {      
        	stmt = con.prepareStatement("select "+option+" from user_detail where mail=?")
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
	    	}
    	}
    	result
 	}
    def getVehicleRecord(id: Int,option:String) = {
    	var result = "null"
    	try {      
    	    stmt = con.prepareStatement("select "+option+" from vehicle_detail where user_id=?")
        	stmt.setInt(1,id);
        	if(option.equals("reg_no")){
        		if (stmt.execute()) {
	        		rs = stmt.getResultSet
	        		while (rs.next()) {
	        			result = rs.getString(1)
	        		}
	      		} else {
	        		println("no execution .....")
	      		}
        	}else{
        		if (stmt.execute()) {
	        		rs = stmt.getResultSet
	        		while (rs.next()) {
	        			result = rs.getInt(1).toString()
	        		}
	      		} else {
	        		println("no execution .....")
	      		}
        	}
      		
    	} catch {
      		case ee: Exception => {
        		println("Error while executing the query ......" + ee.getMessage)
        		result = "Err"
      		}
		}
    	result
  	}
  	def getBalanceRecord(id: Int, option: String) = {
    	var result = "null"
    	try {      
        	stmt = con.prepareStatement("select "+option+" from balance_detail where user_id=?")
        	stmt.setInt(1,id);
      		if(option.equals("pin")){
      			if (stmt.execute()) {
	        		rs = stmt.getResultSet
	        		while (rs.next()) {
	          			result = rs.getString(1)
	        		}
	      		} else {
	        		println("no execution .....")
	      		}
      		}else{
      			if (stmt.execute()) {
        		rs = stmt.getResultSet
        		while (rs.next()) {
          			result = rs.getInt(1).toString()
        		}
      		} else {
        		println("no execution .....")
      		}
      		}
    	} catch {
      		case ee: Exception => {
        		println("Error while executing the query ......" + ee.getMessage)
        		result = "Err"
      		}
		}
    	result
  	}
  	def checkPassword(mail: String): String = {
    	var result: String = null
    	try {
    	    stmt = con.prepareStatement("select password from user_detail where mail=?")
     	    stmt.setString(1, mail)
          	if (stmt.execute()) {
        		rs = stmt.getResultSet
        		while (rs.next()) result = rs.getString(1)
      		}else {
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
    def insertTravelDetails(mail: String, place: String, time: String, second: String):String = {
	    var result:String = "false"
	    try {
	    	stmt = con.prepareStatement("insert into travel_detail (mail,place,time,millisecond) values(?,?,?,?)")
	    	stmt.setString(1, mail)
	    	stmt.setString(2, place)  
	      	stmt.setString(3, time)
	      	stmt.setString(4, second)
	      	stmt.execute()
	      	result = "true"      
	    } catch {
	        case s: SQLException => println(s.getLocalizedMessage)
	    }
	    result
	}
	def getTravelDetails(mail: String):ListBuffer[Map[String,String]] = {
	    var result = ListBuffer[Map[String,String]]()
	    
	    try {
	    	stmt = con.prepareStatement("select * from travel_detail where mail=? order by id desc limit 5")
	      	stmt.setString(1, mail)
	      	if (stmt.execute()) {
	        	var index=1
	        	rs = stmt.getResultSet
	        	while (rs.next()) {
	        		result += Map("id"->index.toString(),"Place"->rs.getString(3),"Time"->rs.getString(4),"Seconds"->rs.getString(5))
	            	index+=1
	        	}
	      	} else {
	        	println("no execution .....")
	      	}      
	    } catch {
	        case s: SQLException => println(s.getLocalizedMessage)
	    }
	    result
	}
}