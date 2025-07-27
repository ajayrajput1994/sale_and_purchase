var relatedList = [],
  reviewList = [];

function preparingData() {
  console.log("preparing screen data...");
  relatedList = loadedDTA.relatedList;
  reviewList = loadedDTA.reviews;
  console.log(loadedDTA.relatedList);
  imageZoomer();
  $("#relatedDom").html("");
  $("#RecentlyVisitDom").html("");
  loadedDTA.relatedList.forEach((e) => {
    $("#relatedDom").append(productHtml(e));
    $("#RecentlyVisitDom").append(productHtml(e));
  });
  renderReviews();
  // localStorage.setItem("aj", "rajput ajay"); // Store data
  let data = localStorage.getItem("aj"); // Retrieve data
  console.log(data);
  // localStorage.removeItem("key"); // Delete data
}

function imageZoomer() {
  // Change main image when hovering over thumbnails
  $(".thumb").hover(function () {
    const newSrc = $(this).attr("src"); // Get the thumbnail image source
    $("#mainImage").attr("src", newSrc); // Update the main image
  });

  // Ensure the main image stays updated without resetting
  $(".thumb").mouseleave(function () {
    // No longer reverting the main image to prevent flashing
  });

  const mainImage = document.getElementById("mainImage");
  const zoomedImage = document.getElementById("zoomedImage");

  mainImage.addEventListener("mousemove", (e) => {
    const rect = mainImage.getBoundingClientRect();
    const x = ((e.clientX - rect.left) / rect.width) * 100;
    const y = ((e.clientY - rect.top) / rect.height) * 100;

    zoomedImage.style.backgroundImage = `url(${mainImage.src})`;
    zoomedImage.style.backgroundPosition = `${x}% ${y}%`;
  });

  mainImage.addEventListener("mouseleave", () => {
    zoomedImage.style.backgroundImage = "none";
  });
}
// function scrollImages(direction) {
//   customScrolling(direction,'.p-images',150);
// }
// function scrollImages(direction) {
//   customScrolling(direction,'.p-images',150);
// }

function detailingView(tabId) {
  $(".tab-dom").hide();
  $(`#${tabId}`).show();
}
function productHtml(p) {
  let image = p.image.split(",")[0];
  let h = `<div class="product-child">
          <div class="card card-b rounded-0 ng-scope">
            <div class="card_img_parent"> 
            <img src="/image/${image}" class="card-img-top" alt="...">
            </div><div class="card-body">
              <a  href="/${p.code}" class="card-title  card-title-line-limit"><h6>${p.name}</h6></a>
              <span class="card_price">${p.price} Rs/-</span><span class="cat_title">(${p.category})</span>
              <p class="card-text card-text-line-limit">${p.description}</p>
            </div>
            <div class="card-footer d-flex justify-content-between bg-white">
                <div><i class="fa-regular fa-heart"></i></div>
                <div><i class="fa-solid fa-cart-arrow-down"></i></div>
                <div><i class="fa-solid fa-share-nodes"></i></div>
                <div><span>4.5</span><i class="fa-solid fa-star"></i></div>
            </div>
          </div> 
        </div> `;
  return h;
}

function renderReviews() {
  // { id: 3190, rating: 5, comment: "nice!", date: "2025-06-04 23:37:39", product_id: 245 },

  const totalReviews = reviewList.length;
  const totalRating = reviewList.reduce(
    (sum, review) => sum + review.rating,
    0
  );
  const averageRating = (totalRating / totalReviews).toFixed(2);

  const ratingCounts = {};
  let h = [];
  reviewList.forEach((review) => {
    ratingCounts[parseInt(review.rating)] =
      (ratingCounts[review.rating] || 0) + 1;
    h.push(`<div class="card mb-3" style="width:98%"> 
      <div class="card-body">
        <h5 class="card-title">${review.review}</h5>
        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card’s content.</p>
        <span class="wishlistDom-icon ">
          <i class="fa-regular fa-thumbs-up"><span class="counter_txt">10</span></i>
          <i class="fa-regular fa-thumbs-down"><span class="counter_txt">10</span></i>
        </span>
      </div>
    </div>`);
  });

  console.log("Total Reviews:", totalReviews);
  console.log("Total Rating:", totalRating);
  console.log("Average Rating:", averageRating);
  console.log("Rating Counts:", ratingCounts);
  $("#ratingChild2").html(h.join(""));
  h = ['<table class="review_table">'];
  for (let i = 5; i >= 1; i--) {
    let count = 0;
    try {
      count = ratingCounts[i].toLocaleString();
    } catch (e) {}
    const percent = (count / totalRating) * 100;
    h.push(`<tr><td>${i}</td>`);
    h.push(`<td>
              <div class="progress rounded-0" style="height: 10px;">
                <div class="progress-bar bg-${
                  i > 2 ? "success" : i == 2 ? "warning" : "danger"
                }" role="progressbar" style="width: ${percent}%" aria-valuenow="${percent}"
                  aria-valuemin="0" aria-valuemax="100"></div>
              </div>
            </td>`);
    h.push(`<td>${count}</td></tr>`);
  }
  h.push("</table>");
  $("#reviewtableDom").html(h.join(""));
  $("#avg_rating").html(
    `${averageRating} <i class="fa-sharp fa-solid fa-star"></i>`
  );
  $("#total_rating").html(
    `${totalRating} Ratings<br>&<br>${totalReviews} Reviews`
  );
}
