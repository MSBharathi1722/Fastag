import Model from '@ember-data/model';
import { attr } from '@ember-data/model';
export default class GetDetailModel extends Model {
  @attr Name;
  @attr Mail;
  @attr Reg_No;
  @attr Avail_Bal;
  @attr Amount;
}
