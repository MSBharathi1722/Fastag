import Service from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class UserService extends Service {
  @tracked userId ;
  @tracked mail ;

  addUserId(item) {
    this.userId=item;
  }
  addMail(item){
    this.mail=item;
  }
}
