var loadedDTA = {},
  catList = [],
  subcatList = [],
  stateList = [],
  cityList = [],
  blogList = [],
  isShort='',
  stateTitle='',
  passcode='',
  cat = [],
  sub = [],
  state = [],
  city = [],
  selectedDT=[];
$(function (){
  loadData();
})
function loadData() {
  // console.log('url:',window.location.pathname.replace('%20',' ').replace('/',''));
  stateTitle=window.location.pathname.replace('%20',' ').replace('/','');
  catList = loadedDTA.cats;
  subcatList = loadedDTA.subcats;
  stateList = loadedDTA.states;
  cityList = loadedDTA.cities;
  blogList = loadedDTA.blogs;
  // let h =showBlogs(blogList);
  blogList.forEach(d=>{
    d['date']=Date.parse(d.update_at);
    // console.log(d.date);
  })
  // $("#recordDom").html(showBlogs(blogList).join(""));
 
  let c = [
    '<li class="list-group-item filterTitle" aria-disabled="true"><h4>Categories</h4></li>',
  ];
  catList.forEach((e) => {
    c.push(`<li class="list-group-item form-check">
      <input class="form-check-input" type="checkbox" onchange="filterData(this)" name="maincat" value="${e.title}" id="${e.title}" >
      <label class="form-check-label" for="${e.title}">
      ${e.title}
      </label>
      </li>`);
  });
  $("#maincatDom").html(c.join(""));
  let sc = [
    '<li class="list-group-item filterTitle" aria-disabled="true"><h4>Sub Categories</h4></li>',
  ];
  subcatList.forEach((e) => {
    sc.push(`<li class="list-group-item form-check">
    <input class="form-check-input" type="checkbox" onchange="filterData(this)" name="subcat" value="${e.title}" id="${e.title}" >
    <label class="form-check-label" for="${e.title}">
      ${e.title}
    </label>
    </li>`);
  });
  $("#subcatDom").html(sc.join(""));
  let st = [
    '<li class="list-group-item filterTitle" aria-disabled="true"><h4>States</h4></li>',
  ];
  stateList.forEach((e) => {
    st.push(`<li class="list-group-item form-check">
    <input class="form-check-input" type="checkbox" onchange="filterData(this)" name="state" value="${e.title}" id="${e.title}" >
    <label class="form-check-label" for="${e.title}">
      ${e.title}
    </label>
    </li>`);
  });
  $("#statesDom").html(st.join(""));
  let ct = [
    '<li class="list-group-item filterTitle" aria-disabled="true"><h4>Cities</h4></li>',
  ];
  cityList.forEach((e) => {
    ct.push(`<li class="list-group-item form-check">
      <input class="form-check-input" type="checkbox" onchange="filterData(this)" name="city" value="${e.title}" id="${e.title}" >
      <label class="form-check-label" for="${e.title}">
      ${e.title}
      </label>
      </li>`);
  });
  $("#cityDom").html(ct.join(""));
  filterData();
  
}
// {
  //   "id": 2,
  //   "mainCategory": "Fashion",
  //   "category": "Girls Fashion",
  //   "price": 232,
  //   "image": "Concept-Car-PNG.png",
  //   "image2": "car-2.png",
  //   "image3": "Car-PNG-Transparent-Image.png",
  //   "image4": "",
  //   "image5": "",
  //   "image6": "",
  //   "region": "india",
  //   "regionState": "saharanpur",
  //   "city": "saharanpur",
  //   "address": "premnagar, bob, nearly iti\r\nbob\r\nopiop",
  //   "create_at": "04-06-2024",
  //   "update_at": "04-06-2024",
  //   "description": "asdf adfsadf sdfasdf asd f sd fasdf asd f asd fasd f sad f sda f sdf",
  //   "title": "RR won the match "
  // }

$('.form-check-input').click(function(){
  filterData();
});

