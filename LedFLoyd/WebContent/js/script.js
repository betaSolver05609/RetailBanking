
//routing
var lf=angular.module('LedFloyd',['ui.router','angularUtils.directives.dirPagination','ngIdle']);
lf.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/home');
	$stateProvider
	.state('login', {
		url: '/login',
		templateUrl: 'html/login.html',
		controller: 'login_controller'
	})
	.state('home', {
		url: '/home',
		templateUrl: 'html/home.html',
		controller: 'homecontroller'
	})
	.state('about', {
		url: '/about',
		templateUrl: 'html/about.html'
	})
	.state('contactUs', {
		url: '/contactUs',
		templateUrl: 'html/ContactUs.html',
		controller: 'contactUsController'
	})
	.state('accountExecutiveHome',{
		url: '/accountExecutiveHome',
		templateUrl: 'html/accountExecutiveHome.html',
		controller: 'accountExecutiveHome'
	})
	.state('cashierHome',{
		url: '/cashierHome',
		templateUrl: 'html/cashierHome.html',
		controller: 'cashierHome'
	})
	.state('cashierHome.deposit',{
		url: '/cashierHome_deposit',
		templateUrl: 'html/CashierHome_deposit.html',
		controller: 'transaction_deposit'
	})
	.state('cashierHome.withdraw',{
		url: '/cashierHome_withdraw',
		templateUrl: 'html/CashierHome_withdraw.html',
		controller: 'transaction_withdraw'
	})
	.state('cashierHome.viewAllTransaction',{
		url: '/cashierHome_viewAllTransaction',
		templateUrl: 'html/CashierHome_viewAllTransaction.html',
		controller: 'transaction_viewAllTransaction'
	})
	.state('cashierHome.viewTransactionByAccountId',{
		url: '/cashierHome_viewTransactionByAccountId',
		templateUrl: 'html/CashierHome_viewTransactionByAccountId.html',
		controller: 'transaction_viewTransactionByAccountId'
	})
	.state('cashierHome.viewTransactionByCustomerId',{
		url: '/cashierHome_viewTransactionByCustomerId',
		templateUrl: 'html/CashierHome_viewTransactionByCustomerId.html',
		controller: 'transaction_viewTransactionByCustomerId'
	})
	.state('accountExecutiveHome.viewAllCustomers',{
		url: '/accountExecutiveHome_viewAllCustomers',
		templateUrl: 'html/Customers_viewAllCustomers.html',
		controller: 'customers_viewAllCustomers'
	})
	.state('accountExecutiveHome.createCustomer',{
		url: '/accountExecutiveHome_createCustomer',
		templateUrl: 'html/Customers_createCustomer.html',
		controller: 'customers_createCustomer'
	})
	.state('accountExecutiveHome.updateCustomer',{
		url: '/accountExecutiveHome_updateCustomer',
		templateUrl: 'html/Customers_updateCustomer.html',
		controller: 'customers_updateCustomer'
	})
	.state('accountExecutiveHome.viewCustomer',{
		url: '/accountExecutiveHome_viewCustomer',
		templateUrl: 'html/Customers_viewCustomer.html',
		controller: 'customers_viewCustomer'
	})
	.state('accountExecutiveHome.deleteCustomer',{
		url: '/accountExecutiveHome_deleteCustomer',
		templateUrl: 'html/Customers_deleteCustomer.html',
		controller: 'customers_deleteCustomer'
	})
	.state('accountExecutiveHome.viewAllAccounts',{
		url: '/accountExecutiveHome_viewAllAccounts',
		templateUrl: 'html/Accounts_viewAllAccounts.html',
		controller: 'accounts_viewAllAccounts'
	})
	.state('accountExecutiveHome.createAccount',{
		url: '/accountExecutiveHome_createAccount',
		templateUrl: 'html/Accounts_createAccount.html',
		controller: 'accounts_createAccount'
	})
	.state('accountExecutiveHome.viewAccount',{
		url: '/accountExecutiveHome_viewAccount',
		templateUrl: 'html/Accounts_viewAccount.html',
		controller: 'accounts_viewAccount'
	})
	.state('accountExecutiveHome.deleteAccount',{
		url: '/accountExecutiveHome_deleteAccount',
		templateUrl: 'html/Accounts_deleteAccount.html',
		controller: 'accounts_deleteAccount'
	})
	.state('accountExecutiveHome.accountAnalytics',{
		url: '/accountExecutiveHome_accountAnalytics',
		templateUrl: 'html/CustomerAnalytics.html',
		controller: 'customerAnalyticsController'
	})
});

