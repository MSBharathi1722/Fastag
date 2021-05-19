import { module, test } from 'qunit';
import { setupTest } from 'ember-qunit';

module('Unit | Route | tollPayment', function(hooks) {
  setupTest(hooks);

  test('it exists', function(assert) {
    let route = this.owner.lookup('route:toll-payment');
    assert.ok(route);
  });
});
