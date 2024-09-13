var loadedDTA = {},
  catList = [],
  subcatList = [],
  stateList = [],
  cityList = [],
  blogList = [],
  isShort='';

function loadData() {
  catList = loadedDTA.cats;
  subcatList = loadedDTA.subcats;
  stateList = loadedDTA.states;
  cityList = loadedDTA.cities;
  blogList = loadedDTA.blogs;
  // let h =showBlogs(blogList);
  blogList.forEach(d=>{
    d['date']=Date.parse(d.update_at);
    console.log(d.date);
  })
  $("#recordDom").html(showBlogs(blogList).join(""));
  let c = [
    '<li class="list-group-item " aria-disabled="true"><h4>Categories</h4></li>',
  ];
  catList.forEach((e) => {
    c.push(`<li class="list-group-item form-check">
      <input class="form-check-input" type="checkbox" name="maincat" value="${e.title}" id="${e.title}" >
      <label class="form-check-label" for="${e.title}">
      ${e.title}
      </label>
      </li>`);
  });
  $("#maincatDom").html(c.join(""));
  let sc = [
    '<li class="list-group-item " aria-disabled="true"><h4>Sub Categories</h4></li>',
  ];
  subcatList.forEach((e) => {
    sc.push(`<li class="list-group-item form-check">
    <input class="form-check-input" type="checkbox" name="subcat" value="${e.title}" id="${e.title}" >
    <label class="form-check-label" for="${e.title}">
      ${e.title}
    </label>
    </li>`);
  });
  $("#subcatDom").html(sc.join(""));
  let st = [
    '<li class="list-group-item " aria-disabled="true"><h4>States</h4></li>',
  ];
  stateList.forEach((e) => {
    st.push(`<li class="list-group-item form-check">
    <input class="form-check-input" type="checkbox" name="state" value="${e.title}" id="${e.title}" >
    <label class="form-check-label" for="${e.title}">
      ${e.title}
    </label>
    </li>`);
  });
  $("#statesDom").html(st.join(""));
  let ct = [
    '<li class="list-group-item " aria-disabled="true"><h4>Cities</h4></li>',
  ];
  cityList.forEach((e) => {
    ct.push(`<li class="list-group-item form-check">
      <input class="form-check-input" type="checkbox" name="city" value="${e.title}" id="${e.title}" >
      <label class="form-check-label" for="${e.title}">
      ${e.title}
      </label>
      </li>`);
  });
  $("#cityDom").html(ct.join(""));
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

function filterData() {
  var cat = [],
    sub = [],
    state = [],
    city = [],
    filteredBlogList=[],
    filteredCatList=blogList.slice(),
    filteredSubList=blogList.slice(),
    filteredStateList=blogList.slice(),
    filteredCityList=blogList.slice();
  $("input:checkbox[name='maincat']:checked").each(function () {
    cat.push($(this).val());
  });
  $("input:checkbox[name='subcat']:checked").each(function () {
    sub.push($(this).val());
  });
  $("input:checkbox[name='state']:checked").each(function () {
    state.push($(this).val());
  });
  $("input:checkbox[name='city']:checked").each(function () {
    city.push($(this).val());
  });
  console.log(cat);
  console.log(sub);
  console.log(state);
  console.log(city);
  let selectedDT=[],filterFlag=false,uniqueDict={},uniqBlogList=[];
  if (cat.length>0) {
    cat.forEach((c) => {
      selectedDT.push(`<span class="badge rounded-pill text-bg-secondary st-filter" data-name="${c}" data-title="${c}" onclick="removeSelected(this)">${c}<i class="fa-regular fa-circle-xmark selected-icon"></i></span>`);
    });
      filteredCatList = filteredCatList.filter((d) => {
        return  cat.includes(d.mainCategory);
      });
      filterFlag=true;
    filteredBlogList=filteredBlogList.concat(filteredCatList);
  }
  if (sub.length>0) {
    sub.forEach((s) => {
      selectedDT.push(`<span class="badge rounded-pill text-bg-secondary st-filter" data-name="${s}" data-title="${s}" onclick="removeSelected(this)">${s}<i class="fa-regular fa-circle-xmark selected-icon"></i></span>`);
    });
      filteredSubList = filteredSubList.filter((d) => {
        return sub.includes(d.category);
      }); 
      filterFlag=true;
    filteredBlogList=filteredBlogList.concat(filteredSubList);
  }
  if (state.length>0) {
    state.forEach((s) => {
      selectedDT.push(`<span class="badge rounded-pill text-bg-secondary st-filter" data-name="${s}" data-title="${s}" onclick="removeSelected(this)">${s}<i class="fa-regular fa-circle-xmark selected-icon"></i></span>`);
    });
      filteredStateList = filteredStateList.filter((d) => {
        return state.includes(d.regionState);
      });
      filterFlag=true;
    filteredBlogList=filteredBlogList.concat(filteredStateList);
  }
  if (city.length>0) {
    city.forEach((s) => {
      selectedDT.push(`<span class="badge rounded-pill text-bg-secondary st-filter" data-name="${s}" data-title="${s}" onclick="removeSelected(this)">${s}<i class="fa-regular fa-circle-xmark selected-icon"></i></span>`);
    });
      filteredCityList = filteredCityList.filter((d) => {
        return city.includes(d.city);
      });
      filterFlag=true;
    filteredBlogList=filteredBlogList.concat(filteredCityList);
  }
  filteredBlogList.forEach(e=>{
    uniqueDict[e.id]=e;
  });
  $.each(uniqueDict,(id,d)=>{
    uniqBlogList.push(d);
  })
  uniqueDict={};
  $('#selectedDom').html(selectedDT.join(''));
  if(filterFlag){
    $("#recordDom").html(showBlogs(uniqBlogList).join(""));
  }else{
    $("#recordDom").html(showBlogs(blogList).join(""));
  }
  console.log(uniqBlogList);
}


function showBlogs(dataList){
  dataList=shortBy(dataList);
  let h=[];
  $('#result-count').html(`Total: ${dataList.length}`);
  dataList.forEach((e) => {
    h.push(`<div class="col-md-4 mt-2" >
    <div class="card card-b">
      <!-- <img th:src="/image/${e.image}" class="card-img-top" alt="..."> -->
      <img src="/image/${e.image}"  class="card-img-top" alt="...">
      <div class="card-body">
        <a href="/${e.title}" class="card-title stretched-link card-title-line-limit"><h6>${e.title}</h6></a>
        <span class="card_price">${e.price} Rs/-</span><span class="cat_title">(${e.category})</span>
        <p class="card-text card-text-line-limit">${e.description}</p>
      </div>
      <div class="card-footer d-flex justify-content-between bg-white">
        <div class="card-link"><i class="fas fa-heart mr-2"></i>${e.update_at}</div>
        <div class="card-link"><span >${e.city}</span> <i class="fas fa-map-marker-alt ml-1"></i></div>
      </div>
    </div>
    </div>`);
  });
  return h;
}

function removeSelected(icon){
  console.log(icon.dataset.name);
  $(`input:checkbox[value='${icon.dataset.name}']`).prop('checked',false);
  $(icon).remove();
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