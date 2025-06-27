(function (angular) {
  "use strict";
  var app = angular.module("cartModule", []);

  app.constant("config", {
    appName: "Cart",
    API_URL: "/cart",
    ORDER_URL: "/Api/create-order",
    PAYMENT_VERIFY_URL: "/Api/verify-payment",
    PAYMENT_FAILED_URL: "/Api/payment-failed",
    GET_ADDRESS_URL: "/Api/User/Address",
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

  app.controller("cartController", ($scope, $http, $window, config) => {
    $scope.name = "AJAY";
    $scope.loaderDom = $("#loaderDom");
    $scope.cartDom = $("#cartDom");
    $scope.orderDetails = $("#orderDetails");
    $scope.emptyDom = $("#emptyDom");
    $scope.notLoggedInDom = $("#notLoggedInDom");
    $scope.user = {};
    $scope.qty = {};
    $scope.cartItems = {};
    $scope.discount = 0;
    $scope.items = 0;
    $scope.itemsPrice = 0;
    $scope.deliveryCharges = 0;
    $scope.selectedAddress = 0;
    $scope.grandTotal = 0;
    $scope.productDict = {};
    $scope.isloadingTime = true;
    $scope.showloader = false;
    $scope.productList = [];
    $scope.addressDta = {};

    $scope.init = () => {
      $scope.name = "AJAY Rajput";
      $scope.user = loadedDTA.user;
      $scope.productList = loadedDTA.productList;
      // $scope.redirectTo();
      // console.log(loadedDTA);
      // $scope.items=loadedDTA.productList.length;
      if (loadedDTA.user.id == 0 && loadedDTA.user.email == "") {
        $scope.notLoggedInDom.show();
      } else if ($scope.productList.length == 0) {
        $scope.emptyDom.show();
      } else {
        // $scope.loadData(loadedDTA.user.id);
        $scope.user = loadedDTA.user;
        $scope.cartItems = JSON.parse(loadedDTA.cartItems);
        angular.forEach($scope.productList, (p, i) => {
          p["quantity"] = $scope.cartItems[p.id];
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
        .get(config.API_URL + "/" + id)
        .then(function (response) {
          // $scope.data = response.data; // Assign the fetched data to a scope variable

          console.log(response.data);
        })
        .catch(function (error) {
          console.error("Error fetching data:", error); // Handle errors
        });
    };
    $scope.getAddress = () => {
      $http
        .get(config.GET_ADDRESS_URL + "/" + $scope.user.id)
        .then(function (response) {
          // $scope.data = response.data; // Assign the fetched data to a scope variable
          console.log(response.data);
          angular.forEach(response.data.data, (d, i) => {
            if (d.active != "") {
              $scope.selectedAddress = d.id;
            }
            $scope.addressDta[d.id] = {
              n: d.name,
              c: d.city,
              s: d.state,
              r: d.region,
              p: d.phone,
              pc: d.pin_code,
              a: d.address,
              l: d.landmark,
              t: d.address_type,
              o: d.other_phone,
              at: d.active,
            };
          });
          console.log($scope.addressDta);
          $scope.cartDom.hide();
          $scope.orderDetails.show();
          infoSweetToaster("Address", "Please Select one address!");
        })
        .catch(function (error) {
          console.error("Error fetching address:", error); // Handle errors
        });
    };
    $scope.SetCartItems = () => {
      $http({
        url: config.API_URL,
        method: config.reqPOSTMethod,
        data: JSON.stringify({
          userId: $scope.user.id,
          items: angular.toJson($scope.cartItems),
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
      $scope.cartItems = {};
      angular.forEach($scope.productDict, (p, code) => {
        $scope.cartItems[code] = p.quantity;
      });
      // console.log($scope.cartItems);
      if (!$scope.isloadingTime) {
        $scope.SetCartItems();
      }
      let products = Object.values($scope.productDict);
      $scope.items = products.length;
      $scope.itemsPrice = products.reduce((sum, p) => sum + p.total, 0);
      $scope.grandTotal = products.reduce((sum, p) => sum + p.total, 0);
      if ($scope.grandTotal < 500) {
        $scope.deliveryCharges = 40;
      }
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
      if ($scope.selectedAddress == 0) {
        infoSweetToaster("Address", "Please Select one address!");
      }
      $scope.cartDom.hide();
      $scope.orderDetails.show();
      $http({
        url: config.ORDER_URL,
        method: config.reqPOSTMethod,
        data: JSON.stringify({
          userId: $scope.user.id.toString(),
          items: angular.toJson($scope.cartItems),
          billing: angular.toJson($scope.addressDta[$scope.selectedAddress]),
          total: $scope.grandTotal,
        }),
        headers: {
          "Content-Type": config.ContentType,
        },
      })
        .then(function (r) {
          // $scope.data = response.data; // Assign the fetched data to a scope variable
          console.log("order created: " + r.data);
          $scope.paynow(r.data.data);
        })
        .catch(function (error) {
          console.error("Error fetching data:", error); // Handle errors
        });
    };
    $scope.paynow = (d) => {
      console.log(d);
      const options = {
        key: "rzp_test_AxAZNCXYzwYinS", //key id
        amount: d.amount * 100, // in paise
        currency: "INR",
        name: "The Digital Den",
        description: "Order Payment",
        order_id: d.rzpOrderId, // from backend
        handler: function (razorpayResponse) {
          console.log("Payment successful:", razorpayResponse);

          // Optional: verify payment on backend
          // $http
          //   .post(config.PAYMENT_VERIFY_URL, {
          //     ...razorpayResponse,
          //     orderId: response.data.orderId,
          //   })
          $http({
            url: config.PAYMENT_VERIFY_URL,
            method: config.reqPOSTMethod,
            data: JSON.stringify({
              userId: $scope.user.id.toString(),
              payId: d.id.toString(),
              paymentId: razorpayResponse.razorpay_payment_id,
              orderId: razorpayResponse.razorpay_order_id,
            }),
            headers: {
              "Content-Type": config.ContentType,
            },
          })
            .then((res) => {
              console.log("Payment verified:", res.data);
            })
            .catch((err) => {
              console.error("Verification failed:", err);
            });
        },
        theme: {
          color: "#3399cc",
        },
      };

      const rzp = new Razorpay(options);
      rzp.on("payment.failed", function (response) {
        console.log(response.error.code);
        console.log(response.error.description);
        console.log(response.error.source);
        console.log(response.error.step);
        console.log(response.error.reason);
        console.log(response.error.metadata.order_id);
        console.log(response.error.metadata.payment_id);
        $http({
          url: config.PAYMENT_FAILED_URL,
          method: config.reqPOSTMethod,
          data: JSON.stringify({
            payId: d.id.toString(),
            paymentId: response.error.metadata.payment_id,
            orderId: response.error.metadata.order_id,
          }),
          headers: {
            "Content-Type": config.ContentType,
          },
        })
          .then((res) => {
            console.log("Payment failer update:", res.data);
          })
          .catch((err) => {
            console.error("Verification failed:", err);
          });
      });
      rzp.open();
    };
    $scope.init();
  });
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
