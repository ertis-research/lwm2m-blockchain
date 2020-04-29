var app = angular.module('myApp', []);

function updateClient(updated, clients) {
    return clients.reduce(function(accu, client) {
        if (updated.endpoint === client.endpoint) {
            accu.push(updated);
        } else {
            accu.push(client);
        }
        return accu;
    }, []);
}


app.controller('ClientListCtrl', [
    '$scope',
    '$http',
    '$location',
    function ClientListCtrl($scope, $http,$location) {

    	// update navbar
        angular.element("#navbar").children().removeClass('active');
        angular.element("#client-navlink").addClass('active');

        // free resource when controller is destroyed
        $scope.$on('$destroy', function(){
            if ($scope.eventsource){
                $scope.eventsource.close();
            }
        });

        // add function to show client
        $scope.showClient = function(client) {
            $location.path('/clients/' + client.endpoint);
        };

        // the tooltip message to display for a client (all standard attributes, plus additional ones)
        $scope.clientTooltip = function(client) {
            var standard = ["Lifetime: " + client.lifetime + "s",
                            "Binding mode: " + client.bindingMode,
                            "Protocol version: " + client.lwM2mVersion,
                            "Address: " + client.address];

            var tooltip = standard.join("<br/>");
            if (client.additionalRegistrationAttributes) {
                var attributes = client.additionalRegistrationAttributes;
                var additionals = [];
                for (key in attributes) {
                    var value = attributes[key];
                    additionals.push(key + " : " + value);
                }
                if (additionals.length>0){
                    tooltip = tooltip + "<hr/>" + additionals.join("<br/>");
                }
            }
            return tooltip;
        };
    	
    	$http.get('api/clients').then(function(data, status, headers, config) {
    		console.log(data);
    		$scope.clients = data.data;

            // HACK : we can not use ng-if="clients"
            // because of https://github.com/angular/angular.js/issues/3969
            $scope.clientslist = true;

            // listen for clients registration/deregistration
            $scope.eventsource = new EventSource('event');

            var registerCallback = function(msg) {
                $scope.$apply(function() {
                    var client = JSON.parse(msg.data);
                    $scope.clients.push(client);
                });
            };

            var updateCallback =  function(msg) {
                $scope.$apply(function() {
                    var client = JSON.parse(msg.data);
                    $scope.clients = updateClient(client.registration, $scope.clients);
                });
            };

            var sleepingCallback =  function(msg) {
                $scope.$apply(function() {
                    var data = JSON.parse(msg.data);
                    for (var i = 0; i < $scope.clients.length; i++) {
                        if ($scope.clients[i].endpoint === data.ep) {
                            $scope.clients[i].sleeping = true;
                        }
                    }
                });
            };

            var awakeCallback =  function(msg) {
                $scope.$apply(function() {
                    var data = JSON.parse(msg.data);
                    for (var i = 0; i < $scope.clients.length; i++) {
                        if ($scope.clients[i].endpoint === data.ep) {
                            $scope.clients[i].sleeping = false;
                        }
                    }
                });
            };

            $scope.eventsource.addEventListener('REGISTRATION', registerCallback, false);

            $scope.eventsource.addEventListener('UPDATED', updateCallback, false);

            $scope.eventsource.addEventListener('SLEEPING', sleepingCallback, false);

            $scope.eventsource.addEventListener('AWAKE', awakeCallback, false);

            var getClientIdx = function(client) {
                for (var i = 0; i < $scope.clients.length; i++) {
                    if ($scope.clients[i].registrationId == client.registrationId) {
                        return i;
                    }
                }
                return -1;
            };
            var deregisterCallback = function(msg) {
                $scope.$apply(function() {
                    var clientIdx = getClientIdx(JSON.parse(msg.data));
                    if(clientIdx >= 0) {
                        $scope.clients.splice(clientIdx, 1);
                    }
                });
            };
            $scope.eventsource.addEventListener('DEREGISTRATION', deregisterCallback, false);
        }, function(data, status, headers, config){
            $scope.error = "Unable to get client list: " + status + " " + data;
            console.error($scope.error);
        });   
}]);


