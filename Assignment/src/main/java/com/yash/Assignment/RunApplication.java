package com.yash.Assignment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@EnableTransactionManagement
@Transactional
public class RunApplication {

	private String fullFilePath;
	private String fileName;
	private List<String> sheetHeaderList;
	private int sheetNumber;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private ServletContext context;

	@RequestMapping(value = "/home")
	public ModelAndView showHomePage() {
		ModelAndView model = new ModelAndView("home");
		return model;
	}

	public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
		File convFile = new File(multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFile(@ModelAttribute FileModel file, Model model) throws IOException {

		System.out.println("file : " + file);
		File file2 = multipartToFile(file.getFile());

		System.out.println("canonical path : " + file2.getCanonicalPath());
		MultipartFile multipartFile = file.getFile();

		fileName = multipartFile.getOriginalFilename();

		System.out.println("file name : " + fileName);

		fullFilePath = FileSystems.getDefault().getPath(fileName).toAbsolutePath().toString();

		System.out.println("using path : " + fullFilePath);
		return "home";

	}

	/*
	 * @RequestMapping(value = "/uploadData", method = RequestMethod.POST) public
	 * ModelAndView uploadData(HttpServletRequest request, HttpServletResponse
	 * response) throws IOException {
	 * 
	 * ModelAndView model = new ModelAndView("uploaded");
	 * 
	 * Session session = hibernateTemplate.getSessionFactory().openSession();
	 * 
	 * String num = request.getParameter("sheetNum");
	 * 
	 * ClassLoader classLoader = getClass().getClassLoader(); File file = new
	 * File(fullFilePath);
	 * 
	 * FileInputStream file1 = new FileInputStream(file);
	 * 
	 * XSSFWorkbook workBook = new XSSFWorkbook(file1); XSSFSheet sheet =
	 * workBook.getSheetAt(Integer.parseInt(num) - 1); Row row; Cell cell; String
	 * firstCell; String[] str; StringBuffer sb = new StringBuffer(); String
	 * tableName = fileName.substring(0, fileName.indexOf("."));
	 * System.out.println("table name : " + tableName); row = sheet.getRow(0); for
	 * (int i = 0; i < row.getLastCellNum(); i++) { if (i == 0) { cell =
	 * row.getCell(i); System.out.println("cell value : " + row.getCell(i));
	 * System.out.println("cell value : " + cell.getStringCellValue()); if (cell ==
	 * null) { break; } else { sb.append(row.getCell(i) + " "); String sql =
	 * "CREATE TABLE " + tableName + "data(" +
	 * cell.getStringCellValue().replaceAll(" ", "_") + " VARCHAR(100));"; SQLQuery
	 * query =
	 * hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql)
	 * ; query.executeUpdate();
	 * 
	 * } } else { cell = row.getCell(i); sb.append(row.getCell(i) + " "); if (cell
	 * == null) { break; } else { String sql = "ALTER TABLE " + tableName +
	 * "data ADD " + cell.getStringCellValue().replaceAll(" ", "_") +
	 * " VARCHAR(100);"; SQLQuery query =
	 * hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql)
	 * ; query.executeUpdate(); } } } String convertedString = sb.toString(); str =
	 * convertedString.split(" "); int columnHeaderNum = 0;
	 * 
	 * for(int i=1; i<=sheet.getLastRowNum();i++) { row = sheet.getRow(i); for(int j
	 * = 0;j<row.getLastCellNum();j++) { Cell nextcell = row.getCell(j); if(j == 0)
	 * { String sql =
	 * "INSERT INTO "+fileName+"data("+str[columnHeaderNum]+") values("+nextcell.
	 * getStringCellValue()+")"; }
	 * 
	 * else { String sql = "UPDATE "+fileName+"data SET"; } } }
	 * 
	 * file1.close(); return model; }
	 */

	@RequestMapping(value = "/uploadData", method = RequestMethod.POST)
	public ModelAndView uploadData(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ModelAndView model = new ModelAndView("uploaded");

		String num = request.getParameter("sheetNum");
		sheetNumber = Integer.parseInt(num) - 1;

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(fullFilePath);

		FileInputStream file1 = new FileInputStream(file);

		XSSFWorkbook workBook = new XSSFWorkbook(file1);
		XSSFSheet sheet = workBook.getSheetAt(sheetNumber);

		String tableName = fileName.substring(0, fileName.indexOf(".")).replaceAll(" ", "_");
		Row row = sheet.getRow(0);

		/*
		 * for (int i = 0; i < row.getLastCellNum(); i++) { if (i == 0) { cell =
		 * row.getCell(i); System.out.println("cell value : " + row.getCell(i));
		 * System.out.println("cell value : " + cell.getStringCellValue().trim());
		 * sb.append(row.getCell(i) + " "); String sql = "CREATE TABLE " + tableName +
		 * sheetNumber + "data(" + cell.getStringCellValue().replaceAll(" ", "_") +
		 * " VARCHAR(1000));"; SQLQuery query =
		 * hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql)
		 * ; query.executeUpdate();
		 * 
		 * } else { cell = row.getCell(i); sb.append(row.getCell(i) + " "); String sql =
		 * "ALTER TABLE " + tableName + sheetNumber + "data ADD " +
		 * cell.getStringCellValue().replaceAll(" ", "_") + " VARCHAR(1000);"; SQLQuery
		 * query =
		 * hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql)
		 * ; query.executeUpdate(); }
		 * 
		 * }
		 */
		// StringBuffer createTableQueryBuffer = new StringBuffer();

		if (row != null) {
			createTable(tableName, sheetNumber, row);

			insertRowValuesIntoTable(sheet, tableName, sheetNumber);
		}
		return model;
	}

	public void createTable(String tableName, int sheetNumber, Row row) {
		sheetHeaderList = new ArrayList<String>();
		StringBuilder createTableStatement = new StringBuilder("Create table " + tableName + sheetNumber + "(");
		for (int i = 0; i < row.getLastCellNum(); i++) {
			sheetHeaderList.add(row.getCell(i).toString().replaceAll(" ", "_"));
			if (i != row.getLastCellNum() - 1)
				createTableStatement.append(row.getCell(i).toString().replaceAll(" ", "_") + " varchar(1000),");
			else
				createTableStatement.append(row.getCell(i).toString().replaceAll(" ", "_") + " varchar(1000));");
			System.out.println(createTableStatement);
		}
		System.out.print("header names : ");
		for (String cellName : sheetHeaderList) {
			System.out.print(cellName + ",");

		}
		System.out.println();
		SQLQuery createTablequery = hibernateTemplate.getSessionFactory().getCurrentSession()
				.createSQLQuery(createTableStatement.toString());
		createTablequery.executeUpdate();
	}

	public void insertRowValuesIntoTable(XSSFSheet sheet, String tableName, int sheetNumber) {
		int j;
		Row row;
		Cell cell;
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			StringBuilder insertStatement = new StringBuilder("INSERT INTO " + tableName + sheetNumber + " VALUES(");
			row = sheet.getRow(i);
			for (j = 0; j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);

				if (j != row.getLastCellNum() - 1) {
					if (cell.getCellTypeEnum().toString().equals("NUMERIC")
							|| cell.getCellTypeEnum().toString().equals("FORMULA")) {
						insertStatement.append("\"" + cell.getNumericCellValue() + "\",");
					} else
						insertStatement.append("\"" + cell.getStringCellValue() + "\",");
				}

				else {
					if (cell.getCellTypeEnum().toString().equals("NUMERIC")
							|| cell.getCellTypeEnum().toString().equals("FORMULA")) {
						insertStatement.append("\"" + cell.getNumericCellValue() + "\");");
					} else
						insertStatement.append("\"" + cell.getStringCellValue().toString() + "\");");
				}

				System.out.println(insertStatement);
			}
			System.out.println(insertStatement);
			SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession()
					.createSQLQuery(insertStatement.toString());
			query.executeUpdate();
		}
	}

	@RequestMapping(value = "/viewData", method = RequestMethod.GET)
	public ModelAndView viewData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("view method called..");

		ModelAndView model = new ModelAndView("viewData");
		List<Employee> employeeList = new ArrayList<Employee>();

		String num = request.getParameter("sheetNum");

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(fullFilePath);

		FileInputStream file1 = new FileInputStream(file);

		XSSFWorkbook workBook = new XSSFWorkbook(file1);
		XSSFSheet sheet = workBook.getSheetAt(Integer.parseInt(num) - 1);
		Row row = sheet.getRow(0);
		Cell cell;
		List<String> tableHeader = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		for (int i = 0; i < row.getLastCellNum(); i++) {
			cell = row.getCell(i);
			System.out.println("cell value : " + row.getCell(i));
			tableHeader.add(row.getCell(i).toString());
		}

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				value.add(row.getCell(j).toString());
			}
		}

		for (String string : tableHeader) {
			System.out.println("Table Header---" + string.toString());
		}

		for (String string : value) {
			System.out.println("Table Value---" + string.toString());
		}
		model.addObject("tableHeader", tableHeader);
		model.addObject("value", value);
		return model;
	}
}
