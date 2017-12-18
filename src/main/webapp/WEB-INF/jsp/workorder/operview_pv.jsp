<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
															<td class='center'><input type="button" value="操作"></td>
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
															<td class='center'><input type="button" value="操作"></td>
														</c:if>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								
								</tbody>
						</table>
	</c:if>
	<c:if test="${service_type!=null && service_type == '3'}">
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
														<td class='center'>${var.virtualmachineName}</td>
														<td class='center'>${opServe.breakdownTime}</td>
														<td class='center'>${opServe.breakdownLevel}</td>
														<td class='center'>${opServe.exceptSolveTime}</td>
														<c:if test="${userRole != null && userRole == 'execute'}">
															<td class='center'><input type="button" value="操作"></td>
														</c:if>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								
								</tbody>
						</table>
	</c:if>
	