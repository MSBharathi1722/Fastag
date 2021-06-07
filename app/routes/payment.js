import Route from '@ember/routing/route';

export default class PaymentRoute extends Route {
  setupController(controller, model) {
    super.setupController(controller, model);

    this.controllerFor('payment').set('success', false);
    this.controllerFor('payment').set('valid', false);
    this.controllerFor('payment').set('amt', "");
    this.controllerFor('payment').set('mm', "");
    this.controllerFor('payment').set('yy', "");
    this.controllerFor('payment').set('cardNo', "");
    this.controllerFor('payment').set('cvv', "");
    this.controllerFor('payment').set('name', "");
  }
}
