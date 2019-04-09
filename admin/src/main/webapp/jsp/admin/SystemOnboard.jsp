<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../cdg_header.jsp" />
<script>

function jsonconstruct() {
	var data = {};
	$(".form-control").serializeArray().map(function(x) {
		data[x.name] = x.value;
	});
	var x = '{"header":{"user":"http://clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
			+ JSON.stringify(data) + '}}';
	document.getElementById('x').value = x;
	document.getElementById('SystemOnboard').submit();
}

$(document).ready(function() {
	$("#system_region").change(function() {
	      var region = $(this).val();
	      if(region!='') {
	        $.ajax({ 
	          type: "POST",
	          url: "${pageContext.request.contextPath}/admin/FetchCountriesForRegion",
	          data: {region: region},
	          cache: false,
	          success: function(html) {
		            $("#co").html(html).show();
		          }
	        });
	      }
	      return false;
	});


	$("#system_eim").change(function() {
	      var eim = $(this).val();
	      if(eim!='') {
	        $.ajax({ 
	          type: "POST",
	          url: "${pageContext.request.contextPath}/admin/EIMValidation",
	          data: {eim: eim},
	          cache: false,
	          success: function(html) {
	        	  $("#validate").html(html);
	            if(document.getElementById("eimvalidation").value=='1'){
		            $("#systemregister").hide();
		            }else{
	            	 $("#systemregister").show();
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
						<h4 class="card-title">System Onboarding</h4>
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
						<form class="forms-sample" id="SystemOnboard" name="SystemOnboard"
							method="POST" action="${pageContext.request.contextPath}/admin/register"
							enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> <input
								type="hidden" name="src_val" id="src_val" value="${src_val}">
							<fieldset class="fs">
								<div class="form-group row">
									<div class="col-sm-6">
										<label>System EIM *</label> <input type="text"
											class="form-control" id="system_eim" name="system_eim"
											placeholder="System EIM" required="required" autofocus>
										<div id="validate"
											style="font-size: 0.7em; text-align: center;"></div>

									</div>

									<div class="col-sm-6">
										<label>System Name *</label> <input type="text"
											class="form-control" id="system_name" name="system_name"
											placeholder="Source Name" required="required">
									</div>
								</div>

								<div class="form-group row">
									<div class="col-sm-4">
										<label>System Region *</label> <select class="form-control"
											id="system_region" name="system_region"
											placeholder="Source Region" required="required">
											<option value="">Region ...</option>
											<option value="Africa">AFRICA</option>
											<option value="Europe">EUROPE</option>
											<option value="Asia">ASIA</option>
											<option value="Africa">AFRICA</option>
											<option value="Americas">AMERICAS</option>
											<option value="Oceania">OCEANIA</option>
											
										</select>
									</div>
									<div class="col-sm-4" id="co">
										<label>Country *</label> <select class="form-control"
											id="system_country" name="system_country" placeholder="Country" required="required">
											<option value="" selected disabled>Country ...</option>
										</select>
									</div>
								

								
									<div class="col-sm-4">
										<label>Environment Type *</label> <select name="environment_type"
											id="environment_type" class="form-control">
											<option value="" selected disabled>Environment Type ...</option>
											<option value="DEV" selected="selected">Development</option>
											<option value="UAT">UAT</option>
											<option value="prod">Production</option>
										</select>
									</div>
								</div>

							</fieldset>
							<button id="systemregister" onclick="jsonconstruct();"
								class="btn btn-rounded btn-gradient-info mr-2">Submit</button>
						</form>
					</div>
				</div>
			</div>
		</div>
<jsp:include page="../cdg_footer.jsp" />