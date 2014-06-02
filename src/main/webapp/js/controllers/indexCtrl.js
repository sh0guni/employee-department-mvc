controllers.controller('IndexCtrl', ['$scope', '$rootScope', '$modal', 'Department', 'Employee',
        function ($scope, $rootScope, $modal, Department, Employee) {
            $rootScope.title = 'Department app';
            $scope.alerts = [];
            $scope.departments = Department.query();
            $scope.totalEmployees = Employee.count();

            $scope.getDepartmentEmployees = function(departmentId, departmentIndex) {
                if ($scope.departments[departmentIndex].employees) {
                    return;
                }
                $scope.departments[departmentIndex].employees = Employee.query({departmentId:departmentId});
            }

            $scope.openNewDepartmentDlg = function() {
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_add_department.html',
                    controller: 'AddNewDepartmentCtrl',
                    scope: $scope
                });

                modalInstance.result.then(
                    function(newDepartment) {
                        $scope.departments = Department.query();
                    },
                    function() {
                    }
                );
            }

            $scope.openEditDepartmentDlg = function(department) {
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_edit_department.html',
                    controller: 'EditDepartmentCtrl',
                    scope: $scope,
                    resolve: {
                        originalDepartment: function() {
                            return department;
                        }
                    }
                });

                modalInstance.result.then(
                    function(editedDepartment) {
                        angular.copy(editedDepartment, department);
                    },
                    function() {
                    }
                );
            }

            $scope.openNewEmployeeDlg = function(department) {
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_add_employee.html',
                    controller: 'AddNewEmployeeCtrl',
                    scope: $scope,
                    resolve: {
                        departmentId: function() {
                            return department.id;
                        }
                    }
                });

                modalInstance.result.then(
                    function() {
                        department.employees = Employee.query({departmentId:department.id});
                        $scope.totalEmployees = Employee.count();
                        department.employeeCount++;
                    },
                    function() {
                    }
                );
            }

            $scope.openEditEmployeeDlg = function(employee, department) {
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_edit_employee.html',
                    controller: 'EditEmployeeCtrl',
                    scope: $scope,
                    resolve: {
                        originalEmployee: function() {
                            return employee;
                        },
                        originalDepartment: function() {
                            return department;
                        }
                    }
                });

                modalInstance.result.then(
                    function(editedEmployee) {
                        if (editedEmployee.departmentId != employee.departmentId) {
                            $scope.departments = Department.query();

                        } else {
                            angular.copy(editedEmployee, employee);
                        }
                    }
                );
            }

            $scope.deleteDepartment = function(department, departmentIndex) {
                if (department.employeeCount > 0) {
                    $scope.alerts.push({
                        type: 'danger',
                        msg: 'Cannot delete a department with employees'
                    });
                    return;
                }
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_dialog.html',
                    controller: 'DialogCtrl',
                    resolve: {
                        header: function () {
                            return 'Please confirm';
                        },
                        bodyText: function () {
                            return 'Delete department ' + department.name + '?';
                        }
                    }
                })

                modalInstance.result.then(
                    function(){
                        Department.remove({departmentId:department.id})
                            .$promise.then(
                            function() {
                                $scope.departments = Department.query();
                            }, function(data) {
                                if (data.status === 403) {
                                    if (department.employeeCount > 0) {
                                        $scope.alerts.push({
                                            type: 'danger',
                                            msg: 'Cannot delete a department with employees'
                                        });
                                    }
                                }
                            });
                    }
                );
            }

            $scope.deleteEmployee = function(employee, department) {
                var modalInstance = $modal.open({
                    templateUrl: '/partials/_dialog.html',
                    controller: 'DialogCtrl',
                    resolve: {
                        header: function () {
                            return 'Please confirm';
                        },
                        bodyText: function () {
                            return 'Delete employee ' + employee.firstName + ' ' + employee.lastName + '?';
                        }
                    }
                })

                modalInstance.result.then(
                    function(){
                        Employee.remove({employeeId:employee.id})
                            .$promise.then(
                            function() {
                                department.employees = Employee.query({departmentId: department.id});
                                department.employeeCount--;
                                $scope.totalEmployees = Employee.count();
                            });
                    }
                );
            }

            $scope.closeAlert = function(index) {
                $scope.alerts.splice(index, 1);
            };
        }]
);
