function infoSweetToaster(title,msg){
  Swal.fire({
    "icon": "info",
    "title": title,
    "text": msg,
    "timer":5000,
  });
}

function wishlistSuccessToaster(title,msg){
  Swal.fire({
    "icon": "success",
    "title": title,
    "text": msg,
    "timer":2000,
    showCancelButton:true,
    confirmButtonText:`Go to wishlist <i class="fa-regular fa-heart"></i>`,
    cancelButtonText: `Later`,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.onmouseenter = Swal.stopTimer;
      toast.onmouseleave = Swal.resumeTimer;
    }
  }).then((result)=>{
    if(result.isConfirmed){
      window.location.href='/wishlist';
      // window.open('/wishlist','_blank');
    }
  });
}
function wishlistCartInfoToaster(title,msg){
  Swal.fire({
    "icon": "info",
    "title": title,
    "text": msg,
    "timer":3000,
    showDenyButton:true,
    showCancelButton:true,
    confirmButtonText:"Login",
    denyButtonText: `Sign up`,
    cancelButtonText: `Later`,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.onmouseenter = Swal.stopTimer;
      toast.onmouseleave = Swal.resumeTimer;
    }
  }).then((result)=>{
    if(result.isConfirmed){
      // window.location.href='/signin';
      window.open('/signin','_blank');
    }
    else if(result.isDenied){
      window.open('/signup','_blank');

    }
  });
}

function cartSuccessToaster(title,msg){
  Swal.fire({
    "icon": "success",
    "title": title,
    "text": msg,
    "timer":2000,
    showCancelButton:true,
    confirmButtonText:`Go to cart <i class="fa-solid fa-cart-shopping"></i>`,
    cancelButtonText: `Later`,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.onmouseenter = Swal.stopTimer;
      toast.onmouseleave = Swal.resumeTimer;
    }
  }).then((result)=>{
    if(result.isConfirmed){
      window.location.href='/cart';
      // window.open('/wishlist','_blank');
    }
  });
}