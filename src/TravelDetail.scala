import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class TravelDetail extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
  	    response.setContentType("applicaton/json")
        response.setCharacterEncoding("utf-8")
        val db: DBHandler = new DBHandler()
        val out: PrintWriter = response.getWriter
        val user_id = request.getParameter("user_id").toInt
        val result = db.getTravelDetails(user_id)
		out.println(Json.toJson(Map("travelDetail"->result)))
	}

    override def doPost(request: HttpServletRequest, response: HttpServletResponse) = {
  	    response.setContentType("applicaton/json")
        response.setCharacterEncoding("utf-8")
        val db: DBHandler = new DBHandler()
        val out: PrintWriter = response.getWriter

        val reader = request.getReader()
        val payloadJson = Json.parse(reader.readLine())
        val json = payloadJson("travelDetail")

        val user_id = try {
        	json("user_id").as[String]
        } catch {
        	case _ => "null"
        }        	
        val amount = try {
			json("amount").as[String]
		} catch {
		  	case _ => "null"
		}
		val return_id = try {
			json("return_id").as[String]
		} catch {
			case _ => "null"
		}
		val place = try {
			json("place").as[String].toLowerCase()
		} catch {
			case _ => "null"
		}

		if(place == "null" && return_id == "null"){

			var currenBalance = db.getBalance(user_id.toInt)
	      	var updatedBalance = currenBalance + amount.toInt
			var status = db.updateBalance(user_id.toInt,updatedBalance)
			var maps = Map("id"->user_id.toString(),"status"->status)
	      	out.println(Json.toJson(Map("travelDetail"->maps)))
	    
	    }

	    else if(return_id == "null" && amount == "null"){
	    	val pin = json("pin").as[String]
	    	val correctPin = db.checkPin(user_id.toInt,pin)
	    	if(correctPin == "true"){
	    		val status:String = db.insertTravelDetails(user_id.toInt,place)
				if(status == "true"){
					val vehicle_type = db.getVehicleType(user_id.toInt)
					val balance = db.getBalance(user_id.toInt)
					var amt:Int = 0
					vehicle_type match {
					    case "type1" => amt = 85
					    case "type2" => amt = 135
					    case "type3" => amt = 285
					    case "type4" => amt = 315
					    case "type5" => amt = 450
					    case "type6" => amt = 550
					}
					val updatedAmount = balance - amt
					var state = db.updateBalance(user_id.toInt,updatedAmount)
					var maps = Map("id"->user_id.toString(),"status"->state)
		        	out.println(Json.toJson(Map("travelDetail"->maps)))
				}else{
					var maps = Map("id"->user_id.toString(),"status"->status)
		        	out.println(Json.toJson(Map("travelDetail"->maps)))	
				}
	    	}
	    	else{
	    		var maps = Map("id"->user_id.toString(),"status"->"Invalid Pin")
		        out.println(Json.toJson(Map("travelDetail"->maps)))
	    	}
	    }

	    else if(place == "null" && amount == "null"){
	    	
			val status: String = db.updateReturn(return_id.toInt)
			val time_balance_amount = db.getTimeBalanceAmount(return_id.toInt)
			
			val time = time_balance_amount("Time").toString()
			val amt = time_balance_amount("Amount").toString()
			val balance = (time_balance_amount("Balance").toString())

			val timeDifference:Int = db.getTimeDifference(time,"CURRENT_TIMESTAMP")

			if(timeDifference == 1){
				var updatedAmount:Int = balance.toInt - (amt.toInt/2).toInt
				var state = db.updateBalance(user_id.toInt,updatedAmount)
				var maps = Map("id"->return_id,"status"->state,"amt"->"half")
	        	out.println(Json.toJson(Map("travelDetail"->maps)))
			}
			else if(timeDifference == 0){
				var updatedAmount:Int = (balance.toInt - amt.toInt)
				var state = db.updateBalance(user_id.toInt,updatedAmount)
				var maps = Map("id"->return_id,"status"->state,"amt"->"full")
	        	out.println(Json.toJson(Map("travelDetail"->maps)))
			}
			else{
				var maps = Map("id"->return_id,"status"->"Failed")
	        	out.println(Json.toJson(Map("travelDetail"->maps)))
			}
	    }
    }
}