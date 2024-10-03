function operDom(v) {
  $("#personalDom,#addressDom,#wishlistDom,#passwordDom,#settingDom").hide();
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
  }
}
