import javax.servlet._
import javax.servlet.http._
import java.io._
import play.api.libs.json._
import scala.collection._

class TravelDetail extends HttpServlet {
	override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
  	    response.setContentType("applicaton/json")
        response.setCharacterEncoding("utf-8")
        val db: DBHandler = new DBHandler()
        val out: PrintWriter = response.getWriter
        val user_id = request.getParameter("user_id").toInt
        val result = db.getTravelDetails(user_id)
		out.println(Json.toJson(Map("travelDetail"->result)))
	}

    override def doPost(request: HttpServletRequest, response: HttpServletResponse) = {
  	    response.setContentType("applicaton/json")
        response.setCharacterEncoding("utf-8")
        val db: DBHandler = new DBHandler()
        val out: PrintWriter = response.getWriter

        val reader = request.getReader()
        val payloadJson = Json.parse(reader.readLine())
        val json = payloadJson("travelDetail")

        val requestFor = json("request_for").as[String]
        val user_id = json("user_id").as[String]

        var maps = Map("id"->user_id)

		if(requestFor == "payment"){			
			var amount = json("amount").as[String]
			val status = payment(user_id.toInt,amount.toInt)
			maps+=("status"->status)    
	    }

	    else if(requestFor == "tollPayment"){
	    	val pin = json("pin").as[String]
	    	val place = json("place").as[String]

	    	val status = newTollPayment(pin,user_id.toInt,place)
	    	maps+=("status"->status)  		
	    }

	    else if(requestFor == "returnPayment"){
	    	val id = json("return_id").as[String]

	    	val status = returnPayment(user_id.toInt,id.toInt)
			maps+=("status"->status)	    		
	    }

	    out.println(Json.toJson(Map("travelDetail"->maps)))
    }
    
    def payment(user_id:Int , amount:Int):String = {
    	val db: DBHandler = new DBHandler()
    	val state = db.updateBalance(user_id,amount,"+")
    	state
    }
    
    def newTollPayment(pin : String, user_id : Int, place: String):String ={
        val db: DBHandler = new DBHandler()
        var state = "null"
        if(IsCorrectPin(user_id,pin) == "true"){
        	val status:String = db.insertTravelDetails(user_id,place)
        	if(status == "true"){
        		val amount = getAmount(user_id)
        		state = db.updateBalance(user_id,amount,"-")
        	}
        }
        state
    }
    
    def returnPayment(user_id:Int, return_id:Int) : String = {
    	val db: DBHandler = new DBHandler()
    	val status: String = db.updateReturn(return_id)
    	var state = "null"
    	if(status == "true"){

			val amount = getAmount(user_id)
        	state = db.updateBalance(user_id,amount,"-")
        	  		

    		/*val time_balance_amount = db.getTimeBalanceAmount(return_id)
			
			val time = time_balance_amount("Time").toString()
			val amt = time_balance_amount("Amount").toString()
			val balance = (time_balance_amount("Balance").toString())

			val timeDifference:Int = db.getTimeDifference(time,"CURRENT_TIMESTAMP")

			if(timeDifference == 1){
				var updatedAmount:Int = balance.toInt - (amt.toInt/2).toInt
				state = db.updateBalance(user_id.toInt,updatedAmount,"-")
			}
			else if(timeDifference == 0){
				var updatedAmount:Int = (balance.toInt - amt.toInt)
				state = db.updateBalance(user_id.toInt,updatedAmount,"-")
			}
			else{
				state = "Failed"
			}*/
    	}
    	state
    }
    def IsCorrectPin(user_id : Int, pin : String):String = {
    	val db: DBHandler = new DBHandler()
    	val isCorrectPin = db.checkPin(user_id,pin)
    	isCorrectPin
    }
    def getAmount(user_id : Int): Int = {
    	val db: DBHandler = new DBHandler()
    	val amount = db.getAmount(user_id)
    	amount
    }
}
