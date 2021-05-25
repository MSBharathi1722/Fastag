import RESTSerializer from '@ember-data/serializer/rest';

export default class ReturnSerializer extends RESTSerializer {
  primaryKey = 'Mail';
}
