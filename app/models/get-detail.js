import Model from '@ember-data/model';
import { attr } from '@ember-data/model';
export default class GetDetailModel extends Model {
  @attr Name;
  @attr Mail;
  @attr Mobile;
  @attr Reg_No;
  @attr Avail_Bal;
  @attr Amount;
  @attr Pin;
}
