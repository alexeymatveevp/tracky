var app = angular.module('app', []);
app.controller('ctrl', function($scope, $http) {
    $scope.personNames = ['alex', 'kristino4ka'];
    $scope.endpoints = ['http://localhost:8080', 'http://91.240.84.2:8080', 'http://192.168.10.22:8080', 'http://192.168.10.21:8080'];
    $scope.endpoint = 'http://localhost:8080';

    $scope.putFoody = function() {
      var request = {
        "name": $scope.foodname,
        "person": $scope.foodperson
      };
      if ($scope.fooddate) {
        request.date = $scope.fooddate;
      };
      if ($scope.foodweight) {
        request.weight = $scope.foodweight;
      }
      // var params = '?name=' + $scope.foodname + '&person=' + $scope.foodperson;
      // if ($scope.fooddate) {
      //   params = params + '&date=' + $scope.fooddate;
      // }
      // if ($scope.foodweight) {
      //   params = params + '&weight=' + $scope.foodweight;
      // }
      // $http.get($scope.endpoint + '/foody' + params).then(function(res) {
      //   console.log(res);
      //   $scope.refreshFoodies();
      // });
      $http.put($scope.endpoint + '/foody', request).then(function(res) {
        console.log(res);
        $scope.refreshFoodies();
      });
    }

    $scope.refreshFoodies = function() {
      $http.get($scope.endpoint + '/foodies').then(function(res) {
        $scope.foodies = res.data;
      })
    }

    $scope.refreshFoodies();
});
