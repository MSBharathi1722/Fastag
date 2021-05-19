import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
export default class SignupController extends Controller {
  @service router;
  //@service user;
  @tracked first=false;
  @tracked second=false;
  @tracked third=false;
  @tracked type;

  @action
  choose(value){
    this.type=value;
  }

  @action
  firstValidate(){
    if(this.validateName(this.name)){
      if(this.validateEmail(this.mail)){
        if(this.validatePassword(this.pwd)){
          if(this.validateMobile(this.mobile)){
            this.first=true;
            this.second =true;
          }else{
            alert("Enter a valid Mobile Number")
          }
        }else{
          alert("Enter a valid Password")
        }
      }else{
        alert("Enter a valid Email")
      }
    }else{
      alert("Enter a valid Name")
    }
  }
  @action
  secondValidate(){
    if(this.validateRegNo(this.regNo)){
      if(this.validateName(this.ownerName)){
        this.second=false;
        this.third=true;
      }else{
        alert("Invalid Name Number")
      }
    }else{
      alert("Invalid Registration Number")
    }
  }

  @action
  async check(){
    const response = await fetch("http://localhost:8080/fastag/signup?mail="+this.mail+"&pwd="+this.pwd+"&mobile="+this.mobile+"&name="+this.name+"&type="+this.type+"&regNo="+this.regNo+"&ownerName="+this.ownerName+"&pin="+this.pin);
    const data = await response.json();
    console.log(data);
    if(data.status == "true"){
      alert("Fastag Account Created Successfully, login to continue");
      this.router.transitionTo("index");
    }
  }

  validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }
  validateName(nam){
    const letters = /^[A-Za-z]+$/;
    return nam.match(letters);
  }
  validatePassword(passw){
    return (passw.length > 7);
  }
  validateMobile(num){
    return true//(typeof num == "number");
  }
  validateRegNo(num){
    return true;
  }

}
