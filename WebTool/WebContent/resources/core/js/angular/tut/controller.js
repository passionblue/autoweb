'use strict';

/* Controllers */

var phonecatApp = angular.module('phonecatApp', []);

phonecatApp.controller('PhoneListCtrl', ['$scope', '$http', function($scope, $http) {

	/*
	 *  $http.get('/resources/core/phones/phones.json').success(function(data) {   <----- failed to load data with "/" at the beginning
	 *  
	 */
	
/*
	
	$http.get('resources/core/phones/phones.json').success(function(data) {
		$scope.phones = data;
	});
	
	
*/
	
	/*
	 * this is supposed to be relative url. 
	 * 
	 * but it works when http://localhost:8080/spring-mvc-form/ but not http://localhost:8080/spring-mvc-form 
	 * Both should work
	 * 
	 */

	/* failed
	
	$http.get('resources/core/phones/phones.json').then(function(data) {
		$scope.phones = data;
	}, function(data) {
	});
	*/

	/*
	$http.get('angular/get-json').success(function(data) {
		$scope.phones = data;

		var popupWin = window.open('', '_blank', 'width=300,height=300');
		  popupWin.document.open();
		  popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /></head><body onload="window.print()">' + data + '</body></html>');
		  popupWin.document.close();
	
	});
	*/
	
    $http({method: 'GET', url: 'angular/get-json'}).
    then(function(response) {
    	
      $scope.status = response.status;
      $scope.phones = response.data;
      
		var popupWin = window.open('', 'xxx', 'width=100,height=100');
		  popupWin.document.open();
		  popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /></head><body">' +  response.data + '</body></html>');
		  popupWin.document.close();
      
    }, function(response) {
    	
      $scope.phones = response.data || "Request failed";
      $scope.status = response.status;

		var popupWin = window.open('', '_blank', 'width=300,height=300');
		  popupWin.document.open();
		  popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /></head><body">' +  $scope.status + '</body></html>');
		  popupWin.document.close();
    });	
	
  $scope.orderProp = 'age';
  
}]);