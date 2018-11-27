<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>


<div style="text-align: center; width: 35%">
  <fieldset>
    <legend>Excel Data</legend>
    
    <form action="./uploadFile" method="post" enctype="multipart/form-data">
        <input type="file" name="file">
        <input type="submit" value="Upload File">
    </form>
   ${msg}
    <br><br>
    <form>
   Select Sheet number :- <select name="sheetNum" size="1" multiple>
  <option value="1">Sheet 1</option>
  <option value="2">Sheet 2</option>
  <option value="3">Sheet 3</option>
  <option value="3">Sheet 3</option>
</select><br><br>

<button type="submit" formaction="./uploadData" formmethod="post">Upload Data</button>
<br><br>
<button type="submit" formaction="./viewData" formmethod="get">View Data</button>
</form>
  </fieldset>
</div>
</body>
</html>