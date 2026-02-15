function validateForm(){

 let name=document.forms["form"]["name"].value;
 let email=document.forms["form"]["email"].value;
 let desc=document.forms["form"]["description"].value;

 if(name.length<3){
  alert("Name must be at least 3 characters");
  return false;
 }

 if(!email.includes("@") || !email.includes(".")){
  alert("Invalid email format");
  return false;
 }

 if(desc.length<10){
  alert("Description must be at least 10 characters");
  return false;
 }

 return true;
}
