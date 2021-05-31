import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class ReturnController extends Controller {
  @service user;
  @service router;
  @tracked content = false;
  @tracked receipt = false;

  @tracked data = this.model;
  @tracked details = this.store.peekRecord('get-detail', this.user.mail);

  @action
  pay(selectedId){
    var selectedArray = this.data.find(({id}) => id == selectedId);
    this.store.queryRecord('update-return', {mail : this.user.mail, id : selectedId })
    .then((funct)=>{
      this.content = true;
      this.receipt = true;
    })
  }

  @action
  reload(){
    this.content = false;
    this.receipt = false;
    this.place="";
    this.pin="";
  }
}
