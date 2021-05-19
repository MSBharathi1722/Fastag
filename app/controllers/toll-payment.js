import Controller from '@ember/controller';
import { action } from '@ember/object';
import { tracked } from '@glimmer/tracking';
import { inject as service } from '@ember/service';
export default class TollPaymentController extends Controller {
  @service router;
  @service user;
  @tracked balance = parseInt(parseInt(this.user.balance) - parseInt(this.user.amount));
  @tracked first = false;
  @tracked second = false;
  @action
  validate(){
    if(this.place != null){
      this.first=true;
      this.second=true;
    }
  }
  async check(){
    console.log(this.user.mail);
    const response1 = await fetch("http://localhost:8080/fastag/check?mail="+this.user.mail+"&pin="+this.pin);
    const data1 = await response1.json();
    console.log(data1);
    if(data1.Status === "true"){
      const response2 = await fetch("http://localhost:8080/fastag/tollpass?mail="+this.user.mail+"&place="+this.place);
      const data2 = await response2.json();
      console.log(data2);
      if(data2.status === "true"){
        const response3 = await fetch("http://localhost:8080/fastag/pay?mail="+this.user.mail+"&amt="+this.balance);
        const data3 = await response3.json();
        console.log(data3);
        if(data3.status === "true"){
          this.router.transitionTo("receipt");
        }
      }else{
        alert("payment Failed");
      }
    }
    else{
      alert("Invalid Credentials");
    }
  }
}
