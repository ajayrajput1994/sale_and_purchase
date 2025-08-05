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
  if (address.address_type == "HOME") {
    $("#HOME").prop("checked", true);
  } else if (address.address_type == "OFFICE") {
    $("#OFFICE").prop("checked", true);
  } else if (address.address_type == "OTHER") {
    $("#OTHER").prop("checked", true);
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
function showOrderDetails(orderId, prodId) {
  let orderRow = orderDict[orderId];
  // console.log(orderRow, item);
  OpenHide("#orderDetailsDom,#ordDetailBackBtn", "#orderItemsDom");
  let billing = {};
  let shipping = {};
  let itemData = [];
  let vouchers = [];

  try {
    billing = orderRow.billing ? JSON.parse(orderRow.billing) : {};
  } catch (e) {
    billing = {};
  }

  try {
    shipping = orderRow.shipping ? JSON.parse(orderRow.shipping) : {};
  } catch (e) {
    shipping = {};
  }

  try {
    itemData = orderRow.itemDta ? JSON.parse(orderRow.itemDta) : [];
  } catch (e) {
    itemData = [];
  }

  try {
    vouchers =
      orderRow.vouchers && orderRow.vouchers !== "pending"
        ? JSON.parse(orderRow.vouchers)
        : [];
  } catch (e) {
    vouchers = [];
  }

  // Format dates safely
  let orderDate = "Invalid date";
  try {
    orderDate = new Date(orderRow.orderDate).toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  } catch (e) {}

  let deliveredDate = "Pending";
  if (orderRow.delivered_at) {
    try {
      deliveredDate = new Date(orderRow.deliveredAt).toLocaleDateString(
        "en-US",
        {
          year: "numeric",
          month: "long",
          day: "numeric",
          hour: "2-digit",
          minute: "2-digit",
        }
      );
    } catch (e) {}
  }

  const voucherInfo =
    Array.isArray(vouchers) && vouchers.length > 0 ? vouchers[0] : null;
  const orderDateFormatted =
    typeof orderDate === "string" && orderDate.includes(" at")
      ? orderDate.split(" at")[0]
      : orderDate || "Pending";
  let statusClass = "status-created";
  if (orderRow.status === "DELIVERED") statusClass = "status-delivered";
  else if (orderRow.status === "ON_HOLD") statusClass = "status-hold";
  else if (orderRow.status === "CANCELLED") statusClass = "status-cancelled";
  let h = [];
  h.push('<div class="container">');
  h.push('<div class="header">');
  h.push('<div class="header-content">');
  h.push(`<div class="order-id">${orderRow.orderId}</div>`);
  h.push(`<div class="order-date">Placed on ${orderDate}</div>`);
  h.push(`<div class="status ${statusClass} pulse">${orderRow.status}</div>`);
  h.push("</div>");
  h.push("</div>");

  h.push('<div class="content">');

  // Order Information section
  h.push('<div class="section">');
  h.push('<div class="section-title">Order Information</div>');
  h.push('<div class="order-info-container">');
  h.push('<div class="info-row">');
  h.push('<div class="info-item">');
  h.push('<div class="info-label">Order ID</div>');
  h.push(`<div class="info-value">${orderRow.orderId}</div>`);
  h.push("</div>");
  h.push('<div class="info-item">');
  h.push('<div class="info-label">Customer</div>');
  h.push(`<div class="info-value">${orderRow.customerName}</div>`);
  h.push("</div>");
  h.push('<div class="info-item">');
  h.push('<div class="info-label">Order Date</div>');
  h.push(`<div class="info-value">${orderDateFormatted}</div>`);
  h.push("</div>");
  h.push("</div>");

  h.push('<div class="info-row">');
  h.push('<div class="info-item">');
  h.push('<div class="info-label">Total Items</div>');
  h.push(
    `<div class="info-value">₹${parseFloat(orderRow.totalPrice).toLocaleString(
      "en-IN",
      { minimumFractionDigits: 2 }
    )}</div>`
  );
  h.push("</div>");
  h.push('<div class="info-item">');
  h.push('<div class="info-label">GST</div>');
  h.push(
    `<div class="info-value">₹${parseFloat(orderRow.gst).toLocaleString(
      "en-IN",
      { minimumFractionDigits: 2 }
    )}</div>`
  );
  h.push("</div>");
  h.push('<div class="info-item">');
  h.push('<div class="info-label">Delivery Fee</div>');
  h.push(
    `<div class="info-value">₹${parseFloat(orderRow.deliveryFee).toLocaleString(
      "en-IN",
      { minimumFractionDigits: 2 }
    )}</div>`
  );
  h.push("</div>");
  h.push("</div>");

  h.push('<div class="info-row">');
  h.push('<div class="info-item">');
  h.push('<div class="info-label">Processing Fee</div>');
  h.push(
    `<div class="info-value">₹${parseFloat(
      orderRow.processingFee
    ).toLocaleString("en-IN", { minimumFractionDigits: 2 })}</div>`
  );
  h.push("</div>");
  h.push('<div class="info-item">');
  h.push('<div class="info-label">Handling Fee</div>');
  h.push(
    `<div class="info-value">₹${parseFloat(orderRow.handlingFee).toLocaleString(
      "en-IN",
      { minimumFractionDigits: 2 }
    )}</div>`
  );
  h.push("</div>");
  h.push('<div class="info-item">');
  h.push('<div class="info-label">Surge Fee</div>');
  h.push(
    `<div class="info-value">₹${parseFloat(orderRow.surgeFee).toLocaleString(
      "en-IN",
      { minimumFractionDigits: 2 }
    )}</div>`
  );
  h.push("</div>");
  h.push("</div>");

  h.push('<div class="info-row">');
  h.push('<div class="info-item">');
  h.push('<div class="info-label">Discount Applied</div>');
  h.push(
    `<div class="info-value discount">-₹${parseFloat(
      orderRow.voucherDiscount
    ).toLocaleString("en-IN", { minimumFractionDigits: 2 })}</div>`
  );
  h.push("</div>");
  h.push('<div class="info-item grand-total">');
  h.push('<div class="info-label">Grand Total</div>');
  h.push(
    `<div class="info-value">₹${parseFloat(orderRow.grandTotal).toLocaleString(
      "en-IN",
      { minimumFractionDigits: 2 }
    )}</div>`
  );
  h.push("</div>");
  h.push("</div>");
  h.push("</div>");
  h.push("</div>");

  // Delivery Address section
  h.push('<div class="section">');
  h.push('<div class="section-title">Delivery Address</div>');
  h.push('<div class="address-card">');
  h.push(`<div class="address-line"><strong>${shipping.n}</strong></div>`);
  h.push(`<div class="address-line">${shipping.a}</div>`);
  h.push(`<div class="address-line">${shipping.c}, ${shipping.l}</div>`);
  h.push(
    `<div class="address-line">${shipping.s}, ${shipping.r} - ${shipping.pc}</div>`
  );
  h.push(
    `<div class="address-line"><strong>Phone:</strong> ${shipping.p}</div>`
  );
  h.push(
    `<div class="address-line"><strong>Type:</strong> ${
      shipping.t.charAt(0).toUpperCase() + shipping.t.slice(1)
    } Address</div>`
  );
  h.push("</div>");
  h.push("</div>");

  // Order Items section with product details
  h.push('<div class="section">');
  h.push('<div class="section-title">Order Items</div>');
  h.push('<div class="items-table">');
  h.push('<div class="table-header">');
  h.push("<div>Product</div>");
  h.push("<div>Item ID</div>");
  h.push("<div>Quantity</div>");
  h.push("<div>Unit Price</div>");
  h.push("<div>Total</div>");
  h.push("</div>");

  // Generate items HTML with product details
  let itemsTotal = 0;
  itemData.forEach((item) => {
    const unitPrice = parseFloat(item.a);
    const quantity = parseInt(item.q);
    const totalPrice = unitPrice * quantity;
    itemsTotal += totalPrice;

    const product = productDict[item.id] || {
      name: "",
      image: "",
    };

    h.push('<div class="table-row">');
    h.push('<div class="product-cell">');
    h.push(
      `<img src="${getFirstImg(product.image)}" alt="${
        product.name
      }" class="product-image" />`
    );
    h.push(`<span class="product-name">${product.name}</span>`);
    h.push("</div>");
    h.push(`<div class="item-id">#${item.id}</div>`);
    h.push(`<div class="quantity">${quantity}</div>`);
    h.push(
      `<div class="amount">₹${unitPrice.toLocaleString("en-IN", {
        minimumFractionDigits: 2,
      })}</div>`
    );
    h.push(
      `<div class="amount">₹${totalPrice.toLocaleString("en-IN", {
        minimumFractionDigits: 2,
      })}</div>`
    );
    h.push("</div>");
  });

  h.push('<div class="table-row total-row">');
  h.push("<div></div>");
  h.push("<div></div>");
  h.push("<div><strong>Total</strong></div>");
  h.push("<div></div>");
  h.push(
    `<div class="amount"><strong>₹${itemsTotal.toLocaleString("en-IN", {
      minimumFractionDigits: 2,
    })}</strong></div>`
  );
  h.push("</div>");
  h.push("</div>");
  h.push("</div>");

  // Payment Details section
  h.push('<div class="section">');
  h.push('<div class="section-title">Payment Details</div>');
  h.push('<div class="payment-info">');
  h.push('<div class="payment-breakdown">');

  if (voucherInfo) {
    h.push('<div class="voucher-info">');
    h.push('<div class="info-label">Voucher Applied</div>');
    h.push(`<div class="voucher-code">${voucherInfo.code}</div>`);
    h.push("</div>");
  }

  h.push('<div class="delivery-info">');
  h.push('<div class="info-label">Delivery Status</div>');
  h.push(`<div class="delivery-date">${deliveredDate}</div>`);
  h.push("</div>");
  h.push("</div>");
  h.push('<div class="total-section">');
  h.push("<div>Grand Total</div>");
  h.push(
    `<div class="total-amount">₹${parseFloat(
      orderRow.grandTotal
    ).toLocaleString("en-IN", { minimumFractionDigits: 2 })}</div>`
  );
  h.push("</div>");
  h.push("</div>");
  h.push("</div>");
  h.push("</div>");
  h.push("</div>");

  $("#orderDetailsDom").html(h.join(""));
}
function closeOrderDetails() {
  OpenHide("#orderItemsDom", "#orderDetailsDom,#ordDetailBackBtn");
}
// <img src="/image/Desert.jpg" class="img-fluid rounded-start article_absolute_img" alt="..." >
function renderOrders() {
  OpenHide("#orderItemsDom", "#emptyDom2");
  if (getDictLength(orderDict) > 0) {
    let h = [];
    $.each(orderDict, (c, d) => {
      JSON.parse(d.itemDta).forEach((dta) => {
        let item = productDict[dta.id];
        h.push({
          name: `<tr><td ><div class="card">
              <div class="row g-0">
                <div class="blog_img d-flex justify-content-center">
                  <img src="${getFirstImg(
                    item.image
                  )}" class="img-fluid rounded-start article_absolute_img" alt="..." >
                </div>
                <div class="blog_detail">
                  <div class="card-body">
                    <h5 class="card-title cursor"  onclick="showOrderDetails('${c}','${
            dta.id
          }')">${item.name} (${d.status})</h5>
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
  $("#wishlistItemsDom").show();
  if (Object.keys(items).length == 0) {
    $("#wishlistItemsDom").hide();
    $("#emptyDom").show();
    return;
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
