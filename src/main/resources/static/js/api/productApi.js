var productList = [],
  catDict = {},
  mainCatList = [],
  subCatDict = {};

var DT, imgPath;
function onload() {
  imgPath = loadedUserDTA.imgpath;
  loadedUserDTA.cats.forEach((d) => {
    if (!mainCatList.includes(d.title)) {
      mainCatList.push(d.title);
    }
  });
  console.log(mainCatList);
  loadedUserDTA.subcat.forEach((d) => {
    if (d.mainCatalog in subCatDict) {
      subCatDict[d.mainCatalog].push(d.title);
    } else {
      subCatDict[d.mainCatalog] = [d.title];
    }
  });
  loadedUserDTA.catList.forEach((d) => {
    if (d.subCategory in catDict) {
      catDict[d.subCategory].push(d.title);
    } else {
      catDict[d.subCategory] = [d.title];
    }
  });
  console.log(subCatDict);
  console.log(catDict);
  DT = $("#states_table").DataTable({
    // "pageLength": 50,
    // "info":false,
    // "paging":false,
    layout: {
      topStart: {
        buttons: ["csvHtml5", "excelHtml5"],
        // buttons:['copyHtml5','excelHtml5','csvHtml5','pdfHtml5','print']
      },
    },
    columns: [
      { data: "id" },
      { data: "code" },
      { data: "image" },
      { data: "img" },
      { data: "userId" },
      { data: "name" },
      { data: "price" },
      { data: "quantity" },
      { data: "category" },
      { data: "subCategory" },
      { data: "mainCategory" },
      { data: "description" },
      { data: "createdAt" },
      { data: "updatedAt" },
      { data: "action" },
    ],
    columnDefs: [
      { visible: true, targets: [0, 1, 3, 5, 6, 8, 9, 10] },
      { visible: false, targets: ["_all"] },
    ],
    initComplete: function () {
      console.log("DataTable is fully loaded! 🚀");
    },
  });
  $("#states_table tbody").on("click", "tr td", function () {
    if ($(this).index() == 9) {
      return false;
    }
    var data = DT.row(this).data();
    console.log(data);
    formDomOpen();
    $("#action").val("yes");
    $("#product_id").val(data.DT_RowId);
    // $('#user').val(data.user);
    $("#image").val(data.image);
    $("#name").val(data.name);
    $("#price").val(data.price);
    $("#quantity").val(data.quantity);
    $("#mainCategory").val(data.mainCategory);
    loadSubCategory(data.mainCategory);
    $("#subCategory").val(data.subCategory);
    loadCategory(data.subCategory);
    $("#category").val(data.category);
    $("#description").val(data.description);
    if (data.image != "") {
      let h = [];
      data.image.split(",").forEach((img) => {
        h.push(
          `<img src="/image/${img}" style="height:50px;margin-right:10px" alt="${img}"/>`
        );
      });
      $("#imageDom").html(h.join(""));
    }
  });
  OpenHide("#recordDom", "#loaderDom");
  // loadTable();
  scrolling();
}

function scrolling() {
  let page = 0,
    size = 8;
  const loadMoreItems = async () => {
    try {
      const response = await fetch(
        `/product/items?page=${page}&size=${size}&userId=0`
      );
      const data = await response.json();
      data.content.forEach((d) => {
        if (d) {
          let img = d.image.split(",");
          d["DT_RowId"] = d.id;
          // d['image']=`<img src="/image/${d.image}" style="height:40px;" alt="${img[0]}"/>`;
          d[
            "img"
          ] = `<img src="/image/${img[0]}" style="height:40px;" alt="${img[0]}"/>`;
          d["action"] =
            '<i class="fas fa-trash red-text text-center text-danger" onclick="deleteState(this)" style="cursor: pointer;"></i>';
          // console.log(d)
          DT.row.add(d).draw();
        }
      });

      page++;
    } catch (error) {
      console.error("Error loading items:", error);
    }
  };
  const onScroll = () => {
    if (
      window.scrollY + window.innerHeight >=
      document.documentElement.scrollHeight
    ) {
      loadMoreItems();
    }
  };

  window.addEventListener("scroll", onScroll);

  // Load initial items
  loadMoreItems();
}
function formDomOpen() {
  $("#product_form")[0].reset();
  $("#product_id").val(0);
  OpenHide("#blogFormDom", "#recordDom");
  // addNewArticle();
  $("#action").val("yes");
}

function recordDomOpen() {
  OpenHide("#recordDom", "#blogFormDom");
}
function loadTable() {
  loadedUserDTA.products.forEach((d) => {
    d["DT_RowId"] = d.id;
    d["action"] =
      '<i class="fas fa-trash red-text text-center text-danger" onclick="deleteState(this)" style="cursor: pointer;"></i>';
    // console.log(d)
    DT.row.add(d);
  });
  DT.draw();
}
function loadSubCategory(value) {
  let h = ['<option disabled="" selected="">-Select-</option>'];
  if (value in subCatDict) {
    subCatDict[value].forEach((name) => {
      h.push(`<option value="${name}">${name}</option>`);
    });
  }
  $("#subCategory").html(h.join(""));
}
function loadCategory(value) {
  let h = ['<option disabled="" selected="">-Select-</option>'];
  if (value in catDict) {
    catDict[value].forEach((name) => {
      h.push(`<option value="${name}">${name}</option>`);
    });
  }
  $("#category").html(h.join(""));
}
async function addProduct() {
  if (!$("#userId").val()) {
    toastr.info("User not found!");
  }
  if (productValidate()) {
    await createMultipartPost(
      "product_form",
      "/admin/product/create",
      "addProductCB"
    );
  }
}

function addProductCB(r) {
  console.log(r);
  // r =JSON.parse(r),
  $("#product_form")[0].reset();
  recordDomOpen();
  let d = r.data;
  let img = d.image.split(",");
  d["DT_RowId"] = d.id;
  d["img"] = `<img src="/image/${
    img[0]
  }?${new Date().getTime()}" style="height:40px;" alt="${img[0]}"/>`;
  d["action"] =
    '<i class="fas fa-trash red-text text-center text-danger" onclick="deleteState(this)" style="cursor: pointer;"></i>';
  if (r.action == "UPDATE") {
    DT.row(`#${d.id}`).data(d).draw();
  } else {
    DT.row.add(d).draw();
  }
}
function deleteState(btn) {
  var id = btn.parentNode.parentNode.id;
  console.log(id);
  createGetRequest(
    "product_form",
    "/admin/product/delete/" + id,
    "deleteStateCB"
  );
}
function deleteStateCB(r) {
  console.log(r);
  DT.row(`#${r.data}`).remove().draw();
}

function productValidate() {
  if (!$("#name").val()) {
    toastr.warning("Name can't be Empty.");
    return false;
  }
  if (!$("#price").val()) {
    toastr.warning("Price can't be Empty.");
    return false;
  }
  if (!$("#quantity").val()) {
    toastr.warning("Quantity can't be Empty.");
    return false;
  }
  if (!$("#mainCategory").val()) {
    toastr.warning("Main Category can't be Empty.");
    return false;
  }
  if (!$("#category").val()) {
    toastr.warning("Category can't be Empty.");
    return false;
  }
  if (!$("#subCategory").val()) {
    toastr.warning("Sub Category can't be Empty.");
    return false;
  }
  let desc = $("#description").val().split(" ");
  if (desc.length < 5) {
    toastr.warning("Description must be greater than 5 words.");
    return false;
  }
  return true;
}
