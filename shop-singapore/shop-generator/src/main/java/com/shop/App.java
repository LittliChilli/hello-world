//package com.shop;
//
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//import org.mybatis.generator.api.MyBatisGenerator;
//import org.mybatis.generator.config.Configuration;
//import org.mybatis.generator.config.xml.ConfigurationParser;
//import org.mybatis.generator.exception.InvalidConfigurationException;
//import org.mybatis.generator.exception.XMLParserException;
//import org.mybatis.generator.internal.DefaultShellCallback;
//
///**
// * 自动化生成mapper.xml、inter接口与实体类
// *
// * @author left
// */
//public class App {
//	public static void main(String[] args) {
//		List<String> warnings = new ArrayList<String>();
//		boolean overwrite = true;
//		String genCfg = "/generatorConfig.xml";
//		File configFile = new File(App.class.getResource(genCfg).getFile());
//		ConfigurationParser cp = new ConfigurationParser(warnings);
//		Configuration config = null;
//		try {
//			config = cp.parseConfiguration(configFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (XMLParserException e) {
//			e.printStackTrace();
//		}
//		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//		MyBatisGenerator myBatisGenerator = null;
//		try {
//			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
//		} catch (InvalidConfigurationException e) {
//			e.printStackTrace();
//		}
//		try {
//			myBatisGenerator.generate(null);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//        System.out.println( "Hello World!" );
//    }
//}
