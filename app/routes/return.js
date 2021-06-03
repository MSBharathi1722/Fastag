import Route from '@ember/routing/route';
import { tracked } from '@glimmer/tracking';
import { inject as service } from '@ember/service';
export default class ReturnRoute extends Route {
  @service user;
  @tracked content = false;
  @tracked receipt = false;
  model(){
    return this.store.query("travel-detail",{user_id : this.user.userId});
  }
}
