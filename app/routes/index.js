import Route from '@ember/routing/route';

export default class IndexRoute extends Route {

  setupController(controller, model) {
    super.setupController(controller, model);
    this.controllerFor('index').set('mail', "");
    this.controllerFor('index').set('pwd', "");
  }
}
