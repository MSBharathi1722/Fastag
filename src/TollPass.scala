import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._
import java.time._
import java.time.format.DateTimeFormatter

class TollPass extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {

		response.setContentType("applicaton/json")
	    response.setCharacterEncoding("utf-8")
		val out: PrintWriter = response.getWriter
		val db: DBHandler = new DBHandler()

		val mail = request.getParameter("mail")
		val place = request.getParameter("place")

		var id = db.getUserId(mail)

		val status:String = db.insertTravelDetails(id,place)
		if(status == "true"){
			val vehicle_type = db.getVehicleType(id)
			val balance = db.getBalance(id)
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

			var state = db.updateBalance(id,updatedAmount)
			var maps = Map("id"->id.toString(),"status"->state)
        	out.println(Json.toJson(Map("travel"->maps)))
		}else{
			var maps = Map("id"->id.toString(),"status"->status)
        	out.println(Json.toJson(Map("travel"->maps)))	
		}
		
    }
    
}
