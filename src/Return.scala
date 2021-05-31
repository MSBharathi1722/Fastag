import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._
import java.time._
import java.time.format.DateTimeFormatter

class Return extends HttpServlet{
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val mail: String = request.getParameter("mail")
	    response.setContentType("applicaton/json")
	    response.setCharacterEncoding("utf-8")
		val out: PrintWriter = response.getWriter
		val db: DBHandler = new DBHandler()
		var id = db.getUserId(mail)
		val result = db.getTravelDetails(id)
		out.println(Json.toJson(Map("return"->result)))
	}
}