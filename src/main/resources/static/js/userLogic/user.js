var userInfo = {},
  addressList = [],
  wishList = [],
  blogDict = {},
  addressDict = {},
  productList = [],
  productDict = {},
  wishlistItems = {};

$(function () {
  loadData();
});
function loadData() {
  // console.log('user',loadedUserDTA.user);
  // addressList=loadedUserDTA.user.addresses;
  setUserInfo(loadedUserDTA.user, true);
  console.log(userInfo);
  wishList = loadedUserDTA.wishlist;
  productList = loadedUserDTA.productList;
  loadedUserDTA.blogs.forEach((d) => (blogDict[d.id] = d));
  loadedUserDTA.addressList.forEach((d) => (addressDict[d.id] = d));
  wishlistItems = JSON.parse(loadedUserDTA.wishlistItems);
  // console.log(addressDict);
  // console.log(blogDict);
  renderWishlistItems();
  renderArticles();
  renderAddresses();
}
function addAddress() {
  createPostRequest("addressForm", "/user/Address/create", "addAddressCB");
}
function addAddressCB(r) {
  // console.log(r);
  let dta = r.data,
    d = addressDict[dta.id];
  d["name"] = dta.name;
  d["phone"] = dta.phone;
  d["landmark"] = dta.landmark;
  d["pin_code"] = dta.pin_code;
  d["other_phone"] = dta.other_phone;
  d["region"] = dta.region;
  d["state"] = dta.state;
  d["city"] = dta.city;
  d["address"] = dta.address;
  d["address_type"] = dta.address_type;
  // addressDict[dta.id]=d;
  $("#addressForm")[0].reset();
  renderAddresses();
  addressForm(false);
}

function updateInfo() {
  createPostRequest("userInfoForm", "/user/info", "infoCB");
}
function infoCB(r) {
  console.log(r);
  setUserInfo(r.data, true);
  console.log(userInfo);
  infoForm(false);
}

function updatePassword() {
  let pass1 = $("#password").val(),
    pass2 = $("#conf_password").val(),
    isTrue = true;
  if (pass1 == "" || pass1 == null) {
    isTrue = false;
    toastr.warning("Password can't Empty");
  }
  if (pass2 == "" || pass2 == null) {
    isTrue = false;
    toastr.warning("Confirm Password can't Empty");
  }
  if (pass1.length < 4) {
    isTrue = false;
    toastr.warning("Password length must be 4 Character");
  }
  if (pass1 != pass2) {
    isTrue = false;
    toastr.warning("Confirm password is not matched");
  }
  if (isTrue) {
    createPostRequest("passwordForm", "/user/password", "updatePasswordCB");
  }
}

function updatePasswordCB(r) {
  console.log(r);
  $("#passwordForm")[0].reset();
  OpenHide("#passwordForm", "#passwordFormDisplay");
}

function setDefaultAddress(id) {
  createGetRequest("blankForm", "/user/Address/" + id, "setDefaultAddressCB");
}

function setDefaultAddressCB(r) {
  console.log(r);
}

function renderWishlistItems() {
  let ids = [];
  $.each(wishlistItems, (name, codes) => {
    codes.forEach((c) => {
      if (!ids.includes(c)) {
        ids.push(c);
      }
    });
  });
  if (productList.length == 0 && ids.length == 0) {
    $("#emptyDom").show();
  } else {
    $("#wishListDom").show();
    getWishlistProducts(ids);
  }
}
function getWishlistProducts(id) {
  $.get(`/items/${id}`)
    .done((response) => {
      JSON.parse(response).data.forEach((p) => {
        productList.push(p);
        productDict[p.id] = p;
      });
      renderWishlistProducts(productDict);
    })
    .fail(() => console.error("Error fetching wishlist products"));
}

function removeFromWishlist(id) {
  if (productDict.hasOwnProperty(id)) {
    delete productDict[id];
    wishlistItems["default"] = Object.keys(productDict);
    $.post({
      url: "/wishlist",
      method: "POST",
      data: JSON.stringify({
        userId: userInfo.id,
        collection: "default",
        items: angular.toJson(wishlistItems),
      }),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then(function (response) {
        console.log(response.items);
        renderWishlistProducts(productDict);
      })
      .catch(function (error) {
        console.error("Error fetching data:", error); // Handle errors
      });
  }
}
