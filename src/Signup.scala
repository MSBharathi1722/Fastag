import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._


class Signup extends HttpServlet {

  override def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    response.setContentType("applicaton/json")
    response.setCharacterEncoding("utf-8")
    val out: PrintWriter = response.getWriter

    val reader = request.getReader()
    val payloadJson = Json.parse(reader.readLine())
    val json = payloadJson("newRecord")

    val mail = json("mail").as[String]
    val password = json("pwd").as[String]
    var mobile_number = (json("mobile").as[String])
    val name = json("name").as[String]
    val vehicle_type = json("type").as[String]
    val reg_no = json("regNo").as[String]
    val owner_name = json("ownerName").as[String]
    val pin = json("pin").as[String]

    val db: DBHandler = new DBHandler()

    val out0 = db.insertUser(mail)
    var id = db.getUserId(mail).toInt
    //id = id.toInt
    val out1:String = db.insertUserRecord(name,mail,mobile_number,password)
    val out2:String = db.insertVehicleRecord(id,vehicle_type,reg_no)
    val out3:String = db.insertBalanceRecord(id,pin)

    if(out1 == "true" && out2 == "true" && out3 == "true"){
        var maps = Map("status"->"true")
        out.println(Json.toJson(maps))
    }
    else{
        var maps = Map("status"->out1,"status1"->out2,"status3"->out3)
        out.println(Json.toJson(maps))
    }
  }
}