//session timeout
lf.config(function(IdleProvider, KeepaliveProvider) {
	IdleProvider.idle(20); // 15 min
	IdleProvider.timeout(10);
	//KeepaliveProvider.interval(30); // heartbeat every 10 min
	//KeepaliveProvider.http('/api/heartbeat'); 
	// URL that makes sure session is alive
});

lf.run(function($rootScope,Idle,$state,$http) {
	Idle.watch();
	
	$rootScope.$on('IdleStart', function() { 
		console.log("here");
		console.log(window.sessionStorage.getItem("username")!=null);
		if(window.sessionStorage.getItem("username")!=null)
		{	
			M.toast({html: 'You will be logged out in 10 secs', classes: 'rounded', inDuration: '400', outDuration: '500'});}
		});
	$rootScope.$on('IdleTimeout', function() {
		if(window.sessionStorage.getItem("username")!=null)
		{
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/login/Invalidate/'+window.sessionStorage.getItem("username"),
		}).then(function(result) {
			
			M.Toast.dismissAll();
			M.toast({html: 'Session Timeout', classes: 'rounded', inDuration: '400', outDuration: '500'});
			$rootScope.displayValue='Login';
			$state.go('login', {}, {reload: true});
			window.sessionStorage.clear();
		})
		}
		});
	
});

//session validation
lf.factory('validateFactory', function($http) {
	 return {
		 validate: function(x) {
			 //console.log("x= "+x);
			 var val = {
					 username : window.sessionStorage.getItem('username'),
					 password : window.sessionStorage.getItem('password')
			 };
			 $http({
					method : 'POST',
					url : 'http://172.26.49.222:8000/api/login/Validate',
					dataType : 'JSON',
					data : JSON.stringify(val),
					contentType : 'application/json',
					mimeType : 'application/json',
				}).then(function(result) {
					if(result.data!='')
					{
						console.log('returning '+(result.data.role == x));
						return (result.data.role == x);
					}
					else 
						return false;
				})
			}
}});


//controllers
lf.controller('homecontroller',function($scope,$http,$rootScope,$state){
	if(window.sessionStorage.getItem('role')!=null)
	{
		$state.go(window.sessionStorage.getItem('role')+'Home');
	}
});

lf.controller('nav_controller',function($scope,$http,$rootScope,$state){
	var uname=window.sessionStorage.getItem('username');
	if(uname!=null)
		$rootScope.displayValue='Logout';
	else
		$rootScope.displayValue='Login';
	$rootScope.$on('LoginEvent',function(event,data){
			$rootScope.displayValue = 'Logout';
	})
	$rootScope.$on('SetNavbar',function(event){
		$rootScope.displayValue = 'Login';
		$rootScope.displayName = window.sessionStorage.getItem('username');
	})
	$scope.log=function(){
		if($rootScope.displayValue != 'Login')
		{
			$http({
				method : 'GET',
				url : 'http://172.26.49.222:8000/api/login/Invalidate/'+window.sessionStorage.getItem('username'),
			}).then(function(result) {
				
				M.Toast.dismissAll();
				M.toast({html: 'Logout Succesfull', classes: 'rounded', inDuration: '400', outDuration: '500'});
				$rootScope.displayValue='Login';
				$state.go('login', {}, {reload: true});
				window.sessionStorage.clear();
			})
		}
		else
		{
			$state.go('login');
		}
	}
})

lf.controller('contactUsController',function($scope,$http,$rootScope,$state){
	$scope.submission = function(){
		M.Toast.dismissAll();
		M.toast({html: 'Submission Succesfull '+result.data, inDuration: 300, outDuration: 300, classes: 'rounded'});
	}
})
lf.controller('login_controller',function(validateFactory,$scope,$http,$rootScope,$state){
	//console.log('In session '+window.sessionStorage.getItem('role'));
	$scope.err="";
	if(window.sessionStorage.getItem('role')!=null)
	{
		//console.log('here');
		$state.go(window.sessionStorage.getItem('role')+'Home');
	}
	$scope.loginSubmission = function(x){
		$http({
			method : 'POST',
			url : 'http://172.26.49.222:8000/api/login/authorizeLogin',
			dataType : 'JSON',
			data : JSON.stringify(x),
			contentType : 'application/json',
			mimeType : 'application/json',
		}).then(function(result) {
			if(result.data!='' && result.data.role!='Already Active')
			{
				
				
				window.sessionStorage.setItem('username',result.data.username);
				window.sessionStorage.setItem('password',result.data.password);
				window.sessionStorage.setItem('role',result.data.role);
				$rootScope.displayName=result.data.username;
				$scope.login = result.data;
				$rootScope.displayValue = 'Logout';
				$rootScope.$emit('LoginEvent',window.sessionStorage.getItem('username'));
				if(window.sessionStorage.getItem('role') == 'accountExecutive')
				{
					$state.go('accountExecutiveHome');
				}
				else if(window.sessionStorage.getItem('role') == 'cashier')
				{
					$state.go('cashierHome');
				}	
			}
			else 
			{
				//$state.go($state.current, {}, {reload: true});
				if(result.data.username==null)
					$scope.err="*The username or password is incorrect";
				else if(result.data.role=='Already Active')
					$scope.err="*Account is already logged in";
			}
		})
	}
})


