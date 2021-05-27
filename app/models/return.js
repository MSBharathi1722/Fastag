import Model, { attr } from '@ember-data/model';

export default class ReturnModel extends Model {
  @attr Place;
  @attr Seconds;
  @attr Time;
}
