import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
export default class HomeController extends Controller {
  @service user;
  @service router;

  @tracked name = this.model.Name;
  @tracked balance = this.model.Avail_Bal;
  @tracked mobile = this.model.Mobile;
  @tracked regNo = this.model.Reg_No;
  @tracked type = this.model.Type;
  @tracked ownerName = this.model.Owner_Name;
  @tracked tollAmount = this.model.Amount;

  @tracked return=false;

  @action
  returns(){
    // const response = await fetch("http://localhost:8080/fastag/return?mail="+this.user.mail+"&amt="+this.user.amount);
    // const data = await response.json();
    // console.log(data);
    // if(data.status == "true"){
    //   alert("You are eligible to pass the toll for free");
    //   this.router.transitionTo("receipt");
    // }else if(data.status == "false"){
    //   alert("Time crossed 30 minutes");
    //   this.router.transitionTo("tollPayment");
    // }else{
    //   alert("Invalid record");
    //   this.router.transitionTo("tollPayment");
    // }
  }

}
