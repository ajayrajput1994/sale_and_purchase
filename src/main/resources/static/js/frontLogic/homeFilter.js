var loadedDTA = {},
  catList = [],
  subcatList = [],
  stateList = [],
  cityList = [],
  blogList = [];

function loadData() {
  catList = loadedDTA.cats;
  subcatList = loadedDTA.subcats;
  stateList = loadedDTA.states;
  cityList = loadedDTA.cities;
  blogList = loadedDTA.blogs;
  // let h =showBlogs(blogList);
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
function filterData() {
  var cat = [],
    sub = [],
    state = [],
    city = [],
    filteredBlogList = [];
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
  
  let selectedDT=[];
  if (cat.length>0) {
    cat.forEach((c) => {
      filteredBlogList = blogList.filter((d) => {
        return d.mainCategory == c;
      });
      selectedDT.push(`<span class="badge rounded-pill text-bg-secondary st-filter" data-name="${c}" data-title="${c}" onclick="removeSelected(this)">${c}<i class="fa-regular fa-circle-xmark selected-icon"></i></span>`);
    });
  }
  if (sub.length>0) {
    sub.forEach((s) => {
      filteredBlogList = blogList.filter((d) => {
        return d.category == s;
      });
      selectedDT.push(`<span class="badge rounded-pill text-bg-secondary st-filter" data-name="${s}" data-title="${s}" onclick="removeSelected(this)">${s}<i class="fa-regular fa-circle-xmark selected-icon"></i></span>`);
    });
  }
  if (state.length>0) {
    state.forEach((s) => {
      filteredBlogList = blogList.filter((d) => {
        return d.regionState == s;
      });
      selectedDT.push(`<span class="badge rounded-pill text-bg-secondary st-filter" data-name="${s}" data-title="${s}" onclick="removeSelected(this)">${s}<i class="fa-regular fa-circle-xmark selected-icon"></i></span>`);
    });
  }
  if (city.length>0) {
    city.forEach((s) => {
      filteredBlogList = blogList.filter((d) => {
        return d.city == s;
      });
      selectedDT.push(`<span class="badge rounded-pill text-bg-secondary st-filter" data-name="${s}" data-title="${s}" onclick="removeSelected(this)">${s}<i class="fa-regular fa-circle-xmark selected-icon"></i></span>`);
    });
  }
  $('#selectedDom').html(selectedDT.join(''));
  $("#recordDom").html(showBlogs(filteredBlogList).join(""));
  console.log(filteredBlogList);
}

function showBlogs(dataList){
  let h=[];
  dataList.forEach((e) => {
    h.push(`<div class="col-md-4 mt-2" >
    <div class="card card-b">
      <!-- <img th:src="/image/${e.image}" class="card-img-top" alt="..."> -->
      <img src="/image/${e.image}"  class="card-img-top" alt="...">
      <div class="card-body">
        <a href="/${e.title}" class="card-title stretched-link"><h6>${e.title}</h6></a>
        <p class="card-text">${e.description}</p>
      </div>
      <div class="card-footer d-flex justify-content-between bg-white">
        <div href="#" class="card-link"><i class="fas fa-heart mr-2"></i></div>
        <div href="#" class="card-link"><span th:text="${e.city}"></span> <i class="fas fa-map-marker-alt ml-1"></i></div>
      </div>
    </div>
    </div>`);
  });
  return h;
}

function removeSelected(icon){
  console.log(icon.dataset.name);
}