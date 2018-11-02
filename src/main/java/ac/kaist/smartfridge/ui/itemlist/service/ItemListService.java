package ac.kaist.smartfridge.ui.itemlist.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.WriteResult;

import ac.kaist.smartfridge.ui.itemlist.vo.ItemListVO;

public interface ItemListService {
	
	public List<ItemListVO> selectItemList();
	
	public ItemListVO selectItem(String key, ObjectId value);
	
	public WriteResult updateItem(String key, ObjectId value, ItemListVO vo);
	
	public WriteResult deleteItem(String key, ObjectId value);
}