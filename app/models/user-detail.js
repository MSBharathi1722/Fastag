import Model,{ attr,hasMany } from '@ember-data/model';

export default class UserDetailModel extends Model {
  @attr user_id;
  @attr name;
  @attr mail;
  @attr reg_no;
  @attr avail_bal;
  @attr amount;
  @attr mobile;
  @attr pin;
  @attr pwd;
  @attr regNo;
  @attr ownerName;
  @attr type;
}
