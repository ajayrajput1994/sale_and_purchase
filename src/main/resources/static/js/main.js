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
  checkPhoneNumber()
})
function checkPhoneNumber() {

  // Allow only numbers for phone input and ensure it has exactly 10 digits
  let phoneEl = $("input[name='phone']");  
  
  function showMessage(element,messageText, className) {
    $(element).next(".message").remove(); 
      $("<div class='message' style='margin-top:5px,margin-left:2px;font-size:13px'></div>")
        .text(messageText).addClass(className).insertAfter(element)
        .fadeOut(3000, function() { $(this).remove(); });
  }
  phoneEl.on("input", function() {
      var phone = $(this).val().replace(/\D/g, '').substring(0, 10);
      $(this).val(phone);
      showMessage(this,phone.length !== 10 ? "Phone number must be exactly 10 digits." : "Phone number is valid.", phone.length !== 10 ? "error" : "success");
  }).on("keypress", function(e) {
      if (!/\d/.test(String.fromCharCode(e.keyCode || e.which))) {
          showMessage(this,"Only digits are allowed.", "error");
          return false;
      }
  });
  $("input[name='email']").on("input", function() {
    var email = $(this).val();
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    showMessage(this, emailPattern.test(email) ? "Email is valid." : "Invalid email format.", emailPattern.test(email) ? "success" : "error");
});
}; 
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

function convertFilesToBase64(files) {const promises = [];
  for (const file of files) {
      const promise = new Promise((resolve, reject) => {
          const reader = new FileReader();
          reader.onload = function(event) {
              resolve(event.target.result);
          };
          reader.onerror = function(error) {
              reject(error);
          };
          reader.readAsDataURL(file);
      });
      promises.push(promise);
  }
  return Promise.all(promises);
}

function compressImgAndConvertToBase64(files) {
  return new Promise((resolve, reject) => {
      let base64ImgList = [], processedImages = 0;

      for (let i = 0; i < files.length; i++) {
          const file = files[i];
          const reader = new FileReader();

          reader.onload = function (event) {
              const img = new Image();
              img.src = event.target.result;

              img.onload = function () {
                
                  console.log(`Og Image${i} size: ${file.size/1024} KB`);
                  compressImage(img, file);
              };
          };

          reader.readAsDataURL(file);
      }

      function compressImage(img, file) {
          const maxWidth = 800;  // Maximum width for resizing
          const maxHeight = 800; // Maximum height for resizing
          let width = img.width;
          let height = img.height;
          let quality = 0.9; // Initial quality for compression

          if (width > height) {
              if (width > maxWidth) {
                  height *= maxWidth / width;
                  width = maxWidth;
              }
          } else {
              if (height > maxHeight) {
                  width *= maxHeight / height;
                  height = maxHeight;
              }
          }

          const canvas = document.createElement('canvas');
          const ctx = canvas.getContext('2d');
          canvas.width = width;
          canvas.height = height;
          ctx.drawImage(img, 0, 0, width, height);

          function tryCompressAgain() {
              canvas.toBlob(function (blob) {
                  if (blob.size > 500000 && quality > 0.1) { // Compress further if still too large
                      quality -= 0.1;
                      tryCompressAgain();
                  } else if (blob.size <= 500000) {
                      console.log(`Compressed Image${processedImages} size: ${blob.size/1024} KB`);
                      const reader = new FileReader();
                      reader.onloadend = function () {
                          const base64data = reader.result;
                          base64ImgList.push(base64data);
                          processedImages++;

                          if (processedImages === files.length) {
                              resolve(base64ImgList);
                          }
                      };
                      reader.readAsDataURL(blob);
                  } else {
                      processedImages++;
                      if (processedImages === files.length) {
                          resolve(base64ImgList);
                      }
                  }
              }, 'image/jpeg', quality);
          }

          tryCompressAgain();
      }
  });
}



