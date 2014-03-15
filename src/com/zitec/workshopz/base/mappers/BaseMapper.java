package com.zitec.workshopz.base.mappers;

import java.util.HashMap;

import com.zitec.workshopz.base.EntityResponseListener;
import com.zitec.workshopz.base.storage.adapters.BaseStorageAdapter;

public abstract class BaseMapper<TEntity> {
	protected BaseStorageAdapter _adapter;
	protected EntityResponseListener _listner;

	public void setAdapter(BaseStorageAdapter adapter) {
		this._adapter = adapter;
	}

	public void setListner(EntityResponseListener listner) {
		this._listner = listner;
	}

	public void find(HashMap<String, String> params) {
		this._adapter.find(params);
	}
	
	public abstract TEntity hydrate(HashMap<String, Object> data);
	public abstract HashMap<String, Object> deHydrate(TEntity data);
}
