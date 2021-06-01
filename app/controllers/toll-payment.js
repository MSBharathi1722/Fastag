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
      console.log(this.user.id)
      let response = this.store.peekRecord('get-detail', 1);
      if(parseInt(response.Avail_Bal) > parseInt(response.Amount)){
        var checkedDetails = this.store.queryRecord('checkpin',{userId : this.user.id , pin:this.pin})
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
    var response = this.store.queryRecord('update',{userId : this.user.id, place:this.place})
    .then((func)=> {
      let state = func.get('status');
      if(state == "true"){
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
