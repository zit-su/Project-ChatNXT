package chatme.nxt;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.budiyev.android.codescanner.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class LogActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private String fontName = "";
	private String typeace = "";
	private String localFontPath = "";
	
	private LinearLayout linear1;
	private LinearLayout dv1;
	private ImageView imageview1;
	private LinearLayout l_email;
	private LinearLayout l_pass;
	private TextView textview2;
	private LinearLayout button_continue;
	private LinearLayout linear_dont_have;
	private LinearLayout dv2;
	private ImageView im_email;
	private EditText tx_email;
	private ImageView im_pass;
	private EditText tx_password;
	private TextView button_text;
	private ProgressBar progressbar1;
	private TextView intext;
	private TextView sign_up;
	private TextView textview3;
	
	private FirebaseAuth fauth;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private OnCompleteListener<Void> fauth_updateEmailListener;
	private OnCompleteListener<Void> fauth_updatePasswordListener;
	private OnCompleteListener<Void> fauth_emailVerificationSentListener;
	private OnCompleteListener<Void> fauth_deleteUserListener;
	private OnCompleteListener<Void> fauth_updateProfileListener;
	private OnCompleteListener<AuthResult> fauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> fauth_googleSignInListener;
	private SharedPreferences file;
	private Intent a = new Intent();
	private TimerTask t;
	private ProgressDialog prog;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.log);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		dv1 = findViewById(R.id.dv1);
		imageview1 = findViewById(R.id.imageview1);
		l_email = findViewById(R.id.l_email);
		l_pass = findViewById(R.id.l_pass);
		textview2 = findViewById(R.id.textview2);
		button_continue = findViewById(R.id.button_continue);
		linear_dont_have = findViewById(R.id.linear_dont_have);
		dv2 = findViewById(R.id.dv2);
		im_email = findViewById(R.id.im_email);
		tx_email = findViewById(R.id.tx_email);
		im_pass = findViewById(R.id.im_pass);
		tx_password = findViewById(R.id.tx_password);
		button_text = findViewById(R.id.button_text);
		progressbar1 = findViewById(R.id.progressbar1);
		intext = findViewById(R.id.intext);
		sign_up = findViewById(R.id.sign_up);
		textview3 = findViewById(R.id.textview3);
		fauth = FirebaseAuth.getInstance();
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		button_continue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_accout_login(tx_email, tx_password);
			}
		});
		
		sign_up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				a.setClass(getApplicationContext(), RegisterActivity.class);
				startActivity(a);
				
				overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
				
				finish();
			}
		});
		
		textview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				a.setClass(getApplicationContext(), LogchatmeActivity.class);
				startActivity(a);
			}
		});
		
		fauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		fauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					textview2.setText("Success...");
					file.edit().putString("email", FirebaseAuth.getInstance().getCurrentUser().getEmail()).commit();
					file.edit().putString("password", tx_password.getText().toString()).commit();
					t = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									a.setClass(getApplicationContext(), MainActivity.class);
									startActivity(a);
									finish();
								}
							});
						}
					};
					_timer.schedule(t, (int)(500));
					textview2.setTextColor(0xFF1A3764);
					if (getIntent().hasExtra("direct")) {
						_ProgresbarDimiss();
					}
				} else {
					if ("A network error (such as timeout, interrupted connection or unreachable host) has occurred.".equals(_errorMessage)) {
						textview2.setText("A network error!");
						textview2.setTextColor(0xFFF44336);
					} else {
						if ("The email address is badly formatted.".equals(_errorMessage)) {
							textview2.setTextColor(0xFFF44336);
							((EditText)tx_email).setError("invalid email-id!");
						} else {
							if ("The password is invalid or the user does not have a password.".equals(_errorMessage)) {
								textview2.setText("Wrong Password !");
								textview2.setTextColor(0xFFF44336);
							} else {
								if ("The user account has been disabled by an administrator.".equals(_errorMessage)) {
									textview2.setText("Your account was Disabled!");
									textview2.setTextColor(0xFFF44336);
								} else {
									if ("There is no user record corresponding to this identifier. The user may have been deleted.".equals(_errorMessage)) {
										textview2.setText("There is no linked account with this email. Try to register first!");
										textview2.setTextColor(0xFFF44336);
									} else {
										textview2.setText(_errorMessage);
									}
								}
							}
						}
					}
					button_continue.setEnabled(true);
					button_text.setVisibility(View.VISIBLE);
					progressbar1.setVisibility(View.GONE);
					textview2.setTextColor(0xFFF44336);
					if (getIntent().hasExtra("direct")) {
						_ProgresbarDimiss();
					}
				}
			}
		};
		
		_fauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		_SetFont("fnm1");
		_lightStatusBar("");
		_UI();
		_directlogin();
	}
	
	public void _lightStatusBar(final String _color) {
		getWindow().setStatusBarColor(Color.parseColor("#F5F5F5"));
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		
	}
	
	
	public void _UI() {
		_ProgressBarColour(progressbar1, "#FFFFFF");
		button_continue.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)38, 0xFF1A3764));
		l_email.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)38, 0xFFFFFFFF));
		l_pass.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)38, 0xFFFFFFFF));
		_ImageColor(im_email, "#1A3764");
		_ImageColor(im_pass, "#1A3764");
		progressbar1.setVisibility(View.GONE);
		button_continue.setElevation((float)3);
		textview3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)25, 0x0D607D8B));
	}
	
	
	public void _ImageColor(final ImageView _image, final String _color) {
		_image.setColorFilter(Color.parseColor(_color),PorterDuff.Mode.SRC_ATOP);
	}
	
	
	public void _ProgressBarColour(final ProgressBar _progressbar, final String _color) {
		if (android.os.Build.VERSION.SDK_INT >=21) {
			_progressbar.getIndeterminateDrawable() .setColorFilter(Color.parseColor(_color), PorterDuff.Mode.SRC_IN);
		}
	}
	
	
	public void _accout_login(final TextView _t1, final TextView _t2) {
		if (!_t1.getText().toString().trim().equals("") && !_t2.getText().toString().trim().equals("")) {
			fauth.signInWithEmailAndPassword(_t1.getText().toString(), _t2.getText().toString()).addOnCompleteListener(LogActivity.this, _fauth_sign_in_listener);
			button_text.setVisibility(View.GONE);
			progressbar1.setVisibility(View.VISIBLE);
			button_continue.setEnabled(false);
		} else {
			if (_t1.getText().toString().trim().equals("")) {
				((EditText)tx_email).setError("Enter password!");
			}
			if (_t2.getText().toString().trim().equals("")) {
				((EditText)tx_password).setError("Enter Email Address!");
			}
		}
	}
	
	
	public void _directlogin() {
		if (getIntent().hasExtra("direct")) {
			fauth.signInWithEmailAndPassword(getIntent().getStringExtra("email"), getIntent().getStringExtra("pass")).addOnCompleteListener(LogActivity.this, _fauth_sign_in_listener);
			_ProgresbarShow("Verifying..");
		}
	}
	
	
	public void _ProgresbarShow(final String _title) {
		prog = new ProgressDialog(LogActivity.this);
		prog.setMax(100);
		prog.setMessage(_title);
		prog.setIndeterminate(true);
		prog.setCancelable(false);
		prog.show();
	}
	
	
	public void _ProgresbarDimiss() {
		if(prog != null)
		{
			prog.dismiss();
		}
	}
	
	
	public void _SetFont(final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		localFontPath = file.getString("font_url", "");
		overrideFonts(this,getWindow().getDecorView()); 
	}
	
	private void overrideFonts(final Context context, final View v) {
		try {
			Typeface typeface;
			
			File localFont = new File(localFontPath);
			if (localFont.exists()) {
				typeface = Typeface.createFromFile(localFont);
			} else {
				typeface = Typeface.createFromAsset(getAssets(), fontName);
			}
			
			applyTypeface(v, typeface);
			
		} catch (Exception e) {
			SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
		}
	}
	
	private void applyTypeface(View v, Typeface typeface) {
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			for (int i = 0; i < vg.getChildCount(); i++) {
				View child = vg.getChildAt(i);
				applyTypeface(child, typeface);
			}
		} else if (v instanceof TextView) {
			((TextView) v).setTypeface(typeface);
		} else if (v instanceof EditText) {
			((EditText) v).setTypeface(typeface);
		} else if (v instanceof Button) {
			((Button) v).setTypeface(typeface);
		}
		
		
	}
	
}