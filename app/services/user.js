import Service from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class UserService extends Service {
  @tracked userId ;
  @tracked mail ;
  @tracked emberId=1;

  addUserId(item) {
    this.userId=item;
  }
  addMail(item){
    this.mail=item;
  }
  addEmberId(id){
    this.emberId = id;
  }
}
