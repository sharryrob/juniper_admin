<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.drop-down {
	position: relative;
}
</style>
<link href="../../assets/css/jquery-ui.css" rel="stylesheet">
<link href="../../assets/css/bootstrap.min2.css" rel="stylesheet">
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/css/bootstrap-select.min.css" />

<script type="text/javascript">
	function jsonconstruct() {
		multisel('groupx', 'group');
		var data = {};
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = JSON.stringify(data);
		document.getElementById('x').value = x;
		//var project_admin = document.getElementById('project_admin').checked;
		//alert(project_admin);
		//console.log(x);
		document.getElementById('onboarduser').submit();
		/* document.getElementById('x').value = x;
		//console.log(x);
		//alert(x);
		document.getElementById('onboarduser').submit(); */
	}
	/*
	 $(document).ready(function() {
	 $("#username").keyup(function() {
	 var user = $(this).val();
	 if(user!='') {
	 $.ajax({ 
	 type: "POST",
	 url: "${pageContext.request.contextPath}/admin/selectuser",
	 data: {user: user},
	 cache: false,
	 success: function(data) {
	 $("#featureblock").html(data);

	 }
	 });
	 }
	 return false;
	 });
	 });
	 */
	$(document)
			.ready(
					function() {
						$("#username")
								.change(
										function() {
											var user = $(this).val();
											document.getElementById('featureblock').innerHTML="";
											if (user != '') {
												$
														.ajax({
															type : "POST",
															url : "${pageContext.request.contextPath}/admin/selectuser",
															data : {
																user : user
															},
															cache : false,
															success : function(
																	data) {
																$(
																		"#featureblock")
																		.html(
																				data);
															}
														});
											}
											return false;
										});
					});
</script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">User-Group Link</h4>

						<%
							if (request.getAttribute("successString") != null) {
						%>
						<p class="text-success h4">${successString}</p>
						<%
							}
						%>
						<%
							if (request.getAttribute("errorString") != null) {
						%>
						<p class="text-danger h4">${errorString}</p>
						<%
							}
						%>
						<form class="forms-sample" id="onboarduser" name="onboarduser"
							method="POST"
							action="${pageContext.request.contextPath}/admin/onboarduser"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="group" id="group" class="form-control"
								value=""> <input type="hidden" name="counter"
								id="counter" class="form-control" value="1">

							<fieldset class="fs">
								<div class="form-group">
									<label>Select User</label> <select name="username"
										id="username" class="form-control selectpicker"
										data-live-search="true">
										<option value="" selected disabled>Select User ...</option>
										<c:forEach items="${user_val}" var="user_val">
											<option value="${user_val}">${user_val}</option>
										</c:forEach>
									</select>
								</div>
								<!-- <div class="form-group row">
									  	<div class="col-sm-6">
											<label>Select User</label>
											<input type="text"
											class="form-control" id="username"
											name="username" placeholder="USER ID">			
										</div>
										
								</div>		-->
								<div id="featureblock" class="form-group row"></div>

							</fieldset>
							<button onclick="jsonconstruct();"
								class="btn btn-rounded btn-gradient-info mr-2">Submit</button>
						</form>
					</div>
				</div>
			</div>
		</div>

		<jsp:include page="../cdg_footer.jsp" />
		<script src="../../assets/js/bootstrap.min.js"></script>
		<script
			src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/js/bootstrap-select.min.js"></script>