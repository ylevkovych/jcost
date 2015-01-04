<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Cost</title>
  <link href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" rel="stylesheet">   
  <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.4/css/jquery.dataTables.css">
  <link href="http://code.jboxcdn.com/0.3.2/jBox.css" rel="stylesheet">  
</head>
<body>	
	<div id="mainTab">
	  	<ul>
	  	  <li><a href="#t1">Orders</a></li>
	  	  <li><a href="#t2">Services</a></li>
	  	  <li><a href="/jcost/resident">Residents</a></li>
	  	  <li><a href="#t4">Goods</a></li>
	  	  <li><a href="/jcost/currency">Currencies</a></li>
	  	</ul>	  
	    <div id="t1">TODO: Order page should be implemented</div>
	    <div id="t2">TODO: Service page should be implemented</div>
	    <div id="t4">TODO: Good page should be implemented</div>
	 </div>

 <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
 <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
 <script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.js"></script>
 <script src="http://code.jboxcdn.com/0.3.2/jBox.min.js"></script>
   
 <script>
 $(document).ready(function() {
	 $("#mainTab").tabs();
 }); 
 </script> 
 
  
</body>
</html>
