import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class UpdateReturn extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val mail: String = request.getParameter("mail")
		val id:Int = request.getParameter("id").toInt
	    response.setContentType("applicaton/json")
		val out: PrintWriter = response.getWriter
		val db: DBHandler = new DBHandler()
		val user_id = db.getUserId(mail)
		val status: String = db.updateReturn(id)
		val time:String = db.getTime(id)
		val timeDifference:Int = db.getTimeDifference(time,"CURRENT_TIMESTAMP")
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

		if(timeDifference == 1){
			var updatedAmount = balance - (amt/2).toInt

			var state = db.updateBalance(user_id,updatedAmount)
			var maps = Map("id"->id.toString(),"status"->state,"amt"->"half")
        	out.println(Json.toJson(Map("travel"->maps)))
		}
		else if(timeDifference == 0){
			var updatedAmount = balance - amt

			var state = db.updateBalance(user_id,updatedAmount)
			var maps = Map("id"->id.toString(),"status"->state,"amt"->"full")
        	out.println(Json.toJson(Map("travel"->maps)))
		}
		else{
			var maps = Map("id"->id.toString(),"status"->"Failed")
        	out.println(Json.toJson(Map("travel"->maps)))
		}
    }
}