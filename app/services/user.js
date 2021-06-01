import Service from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class UserService extends Service {
  @tracked id ;
  @tracked mail ;
  @tracked amount;
  @tracked balance;

  addUserId(item) {
    this.id=item;
    console.log(this.id);
  }
  addMail(item){
    this.mail=item;
  }
}
