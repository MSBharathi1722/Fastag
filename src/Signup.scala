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

    val mail = json("mail").as[String].toLowerCase()
    val password = json("pwd").as[String]
    var mobile_number = (json("mobile").as[String])
    val name = json("name").as[String]
    val vehicle_type = json("type").as[String]
    val reg_no = json("regNo").as[String].toUpperCase()
    val owner_name = json("ownerName").as[String]
    val pin = json("pin").as[String]

    if(isValidMail(mail)){
        val db: DBHandler = new DBHandler()
        

        val out1:String = db.insertUserRecord(name,mail,mobile_number,password)
        var id = db.getUserId(mail)
        val out2:String = db.insertVehicleRecord(id.toInt,vehicle_type,reg_no)
        val out3:String = db.insertBalanceRecord(id.toInt,pin)

        if(out1 == "true" && out2 == "true" && out3 == "true"){
            var maps = Map("status"->"true","id"->"1")
            out.println(Json.toJson(Map("travel"->maps)))
        }
        else{
            var maps = Map("status"->out1,"status1"->out2,"status3"->out3)
            out.println(Json.toJson(maps))
        }
    }else{
        var maps = Map("status"->"Invalid Mail Id")
        out.println(Json.toJson(maps))
    }
    
  }
  def isValidMail(mail:String): Boolean ={
    true
  }  
}