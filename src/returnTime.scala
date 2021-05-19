import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._
import java.time._
import java.time.format.DateTimeFormatter

class returnTime extends HttpServlet{
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val mail: String = request.getParameter("mail")
		val amount: String = request.getParameter("amt")
		val place: String = request.getParameter("place")
	    response.setContentType("applicaton/json")
	    response.setCharacterEncoding("utf-8")
		val out: PrintWriter = response.getWriter
		val db: returnDb = new returnDb()

		val time1:String = db.getRecord(mail,place)
		if(time1 != null){
			val hours1 = (time1.substring(0,2)).toInt
			val mins1 = (time1.substring(3,5)).toInt
			val timeTaken1 = ((hours1*60)+mins1)

	        val time2: String = timeNow()
	        val hours2 = (time2.substring(0,2)).toInt
			val mins2 = (time2.substring(3,5)).toInt
			val timeTaken2 = ((hours2*60)+mins2)

			val timeCheck = (timeTaken2 - timeTaken1)

			if(timeCheck < 30){
				val bal:String = db.selectBalance(mail)
				val temp:String = amount
				var half:String = null
			    temp match {
			      case "85" => half = "40"
			      case "135" => half = "70"
			      case "285" => half = "140"
			      case "315" => half = "150"
			      case "450" => half = "225"
			      case "550" => half = "275"
			    }
				val newBal:Int = bal.toInt + half.toInt
				val balStr:String = newBal.toString
				val status:String = db.updateBalance(mail,balStr)
				if(status == "true"){
					var maps = Map("status"->"true")
	        		out.println(Json.toJson(maps))
				}else{
					var maps = Map("status"->"updation failed")
	        		out.println(Json.toJson(maps))
				}
				
			}else if(timeCheck > 30){
				var maps = Map("status"->"false")
	        	out.println(Json.toJson(maps))
			}else{
				var maps = Map("status"->"invalid")
	        	out.println(Json.toJson(maps))
			}
		}else{
			var maps = Map("status"->"invalid")
	        	out.println(Json.toJson(maps))
		}
    }
    def timeNow()={
    	val time: LocalTime = LocalTime.now()
		val formatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
	    val value: String = time.format(formatter)
	    value.substring(0,5)
    }
}