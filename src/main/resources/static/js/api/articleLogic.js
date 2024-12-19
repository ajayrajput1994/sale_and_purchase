var manCategoryList=[],
subDict={},
subCatList=[],
catList=[],
catArray=[],
articleEditFormHtml=`<form id="articleForm"> <div class="row" id="firstStep">
  <h4>Article Details </h4>
  <input type="hidden" name="id" id="article_id">
  <input type="hidden" name="user" id="user">
  <input type="hidden" name="action" id="action">
  <div class="form-group col-md-12">
    <label for="title" class="">Enter Title</label>
    <input type="text" name="title" id="title" class="form-control" required="">
  </div>
  <div class="form-group col-md-4">
    <label for="article_mainCategory" class="">Main Category</label>
    <select name="mainCategory" id="article_mainCategory" onchange="loadSubcat(this.value)"
      class="select form-control">
      <option disabled="" selected="" value="">Select main Category</option>
    </select>
  </div>
  <div class="form-group col-md-4">
    <label for="article_category" class="">Sub Category</label>
    <select name="category" id="article_category" class="select form-control"  required="">
      <option disabled="" selected="">Select sub Category</option>
    </select>
  </div>
  <div class="form-group col-md-6" style="display:none">
    <label class="active" for="article_region">Region</label>
    <input type="text" name="region" value="india" id="article_region" class="form-control">
  </div>
  <div class="form-group col-md-6" style="display:none">
    <label  for="article_state">State</label>
    <input type="text" name="regionState" id="article_state"  class=" form-control" required="">
  </div>
  <div class="form-group col-md-6" style="display:none">
    <label for="article_city">City</label>
    <input type="text"  name="city" id="article_city" class=" form-control"  required="">
  </div>
  <div class="form-group col-md-4">
    <label for="article_price" data-error="wrong" data-success="right">Enter Price</label>
    <input type="number" name="price" id="article_price" class="form-control validate" required="">
  </div>
  <div class="form-group col-md-12">
    <label for="article_desc">Enter Description</label>
    <textarea type="text" name="description" id="article_desc" class="md-textarea form-control" rows="3" required=""></textarea>
  </div>
  <div class="d-flex justify-content-end mt-3">
    <!-- <button type="button" class="btn btn-secondary btn-sm rounded-0 mr-5" onclick="gotoStep(false)">Previous</button> -->
    <button type="button" class="btn btn-info btn-sm rounded-0" onclick="gotoStep(3)" style="margin-left: 10px;">Next</button>
  </div>
</div>
<div class="row" id="thirdStep" style="display: none;">
  <h4>Article Images </h4>
  <div class="form-group col-md-4 mt-2 imagefiles">
    <input type="file" name="multipartFile" id="multipartFile" onchange="selectFile(this)" accept="image/*"  required="" multiple>
  </div>
  <div class="form-group col-md-8 mt-2 ">
  <div class="row multipartFile"></div>
  </div>
  
  <div class="d-flex justify-content-end mt-3">
    <button type="button" class="btn btn-secondary btn-sm rounded-0 mr-5" onclick="gotoStep(1)">Previous</button>
    <button type="button" class="btn btn-success btn-sm rounded-0" onclick="articleSubmit()"  style="margin-left: 10px;">Submit</button>
  </div>
</div></form>`,
articleNewFormHtml=`<div class="row" id="firstStep">
  <h4>Article Details </h4>
  <div class="form-group col-md-12">
    <label for="title" class="">Enter Title</label>
    <input type="text" name="title" id="title" class="form-control" required="">
  </div>
  <div class="form-group col-md-6">
    <label for="article_mainCategory" class="">Main Category</label>
    <select name="mainCategory" id="article_mainCategory" onchange="loadSubcat(this.value)"
      class="select form-control">
      <option disabled="" selected="" value="">Select main Category</option>
    </select>
  </div>
  <div class="form-group col-md-6">
    <label for="article_category" class="">Sub Category</label>
    <select name="category" id="article_category" class="select form-control"  required="">
      <option disabled="" selected="">Select sub Category</option>
    </select>
  </div>
  <div class="form-group col-md-6">
    <label class="active" for="article_region">Region</label>
    <input type="text" name="region" value="india" id="article_region" class="form-control">
  </div>
  <div class="form-group col-md-6">
    <label  for="article_state">State</label>
    <input type="text" name="regionState" id="article_state"  class=" form-control" required="">
  </div>
  <div class="form-group col-md-6">
    <label for="article_city">City</label>
    <input type="text"  name="city" id="article_city" class=" form-control"  required="">
  </div>
  <div class="form-group col-md-6">
    <label for="article_price" data-error="wrong" data-success="right">Enter Price</label>
    <input type="number" name="price" id="article_price" class="form-control validate" required="">
  </div>
  <div class="form-group col-md-12">
    <label for="article_desc">Enter Description</label>
    <textarea type="text" name="description" id="article_desc" class="md-textarea form-control" rows="3" required=""></textarea>
  </div>
  <div class="d-flex justify-content-end mt-3">
    <!-- <button type="button" class="btn btn-secondary btn-sm rounded-0 mr-5" onclick="gotoStep(false)">Previous</button> -->
    <button type="button" class="btn btn-info btn-sm rounded-0" onclick="gotoStep(2)" style="margin-left: 10px;">Next</button>
  </div>
</div>
<div class="row" id="secondStep" style="display: none;">
  <h4>Personal Details </h4>
  <div class="form-group col-md-6">
    <label for="article_name" data-error="wrong" data-success="right">Enter name</label>
    <input type="text" name="name" id="article_name" class="form-control validate" required="">
  </div>
  <div class="form-group col-md-6">
    <label for="article_email" data-error="wrong" data-success="right">Enter email</label>
    <input type="email" name="email" id="article_email" class="form-control validate" required="">
  </div>
  <div class="form-group col-md-6">
    <label for="article_phone" data-error="wrong" data-success="right">Enter phone</label>
    <input type="number" name="phone" id="article_phone" class="form-control validate" required="">
  </div>
  <div class="form-group col-md-6">
    <label for="article_passcode" data-error="wrong" data-success="right">Passcode (it's required for activities "wishlist,cart" without login)</label>
    <input type="number" name="passcode" id="article_passcode" class="form-control validate"  placeholder="Enter 5 Digit ">
  </div>
  <div class="form-group col-md-12">
    <label for="article_address">Enter Address</label>
    <textarea type="text" name="address" id="article_address" class="md-textarea form-control" rows="3" required=""></textarea>
  </div>
  <div class="d-flex justify-content-end mt-3">
    <button type="button" class="btn btn-secondary btn-sm rounded-0 mr-5" onclick="gotoStep(2)">Previous</button>
    <button type="button" class="btn btn-info btn-sm rounded-0" onclick="gotoStep(3)" style="margin-left: 10px;">Next</button>
  </div>
  
</div>
<div class="row" id="thirdStep" style="display: none;">
  <h4>Article Images </h4>
  <div class="form-group col-md-6">
    <input type="file" name="multipartFile" id="multipartFile"  required="">
  </div>
  <div class="form-group col-md-6">
    <input type="file" name="multipartFile1" id="multipartFile1">
  </div>
  <div class="form-group col-md-6">
    <input type="file" name="multipartFile2" id="multipartFile2">
  </div>
  <div class="form-group col-md-6">
    <input type="file" name="multipartFile3" id="multipartFile3">
  </div>
  <div class="form-group col-md-6">
    <input type="file" name="multipartFile4" id="multipartFile4">
  </div>
  <div class="form-group col-md-6">
    <input type="file" name="multipartFile5" id="multipartFile5">
  </div>
  
  <div class="d-flex justify-content-end mt-3">
    <button type="button" class="btn btn-secondary btn-sm rounded-0 mr-5" onclick="gotoStep(1)">Previous</button>
    <button class="btn btn-success btn-sm rounded-0"  style="margin-left: 10px;">Submit</button>
  </div>
</div>`;
$(function(){
  //articleLogin 
subCatList=loadedUserDTA.subcat;
catList=loadedUserDTA.cats;
  setSubCategory();
})
function setSubCategory(){
let mainCatSet= new Set();
  if(catList.length>0){
    catList.forEach(e => mainCatSet.add(e.mainCatalog));
  };
  catArray=Array.from(mainCatSet);
  catArray.sort((a,b)=>a-b);
  if(subCatList.length>0){
    subCatList.forEach(e => {
      if(e.mainCatalog in subDict){
        let lt=subDict[e.mainCatalog];
        if(!lt.includes(e.subCatalog)){
          lt.push(e.subCatalog);
        }
        subDict[e.mainCatalog]=lt;
      }else{
        subDict[e.mainCatalog]=[e.subCatalog];
      }
    });
    console.log(subCatList,subDict);
  }
}
function loadCategory(){
  let h=['<option disabled="" selected="" value="">Select main Category</option>'];
  catArray.forEach(n => h.push(`<option value="${n}">${n}</option>`));
  $('#article_mainCategory').html(h.join(''));
}
function editArticle(btn){
  console.log(btn.dataset.id);
  console.log(blogDict[btn.dataset.id]);
  let d=blogDict[btn.dataset.id];
  OpenHide('#articleFormDom, #articleBackBtn ','#articleRowDom, #articleEditBtn');
  $('#articleFormDom').html(articleEditFormHtml);
  loadCategory();
  $('#article_id').val(d.id);
  $('#title').val(d.title);
  $('#article_mainCategory').val(d.mainCategory);
  loadSubcat(d.mainCategory);
  $('#article_category').val(d.category);
  $('#article_region').val(d.region);
  $('#article_state').val(d.regionState);
  $('#article_city').val(d.city);
  $('#article_price').val(d.price);
  $('#article_desc').val(d.description);
  $('#article_name').val(d.name);
  $('#article_email').val(d.name);
  $('#article_phone').val(d.name);
  $('#article_passcode').val(d.name);
  $('#article_address').val(d.name);
  onEditImg(d.image);
}
function gotoStep(v){
  if(v==1){
    //first step
    $('#secondStep,#thirdStep').hide();
    $('#firstStep').show();
  }else if(v==2){
    //second step
    if(postvalid()){return;}
    $('#secondStep').show();
    $('#firstStep,#thirdStep').hide();
  }else if(v==3){
    //previous step
    if(postvalid()){return;}
    
    // if(userValid()){return;}
    $('#thirdStep').show();
    $('#secondStep,#firstStep').hide();
  }
}
function loadSubcat(v){
  let h=[];
  $('#article_category').html('');
  if(v!=""){
    if(v in subDict){
        subDict[v].forEach(sub=>{
          h.push(`<option value='${sub}'>${sub}</option>`);
        })
    }
  }
  $('#article_category').html(h.join(' '));
}
function userValid(){
  let isValid=false;
  if(!$('#article_name').val()){
      toastr.warning('Enter Name');
      isValid=true;
    }
  if(!$('#article_email').val()){
      toastr.warning('Enter Email');
      isValid=true;
    }
  if(!$('#article_phone').val()){
      toastr.warning('Enter Phone Number');
      isValid=true;
    }
  // if(!$('#other_number').val()){
  //     toastr.warning('Enter Title');
  //     isValid=true;
  //   }
  if(!$('#article_address').val()){
      toastr.warning('Enter Title');
      isValid=true;
    }
    return isValid;
}
function postvalid(){
  let isValid=false;
  if(!$('#title').val()){
      toastr.warning('Enter Title');
      isValid=true;
    }
    if(!$('#article_mainCategory').val()){
      toastr.warning('Select Main Category');
      isValid=true;
    }
    if(!$('#article_category').val()){
      toastr.warning('Select Sub Category');
      isValid=true;
    }
    /*
    if(!$('#article_region').val()){
      toastr.warning('Enter Region');
      isValid=true;
    }
    if(!$('#article_state').val()){
      toastr.warning('Enter State');
      isValid=true;
    }
    if(!$('#article_city').val()){
      toastr.warning('Enter City');
      isValid=true;
    }
    */
    if(!$('#article_price').val()){
      toastr.warning('Enter Price');
      isValid=true;
    }
    return isValid;
}

