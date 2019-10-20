package com.svarks.lapp.order.rest.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.order.entity.OrderInfo;
import com.svarks.lapp.order.entity.OrderLineItem;
import com.svarks.lapp.order.entity.SAPFileInfo;

public class TestFunSample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String str="rajeshsavi123@gmail.com";
		System.out.println("base 64 pwd encryption==>"+getBase64EncryptionPwd(str));
		System.out.println("pwd==>"+OrderMarkingAdminController.getAlphaNumericString(6, str));
		System.out.println("base 64 pwd decryption==>"+getBase64DecryptionPwd(str));
*/
		
		/*System.out.println("header size==>"+getSAPHeaderList().size());
		System.out.println("Is Header matches==>"+isHeaderMatches("Artice no"));*/
		uploadSAPOrderData();
	}
	
	public static boolean isHeaderMatches(String headerName) {
		return getSAPHeaderList().stream().anyMatch(headerName::equalsIgnoreCase);
	}
	
	private static List<String> getSAPHeaderList(){
		List<String> headerList = new LinkedList<String>();
		headerList.add("LineItem");
		headerList.add("OpenQuantity");
		headerList.add("ProductionOrder No");
		headerList.add("Production Order Status");
		headerList.add("ArticleNo");
		headerList.add("Description");
		headerList.add("CustomerPart No");
		headerList.add("EmailID");
		headerList.add("CustomerNo");
		headerList.add("Contact");
		headerList.add("Country");
		headerList.add("");
		headerList.add("");
		return headerList;
		
	}


	
	private static String getBase64EncryptionPwd(String str) {
	return Base64.getEncoder().encodeToString(OrderMarkingAdminController.getAlphaNumericString(6, str).getBytes());
	}
	
	private static String getBase64DecryptionPwd(String str) {
		 byte[] actualByte = Base64.getDecoder().decode(str); 
		 return new String(actualByte); 
		
	}
	
	public static void uploadSAPOrderData() {

		try {
			File sapExcelFile = new File("D:/sapFile/lappExcel.xlsx");
			if (sapExcelFile.exists()) {
				InputStream inputStream = new FileInputStream(sapExcelFile);
				XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet worksheet = workbook.getSheetAt(0);
				for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
					XSSFRow row = worksheet.getRow(i);
					if (!isRowValid(row)) {
						System.out.println("*****Row invalid**** Row Number"+i);
						continue;
					}
					
					System.out.println("Row valid");
					System.out.println("cell value===>"+getCellValue(row.getCell(0)));
					System.out.println("cell value===>"+getCellValue(row.getCell(1)));
					System.out.println("cell value===>"+getCellValue(row.getCell(2)));
					System.out.println("cell value===>"+getCellValue(row.getCell(3)));
					System.out.println("cell value===>"+getCellValue(row.getCell(4)));
					System.out.println("cell value===>"+getCellValue(row.getCell(5)));
					System.out.println("cell value===>"+getCellValue(row.getCell(6)));
					System.out.println("cell value===>"+getCellValue(row.getCell(7)));
					System.out.println("cell value===>"+getCellValue(row.getCell(8)));
					System.out.println("cell value===>"+getCellValue(row.getCell(9)));
					System.out.println("cell value===>"+getCellValue(row.getCell(10)));
					System.out.println("cell value===>"+getCellValue(row.getCell(11)));
					//System.out.println("cell value===>"+getCellValue(row.getCell(12)));
					
					
					//if (orderInfoService.findByProdOrder(getCellValue(row.getCell(3)))) {
						/*orderLineItem.setArticleNo(getCellValue(row.getCell(5)));
						orderLineItem.setCreatedDate(new Date());
						orderLineItem.setCustomerNo(getCellValue(row.getCell(9)));
						orderLineItem.setCustomerPartNo(getCellValue(row.getCell(7)));
						orderLineItem.setDescription(getCellValue(row.getCell(6)));
						// orderLineItem.setLength(length);
						orderLineItem.setLineItemno(getCellValue(row.getCell(1)));
						orderLineItem.setModifiedDate(new Date());
						orderLineItem.setProductionOrderno(getCellValue(row.getCell(3)));
						orderLineItem.setUpdatedBy("");
						orderLineItemList.add(orderLineItem);*/
					/*} else {
						OrderInfo orderInfo = new OrderInfo();
						List<OrderLineItem> lineItemList = new LinkedList<>();
						orderInfo.setCountryCode(getCellValue(row.getCell(11)));
						orderInfo.setCreatedBy(sapFileInfo.getUploadedBy());
						orderInfo.setCreatedDate(new Date());
						orderInfo.setModifiedDate(new Date());
						orderInfo.setOrderDate(new Date());
						orderInfo.setOrderStatus(OrderMarkingConstants.NEW_ORDER);
						orderInfo.setProductionOrderno(getCellValue(row.getCell(3)));
						orderInfo.setProductionOrderStatus(getCellValue(row.getCell(4)));
						orderInfo.setUserEmailId(getCellValue(row.getCell(12)));

						orderLineItem.setArticleNo(getCellValue(row.getCell(5)));
						orderLineItem.setCreatedDate(new Date());
						orderLineItem.setCustomerNo(getCellValue(row.getCell(9)));
						orderLineItem.setCustomerPartNo(getCellValue(row.getCell(7)));
						orderLineItem.setDescription(getCellValue(row.getCell(6)));
						// orderLineItem.setLength(length);
						orderLineItem.setLineItemno(getCellValue(row.getCell(1)));
						orderLineItem.setModifiedDate(new Date());
						orderLineItem.setProductionOrderno(getCellValue(row.getCell(3)));
						orderLineItem.setUpdatedBy("");
						lineItemList.add(orderLineItem);
						orderInfo.setOrderListItemInfoList(lineItemList);
						orderInfoService.save(orderInfo);
						log.info("New Order Created==>" + orderInfo.getProductionOrderno());
					}
					
					 * log.info("Cell 0==>" + row.getCell(0)); log.info("Cell 1==>" +
					 * row.getCell(1)); log.info("Cell 2==>" + row.getCell(2)); log.info("Cell 3==>"
					 * + row.getCell(3)); log.info("Cell 4==>" + row.getCell(4));
					 * log.info("Cell 5==>" + row.getCell(5)); log.info("Cell 6==>" +
					 * row.getCell(6)); log.info("Cell 7==>" + row.getCell(7)); log.info("Cell 8==>"
					 * + row.getCell(8)); log.info("Cell 9==>" + row.getCell(9));
					 * log.info("Cell 10==>" + row.getCell(10));
					 */

				/*if (!orderLineItemList.isEmpty())
					orderItemServie.saveAll(orderLineItemList);

				// Update the order count and order list item count
				updateSAPFileStatus(OrderMarkingConstants.SUCCESS, sapFileInfo.getFileId(), orderCount, orderItemCount);

			} else {
				log.info("Uploaded file does not exists..!" + sapFileInfo.getFilePath());
			}*/
				}
			}
		} catch (Exception e) {
			System.out.println("Exception while reading excel file==>" + e);
		}
	}

	private static boolean isRowValid(XSSFRow row) {
		if (getCellValue(row.getCell(3)).isEmpty() || getCellValue(row.getCell(0)).isEmpty()
				|| getCellValue(row.getCell(8)).isEmpty() || getCellValue(row.getCell(3)).isEmpty()) {
			return false;
		}
		return true;
	}

	private static String getCellValue(XSSFCell cellValue) {
		DataFormatter formatter = new DataFormatter();
		try {
			return  formatter.formatCellValue(cellValue);
			//String cellValueInStr = formatter.formatCellValue(cellValue);
			//return (cellValueInStr != null && !cellValueInStr.isEmpty()) ? cellValueInStr : "";

		} catch (NullPointerException e) {
			//log.error("Cell value is getting NULL pointer==>" + e);
			System.gc();
			return "";
		} catch (Exception e) {
			//log.error("Exception while reading cell value==>" + e);
			e.printStackTrace();
			return "";
		}

	}
}
