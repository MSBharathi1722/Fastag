import Controller from '@ember/controller';
import { action } from '@ember/object';
import { tracked } from '@glimmer/tracking';
import { inject as service } from '@ember/service';
export default class TollPaymentController extends Controller {
  @service router;
  @service user;
  @tracked time ;
  @tracked first = false;
  @tracked second = false;
  @tracked receipt = false;
  @action
  validate(){
    if(this.place != null){
      //this.first=true;
      this.second=true;
    }
  }
  pay(){
    let response = this.store.peekRecord('get-detail', this.user.mail);
    if(parseInt(response.Avail_Bal) > parseInt(response.Amount)){
      if(response.Pin === this.pin){
        response.Avail_Bal = (parseInt(response.Avail_Bal) - parseInt(response.Amount)).toString();
        response.save();
        this.second = false;
        this.time =new Date().getTime();;
        this.store.queryRecord('travel', { mail : this.user.mail, place : this.place , time : this.time});
        this.receipt = true;
      }else{
        alert("Invalid Pin");
      }
    }else{
      alert("You Don't have enough Balance");
      this.router.transitionTo("home");
      this.reload();
    }
  }
  @action
  reload(){
    this.first = false;
    this.second = false;
    this.receipt = false;
    this.place="";
    this.pin="";
  }
}
