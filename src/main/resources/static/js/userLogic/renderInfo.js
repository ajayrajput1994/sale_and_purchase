function operDom(v) {
  $(
    "#personalDom,#addressDom,#wishlistDom,#passwordDom,#settingDom,#articleDom,#ordersDom"
  ).hide();
  if (v == "personal") {
    $("#personalDom").show();
  } else if (v == "address") {
    $("#addressDom").show();
  } else if (v == "wishlist") {
    $("#wishlistDom").show();
  } else if (v == "password") {
    $("#passwordDom").show();
  } else if (v == "setting") {
    $("#settingDom").show();
  } else if (v == "article") {
    $("#articleDom").show();
  } else if (v == "orders") {
    $("#ordersDom").show();
  }
}

function setUserInfo(dta, v) {
  if (v) {
    userInfo["id"] = dta.id;
    userInfo["name"] = dta.name;
    userInfo["phone"] = dta.phone;
    userInfo["email"] = dta.email;
    userInfo["img"] = dta.image;
    userInfo["ophone"] = dta.other_phone;
    userInfo["pass"] = dta.password;
    userInfo["cd"] = dta.create_at;
    userInfo["ud"] = dta.update_at;
  }
  //fill user data in form
  $("#u_name").val(userInfo.name);
  $("#u_email").val(userInfo.email);
  $("#u_phone").val(userInfo.phone);
  $("#uother_phone").val(userInfo.ophone);
  //user display
  //  $('#info_ophone').textContent=userInfo.ophone;
  document.getElementById("info_name").innerText = userInfo.name;
  document.getElementById("info_phone").innerText = userInfo.phone;
  document.getElementById("info_email").innerText = userInfo.email;
  document.getElementById("info_ophone").innerText = userInfo.ophone;
  document.getElementById("info_create_date").innerText = userInfo.cd;
}

