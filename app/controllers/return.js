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
  @tracked details = this.store.peekRecord('user-detail', this.user.id);

  @action
  pay(selectedId){
    var selectedArray = this.data.find(({id}) => id == selectedId);
    var response = this.store.createRecord('travel-detail', {id : this.user.emberId});
    this.user.addEmberId(this.user.emberId+1)
    response.user_id = this.user.id;
    response.return_id = selectedId;
    response.save()
    .then((funct)=>{
      let state = funct.get('status');
      if(state == 'true'){
        this.content = true;
        this.receipt = true;
      }else{
        alert("Unfortunately payment failed, try again");
      }

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
