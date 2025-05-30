(function (angular) {
  "use strict";
  var app = angular.module("wishlistModule", []);

  app.constant("config", {
    appName: "Wishlist",
    API_URL: "/wishlist",
    ITEMS_URL: "/items",
    // ContentType : 'application/x-www-form-urlencoded; charset=UTF-8',
    ContentType: "application/json",
    reqGETMethod: "GET",
    reqPOSTMethod: "POST",
  });

  app.service("dataService", ($http, API_URL) => {
    this.getData = (id) => {
      return $http.get(API_URL + "/" + id);
    };
    // this.postData=(url,data)=>{
    //   return $http.post(url,data)
    //   .then((response)=>{
    //     return response.data;
    //   })
    //   .catch((error)=>{
    //     return "Error occurred: "+ error.statusText;
    //   })

    // }
  });

  app.controller(
    "wishlistController",
    ($scope, $http, $window, $timeout, config) => {
      $scope.name = "AJAY";
      $scope.loaderDom = $("#loaderDom");
      $scope.cartDom = $("#cartDom");
      $scope.orderDetails = $("#orderDetails");
      $scope.emptyDom = $("#emptyDom");
      $scope.notLoggedInDom = $("#notLoggedInDom");
      $scope.user = {};
      $scope.qty = {};
      $scope.wishlistItems = {};
      $scope.discount = 0;
      $scope.items = 0;
      $scope.itemsPrice = 0;
      $scope.deliveryCharges = 40;
      $scope.grandTotal = 0;
      $scope.productDict = {};
      $scope.isloadingTime = true;
      $scope.showloader = false;
      $scope.productList = [];

      $scope.init = () => {
        $scope.name = "AJAY Rajput";
        console.log(loadedUserDTA);
        $scope.user = loadedUserDTA.user;
        $scope.productList = loadedUserDTA.productList;
        $scope.wishlistItems = JSON.parse(loadedUserDTA.wishlistItems);
        let ids = new Set();
        angular.forEach($scope.wishlistItems, (p, i) => {
          p.forEach((c) => {
            ids.add(c);
          });
        });
        // $scope.redirectTo();
        // $scope.items=loadedUserDTA.productList.length;
        if (loadedUserDTA.user.id == 0 && loadedUserDTA.user.email == "") {
          $scope.notLoggedInDom.show();
        } else if ($scope.productList.length == 0 && ids.size == 0) {
          $scope.emptyDom.show();
        } else {
          $scope.productList = [];
          $scope.loadData([...ids]);
          $scope.user = loadedUserDTA.user;
          angular.forEach($scope.productList, (p, i) => {
            p["quantity"] = $scope.wishlistItems[p.id];
            $scope.qty[p.id] = p.quantity;
            p["total"] = p.quantity * p.price;
            $scope.productDict[p.id] = p;
          });
          // console.log($scope.productDict);
          $scope.cartDom.show();
          $scope.recalculate();
        }
        $scope.loaderDom.addClass("loading");
      };

      $scope.getFirstImg = (imgStr) => {
        if (imgStr) {
          return "/image/" + imgStr.split(",")[0];
        }
        return "/image/no_img.jpg";
      };

      $scope.loadData = (id) => {
        $http
          .get(config.ITEMS_URL + "/" + id)
          .then(function (response) {
            // $scope.data = response.data; // Assign the fetched data to a scope variable
            response.data.data.forEach((p) => {
              $scope.productList.push(p);
              $scope.productDict[p.id] = p;
            });
            console.log($scope.productDict);
          })
          .catch(function (error) {
            console.error("Error fetching data:", error); // Handle errors
          });
      };

      $scope.SetwishlistItems = () => {
        $http({
          url: config.API_URL,
          method: config.reqPOSTMethod,
          data: JSON.stringify({
            userId: $scope.user.id,
            items: angular.toJson($scope.wishlistItems),
          }),
          headers: {
            "Content-Type": config.ContentType,
          },
        })
          .then(function (response) {
            // $scope.data = response.data; // Assign the fetched data to a scope variable
            console.log(response.data);
          })
          .catch(function (error) {
            console.error("Error fetching data:", error); // Handle errors
          });
      };
      $scope.increaseQty = (code) => {
        if ($scope.showloader) return;
        $scope.showloader = true;
        $scope.isloadingTime = false;
        // console.log($scope.qty);
        if (code in $scope.qty) {
          $scope.qty[code] += 1;
        }
        // $scope.qty[code] = ($scope.qty[code] || 0) + 1;
        $scope.recalculate();
        $scope.showloader = false;
      };
      $scope.decreaseQty = (code) => {
        if ($scope.showloader) return;
        $scope.showloader = true;
        $scope.isloadingTime = false;
        if (code in $scope.qty) {
          if ($scope.qty[code] > 0) {
            $scope.qty[code] -= 1;
          }
          // if($scope.qty[code]<1){
          //   delete $scope.qty[code];
          // }
        }
        // $scope.qty[code] = ($scope.qty[code] || 0) - 1;
        $scope.recalculate();
        $scope.showloader = false;
      };

      $scope.recalculate = () => {
        $scope.loaderDom.removeClass("loading");
        for (let code in $scope.qty) {
          if (!$scope.productDict.hasOwnProperty(code)) continue;
          if ($scope.productDict.hasOwnProperty(code) && $scope.qty[code] > 0) {
            let p = $scope.productDict[code];
            p["quantity"] = $scope.qty[code];
            p["total"] = p.quantity * p.price;
          } else {
            toastr.info(`${$scope.productDict[code].name} is removed.`);
            delete $scope.productDict[code];
            delete $scope.qty[code];
          }
        }
        $scope.wishlistItems = {};
        angular.forEach($scope.productDict, (p, code) => {
          $scope.wishlistItems[code] = p.quantity;
        });
        // console.log($scope.wishlistItems);
        if (!$scope.isloadingTime) {
          $scope.SetwishlistItems();
        }
        let products = Object.values($scope.productDict);
        $scope.items = products.length;
        $scope.itemsPrice = products.reduce((sum, p) => sum + p.total, 0);
        $scope.grandTotal = products.reduce((sum, p) => sum + p.total, 0);
        $scope.grandTotal += $scope.deliveryCharges;
        $scope.loaderDom.addClass("loading");
      };
      $scope.redirectTo = function () {
        // console.log($window.location.origin);
        $window.location.href = $window.location.origin + "/signin"; // Redirects to the desired route within the AngularJS app
      };
      $scope.goToHome = function () {
        $window.location.href = $window.location.origin; // Redirects to the desired route within the AngularJS app
      };
      $scope.removeFromCart = function (code) {
        delete $scope.productDict[code];
        delete $scope.qty[code];
        $scope.isloadingTime = false;
        $scope.recalculate();
        if (Object.keys($scope.productDict).length == 0) {
          $scope.cartDom.hide();
          $scope.emptyDom.show();
          return;
        }
      };
      $scope.goBack = () => {
        $scope.cartDom.show();
        $scope.orderDetails.hide();
      };
      $scope.placeOrder = () => {
        $scope.cartDom.hide();
        $scope.orderDetails.show();
      };
      $timeout(function () {
        $scope.init();
      }, 1000); // Delay by 1 second
    }
  );
})(window.angular);
/*
{
  "id": 93,
  "name": "Sample Product",
  "description": "This is a sample product fadfasd f sd fds",
  "price": 350.99,
  "userId": 1,
  "quantity": 15,
  "image": "05-03-2025/cde34d1e-f19e-4d3c-83eb-b2aa20ef780c_31510-9-car-transparent-background.png",
  "category": "Fashion",
  "subCategory": "Kids Fashion",
  "code": "wrerew",
  "createdAt": null,
  "updatedAt": "2025-03-05 23:42:29",
  "$$hashKey": "object:3"
}
  */
