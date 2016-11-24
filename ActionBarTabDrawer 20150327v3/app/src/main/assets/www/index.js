// 快速關聯HTML標籤用
function $(id) {return document.getElementById(id);}

// 存放GoogleMap物件用
var map;

document.addEventListener("DOMContentLoaded", init);

// 初始化並載入GoogleMap
function init()
{
    // 建立經緯度物件
    var myLatLng = new google.maps.LatLng(25.046293, 121.517607); // 台北車站

    // 設定GoogleMap屬性常數(通常用來設定GoogleMap上要放甚麼元件)
    var myOptions = 
    {
        zoom: 17,                  // 地圖的遠近
        center: myLatLng,          // 地圖載入時的中心位置(須使用經緯度物件)
        mapTypeId: google.maps.MapTypeId.ROADMAP,  // 地圖樣式(一般街道圖)
        streetViewControl: false   // 擺放StreetView元件
    }

    // 關聯mapCanvas標籤
    var mapCanvas = $("mapCanvas");

    // 建立GoogleMap物件並顯示在div上
    map = new google.maps.Map(mapCanvas, myOptions);

    // 測試標記
    centerAt(25.046293, 121.517607);
}

// 存放標記物件
var locationMarker;

function centerAt(latitude, longitude)
{
    var myLatLng = new google.maps.LatLng(latitude, longitude);

    // 移除舊的Marker
    if(locationMarker)
    {
        locationMarker.setMap(null);
        locationMarker = null;
    }

    // 根據新的座標建立新的Marker
    locationMarker = new google.maps.Marker(
    {
        position: myLatLng, // Marker要顯示在哪個座標上
        map: map,           // 要顯示的地圖物件
        icon: "car.png"     // 要當Marker的圖片
    });

    // 將要設定的座標拉到地圖中央
    map.panTo(myLatLng);

    // 設定Marker被點擊後要做的事情
    google.maps.event.addListener(locationMarker, "click", onMarkerClicked);
}

// Marker(車子)被點擊後要做的事情(跳出InfoWindow)
function onMarkerClicked()
{
    // 建立GoogleMap用InfoWindow
    var infoWindow = new google.maps.InfoWindow(
    {
        // InfoWindow內要顯示的內容, 格式為HTML
        content: "<a href='http://www.aaronlife.com'><img src='pic.jpg'" +
                 "width='220px' height='150px'/></a><br/>我在這裡!"
    });

    // 將InfoWindow顯示出來
    // 參數一: InfoWindow要出現的GoogleMap物件
    // 參數二: InfoWindow要出現的位置(Marker物件上)
    infoWindow.open(map, locationMarker);
}