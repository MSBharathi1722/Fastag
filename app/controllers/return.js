import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class ReturnController extends Controller {
  @service user;
  @service router;
  @tracked timeNow = new Date().getTime();
  @tracked content = false;
  @tracked receipt = false;
  @action
  check(){
    let response = this.store.createRecord('return', { mail:this.user.mail });
    response.place = this.place;
    response.save();//.//then(this.start());
  }
  start(){
    let response = this.store.peekRecord('return', this.user.mail);
    console.log(response);
    if((parseInt(this.timeNow) - parseInt(response.Time)) < 1800000){
      this.content = true;
      this.receipt = true;
      let data = this.store.peekRecord('get-detail', this.user.mail);
      data.Avail_Bal = parseInt(data.Avail_Bal) - (parseInt(data.Amount))/2;
      this.content = false;
      this.receipt = false;
      this.place="";
    }else{
      alert("You Don't have a valid return pass");
      this.router.transitionTo("tollPayment");
      this.content = false;
      this.receipt = false;
    }
  }
}
