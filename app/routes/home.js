import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';
export default class HomeRoute extends Route {
  @service user;
  model(){
    var response = this.store.queryRecord('get-detail', { mail : this.user.mail });
    console.log(response);
    return response;
  }
}
