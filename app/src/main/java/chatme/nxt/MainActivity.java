package chatme.nxt;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import  android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import java.text.Normalizer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.gms.tasks.OnFailureListener;
import androidx.annotation.NonNull;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private FloatingActionButton _fab;
	private String inbox_reff = "";
	private String fontName = "";
	private String typeace = "";
	private String currentUserId = "";
	private double position = 0;
	private HashMap<String, Object> map_search = new HashMap<>();
	private HashMap<String, Object> umap = new HashMap<>();
	private String myf = "";
	private double request_count = 0;
	private boolean useLocalFont = false;
	private String path = "";
	private boolean IsNotify = false;
	private String user2_id = "";
	private HashMap<String, Object> status = new HashMap<>();
	private String localFontPath = "";
	
	private ArrayList<HashMap<String, Object>> uses = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> searchMap = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear_top;
	private LinearLayout linear_search;
	private ProgressBar progressbar1;
	private RecyclerView recyclerview1;
	private LinearLayout linear_no_chats;
	private RelativeLayout linear_dp_background;
	private LinearLayout div1;
	private TextView textview_inbox;
	private LinearLayout linear3;
	private ImageView settings;
	private LinearLayout linear_dp;
	private LinearLayout linear4;
	private CircleImageView myprofile;
	private LinearLayout linear_count_dot;
	private TextView textview_count;
	private ImageView qr;
	private ImageView search;
	private EditText edittext1;
	private ImageView imageview_clear;
	private ImageView imageview_no_messages_icon;
	private TextView textview_no_messages_title;
	private TextView textview_no_messages_body;
	
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private DatabaseReference inbox = _firebase.getReference("inbox");
	private ChildEventListener _inbox_child_listener;
	private SharedPreferences file;
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
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private Intent a = new Intent();
	private DatabaseReference FriendRequests = _firebase.getReference("FriendRequests");
	private ChildEventListener _FriendRequests_child_listener;
	private OnCompleteListener FCM_onCompleteListener;
	private AlertDialog.Builder delete;
	private Intent not = new Intent();
	private DatabaseReference get_status = _firebase.getReference("get_status");
	private ChildEventListener _get_status_child_listener;
	private Calendar cal = Calendar.getInstance();
	private Intent af = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_fab = findViewById(R.id._fab);
		linear1 = findViewById(R.id.linear1);
		linear_top = findViewById(R.id.linear_top);
		linear_search = findViewById(R.id.linear_search);
		progressbar1 = findViewById(R.id.progressbar1);
		recyclerview1 = findViewById(R.id.recyclerview1);
		linear_no_chats = findViewById(R.id.linear_no_chats);
		linear_dp_background = findViewById(R.id.linear_dp_background);
		div1 = findViewById(R.id.div1);
		textview_inbox = findViewById(R.id.textview_inbox);
		linear3 = findViewById(R.id.linear3);
		settings = findViewById(R.id.settings);
		linear_dp = findViewById(R.id.linear_dp);
		linear4 = findViewById(R.id.linear4);
		myprofile = findViewById(R.id.myprofile);
		linear_count_dot = findViewById(R.id.linear_count_dot);
		textview_count = findViewById(R.id.textview_count);
		qr = findViewById(R.id.qr);
		search = findViewById(R.id.search);
		edittext1 = findViewById(R.id.edittext1);
		imageview_clear = findViewById(R.id.imageview_clear);
		imageview_no_messages_icon = findViewById(R.id.imageview_no_messages_icon);
		textview_no_messages_title = findViewById(R.id.textview_no_messages_title);
		textview_no_messages_body = findViewById(R.id.textview_no_messages_body);
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		fauth = FirebaseAuth.getInstance();
		net = new RequestNetwork(this);
		delete = new AlertDialog.Builder(this);
		
		linear_dp_background.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (IsNotify) {
					not.setClass(getApplicationContext(), NotificationActivity.class);
					startActivity(not);
					finish();
				} else {
					a.setClass(getApplicationContext(), ProfileActivity.class);
					a.putExtra("main", "true");
					startActivity(a);
					finish();
				}
			}
		});
		
		linear3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_ShowMyRQ(linear3);
			}
		});
		
		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_CreatePopup(settings);
			}
		});
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				_SearchListMap(uses, searchMap, "username", _charSeq);
				recyclerview1.setAdapter(new Recyclerview1Adapter(searchMap));
				if (0 < _charSeq.length()) {
					imageview_clear.setVisibility(View.VISIBLE);
				} else {
					imageview_clear.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		imageview_clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				edittext1.setText("");
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
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
						if (_childValue.containsKey("dp")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(myprofile);
						} else {
							myprofile.setImageResource(R.drawable.profile_image);
						}
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
						if (_childValue.containsKey("dp")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(myprofile);
						} else {
							myprofile.setImageResource(R.drawable.profile_image);
						}
					}
					_firebaseReffs();
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
						if (_childValue.containsKey("dp")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(myprofile);
						} else {
							myprofile.setImageResource(R.drawable.profile_image);
						}
					}
					_firebaseReffs();
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		users.addChildEventListener(_users_child_listener);
		
		_inbox_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				inbox.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						uses = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								uses.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(uses));
						if (_childValue.containsKey("timestamp")) {
							_sortListMap(uses, "timestamp", true, true);
						}
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
				inbox.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						uses = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								uses.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(uses));
						if (_childValue.containsKey("timestamp")) {
							_sortListMap(uses, "timestamp", true, true);
						}
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				inbox.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						uses = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								uses.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(uses));
						if (_childValue.containsKey("timestamp")) {
							_sortListMap(uses, "timestamp", true, true);
						}
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		inbox.addChildEventListener(_inbox_child_listener);
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_FriendRequests_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		FriendRequests.addChildEventListener(_FriendRequests_child_listener);
		
		FCM_onCompleteListener = new OnCompleteListener<InstanceIdResult>() {
			@Override
			public void onComplete(Task<InstanceIdResult> task) {
				final boolean _success = task.isSuccessful();
				final String _token = task.getResult().getToken();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				if (_success) {
					
				}
			}
		};
		
		_get_status_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		get_status.addChildEventListener(_get_status_child_listener);
		
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
				if (_success) {
					a.setClass(getApplicationContext(), RegisterActivity.class);
					startActivity(a);
					finish();
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
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
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		linear_count_dot.setVisibility(View.GONE);
		_lightStatusBar("");
		_UI("");
		_onCreateCode();
		_AnimationFab();
		_SetFont("light");
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			_setStatus("Active");
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			_setStatus("offline");
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			_setStatus("Last Seen: ".concat(new SimpleDateFormat("E, dd MMM yyyy").format(cal.getTime())));
		}
	}
	public void _lightStatusBar(final String _color) {
		getWindow().setStatusBarColor(Color.parseColor("#F5F5F5"));
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		
	}
	
	
	public void _UI(final String _color) {
		linear_dp.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFFFFFFFF));
		linear_count_dot.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFF1A3764));
		linear_search.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFFFFFFF));
		IsNotify = false;
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			//COPY USERS DTA TO INBOX
			
			// Get current user's UID
			final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
			
			// Firebase references
			final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
			final DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox");
			
			// Fetch the current user's data
			usersRef.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot myDataSnapshot) {
					if (myDataSnapshot.exists()) {
						// Store user's data to be copied
						final Map<String, Object> myData = (Map<String, Object>) myDataSnapshot.getValue();
						
						// Iterate over each user's inbox to find occurrences of myUid
						inboxRef.addListenerForSingleValueEvent(new ValueEventListener() {
							@Override
							public void onDataChange(@NonNull DataSnapshot inboxSnapshot) {
								for (DataSnapshot userInbox : inboxSnapshot.getChildren()) {
									final String userUid = userInbox.getKey();  // Marked as final
									if (userInbox.hasChild(myUid)) {
										// Get current data for this inbox entry
										inboxRef.child(userUid).child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
											@Override
											public void onDataChange(@NonNull DataSnapshot snapshot) {
												// Prepare a map to merge data
												HashMap<String, Object> mergedData = new HashMap<>();
												
												// Retain existing keys if they exist
												if (snapshot.hasChild("lastMessage")) {
													mergedData.put("lastMessage", snapshot.child("lastMessage").getValue());
												}
												if (snapshot.hasChild("timestamp")) {
													mergedData.put("timestamp", snapshot.child("timestamp").getValue());
												}
												if (snapshot.hasChild("unreadCount")) {
													mergedData.put("unreadCount", snapshot.child("unreadCount").getValue());
												}
												if (snapshot.hasChild("subimg")) {
													mergedData.put("subimg", snapshot.child("subimg").getValue());
												}
												if (snapshot.hasChild("ban")) {
													mergedData.put("ban", snapshot.child("ban").getValue());
												}
												if (snapshot.hasChild("deleted")) {
													mergedData.put("deleted", snapshot.child("deleted").getValue());
												}
												
												// Add new data
												mergedData.putAll(myData);
												
												// Update the inbox entry with merged data
												inboxRef.child(userUid).child(myUid)
												.updateChildren(mergedData)
												.addOnCompleteListener(new OnCompleteListener<Void>() {
													@Override
													public void onComplete(@NonNull Task<Void> task) {
														if (task.isSuccessful()) {
															// Successfully updated
														} else {
															SketchwareUtil.showMessage(getApplicationContext(), "Failed");
														}
													}
												});
											}
											
											@Override
											public void onCancelled(@NonNull DatabaseError error) {
												SketchwareUtil.showMessage(getApplicationContext(), "Error: " + error.getMessage());
											}
										});
									}
								}
							}
							
							@Override
							public void onCancelled(@NonNull DatabaseError databaseError) {
								SketchwareUtil.showMessage(getApplicationContext(), "Error: " + databaseError.getMessage());
							}
						});
					} else {
						//Delete user ?
						
						FirebaseAuth.getInstance().getCurrentUser().delete()
						.addOnCompleteListener(fauth_deleteUserListener);
						
						
						
						SketchwareUtil.showMessage(getApplicationContext(), "Failed to fetch your data.");
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError error) {
					SketchwareUtil.showMessage(getApplicationContext(), "Error: " + error.getMessage());
				}
			});
			
			//Starts Background Notifications!
			
			Intent intent = new Intent(MainActivity.this, notiservice.class);
			startService(intent);
			
			Glide.with(getApplicationContext()).load(Uri.parse("https://api.qrserver.com/v1/create-qr-code/?data=".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("?b=q&size=600×600")))).into(qr);
			qr.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFFFFFFF));
		}
	}
	
	
	public void _onCreateCode() {
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			inbox.removeEventListener(_inbox_child_listener);
			inbox_reff = "inbox/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
			inbox = _firebase.getReference(inbox_reff);
			inbox.addChildEventListener(_inbox_child_listener);
			_friend_requests();
			_CheckEmail_verification();
			_firebaseReffs();
			_setStatus("Active");
		} else {
			a.setClass(getApplicationContext(), LogActivity.class);
			startActivity(a);
			finish();
		}
		recyclerview1.setVisibility(View.GONE);
		linear_no_chats.setVisibility(View.GONE);
		imageview_clear.setVisibility(View.GONE);
	}
	
	
	public void _firebaseReffs() {
		currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
		DatabaseReference inboxref = FirebaseDatabase.getInstance().getReference("inbox").child(currentUserId);
		
		inboxref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (snapshot.exists() && snapshot.hasChildren()) {
					linear_no_chats.setVisibility(View.GONE);
					recyclerview1.setVisibility(View.VISIBLE);
					progressbar1.setVisibility(View.GONE);
				} else {
					linear_no_chats.setVisibility(View.VISIBLE);
					recyclerview1.setVisibility(View.GONE);
					progressbar1.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				Toast.makeText(getApplicationContext(), "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
		
		//COPY USERS DTA TO INBOX
		
		// Get current user's UID
		final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		
		// Firebase references
		final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
		final DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox");
		
		// Fetch the current user's data
		
		
		// Previous Code 
		/*
// Fetch all users from "users" node
usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot usersSnapshot) {
        final HashMap<String, HashMap<String, Object>> allUsersData = new HashMap<>();

        // Store existing users
        for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
            String userUuid = userSnapshot.getKey();
            HashMap<String, Object> userData = (HashMap<String, Object>) userSnapshot.getValue();
            if (userData != null) {
                allUsersData.put(userUuid, userData);
            }
        }

        // Check inbox data
        inboxRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot inboxSnapshot) {
                for (DataSnapshot userInbox : inboxSnapshot.getChildren()) {
                    final String userUuid = userInbox.getKey();

                    for (DataSnapshot chatEntry : userInbox.getChildren()) {
                        final String otherUserUuid = chatEntry.getKey();

                        if (allUsersData.containsKey(otherUserUuid)) {
                            // Copy only if the user exists in /users/
                            inboxRef.child(userUuid).child(otherUserUuid)
                                    .setValue(allUsersData.get(otherUserUuid));
                        } else {
                            // Delete entry if user does not exist in /users/
                            inboxRef.child(userUuid).child(otherUserUuid).removeValue();
                        }
                    }
                }
                Log.d("Firebase", "Data copied for existing users, deleted for missing users.");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Database error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onCancelled(DatabaseError error) {
        Log.e("Firebase", "Database error: " + error.getMessage());
    }
});
*/
		
		// Current user Code--
		
		// Fetch all users from "users" node
		usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot usersSnapshot) {
				final HashMap<String, HashMap<String, Object>> allUsersData = new HashMap<>();
				
				// Store existing users
				for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
					String userUuid = userSnapshot.getKey();
					HashMap<String, Object> userData = (HashMap<String, Object>) userSnapshot.getValue();
					if (userData != null) {
						allUsersData.put(userUuid, userData);
					}
				}
				
				// Check inbox data
				inboxRef.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot inboxSnapshot) {
						for (DataSnapshot userInbox : inboxSnapshot.getChildren()) {
							final String userUuid = userInbox.getKey();
							
							for (DataSnapshot chatEntry : userInbox.getChildren()) {
								final String otherUserUuid = chatEntry.getKey();
								
								if (allUsersData.containsKey(otherUserUuid)) {
									// Merge existing inbox data with new data
									inboxRef.child(userUuid).child(otherUserUuid).addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(DataSnapshot snapshot) {
											HashMap<String, Object> mergedData = new HashMap<>(allUsersData.get(otherUserUuid));
											
											// Retain existing keys if they exist
											if (snapshot.hasChild("lastMessage")) {
												mergedData.put("lastMessage", snapshot.child("lastMessage").getValue());
											}
											if (snapshot.hasChild("timestamp")) {
												mergedData.put("timestamp", snapshot.child("timestamp").getValue());
											}
											if (snapshot.hasChild("unreadCount")) {
												mergedData.put("unreadCount", snapshot.child("unreadCount").getValue());
											}
											if (snapshot.hasChild("subimg")) {
												mergedData.put("subimg", snapshot.child("subimg").getValue());
											}
											if (snapshot.hasChild("ban")) {
												mergedData.put("ban", snapshot.child("ban").getValue());
											}
											if (snapshot.hasChild("deleted")) {
												mergedData.put("deleted", snapshot.child("deleted").getValue());
											}
											
											// Update the inbox with merged data
											inboxRef.child(userUuid).child(otherUserUuid).setValue(mergedData);
										}
										
										@Override
										public void onCancelled(DatabaseError error) {
											Log.e("Firebase", "Database error: " + error.getMessage());
										}
									});
								} else {
									// Delete entry if user does not exist in /users/
									inboxRef.child(userUuid).child(otherUserUuid).removeValue();
								}
							}
						}
						Log.d("Firebase", "Data copied for existing users, deleted for missing users.");
					}
					
					@Override
					public void onCancelled(DatabaseError error) {
						Log.e("Firebase", "Database error: " + error.getMessage());
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				Log.e("Firebase", "Database error: " + error.getMessage());
			}
		});
		
		//Check user Login -- 
		
		
		final FirebaseAuth fauth = FirebaseAuth.getInstance();
		
		
		// Get current user's UID
		if (fauth.getCurrentUser() != null) {
			final String currentUserUuid = fauth.getCurrentUser().getUid();
			
			// Check if user exists in /users/
			usersRef.child(currentUserUuid).addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot snapshot) {
					if (!snapshot.exists()) {
						// User does not exist, log out and go to register activity
						fauth.signOut();
						Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(intent);
						finish();
					}
				}
				
				@Override
				public void onCancelled(DatabaseError error) {
					Log.e("Firebase", "Database error: " + error.getMessage());
				}
			});
		}
		
		//Request Notifications Counts..
		
		
		
		final DatabaseReference friendRequestRef = FirebaseDatabase.getInstance().getReference("FriendRequests");
		
		// Get current user UID
		if (fauth.getCurrentUser() != null) {
			
			
			friendRequestRef.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot snapshot) {
					int request_count = 0; // Local variable to store count
					
					if (snapshot.exists()) {
						for (DataSnapshot request : snapshot.getChildren()) {
							String status = request.child("status").getValue(String.class);
							
							// Count requests where status is NOT "accepted"
							if (status != null && !status.equals("accepted")) {
								request_count++;
								textview_count.setText(String.valueOf((long)(request_count)));
							}
						}
					}
					
					// Show dot if pending requests exist, else hide
					if (request_count > 0) {
						linear_count_dot.setVisibility(View.VISIBLE);
						
						IsNotify = true;
						
					} else {
						linear_count_dot.setVisibility(View.GONE);
						
						IsNotify = false;
					}
					
					// Show request count (modify this part as needed)
					
				}
				
				@Override
				public void onCancelled(DatabaseError error) {
					Log.e("Firebase", "Database error: " + error.getMessage());
				}
			});
		}
		
	}
	
	
	public void _SearchListMap(final ArrayList<HashMap<String, Object>> _list_map, final ArrayList<HashMap<String, Object>> _list_serach, final String _key, final String _text) {
		position = 0;
		_list_serach.clear();
		if (_text.equals("")) {
			for(int _repeat48 = 0; _repeat48 < (int)(_list_map.size()); _repeat48++) {
				map_search = _list_map.get((int)position);
				_list_serach.add(map_search);
				position++;
			}
		} else {
			try {
				for(int _repeat13 = 0; _repeat13 < (int)(_list_map.size()); _repeat13++) {
					if (_list_map.get((int)position).containsKey(_key)) {
						if (_list_map.get((int)position).get(_key).toString().toLowerCase().contains(_text.toLowerCase())) {
							map_search = _list_map.get((int)position);
							_list_serach.add(map_search);
						}
					}
					position++;
				}
			} catch (Exception e) {
				SketchwareUtil.showMessage(getApplicationContext(), "Error: "+e.toString());
			}
		}
	}
	
	
	public void _friend_requests() {
		FriendRequests.removeEventListener(_FriendRequests_child_listener);
		myf = "FriendRequests/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
		FriendRequests = _firebase.getReference(myf);
		FriendRequests.addChildEventListener(_FriendRequests_child_listener);
	}
	
	
	public void _Setcolor(final TextView _view, final String _color) {
		_view.setTextColor(Color.parseColor(_color)); // Set text color to white
		
	}
	
	
	public void _CheckEmail_verification() {
		final FirebaseAuth auth = FirebaseAuth.getInstance();
		final FirebaseUser user = fauth.getCurrentUser();
		
		if (user != null) {
			user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
				@Override
				public void onComplete(@NonNull Task<Void> task) {
					if (user.isEmailVerified()) {
						Log.d("Firebase", "Email is verified.");
						// Email is verified, proceed normally
					} else {
						Log.e("Firebase", "Email not verified! Logging out...");
						// Email is not verified, log out and go to login screen
						
						Intent intent = new Intent(getApplicationContext(), VerifyEmailActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(intent);
						finish();
					}
				}
			});
		}
		
	}
	
	
	public void _AnimationFab() {
		// Import required classes
		
		
		
		// Glow Effect (Pulse Animation)
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(textview_count, "scaleX", 1f, 1.2f, 1f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(textview_count, "scaleY", 1f, 1.2f, 1f);
		ObjectAnimator alpha = ObjectAnimator.ofFloat(textview_count, "alpha", 1f, 0.7f, 1f);
		
		scaleX.setDuration(1000);
		scaleY.setDuration(1000);
		alpha.setDuration(1000);
		
		scaleX.setRepeatCount(ValueAnimator.INFINITE);
		scaleY.setRepeatCount(ValueAnimator.INFINITE);
		alpha.setRepeatCount(ValueAnimator.INFINITE);
		
		scaleX.start();
		scaleY.start();
		alpha.start();
		
		// Floating (Up-Down Motion)
		ObjectAnimator translateY = ObjectAnimator.ofFloat(_fab, "translationY", 0, -10, 0);
		translateY.setDuration(1500);
		translateY.setRepeatCount(ValueAnimator.INFINITE);
		translateY.setInterpolator(new LinearInterpolator());
		translateY.start();
		
		// Rotation (Spin Effect on Click) using Anonymous Inner Class
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_fab.animate()
				.rotationBy(360)
				.setDuration(500)
				.setInterpolator(new LinearInterpolator())
				.start();
				a.setClass(getApplicationContext(), FriendListActivity.class);
				startActivity(a);
				
			}
		});
		
		
		// Call animateFab() inside onCreate()
		
	}
	
	
	public void _SetTimeAgo(final double _timestamp, final TextView _extra) {
		// Get the current timestamp
		Calendar timest = Calendar.getInstance();
		long currentTime = timest.getTimeInMillis();
		
		// Calculate the difference in milliseconds (cast _timestamp to long)
		long difference = currentTime - (long) _timestamp;
		
		// Determine the time format and set it directly to the TextView
		if (difference < 10000) { // Less than 10 seconds
			_extra.setText("Just now");
		} else if (difference < 60000) { // Less than 1 minute
			_extra.setText((difference / 1000) + " sec ago");
		} else if (difference < 3600000) { // Less than 1 hour
			_extra.setText((difference / 60000) + " min ago");
		} else if (difference < 86400000) { // Less than 1 day
			_extra.setText((difference / 3600000) + " hr ago");
		} else if (difference < 604800000) { // Less than 1 week
			_extra.setText((difference / 86400000) + " days ago");
		} else if (difference < 2592000000L) { // Less than 1 month
			_extra.setText((difference / 604800000) + " weeks ago");
		} else if (difference < 31536000000L) { // Less than 1 year
			_extra.setText((difference / 2592000000L) + " months ago");
		} else { // More than 1 year
			_extra.setText((difference / 31536000000L) + " years ago");
		}
		
	}
	
	
	public void _CreatePopup(final View _view) {
		/*View popupView = getLayoutInflater().inflate(R.layout.popup, null);
final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

LinearLayout bg = popupView.findViewById(R.id.bg);

LinearLayout r1 = popupView.findViewById(R.id.r1);

LinearLayout r2 = popupView.findViewById(R.id.r2);

LinearLayout r3 = popupView.findViewById(R.id.r3);

LinearLayout r4 = popupView.findViewById(R.id.r4);

//Set corners Round 
bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)36, 0xFFFFFFFF));
        
r1.setOnClickListener(new OnClickListener() { public void onClick(View view) {
        
        a.setClass(getApplicationContext(), ScannerActivity.class);
        startActivity(a);
        
        
		SketchwareUtil.showMessage(getApplicationContext(), "Scan");
		popup.dismiss();
	} });

r2.setOnClickListener(new OnClickListener() { public void onClick(View view) {
		SketchwareUtil.showMessage(getApplicationContext(), "Add Friend");
		popup.dismiss();
	} });

r3.setOnClickListener(new OnClickListener() { public void onClick(View view) {
		//settings page
        a.setClass(getApplicationContext(), SettingsActivity.class);
        startActivity(a);
        finish();

		popup.dismiss();
	} });

r4.setOnClickListener(new OnClickListener() { public void onClick(View view) {
		SketchwareUtil.showMessage(getApplicationContext(), "Quick Chat");
		popup.dismiss();
	} });

//animation style -- 
popup.setAnimationStyle(R.anim.popup_anim);

popup.showAsDropDown(_view, 0,0);

popup.setBackgroundDrawable(new BitmapDrawable());
*/
		
		// New Code:
		
		
		View popupView = getLayoutInflater().inflate(R.layout.popup, null);
		final PopupWindow popup = new PopupWindow(popupView, 
		ViewGroup.LayoutParams.WRAP_CONTENT, 
		ViewGroup.LayoutParams.WRAP_CONTENT, 
		true);
		
		// Set background for dismiss on outside touch
		popup.setBackgroundDrawable(new BitmapDrawable());
		popup.setOutsideTouchable(true);
		
		// Find layout elements
		LinearLayout bg = popupView.findViewById(R.id.bg);
		LinearLayout r1 = popupView.findViewById(R.id.r1);
		LinearLayout r2 = popupView.findViewById(R.id.r2);
		LinearLayout r3 = popupView.findViewById(R.id.r3);
		LinearLayout r4 = popupView.findViewById(R.id.r4);
		
		// Set rounded corners
		bg.setBackground(new android.graphics.drawable.GradientDrawable() {
			public android.graphics.drawable.GradientDrawable getIns(int a, int b) {
				this.setCornerRadius(a);
				this.setColor(b);
				return this;
			}
		}.getIns(36, 0xFFFFFFFF));
		
		// Button Click Listeners
		r1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				a.setClass(getApplicationContext(), ScannerActivity.class);
				startActivity(a);
				
				popup.dismiss();
			}
		});
		
		r2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				af.setClass(getApplicationContext(), FriendListActivity.class);
				startActivity(af);
				
				popup.dismiss();
			}
		});
		
		r3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				a.setClass(getApplicationContext(), SettingsActivity.class);
				startActivity(a);
				finish();
				popup.dismiss();
			}
		});
		
		r4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				af.setClass(getApplicationContext(), QuickchatActivity.class);
				startActivity(af);
				
				popup.dismiss();
			}
		});
		
		// Set initial position (above screen)
		popupView.setAlpha(0f);
		popupView.setTranslationY(-400);
		
		popup.showAsDropDown(_view, 0, 0);
		
		// Apply Slide Down Animation with AccelerateInterpolator
		popupView.animate()
		.translationY(0) // Move down
		.alpha(1f) // Fade in
		.setDuration(250) // 250ms duration
		.setInterpolator(new AccelerateInterpolator()) // Fast start, slow end
		.start();
		
	}
	
	
	public void _ShowMyRQ(final View _view) {
		View popupView = getLayoutInflater().inflate(R.layout.myqr, null);
		final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		
		LinearLayout bg = popupView.findViewById(R.id.linear1);
		ImageView qr = popupView.findViewById(R.id.image1);
		//Set corners Round 
		bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)36, 0xFFFFFFFF));
		Glide.with(getApplicationContext()).load(Uri.parse("https://api.qrserver.com/v1/create-qr-code/?data=".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("?b=q&size=600×600")))).into(qr);
		
		popup.setAnimationStyle(android.R.style.Animation_Dialog);
		
		popup.showAsDropDown(_view, 0,0);
		
		popup.setBackgroundDrawable(new BitmapDrawable());
	}
	
	
	public void _setStatus(final String _status) {
		status = new HashMap<>();
		status.put("status", _status.trim());
		get_status.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(status);
		status.clear();
	}
	
	
	public void _sortListMap(final ArrayList<HashMap<String, Object>> _maps, final String _key, final boolean _descending, final boolean _numeric) {
		
		Collections.sort(_maps, new Comparator<HashMap<String, Object>>() {
			@Override
			public int compare(HashMap<String, Object> map1, HashMap<String, Object> map2) {
				Object value1 = map1.get(_key);
				Object value2 = map2.get(_key);
				
				if (value1 == null || value2 == null) {
					return 0; // Null-safe comparison
				}
				
				if (_numeric) {
					try {
						// Use Long to handle large numbers
						long num1 = Long.parseLong(value1.toString());
						long num2 = Long.parseLong(value2.toString());
						return  _descending ? Long.compare(num2, num1) : Long.compare(num1, num2);
					} catch (NumberFormatException e) {
						return 0; // Handle invalid formats gracefully
					}
				} else {
					String str1 = value1.toString();
					String str2 = value2.toString();
					return _descending ? str2.compareTo(str1) : str1.compareTo(str2);
				}
			}
		});
		
		
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
			View _v = _inflater.inflate(R.layout.userlist, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final RelativeLayout linear_dp_bg = _view.findViewById(R.id.linear_dp_bg);
			final LinearLayout linear_text = _view.findViewById(R.id.linear_text);
			final LinearLayout linear_extra = _view.findViewById(R.id.linear_extra);
			final LinearLayout linear_dp_main = _view.findViewById(R.id.linear_dp_main);
			final LinearLayout linear_dp_sub = _view.findViewById(R.id.linear_dp_sub);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = _view.findViewById(R.id.circleimageview1);
			final de.hdodenhof.circleimageview.CircleImageView verify_icon = _view.findViewById(R.id.verify_icon);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linearSubTitle = _view.findViewById(R.id.linearSubTitle);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final ImageView name_verified_icon = _view.findViewById(R.id.name_verified_icon);
			final ImageView sub_image = _view.findViewById(R.id.sub_image);
			final TextView subtitle_text = _view.findViewById(R.id.subtitle_text);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final LinearLayout linear_message_dot = _view.findViewById(R.id.linear_message_dot);
			final TextView status = _view.findViewById(R.id.status);
			final TextView message_count = _view.findViewById(R.id.message_count);
			
			if (file.contains("cust_font")) {
				if (FileUtil.isExistFile(file.getString("font_url", ""))) {
					textview1.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					subtitle_text.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					status.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					message_count.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
				} else {
					textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medium.ttf"), 0);
					subtitle_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
					status.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
					message_count.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
				}
			} else {
				textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medium.ttf"), 0);
				subtitle_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
				status.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
				message_count.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
			}
			//Design ----
			linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)36, 0xFFFFFFFF));
			linear_dp_main.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFFF7F6F2));
			linear_message_dot.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFF1A3764));
			
			// Dp & Username Gets.....
			
			if (_data.get((int)_position).containsKey("dp")) {
				if (!"null".equals(_data.get((int)_position).get("dp").toString())) {
					if (_data.get((int) _position).containsKey("block_".concat(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
						circleimageview1.setImageResource(R.drawable.banned_user);
					} else {
						if (_data.get((int) _position).containsKey("block_".concat(_data.get((int) _position).get("id").toString()))) {
							circleimageview1.setImageResource(R.drawable.ban);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int) _position).get("dp").toString())).into(circleimageview1);
						}
					}
				}
				else {
					
					circleimageview1.setImageResource(R.drawable.sub_img);
				}
			}
			if (_data.get((int)_position).containsKey("username")) {
				if (!"".equals(_data.get((int)_position).get("username").toString())) {
					textview1.setText(_data.get((int)_position).get("username").toString());
					linear1.setVisibility(View.VISIBLE);
				}
				else {
					textview1.setText("Unknown");
					linear1.setVisibility(View.GONE);
				}
			}
			
			// Get Verified Status + Warning Status + Moree --------
			
			if (_data.get((int)_position).containsKey("verify")) {
				if ("true".equals(_data.get((int)_position).get("verify").toString())) {
					verify_icon.setVisibility(View.GONE);
					name_verified_icon.setVisibility(View.VISIBLE);
				}
				else {
					if ("admin".equals(_data.get((int)_position).get("verify").toString())) {
						verify_icon.setVisibility(View.VISIBLE);
						name_verified_icon.setVisibility(View.VISIBLE);
						verify_icon.setImageResource(R.drawable.admin);
					}
					else {
						if ("report".equals(_data.get((int)_position).get("verify").toString())) {
							verify_icon.setVisibility(View.VISIBLE);
							name_verified_icon.setVisibility(View.VISIBLE);
							verify_icon.setImageResource(R.drawable.warning);
						}
						else {
							if ("rise".equals(_data.get((int)_position).get("verify").toString())) {
								verify_icon.setVisibility(View.VISIBLE);
								name_verified_icon.setVisibility(View.VISIBLE);
								verify_icon.setImageResource(R.drawable.spark);
							}
							else {
								if ("imp".equals(_data.get((int)_position).get("verify").toString())) {
									verify_icon.setVisibility(View.VISIBLE);
									name_verified_icon.setVisibility(View.VISIBLE);
									verify_icon.setImageResource(R.drawable.star);
								}
								else {
									if ("premium".equals(_data.get((int)_position).get("verify").toString())) {
										verify_icon.setVisibility(View.VISIBLE);
										name_verified_icon.setVisibility(View.VISIBLE);
										verify_icon.setImageResource(R.drawable.premium);
									}
									else {
										if (_data.get((int)_position).containsKey("v")) {
											if ("true".equals(_data.get((int)_position).get("v").toString())) {
												verify_icon.setVisibility(View.GONE);
												name_verified_icon.setVisibility(View.VISIBLE);
											}else{
												verify_icon.setVisibility(View.GONE);
												name_verified_icon.setVisibility(View.GONE);
												
											}
										}
										else {
											verify_icon.setVisibility(View.GONE);
											name_verified_icon.setVisibility(View.GONE);
										}
									}
								}
							}
						}
					}
				}
			}else {
				verify_icon.setVisibility(View.GONE);
				name_verified_icon.setVisibility(View.GONE);
				
			}
			
			
			// extra here..
			// Unread Message Count 
			
			if (_data.get((int) _position).containsKey("unreadCount")) {
				if (0 == Double.parseDouble(_data.get((int) _position).get("unreadCount").toString())) {
					linear_message_dot.setVisibility(View.GONE);
					subtitle_text.setTextColor(0xFF9E9E9E);
				} else {
					subtitle_text.setTextColor(0xFF1A3764);
					linear_message_dot.setVisibility(View.VISIBLE);
					message_count.setText(String.valueOf((long) (Double.parseDouble(_data.get((int) _position).get("unreadCount").toString()))));
				}
			} else {
				linear_message_dot.setVisibility(View.GONE);
			}
			//timestamp
			
			if (_data.get((int) _position).containsKey("timestamp")) {
				status.setVisibility(View.VISIBLE);
				_SetTimeAgo(Double.parseDouble(_data.get((int) _position).get("timestamp").toString()), status);
			} else {
				status.setVisibility(View.GONE);
			}
			//sub image 
			if (_data.get((int) _position).containsKey("subimg")) {
				if ("null".equals(_data.get((int) _position).get("subimg").toString())) {
					sub_image.setVisibility(View.GONE);
					sub_image.setImageResource(R.drawable.logo);
				} else {
					sub_image.setVisibility(View.VISIBLE);
					sub_image.setImageResource(R.drawable.sub_img);
				}
			} else {
				sub_image.setVisibility(View.GONE);
			}
			
			//LastMessage 
			
			if (_data.get((int) _position).containsKey("lastMessage")) {
				subtitle_text.setText(_data.get((int) _position).get("lastMessage").toString());
			} else {
				if (_data.get((int) _position).containsKey("user")) {
					subtitle_text.setText("@".concat(_data.get((int) _position).get("user").toString()));
				}
			}
			
			
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
						if (_data.get((int) _position).containsKey("unreadCount")) {
							// Reset unread count logic
							final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
							final String recipientId = _data.get((int) _position).get("id").toString();
							DatabaseReference inboxRef = FirebaseDatabase.getInstance()
							.getReference("inbox")
							.child(myUid)
							.child(recipientId);
							HashMap<String, Object> updateMap = new HashMap<>();
							updateMap.put("unreadCount", 0);
							inboxRef.updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
								@Override
								public void onComplete(@NonNull Task<Void> task) {
									if (!task.isSuccessful()) {
										SketchwareUtil.showMessage(getApplicationContext(), "Failed to reset.");
									}
								}
							}).addOnFailureListener(new OnFailureListener() {
								@Override
								public void onFailure(@NonNull Exception e) {
									SketchwareUtil.showMessage(getApplicationContext(), "Error: " + e.getMessage());
								}
							});
						}
						user2_id = _data.get((int) _position).get("id").toString();
						if (_data.get((int) _position).containsKey("type")) {
							if ("user".equals(_data.get((int)_position).get("type").toString())) {
								
								a.setClass(getApplicationContext(), ChatActivity.class);
								a.putExtra("use1", FirebaseAuth.getInstance().getCurrentUser().getUid());
								a.putExtra("user2", user2_id);
								a.putExtra("user", "true");
								a.putExtra("group", "false");
								startActivity(a);
								
								
							}else{
								a.setClass(getApplicationContext(), ChatActivity.class);
								a.putExtra("use1", FirebaseAuth.getInstance().getCurrentUser().getUid());
								a.putExtra("user2", user2_id);
								a.putExtra("group", "true");
								a.putExtra("user", "false");
								startActivity(a);
								
								
							}
						}
					} else {
						SketchwareUtil.showMessage(getApplicationContext(), "Please check account details!");
					}
				}
			});
			linear1.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					delete.setTitle("Delete Chats");
					delete.setIcon(R.drawable.delete_user);
					delete.setMessage("Delete this User & Chats?");
					delete.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							// Firebase Database Reference
							DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox");
							
							// Get current user UUID
							String currentUserUUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
							
							
							// Target user ID (Replace with actual ID you want to delete)
							String targetUserID = _data.get((int)_position).get("id").toString();
							
							// Delete both paths
							inboxRef.child(currentUserUUID).child(targetUserID).removeValue();
							inboxRef.child(targetUserID).child(currentUserUUID).removeValue();
							
						}
					});
					delete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					delete.create().show();
					return true;
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