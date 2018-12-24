package ac.kaist.smartfridge.ui.itemlist.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mongodb.WriteResult;

import ac.kaist.smartfridge.ui.itemlist.vo.ItemListVO;

@Transactional
@Service
public class ItemListServiceImpl implements ItemListService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<ItemListVO> selectItemList() {
		try {

		    List<ItemListVO> datas = mongoTemplate.find(new Query(), ItemListVO.class, "ItemListTb");
		    
		    if(datas != null) {
		    	for (ItemListVO itemListVO : datas) {
		    		
			        System.out.print(itemListVO.getFullCode());
			        System.out.println(itemListVO.getItemName());
			    }
		    }
		    return datas;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return  null;
	}
	
	@Override
	public ItemListVO selectItem(String key, ObjectId value) {
		try {
			System.out.println("mongoTemplate: " + mongoTemplate);
			
			Criteria criteria = new Criteria(key);
			criteria.is(value);
	        
		    Query query = new Query(criteria);
		        
		    ItemListVO data = mongoTemplate.findOne(query, ItemListVO.class, "ItemListTb");
		    if(data != null) {
		    	System.out.print(data.toString());
		    }
		   
		    return data;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return  null;
	}
	
	@Override
	public WriteResult updateItem(String key, ObjectId value, ItemListVO vo) {
		try {
			System.out.println("mongoTemplate: " + mongoTemplate);
			
			Criteria criteria = new Criteria(key);
			criteria.is(value);
		    Query query = new Query(criteria);

		    Update update = new Update();
		    update.set("itemName", vo.getItemName());
		    update.set("expirationDate", vo.getExpirationDate());
		    update.set("count", vo.getCount());
		    update.set("remark", vo.getRemark());
		    
		    WriteResult wr = mongoTemplate.updateMulti(query, update, "ItemListTb");
		    
		   
		    return wr;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return  null;
	}
	
	@Override
	public WriteResult deleteItem(String key, ObjectId value) {
		ItemListVO vo = null;
		try {
			System.out.println("mongoTemplate: " + mongoTemplate);
			
			Criteria criteria = new Criteria(key);
			criteria.is(value);
		    Query query = new Query(criteria);
		    
		    vo = selectItem(key, value);
		    WriteResult wr = mongoTemplate.remove(query, ItemListVO.class, "ItemListTb");
		    
		    if(vo != null && wr.wasAcknowledged()) {
		    	captureEvent("pull", vo);
		    }
		    
		    return wr;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return  null;
	}

	@Override
	public void insertItem(ItemListVO vo) {
		
		try {
			System.out.println("mongoTemplate: " + mongoTemplate);
			mongoTemplate.insert(vo,"ItemListTb");
			
			if(vo != null) {
				captureEvent("push", vo);
			}
			
		   
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public ItemListVO selectLocalGS1SourceProduct(String key, String value) {
		try {
			System.out.println("mongoTemplate: " + mongoTemplate);
			
			Criteria criteria = new Criteria(key);
			criteria.is(value);
	        
		    Query query = new Query(criteria);
		        
		    ItemListVO data = mongoTemplate.findOne(query, ItemListVO.class, "localGS1SourceTb");
		    if(data != null) {
		    	System.out.print(data.toString());
		    }
		   
		    return data;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return  null;
	}
	
	@Override
	public WriteResult deleteVoiceItem(String key, String value) {
		WriteResult wr = null;
		try {
			System.out.println("mongoTemplate: " + mongoTemplate);
			
			// 삭제할 데이터 1건 조회 
			Criteria criteria = new Criteria(key);
			criteria.is(value);
		    Query query = new Query(criteria);
		    ItemListVO data = mongoTemplate.findOne(query, ItemListVO.class, "ItemListTb");
		    
		    // 조회된 데이터 삭제 
		    if(data != null) {
		    	criteria = new Criteria("_id");
			    criteria.is(new ObjectId(data.getId()));
			    query = new Query(criteria);
			    wr = mongoTemplate.remove(query, ItemListVO.class, "ItemListTb");
			    
			    System.out.println(wr.wasAcknowledged());
			    if(wr.wasAcknowledged()) {
			    	captureEvent("pull", data);
			    }
		    }
		    
		    
		   
		    return wr;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return  null;
	}

	@Override
	public void insertLocalGS1SourceItem(ItemListVO vo) {
		try {
			System.out.println("mongoTemplate: " + mongoTemplate);
			mongoTemplate.insert(vo,"localGS1SourceTb");
		   
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void captureEvent(String state, ItemListVO vo) {
		
		String strXML = makeEventXML(state, vo);
		
		requestCapturedEvent(strXML);
	}
	
	public String makeEventXML(String state, ItemListVO vo) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			Date date = new Date();
			
			setEmptyAttr(vo);	// gtin, manufacturedDate, expirationDate 
			
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			Element root = document.createElement("epcis:EPCISDocument");
			root.setAttribute("xmlns:epcis", "urn:epcglobal:epcis:xsd:1");
			root.setAttribute("xmlns:refrigerator", "urn:autoidlabsk:epcisapp:refrigerator");
			root.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
			root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			root.setAttribute("creationDate", sdf.format(date));
			root.setAttribute("schemaVersion", "1.2");
			
			Element epcisBody = document.createElement("EPCISBody");
			Element eventList = document.createElement("EventList");
			Element aggregationEvent = document.createElement("AggregationEvent");
			
			Element eventTime = document.createElement("eventTime");
			eventTime.setTextContent(sdf.format(date));
			
			Element eventTimeZoneOffset = document.createElement("eventTimeZoneOffset");
			eventTimeZoneOffset.setTextContent("-09:00");
			
			Element parentID = document.createElement("parentID");
			parentID.setTextContent("urn:epc:id:sgtin:0614141.107346.2018");
			
			Element childEPCs = document.createElement("childEPCs");
			Element epc = document.createElement("epc");
			epc.setTextContent("urn:epc:id:sgtin:" + vo.getGtin() + "."
					+ vo.getManufacturedDate() + "." + vo.getExpirationDate());
			
			Element action = document.createElement("action");
			if("push".equals(state)){
				action.setTextContent("ADD");
			} else if("pull".equals(state)) {
				action.setTextContent("DELETE");
			}
			
			Element bizStep = document.createElement("bizStep");
			bizStep.setTextContent("urn:epcglobal:cbv:bizstep:" + state);
			
			Element extension = document.createElement("extension");
			Element childQuantityList = document.createElement("childQuantityList");
			Element quantityElement = document.createElement("quantityElement");
			Element epcClass = document.createElement("epcClass");
			epcClass.setTextContent("urn:epc:id:sgtin:" + vo.getGtin() + "."
					+ vo.getManufacturedDate() + "." + vo.getExpirationDate());
			
			Element quantity = document.createElement("quantity");
			quantity.setTextContent("1");
			
			document.appendChild(root);
			root.appendChild(epcisBody);
			epcisBody.appendChild(eventList);
			eventList.appendChild(aggregationEvent);
			
			aggregationEvent.appendChild(eventTime);
			aggregationEvent.appendChild(eventTimeZoneOffset);
			aggregationEvent.appendChild(parentID);
			aggregationEvent.appendChild(childEPCs);
			
			childEPCs.appendChild(epc);
			
			aggregationEvent.appendChild(action);
			aggregationEvent.appendChild(bizStep);
			aggregationEvent.appendChild(extension);
			
			extension.appendChild(childQuantityList);
			childQuantityList.appendChild(quantityElement);
			
			quantityElement.appendChild(epcClass);
			quantityElement.appendChild(quantity);
			
			// XML 문자열로 변환하기 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(out);
			
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			
			// 출력 시 문자코드: UTF-8
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			// 들여 쓰기 있음
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			
			String strXML = new String(out.toByteArray(), StandardCharsets.UTF_8);
			System.out.println(strXML);
			
			
			return strXML;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void setEmptyAttr(ItemListVO vo) {
		
		try {
			SimpleDateFormat dbSdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat gs1Sdf = new SimpleDateFormat("yyMMdd");
			
			String gtin = vo.getGtin();
			String expirationDate = vo.getExpirationDate();
			String manufacturedDate = vo.getManufacturedDate();
			
			if(gtin == null) {
				vo.setGtin("08800000000000");
			}
			
			if(manufacturedDate == null) {
				vo.setManufacturedDate(gs1Sdf.format(new Date()));
			} else {
				Date date = dbSdf.parse(expirationDate);
				vo.setManufacturedDate(gs1Sdf.format(date));
			}
			
			if(expirationDate == null) {
				vo.setExpirationDate(gs1Sdf.format(new Date()));
			} else {
				Date date = dbSdf.parse(expirationDate);
				vo.setExpirationDate(gs1Sdf.format(date));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean requestCapturedEvent(String strXML) {
		URL url = null;
		HttpURLConnection conn = null;
		int responseCode = 0;
				
		try {
			url = new URL("http://125.131.73.85:8080/epcis/Service/EventCapture");
			System.out.println("http://125.131.73.85:8080/epcis/Service/EventCapture");
			
			// request
			conn = (HttpURLConnection)url.openConnection();	
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-type", "text/xml");
			
			OutputStream os = conn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			osw.write(strXML);
			osw.flush();
			osw.close();
			
			responseCode = conn.getResponseCode();
			System.out.println("ResponseCode: " + responseCode);
			
			if(responseCode == 200) {
				conn.disconnect();
				return true;
			}
			
			
		
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		return false;
	}
}
