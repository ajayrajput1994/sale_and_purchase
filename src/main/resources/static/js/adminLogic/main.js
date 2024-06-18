var errorList=[],cat=[];
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
function showAndHideAnyDom(showList,hideList){
  // console.log('main.js',showList,hideList);
  showList.forEach(list => {
    // document.getElementById(list).style.display = 'none';
     $(`#${list}`).show();
  });
  hideList.forEach(list => {
    $(`#${list}`).hide();
  });
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
  formData=$(`#${formid}`).serializeArray();
  formData.forEach((e)=>{
   // jsonDict[e['name']]=e['value'];
   //var type=input.prop("type"); // get input type
    var input=$(`input[name=${e['name']}]`);
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
