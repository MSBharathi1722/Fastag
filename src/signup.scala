import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._


class signup extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse): Unit = {
    
    val mail: String = request.getParameter("mail")
    val password: String = request.getParameter("pwd")
    val mobile: String = request.getParameter("mobile")
    val name: String = request.getParameter("name")
    val types: String = request.getParameter("type")
    val regNo: String = request.getParameter("regNo")
    val ownerName: String = request.getParameter("ownerName")
    val pin: String = request.getParameter("pin")

    val dbs: newUserDb = new newUserDb()
    response.setContentType("applicaton/json")
    response.setCharacterEncoding("utf-8")
    val out: PrintWriter = response.getWriter

    val out1:String = dbs.insertUserDetails(name,mail,mobile,password)
    val out2:String = dbs.insertVehicleDetails(mail,types,regNo,ownerName)
    val out3:String = dbs.insertBalanceDetails(mail,pin)

    if(out1 == "true" && out2 == "true" && out3 == "true"){
        var maps = Map("status"->"true")
        out.println(Json.toJson(maps))
    }
    else{
        var maps = Map("status"->"false")
        out.println(Json.toJson(maps))
    }
  }
}