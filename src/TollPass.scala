import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._
import java.time._
import java.time.format.DateTimeFormatter

class TollPass extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) {

		response.setContentType("applicaton/json")
	    response.setCharacterEncoding("utf-8")
		val out: PrintWriter = response.getWriter
		val db: DBHandler = new DBHandler()

		val mail = request.getParameter("mail")
		val place = request.getParameter("place")
		val tim = request.getParameter("time")
		val time = timeNow()


		val status:String = db.insertTravelDetails(mail,place,time,tim)
		var maps = Map("status"->status)
        out.println(Json.toJson(maps))
    }
    def timeNow()={
    	val time: LocalTime = LocalTime.now()
		val formatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
	    val value: String = time.format(formatter)
	    value.substring(0,5)
    }
}


