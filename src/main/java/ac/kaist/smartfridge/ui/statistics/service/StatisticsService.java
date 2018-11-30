package ac.kaist.smartfridge.ui.statistics.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.WriteResult;

import ac.kaist.smartfridge.ui.itemlist.vo.ItemListVO;

public interface StatisticsService {
	
	public List<ItemListVO> selectItemList();
	
	
}
