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
		    System.out.print(data.getRemark());
		   
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
	
	
}
