package ac.kaist.smartfridge.ui.itemlist.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		    System.out.print(data.toString());
		   
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
		try {
			System.out.println("mongoTemplate: " + mongoTemplate);
			
			Criteria criteria = new Criteria(key);
			criteria.is(value);
		    Query query = new Query(criteria);

		    WriteResult wr = mongoTemplate.remove(query, ItemListVO.class, "ItemListTb");
		   
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
		    System.out.print(data.toString());
		   
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
			
			// 삭제할 데이터 1건 조
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
		    }
		   
		    return wr;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return  null;
	}
	
	
}
