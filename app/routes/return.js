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
  setupController(controller, model) {
    super.setupController(controller, model);
    this.controllerFor('return').set('content', false);
    this.controllerFor('return').set('receipt', false);
    this.controllerFor('return').set('place', "");
    this.controllerFor('return').set('pin', "");
  }
}
