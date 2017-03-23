var app = angular.module('myApp');

app.factory('treatmentDetailService', function($http){
	
	
	 var createTreatmentDetail = function(treatment){
		 return $http.post("http://localhost:8080/treatmentdt",treatment)
		  .then(function(response){
				return response.data;
			});
	 }
	 
	 var getAllTreatmentDetail = function(){
		 return  $http.get("http://localhost:8080/treatmentdt")
		  .then(function(response){
				return response.data;
			});
	 }
	 
	 var deleteTreatmentDetail = function(id){
		 return $http.delete("http://localhost:8080/treatmentdt/"+id)
		  .then(function(response){
				return response.data;
			});
	 }
	 
	 var deleteFile = function(id){
		 return $http.delete("http://localhost:8080/file/"+id)
		  .then(function(response){
				return response.data;
			});
	 }
	 return{
		 createTreatmentDetail:createTreatmentDetail,
		 getAllTreatmentDetail:getAllTreatmentDetail,
		 deleteTreatmentDetail:deleteTreatmentDetail,
		 deleteFile:deleteFile,
	 }
	
});