lf.controller('accountExecutiveHome',function(validateFactory,$scope,$state,$rootScope){
	$rootScope.displayValue=window.sessionStorage.getItem('username');
	var val=validateFactory.validate('accountExecutive');
	if((val==false) || ($rootScope.displayValue == 'Login'))
		$state.go('login');
	
	$scope.customerTime = true;
	$scope.accountsTime = false;
	$scope.switch_a_roo = function()
	{
		$scope.customerTime = !$scope.customerTime;
		$scope.accountsTime = !$scope.accountsTime;
	}
})




lf.controller('customers_viewAllCustomers',function($scope,$http,$rootScope,$state,$window){
	$scope.sortField = 'customerId';
	$scope.reverse = false;
	$http({
		method : 'GET',
		url : 'http://172.26.49.222:8000/api/customer/viewAllCustomers'
	}).then(function(result) {
		$scope.tableData = result.data;
	})
	$scope.deletion = function(value){
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/customer/deleteCustomer/'+value
		}).then(function(result) {
			if(result.data == "true")
			{
				M.toast({html: 'Deletion Success',inDuration: 300, outDuration: 300, classes: 'rounded'})
				$state.go($state.current, {}, {reload: true});
				$scope.displayTable = false;				
			}
			else
			{
				M.toast({html: 'Unable to delete, check with the Accounts department',inDuration: 300, outDuration: 300, classes: 'rounded'})
				$scope.displayTable = false;
			}
			$scope.displayData = false;
		})
	}
})

lf.controller('customers_createCustomer',function($scope,$http,$rootScope,$state){
	$scope.checkssn=function(y){
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/customer/viewBySsnDirty/'+y
		}).then(function(result) {

			console.log(result.data);
			if(result.data.ssn!=null)
				$scope.exists=true;
			else
				$scope.exists=false;
		})
	}
	
	
	
	
	$scope.customer_submit = function(x){
		$http({
			method : 'POST',
			url : 'http://172.26.49.222:8000/api/customer/createCustomer',
			dataType : 'JSON',
			data : JSON.stringify(x),
			contentType : 'application/json',
			mimeType : 'application/json',
		}).then(function(result) {
			var x = result.data;
			M.Toast.dismissAll();
			if(x == "")
			{
				M.toast({html: 'Unable to create, Some problem',inDuration: 300, outDuration: 300, classes: 'rounded'})
			}
			else
			{
				M.toast({html: 'Creation successfull, Customer Id '+x,inDuration: 300, outDuration: 300, classes: 'rounded'});
				$state.go($state.current, {}, {reload: true});
			}
		})
	}
})
lf.controller('customers_updateCustomer',function($scope,$http,$rootScope,$state,$window){
	$scope.x={};
	$scope.parameter = 'customerId';
	$scope.displayData = false;
	$scope.update = function(){
		$http({
			method : 'POST',
			url : 'http://172.26.49.222:8000/api/customer/updateCustomer',
			dataType : 'JSON',
			data : JSON.stringify($scope.x),
			contentType : 'application/json',
			mimeType : 'application/json',
		}).then(function(result) {
			var res = result.data;
			M.Toast.dismissAll();
			if(!res)
			{
				M.toast({html: 'Unable to update, Some problem',inDuration: 300, outDuration: 300, classes: 'rounded'})
			}
			else
			{
				M.toast({html: 'Updation successfull'})
			}
		})
	}
	$scope.updation = function(value,parameter){
		var urlWrite = 'http://172.26.49.222:8000/api/customer/viewBy'+(parameter.substring(0,1).toUpperCase()+parameter.substring(1,parameter.length))+'/'+ value;
		$http({
			method : 'GET',
			url : urlWrite,
		}).then(function(result) {
			M.Toast.dismissAll();
			if(result.data.ssn!=null)
			{
				$scope.tableData = result.data;
				$scope.displayData = true;
				$scope.x=result.data;
				$scope.x.contactNo=parseInt(result.data.contactNo);
			}
			else
			{
				M.toast({html: 'No Records present with the entered details',inDuration: 300, outDuration: 300, classes: 'rounded'})
				$scope.displayData = false;
			}

		})

	}

	$scope.display = function(x)
	{
		if(x == 'customerId')
			return 'Customer Id';
		else 
			return 'SSN';
	}
})

