((angular) => {
  "use strict";
  var app = angular.module("homeModule", []);
  app.constant("config", {
    appName: "Welcome to home.",
    API_URL: "/",
    CART_URL: "/cart",
    WISHLIST_URL: "/wishlist",
    ContentType: "application/json",
    reqGetMethod: "GET",
    reqPostMethod: "POST",
  });

  app.controller(
    "homeController",
    ($scope, $compile, $http, $window, config) => {
      $scope.loaderDom = $("#loaderDom");
      ($scope.categoryDom = $("#categoryDom")),
        ($scope.newProductDom = $("#newProductDom")),
        ($scope.topPicksDom = $("#topPicksDom")),
        ($scope.subCatDom = $("#subCatDom")),
        ($scope.RecentlyVisitDom = $("#RecentlyVisitDom"));
      $scope.productList = [];
      $scope.categoryDict = {};
      $scope.mainCategoryDict = {};
      $scope.subcatDict = {};
      $scope.catSubcatDict = {};
      $scope.miniCatSubDict = {};
      $scope.newproductList = [];
      $scope.topPickList = [];
      $scope.user = {};
      $scope.cartItems = {};
      $scope.wishlist = {};
      $scope.init = () => {
        $scope.user = loadedDTA.user;
        if ($scope.user.id != 0) {
          $scope.cartItems = JSON.parse(loadedDTA.cartItems);
          $scope.wishlist = JSON.parse(loadedDTA.wishlist);
        }
        $scope.productList = loadedDTA.productList;
        angular.forEach(loadedDTA.mainCategory, (d, i) => {
          $scope.mainCategoryDict[d.title] = d;
        });
        angular.forEach(loadedDTA.category, (d, i) => {
          $scope.categoryDict[d.title] = d;
          if ($scope.miniCatSubDict.hasOwnProperty(d.subCategory)) {
            $scope.miniCatSubDict[d.subCategory].push(d.title);
          } else {
            $scope.miniCatSubDict[d.subCategory] = [d.title];
          }
        });
        angular.forEach(loadedDTA.subCategory, (d, i) => {
          $scope.subcatDict[d.title] = d;
          if ($scope.catSubcatDict.hasOwnProperty(d.mainCatalog)) {
            $scope.catSubcatDict[d.mainCatalog].push(d.title);
          } else {
            $scope.catSubcatDict[d.mainCatalog] = [d.title];
          }
        });
        // $scope.productList.sort((a,b)=>b.id-a.id);
        // angular.forEach($scope.productList,(p,i)=>{
        //   if(i>5)return false;
        //   $scope.newproductList.push(p);
        // });
        console.log($scope.mainCategoryDict);
        console.log($scope.catSubcatDict);
        console.log($scope.miniCatSubDict);
        $scope.laodCategory();
        $scope.loaderDom.addClass("loading");
        var dta = {
          default: [93, 94, 95],
        };
        console.log(JSON.stringify(dta));
      };

      $scope.laodCategory = () => {
        angular.forEach($scope.catSubcatDict, (d, title) => {
          let compiledHtml = $compile($scope.categoryHtml(title, d))($scope);
          $scope.categoryDom.append(compiledHtml);
        });
      };
      $scope.loadNewProducts = () => {
        $(".newProductDom").show();
        // $scope.newproductList=[]
        $scope.productList.sort((a, b) => b.id - a.id);
        angular.forEach($scope.productList, (p, i) => {
          if (i > 4) return false;
          // p.image=p.image.split(',')[0];
          // $scope.newproductList.push(p);
          let compiledHtml = $compile($scope.productHtml(p))($scope);
          $scope.newProductDom.append(compiledHtml);
        });
        console.log("Loading new products...");
        // Add your logic here
      };

      $scope.loadTopPicks = () => {
        $(".topPicksDom").show();
        $scope.productList.sort((a, b) => a.id - b.id);
        angular.forEach($scope.productList, (p, i) => {
          if (i > 4) return false;
          let compiledHtml = $compile($scope.productHtml(p))($scope);
          $scope.topPicksDom.append(compiledHtml);
        });
      };

      $scope.loadSubCategory = () => {
        console.log("Loading subcategories...");
        $(".subCatDom").show();
        $scope.subCatDom.show();
        angular.forEach($scope.miniCatSubDict, (d, title) => {
          console.log(d);
          let compiledHtml = $compile($scope.subcatHTML(d, title))($scope);
          $scope.subCatDom.append(compiledHtml);
        });
        // Add your logic here
      };

      $scope.loadRecentlyVisited = () => {
        $(".RecentlyVisitDom").show();
        $scope.productList.sort((a, b) => a.name - b.name);
        angular.forEach($scope.productList, (p, i) => {
          if (i > 4) return false;
          let compiledHtml = $compile($scope.productHtml(p))($scope);
          $scope.RecentlyVisitDom.append(compiledHtml);
        });
      };

      /*start scrolling loading functions*/
      $scope.functionsToLoad = [
        $scope.loadNewProducts,
        $scope.loadSubCategory,
        $scope.loadTopPicks,
        $scope.loadRecentlyVisited,
      ];
      angular.element($window).bind("scroll", function () {
        if (
          $window.pageYOffset + $window.innerHeight >=
          document.body.offsetHeight - 100
        ) {
          if ($scope.functionsToLoad.length > 0) {
            let nextFunction = $scope.functionsToLoad.shift();
            if (typeof nextFunction === "function") {
              nextFunction();
              $scope.$apply();
            } else {
              console.error(
                "Error: The next function is not callable.",
                nextFunction
              );
            }
          }
        }
      });
      $scope.$on("$destroy", function () {
        angular.element($window).unbind("scroll");
      });
      /*end scrolling loading function*/

      $scope.testing = () => {
        console.log("ok");
      };
      $scope.gotoProductDetails = (prodCode) => {
        $window.open("/code/" + prodCode, "_blank");
      };
      $scope.gotoFilter = (title) => {
        $window.open("/" + title, "_blank");
      };
      $scope.addToCart = (pid) => {
        if ($scope.user.id == 0) {
          // toastr.info("To unlock your Wishlist and Cart, please log in! Sign up or log in to save your favorites and continue shopping seamlessly!");
          wishlistCartInfoToaster(
            "To unlock your Cart",
            "please log in! Sign up or log in to save your favorites and continue shopping seamlessly!"
          );
          return;
        }
        if (pid == 0) {
          return;
        }
        if ($scope.cartItems.hasOwnProperty(pid)) {
          // toastr.info("already added!");
          cartSuccessToaster("Already added!", "");
          return;
        }
        $scope.cartItems[pid] = 1;
        $scope.loaderDom.removeClass("loading");
        $http({
          url: config.CART_URL,
          method: config.reqPostMethod,
          data: JSON.stringify({
            userId: $scope.user.id,
            items: angular.toJson($scope.cartItems),
          }),
          headers: {
            "Content-Type": config.ContentType,
          },
        })
          .then(function (response) {
            $scope.cartItems = JSON.parse(response.data.items);
            console.log(response.data);
            // toastr.info("Successfuly add to cart!");
            cartSuccessToaster("Successfuly add to cart!", "");
          })
          .catch(function (error) {
            console.error("Error fetching data:", error); // Handle errors
          });
        $scope.loaderDom.addClass("loading");
      };

      $scope.addToWishlist = async (pid) => {
        if ($scope.user.id == 0) {
          // toastr.info("To unlock your Wishlist and Cart, please log in! Sign up or log in to save your favorites and continue shopping seamlessly!");
          wishlistCartInfoToaster(
            "unlock your Wishlist",
            "please log in! Sign up or log in to save your favorites and continue shopping seamlessly!"
          );
          return;
        }
        if (pid == 0) {
          return;
        }
        if ($scope.wishlist.default.includes(pid)) {
          // toastr.info("Already added!");
          wishlistSuccessToaster("Already added!", "");
          return;
        }
        $scope.wishlist["default"].push(pid);
        $scope.loaderDom.removeClass("loading");
        $http({
          url: config.WISHLIST_URL,
          method: config.reqPostMethod,
          data: JSON.stringify({
            userId: $scope.user.id,
            collection: "default",
            items: angular.toJson($scope.wishlist),
          }),
          headers: {
            "Content-Type": config.ContentType,
          },
        })
          .then(function (response) {
            $scope.cartItems = JSON.parse(response.data.items);
            console.log(response.data);
            // toastr.info("Successfuly add to Wishlist!");
            wishlistSuccessToaster("Successfuly add to Wishlist!", "");
          })
          .catch(function (error) {
            console.error("Error fetching data:", error); // Handle errors
          });
        $scope.loaderDom.addClass("loading");
      };
      $scope.categoryHtml = (title, subList) => {
        let img = "no_img.jpg";
        let h = `<div class="">
            <div class="card rounded-0"> 
              <div class="card-body">
                <h5 class="card-title">${title}</h5> 
                  <div class="categoryDomChild">`;
        subList.forEach((cat, i) => {
          if (i > 3) return false;
          if (cat in $scope.subcatDict) {
            let d = $scope.subcatDict[cat];
            h += `<div class="catNChild" ng-click="gotoFilter('${cat}')"><img src="/image/${
              d.image == "" ? img : d.image
            }"  class="subcatImg"/>
                    <span class="subcatTitle">${cat}</span>
                    </div>`;
          }
        });
        h += `</div> 
                <div class="d-flex justify-content-end mt-3">

                  <a class="my_btn" ng-click="testing()">see more</a>
                </div>
              </div>
            </div>
          </div>`;
        return h;
      };
      $scope.productHtml = (p) => {
        let image = p.image.split(",")[0];
        return `<div class="product-child">
          <div class="card card-b rounded-0 ng-scope">
            <div class="card_img_parent cursor" ng-click="gotoProductDetails('${p.code}')"> 
            <img src="/image/${image}" class="card-img-top" alt="...">
            </div><div class="card-body">
              <div  ng-click="gotoProductDetails('${p.code}')" class="card-title  card-title-line-limit"><h6>${p.name}</h6></div>
              <span class="card_price">${p.price} Rs/-</span><span class="cat_title">(${p.category})</span>
              <p class="card-text card-text-line-limit">${p.description}</p>
            </div>
            <div class="card-footer d-flex justify-content-between bg-white">
                <div><i class="fa-regular fa-heart cursor" ng-click="addToWishlist(${p.id})"></i></div>
                <div><i class="fa-solid fa-cart-arrow-down cursor" ng-click="addToCart(${p.id})"></i></div>
                <div><i class="fa-solid fa-share-nodes cursor"></i></div>
                <div><span>4.5</span><i class="fa-solid fa-star"></i></div>
            </div>
          </div> 
        </div> `;
      };
      $scope.subcatHTML = (catlist, title) => {
        // <p class="card-text placeholder-glow row">
        //   <span class="placeholder col-6 mx-auto card_img"></span>
        // </p>
        let h = `<div class="col-md-3 mt-2">
            <div class="card rounded-0" aria-hidden="true"> 
              <div class="card-body">
                <h5 class="card-title ">${title}</h5>
               <div class="card-text  minCatDomChild">`;
        catlist.forEach((cat, i) => {
          if (i > 3) return false;
          if (cat in $scope.categoryDict) {
            let d = $scope.categoryDict[cat];
            h += `<div class="subcat2"  ng-click="gotoFilter('${cat}')"><img src="/image/${
              d.image == "" ? img : d.image
            }" />
                    <span >${cat}</span>
                    </div>`;
          }
        });
        h += `</div> 
                <div class=" d-flex justify-content-end">

                  <a class="my_btn" ng-click="testing()">see more</a>
                </div>
              </div>
            </div>
          </div>`;
        return h;
      };
      $scope.init();
    }
  );
})(window.angular);
