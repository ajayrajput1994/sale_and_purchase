$(document).ready(function(){
  getMyLocation();
})
function getMyLocation(){
  const watchID = navigator.geolocation.watchPosition(successCB, errorCB, optionsCB);
}
function successCB(position) {
  // latitude=position.coords.latitude;
  // longitude=position.coords.longitude;
  var bdcApi = `https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${latitude=position.coords.latitude}&longitude=${position.coords.longitude}&localityLanguage=en`;
  console.log(bdcApi);
  $.ajax({
    url: bdcApi,
    type: 'GET',
    dataType: 'json', // added data type
    success: function(res) {
       // console.log(res);
      var loc=`${res.city} ${res.principalSubdivision} ${res.principalSubdivision}`;
        $("#currentloc").val(res.city);
        $("#currentloc").html(loc);
    }
  });
}

function errorCB() {
  alert("Sorry, no position available.");
}

const optionsCB = {
  enableHighAccuracy: true,
  maximumAge: 30000,
  timeout: 27000,
};

