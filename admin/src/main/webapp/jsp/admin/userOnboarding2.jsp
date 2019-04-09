							<fieldset class="fs">
									<div class="form-group row">
										<div class="col-sm-6">
											<label>PSID *</label> <input type="text"
												class="form-control" id="username" name="username"
												value="${username}" readonly="readonly">
										</div>
										<div class="col-sm-6">
											<label>Domain *</label> <input type="text"
												class="form-control" id="domain" name="domain"
												value="${domain}">
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label>Name *</label> <input type="text"
												class="form-control" id="name" name="name"
												value="${name}">
										</div>
										<div class="col-sm-6">
											<label>Email Id *</label> <input type="text"
												class="form-control" id="email" name="email"
												value="${email}">
										</div>
									</div>
									<button onclick="jsonconstruct('upd');" id="save" name="save"
								class="btn btn-rounded btn-gradient-info mr-2">Save</button>
							</fieldset>