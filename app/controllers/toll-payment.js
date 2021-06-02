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
      let response = this.store.peekRecord('user-detail', 1);
      if(parseInt(response.avail_bal) > parseInt(response.amount)){
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
    var response = this.store.createRecord('travel-detail',{id : this.user.emberId});
    this.user.addEmberId(this.user.emberId+1);
    response.user_id = this.user.id;
    response.place = this.place;
    response.save()
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
