import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class ReturnController extends Controller {
  @service user;
  @service router;
  @tracked content = false;
  @tracked receipt = false;
  @action
  validate(){
    var response = this.store.queryRecord('return', { mail : this.user.mail, place: this.place})
    .then((retur) => {
      let time = retur.get('Time');
      if((parseInt(new Date().getTime()) - parseInt(time)) < 1800000){
        this.true();
      }else{
        this.false();
      }
    });
  }
  true(){
      this.content = true;
      this.receipt = true;
      let data = this.store.peekRecord('get-detail', this.user.mail);
      data.Avail_Bal = parseInt((parseInt(data.Avail_Bal) + (parseInt(data.Amount))/2)).toString();
      data.save();
  }
  false(){
    alert("Not a valid Return");
    this.router.transitionTo("home");
  }
  @action
  reload(){
    this.content = false;
    this.receipt = false;
    this.place="";
    this.pin="";
  }
}
