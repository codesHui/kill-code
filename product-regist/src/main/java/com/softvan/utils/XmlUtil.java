package com.softvan.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Xml操作工具类。
 * */
public final class XmlUtil {
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	/**
	 * 创建DocumentBuilder对象。
	 * @return
	 * 	DocumentBuilder对象。
	 * @throws ParserConfigurationException 
	 * */
	public synchronized static DocumentBuilder createDocumentBuilder() throws ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		return factory.newDocumentBuilder();
	}
	/**
	 * 创建Document对象。
	 * @return Document对象。
	 * @throws ParserConfigurationException 
	 * */
	public synchronized static Document createDocument() throws ParserConfigurationException{
		DocumentBuilder builder = createDocumentBuilder();
		if(builder == null) return null;
		return builder.newDocument();
	}
	/**
	 * 加载创建Document对象。
	 * @param file
	 * 	文件。
	 * @return
	 * 	Document对象。
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * */
	public synchronized static Document loadDocument(File file) throws SAXException, IOException, ParserConfigurationException{
		if(file != null && file.exists()){
			DocumentBuilder builder = createDocumentBuilder();
			if(builder != null){
				return builder.parse(file);
			}
		}
		return null;
	}
	/**
	 * 加载创建Document对象。
	 * @param is
	 * 	文件流。
	 * @return
	 * 	Document对象。
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * */
	public synchronized static Document loadDocument(InputStream is) throws SAXException, IOException, ParserConfigurationException{
		if(is != null){			 
			DocumentBuilder builder = createDocumentBuilder();
			if(builder != null){
				return builder.parse(is);
			}
		}
		return null;
	}
	/**
	 * 加载创建Document对象。
	 * @param data
	 * 	xml字符串。
	 * @return
	 * 	Document对象。
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * */
	public synchronized static Document loadDocument(String data) throws SAXException, IOException, ParserConfigurationException{
		if(data != null && !data.isEmpty()){
			return loadDocument(new ByteArrayInputStream(data.getBytes("UTF-8")));
		}
		return null;
	}
	/**
	 * 通过xpath取得节点列表。
	 * @param node
	 * 节点。
	 * @param expression
	 * XPath表达式。
	 * @return
	 * NodeList
	 * @throws XPathExpressionException
	 */
	public static NodeList selectNodes(Node node,String expression) throws XPathExpressionException{
		if(node == null || StringUtils.isEmpty(expression)) return null;
		XPathExpression xPathExpression = xpath.compile(expression);
		Object obj = xPathExpression.evaluate(node, XPathConstants.NODESET);
		return (NodeList)obj;
	}
	/**
	 * 通过xpath取得单个节点。
	 * @param node
	 * 节点。
	 * @param expression
	 * XPath表达式。
	 * @return
	 * @throws XPathExpressionException
	 */
	public static Node selectSingleNode(Node node,String expression) throws XPathExpressionException{
		if(node == null || StringUtils.isEmpty(expression)) return null;
		XPathExpression xPathExpression = xpath.compile(expression);
		Object obj = xPathExpression.evaluate(node, XPathConstants.NODE);
		return (Node)obj;
	}
	/**
	 * 根据xpath获取节点的文本值。
	 * @param node
	 * 节点。
	 * @param expression
	 * XPath 表达式。
	 * @return String
	 * @throws XPathExpressionException
	 * XPath表达式。
	 */
	public static String getNodeStringValue(Node node, String expression) throws XPathExpressionException{
		XPathExpression xPathExpression = xpath.compile(expression);
		Object obj = xPathExpression.evaluate(node, XPathConstants.STRING);
		return (String)obj;
	}
	/**
	 * 写入Xml.
	 * @param document
	 * 	document对象。
	 * @param writer
	 * 	写入器对象。
	 * @param encoding
	 * 	编码格式。
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * */
	public synchronized static void writeXml(Document document, Writer writer, String encoding) throws TransformerFactoryConfigurationError, TransformerException{
		if(document != null && writer != null){
			Source source = new DOMSource(document);
			Result result = new StreamResult(writer);
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.setOutputProperty(OutputKeys.ENCODING, (encoding == null || encoding.isEmpty()) ? "UTF-8":encoding);
			xformer.transform(source, result);
		}
	}
	/**
	 * 将Xml写入文件.
	 * @param document
	 * 	document对象.
	 * @param outputFile
	 * 	写入文件对象.
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws IOException 
	 * */
	public synchronized static void writeXmlToFile(Document document,File outputFile) throws TransformerFactoryConfigurationError, TransformerException, IOException{
		if(document != null && outputFile != null){
			FileOutputStream fos = new FileOutputStream(outputFile);
			OutputStreamWriter writer = new OutputStreamWriter(fos);
			writeXml(document, writer, null);
			writer.close();
		}
	}
	/**
	 * 将Document写入字符串。
	 * @param doc
	 * 	Document对象。
	 * @return 结果字符串。
	 * @throws TransformerException 
	 * @throws TransformerFactoryConfigurationError 
	 * */
	public synchronized static String writeXmlToString(Document doc) throws TransformerFactoryConfigurationError, TransformerException{
		if(doc != null){
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(output);
			writeXml(doc, writer, null);
			return output.toString();
		}
		return null;
	}
	/**
	 * 根据Xsd验证Xml文档。
	 * @param xmlInputStream
	 * 	Xml流。
	 * @param xsdInputStream
	 * Xsd文件流。
	 * @return 验证结果。
	 * @throws SAXException 
	 * @throws IOException 
	 * */
	public static boolean validateXml(InputStream xmlInputStream, InputStream xsdInputStream) throws SAXException, IOException{
		if(xmlInputStream == null || xsdInputStream == null) return false;
		//建立Schema工厂。
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		//利用Schema工厂，接收验证文档流生成Schema对象。
		Schema schema = schemaFactory.newSchema(new StreamSource(xsdInputStream));
		//通过Schema产生针对于此Schema的验证器，利用SchemaFile进行验证。
		Validator validator = schema.newValidator();
		//得到验证的数据源。
		Source source = new StreamSource(xmlInputStream);
			//开始验证，成功输出true,失败false。
		validator.validate(source);
		return true;	  
	}
}