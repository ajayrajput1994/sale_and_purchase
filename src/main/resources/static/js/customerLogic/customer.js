var userInfo = {},
  addressList = [],
  wishList = [],
  blogDict = {},
  orderDict = {},
  addressDict = {},
  productList = [],
  productDict = {},
  wishlistItemDict = {},
  reviewsDict = {},
  reviewsList = [];
wishlistItems = {};

$(function () {
  loadData();
});
async function loadData() {
  // console.log('user',loadedUserDTA.user);
  // addressList=loadedUserDTA.user.addresses;
  setUserInfo(loadedUserDTA.user, true);
  // console.log(userInfo);
  wishList = loadedUserDTA.wishlist;
  productList = loadedUserDTA.productList;
  reviewsList = loadedUserDTA.reviews;
  // loadedUserDTA.blogs.forEach((d) => (blogDict[d.id] = d));
  loadedUserDTA.orders.forEach((d) => (orderDict[d.id] = d));
  loadedUserDTA.addressList.forEach((d) => (addressDict[d.id] = d));
  loadedUserDTA.productList.forEach((d) => (productDict[d.id] = d));
  loadedUserDTA.reviews.forEach((d) => {
    if (reviewsDict.hasOwnProperty(d.productId)) {
      reviewsDict[d.productId]["r"] += d.rating;
      reviewsDict[d.productId]["t"] += 1;
    } else {
      reviewsDict[d.productId] = { r: d.rating, t: 1 };
    }
  });
  wishlistItems = JSON.parse(loadedUserDTA.wishlistItems);
  // console.log(reviewsDict);
  // console.log(blogDict);
  await renderWishlistItems();
  renderOrders();
  renderAddresses();
}
function addAddress() {
  createPostRequest("addressForm", "/Api/Address/create", "addAddressCB");
}
function addAddressCB(r) {
  console.log(r);
  // d = addressDict[dta.id];
  let dta = r.data,
    d = {};
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
  addressDict[dta.id] = d;
  $("#addressForm")[0].reset();
  renderAddresses();
  addressForm(false);
}

function updateInfo() {
  createPostRequest("userInfoForm", "/Api/info", "infoCB");
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
    createPostRequest("passwordForm", "/Api/password", "updatePasswordCB");
  }
}

function updatePasswordCB(r) {
  console.log(r);
  $("#passwordForm")[0].reset();
  OpenHide("#passwordForm", "#passwordFormDisplay");
}

function setDefaultAddress(id) {
  createGetRequest("blankForm", "/Api/Address/" + id, "setDefaultAddressCB");
}

function setDefaultAddressCB(r) {
  console.log(r);
}

async function renderWishlistItems() {
  let ids = [];
  $.each(wishlistItems, (name, codes) => {
    codes.forEach((c) => {
      if (!ids.includes(c)) {
        ids.push(c);
      }
    });
  });
  if (productList.length == 0 || ids.length == 0) {
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
        wishlistItemDict[p.id] = p;
        productDict[p.id] = p;
      });
      renderWishlistProducts(wishlistItemDict);
    })
    .fail(() => console.error("Error fetching wishlist products"));
}

function removeFromWishlist(id) {
  if (wishlistItemDict.hasOwnProperty(id)) {
    delete wishlistItemDict[id];
    wishlistItems["default"] = Object.keys(wishlistItemDict);
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
        renderWishlistProducts(wishlistItemDict);
      })
      .catch(function (error) {
        console.error("Error fetching data:", error); // Handle errors
      });
  }
}