lf.controller('customers_viewCustomer',function($scope,$http,$rootScope,$state,$window){
	$scope.parameter = 'customerId';
	$scope.displayTable = false;
	$scope.customer_submit = function(value,parameter){
		var urlWrite = 'http://172.26.49.222:8000/api/customer/viewBy'+(parameter.substring(0,1).toUpperCase()+parameter.substring(1,parameter.length))+'/'+ value;
		if(urlWrite==(('http://172.26.49.222:8000/api/customer/viewBySsn/'+ value)))
			{
			urlWrite='http://172.26.49.222:8000/api/customer/viewBySsnDirty/'+ value
			}
		$http({
			method : 'GET',
			url : urlWrite,
		}).then(function(result) {
			M.Toast.dismissAll();
			if(result.data!=null)
			{
				if(result.data.length!= 0)
				{
					{
						if(parameter=='ssn' || parameter=='customerId')
						{
							var arr = [];
							arr.push(result.data);
						}
						else
							arr = result.data;
						$scope.tableData = arr;
						$scope.displayTable = true;
					}
				}
				else
				{
					M.toast({html: 'No Records present with the entered details',inDuration: 300, outDuration: 300, classes: 'rounded'})
					$scope.displayTable = false;
				}
			}
			else
			{
				M.toast({html: 'No Records present with the entered details',inDuration: 300, outDuration: 300, classes: 'rounded'})
				$scope.displayTable = false;
			}
		})
	}

	$scope.display = function(x)
	{
		if(x == 'contactNo')
			return 'Contact Number';
		else if(x == 'ssn')
			return 'SSN';
		else
			return x.substring(0,1).toUpperCase()+x.substring(1,x.length);
	}
})

lf.controller('customers_deleteCustomer',function($scope,$http,$rootScope,$state,$window){
	$scope.displayData = false;
	$scope.customer_submit = function(x){
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/customer/viewBySsn/'+x
		}).then(function(result) {

			console.log(result.data);
			if(result.data.ssn!=null)
			{
				$scope.tableData = result.data;
				$scope.displayData = true;
			}
			else
			{
				M.toast({html: 'No Records present with the entered details',inDuration: 300, outDuration: 300, classes: 'rounded'})
				$scope.displayData = false;
			}
		})

	}
	$scope.deletion = function(){
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/customer/deleteCustomer/'+$scope.value
		}).then(function(result) {
			if(result.data == "true")
			{
				M.toast({html: 'Deletion Success',inDuration: 300, outDuration: 300, classes: 'rounded'})
					$state.go($state.current, {}, {reload: true});
				$scope.displayTable = false;				
			}
			else
			{
				M.toast({html: 'Unable to delete, check with the Accounts department',inDuration: 300, outDuration: 300, classes: 'rounded'})
				$scope.displayTable = false;
			}
			$scope.displayData = false;
		})
	}
	$scope.cancel = function(){
		$scope.displayData = false;
	}
})

lf.controller('accounts_viewAccount',function($scope,$http,$state){
	$scope.parameter = 'customerId';
	$scope.displayTable = false;
	$scope.submit = function(value,parameter){
		var urlWrite = 'http://172.26.49.222:8000/api/account/viewBy'+(parameter.substring(0,1).toUpperCase()+parameter.substring(1,parameter.length))+'/'+ value;
		$http({
			method : 'GET',
			url : urlWrite,
		}).then(function(result) {
			M.Toast.dismissAll();
			console.log(result.data+' '+typeof(result.data)+' '+urlWrite);
			if(parameter == 'customerId' && result.data.length!=0)
				{
					arr = result.data;
					$scope.tableData = arr;
					$scope.displayTable = true;
				}
			else if(parameter == 'accountId' && result.data.accountId!= null)
				{
					var arr = [];
					arr.push(result.data);
					$scope.tableData = arr;
					$scope.displayTable = true;
				}
			else
				{
				M.toast({html: 'Data Not Found',inDuration: 300, outDuration: 300, classes: 'rounded'})
				$scope.displayTable = false;
				}
		})
	}
	$scope.deletion = function(value){
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/account/deleteAccount/'+value
		}).then(function(result) {
			console.log(typeof(result.data))
			if(result.data == "true")
			{
				M.toast({html: 'Deletion Success',inDuration: 300, outDuration: 300, classes: 'rounded'})
					$state.go($state.current, {}, {reload: true});
				$scope.displayTable = false;				
			}
			else
			{
				M.toast({html: 'Unable to delete, Balance is not zero',inDuration: 300, outDuration: 300, classes: 'rounded'})
				$scope.displayTable = false;
			}
			$scope.displayData = false;
		})
	}
	$scope.display = function(x)
	{
		if(x == 'customerId')
			return 'Customer Id';
		else if(x == 'accountId')
			return 'Account Id';
	}
})

