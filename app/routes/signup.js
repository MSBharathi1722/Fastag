import Route from '@ember/routing/route';

export default class SignupRoute extends Route {
  setupController(controller, model) {
    super.setupController(controller, model);

    this.controllerFor('signup').set('first', false);
    this.controllerFor('signup').set('second', false);
    this.controllerFor('signup').set('third', "");
    this.controllerFor('signup').set('name', "");
    this.controllerFor('signup').set('mail', "");
    this.controllerFor('signup').set('mobile', "");
    this.controllerFor('signup').set('pwd', "");
    this.controllerFor('signup').set('ownerName', "");
    this.controllerFor('signup').set('regNo', "");
    this.controllerFor('signup').set('pin', "");
  }
}
