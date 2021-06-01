import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class UpdateDetail extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val user_id = request.getParameter("userId").toInt
		var amount = if(request.getParameter("amt") == null) "null" else request.getParameter("amt")
		val place = if(request.getParameter("place") == null) "null" else request.getParameter("place").toLowerCase()
		val return_id = if(request.getParameter("returnId") == null) "null" else request.getParameter("returnId")
	    response.setContentType("applicaton/json")
		val out: PrintWriter = response.getWriter
		val db: DBHandler = new DBHandler()

		if(place == "null" && return_id == "null"){

			var currenBalance = db.getBalance(user_id)
	      	var updatedBalance = currenBalance + amount.toInt
			var status = db.updateBalance(user_id,updatedBalance)
			var maps = Map("id"->user_id.toString(),"status"->status)
	      	out.println(Json.toJson(Map("update"->maps)))
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
				var state = db.updateBalance(user_id,updatedAmount)
				var maps = Map("id"->return_id,"status"->state,"amt"->"half")
	        	out.println(Json.toJson(Map("update"->maps)))
			}
			else if(timeDifference == 0){
				var updatedAmount:Int = (balance.toInt - amt.toInt)
				var state = db.updateBalance(user_id,updatedAmount)
				var maps = Map("id"->return_id,"status"->state,"amt"->"full")
	        	out.println(Json.toJson(Map("update"->maps)))
			}
			else{
				var maps = Map("id"->return_id,"status"->"Failed")
	        	out.println(Json.toJson(Map("update"->maps)))
			}
	    }

	    else if(return_id == "null" && amount == "null"){
	    	val status:String = db.insertTravelDetails(user_id,place)
			if(status == "true"){
				val vehicle_type = db.getVehicleType(user_id)
				val balance = db.getBalance(user_id)
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
				var state = db.updateBalance(user_id,updatedAmount)
				var maps = Map("id"->user_id.toString(),"status"->state)
	        	out.println(Json.toJson(Map("update"->maps)))
			}else{
				var maps = Map("id"->user_id.toString(),"status"->status)
	        	out.println(Json.toJson(Map("update"->maps)))	
			}
	    }
    }
}