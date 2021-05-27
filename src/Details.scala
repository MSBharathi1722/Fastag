import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class Details extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val mail: String = request.getParameter("mail")
	    response.setContentType("applicaton/json")
		val out: PrintWriter = response.getWriter
		val db: DBHandler = new DBHandler()

		var id = db.getUserId(mail).toInt

		var name = db.getUserRecord(mail,"name")
		var mobile = db.getUserRecord(mail,"mobile_number")

		var regNo = db.getVehicleRecord(id,"reg_no")
		var tollamt = db.getVehicleRecord(id,"amount")

		var balance = db.getBalanceRecord(id,"balance")
		var pin = db.getBalanceRecord(id,"pin")

		//val user_record = db.getUserRecord(mail)
		//val vehicle_record = db.getVehicleRecord(mail)
		//val balance_record = db.getBalanceRecord(mail)
		//var list1 = user_record.++(vehicle_record)
		//var list2 = list1.++(balance_record)

		var maps = Map(("id"->"1"),("Name"->name),("Mobile"-> mobile),("Mail"->mail),("Reg_No"->regNo),("Amount"->tollamt),("Pin"->pin),("Avail_Bal"->balance))
      	
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
		val db: DBHandler = new DBHandler()
		var id = db.getUserId(mail).toInt
		val status:String = db.updateBalance(id,availBal)
		var maps = Map("status"->mail)
        out.println(Json.toJson(maps))
    }
}