import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';
export default class HomeRoute extends Route {
  @service user;
  async model(){
    const response = await fetch("http://localhost:8080/fastag/getDetails?mail="+this.user.mail);
    const data = await response.json();
    console.log(data);
    this.user.addRegNo(data.Reg_No);
    this.user.addAmount(data.Amount);
    this.user.addBalance(data.Avail_Bal);
    return data;
  }
}