function addressForm(v) {
  if (v) {
    OpenHide(
      "#addressForm, #addressBackBtn",
      "#addressFormDom,#addressEditBtn"
    );
  } else {
    $("#addressForm")[0].reset();
    OpenHide(
      "#addressFormDom,#addressEditBtn",
      "#addressForm, #addressBackBtn"
    );
  }
}
function editAddress(v) {
  OpenHide("#addressForm, #addressBackBtn", "#addressFormDom,#addressEditBtn");
  let address = addressDict[v];
  $("#ad_id").val(address.id);
  $("#ad_name").val(address.name);
  $("#ad_phone").val(address.phone);
  $("#pin_code").val(address.pin_code);
  $("#landmark").val(address.landmark);
  $("#address").val(address.address);
  $("#city").val(address.city);
  $("#state").val(address.state);
  $("#region").val(address.region);
  $("#other_phone").val(address.other_phone);
  if (address.address_type == "work") {
    $("#work").prop("checked", true);
  }
}
function infoForm(v) {
  if (v) {
    setUserInfo(userInfo, false);
    OpenHide("#userInfoForm, #infoBackBtn", "#infoAddressDom,#infoEditBtn");
  } else {
    OpenHide("#infoAddressDom,#infoEditBtn", "#userInfoForm, #infoBackBtn");
  }
}
// <img src="/image/Desert.jpg" class="img-fluid rounded-start article_absolute_img" alt="..." >
function renderOrders() {
  OpenHide("#orderItemsDom", "#emptyDom2");
  if (getDictLength(orderDict) > 0) {
    let h = [];
    $.each(orderDict, (c, d) => {
      JSON.parse(d.itemDta).forEach((dta) => {
        let item = productDict[dta.id];
        // console.log(item);
        h.push({
          name: `<tr><td><div class="card">
              <div class="row g-0">
                <div class="blog_img d-flex justify-content-center">
                  <img src="${getFirstImg(
                    item.image
                  )}" class="img-fluid rounded-start article_absolute_img" alt="..." >
                </div>
                <div class="blog_detail">
                  <div class="card-body">
                    <h5 class="card-title">${item.name}</h5>
                    <p class="card-text card-text-line-limit">${
                      item.description
                    }</p>
                    <p class="card-text"><span class="card_price">${
                      item.price
                    } Rs./</span><span class="cat_title">(${
            item.category
          })</span>
                    </p>
                    <div class="d-flex justify-content-between">
                      <span class="wishlistDom-icon">
                      <i class="fa-regular fa-envelope"><span class="counter_txt">10</span></i>
                        <i class="fa-regular fa-message"><span class="counter_txt">10</span></i>
                        <i class="fa-regular fa-thumbs-up"><span class="counter_txt">10</span></i>
                        <i class="fa-regular fa-thumbs-down"><span class="counter_txt">10</span></i>
                      </span>
    
                      <span class="wishlistDom-icon">${calculeteReview(item.id)}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div></td></tr>`,
        });
      });
    });
    let tb = `<table id="myTable" class="table table-hover display" style="width:100%">
        <tbody></tbody></table>`;
    $("#OrderRowDom").html(tb);
    $("#myTable").DataTable({
      pageLength: 10,
      bFilter: false,
      lengthChange: false,
      // bInfo: false,
      // paging: false,
      data: h,
      columns: [{ data: "name", width: "100%" }],
    });
  } else {
    OpenHide("#emptyDom2", "#orderItemsDom");
  }
}
/* commenct for actical
function renderArticles() {
  if (getDictLength(blogDict) > 0) {
    let h = [];
    $.each(blogDict, (c, d) => {
      h.push({
        name: `<tr><td><div class="card">
        <div class="edit_icon_right">
          <a href="javascript:" id="addressEditBtn" data-id="${d.id}" onclick="editArticle(this)" style="color: blue;margin-top: 18px;"><i class="fa-solid fa-pen-to-square"></i></a>
        </div>
              <div class="row g-0">
                <div class="blog_img">
                  <img src="${d.image}" class="img-fluid rounded-start article_absolute_img" alt="..." >
                </div>
                <div class="blog_detail">
                  <div class="card-body">
                    <h5 class="card-title">${d.title}</h5>
                    <p class="card-text card-text-line-limit">${d.description}</p>
                    <p class="card-text"><span class="card_price">${d.price} Rs./</span><span class="cat_title">(${d.category})</span>
                    </p>
                    <div class="d-flex justify-content-between">
                      <span class="wishlistDom-icon">
                      <i class="fa-regular fa-envelope"><span class="counter_txt">10</span></i>
                        <i class="fa-regular fa-message"><span class="counter_txt">10</span></i>
                        <i class="fa-regular fa-thumbs-up"><span class="counter_txt">10</span></i>
                        <i class="fa-regular fa-thumbs-down"><span class="counter_txt">10</span></i>
                      </span>
    
                      <span class="wishlistDom-icon">
                        <i class="fa-solid fa-star"></i>
                        <i class="fa-solid fa-star"></i>
                        <i class="fa-solid fa-star-half-stroke"></i>
                        <i class="fa-regular fa-star"></i>
                        <i class="fa-regular fa-star"></i>
                        <span class="rating_price">4.5</span>
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div></td></tr>`,
      });
    });
    let tb = `<table id="myTable" class="table table-hover display" style="width:100%">
        <tbody></tbody></table>`;
    $("#articleRowDom").html(tb);
    $("#myTable").DataTable({
      pageLength: 10,
      bFilter: false,
      lengthChange: false,
      // bInfo: false,
      // paging: false,
      data: h,
      columns: [{ data: "name", width: "100%" }],
    });
  } else {
    $("#articleRowDom").html(
      `<h5 class="text-center">No Article found!</br>Create New Articles.</h5>`
    );
  }
}*/
function renderAddresses() {
  if (getDictLength(addressDict) > 0) {
    let h = [];
    $.each(addressDict, (c, d) => {
      h.push(`<div class=" card border-success p-2 mt-2">
      <div class="edit_icon_right">
        <a href="javascript:" id="addressEditBtn" onclick="editAddress(${
          d.id
        })" style="color: blue;margin-top: 18px;"><i class="fa-solid fa-pen-to-square"></i></a>
      </div>
      <div class="row ">
      <div class="col-width"><span><b>Name:</b></span> <span>${
        d.name
      }</span=></div>
      <div  class="col-width"><b><span>Phone:</b></span> <span>${
        d.phone
      }</span></div>
      <div  class="col-width"><b><span>Pincode:</b></span> <span>${
        d.pin_code
      }</span></div>
      <div  class="col-width"><b><span>Landmark:</b></span> <span>${
        d.landmark
      }</span></div>
      <div  class="col-width"><b><span>City:</b></span> <span>${
        d.city
      }</span></div>
      <div  class="col-width"><b><span>State:</b></span> <span>${
        d.state
      }</span></div>
      <div  class="col-width"><b><span>Region:</b></span> <span>${
        d.region
      }</span></div>
      <div  class="col-width"><b><span>Phone (Optional):</b></span> <span>${
        d.other_phone
      }</span></div>
      <div  class="col-md-12"><b><span>Address:</b></span> <span>${
        d.address
      }</span></div>
      <div  class="col-width"><b><span>Type:</b></span> <span class="badge text-bg-danger">${
        d.address_type
      }</span></div>
      <div class="col-width form-check form-switch">
        <input class="form-check-input" type="checkbox" onchange="setDefaultAddress(${
          d.id
        })" role="switch" id="flexSwitchCheckChecked" ${
        d.active == "yes" ? "checked" : ""
      } >
        <label class="form-check-label" for="flexSwitchCheckChecked"><b><span>Active:</b></span> </label>
      </div>
      </div>
      </div>`);
    });
    $("#addressFormDom").html(h.join(""));
  } else {
    $("#addressFormDom").html(
      `<h5 class="text-center">No Address found!</br>Create New Address.</h5>`
    );
  }
}