lf.controller('accounts_deleteAccount',function($scope,$http){
	$scope.displayData = false;
	$scope.submit = function(x){
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/account/viewByAccountId/'+x
		}).then(function(result) {
			console.log(result.data);
			if(result.data.accountId!=null)
			{
				$scope.tableData = result.data;
				$scope.displayData = true;
			}
			else
			{
				M.toast({html: 'No accounts present with the entered details',inDuration: 300, outDuration: 300, classes: 'rounded'})
				$scope.displayData = false;
			}
		})

	}
	$scope.deletion = function(){
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/account/deleteAccount/'+$scope.value
		}).then(function(result) {
			console.log(typeof(result.data))
			if(result.data == "true")
			{
				M.toast({html: 'Deletion Success',inDuration: 300, outDuration: 300, classes: 'rounded'})
					$state.go($state.current, {}, {reload: true});

				$scope.displayTable = false;				
			}
			else
			{
				M.toast({html: 'Unable to delete, balance is not zero',inDuration: 300, outDuration: 300, classes: 'rounded'})
				
				$scope.displayTable = false;
			}
			$scope.displayData = false;
		})
	}
	$scope.cancel = function(){
		$scope.displayData = false;
	}
})

lf.controller('accounts_viewAllAccounts',function($scope,$http,$state){
	$scope.sortField = 'accountId';
	$scope.reverse = false;
	$http({
		method : 'GET',
		url : 'http://172.26.49.222:8000/api/account/viewAllAccounts'
	}).then(function(result) {
		$scope.tableData = result.data;
	})
	$scope.deletion = function(value){
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/account/deleteAccount/'+value
		}).then(function(result) {
			console.log(typeof(result.data))
			if(result.data == "true")
			{
				M.toast({html: 'Deletion Success',inDuration: 300, outDuration: 300, classes: 'rounded'})
					$state.go($state.current, {}, {reload: true});
				$scope.displayTable = false;				
			}
			else
			{
				M.toast({html: 'Unable to delete, balance is not zero',inDuration: 300, outDuration: 300, classes: 'rounded'})
				$scope.displayTable = false;
			}
			$scope.displayData = false;
		})
	}
})

lf.controller('accounts_createAccount',function($scope,$http){
	$scope.checkId = function(y){
		if((y!=null)||(y!='')){
			
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/customer/checkCustomerByCustomerId/'+y
		}).then(function(result) {

			console.log(result.data);
			$scope.exists=result.data;
		})}
	}
	
	$scope.submit = function(x){
		$http({
			method : 'POST',
			url : 'http://172.26.49.222:8000/api/account/createAccount',
			dataType : 'JSON',
			data : JSON.stringify(x),
			contentType : 'application/json',
			mimeType : 'application/json',
		}).then(function(result) {
			console.log();
			M.Toast.dismissAll();
			if(result.data!="")
				{
					M.toast({html: 'Account Created with Id '+result.data, inDuration: 300, outDuration: 300, classes: 'rounded'})
				}
			else
				{
					M.toast({html: 'Account already present Or internal error',inDuration: 300, outDuration: 300, classes: 'rounded'})
				}
		})
	}
})
lf.controller('cashierHome',function(validateFactory,$scope,$state,$rootScope){

	$rootScope.displayValue=window.sessionStorage.getItem('username');
	console.log($scope.displayName+' USERNAME');
	var val=validateFactory.validate('cashier');
	if((val==false) || ($rootScope.displayValue == 'Login'))
		$state.go('login');
})