function convertFilesToBase64(files) {
  const promises = [];
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
function onEditImg(img){

$('.imagefiles').prepend(`<textarea class="form-control" name="image" id="image" style="display:none" >${img}</textarea> `);
          $('.multipartFile').prepend(`<img class="col-md-2 img-thumbnail" id="myImage" src="${img}" alt="image" >`);
}
// Example usage:
function selectFile(btn) {
  $('.multipartFile').html('');
  console.log(btn.files);
  if(btn.files.length<7){
  convertFilesToBase64(btn.files)
      .then(base64Strings => {
        base64Strings.forEach((img,i)=>{
          let h='';
          if(i==0){
            h=`<textarea class="form-control" name="image" id="image" style="display:none" >${img}</textarea> `;
          }else{
            h=`<textarea class="form-control" name="image${i+1}" id="image${i+1}" style="display:none" >${img}</textarea>`;

          }
          $('.imagefiles').prepend(h);
          $('.multipartFile').prepend(`<img class="col-md-2 img-thumbnail" id="myImage" src="${img}" alt="image${i}" >`);

          console.log(img.length);
        });

          // console.log(base64Strings); // An array of Base64 strings
      })
      .catch(error => {
          console.error('Error converting files:', error);
      });
    }else{
      toastr.warning(`Selected maxium 6 files.`);
    }
}

function addNewArticle(){
  $('#articleFormDom').html(articleEditFormHtml);
  OpenHide('#articleFormDom, #articleBackBtn ','#articleEditBtn, #articleRowDom');
  loadCategory();
}

function articleSubmit(){
  if(!$('#image').val())return;
  let userID=$('#user').val();
			
  if($('#action').val()=="yes"){
    if(!userID){
      userID=1;
    }
    createPostRequest('articleForm','/admin/blog/create/'+userID, 'createStatesCB');
  }else{
    createPostRequest('articleForm','/user/Blog/create','articleSubmitCB');

  }

}

function articleSubmitCB(r){
  console.log(r); 
  OpenHide('#articleRowDom','#articleFormDom, #articleBackBtn ');

}