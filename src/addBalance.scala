import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class addBalance extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val pin: String = request.getParameter("pin")
		val mail: String = request.getParameter("mail")
		val amount: String = request.getParameter("amt")
	    response.setContentType("applicaton/json")
		val out: PrintWriter = response.getWriter
		val chk: addBalanceDb = new addBalanceDb()
		var pinNum = chk.selectQuery(mail)
		var availBal = chk.selectBalance(mail)
		val updateBal:Int = availBal.toInt + amount.toInt
		val strBal:String = updateBal.toString

		if(pin.equals(pinNum)){
			val status:String = chk.updateBalance(mail,strBal)
			if(status == "true"){
				var maps = Map("Status"->"true")
				out.println(Json.toJson(maps))
			}
		}else{
			var maps = Map("Status"->"false")
			out.println(Json.toJson(maps))
		}
      	
    }
}