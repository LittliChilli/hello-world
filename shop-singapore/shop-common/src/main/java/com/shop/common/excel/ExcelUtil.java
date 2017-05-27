package com.shop.common.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jxl.Workbook;
import jxl.write.Alignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@SuppressWarnings({ "deprecation", "unused" })
public class ExcelUtil {
	/**
	 * 
	 * @Description 使用JXL导出excel
	 * @version
	 * @author lion
	 * @date:2015年11月26日
	 * 
	 * @param fileName
	 *            excel名称
	 * @param Title
	 *            标题
	 * @param uesrSharingList
	 *            导出数据
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public final static String exportExcel(String fileName, String[] title, List list, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "0";
		// 以下开始输出到EXCEL
		try {
			/** **********创建工作簿************ */
			String separator = File.separator;
			File excelFile = new File("D:/" + fileName);
			// File excelFile = new File("D:" + separator + fileName);
			if (excelFile.exists()) {
				excelFile.delete();
			}
			excelFile.getParentFile().mkdirs();
			excelFile.createNewFile();
			WritableWorkbook workbook = Workbook.createWorkbook(excelFile);

			/** **********创建工作表************ */
			WritableSheet sheet = workbook.createSheet(fileName, 0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			// 普通text 或String类型
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// Integer类型
			WritableCellFormat integerFormat = new WritableCellFormat(NormalFont, NumberFormats.INTEGER);
			integerFormat.setBorder(Border.NONE, BorderLineStyle.THIN);

			// Double类型
			NumberFormat doubleFormat = new NumberFormat("0.00");
			WritableCellFormat doublewcf = new WritableCellFormat(NormalFont, doubleFormat);
			doublewcf.setBorder(Border.NONE, BorderLineStyle.THIN);

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL开头大标题********************* */
			// sheet.mergeCells(0, 0, title.length - 2, 1);
			// sheet.addCell(new Label(0, 0, title[0], wcf_center));
			/** ***************以下是EXCEL第一行列标题********************* */
			for (int i = 1; i < title.length; i++) {
				sheet.addCell(new Label(i - 1, 0, title[i], wcf_center));
			}
			/** ***************以下是EXCEL正文数据********************* */
			int i = 1;
			Field[] fields = null;
			for (Object obj : list) {
				fields = obj.getClass().getDeclaredFields();
				int j = 0;
				for (Field field : fields) {
					field.setAccessible(true);
					Object value = field.get(obj);

					// 设置单元格宽度
					sheet.setColumnView(j, 30);
					if (value == null) {
						value = "";
					}
					if (value.getClass().toString().equals("class java.lang.String")) {
						sheet.addCell(new Label(j, i, value.toString(), wcf_left));
					}
					if (value.getClass().toString().equals("class java.lang.Long")) {
						sheet.addCell(new Label(j, i, value.toString(), integerFormat));
					}
					if (value.getClass().toString().equals("class java.lang.Integer")) {
						sheet.addCell(new Label(j, i, value.toString(), integerFormat));
					}
					if (value.getClass().toString().equals("class java.lang.Double")) {
						sheet.addCell(new Label(j, i, value.toString(), doublewcf));
					}
					if (value.getClass().toString().equals("class java.lang.Boolean")) {
						sheet.addCell(new Label(j, i, value.toString(), wcf_left));
					}
					if (value.getClass().toString().equals("class java.util.Date")) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sheet.addCell(new Label(j, i, sdf.format(date), wcf_left));
					}
					if (value.getClass().toString().equals("class java.lang.Short")) {
						sheet.addCell(new Label(j, i, value.toString(), integerFormat));
					}
					j++;
				}
				i++;
			}

			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();

			// 下载excel文件
			downLoad(excelFile.getPath(), response);
		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			e.printStackTrace();
		}
		return result;
	}

	public static void downLoad(String path, HttpServletResponse response) throws IOException {
		// path是指欲下载的文件的路径。
		File file = new File(path);
		// 取得文件名。
		String filename = file.getName();
		// 以流的形式下载文件。
		InputStream fis = new BufferedInputStream(new FileInputStream(path));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		// 清空response
		response.reset();
		// 设置response的Header
		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(filename.getBytes("GB2312"), "iso8859-1"));
		response.addHeader("Content-Length", "" + file.length());
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/vnd.ms-excel;charset=utf8");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
	}
}
