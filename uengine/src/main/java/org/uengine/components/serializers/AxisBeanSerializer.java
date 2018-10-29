package org.uengine.components.serializers;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Hashtable;

import javax.xml.namespace.QName;

import org.apache.axis.MessageContext;
import org.apache.axis.client.AxisClient;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.message.EnvelopeHandler;
import org.apache.axis.message.SOAPHandler;
import org.uengine.kernel.GlobalContext;
import org.xml.sax.InputSource;

/**
 * @author Jinyoung Jang
 */

public class AxisBeanSerializer implements org.uengine.kernel.Serializer{

	public boolean isSerializable(Class srcCls){
		try{
System.out.println("AxisBeanSerializer::isSerializable : testing class is " + srcCls);
			return (srcCls.getMethod("getTypeDesc", new Class[]{}) != null);
		}catch(Exception e){
			return false;
		}
	}

	public void serialize(Object sourceObj, OutputStream os, Hashtable extendedContext) throws Exception{

		try{
			final ClassLoader urlClassLoader = GlobalContext.getComponentClassLoader();
			/* this will replace the default system class loader with the new custom classloader, so that XMLEncoder will use the new custom classloader to lookup a class */
			Thread.currentThread().setContextClassLoader(urlClassLoader);
		} catch (Exception e) {
System.out.println("can't replace classloader");
		}
		
		try{		
			TypeDesc desc = (TypeDesc)sourceObj.getClass().getMethod("getTypeDesc", new Class[]{}).invoke(sourceObj, new Object[]{});
			QName xmlType = desc.getXmlType();
			//
			//xmlType = (QName)extendedContext.get("qName");
			//
			xmlType = new QName("http://" + sourceObj.getClass().getName(), org.uengine.util.UEngineUtil.getClassNameOnly(sourceObj.getClass()) );
//			xmlType = new QName(Constants.URI_SOAP11_ENV, Constants.ELEM_ENVELOPE );

			org.apache.axis.encoding.Serializer ser = 
				(org.apache.axis.encoding.Serializer)
				sourceObj.getClass().getMethod("getSerializer", new Class[]{String.class, Class.class, QName.class})
				.invoke(sourceObj, new Object[]{"", sourceObj.getClass(), xmlType});

			//Writer w = new OutputStreamWriter(os);
			StringWriter w = new StringWriter();
			SerializationContext context = new SerializationContext(w);
//			SerializationContext context = new SerializationContextImpl(w){
//				public MessageContext getMessageContext(){
//					return new MessageContext(null){
//						public String getEncodingStyle(){
//							return "";
//						}
//					};
//				}
//			};
	 		ser.serialize(xmlType, (org.xml.sax.Attributes)null, sourceObj, context);
	 		
	 		//System.out.println(w);
	 		//w.write("SdfasdfsadfA");
	 		
	 		BufferedOutputStream bao = new BufferedOutputStream(os);
	 		
	 		bao.write(w.toString().getBytes());
	 		
	 		bao.flush();
	 		bao.close();
	 		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Object deserialize(InputStream is, Hashtable extendedContext) throws Exception{
	
		//getting deserializer
		Class targetCls = (Class)extendedContext.get("targetClass");
		Object objectOfTargetCls = targetCls.newInstance();	
		TypeDesc desc = (TypeDesc)objectOfTargetCls.getClass().getMethod("getTypeDesc", new Class[]{}).invoke(objectOfTargetCls, new Object[]{});
		final QName xmlType;// = desc.getXmlType();
		xmlType = new QName("http://" + objectOfTargetCls.getClass().getName(), org.uengine.util.UEngineUtil.getClassNameOnly(objectOfTargetCls.getClass()) );
	        Deserializer dser = 
			(Deserializer)objectOfTargetCls.getClass().getMethod("getDeserializer", new Class[]{String.class, Class.class, QName.class})
				.invoke(objectOfTargetCls, new Object[]{"", objectOfTargetCls.getClass(), xmlType});
		//end
System.out.println("dser:"+dser);
		
		DeserializationContext context = new DeserializationContext(new InputSource(is), new MessageContext(new AxisClient()), "PurchaseOrder");
//		DeserializationContext context = new DeserializationContextImpl(
//			new org.xml.sax.InputSource(is),		
///*			new MessageContext(null){
//				public String getEncodingStyle(){
//					return xmlType.getNamespaceURI();
//				}
//			},*/
//			new MessageContext(new AxisClient()),
//			//Message.RESPONSE
//			"PurchaseOrder"
//		);	

	        boolean oldVal = context.isDoneParsing();
	        context.deserializing(true);
//	        ((DeserializationContextImpl)context).deserializing(true);
	        context.pushElementHandler(new EnvelopeHandler((SOAPHandler)dser));
	
//	        context.getRecorder().replay(0, -1, (org.xml.sax.ContentHandler)context);
	        context.getRecorder().replay(0, -1, (org.xml.sax.ContentHandler)context);
	
	        context.deserializing(oldVal);
//	        ((DeserializationContextImpl)context).deserializing(oldVal);
	        
	        context.parse();

	        return dser.getValue();

	}
	
/*	public static void main(String args[]) throws Exception{
		
		com.supplier.PurchaseOrder po = new com.supplier.PurchaseOrder();
		po.setItemNo("1");
		po.setQty(2000);
		
		AxisBeanSerializer ser = new AxisBeanSerializer();
		
		Hashtable ctx = new Hashtable();
		ctx.put("qName", new QName("",""));
		
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ser.serialize(po, bao, ctx);
		System.out.println("ser. result => \n" + bao);
		
		ByteArrayInputStream sbi = new ByteArrayInputStream(bao.toByteArray());
		
		ctx.put("targetClass", com.supplier.PurchaseOrder.class);
		
		System.out.println("deser. result => \n" +ser.deserialize(sbi, ctx));
		
		
		//ProcessVariableDescriptor pd = new ProcessVariableDescriptor();
		//pd.setXmlBindingClassName("com.supplier.PurchaseOrder");
		
		//System.out.println("deser. result => \n" +GlobalContext.deserialize(sbi, pd));
	}*/
}