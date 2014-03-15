package com.zitec.workshopz.user.views;

import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.zitec.workshopz.R;
import com.zitec.workshopz.base.BaseView;
import com.zitec.workshopz.user.activities.LoginActivity;

public class LoginView extends BaseView {

	protected LoginActivity act;
	EditText username;
	EditText password;
	Button submit;
	SparseArray<String> _errors;

	public LoginView(LoginActivity act) {
		this.act = act;
		this._errors = new SparseArray<String>();

		this._errors.put(R.id.username,
				this.act.getResources()
						.getString(R.string.empty_username_error));
		this._errors.put(R.id.password,
				this.act.getResources()
						.getString(R.string.empty_password_error));
	}

	@Override
	public void initView() {
		this.act.setContentView(R.layout.login_activity);
		this.username = (EditText) this.act.findViewById(R.id.username);
		this.password = (EditText) this.act.findViewById(R.id.password);
		this.submit = (Button) this.act.findViewById(R.id.submit);
	}

	@Override
	public void setActions() {
		this.submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginView.this.submitLogin();
			}
		});
		this.password.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (event == null)
					return false;

				LoginView.this.submitLogin();
				return false;

			}
		});
	}

	protected void submitLogin() {
		SparseArray<String> values = new SparseArray<String>();
		
		String username = this.username.getText().toString();
		String pass =this.password.getText().toString();
		
		values.append(R.id.username, username);
		values.append(R.id.password, pass);

		if (this.act.validateLogin(values)) {
			this.act.login(username, pass);
		} else {
			this.showErrors();
		}
	}

	private void showErrors() {
		SparseArray<String> errors = this.act.getErrors();
		int count = errors.size();

		for (int i = 0; i < count; i++) {
			((EditText) this.act.findViewById(errors.keyAt(i))).setError(errors
					.valueAt(i));
		}
	}

	public String getEmptyError(int key) {
		return this._errors.get(key);
	}
}
