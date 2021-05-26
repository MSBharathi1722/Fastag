import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._
import java.time._
import java.time.format.DateTimeFormatter

class ReturnTime extends HttpServlet{
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val mail: String = request.getParameter("mail")
		val place: String = request.getParameter("place")
	    response.setContentType("applicaton/json")
	    response.setCharacterEncoding("utf-8")
		val out: PrintWriter = response.getWriter
		val db: ReturnDb = new ReturnDb()
		
		val time: String = db.getRecord(mail,place)
		var maps = Map(("Mail"->mail),("Time"->time))
		out.println(Json.toJson(Map("return"->maps)))
	}
}