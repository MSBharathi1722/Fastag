import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._


class signup extends HttpServlet {

  override def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    response.setContentType("applicaton/json")
    response.setCharacterEncoding("utf-8")
    val out: PrintWriter = response.getWriter

    val reader = request.getReader()
    val payloadJson = Json.parse(reader.readLine())
    val json = payloadJson("newRecord")

    val mail: String = json("mail").as[String]
    val password: String = json("pwd").as[String]
    val mobile: String = json("mobile").as[String]
    val name: String = json("name").as[String]
    val types: String = json("type").as[String]
    val regNo: String = json("regNo").as[String]
    val ownerName: String = json("ownerName").as[String]
    val pin: String = json("pin").as[String]

    val dbs: newUserDb = new newUserDb()


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