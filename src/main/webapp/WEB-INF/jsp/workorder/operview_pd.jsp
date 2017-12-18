<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <c:if test="${rebootSoft != null}">
				<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">服务类型</th>
										<th class="center">操作类型</th>
										<th class="center">虚拟机</th>
										<th class="center">中间件</th>
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
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								
								</tbody>
						</table>
	</c:if>