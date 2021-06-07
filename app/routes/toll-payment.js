import Route from '@ember/routing/route';
export default class TollPaymentRoute extends Route {
  setupController(controller, model) {
    super.setupController(controller, model);

    this.controllerFor('toll-payment').set('first', false);
    this.controllerFor('toll-payment').set('receipt', false);
    this.controllerFor('toll-payment').set('place', "");
    this.controllerFor('toll-payment').set('pin', "");
  }
}
