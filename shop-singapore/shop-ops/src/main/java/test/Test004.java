/**
 * 	短8位UUID思想其实借鉴微博短域名的生成方式，但是其重复概率过高，而且每次生成4个，需要随即选取一个。
	本算法利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，然后通过模62操作，结果作为索引取出字符，
	这样重复率大大降低。
	经测试，在生成一千万个数据也没有出现重复，完全满足大部分需求。
 */
package test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.Font;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class Test004 {

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public static void generateShortUuid() throws IOException {
		StringBuffer shortBuffer = new StringBuffer();
		for(int t=0;t<10000000;t++){
			String uuid = UUID.randomUUID().toString().replace("-", "");
			for (int i = 0; i < 8; i++) {
				String str = uuid.substring(i * 4, i * 4 + 4);
				int x = Integer.parseInt(str, 16);
				shortBuffer.append(chars[x % 0x3E]);
			}
			
			shortBuffer.append("\r\n");
		}
		System.out.println("生成完毕===============");
		FileWriter writer = new FileWriter("D:/tst2.txt");
		writer.write(shortBuffer.toString());
		writer.flush();
		writer.close();

	}
	
	
	public static String generateShortUuid2() {
	    StringBuffer shortBuffer = new StringBuffer();
	    String uuid = UUID.randomUUID().toString().replace("-", "");
	    for (int i = 0; i < 8; i++) {
	        String str = uuid.substring(i * 4, i * 4 + 4);
	        int x = Integer.parseInt(str, 16);
	        shortBuffer.append(chars[x % 0x3E]);
	    }
	    return shortBuffer.toString();

	}

	public static void main(String[] args) throws IOException, WriterException {
//		generateShortUuid();
		String text = "http://10.100.101.203:8080/#!/user/newRegister/referral=invitationCode&&username="
			+ "this.partenrInfo.realName" + "&&t=";   
        int width = 100;   
        int height = 100;   
        String format = "png";   
        Hashtable hints= new Hashtable();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);   
        File outputFile = new File("D:/new.png");  
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, format, byteArrayOut);
        FileOutputStream fileOut = null;     
        BufferedImage bufferImg = null;     
       //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray    
       try {  
           HSSFWorkbook wb = new HSSFWorkbook();     
           HSSFSheet sheet1 = wb.createSheet("test picture"); 
           
           sheet1.setColumnWidth(1, 100 * 256);
           sheet1.setColumnWidth(2, 40 * 256);
           
           HSSFRow row = sheet1.createRow((int) 0);  
           // 第四步，创建单元格，并设置值表头 设置表头居中  
           HSSFCellStyle style = wb.createCellStyle();  
           style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
           
           HSSFCellStyle style2 = wb.createCellStyle();  
           style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式 
           style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式 
           style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);	//填充单元格
           style2.setFillForegroundColor(HSSFColor.YELLOW.index); //
           
           HSSFFont font = wb.createFont();
           font.setFontHeightInPoints((short)12); //字体大小
           font.setFontName("楷体");
           font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
//           font.setColor(HSSFColor.GREEN.index);    //绿字
           style2.setFont(font);
     
           HSSFCell cell = row.createCell((short) 0);  
           
           cell.setCellValue("邀请码");  
           cell.setCellStyle(style2);  
           cell = row.createCell((short) 1);  
           cell.setCellValue("链接");  
           cell.setCellStyle(style2);  
           cell = row.createCell((short) 2);  
           cell.setCellValue("二维码");  
           cell.setCellStyle(style2);  
           //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）  
           HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();  
           
           int count = 1;
           int row1 = 1;
           int row2 = 2;
           for(int t=0;t<3;t++){
        	   
        	   row = sheet1.createRow((int) t + 1); 
        	   row.setHeight((short) (200*20));
               HSSFCell valueCell = row.createCell((short) 0);
               valueCell.setCellValue("dsdasd44");
               valueCell.setCellStyle(style);
               valueCell = row.createCell((short) 1);
               valueCell.setCellValue(text);
               valueCell.setCellStyle(style);
        	   
        	   HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 20, 20,(short) 2, row1, (short) 3, row2);     
               anchor1.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);     
               //插入图片    
               patriarch.createPicture(anchor1, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
               row1 += count;
               row2 += count;
           }
           
           //anchor主要用于设置图片的属性  
           
           
           fileOut = new FileOutputStream("D:/测试Excel.xls");     
           // 写入excel文件     
            wb.write(fileOut);     
            System.out.println("----Excle文件已生成------");  
       } catch (Exception e) {  
           e.printStackTrace();  
       }finally{  
           if(fileOut != null){  
                try {  
                   fileOut.close();  
               } catch (IOException e) {  
                   e.printStackTrace();  
               }  
           }  
       }  
        
        
        
        
        
	}

}
