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
		var balance = detail.selectQuery(mail,"Avail_Bal")
		var pin = detail.selectQuery(mail,"Pin")
		var maps = Map(("id"->"1"),("Name"->name),("Mobile"-> mobile),("Mail"->mail),("Type"-> types),("Reg_No"->regNo),("Owner_Name"->ownerName),("Amount"->tollamt),("Pin"->pin),("Avail_Bal"->balance))
      	
      	out.println(Json.toJson(Map("getDetail"->maps)))
    }
    override def doPut(request: HttpServletRequest, response: HttpServletResponse){
    	response.setContentType("applicaton/json")
		val out: PrintWriter = response.getWriter
    	val reader = request.getReader()
		val payloadJson = Json.parse(reader.readLine())
		val json = payloadJson("getDetail")
		val mail = json("Mail").as[String]
		val availBal = json("Avail_Bal").as[String]
		val db: paymentDb = new paymentDb()
		val status:String = db.updateBalance(mail,availBal)
		var maps = Map("status"->mail)
        out.println(Json.toJson(maps))
    }
}