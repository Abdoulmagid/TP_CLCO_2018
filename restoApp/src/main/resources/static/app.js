angular.module("myApp", ["ui.router", "ngStorage", "ui-notification"])

    .run(
        [   '$rootScope', '$state', '$stateParams', '$localStorage',
            function ($rootScope, $state, $stateParams, $localStorage) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
                $rootScope.Math = window.Math;
                $rootScope.$storage = $localStorage.$default({
                    user: null,
                    authenticated: false
                });
            }
        ]
    )

    .config(
        [   '$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {

                $stateProvider.state("home", {
                    url: "/",
                    templateUrl: "/views/home.html",
                    controller: [
                        '$scope', '$state', '$http', 'Notification',
                        function ($scope, $state, $http, Notification) {

                            $scope.suerrorMessage = "";
                            $scope.sierrorMessage = "";

                            $scope.signup = function (visitor) {
                                $http.post("/visitors/signup", visitor)
                                    .then(function (resp) {
                                        if (!resp.data.errors) {
                                            //console.log(resp);
                                            $scope.$storage.user = resp.data;
                                            $scope.$storage.authenticated = true;
                                            $state.go('restaurants.list', {}, { reload: true });
                                            Notification.success('Welcome, '+resp.data.username+'. Account successful created !');
                                        } else {
                                            //console.log(resp);
                                            $scope.suerrorMessage = resp.data.message;
                                            Notification.error('Sorry, It seem like there are some error. Try again !');
                                        }
                                    })
                            };

                            $scope.signin = function (signin) {
                                $http.post("/visitors/signin", signin)
                                    .then(function (resp) {
                                        if (!resp.data.errors) {
                                            //console.log(resp);
                                            $scope.$storage.user = resp.data;
                                            $scope.$storage.authenticated = true;
                                            $state.go('restaurants.list', {}, { reload: true });
                                            Notification.info('Happy seeing you, '+resp.data.username+' !');
                                        } else {
                                            //console.log(resp);
                                            $scope.sierrorMessage = resp.data.message;
                                            Notification.error('Sorry, It seem like there are some error. Try again !');
                                        }
                                    })
                            };

                            $scope.logout = function () {
                                $scope.$storage.user = null;
                                $scope.$storage.authenticated = false;
                                Notification.success('Successful logout, See you next time or continue visiting !');
                            };

                        }
                    ]
                });

                $stateProvider.state("restaurants", {
                    abstract: true,
                    url: "/restaurants",
                    templateUrl: "views/restaurants.html",
                    resolve: {
                        restaurantsData: ['RestaurantService', function(RestaurantService) {
                            return RestaurantService.getAllRestaurants();
                        }]
                    },
                    controller: [
                        '$scope', '$state', '$http', 'restaurantsData', 'Notification',
                        function ($scope, $state, $http, restaurantsData, Notification ) {
                            $scope.restaurants = restaurantsData.content;
                            $scope.topRestaurants = restaurantsData.content;
                            $scope.searchKey = "";
                            $scope.currentPage = 0;
                            $scope.pageSize = 10;
                            $scope.pages = new Array(restaurantsData.totalPages);

                            $scope.search = function () {
                                $http.get("/restaurants/search?q="+$scope.searchKey+"&page="+$scope.currentPage+"&size="+$scope.pageSize)
                                    .then(function (resp) {
                                        if (!resp.data.errors) {
                                            //console.log(resp);
                                            $scope.restaurants = resp.data.content;
                                            $scope.pages = new Array(resp.data.totalPages);
                                        } else {
                                            //console.log(resp);
                                            Notification.error('Sorry, It seem like there are some error. Try again !');
                                        }
                                    })
                            };

                            $scope.gotoPage = function (p) {
                                $scope.currentPage = p;
                                $scope.search();
                            };

                            $scope.gotoPreviousPage = function () {
                                $scope.currentPage--;
                                $scope.search();
                            };

                            $scope.gotoNextPage = function () {
                                $scope.currentPage++;
                                $scope.search();
                            };
                        }
                    ]
                });

                $stateProvider.state("restaurants.list", {
                    url: "",
                    templateUrl: "/views/restaurants.list.html"
                });

                $stateProvider.state("restaurants.detail", {
                    url: "/{restoId:[0-9]{1,4}}",
                    views: {
                        '': {
                            templateUrl: "/views/restaurants.detail.html",
                            controller: ['$scope', '$stateParams', '$http', '$state', 'Notification',
                                function ($scope, $stateParams, $http, $state, Notification) {

                                    $scope.myGrade = null;

                                    $http.get("/restaurants/"+$stateParams.restoId+"/details")
                                        .then(function (resp) {
                                            if (!resp.data.errors) {
                                                //console.log(resp);
                                                $scope.restaurant = resp.data;
                                            } else {
                                                //console.log(resp);
                                                $state.go('error404', {}, { reload: true });
                                                Notification.error('Error: '+resp.data.message);
                                            }
                                        });

                                    if ($scope.$storage.user != null && $scope.$storage.authenticated) {
                                        $http.get("/restaurants/"+$stateParams.restoId+"/grades/"+$scope.$storage.user.id)
                                            .then(function (resp) {
                                                if (!resp.data.errors) {
                                                    //console.log(resp);
                                                    $scope.myGrade = resp.data;
                                                } else {
                                                    //console.log(resp);
                                                    $scope.myGrade = null;
                                                }
                                            });
                                    }

                                    $scope.graduate = function (grade) {
                                        grade.restoId = $scope.restaurant.id;
                                        grade.clientId = $scope.$storage.user.id;
                                        //console.log(grade);
                                        $http.post("/restaurants/"+$stateParams.restoId+"/grades/"+$scope.$storage.user.id, grade)
                                            .then(function (resp) {
                                                if (!resp.data.errors) {
                                                    //console.log(resp);
                                                    $scope.myGrade = resp.data;
                                                    Notification.success('Thanks ! Your rating have been successfull register.');
                                                } else {
                                                    //console.log(resp);
                                                    $scope.myGrade = null;
                                                    Notification.error('Error: '+resp.data.message);
                                                }
                                            })
                                    };
                                }
                            ]
                        }
                    }
                });

                $stateProvider.state("error404", {
                    url: "/error404",
                    templateUrl: "/views/error.html"
                });

                $urlRouterProvider.otherwise("/");
            }
        ]
    )

    .config(['$qProvider', function ($qProvider) {
        $qProvider.errorOnUnhandledRejections(false);
    }])

    .config(function(NotificationProvider) {
        NotificationProvider.setOptions({
            delay: 5000,
            startTop: 20,
            startRight: 10,
            verticalSpacing: 20,
            horizontalSpacing: 20,
            positionX: 'right',
            positionY: 'top'
        });
    })

    .config(function($logProvider){
        $logProvider.debugEnabled(false);
    })

    .service('RestaurantService', function($http) {
        return {
            getAllRestaurants: function () {
                return $http.get("/restaurants")
                    .then(function (resp) {
                        console.log("Getting All Resto Data");
                        return resp.data;
                    })
            }
        };
    });