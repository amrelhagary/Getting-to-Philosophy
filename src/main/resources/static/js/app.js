'use strict';

var app = angular.module('app', []);

app.controller('findPhilosophy', function($scope, $http){
    $scope.find = function(){
        $scope.topic = "";
        $scope.error = "";
        var wikiUrl = $scope.wikiurl;
        if(wikiUrl.indexOf("https://en.wikipedia.org/wiki/") !== -1){
            wikiUrl = wikiUrl.replace("https://en.wikipedia.org/wiki/","");
        }
        $http({
            url: "http://localhost:8080/api/find-philosophy/" + decodeURI(wikiUrl),
            method: 'GET',
            transformResponse: function(d, h){
                let p = null;
                try{
                       p = JSON.parse(d);
                }catch(e){}
                if(p !== null){
                    return p;
                }else{
                    return d;
                }
                }
             })
            .success(function(response){
                $scope.topic = response;
            })
            .error(function(response){
                $scope.error = response;
                console.info(response);
            })
            .finally(function(){
                console.log("Request Finished");
            });
    }
});