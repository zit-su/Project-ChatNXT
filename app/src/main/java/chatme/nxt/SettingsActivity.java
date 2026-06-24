package chatme.nxt;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.budiyev.android.codescanner.*;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import android.database.Cursor;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class SettingsActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String fontName = "";
	private String typeace = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String localFontPath = "";
	
	private LinearLayout linear_header;
	private LinearLayout linear_bg;
	private ImageView back;
	private LinearLayout linear_div;
	private LinearLayout linear_logout;
	private TextView logout_txt;
	private ImageView imageview_logout;
	private LinearLayout linear4;
	private ScrollView vscroll1;
	private CircleImageView circleimageview1;
	private TextView username;
	private TextView email;
	private LinearLayout edit_button;
	private TextView edit_profile_text;
	private ImageView go_to_profile;
	private LinearLayout linear_vs_child;
	private LinearLayout linear5;
	private TextView textview6;
	private LinearLayout Account_switch_layout;
	private TextView instraction1;
	private LinearLayout linear_q_chat;
	private TextView textview7;
	private LinearLayout linear_notification;
	private LinearLayout linear_font;
	private ImageView profile_switch;
	private TextView textview5;
	private ImageView btn_switch;
	private TextView qchat;
	private Switch q_chat;
	private ImageView notify_img;
	private Switch notifications;
	private ImageView font_img;
	private LinearLayout linear6;
	private Switch font;
	private TextView textview8;
	
	private Intent a = new Intent();
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
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
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.settings);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear_header = findViewById(R.id.linear_header);
		linear_bg = findViewById(R.id.linear_bg);
		back = findViewById(R.id.back);
		linear_div = findViewById(R.id.linear_div);
		linear_logout = findViewById(R.id.linear_logout);
		logout_txt = findViewById(R.id.logout_txt);
		imageview_logout = findViewById(R.id.imageview_logout);
		linear4 = findViewById(R.id.linear4);
		vscroll1 = findViewById(R.id.vscroll1);
		circleimageview1 = findViewById(R.id.circleimageview1);
		username = findViewById(R.id.username);
		email = findViewById(R.id.email);
		edit_button = findViewById(R.id.edit_button);
		edit_profile_text = findViewById(R.id.edit_profile_text);
		go_to_profile = findViewById(R.id.go_to_profile);
		linear_vs_child = findViewById(R.id.linear_vs_child);
		linear5 = findViewById(R.id.linear5);
		textview6 = findViewById(R.id.textview6);
		Account_switch_layout = findViewById(R.id.Account_switch_layout);
		instraction1 = findViewById(R.id.instraction1);
		linear_q_chat = findViewById(R.id.linear_q_chat);
		textview7 = findViewById(R.id.textview7);
		linear_notification = findViewById(R.id.linear_notification);
		linear_font = findViewById(R.id.linear_font);
		profile_switch = findViewById(R.id.profile_switch);
		textview5 = findViewById(R.id.textview5);
		btn_switch = findViewById(R.id.btn_switch);
		qchat = findViewById(R.id.qchat);
		q_chat = findViewById(R.id.q_chat);
		notify_img = findViewById(R.id.notify_img);
		notifications = findViewById(R.id.notifications);
		font_img = findViewById(R.id.font_img);
		linear6 = findViewById(R.id.linear6);
		font = findViewById(R.id.font);
		textview8 = findViewById(R.id.textview8);
		fauth = FirebaseAuth.getInstance();
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				a.setClass(getApplicationContext(), MainActivity.class);
				startActivity(a);
				finish();
			}
		});
		
		linear_logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseAuth.getInstance().signOut();
				a.setClass(getApplicationContext(), MainActivity.class);
				startActivity(a);
				finish();
			}
		});
		
		edit_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				a.setClass(getApplicationContext(), ProfileActivity.class);
				startActivity(a);
				finish();
			}
		});
		
		Account_switch_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseAuth.getInstance().signOut();
				a.setClass(getApplicationContext(), LogchatmeActivity.class);
				startActivity(a);
				finish();
			}
		});
		
		notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					//Starts Background Notifications!
					
					Intent intent = new Intent(SettingsActivity.this, notiservice.class);
					startService(intent);
					
					file.edit().putString("noti", "true").commit();
				} else {
					//Stop Background Notifications 
					
					Intent intent = new Intent(SettingsActivity.this, notiservice.class);
					stopService(intent);
					
					file.edit().putString("noti", "false").commit();
				}
			}
		});
		
		font.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					textview8.setVisibility(View.VISIBLE);
				} else {
					textview8.setVisibility(View.GONE);
					file.edit().remove("cust_font").commit();
					file.edit().remove("font_url").commit();
				}
			}
		});
		
		textview8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(Intent.createChooser(intent, "Select a font"), 1001);
				
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						if (_childValue.containsKey("username")) {
							username.setText(_childValue.get("username").toString());
						}
						if (_childValue.containsKey("email")) {
							email.setText(_childValue.get("email").toString());
						}
						if (_childValue.containsKey("dp")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(circleimageview1);
						}
					} else {
						
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						if (_childValue.containsKey("username")) {
							username.setText(_childValue.get("username").toString());
						}
						if (_childValue.containsKey("email")) {
							email.setText(_childValue.get("email").toString());
						}
						if (_childValue.containsKey("dp")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(circleimageview1);
						}
					} else {
						
					}
				}
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						if (_childValue.containsKey("username")) {
							username.setText(_childValue.get("username").toString());
						}
						if (_childValue.containsKey("email")) {
							email.setText(_childValue.get("email").toString());
						}
						if (_childValue.containsKey("dp")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(circleimageview1);
						}
					} else {
						
					}
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		users.addChildEventListener(_users_child_listener);
		
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
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(0xFFFFFFFF);
		_SetFont("fnm1");
		_UI();
		_switch_functions();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		/*if (_requestCode == 1001 && _resultCode == RESULT_OK && _data != null) {
        Uri fontUri = _data.getData();
        String fontPath = FileUtil.convertUriToFilePath(getApplicationContext(), fontUri);
        
        try {
            Typeface tf = Typeface.createFromFile(new File(fontPath));
            applyTypeface(getWindow().getDecorView(), tf);
        } catch (Exception e) {
            SketchwareUtil.showMessage(getApplicationContext(), "Invalid font file");
        }
    }
*/
		if (_requestCode == 1001 && _resultCode == RESULT_OK && _data != null) {
			Uri fontUri = _data.getData();
			String fontPath = FileUtil.convertUriToFilePath(getApplicationContext(), fontUri);
			
			try {
				Typeface tf = Typeface.createFromFile(new File(fontPath));
				applyTypeface(getWindow().getDecorView(), tf);
				SketchwareUtil.showMessage(getApplicationContext(), "Font applied successfully!");
				file.edit().putString("font_url", fontPath).commit();
				file.edit().putString("cust_font", "true").commit();
			} catch (Exception e) {
				SketchwareUtil.showMessage(getApplicationContext(), "Invalid font file");
			}
		}
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		a.setClass(getApplicationContext(), MainActivity.class);
		startActivity(a);
		finish();
	}
	
	public void _UI() {
		_ImageColor(imageview_logout, "#F44336");
		_ripple(back);
		_ripple(linear_logout);
		edit_button.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFF1A3764));
		linear_font.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)36, 0xFFF0F2F5));
		Account_switch_layout.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)36, 0xFFF0F2F5));
		profile_switch.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFFFFFFFF));
		font_img.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFFFFFFFF));
		linear_notification.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)36, 0xFFF0F2F5));
		notify_img.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFFFFFFFF));
		qchat.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFFFFFFFF));
		linear_q_chat.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)36, 0xFFF0F2F5));
		qchat.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/cap.ttf"), 0);
		textview8.setVisibility(View.GONE);
	}
	
	
	public void _ImageColor(final ImageView _image, final String _color) {
		_image.setColorFilter(Color.parseColor(_color),PorterDuff.Mode.SRC_ATOP);
	}
	
	
	public void _ripple(final View _view) {
		
		int[] attrs = new int[]{android.R.attr.selectableItemBackgroundBorderless};
		android.content.res.TypedArray typedArray = this.obtainStyledAttributes(attrs);
		int backgroundResource = typedArray.getResourceId(0, 0); _view.setBackgroundResource(backgroundResource);
		_view.setClickable(true);
	}
	
	
	public void _switch_functions() {
		if (file.contains("noti")) {
			if ("true".equals(file.getString("noti", ""))) {
				notifications.setChecked(true);
			} else {
				notifications.setChecked(false);
			}
		}
		if (file.contains("cust_font")) {
			if ("true".equals(file.getString("cust_font", ""))) {
				font.setChecked(true);
			} else {
				font.setChecked(false);
			}
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
	
	
	public void _URlConverCode() {
	}
	public static String convertUriToFilePath(Context context, Uri uri) {
		String filePath = "";
		if (DocumentsContract.isDocumentUri(context, uri)) {
			String docId = DocumentsContract.getDocumentId(uri);
			String[] split = docId.split(":");
			String type = split[0];
			
			if ("primary".equalsIgnoreCase(type)) {
				filePath = Environment.getExternalStorageDirectory() + "/" + split[1];
			} else if ("msf".equalsIgnoreCase(type) || "raw".equalsIgnoreCase(type)) {
				// Handle special cases
				filePath = split.length > 1 ? split[1] : docId;
			} else {
				filePath = "/storage/" + type + "/" + split[1];
			}
		} else if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = null;
			try {
				cursor = context.getContentResolver().query(uri, proj, null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					filePath = cursor.getString(column_index);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) cursor.close();
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			filePath = uri.getPath();
		}
		
		return filePath;
	}
	
	{
	}
	
}