lf.controller('transaction_deposit',function($scope,$http,$rootScope,$state,$window){
	$scope.checkId = function(y){
		if((y!=null)||(y!='')){
			
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/account/checkByAccountId/'+y
		}).then(function(result) {

			console.log(result.data);
			$scope.exists=result.data;
		})}
	}
	$scope.submit = function(x){
		$http({
			method : 'POST',
			url : 'http://172.26.49.222:8000/api/transaction/deposit',
			dataType : 'JSON',
			data : JSON.stringify(x),
			contentType : 'application/json',
			mimeType : 'application/json',
		}).then(function(result) {
			console.log();
			M.Toast.dismissAll();
			if(result.data!="")
				{
					M.toast({html: 'Transaction Created with Id '+result.data, inDuration: 300, outDuration: 300, classes: 'rounded'})
				}
			else
				{
					M.toast({html: 'Internal error',inDuration: 300, outDuration: 300, classes: 'rounded'})
				}
		})
	}
})
lf.controller('transaction_withdraw',function($scope,$http,$rootScope,$state,$window){
	$scope.checkId = function(y){
		if((y!=null)||(y!='')){
			
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/account/checkByAccountId/'+y
		}).then(function(result) {

			console.log(result.data);
			$scope.exists=result.data;
		})}
	}
	
	$scope.submit = function(x){
		
		$http({
			method : 'POST',
			url : 'http://172.26.49.222:8000/api/transaction/withdraw',
			dataType : 'JSON',
			data : JSON.stringify(x),
			contentType : 'application/json',
			mimeType : 'application/json',
		}).then(function(result) {
			console.log();
			M.Toast.dismissAll();
			if(result.data!="")
				{
					M.toast({html: 'Transaction Created with Id '+result.data, inDuration: 300, outDuration: 300, classes: 'rounded'})
				}
			else
				{
					M.toast({html: 'Insufficient Balance',inDuration: 300, outDuration: 300, classes: 'rounded'})
				}
		})
	}
})
lf.controller('transaction_viewAllTransaction',function($scope,$http,$rootScope,$state,$window){
	$scope.sortField = 'transactionId';
	$scope.reverse = false;
	
	$scope.startDate = new Date();
	$scope.endDate = new Date();
	
	console.log($scope.startDate);
	console.log($scope.endDate);
	
	$scope.displayData = function(startDate,endDate)
	{
		console.log('http://172.26.49.222:8000/api/transaction/viewAllTransaction/'+startDate+'/'+endDate);
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/transaction/viewAllTransaction/'+startDate+'/'+endDate
		}).then(function(result) {
			if(result.data.length !=0)
				{
					$scope.tableData = result.data;
					$scope.displayTable = true;
				}
			else
				{
					M.Toast.dismissAll();
					$scope.displayTable = false;
					M.toast({html: 'No Transactions were found with these dates '+result.data, inDuration: 300, outDuration: 300, classes: 'rounded'})
				}
		})
	}
})
lf.controller('transaction_viewTransactionByAccountId',function($scope,$http,$rootScope,$state,$window){
	$scope.checkId = function(y){
		if((y!=null)||(y!='')){
			
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/account/checkByAccountId/'+y
		}).then(function(result) {

			console.log(result.data);
			$scope.exists=result.data;
		})}
	}
	$scope.sortField = 'transactionId';
	$scope.reverse = false;
	$scope.displayData = function(accountId,startDate,endDate)
	{
		console.log('http://172.26.49.222:8000/api/transaction/viewByAccountId/'+accountId+'/'+startDate+'/'+endDate);
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/transaction/viewByAccountId/'+accountId+'/'+startDate+'/'+endDate
		}).then(function(result) {
			if(result.data.length !=0)
				{
					$scope.tableData = result.data;
					$scope.displayTable = true;
				}
			else
				{
					M.Toast.dismissAll();
					$scope.displayTable = false;
					M.toast({html: 'No Transactions were found with this account and dates '+result.data, inDuration: 300, outDuration: 300, classes: 'rounded'})
				}
		})
	}
})
lf.controller('transaction_viewTransactionByCustomerId',function($scope,$http,$rootScope,$state,$window){
	$scope.checkId = function(y){
		if((y!=null)||(y!='')){
			
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/customer/checkCustomerByCustomerId/'+y
		}).then(function(result) {

			console.log(result.data);
			$scope.exists=result.data;
		})}
	}
	$scope.sortField = 'transactionId';
	$scope.reverse = false;
	$scope.displayData = function(customerId,startDate,endDate)
	{
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/transaction/viewByCustomerId/'+customerId+'/'+startDate+'/'+endDate
		}).then(function(result) {
			if(result.data.length !=0)
				{
					$scope.tableData = result.data;
					$scope.displayTable = true;
				}
			else
				{
					M.Toast.dismissAll();
					$scope.displayTable = false;
					M.toast({html: 'No Transactions were found with this customer and dates '+result.data, inDuration: 300, outDuration: 300, classes: 'rounded'})
				}
		})
	}
	
});

