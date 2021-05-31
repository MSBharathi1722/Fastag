import Controller from '@ember/controller';
import { inject as service } from '@ember/service';

export default class IndexController extends Controller {
  @service user;
  @service router;
  async validate(){
    const response = await fetch("http://localhost:8080/fastag/login?mail="+this.mail+"&pwd="+this.pwd);
    const data = await response.json();
    console.log(data);
    if(data.status === 'true'){
      this.user.addMail(this.mail);
      this.router.transitionTo("home");
      this.mail="";
      this.pwd="";
    }
    else{
      alert("Invalid Credentials");
    }
  }
}