function filterData(check) {
  var  filteredBlogList=[],
    filteredCatList=blogList.slice(),
    filteredSubList=blogList.slice(),
    filteredStateList=blogList.slice(),
    filteredCityList=blogList.slice(),
    checkboxName='',
    checkboxValue='';

    if(check!=undefined){
      checkboxName=check.name;
      checkboxValue=check.value;
      if(!checkboxName || !checkboxValue)return;      
    }
    let filterFlag=false,uniqueDict={},uniqBlogList=[];
  if(stateTitle){
    $(`input:checkbox[value='${stateTitle}']`).prop('checked',true);
    state.push(stateTitle);
    selectedDT.push(stateTitle);
    // selectedDT.push(`<span class="badge rounded-pill text-bg-secondary st-filter" data-name="${stateTitle}" data-title="${stateTitle}" onclick="removeSelected(this)">${stateTitle}<i class="fa-regular fa-circle-xmark selected-icon"></i></span>`);
  }
  if(checkboxName=='maincat'){
    if(check.checked){
      cat.push(checkboxValue);
    }else{
      cat = cat.filter(n => n !== checkboxValue);
    }
    if (cat.length>0) {
      selectedDT.push(...cat);
        filteredCatList = filteredCatList.filter((d) => {
          return  cat.includes(d.mainCategory);
        });
        filterFlag=true;
      filteredBlogList=filteredBlogList.concat(filteredCatList);
    }
  }else if(checkboxName=='subcat'){
    if(check.checked){
      sub.push(checkboxValue);
    }else{
      sub = sub.filter(n => n !== checkboxValue);
    }
    if (sub.length>0) {
      selectedDT.push(...sub);
        filteredSubList = filteredSubList.filter((d) => {
          return sub.includes(d.category);
        }); 
        filterFlag=true;
      filteredBlogList=filteredBlogList.concat(filteredSubList);
    }
  }else if(checkboxName=='state'){
    if(check.checked){
      state.push(checkboxValue);
    }else{
      state = state.filter(n => n !== checkboxValue);
    }
    if (state.length>0) {
      selectedDT.push(...state);
        filteredStateList = filteredStateList.filter((d) => {
          return state.includes(d.regionState);
        });
        filterFlag=true;
      filteredBlogList=filteredBlogList.concat(filteredStateList);
    }

  }else if(checkboxName=='city'){
    if(check.checked){
      city.push(checkboxValue);
    }else{
      city = city.filter(n => n !== checkboxValue);
    }
    if (city.length>0) {
      selectedDT.push(...city);
        filteredCityList = filteredCityList.filter((d) => {
          return city.includes(d.city);
        });
        filterFlag=true;
      filteredBlogList=filteredBlogList.concat(filteredCityList);
    }

  }
  // $("input:checkbox[name='maincat']:checked").each(function () {
  //   cat.push($(this).val());
  // });
  // $("input:checkbox[name='subcat']:checked").each(function () {
  //   sub.push($(this).val());
  // });
  // $("input:checkbox[name='state']:checked").each(function () {
  //   state.push($(this).val());
  // });
  // $("input:checkbox[name='city']:checked").each(function () {
  //   city.push($(this).val());
  // });
  if(selectedDT.length>0){
    $('#allClearDom').html('<span class="badge rounded-pill text-bg-danger st-filter" style="cursor:pointer;margin-right:5px;"  onclick="removeAllFilter()">All Clear</span>');
  }
  let badgeList=[],badgeSet=new Set();
  selectedDT.forEach(s=>badgeSet.add(s));
  badgeSet.forEach((s) => {
    badgeList.push(`<span class="badge rounded-pill text-bg-secondary st-filter" data-name="${s}" data-title="${s}" onclick="removeSelected(this)">${s}<i class="fa-regular fa-circle-xmark selected-icon"></i></span>`);
  });
  
  
  filteredBlogList.forEach(e=>{
    uniqueDict[e.id]=e;
  });
  $.each(uniqueDict,(id,d)=>{
    uniqBlogList.push(d);
  })
  uniqueDict={};
  $('#selectedDom').html(badgeList.join(''));
  if(filterFlag){
    $("#recordDom").html(showBlogs(uniqBlogList).join(""));
  }else{
    $("#recordDom").html(showBlogs(blogList).join(""));
  }
  // console.log(uniqBlogList);
}


