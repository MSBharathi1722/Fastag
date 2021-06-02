import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';

export default class HomeRoute extends Route {
  @service user;
  model(){
    var response = this.store.queryRecord('user-detail', { mail : this.user.mail })
    .then((funct)=> {
      let user_id = funct.get("user_id");
      this.user.addUserId(user_id);
      return funct;
    });
    return response;
  }
}
