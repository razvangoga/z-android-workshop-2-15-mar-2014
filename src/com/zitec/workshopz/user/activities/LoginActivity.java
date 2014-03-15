package com.zitec.workshopz.user.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseLongArray;

import com.zitec.workshopz.R;
import com.zitec.workshopz.base.BaseActivity;
import com.zitec.workshopz.base.BaseEntity;
import com.zitec.workshopz.base.EntityResponseListener;
import com.zitec.workshopz.base.storage.Error;
import com.zitec.workshopz.base.validators.NotEmpty;
import com.zitec.workshopz.user.entities.User;
import com.zitec.workshopz.user.mappers.UserMapper;
import com.zitec.workshopz.user.storage.adapters.UserDbAdapter;
import com.zitec.workshopz.user.storage.adapters.UserWSAdapter;
import com.zitec.workshopz.user.views.LoginView;

public class LoginActivity extends BaseActivity {
	private LoginView view;
	private SparseArray<String> _errors;
	
	@Override
	public void onCreate(Bundle args0){
		super.onCreate(args0);
		this.view = new LoginView(this);
		this.view.initView();
		this.view.setActions();
		
		this._errors = new SparseArray<String>();
	}
	
	public SparseArray<String> getErrors() {
		return this._errors;
	}
	
	public boolean validateLogin(SparseArray<String> values) {
		boolean result = true;
		
		NotEmpty validator = new NotEmpty();
		int valuesCount = values.size();
		
		this._errors.clear();
		
		for(int i=0; i< valuesCount; i++){
			int key = values.keyAt(i);
			boolean isValied =  validator.validate(values.valueAt(i));			
			result &= isValied;
			
			if(!isValied)
				this._errors.put(key, this.view.getEmptyError(key));
		}
		
		return result;
	}

	public void login(String username, String password) {
		final UserMapper mapper = new UserMapper();
		mapper.setAdapter(new UserWSAdapter(this));
		mapper.setListener(new EntityResponseListener() {
			
			@Override
			public void onSuccess(ArrayList<BaseEntity> obj) {
				
				if(obj.size() < 1) {
					LoginActivity.this.showGenericError(LoginActivity.this, 
							new Error(LoginActivity.this.getResources().getString(R.string.bad_credentials)));
					return;
				}
				User user = (User)obj.get(0);
				user.setCurrentIdentity("true");
				
				mapper.setAdapter(new UserDbAdapter(LoginActivity.this));
				mapper.save(user);
				
				BaseActivity.identity = user;
				
				LoginActivity.this.loadWorkshops();
			}
			
			@Override
			public void onError(Error err) {
				LoginActivity.this.showGenericError(LoginActivity.this, err);
			}
		});

		mapper.getEntity(username, password);
	}
}
