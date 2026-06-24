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
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
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
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class LogchatmeActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String reff = "";
	private String Dv = "";
	private boolean listAv = false;
	private String fontName = "";
	private String localFontPath = "";
	
	private ArrayList<HashMap<String, Object>> map = new ArrayList<>();
	
	private LinearLayout linear2;
	private LinearLayout linear1;
	private TextView textview1;
	private RecyclerView recyclerview1;
	private TextView textview2;
	private TextView textview3;
	private ProgressBar probar1;
	
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
	private DatabaseReference saved = _firebase.getReference("saved");
	private ChildEventListener _saved_child_listener;
	private Intent sa = new Intent();
	private SharedPreferences file;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.logchatme);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear2 = findViewById(R.id.linear2);
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		recyclerview1 = findViewById(R.id.recyclerview1);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);
		probar1 = findViewById(R.id.probar1);
		fauth = FirebaseAuth.getInstance();
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		
		_saved_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				saved.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								map.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(map));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		saved.addChildEventListener(_saved_child_listener);
		
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
		_lightStatusBar("");
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		textview3.setVisibility(View.GONE);
		listAv = false;
		Dv = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
		_chatrooms();
		// Firebase reference to saved/device/Dv
		DatabaseReference deviceRef = FirebaseDatabase.getInstance().getReference("saved/device").child(Dv);
		
		// Check if any child exists in saved/device/Dv
		deviceRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (!snapshot.hasChildren()) {
					// Close if no child nodes exist
					textview3.setVisibility(View.VISIBLE);
					probar1.setVisibility(View.GONE);
					
				}else{
					probar1.setVisibility(View.GONE);
					
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				SketchwareUtil.showMessage(getApplicationContext(), "Error: " + error.getMessage());
			}
		});
		
		_ProgressBarColour(probar1, "#1A3764");
		_SetFont("light");
	}
	
	@Override
	public void onBackPressed() {
		sa.setClass(getApplicationContext(), LogActivity.class);
		startActivity(sa);
		finish();
	}
	public void _chatrooms() {
		saved.removeEventListener(_saved_child_listener);
		reff = "saved/device/".concat(Dv);
		saved = _firebase.getReference(reff);
		saved.addChildEventListener(_saved_child_listener);
	}
	
	
	public void _ProgressBarColour(final ProgressBar _progressbar, final String _color) {
		if (android.os.Build.VERSION.SDK_INT >=21) {
			_progressbar.getIndeterminateDrawable() .setColorFilter(Color.parseColor(_color), PorterDuff.Mode.SRC_IN);
		}
	}
	
	
	public void _lightStatusBar(final String _color) {
		getWindow().setStatusBarColor(Color.parseColor("#F5F5F5"));
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		
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
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.saved, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView deleteImageView = _view.findViewById(R.id.deleteImageView);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = _view.findViewById(R.id.circleimageview1);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linearSubTitle = _view.findViewById(R.id.linearSubTitle);
			final TextView username = _view.findViewById(R.id.username);
			final TextView userid = _view.findViewById(R.id.userid);
			
			linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0x0D607D8B));
			if (_data.get((int)_position).containsKey("username")) {
				username.setText(_data.get((int)_position).get("username").toString());
			}
			if (_data.get((int)_position).containsKey("user")) {
				userid.setText(_data.get((int)_position).get("user").toString());
			}
			if (_data.get((int)_position).containsKey("dp")) {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("dp").toString())).into(circleimageview1);
			}
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_data.get((int)_position).containsKey("email")) {
						sa.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						sa.setClass(getApplicationContext(), LogActivity.class);
						sa.putExtra("direct", "true");
						sa.putExtra("email", _data.get((int)_position).get("email").toString());
						sa.putExtra("pass", _data.get((int)_position).get("en").toString());
						startActivity(sa);
						finish();
					} else {
						SketchwareUtil.showMessage(getApplicationContext(), "Login Failed!");
					}
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
}