lf.controller('customerAnalyticsController',function($scope,$http,$rootScope,$state,$window){
	$scope.doughnuts=function()
	{
		$scope.switchy='doughnut';
		var ctx = document.getElementById("doughnut").getContext("2d");
		var dt=new Date();
		var year=dt.getYear()+1900;
		var start_date= year+"-01-01";
		var end_date= year+"-12-31";
		var months=[]
		count=[0,0,0,0,0,0,0,0,0,0,0,0]
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/transaction/viewAllTransaction/'+start_date+'/'+end_date
		}).then(function(result) {
			if(result.data.length !=0)
				{
					$scope.data = result.data;
					
					for(i=0;i<result.data.length;i++)
						{
						months.push(parseInt($scope.data[i].timestamp.slice(5,7)))
						count[parseInt($scope.data[i].timestamp.slice(5,7))-1]+=parseInt($scope.data[i].amount);
						}
//new Chart(document.getElementById("doughnuts"),{"type":"doughnut","data":{"labels":["January","February","March","April","May","June","July"],"datasets":[{"label":"My First Dataset","data":[65,59,80,81,56,55,40],"fill":false,"borderColor":"rgb(75, 192, 192)","lineTension":0.1}]},"options":{responsive: false;}});
					var myChart = new Chart(ctx, {
				        type: 'doughnut',
				        data: {
				            labels: ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"],
				            datasets: [
				                {
				                    label: "Amount Transacted per month",
				                    data: count,
				                    fill: false,
				                    borderColor: 'rgb(75, 192, 192)',
				                    backgroundColor: [
						                'rgba(255, 99, 132, 1)',
						                'rgba(54, 162, 235, 1)',
						                'rgba(255, 206, 86, 1)',
						                'rgba(75, 192, 192, 1)',
						                'rgba(153, 102, 255, 1)',
						                'rgba(255, 159, 64, 1)',
										'rgba(255, 99, 132, 1)',
						                'rgba(54, 162, 235, 1)',
						                'rgba(255, 206, 86, 1)',
						                'rgba(75, 192, 192, 1)',
						                'rgba(153, 102, 255, 1)',
						                'rgba(255, 159, 64, 1)'
						            ],
				                    lineTension: 0.1
				                }
				            ]
				        },
				        options: {
				            responsive: false
				        }
					})
				}
		})
		
		
		
		
	}
	$scope.lineGraph=function(){
	$scope.switchy='line';
	var ctx = document.getElementById("line").getContext("2d");
	var dt=new Date();
	var year=dt.getYear()+1900;
	var start_date= year+"-1-01";
	var end_date= year+"-12-31";
	var months=[]
	count=[0,0,0,0,0,0,0,0,0,0,0,0]
	$http({
		method : 'GET',
		url : 'http://172.26.49.222:8000/api/transaction/viewAllTransaction/'+start_date+'/'+end_date
	}).then(function(result) {
		if(result.data.length !=0)
			{
				$scope.data = result.data;
				
				for(i=0;i<result.data.length;i++)
					{
					months.push(parseInt($scope.data[i].timestamp.slice(5,7)))
					count[parseInt($scope.data[i].timestamp.slice(5,7))-1]++;
					}
				var myChart = new Chart(ctx, {
			        type: 'line',
			        data: {
			            labels: ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"],
			            datasets: [
			                {
			                    label: "Number of transactions happened in current financial year",
			                    data: count,
			                    borderColor: 'rgb(75, 192, 192)',
			                    backgroundColor: 'rgba(255, 99, 132, 0.2)'
			                    
			                }
			            ]
			        },
			        options: {
			            responsive: false
			        }
				})
			}
	})
	}
	$scope.barGraph=function()
	{
		$scope.switchy='bar'
		var ctx = document.getElementById("bar").getContext("2d");
		var dt=new Date();
		var year=dt.getYear()+1900;
		var start_date= year+"-1-01";
		var end_date= year+"-12-31";
		var months=[]
		count=[0,0,0,0,0,0,0,0,0,0,0,0]
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/transaction/viewAllTransaction/'+start_date+'/'+end_date
		}).then(function(result) {
			if(result.data.length !=0)
				{
					$scope.data = result.data;
					
					for(i=0;i<result.data.length;i++)
						{
						months.push(parseInt($scope.data[i].timestamp.slice(5,7)))
						count[parseInt($scope.data[i].timestamp.slice(5,7))-1]++;
						}
					var myChart = new Chart(ctx, {
				        type: 'bar',
				        data: {
				            labels: ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"],
				            datasets: [
				                {
				                    label: "Number of transactions happened in current financial year",
				                    data: count,
				                    borderColor:  [
				                        'rgba(255,99,132,1)',
				                        'rgba(54, 162, 235, 1)',
				                        'rgba(255, 206, 86, 1)',
				                        'rgba(75, 192, 192, 1)',
				                        'rgba(153, 102, 255, 1)',
				                        'rgba(255, 159, 64, 1)',
				        				'rgba(255,99,132,1)',
				                        'rgba(54, 162, 235, 1)',
				                        'rgba(255, 206, 86, 1)',
				                        'rgba(75, 192, 192, 1)',
				                        'rgba(153, 102, 255, 1)',
				                        'rgba(255, 159, 64, 1)'
				                    ],				                  
				                    backgroundColor: [
				                        'rgba(255, 99, 132, 1)',
				                        'rgba(54, 162, 235, 1)',
				                        'rgba(255, 206, 86, 1)',
				                        'rgba(75, 192, 192, 1)',
				                        'rgba(153, 102, 255, 1)',
				                        'rgba(255, 159, 64, 1)',
				        				'rgba(255, 99, 132, 1)',
				                        'rgba(54, 162, 235, 1)',
				                        'rgba(255, 206, 86, 1)',
				                        'rgba(75, 192, 192, 1)',
				                        'rgba(153, 102, 255, 1)',
				                        'rgba(255, 159, 64, 1)'
				                    ]
				                    
				                }
				            ]
				        },
				        options: {
				            responsive: false
				        }
					})
				}
		})

	}
	$scope.polarArea=function()
	{
		$scope.switchy='polarArea'
		var ctx = document.getElementById("polarArea").getContext("2d");
		var dt=new Date();
		var year=dt.getYear()+1900;
		var start_date= year+"-1-01";
		var end_date= year+"-12-31";
		var months=[]
		count=[0,0,0,0,0,0,0,0,0,0,0,0]
		$http({
			method : 'GET',
			url : 'http://172.26.49.222:8000/api/account/viewAllAccounts'
		}).then(function(result) {
			$scope.data = result.data;
			if(result.data.length !=0)
			{
				$scope.data = result.data;
				for(i=0;i<result.data.length;i++)
					{
					months.push(parseInt($scope.data[i].creationDate.slice(5,7)))
					count[parseInt($scope.data[i].creationDate.slice(5,7))-1]++;
					}
				var myChart = new Chart(ctx, {
			        type: 'polarArea',
			        data: {
			            labels: ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"],
			            datasets: [
			                {
			                    label: "Number of accounts opened",
			                    data: count,
			                    borderColor:  [
			                        'rgba(255,99,132,1)',
			                        'rgba(54, 162, 235, 1)',
			                        'rgba(255, 206, 86, 1)',
			                        'rgba(75, 192, 192, 1)',
			                        'rgba(153, 102, 255, 1)',
			                        'rgba(255, 159, 64, 1)',
			        				'rgba(255,99,132,1)',
			                        'rgba(54, 162, 235, 1)',
			                        'rgba(255, 206, 86, 1)',
			                        'rgba(75, 192, 192, 1)',
			                        'rgba(153, 102, 255, 1)',
			                        'rgba(255, 159, 64, 1)'
			                    ],				                  
			                    backgroundColor: [
			                        'rgba(255, 99, 132, 1)',
			                        'rgba(54, 162, 235, 1)',
			                        'rgba(255, 206, 86, 1)',
			                        'rgba(75, 192, 192, 1)',
			                        'rgba(153, 102, 255, 1)',
			                        'rgba(255, 159, 64, 1)',
			        				'rgba(255, 99, 132, 1)',
			                        'rgba(54, 162, 235, 1)',
			                        'rgba(255, 206, 86, 1)',
			                        'rgba(75, 192, 192, 1)',
			                        'rgba(153, 102, 255, 1)',
			                        'rgba(255, 159, 64, 1)'
			                    ]
			                    
			                }
			            ]
			        },
			        options: {
			            responsive: false
			        }
				})
			}
	})

		
		
		
		
		
	}
});
		
    

