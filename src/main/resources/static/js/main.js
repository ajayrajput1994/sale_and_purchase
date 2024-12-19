var errorList=[],cat=[],
loadedUserDTA={};
$(function(){
  toastr.options = {
    "closeButton": false,
    "debug": true,
    "newestOnTop": false,
    "progressBar": true,
    "positionClass": "toast-top-center",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "2000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
  }
})
// function showAndHideAnyDom(showDom,hideDom){
//   $(showDom).show();
//   $(hideDom).hide();
// }
function OpenHide(show,hide){
  $(show).show();
  $(hide).hide();
}
function createGetRequest(formid,url,callBack){
  $("button").attr("disabled", true);
   formData=$(`#${formid}`).serialize();
  // formData=$(`#${formid}`).serializeArray();
  //jsonDict=formDataToJson(formid);
  // console.log(jsonDict);
  $.ajax({
    url:url,
    type:'GET',
    data:JSON.stringify(formData),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function(obj) {
      // console.log('success',obj); 
			if (callBack != null) {
				var callbackMethod = eval(callBack);
				callbackMethod(obj, formid);
        $(`#${formid}`)[0].reset();
			}
			$("button").attr("disabled", false);
			messageConfirmation(obj);
		}
  });

}

function createPostRequest(formid,url,callBack){
  $("button").attr("disabled", true);
  // formData=$(`#${formid}`).serialize();
  // formData=$(`#${formid}`).serializeArray();
  jsonDict=formDataToJson(formid);
  // console.log(jsonDict);
  $.ajax({
    url:url,
    type:'POST',
    data:JSON.stringify(jsonDict),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function(obj) {
      // console.log('success',obj); 
			if (callBack != null) {
				var callbackMethod = eval(callBack);
				callbackMethod(obj, formid);
        $(`#${formid}`)[0].reset();
			}
			$("button").attr("disabled", false);
			messageConfirmation(obj);
		}
  });

}

function uploadFiles(formid,url,callBack){
  $("button").attr("disabled", true);
  // var formData= new FormData();
  // var files=$('#file')[0].files;
  // console.log(files);
  // if(files.length>0){
  //   formData.append('logo',files[0]);
  formdata=$(`#${formid}`).serializeArray();
  $.ajax({
    url:url,
    type:'post',
    data:JSON.stringify(formdata),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    // enctype: 'multipart/form-data',
    // processData: false,
    // contentType: false,
    cache: false,
    success: function(obj) {
      // console.log('success',obj); 
			if (callBack != null) {
				var callbackMethod = eval(callBack);
				callbackMethod(obj, formid);
        $(`#${formid}`)[0].reset();
			}
			$("button").attr("disabled", false);
			messageConfirmation(obj);
		}
  });
}



function messageConfirmation(obj){
  if(obj.responseText=='SUCCESS'){
    toastr.success(obj.message);
  }else if(obj.responseText=='ERROR'){
    toastr.danger(obj.message);
  }else{
    toastr.warning(obj.message);
  }
}
function formDataToJson(formid){
  var jsonDict={};
  if(formid!=null){
    formData=$(`#${formid}`).serializeArray();
  formData.forEach((e)=>{
    jsonDict[e['name']]=e['value'];
  });
  }
return jsonDict;
}

function formValidate(formid){
 let formData=$(`#${formid}`).serializeArray();
  formData.forEach((e)=>{
    console.log(e);
   // jsonDict[e['name']]=e['value'];
   if(!$(this).prop('required')){
    console.log("NR");
  } else {
    console.log("IR");
  }
  let input=$(`input[name=${e['name']}]`);
  let type=input.prop("type"); // get input type
  console.log(type);
  console.log(input.prop('required'));
   if(input.prop('required')){// required element
    console.log(e['name'],'is required .');
   $(`input[name=${e['name']}]`).removeClass('border-danger');
   	if(e['value'].trim().length==0){
      errorList.push(e['name']);
      // console.log('empty');
      }}
  });
  if(errorList.length>0){
    errorList.forEach((e)=>{
      $(`input[name=${e}]`).addClass('border-danger');
      toastr.warning(`${e} is Empty`).delay(100);
    });
    errorList=[];
    return false;
  }
  return true;
}

function getDictLength(obj){
  return Object.entries(obj).length;
}

function getUniqueId(length){
  return Math.random().toString(36).substring(2, length + 2);
}

function setCookies(key, value) {
  const date = new Date();
  document.cookie = `${key}=${value}; expires=${new Date(date.getFullYear(), date.getMonth(), 29, 11, 0, 0).toUTCString()}; secure=true`;
}


function getCookie(key) {
  // clearAllCookies();
  let cookieDict={};
  const cookiesArray = document.cookie.split('; ');
  for (const cookie of cookiesArray) {
    const [name, value] = cookie.split('=');
    if (name === key) {
      let data=[];
      $.each(JSON.parse(value),(kt,dt)=>{
        dt.forEach(d=>{
          if(getDictLength(d)>0){
            data.push(d);
          }
        });
        cookieDict[kt]=data;
      });
      return cookieDict;
    }
  }
  return cookieDict; // Return null if the cookie is not found
}
function clearAllCookies() {
  const cookies = document.cookie.split(';');
  for (const cookie of cookies) {
    const [name] = cookie.trim().split('=');
    document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/;`;
  }
}

function getAllUniqueKeysFromListOfMap(arrayOfObjects) {
  const allKeys = new Set();
  console.log('getAllUniqueKeysFromListOfMap:',arrayOfObjects);
  if(arrayOfObjects.length>0){
    arrayOfObjects.forEach(obj => {
      const keys = Object.keys(obj);
      keys.forEach(key => allKeys.add(key));
    });

  }

  return Array.from(allKeys);
}
