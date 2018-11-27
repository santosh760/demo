<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Excel Data</title>
</head>
<body>
<div align="center">

<div class = "col-md-12" style = "overflow-x: auto">  




	
	
	
	<table border="1"  cellpadding="5">
	
	
                            <tr style="color: red;">
                                <th>SR</th>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Designation</th>
                                <th>Department</th>
                                <th>Salary</th> 
                                <th>Salary1</th>                                
                            </tr>
                            <%
                           int i=0;
                    %>
                    <c:forEach items="${employeeList}" var="emp">
                    	<tr>
                                    <td><%=++i%></td>
                                    <td><c:out value="${emp.eId}"></c:out></td>
                                    <td><c:out value="${emp.eName}"></c:out></td>
                                   <td> <c:out value="${emp.designation}"></c:out></td>
                                  <td> <c:out value="${emp.dept}"></c:out></td>
                                  <td> <c:out value="${emp.salary}"></c:out></td>
                                </tr>
                    </c:forEach>
                        
                 </form>
			</td>
			</tr>
			<%-- </c:forEach> --%>
			</table>
	
	
	
	
	</div><br><br>
	<a href="index.jsp">Back</a>
	</div>
</body>
</html>