import Controller from '@ember/controller';
import { action } from '@ember/object';
import { tracked } from '@glimmer/tracking';
import { inject as service } from '@ember/service';
export default class TollPaymentController extends Controller {
  @service router;
  @service user;
  @tracked time ;
  @tracked first = false;
  @tracked receipt = false;
  @action
  validatePlace(){
    if(this.place != null){
      return true;
    }else{
      return false;
    }
  }
  pay(){
    if(this.validatePlace()){
      let response = this.store.peekRecord('get-detail', this.user.mail);
      if(parseInt(response.Avail_Bal) > parseInt(response.Amount)){
        var checkedDetails = this.store.queryRecord('checkpin',{mail : this.user.mail , pin:this.pin})
        .then((funct)=> {
          let state = funct.get('status');
          if(state == "true"){
            this.proceed();
          }else{
            alert("Invalid Pin");
          }
        });
      }else{
        alert("You Don't have enough Balance");
        this.router.transitionTo("home");
        this.reload();
      }
    }else{
      alert("Enter a valid Toll Location");
    }
  }
  proceed(){
    this.store.queryRecord('travel',{mail : this.user.mail, place:this.place})
    .then((funct)=>{
      let status = funct.get('status');
      if(status == "true"){
        this.first = true;
        this.receipt = true;
      }else{
        alert("Unfortunately Payment failed");
        this.router.transitionTo("home");
        this.reload();
      }
    });
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
