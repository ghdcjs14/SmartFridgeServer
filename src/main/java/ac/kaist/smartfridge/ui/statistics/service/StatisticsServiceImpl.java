package ac.kaist.smartfridge.ui.statistics.service;

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
public class StatisticsServiceImpl implements StatisticsService {
	
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
	
	
	
}
