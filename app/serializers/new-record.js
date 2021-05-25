import RESTAdapter from '@ember-data/serializer/rest';

export default class NewRecordSerializer extends RESTAdapter {
  primaryKey = 'Mail';
}
