import Model,{attr} from '@ember-data/model';

export default class TravelDetailModel extends Model {
  @attr user_id;
  @attr place;
  @attr pin;
  @attr return_id;
  @attr amount;
  @attr status;
}