function showBlogs(dataList){
  dataList=shortBy(dataList);
  let h=[],cookie=[],list=[];
  $('#result-count').html(`Total: ${dataList.length}`);
  cookie= Object.values(getCookie('mywishlist'));
  if(cookie.length>0){
    list=getAllUniqueKeysFromListOfMap(cookie[0]);
  }
  dataList.forEach((e) => {
    let isWishList=false;
    if(list.includes(`${e.id}`)){
      isWishList=true;
    }
    h.push(`
    <div class="card card-b">
      <!-- <img th:src="/image/${e.image}" class="card-img-top" alt="..."> 
      <img src="/image/${e.image}"  class="card-img-top" alt="..."> -->
      <img src="${e.image}"  class="card-img-top" alt="...">
      <div class="card-body">
        <a href="/${e.title}" class="card-title  card-title-line-limit"><h6>${e.title}</h6></a>
        <span class="card_price">${e.price} Rs/-</span><span class="cat_title">(${e.category})</span>
        <p class="card-text card-text-line-limit">${e.description}</p>
      </div>
      <div class="card-footer d-flex justify-content-between bg-white">
        <div class="card-link"><i class="fas fa-heart mr-2" id="${e.id}" style="color:${isWishList?'red':'grey'}" onclick="gotoWishlist(this)"></i>${e.update_at}</div>
        <div class="card-link"><span >${e.city}</span> <i class="fas fa-map-marker-alt ml-1"></i></div>
      </div>
    </div>
    `);
  });
  return h;
}
function removeAllFilter(){
  cat = [];sub = [];state = [];city = [];selectedDT=[];
  $('#allClearDom').html('');
  $('#selectedDom').html('');
  filterData();
}
function removeSelected(icon){
  console.log(icon.dataset.name);
  $(`input:checkbox[value='${icon.dataset.name}']`).prop('checked',false);
  $(icon).remove();
  selectedDT=selectedDT.filter(n => n !== icon.dataset.name);
  cat=cat.filter(n => n !== icon.dataset.name);
  sub=sub.filter(n => n !== icon.dataset.name);
  state=state.filter(n => n !== icon.dataset.name);
  city=city.filter(n => n !== icon.dataset.name);
  if(selectedDT.length==0){
    $('#allClearDom').html('');
  }
  filterData();
  // $("input:checkbox[name='maincat']").prop('checked',false);
}

function shortBy(DATA){
  if(isShort=="PRICEHTL"){

    DATA.sort(function(a,b){
      return b.price -a.price;
    });
  }else if(isShort=="PRICELTH"){

    DATA.sort(function(a,b){
      return a.price -b.price;
    });
  }else if(isShort=="NEW"){
    DATA.sort(function(a,b){
      return b.date -a.date;
    });
  }
  return DATA;
}

function getShort(v){
  $(v).parent().find('span').removeClass("active_sortby");;
  $(v).addClass('active_sortby');
  isShort=v.dataset.name;
  filterData();
}

function gotoWishlist(value){
  // clearAllCookies();
  // console.log(value);
  const d=new Date();
  let v=parseInt(value.id),
   date=`${d.getDate()}/${d.getMonth()}/${d.getFullYear()}`,
   dta={},
  cookie=getCookie('mywishlist');
 if(getDictLength(cookie)>0){
   $.each(cookie,(key,listOfMap)=>{
    let keys=getAllUniqueKeysFromListOfMap(listOfMap);
    // console.log(key);
    // console.log(listOfMap);
    if(keys.includes(`${v}`)){
      listOfMap.forEach(d=>{
        if(d.hasOwnProperty(v)){
          delete d[v];
          $(`#${v}`).attr('style', 'color: grey !important');
        }
      })
    }else{
      dta[v]=date;
      listOfMap.push(dta);
      $(`#${v}`).attr('style', 'color: red !important');
    }
    cookie[key]=listOfMap;
    setCookies('mywishlist',JSON.stringify(cookie));
    console.log('inside...',cookie);
  })
}else{
  if(passcode==""){
  swal({
      title: 'Provide Sign Up Passcode',
      text: "It's required for activities without login!",
      content: {
          element: 'input',
          attributes: {
              placeholder: 'Type your passcode here',
              type: 'number',
          },
      },
      buttons: {
          cancel: true,
          confirm: {
              text: 'Submit',
              closeModal: false,
          },
      },
    }).then((value) => {
        if (value) {
          passcode=value;
            swal(`Thanks for your passcode: ${value}`, 'Passcode Received', 'success');
        } else {
            swal('Please provide signup passcode.', 'Input Required', 'warning');
        }
    });

    let lt=[];
    lt.push({'passcode':passcode});
    lt.push({v:date});
    cookie[getUniqueId(10)]=lt;
    // cookie['passcode']=passcode;
    setCookies('mywishlist',JSON.stringify(cookie));
  }
 }
}


 function getAlert(){
      swal({
          title: 'Provide Sign Up Passcode',
          text: "It's required for activities without login!",
          content: {
              element: 'input',
              attributes: {
                  placeholder: 'Type your passcode here',
                  type: 'number',
              },
          },
          buttons: {
              cancel: true,
              confirm: {
                  text: 'Submit',
                  closeModal: false,
              },
          },
      }).then((value) => {
          if (value) {
            passcode=value;
              swal(`Thanks for your passcode: ${value}`, 'Passcode Received', 'success');
          } else {
              swal('Please provide signup passcode.', 'Input Required', 'warning');
          }
      });
  }

  function onTap(check){
    console.log(check.value);
    console.log(check.checked);
    console.log(check.name);
    console.log(check.id);
  }