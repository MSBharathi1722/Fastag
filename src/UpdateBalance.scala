import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class UpdateBalance extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val mail: String = request.getParameter("mail")
		val amt:Int = request.getParameter("amt").toInt
	    response.setContentType("applicaton/json")
		val out: PrintWriter = response.getWriter
		val db: DBHandler = new DBHandler()

		var id = db.getUserId(mail)
      	var currenBalance = db.getBalance(id)
      	var updatedBalance = currenBalance + amt
		var status = db.updateBalance(id,updatedBalance)
		var maps = Map("id"->id.toString(),"status"->status)
      	out.println(Json.toJson(Map("update"->maps)))
    }
}