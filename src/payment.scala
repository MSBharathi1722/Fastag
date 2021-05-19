import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class payment extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse){
		val mail: String = request.getParameter("mail")
		val amount: String = request.getParameter("amt")
		response.setContentType("applicaton/json")
	    response.setCharacterEncoding("utf-8")
		val out: PrintWriter = response.getWriter
		val db: paymentDb = new paymentDb()

		val status:String = db.updateBalance(mail,amount)
		var maps = Map("status"->status)
        out.println(Json.toJson(maps))
	}
}