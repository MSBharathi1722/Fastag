import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class CheckUser extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
    val mail: String = request.getParameter("mail")
    val db: DBHandler = new DBHandler()
    response.setContentType("applicaton/json")
    response.setCharacterEncoding("utf-8")
    val out: PrintWriter = response.getWriter
    val status = db.checkUser(mail)
    
    var maps = Map(("Status"->status))
    out.println(Json.toJson(maps))

  }
}