var productList=[],
catList=[],
subCatDict={};

var DT; 
function onload(){ 
  loadedUserDTA.cats.forEach(d=>{
    if(!catList.includes(d.title)){ 
      catList.push(d.title);
    }
  });
  console.log(catList);
  loadedUserDTA.subcat.forEach(d=>{
    if(d.mainCatalog in subCatDict){
      subCatDict[d.mainCatalog].push(d.title);
    }else{
      subCatDict[d.mainCatalog]=[d.title];
    }
  });
  console.log(subCatDict);
  DT = $('#states_table').DataTable({
    // "pageLength": 50,
    // "info":false,
    // "paging":false,
    "layout": {
      topStart: {
        buttons: ['csvHtml5', 'excelHtml5']
        // buttons:['copyHtml5','excelHtml5','csvHtml5','pdfHtml5','print']
      }
    },
    columns: [
      { "data": 'id' },
      { "data": 'code' },
      { "data": 'image' },  
      { "data": 'userId' },
      { "data": 'name' },
      { "data": 'price' },
      { "data": 'quantity' },
      { "data": 'category' },
      { "data": 'subCategory' },
      { "data": 'description' },
      { "data": 'createdAt' },
      { "data": 'updatedAt' },
      { "data": 'action' },
    ],
    columnDefs: [
      { visible: true, targets: [0,1,4,5,6,7,8,10,11,12] },
      { visible: false, targets: ['_all'] },
    ],
    initComplete: function () {
    console.log('DataTable is fully loaded! 🚀')
    
    }
  });
  $('#states_table tbody').on('click', 'tr td', function () {
    if ($(this).index() == 9) {
      return false;
    }
    var data = DT.row(this).data();
    console.log(data);
    formDomOpen();
    $('#action').val('yes');
    $('#product_id').val(data.DT_RowId);
    // $('#user').val(data.user);
    $('#image').val(data.image);
    $('#name').val(data.name);
    $('#price').val(data.price);
    $('#quantity').val(data.quantity);
    $('#category').val(data.category);
    loadSubCategory(data.category);
    $('#subCategory').val(data.subCategory);
    $('#description').val(data.description);  
  });
  OpenHide('#recordDom','#loaderDom');
  // loadTable();
  scrolling();
};

function scrolling(){
  let page = 0;

const loadMoreItems = async () => {
    try {
        const response = await fetch(`/product/items?page=${page}&size=5`);
        const data = await response.json();

        data.content.forEach(d => {
          if(d){
            d['DT_RowId']=d.id;
            d['action']='<i class="fas fa-trash red-text text-center text-danger" onclick="deleteState(this)" style="cursor: pointer;"></i>';
            // console.log(d)
            DT.row.add(d).draw();
          }
            
        });

        page++;
    } catch (error) {
        console.error('Error loading items:', error);
    }
};

const onScroll = () => {
    if (window.scrollY + window.innerHeight >= document.documentElement.scrollHeight) {
        loadMoreItems();
    }
};

window.addEventListener('scroll', onScroll);

// Load initial items
loadMoreItems();

}
function loadTable(){
  loadedUserDTA.products.forEach(d => {
      d['DT_RowId']=d.id;
      d['action']='<i class="fas fa-trash red-text text-center text-danger" onclick="deleteState(this)" style="cursor: pointer;"></i>';
      // console.log(d)
      DT.row.add(d);
    });
    DT.draw();
}
function loadSubCategory(value){
  let h=['<option disabled="" selected="">-Select-</option>'];
  if(value in subCatDict){
    subCatDict[value].forEach(name=>{
      h.push(`<option value="${name}">${name}</option>`);
    })
  }
  $("#subCategory").html(h.join(''));
}
function addProduct(){
  if(!$("#userId").val()){
    toastr.info("User not found!");
  }
  if(productValidate()){
    	// createPostRequest('product_form', '/product', 'addProductCB');
    	createPostRequest('product_form', '/admin/product/create', 'addProductCB');
  }
}

function addProductCB(r){
  console.log(r);
  $('#product_form')[0].reset();
  recordDomOpen();
  let d = r.data;
  d['DT_RowId']=d.id;
  d['action']='<i class="fas fa-trash red-text text-center text-danger" onclick="deleteState(this)" style="cursor: pointer;"></i>';
  if (r.action == "UPDATE") {
    DT.row(`#${d.id}`).data(d).draw();
  } else {
    DT.row.add(d).draw();
  }
}
function deleteState(btn) {
  var id = btn.parentNode.parentNode.id;
  console.log(id);
  createGetRequest('product_form', '/admin/product/delete/' + id, 'deleteStateCB');
}
function deleteStateCB(r) {
  console.log(r);
  DT.row(`#${r.data}`).remove().draw();
}

function productValidate(){
  if(!$("#name").val()){
    toastr.warning("Name can't be Empty.");
    return false;
  }
  if(!$("#price").val()){
    toastr.warning("Price can't be Empty.");
    return false;
  }
  if(!$("#quantity").val()){
    toastr.warning("Quantity can't be Empty.");
    return false;
  }
  if(!$("#category").val()){
    toastr.warning("Category can't be Empty.");
    return false;
  }
  if(!$("#subCategory").val()){
    toastr.warning("Sub Category can't be Empty.");
    return false;
  }
  let desc=$("#description").val().split(' ');
  if(desc.length<5){
    toastr.warning("Description must be greater than 5 words.");
    return false;
  }

  return true;
}