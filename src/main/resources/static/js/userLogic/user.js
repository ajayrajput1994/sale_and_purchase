var loadedDTA={},
userInfo={},
addressList=[],
wishList=[],
blogDict={},
addressDict={};


function loadData(){
  // console.log('user',loadedDTA.user);
// addressList=loadedDTA.user.addresses;
setUserInfo(loadedDTA.user,true);
console.log(userInfo)
wishList=loadedDTA.user.wishlist;
loadedDTA.user.blog.forEach(d => blogDict[d.id]=d);
loadedDTA.user.addresses.forEach(d => addressDict[d.id]=d);
// console.log(addressDict);
// console.log(blogDict);

renderArticles();
renderAddresses();
}
function addAddress(){
  createPostRequest('addressForm','/user/Address/create','addAddressCB');
}
function addAddressCB(r){
  // console.log(r);
  let dta=r.data,
  d=addressDict[dta.id];
  d['name']=dta.name;
  d['phone']=dta.phone;
  d['landmark']=dta.landmark;
  d['pin_code']=dta.pin_code;
  d['other_phone']=dta.other_phone;
  d['region']=dta.region;
  d['state']=dta.state;
  d['city']=dta.city;
  d['address']=dta.address;
  d['address_type']=dta.address_type;
  // addressDict[dta.id]=d;
  $('#addressForm')[0].reset();
  renderAddresses();
  addressForm(false);
}

function updateInfo(){
  createPostRequest('userInfoForm','/user/info','infoCB');
}
function infoCB(r){
  console.log(r);
  setUserInfo(r.data,true);
  console.log(userInfo);
  infoForm(false);
}

function updatePassword(){
let pass1=$('#password').val(),
 pass2=$('#conf_password').val(),
 isTrue=true;
 if(pass1=="" || pass1==null){
  isTrue=false;
  toastr.warning("Password can't Empty");
 }
 if(pass2=="" || pass2==null){
  isTrue=false;
   toastr.warning("Confirm Password can't Empty");
  }
  if(pass1.length<4){
    isTrue=false;
   toastr.warning("Password length must be 4 Character");
 }
  if(pass1!=pass2){
    isTrue=false;
   toastr.warning("Confirm password is not matched");
 }
 if(isTrue){
   createPostRequest('passwordForm','/user/password','updatePasswordCB');
 }
}

function updatePasswordCB(r){
  console.log(r);
  $('#passwordForm')[0].reset();
  OpenHide('#passwordForm','#passwordFormDisplay');
}
