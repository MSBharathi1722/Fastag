import Route from '@ember/routing/route';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class ReturnRoute extends Route {
  @service user;
  @service router;
  @tracked content = false;
  @tracked receipt = false;
  //@tracked place;
  // @action
  //  async check(){
  //   console.log(this.place)
  //   const response = await fetch("http://localhost:8080/fastag/return?mail="+this.user.mail+"&amt="+this.user.amount+"&place="+this.place);
  //   const data = await response.json();
  //   console.log(data);
  //   if(data.status == "true"){
  //     this.content = true;
  //     this.receipt = true;
  //   }
  // }
}
