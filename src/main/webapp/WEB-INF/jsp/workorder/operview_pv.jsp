<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
 <c:if test="${not empty rebootSoft}">
				<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">服务类型</th>
										<th class="center">操作类型</th>
										<th class="center">虚拟机</th>
										<th class="center">中间件</th>
										<c:if test="${userRole != null && userRole == 'execute'}">
											<th class="center">操作</th>
										</c:if>
									</tr>
								</thead>

								<tbody>
										<c:choose>
										<c:when test="${not empty rebootSoft}">
											<c:forEach items="${rebootSoft}" var="var" varStatus="vs">
													<tr>
														<td class='center'>${opServe.serviceTypeName}</td>
														<td class='center'>${opServe.operTypeName}</td>
														<td class='center'>${var.virtualmachineName}</td>
														<td class='center'>${var.softName}</td>
														<c:if test="${userRole != null && userRole == 'execute'}">
														<td class='center' rowspan='${fn:length(rebootSoft) }'>
															<c:if test="${vs.index == 0}">
																<input type="hidden" name="executeStatus" id="executeStatus" value="${workorder.executeStatus}">
																<button id="executeStatus_0"  style="float:left;margin-left: 100px; display: none;" type="button" 
																	onclick="showRebootSoft('${rebootSoftIds}');">执行</button>
																<button id="executeStatus_1"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
																	>执行中...</button>
																<button id="executeStatus_2"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
																	>执行完毕</button>
																<button id="executeStatus_3"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
																	>执行异常</button>
															</c:if>
														</td>
														</c:if>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								</tbody>
						</table>
	</c:if>
	<c:if test="${not empty installSoftList}">
				<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">服务类型</th>
										<th class="center">操作类型</th>
										<th class="center">虚拟机</th>
										<th class="center">中间件</th>
										<c:if test="${userRole != null && userRole == 'execute'}">
											<th class="center">操作</th>
										</c:if>
									</tr>
								</thead>

								<tbody>
									
													
										<c:choose>
										<c:when test="${not empty installSoftList}">
											<c:forEach items="${installSoftList}" var="var" varStatus="vs">
													<tr>
														<td class='center'>${opServe.serviceTypeName}</td>
														<td class='center'>${opServe.operTypeName}</td>
														<td class='center'>${var.virtualmachineName}</td>
														<td class='center'>${var.softName}</td>
														<c:if test="${userRole != null && userRole == 'execute'}">
															<td class='center' rowspan='${fn:length(installSoftList) }'>
															<c:if test="${vs.index == 0}">
																<input type="hidden" name="executeStatus" id="executeStatus" value="${workorder.executeStatus}">
																<button id="executeStatus_0"  style="float:left;margin-left: 100px; display: none;" type="button" 
																	onclick="showInstallSoft('${installSoftIds}');">部署</button>
																<button id="executeStatus_1"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
																	>部署中...</button>
																<button id="executeStatus_2"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
																	>部署完毕</button>
																<button id="executeStatus_3"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
																	>部署异常</button>
															</c:if>
														</td>
														</c:if>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								
								</tbody>
						</table>
	</c:if>
	<c:if test="${serviceType!=null && serviceType == '3'}">
				<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">服务类型</th>
										<th class="center">操作类型</th>
										<th class="center">虚拟机</th>
										<th class="center">故障时间</th>
										<th class="center">故障级别</th>
										<th class="center">期望解决时间时间</th>
										<c:if test="${userRole != null && userRole == 'execute'}">
											<th class="center">详情</th>
										</c:if>
									</tr>
								</thead>

								<tbody>
									
													
										<c:choose>
										<c:when test="${not empty vmList}">
											<c:forEach items="${vmList}" var="var" varStatus="vs">
													<tr>
														<td class='center'>${opServe.serviceTypeName}</td>
														<td class='center'>${opServe.operTypeName}</td>
														<td class='center'>${var.name}</td>
														<td class='center'>${opServe.breakdownTime}</td>
														<td class='center'>${opServe.breakdownLevelName}</td>
														<td class='center'>${opServe.exceptSolveTime}</td>
														<c:if test="${userRole != null && userRole == 'execute'}">
															<td class='center'><input type="button" data-toggle="modal" data-target="#breakdown_info_modal" value="故障详情"></td>
														</c:if>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								
								</tbody>
						</table>
	</c:if>
	
	<c:if test="${serviceType!=null && serviceType == '4'}">
				<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">服务类型</th>
										<th class="center">操作类型</th>
										<th class="center">虚拟机</th>
										<c:if test="${userRole != null && userRole == 'execute'}">
											<th class="center">分区详情</th>
										</c:if>
									</tr>
								</thead>

								<tbody>
										<c:choose>
										<c:when test="${not empty vmList}">
											<c:forEach items="${vmList}" var="var" varStatus="vs">
													<tr>
														<td class='center'>${opServe.serviceTypeName}</td>
														<td class='center'>${opServe.operTypeName}</td>
														<td class='center'>${var.name}</td>
														<c:if test="${userRole != null && userRole == 'execute'}">
															<td class='center'><input type="button" data-toggle="modal" data-target="#partition_info_modal" value="分区详情"></td>
														</c:if>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								</tbody>
						</table>
	</c:if>
	
	
	<c:if test="${serviceType!=null && serviceType == '5'}">
				<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">服务类型</th>
										<th class="center">操作类型</th>
										<th class="center">虚拟机</th>
									</tr>
								</thead>

								<tbody>
										<c:choose>
										<c:when test="${not empty vmList}">
											<c:forEach items="${vmList}" var="var" varStatus="vs">
													<tr>
														<td class='center'>${opServe.serviceTypeName}</td>
														<td class='center'>${opServe.operTypeName}</td>
														<td class='center'>${var.name}</td>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								</tbody>
						</table>
	</c:if>
	
	<c:if test="${serviceType!=null && serviceType == '6'}">
				<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">服务类型</th>
										<th class="center">操作类型</th>
										<th class="center">虚拟机</th>
										<th class="center">指定目录</th>
										<c:if test="${userRole != null && userRole == 'execute'}">
											<th class="center">操作</th>
										</c:if>
									</tr>
								</thead>

								<tbody>
										<c:choose>
										<c:when test="${not empty vmList}">
											<c:forEach items="${vmList}" var="var" varStatus="vs">
													<tr>
														<td class='center'>${opServe.serviceTypeName}</td>
														<td class='center'>${opServe.operTypeName}</td>
														<td class='center'>${var.name}</td>
														<td class='center'>${opServe.directory}</td>
														<c:if test="${userRole != null && userRole == 'execute'}">
															<td class='center' rowspan='${fn:length(installSoftList) }'>
															<c:if test="${vs.index == 0}">
																<input type="hidden" name="executeStatus" id="executeStatus" value="${workorder.executeStatus}">
																<button id="executeStatus_0"  style="float:left;margin-left: 100px; display: none;" type="button" 
																	onclick="showMountDisk();">部署</button>
																<button id="executeStatus_1"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
																	>部署中...</button>
																<button id="executeStatus_2"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
																	>部署完毕</button>
																<button id="executeStatus_3"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
																	>部署异常</button>
															</c:if>
														</td>
														</c:if>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								</tbody>
						</table>
	</c:if>
	
	<c:if test="${serviceType!=null && serviceType == '7'}">
				<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">服务类型</th>
										<th class="center">操作类型</th>
										<th class="center">虚拟机</th>
										<th class="center">指定目录</th>
										<th class="center">使用期限</th>
										<th class="center">ROOT密码</th>
									</tr>
								</thead>

								<tbody>
										<c:choose>
										<c:when test="${not empty vmList}">
											<c:forEach items="${vmList}" var="var" varStatus="vs">
													<tr>
														<td class='center'>${opServe.serviceTypeName}</td>
														<td class='center'>${opServe.operTypeName}</td>
														<td class='center'>${var.name}</td>
														<td class='center'>${opServe.directory}</td>
														<td class='center'>${opServe.expTime}</td>
														<td class='center'>${opServe.remark1}</td>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								</tbody>
						</table>
	</c:if>
	<c:if test="${serviceType!=null && serviceType == '8'}">
				<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">服务类型</th>
										<th class="center">操作类型</th>
										<th class="center">虚拟机</th>
										<th class="center">指定目录</th>
										<th class="center">VIP数量</th>
									</tr>
								</thead>

								<tbody>
										<c:choose>
										<c:when test="${not empty vmList}">
											<c:forEach items="${vmList}" var="var" varStatus="vs">
													<tr>
														<td class='center'>${opServe.serviceTypeName}</td>
														<td class='center'>${opServe.operTypeName}</td>
														<td class='center'>${var.name}</td>
														<td class='center'>${opServe.directory}</td>
														<td class='center'>${opServe.vipNum}个</td>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								</tbody>
						</table>
	</c:if>
	