package com.zitec.workshopz.user.mappers;

import java.util.HashMap;

import com.zitec.workshopz.base.BaseEntity;
import com.zitec.workshopz.base.BaseStorageMapper;
import com.zitec.workshopz.base.mappers.BaseMapper;
import com.zitec.workshopz.user.entities.User;
import com.zitec.workshopz.user.storage.adapters.UserWSAdapter;

public class UserMapper extends BaseStorageMapper {
	
	public void getEntity(String username, String password){
		((UserWSAdapter)this.adapter).getEntity(username, password);
	}
	
	@Override
	public BaseEntity hydrate(HashMap<String, String> data) {
		User user = new User();
		
		user.setEmail(data.get("email"));
		user.setRemoteId(data.get("remote_id"));
		user.setName(data.get("name"));
		user.setPhoneNumber(data.get("phone_number"));
		user.setPosition(data.get("position"));
		
		return user;
	}

	@Override
	public HashMap<String, String> dehydrate(BaseEntity data) {
		// TODO Auto-generated method stub
		return null;
	}
}
