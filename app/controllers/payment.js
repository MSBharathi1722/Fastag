import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class PaymentController extends Controller {
  @service user;
  @service router;
  @tracked success=false;
  @tracked valid=false;
  @action
  recharge(){
    if(this.amt>0 && parseInt(this.amt)){
      if(this.isValidAccNo(this.cardNo)){
        if(this.cvv.length == 3){
          if(this.isValidDate(this.mm)){
            if(this.isValidDate(this.yy)){
              var response = this.store.createRecord('travel-detail');
              response.request_for = "payment";
              response.user_id = this.user.userId;
              response.amount = this.amt;
              response.save()
              .then((funct) =>{
                let status = funct.get('status')
                if(status == "true"){
                  this.valid=true;
                  this.success=true;
                }else{
                  alert("payment Failed, Try after some time")
                }
              });
            }else{
              alert("Enter a valid Expiry Year");
            }
          }else{
            alert("Enter a valid Expiry Month");
          }
        }else{
          alert("Enter a valid CVV Number");
        }
      }else{
        alert("Enter a valid Account Number");
      }
    }else{
      alert("Invalid Amount ")
    }
  }
  isValidAccNo(num){
    return (num.length == 16)/* && (typeof num == "number")*/;
  }
  isValidDate(num){
    return num.length == 2;
  }
}
