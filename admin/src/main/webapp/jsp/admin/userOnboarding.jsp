<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.drop-down{
    position:relative;
}
</style>
<link href="../../assets/css/jquery-ui.css" rel="stylesheet">
<link href="../../assets/css/bootstrap.min2.css" rel="stylesheet">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/css/bootstrap-select.min.css" />

<script>
	function jsonconstruct(val) {
		var data = {};
		document.getElementById('button_type').value = val;
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
				+ JSON.stringify(data) + '}}';
		document.getElementById('x').value = x;
		//alert(x);
		//console.log(x);
		document.getElementById('UserOnboarding').submit();
	}

	function funccheck(val) {
		if (val == 'create') {
			window.location.reload();
		} 
		else {
			document.getElementById('usr').style.display = "block";
		}
	}

	$(document).ready(function() {
		$("#useridnm").change(function() {
			var user_val = document.getElementById("useridnm").value;
			$.post('${pageContext.request.contextPath}/admin/UserEdit', {
				user_val : user_val
			}, function(data) {
				$('#us').html(data)
			});
		});


	$("#username").change(function() {
	      var psid = $(this).val();
	      if(psid!='') {
	        $.ajax({ 
	          type: "POST",
	          url: "${pageContext.request.contextPath}/admin/PSIDValidation",
	          data: {psid: psid},
	          cache: false,
	          success: function(html) {
	        	  $("#validate").html(html);
	            if(document.getElementById("psidvalidation").value=='1'){
		            $("#save").hide();
		            }else{
	            	 $("#save").show();
		            }
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
						<h4 class="card-title">User Onboarding</h4>
						<div class="mt-3" align="center">

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
						</div>
						<form class="forms-sample" id="UserOnboarding"
							name="UserOnboarding" method="POST"
							action="${pageContext.request.contextPath}/admin/userOnboarding1" enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> 
							<input type="hidden" name="button_type" id="button_type" value="">
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">User</label>
								<div class="col-sm-4">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="radio" id="radio1"
											checked="checked" value="create"
											onclick="funccheck(this.value)"> Create
										</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="radio" id="radio2"
											value="edit" onclick="funccheck(this.value)"> Edit/View
										</label>
									</div>
								</div>
							</div>
							<div class="form-group" id="usr" style="display: none;">
								<label>Select PSID</label> 
								<select name="useridnm"
									id=useridnm class="form-control selectpicker" data-live-search="true">
									<option value="" selected disabled>Select PSID ...</option>
									<c:forEach items="${user_val}" var="user_val">
										<option value="${user_val}">${user_val}</option>
									</c:forEach>
								</select>
							</div>	
							<div id="us">		
							<fieldset class="fs">
									<div class="form-group row">
										<div class="col-sm-6">
											<label>PSID *</label> <input type="text"
												class="form-control" id="username" name="username"
												placeholder="Username" autofocus>
												<div id="validate"
											style="font-size: 0.7em; text-align: center;"></div>
										</div>
										<div class="col-sm-6">
											<label>Domain *</label> <input type="text"
												class="form-control" id="domain" name="domain"
												placeholder="HBEU/HRES/HBAP">
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label>Name *</label> <input type="text"
												class="form-control" id="name" name="name"
												placeholder="Name">
										</div>
										<div class="col-sm-6">
											<label>Email Id *</label> <input type="text"
												class="form-control" id="email" name="email"
												placeholder="Email Id">
										</div>
									</div>
							</fieldset>
							<button onclick="jsonconstruct('add');" id="save" name="save"
								class="btn btn-rounded btn-gradient-info mr-2">Save</button>
							</div>	
							
						</form>
						</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />
		 <script src="../../assets/js/bootstrap.min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/js/bootstrap-select.min.js"></script>