import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class Details extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val mail: String = request.getParameter("mail")
	    response.setContentType("applicaton/json")
		val out: PrintWriter = response.getWriter
		val detail: detailsDb = new detailsDb()
		var name = detail.userQuery(mail,"Name")
		var mobile = detail.userQuery(mail,"Mobile")
		var types = detail.vehicleQuery(mail,"Type")
		var regNo = detail.vehicleQuery(mail,"Reg_No")
		var ownerName = detail.vehicleQuery(mail,"Owner_Name")
		var tollamt = detail.vehicleQuery(mail,"Amount")
		var balance = detail.selectQuery(mail)
		var maps = Map(("Name"->name),("Mobile"-> mobile),("Type"-> types),("Reg_No"->regNo),("Owner_Name"->ownerName),("Amount"->tollamt),("Avail_Bal"->balance))
      	out.println(Json.toJson(maps))
    }
}