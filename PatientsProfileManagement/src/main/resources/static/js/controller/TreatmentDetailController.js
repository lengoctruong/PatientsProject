var app = angular.module('myApp');

app.controller('treatmentDetailController', function(
        $scope, $interval, $location,$http) {
	
	$http.get("http://localhost:8080/treatment").then(function(response) {
		$scope.patients = response.data;
    });

});