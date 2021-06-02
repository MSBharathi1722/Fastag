import java.sql._
import scala.collection.mutable._

class DBHandler{

	private var con: Connection = null
	private var stmt: PreparedStatement = null
	private var rs: ResultSet = null

	def getConnection(){
		try {
	    	Class.forName("com.mysql.cj.jdbc.Driver")
	    	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "fastag", "root", "root")
	  	}
	  	catch {    
	  		case cfe: ClassNotFoundException => {}
	    	case e: SQLException => println(e)
	  	}
	}
	
	def getUserDetail(id:Int):Map[String,String] = {
		var details=Map[String,String]();
		getConnection()
		try {
    	    stmt = con.prepareStatement("select name,mail,balance,reg_no,vehicle_type from user_detail inner join balance_detail on user_detail.user_id=balance_detail.user_id inner join vehicle_detail on user_detail.user_id=vehicle_detail.user_id where user_detail.user_id=?")
     	    stmt.setInt(1, id)
          	if (stmt.execute()) {
        		rs = stmt.getResultSet
        		while (rs.next()) {
        			var vehicle_type: String = rs.getString(5)
			    	var amt:Int = 0
			    	vehicle_type match {
			    		case "type1" => amt = 85
			      		case "type2" => amt = 135
			      		case "type3" => amt = 285
			      		case "type4" => amt = 315
			      		case "type5" => amt = 450
			      		case "type6" => amt = 550
			    	}

        			details += ("id"->"1","user_id"->id.toString(),"name"->rs.getString(1),"mail"->rs.getString(2),"amount"->amt.toString(),"reg_no"->rs.getString(4),"avail_bal"->rs.getString(3))      
        		}
      		}else {
        		println("no execution .....")
      		}
    	} catch {
      		case ee: Exception => {
        		println("Error while executing the query ......" + ee.getMessage)
        		details += ("Error"->ee.getMessage)
      		}
    	}finally{
    		stmt.close()
    		rs.close()
			con.close()
		}
    	details
	}

	def getTimeBalanceAmount(id:Int):Map[String,String] = {
		getConnection()
		var details=Map[String,String]();
		try{
			stmt = con.prepareStatement("select time,vehicle_type,balance from travel_detail inner join vehicle_detail on travel_detail.user_id=vehicle_detail.user_id inner join balance_detail on travel_detail.user_id=balance_detail.user_id where travel_detail.id=?")
			stmt.setInt(1, id)
			if (stmt.execute()) {
				rs = stmt.getResultSet
	        	while (rs.next()) {
	        		var vehicle_type: String = rs.getString(2)
				    var amt:Int = 0
				    vehicle_type match {
				    	case "type1" => amt = 85
				    	case "type2" => amt = 135
				    	case "type3" => amt = 285
				    	case "type4" => amt = 315
				    	case "type5" => amt = 450
				    	case "type6" => amt = 550
				    }
				    details += ("Time"->rs.getString(1),"Amount"->amt.toString(),"Balance"->rs.getInt(3).toString())
	        	}
			}
		}catch{
			case ee: Exception => {
        		println("Error while executing the query ......" + ee.getMessage)
        		details += ("Error"->ee.getMessage)
      		}
		}finally{
			stmt.close()
    		rs.close()
			con.close()
		}
        details	
	}
	def getVehicleType(id:Int):String = {
		var result : String = null
		getConnection()
    	try {
    	    stmt = con.prepareStatement("select vehicle_type from vehicle_detail where user_id=?")
     	    stmt.setInt(1, id)
          	if (stmt.execute()) {
        		rs = stmt.getResultSet
        		while (rs.next()) result = (rs.getString(1))
      		}else {
        		println("no execution .....")
      		}
    	} catch {
      		case ee: Exception => {
        		println("Error while executing the query ......" + ee.getMessage)
        		result = "404"
      		}
    	}finally{
    		rs.close()
    		stmt.close()
			con.close()
		}
    	result
	}
	def getBalance(id:Int):Int = {
		var result: Int = 0
		getConnection()
    	try {
    	    stmt = con.prepareStatement("select balance from balance_detail where user_id=?")
     	    stmt.setInt(1, id)
          	if (stmt.execute()) {
        		rs = stmt.getResultSet
        		while (rs.next()) result = (rs.getInt(1))
      		}else {
        		println("no execution .....")
      		}
    	} catch {
      		case ee: Exception => {
        		println("Error while executing the query ......" + ee.getMessage)
        		result = 404
      		}
    	}finally{
    		rs.close()
    		stmt.close()
			con.close()
		}
    	result
	} 
	def getUserId(mail:String):Int = {
		var result: Int = 0
		getConnection()
    	try {
    	    stmt = con.prepareStatement("select user_id from user_detail where mail=?")
     	    stmt.setString(1, mail)
          	if (stmt.execute()) {
        		rs = stmt.getResultSet
        		while (rs.next()) result = (rs.getInt(1))
      		}else {
        		println("no execution .....")
      		}
    	} catch {
      		case ee: Exception => {
        		println("Error while executing the query ......" + ee.getMessage)
        		result = 404
      		}
    	}finally{
    		rs.close()
    		stmt.close()
			con.close()
		}
    	result
	}
	def checkUser(mail: String):String = {
		var result: String = "false"
		getConnection()
		try{
			stmt = con.prepareStatement("select user_id from user_detail where mail=?")
			stmt.setString(1, mail)
			if (stmt.execute()) {
        		rs = stmt.getResultSet
        		while (rs.next()) result = "true"
      		}else {
        		println("no execution .....")
        	}
		}catch{
			case ee: Exception => {
        		println("Error while executing the query ......" + ee.getMessage)
        		result = ee.getMessage
      		}
		} finally{
			con.close()
		}
		result
	}
	def checkPin(id: Int, pin: String):String = {
		var result: String = "false"
		var enteredPin :String = pin
		getConnection()
		try{
			stmt = con.prepareStatement("select pin from balance_detail where user_id=?")
			stmt.setInt(1, id)
			if (stmt.execute()) {
        		rs = stmt.getResultSet
        		while (rs.next()) {
        			var correctPin = rs.getString(1)
        			if(correctPin.equals(enteredPin)){
        				result = "true"
        			}else{
        				result = "incorrect pin"
        			}
        		}
      		}else {
        		println("no execution .....")
        	}
		}catch{
			case ee: Exception => {
        		println("Error while executing the query ......" + ee.getMessage)
        		result = ee.getMessage
      		}
		} finally{
			con.close()
		}
		result
	}

	def insertUserRecord(name: String, mail: String, mobile: String, pass:String) = {
    	var result:String = null
    	getConnection()
    	try {
    		stmt = con.prepareStatement("insert into user_detail (name,mail,mobile_number,password) values(?,?,?,?)")
        	stmt.setString(1, name)
      		stmt.setString(2, mail.toLowerCase())
      		stmt.setString(3, mobile)
      		stmt.setString(4, pass)
      		stmt.execute()
      		result = "true"   
    	} catch {
      		case s: SQLException => result = (s.getLocalizedMessage)
    	} finally{
			con.close()
		}
    	result
  	}

  	def insertVehicleRecord(id:Int, types: String, regNo: String) = {
  		getConnection()
    	var result:String = null
    	
    	try {
      		stmt = con.prepareStatement("insert into vehicle_detail(user_id,reg_no,vehicle_type) values(?,?,?)")
      		stmt.setInt(1, id)
      		stmt.setString(2, regNo.toUpperCase())
      		stmt.setString(3, types)
      		stmt.execute()
      		result = "true"
    	} catch {
      		case s: SQLException => result = (s.getLocalizedMessage)
    	} finally{
			con.close()
		}
    	result
  	}
	def insertBalanceRecord(id: Int, pin: String) = {
		getConnection()
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
	    } finally{
			con.close()
		}
	    result
	}
	def updateBalance(id:Int , amount:Int):String = {
		getConnection()
	    var result:String = "false"
	    try {
	      stmt = con.prepareStatement("update balance_detail set balance=? where user_id=?")
	      stmt.setInt(1, amount)
	      stmt.setInt(2, id)
	      stmt.executeUpdate()
	      result="true"
	    } catch {
	        case s: SQLException => result = (s.getLocalizedMessage)
	    } finally{
			con.close()
		}
	    result
	}

  	def checkPassword(mail: String): String = {
  		getConnection()
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
    	} finally{
			con.close()
		}
    	result
    }
    def insertTravelDetails(id: Int, place: String ):String = {
    	getConnection()
	    var result:String = "false"
	    try {
	    	stmt = con.prepareStatement("insert into travel_detail (user_id,place,time) values(?,?,CURRENT_TIMESTAMP)")
	    	stmt.setInt(1, id)
	    	stmt.setString(2, place)
	      	stmt.execute()
	      	result = "true"      
	    } catch {
	        case s: SQLException => result = (s.getLocalizedMessage)
	    } finally{
			con.close()
		}
	    result
	}
	def getTravelDetails(id: Int):ListBuffer[Map[String,String]] = {
		getConnection()
	    var result = ListBuffer[Map[String,String]]()
	    
	    try {
	    	stmt = con.prepareStatement("select * from travel_detail where user_id=? and return_time is null order by id desc limit 5")
	      	stmt.setInt(1, id)
	      	if (stmt.execute()) {
	        	var index=1
	        	rs = stmt.getResultSet
	        	while (rs.next()) {
	        		result += Map("id"->rs.getInt(1).toString(),"Place"->rs.getString(3),"Time"->rs.getString(4))
	            	index+=1
	        	}
	      	} else {
	        	println("no execution .....")
	      	}      
	    } catch {
	        case s: SQLException => Map("error"->(s.getLocalizedMessage))
	    } finally{
			con.close()
		}
	    result
	}
	def updateReturn(id: Int): String = {
		getConnection()
		var result: String = "false"
		try{
			stmt = con.prepareStatement("update travel_detail set return_time=CURRENT_TIMESTAMP where id=?")
			stmt.setInt(1, id)
	      	stmt.executeUpdate()
	      	result ="true"
		}catch{
			case s: SQLException => result = (s.getLocalizedMessage)
		}finally{
			con.close()
		}
		result
	}
	def getTimeDifference(t1: String, t2: String):Int = {
		getConnection()
		var result: Int = 2
		try{
			stmt = con.prepareStatement("select (TIMESTAMPDIFF(MINUTE,?,?))<30")
			stmt.setString(1,t1)
			stmt.setString(2,t2)
			if (stmt.execute()) {
	        	rs = stmt.getResultSet
	        	while (rs.next()) {
	        		result = rs.getInt(1)
	        	}
	      	} else {
	        	println("no execution .....")
	      	} 
		}catch{
			case s: SQLException => println(s.getLocalizedMessage)
		}finally{
			con.close()
		}
		result
	}
	def getTime(id:Int):String ={
		getConnection()
		var result:String = null
		try{
			stmt = con.prepareStatement("select time from travel_detail where id=?")
     	    stmt.setInt(1, id)
          	if (stmt.execute()) {
        		rs = stmt.getResultSet
        		while (rs.next()) result = (rs.getString(1))
      		}else {
        		println("no execution .....")
      		}
		}catch{
			case s: SQLException => result = (s.getLocalizedMessage)
		}finally{
			con.close()
		}
		result
	}
}

