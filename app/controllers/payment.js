import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class PaymentController extends Controller {
  @service user;
  @tracked success=false;
  @tracked valid=false;
  @action
  async validate(){
    if(this.cardNo.length == 16 && this.cvv.length == 3){
      if(this.isvalidDate()){
        const response = await fetch("http://localhost:8080/fastag/addBal?mail="+this.user.mail+"&pin="+this.pin+"&amt="+this.amt);
        const data = await response.json();
        if(data.Status == 'true'){
          this.valid=true;
          this.success=true;
        }else{
          alert("invalid Pin");
        }
      }
    }
  }
  isvalidDate(dat){
    return true;
  }
}
