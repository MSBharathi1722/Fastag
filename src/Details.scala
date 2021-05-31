import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._
import java.util.regex._

class Details extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val mail: String = request.getParameter("mail").toLowerCase()
	    response.setContentType("applicaton/json")
		val out: PrintWriter = response.getWriter
		val db: DBHandler = new DBHandler()

		var id = db.getUserId(mail)
      	
		var details = db.getUserDetail(id)

      	out.println(Json.toJson(Map("getDetail"->details)))
    }
}