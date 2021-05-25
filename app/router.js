import EmberRouter from '@ember/routing/router';
import config from 'fastag/config/environment';

export default class Router extends EmberRouter {
  location = config.locationType;
  rootURL = config.rootURL;
}

Router.map(function () {
  this.route('signup');
  this.route('home');
  this.route('payment');
  this.route('tollPayment');
  this.route('return');
});
