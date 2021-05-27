import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class ReturnController extends Controller {
  @service user;
  @service router;
  @tracked content = false;
  @tracked receipt = false;

  @tracked data = this.store.peekAll('return');
  @tracked firstData = this.data.find(({ id }) => id == 1);
  @tracked secondData = this.data.find(({ id }) => id == 2);
  @tracked thirdData = this.data.find(({ id }) => id == 3);
  @tracked fourthData = this.data.find(({ id}) => id == 4);
  @tracked fifthData = this.data.find(({ id}) => id == 5);

  @tracked details = this.store.peekRecord('get-detail', this.user.mail);

  @action
  pay(selectedId){
    var selectedArray = this.data.find(({id}) => id == selectedId);
    this.store.queryRecord('travel', { mail : this.user.mail, place : selectedArray.Place , time : new Date().getTime()});
    this.content = true;
    this.receipt = true;
    if((parseInt(new Date().getTime())- parseInt(selectedArray.Seconds)) < 1800000){
      this.true();
    }else{
      this.false();
    }
  }
  true(){
    this.details.Avail_Bal = parseInt((parseInt(data.Avail_Bal) - (parseInt(data.Amount))/2)).toString();
    this.details.save();
  }
  false(){
    this.details.Avail_Bal = (parseInt(parseInt(data.Avail_Bal) - parseInt(data.Amount))).toString();
    this.details.save();
  }
  
  @action
  reload(){
    this.content = false;
    this.receipt = false;
    this.place="";
    this.pin="";
  }
}
