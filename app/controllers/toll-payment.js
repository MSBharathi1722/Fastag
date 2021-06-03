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
      let data = this.store.peekRecord('user-detail', 1);
      if(parseInt(data.avail_bal) > parseInt(data.amount)){
        var response = this.store.createRecord('travel-detail');
        response.pin = this.pin;
        response.user_id = this.user.userId;
        response.place = this.place;
        response.save()
        .then((func)=> {
          let state = func.get('status');
          if(state == "Invalid Pin"){
            alert("Invalid Pin");
          }else{
            if(state == "true"){
              this.first = true;
              this.receipt = true;
            }else{
              alert("Unfortunately Payment failed");
              this.router.transitionTo("home");
              this.reload();
            }
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

  @action
  reload(){
    this.first = false;
    this.second = false;
    this.receipt = false;
    this.place="";
    this.pin="";
  }
}
