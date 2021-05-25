import Model, { attr } from '@ember-data/model';

export default class NewRecordModel extends Model {
  @attr mail;
  @attr name;
  @attr mobile;
  @attr pin;
  @attr pwd;
  @attr regNo;
  @attr ownerName;
  @attr type;
}
