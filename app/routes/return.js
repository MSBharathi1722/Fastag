import Route from '@ember/routing/route';
import { tracked } from '@glimmer/tracking';
export default class ReturnRoute extends Route {

  @tracked content = false;
  @tracked receipt = false;
}