function renderWishlistProducts(items) {
  $(".wishlistItemsDom").html("");
  if (Object.keys(items).length == 0) {
    $("#wishlistItemsDom").hide();
    $("#emptyDom").show();
  }
  let h = [];
  $.each(items, (c, p) => {
    h.push(`<div class="card mb-3 rounded-0">
      <div class="grid-container">
        <div class="grid-item">
          <img src="${getFirstImg(
            p.image
          )}" class="img-fluid rounded-start" alt="...">
        </div>
        <div class="grid-item">
          <div class="card-body">
            <h5 class="card-title">${p.name}(${p.id})</h5>
            <div class="card-text"><small class="text-body-secondary"><i
                  class="fa-solid fa-rupee-sign price_icon"></i>${p.price.toFixed(
                    2
                  )}</small></div>
            <div class="d-flex justify-content-start"> 
              <a href="javascript:" onclick="removeFromWishlist(${
                p.id
              })" class="price_btn">REMOVE</a>
            </div>
          </div>
        </div>
      </div>
    </div>`);
  });
  $(".wishlistItemsDom").html(h.join(""));
}
function getFirstImg(imgStr) {
  if (imgStr) {
    return "/image/" + imgStr.split(",")[0];
  }
  return "/image/no_img.jpg";
}

function calculeteReview(prodId) {
  let rating = 0;
  if (reviewsDict.hasOwnProperty(prodId)) {
    rating = reviewsDict[prodId].r / reviewsDict[prodId].t;
  }
  const icons = [
    '<i class="fa-solid fa-star"></i>',
    '<i class="fa-solid fa-star-half-stroke"></i>',
    '<i class="fa-regular fa-star"></i>',
  ];
  let html = Array(5).fill(icons[2]); // Default to empty stars
  for (let i = 0; i < Math.floor(rating); i++) html[i] = icons[0]; // Full stars
  if (rating % 1 !== 0) html[Math.floor(rating)] = icons[1]; // Half star
  return (
    html.join("") + ` <span class="rating_price">${rating.toFixed(1)}</span>`
  );
}
