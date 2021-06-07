import Service from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class UserService extends Service {
  @tracked userId ;
  @tracked mail ;

  addUserId(item) {
    this.userId=item;
    console.log(this.userId);
  }
  addMail(item){
    this.mail=item;
  }
}
