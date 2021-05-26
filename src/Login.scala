import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._

class Login extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse): Unit = {
    val mail: String = request.getParameter("mail")
    val password: String = request.getParameter("pwd")
    val dbs: UserDb = new UserDb()
    var passw: String = dbs.selectQuery(mail)
    response.setContentType("applicaton/json")
    response.setCharacterEncoding("utf-8")
    val out: PrintWriter = response.getWriter
    if (password == passw) {
	     var maps = Map(("Status"->"true"))
       out.println(Json.toJson(maps))
    } else {
      	var maps = Map(("Status"->"false"),("pass"->passw))
        out.println(Json.toJson(maps))
    }
  }
}