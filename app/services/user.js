import Service from '@ember/service';
import { tracked } from '@glimmer/tracking';
export default class UserService extends Service {
  @tracked regNo ;
  @tracked mail ;
  @tracked amount;
  @tracked balance;

  addUserId(item) {
    this.id=item;
  }
  addMail(item){
    this.mail=item;
  }
  addAmount(item){
    this.amount=item;
  }
  addBalance(item){
    this.balance=item;
  }
}
