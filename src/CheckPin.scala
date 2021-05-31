import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class CheckPin extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
    val mail: String = request.getParameter("mail")
    val pin: String = request.getParameter("pin")
    val db: DBHandler = new DBHandler()
    response.setContentType("applicaton/json")
    response.setCharacterEncoding("utf-8")
    val out: PrintWriter = response.getWriter
    var id = db.getUserId(mail)
    val status = db.checkPin(id,pin)
    
    var maps = Map("id"->id.toString(),"status"->status)
    out.println(Json.toJson(Map("checkpin"->maps)))

  }
}