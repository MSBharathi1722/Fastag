import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class Check extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val pin: String = request.getParameter("pin")
		val mail: String = request.getParameter("mail")
	    response.setContentType("applicaton/json")
		val out: PrintWriter = response.getWriter
		val chk: CheckPin = new CheckPin()
		var pinNum = chk.selectQuery(mail)

		if(pin.equals(pinNum)){
			var maps = Map("Status"->"true")
			out.println(Json.toJson(maps))
		}else{
			var maps = Map("Status"->"false")
			out.println(Json.toJson(maps))
		}
      	
    }
}