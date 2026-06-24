package chatme.nxt;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.io.File;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import android.database.Cursor;
import android.provider.OpenableColumns;
import androidx.core.app.NotificationCompat;
import android.app.PendingIntent;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.content.res.Resources;

public class ChatActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private String fontName = "";
	private String typeace = "";
	private HashMap<String, Object> mo = new HashMap<>();
	private String user2 = "";
	private boolean Isgroup = false;
	private String chatroom = "";
	private String chatcopy = "";
	private String chat_key = "";
	private HashMap<String, Object> chatmap = new HashMap<>();
	private String sender = "";
	private String senderid = "";
	private boolean meAsAdmin = false;
	private String mydp = "";
	private boolean pause = false;
	private HashMap<String, Object> chat_status = new HashMap<>();
	private HashMap<String, Object> status = new HashMap<>();
	private String fileName = "";
	private String fileSize = "";
	private String filePath = "";
	private String dataPath = "";
	private BroadcastReceiver stopMusicReceiver;
	MediaPlayer mediaPlayer;
	private Handler handler = new Handler();
	private Runnable updateProgress;
	private String deletekey = "";
	private String copy = "";
	private String myUid = "";
	private String friendUid = "";
	private String localFontPath = "";
	
	private ArrayList<HashMap<String, Object>> chats = new ArrayList<>();
	private ArrayList<String> ls = new ArrayList<>();
	
	private LinearLayout toolbar;
	private LinearLayout linear_body;
	private LinearLayout linear_bottom;
	private ImageView back_img;
	private RelativeLayout linear_relative;
	private LinearLayout linear1;
	private ImageView settings;
	private LinearLayout linear_dp_image;
	private LinearLayout linear_support_dp;
	private CircleImageView circleimageview1;
	private CircleImageView verify_icon;
	private TextView username;
	private TextView status_view;
	private RecyclerView recyclerview1;
	private LinearLayout linear_message_body;
	private ImageView image_more;
	private LinearLayout msg_spc;
	private EditText message;
	private ImageView send;
	
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
	private Intent a = new Intent();
	private DatabaseReference Chat1 = _firebase.getReference("+ chatroom +");
	private ChildEventListener _Chat1_child_listener;
	private DatabaseReference Chat2 = _firebase.getReference("+ chatcopy +");
	private ChildEventListener _Chat2_child_listener;
	private Calendar timest = Calendar.getInstance();
	private DatabaseReference pending = _firebase.getReference("pending");
	private ChildEventListener _pending_child_listener;
	private StorageReference storageBuket = _firebase_storage.getReference("storageBucket");
	private OnCompleteListener<Uri> _storageBuket_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _storageBuket_download_success_listener;
	private OnSuccessListener _storageBuket_delete_success_listener;
	private OnProgressListener _storageBuket_upload_progress_listener;
	private OnProgressListener _storageBuket_download_progress_listener;
	private OnFailureListener _storageBuket_failure_listener;
	private TimerTask ts;
	private Calendar t24 = Calendar.getInstance();
	private DatabaseReference get_status = _firebase.getReference("get_status");
	private ChildEventListener _get_status_child_listener;
	private Calendar cal = Calendar.getInstance();
	private DatabaseReference inboxRef = _firebase.getReference("inbox");
	private ChildEventListener _inboxRef_child_listener;
	private DatabaseReference friendRequestRef = _firebase.getReference("FriendRequests");
	private ChildEventListener _friendRequestRef_child_listener;
	private SharedPreferences file;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.chat);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		toolbar = findViewById(R.id.toolbar);
		linear_body = findViewById(R.id.linear_body);
		linear_bottom = findViewById(R.id.linear_bottom);
		back_img = findViewById(R.id.back_img);
		linear_relative = findViewById(R.id.linear_relative);
		linear1 = findViewById(R.id.linear1);
		settings = findViewById(R.id.settings);
		linear_dp_image = findViewById(R.id.linear_dp_image);
		linear_support_dp = findViewById(R.id.linear_support_dp);
		circleimageview1 = findViewById(R.id.circleimageview1);
		verify_icon = findViewById(R.id.verify_icon);
		username = findViewById(R.id.username);
		status_view = findViewById(R.id.status_view);
		recyclerview1 = findViewById(R.id.recyclerview1);
		linear_message_body = findViewById(R.id.linear_message_body);
		image_more = findViewById(R.id.image_more);
		msg_spc = findViewById(R.id.msg_spc);
		message = findViewById(R.id.message);
		send = findViewById(R.id.send);
		fauth = FirebaseAuth.getInstance();
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		
		back_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_CreatePopup(settings);
			}
		});
		
		image_more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//dialogue style
				/*
View dialogView = getLayoutInflater().inflate(R.layout.file_picker, null);
final AlertDialog dialog = new AlertDialog.Builder(ChatActivity.this).create();
dialog.setView(dialogView);
dialog.show();

ImageView btnImage = dialogView.findViewById(R.id.btn_image);


btnImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        _PickFileWithDialogue("image/png");
        dialog.dismiss();
    }
});

*/
				//Bottom Sheet Style 2
				
				View bottomSheetView = getLayoutInflater().inflate(R.layout.file_picker, null);
				
				final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog =
				new com.google.android.material.bottomsheet.BottomSheetDialog(ChatActivity.this);
				
				bottomSheetDialog.getWindow().setBackgroundDrawable(new GradientDrawable() {{
						setColor(Color.WHITE);
						setCornerRadius(36f); // For 36dp radius
					}});
				
				bottomSheetDialog.setContentView(bottomSheetView);
				bottomSheetDialog.show();
				
				// Button handlers
				ImageView btnImage = bottomSheetView.findViewById(R.id.btn_image);
				
				btnImage.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						_PickFileWithDialogue("image/png");
						bottomSheetDialog.dismiss();
					}
				});
				
				
				
			}
		});
		
		message.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (0 < _charSeq.trim().length()) {
					send.setImageResource(R.drawable.ic_send_black);
					_AutoTrasitionImage(send, 200);
				} else {
					send.setImageResource(R.drawable.mic);
					_AutoTrasitionImage(send, 200);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (0 < message.getText().toString().trim().length()) {
					_TextMessage(message.getText().toString());
				} else {
					
				}
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (_childKey.equals(user2)) {
						if (_childValue.containsKey("dp")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(circleimageview1);
						}
						else {
							circleimageview1.setImageResource(R.drawable.profile_image);
						}
						if (_childValue.containsKey("username")) {
							username.setText(_childValue.get("username").toString());
						}
						else {
							if (_childValue.containsKey("user")) {
								username.setText(_childValue.get("user").toString());
							}
							else {
								username.setText("ChatMe User");
							}
						}
						if (_childValue.containsKey("verify")) {
							if ("true".equals(_childValue.get("verify").toString())) {
								verify_icon.setImageResource(R.drawable.verified_badge);
								verify_icon.setVisibility(View.VISIBLE);
							}
							else {
								if ("admin".equals(_childValue.get("verify").toString())) {
									verify_icon.setImageResource(R.drawable.admin);
									verify_icon.setVisibility(View.VISIBLE);
								}
								else {
									if ("report".equals(_childValue.get("verify").toString())) {
										verify_icon.setImageResource(R.drawable.reported_user);
										verify_icon.setVisibility(View.VISIBLE);
									}
									else {
										if ("rise".equals(_childValue.get("verify").toString())) {
											verify_icon.setImageResource(R.drawable.spark);
											verify_icon.setVisibility(View.VISIBLE);
										}
										else {
											if ("imp".equals(_childValue.get("verify").toString())) {
												verify_icon.setImageResource(R.drawable.star);
												verify_icon.setVisibility(View.VISIBLE);
											}
											else {
												if ("premium".equals(_childValue.get("verify").toString())) {
													verify_icon.setImageResource(R.drawable.premium);
													verify_icon.setVisibility(View.VISIBLE);
												}
												else {
													verify_icon.setVisibility(View.GONE);
												}
											}
										}
									}
								}
							}
						}else{ verify_icon.setVisibility(View.GONE); 
							
						}
						
						if (_childValue.containsKey("type")) {
							if("user".equals(_childValue.get("type").toString())) {
								
								Isgroup = false;
								
								
							}else {
								
								Isgroup = true;
								status_view.setText("Click here for Group info..");
								
							}
							
						}
						
						
						
					}
				}
				if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(_childKey)) {
					if (_childValue.containsKey("username")) {
						sender = _childValue.get("username").toString();
					}
					if (_childValue.containsKey("dp")) {
						mydp = _childValue.get("dp").toString();
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (_childKey.equals(user2)) {
						if (_childValue.containsKey("dp")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(circleimageview1);
						} else {
							circleimageview1.setImageResource(R.drawable.profile_image);
						}
						if (_childValue.containsKey("username")) {
							username.setText(_childValue.get("username").toString());
						} else {
							if (_childValue.containsKey("user")) {
								username.setText(_childValue.get("user").toString());
							} else {
								username.setText("ChatMe User");
							}
						}
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
					if (_childKey.equals(user2)) {
						if (_childValue.containsKey("dp")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(circleimageview1);
						} else {
							circleimageview1.setImageResource(R.drawable.profile_image);
						}
						if (_childValue.containsKey("username")) {
							username.setText(_childValue.get("username").toString());
						} else {
							if (_childValue.containsKey("user")) {
								username.setText(_childValue.get("user").toString());
							} else {
								username.setText("ChatMe User");
							}
						}
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
		
		_Chat1_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Chat1.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						chats = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								chats.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(chats));
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
				Chat1.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						chats = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								chats.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(chats));
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
				Chat1.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						chats = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								chats.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(chats));
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
		Chat1.addChildEventListener(_Chat1_child_listener);
		
		_Chat2_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (!pause) {  
					if (_childValue != null && _childValue.containsKey("status") && _childValue.containsKey("key")) {  
						Map<String, Object> chat_status = new HashMap<>();  
						chat_status.put("status", "seen");  
						
						Chat2.child(_childValue.get("key").toString()).updateChildren(chat_status);  
					}  
				}
				
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
		Chat2.addChildEventListener(_Chat2_child_listener);
		
		_pending_child_listener = new ChildEventListener() {
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
		pending.addChildEventListener(_pending_child_listener);
		
		_storageBuket_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_storageBuket_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_storageBuket_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				
			}
		};
		
		_storageBuket_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_storageBuket_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_storageBuket_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_get_status_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (_childKey.equals(user2)) {
						if (_childValue.containsKey("status")) {
							status_view.setText(_childValue.get("status").toString());
							if ("Active".equals(_childValue.get("status").toString())) {
								status_view.setTextColor(0xFF4CAF50);
							} else {
								if ("Offline".equals(_childValue.get("status").toString())) {
									status_view.setTextColor(0xFFF44336);
								} else {
									status_view.setTextColor(0xFF94A2B8);
								}
							}
						} else {
							status_view.setText("Active Recently..");
							status_view.setTextColor(0xFF94A2B8);
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
					if (_childKey.equals(user2)) {
						if (_childValue.containsKey("status")) {
							status_view.setText(_childValue.get("status").toString());
							if ("Active".equals(_childValue.get("status").toString())) {
								status_view.setTextColor(0xFF4CAF50);
							} else {
								if ("Offline".equals(_childValue.get("status").toString())) {
									status_view.setTextColor(0xFFF44336);
								} else {
									if ("Typing..".equals(_childValue.get("status").toString())) {
										status_view.setTextColor(0xFF54C1DD);
									} else {
										status_view.setTextColor(0xFF94A2B8);
									}
								}
							}
						} else {
							status_view.setText("Active Recently..");
							status_view.setTextColor(0xFF94A2B8);
						}
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
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		get_status.addChildEventListener(_get_status_child_listener);
		
		_inboxRef_child_listener = new ChildEventListener() {
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
		inboxRef.addChildEventListener(_inboxRef_child_listener);
		
		_friendRequestRef_child_listener = new ChildEventListener() {
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
		friendRequestRef.addChildEventListener(_friendRequestRef_child_listener);
		
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
		_SetFont("light");
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			_UI();
			if (getIntent().hasExtra("user2")) {
				user2 = getIntent().getStringExtra("user2");
				_chatrooms();
			} else {
				SketchwareUtil.showMessage(getApplicationContext(), "Sorry, Can't Chat with this user!");
				finish();
			}
			androidx.recyclerview.widget.LinearLayoutManager layoutManager=
			new androidx.recyclerview.widget.LinearLayoutManager(getApplicationContext(),androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,true);
			layoutManager.setReverseLayout(false);
			layoutManager.setStackFromEnd(true);
			recyclerview1.setLayoutManager(layoutManager);
			SwipeController controller = new SwipeController(getApplicationContext(), new ISwipeControllerActions() {
				@Override
				public void onSwipePerformed(int position) {
					
					//action here when recycler is swiped
					if (chats.get((int)position).containsKey("msg")) {
						
					}
					if (chats.get((int)position).containsKey("reply")) {
						
					}
					if (chats.get((int)position).containsKey("img")) {
						
					}
				}
			});
			androidx.recyclerview.widget.ItemTouchHelper itemTouchHelper = new androidx.recyclerview.widget.ItemTouchHelper(controller);
			itemTouchHelper.attachToRecyclerView(recyclerview1);
			_setStatus("Active");
		} else {
			SketchwareUtil.showMessage(getApplicationContext(), "Sorry, Can't Chat with this user!");
			finish();
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		if (_requestCode == 101 && _resultCode == RESULT_OK && _data != null) {
			Uri fileUri = _data.getData();
			String path = FileUtil.convertUriToFilePath(getApplicationContext(), fileUri);
			SketchwareUtil.showMessage(getApplicationContext(), "Selected: " + path);
			
			// File handling logic here
		}
		
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			_setStatus("Active");
			pause = false;
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			_setStatus("Offline");
			pause = true;
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			_setStatus("Last Seen: ".concat(new SimpleDateFormat("E, dd MMM yyyy").format(cal.getTime())));
		}
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		if (updateProgress != null) handler.removeCallbacks(updateProgress);
		
	}
	
	public void _UI() {
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		_ImageColor(send, "#ffffff");
		linear_message_body.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFFFFFFFF));
		send.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)555, 0xFF434065));
		senderid = FirebaseAuth.getInstance().getCurrentUser().getUid();
	}
	
	
	public void _ImageColor(final ImageView _image, final String _color) {
		_image.setColorFilter(Color.parseColor(_color),PorterDuff.Mode.SRC_ATOP);
	}
	
	
	public void _TransitionManager(final View _view, final double _duration) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
	}
	
	
	public void _AutoTrasitionImage(final View _view, final double _duration) {
		ImageView imageView = (ImageView) _view; // Cast to ImageView
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition();
		autoTransition.setDuration((long) _duration);
		
		android.transition.TransitionManager.beginDelayedTransition((ViewGroup) imageView.getParent(), autoTransition);
		
	}
	
	
	public void _chatrooms() {
		Chat1.removeEventListener(_Chat1_child_listener);
		Chat2.removeEventListener(_Chat2_child_listener);
		chatroom = "chat/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(getIntent().getStringExtra("user2"))));
		chatcopy = "chat/".concat(getIntent().getStringExtra("user2").concat("/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())));
		Chat1 = _firebase.getReference(chatroom);
		
		Chat2 = _firebase.getReference(chatcopy);
		Chat1.addChildEventListener(_Chat1_child_listener);
		Chat2.addChildEventListener(_Chat2_child_listener);
	}
	
	
	public void _TextMessage(final String _msg) {
		timest = Calendar.getInstance(); 
		chat_key = Chat1.push().getKey(); 
		
		// Prepare chat message map
		chatmap = new HashMap<>(); 
		chatmap.put("dp", mydp);
		chatmap.put("sender", sender); 
		chatmap.put("re", getIntent().getStringExtra("user2")); 
		chatmap.put("key", chat_key); 
		chatmap.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid()); 
		chatmap.put("msg", _msg); 
		chatmap.put("p", _msg); 
		chatmap.put("dKey","This chat does not require the Delete key!"); 
		
		if ("true".equals(getIntent().getStringExtra("group"))) {
			if (meAsAdmin) {
				chatmap.put("admin", FirebaseAuth.getInstance().getCurrentUser().getUid()); 
			} 
		}
		
		chatmap.put("status", "0s"); 
		chatmap.put("timestamp", String.valueOf((long) (timest.getTimeInMillis()))); 
		chatmap.put("senderid", FirebaseAuth.getInstance().getCurrentUser().getUid()); 
		chatmap.put("date", new SimpleDateFormat("E, MMM dd yyyy").format(timest.getTime())); 
		
		// Update message in both Chat1 and Chat2
		Chat1.child(chat_key).updateChildren(chatmap); 
		Chat2.child(chat_key).updateChildren(chatmap); 
		//here is missing code!
		pending.child(chat_key).updateChildren(chatmap); 
		
		// Update inbox for the current user under inbox/{current_user}/{user2}
		DatabaseReference currentUserInboxRef = _firebase.getReference("inbox")
		.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
		.child(getIntent().getStringExtra("user2"));
		
		HashMap<String, Object> currentUserUpdateMap = new HashMap<>();
		currentUserUpdateMap.put("lastMessage", _msg); 
		currentUserUpdateMap.put("sunimg", "null"); // Last message sent
		currentUserUpdateMap.put("timestamp", timest.getTimeInMillis()); // Timestamp
		currentUserInboxRef.updateChildren(currentUserUpdateMap);
		
		// Update inbox for the recipient under inbox/{user2}/{current_user}
		final DatabaseReference recipientInboxRef = _firebase.getReference("inbox")
		.child(getIntent().getStringExtra("user2"))
		.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		
		recipientInboxRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				long unreadCount = 0;
				if (snapshot.hasChild("unreadCount")) {
					unreadCount = snapshot.child("unreadCount").getValue(Long.class);
				}
				HashMap<String, Object> updateMap = new HashMap<>();
				updateMap.put("subimg", "null");
				updateMap.put("lastMessage", _msg); // Last message sent
				updateMap.put("timestamp", timest.getTimeInMillis()); // Timestamp
				updateMap.put("unreadCount", unreadCount + 1); // Increment unread count
				
				recipientInboxRef.updateChildren(updateMap);
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// Handle error if needed
			}
		});
		
		// Clear chat map and reset input field
		chatmap.clear(); 
		message.setText(""); 
		
		// Scroll the RecyclerView to the latest message
		ts = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (0 < chats.size()) { 
							recyclerview1.smoothScrollToPosition(chats.size() - 1); 
						}
					}
				});
			}
		};
		_timer.schedule(ts, 260);
		
	}
	
	
	public void _SwipeController() {
		//This is the swipe controller class
	}
	public class SwipeController extends androidx.recyclerview.widget.ItemTouchHelper.Callback {
		
		private Context mContext;
		private ISwipeControllerActions mSwipeControllerActions;
		
		private android.graphics.drawable.Drawable mReplyIcon;
		private android.graphics.drawable.Drawable mReplyIconBackground;
		
		private androidx.recyclerview.widget.RecyclerView.ViewHolder mCurrentViewHolder;
		private View mView;
		
		private float mDx = 0f;
		
		private float mReplyButtonProgress = 0f;
		private long  mLastReplyButtonAnimationTime = 0;
		
		private boolean mSwipeBack = false;
		private boolean mIsVibrating = false;
		private boolean mStartTracking = false;
		
		private int mBackgroundColor = 0x20606060;
		
		private int mReplyBackgroundOffset = 18;
		
		private int mReplyIconXOffset = 12;
		private int mReplyIconYOffset = 11;
		
		public SwipeController(Context context, ISwipeControllerActions swipeControllerActions){
			mContext = context;
			mSwipeControllerActions = swipeControllerActions;
			
			mReplyIcon = ContextCompat.getDrawable(mContext,R.drawable.ic_reply);
			mReplyIconBackground = androidx.core.content.res.ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_round_shape,null);
		}
		
		
		public SwipeController(Context context, ISwipeControllerActions swipeControllerActions, int replyIcon, int replyIconBackground, int backgroundColor){
			mContext = context;
			mSwipeControllerActions = swipeControllerActions;
			
			mReplyIcon = mContext.getResources().getDrawable(replyIcon);
			mReplyIconBackground = mContext.getResources().getDrawable(replyIconBackground);
			mBackgroundColor = backgroundColor;
		}
		
		@Override
		public int getMovementFlags(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
			mView = viewHolder.itemView;
			
			return androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags(androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE, androidx.recyclerview.widget.ItemTouchHelper.LEFT);
		}
		
		@Override
		public boolean onMove(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder1) {
			return false;
		}
		
		@Override
		public void onSwiped(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int i) {
			
		}
		
		@Override
		public int convertToAbsoluteDirection(int flags, int layoutDirection){
			if (mSwipeBack){
				mSwipeBack = false;
				return 0;
			}
			return super.convertToAbsoluteDirection(flags, layoutDirection);
		}
		
		@Override
		public void onChildDraw(@androidx.annotation.NonNull Canvas c, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
			if (actionState == androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE){
				setTouchListener(recyclerView, viewHolder);
			}
			if (mView.getTranslationX() < convertToDp(130) || dX > mDx ){
				
				int width = mContext.getResources().getDisplayMetrics().widthPixels;
				float widthCenter = (width / 2) - 170;
				float offset = dX / width;
				
				float newX = widthCenter * offset;
				super.onChildDraw(c, recyclerView, viewHolder, newX, dY, actionState, isCurrentlyActive);
				mDx = newX;
				mStartTracking = true;
			}
			mCurrentViewHolder = viewHolder;
			drawReplyButton(c);
		}
		
		private void setTouchListener(androidx.recyclerview.widget.RecyclerView recyclerView, final androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder){
			recyclerView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
						mSwipeBack = true;
					} else {
						mSwipeBack = false;
					}
					if (mSwipeBack) {
						if (Math.abs(mView.getTranslationX()) >= convertToDp(35)) {
							mSwipeControllerActions.onSwipePerformed(viewHolder.getAdapterPosition());
						}
					}
					return false;
				}
			});
		}
		
		// private int convertToDp(int pixels){
		//      return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, getResources().getDisplayMetrics());
		//  }
		
		private int convertToDp(int pixels){
			return (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP,
			pixels,
			mContext.getResources().getDisplayMetrics()
			);
		}
		
		
		private void drawReplyButton(Canvas canvas){
			
			float width = mContext.getResources().getDisplayMetrics().widthPixels;
			if (mCurrentViewHolder == null){
				return;
			}
			
			float translationX = -mView.getTranslationX();
			
			long newTime = System.currentTimeMillis();
			long dt = Math.min(17, newTime - mLastReplyButtonAnimationTime);
			mLastReplyButtonAnimationTime = newTime;
			boolean showing = false;
			if (translationX >= convertToDp(10)){
				showing = true;
			}
			if (showing){
				if (mReplyButtonProgress < 1.0f){
					mReplyButtonProgress += dt / 180.0f;
					if (mReplyButtonProgress > 1.0f){
						mReplyButtonProgress = 1.0f;
					} else {
						mView.invalidate();
					}
				}
			} else if (translationX <= 0.0f){
				mReplyButtonProgress = 0f;
				mStartTracking = false;
				mIsVibrating = false;
			} else {
				if (mReplyButtonProgress > 0.0f){
					mReplyButtonProgress -= dt / 180.0f;
					if (mReplyButtonProgress < 0.1f){
						mReplyButtonProgress = 0f;
					}
				}
				mView.invalidate();
			}
			int alpha;
			float scale;
			if (showing){
				if (mReplyButtonProgress <= 0.8f){
					scale = 1.2f * (mReplyButtonProgress / 0.8f);
				} else{
					scale = 1.2f - 0.2f * ((mReplyButtonProgress - 0.8f) / 0.2f);
				}
				alpha = Math.min(255, 255 * ((int)(mReplyButtonProgress / 0.8f)));
			} else{
				scale = mReplyButtonProgress;
				alpha = Math.min(255, 255 * (int)mReplyButtonProgress);
			}
			mReplyIconBackground.setAlpha(alpha);
			mReplyIcon.setAlpha(alpha);
			if (mStartTracking){
				if (!mIsVibrating && -mView.getTranslationX() >= convertToDp(6)){
					mView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
				}
				mIsVibrating = true;
			}
			
			int x;
			float y;
			//        if (-mView.getTranslationX() > convertToDp(30)){
			//            x = width - ((int)-mView.getTranslationX()) + 40;
			//        }else{
			//            x = (int)mView.getTranslationX() + 40;
			//        }
			x = Math.round(width + mView.getTranslationX() + 40);
			
			y = mView.getTop() + ((float) mView.getMeasuredHeight() /2);
			mReplyIconBackground.setColorFilter(mBackgroundColor, PorterDuff.Mode.MULTIPLY);
			
			mReplyIconBackground.setBounds(new Rect(
			(int)(x - convertToDp(mReplyBackgroundOffset) * scale),
			(int)(y - convertToDp(mReplyBackgroundOffset) * scale),
			(int)(x + convertToDp(mReplyBackgroundOffset) * scale),
			(int)(y + convertToDp(mReplyBackgroundOffset) * scale)
			));
			mReplyIconBackground.draw(canvas);
			
			mReplyIcon.setBounds(new Rect(
			(int)(x - convertToDp(mReplyIconXOffset) * scale),
			(int)(y - convertToDp(mReplyIconYOffset) * scale),
			(int)(x + convertToDp(mReplyIconXOffset) * scale),
			(int)(y + convertToDp(mReplyIconYOffset) * scale)
			));
			mReplyIcon.draw(canvas);
			
			mReplyIconBackground.setAlpha(255);
			mReplyIcon.setAlpha(255);
		}
	}
	
	
	public void _SwipeInterface() {
		//swipe actions interface
	}
	public interface ISwipeControllerActions {
		
		void onSwipePerformed(int position);
	}
	
	
	public void _setTime(final double _currentTime, final TextView _txt) {
		timest.setTimeInMillis((long)(_currentTime));
		_txt.setText(new SimpleDateFormat("hh:mm").format(timest.getTime()));
	}
	
	
	public void _CreatePopup(final View _view) {
		myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		friendUid = getIntent().getStringExtra("user2");
		View popupView = getLayoutInflater().inflate(R.layout.chatpopup, null);
		final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		
		LinearLayout bg = popupView.findViewById(R.id.bg);
		
		LinearLayout r1 = popupView.findViewById(R.id.r1);
		
		LinearLayout r2 = popupView.findViewById(R.id.r2);
		
		final LinearLayout r3 = popupView.findViewById(R.id.r3);
		
		LinearLayout r4 = popupView.findViewById(R.id.r4);
		
		//Set corners Round 
		bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)36, 0xFFFFFFFF));
		
		r2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Build a confirmation dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
				builder.setTitle("Remove Friend");
				builder.setMessage("Are you sure you want to remove this friend? This action cannot be undone.");
				builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// User confirmed – proceed with removal
						performRemoveFriend();
						popup.dismiss(); // dismiss the popup after removal starts
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// User cancelled – just close dialog, popup stays open
						dialog.dismiss();
					}
				});
				builder.show();
			}
		});
		
		
		
		r1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Show confirmation dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
				builder.setTitle("Clear Chat");
				builder.setMessage("Delete all messages in this conversation?");
				builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// User confirmed → clear chat
						performClearChat();
						popup.dismiss(); // dismiss the popup
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss(); // just close dialog
					}
				});
				builder.show();
			}
		});
		
		
		
		r3.setOnClickListener(new OnClickListener() { public void onClick(View view) {
				//QR page
				_ShowMyRQ(r3);
				
				popup.dismiss();
			} });
		
		r4.setOnClickListener(new OnClickListener() { public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Quick Chat");
				popup.dismiss();
			} });
		
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
	
	
	public void _PlayMusic(final String _url) {
		//old code 
		
		/*
final com.google.android.material.bottomsheet.BottomSheetDialog bottom_sheetD = new com.google.android.material.bottomsheet.BottomSheetDialog(ChatActivity.this);
View bottom_sheetV = getLayoutInflater().inflate(R.layout.voice, null);
bottom_sheetD.setContentView(bottom_sheetV);

// Views
final TextView music_title = (TextView) bottom_sheetV.findViewById(R.id.music_title);
final Button play_pause_btn = (Button) bottom_sheetV.findViewById(R.id.play_pause_btn);

// Prepare waveform view
ViewGroup holder = (ViewGroup) bottom_sheetV.findViewById(R.id.wave_holder);
holder.removeAllViews();

final WaveformView waveform = new WaveformView(ChatActivity.this);

int heightPx = (int) SketchwareUtil.getDip(getApplicationContext(), 80);
LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
    LinearLayout.LayoutParams.MATCH_PARENT,
    heightPx
);
waveform.setLayoutParams(params);
waveform.setVisibility(View.INVISIBLE); // Initially hidden
holder.addView(waveform);

// MediaPlayer
final MediaPlayer mediaPlayer = new MediaPlayer();
final String streamUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"; // your streaming URL

bottom_sheetD.setCancelable(false);
bottom_sheetD.show();

play_pause_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View _view) {
        if (!mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.reset(); // reset before setting new source
                mediaPlayer.setDataSource(streamUrl);
                mediaPlayer.prepareAsync();
                waveform.setVisibility(View.VISIBLE);
                play_pause_btn.setText("Loading...");
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        play_pause_btn.setText("Pause");
                    }
                });
            } catch (Exception e) {
                SketchwareUtil.showMessage(getApplicationContext(), "Error: " + e.getMessage());
            }
        } else {
            mediaPlayer.pause();
            play_pause_btn.setText("Play");
            waveform.setVisibility(View.INVISIBLE);
        }
    }
});

bottom_sheetD.setOnDismissListener(new DialogInterface.OnDismissListener() {
    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }
});

final com.google.android.material.bottomsheet.BottomSheetDialog bottom_sheetD = new com.google.android.material.bottomsheet.BottomSheetDialog(ChatActivity.this);
View bottom_sheetV = getLayoutInflater().inflate(R.layout.voice, null);
bottom_sheetD.setContentView(bottom_sheetV);

// Views
final TextView music_title = (TextView) bottom_sheetV.findViewById(R.id.music_title);
final Button play_pause_btn = (Button) bottom_sheetV.findViewById(R.id.play_pause_btn);
final ViewGroup container = (ViewGroup) bottom_sheetV.findViewById(R.id.wave_holder);
final LinearLayout music_controls = (LinearLayout) bottom_sheetV.findViewById(R.id.music_controls);

music_controls.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)36, 0xFFFFFFFF));

// MediaPlayer
final MediaPlayer mediaPlayer = new MediaPlayer();
final String streamUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";

// Fix type issues
final int barWidth = (int) SketchwareUtil.getDip(getApplicationContext(), 8);
final int barHeight = (int) SketchwareUtil.getDip(getApplicationContext(), 50);
final int margin = (int) SketchwareUtil.getDip(getApplicationContext(), 4);

// Initialize bar views
final ArrayList<View> bars = new ArrayList<>();
container.removeAllViews();

int barCount = 5;

for (int i = 0; i < barCount; i++) {
    View bar = new View(ChatActivity.this);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(barWidth, barHeight);
    params.setMargins(margin, margin, margin, margin);
    bar.setLayoutParams(params);

    GradientDrawable drawable = new GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        new int[]{Color.parseColor("#D48DF3"), Color.parseColor("#8E90FF")}
    );
    drawable.setCornerRadius(barWidth);
    bar.setBackgroundDrawable(drawable);

    bar.setScaleY(1f);
    bar.setPivotY(barHeight);
    bar.setVisibility(View.INVISIBLE);

    container.addView(bar);
    bars.add(bar);
}

// Bar animation
final Handler animHandler = new Handler();
final Runnable[] animateBars = new Runnable[1];
animateBars[0] = new Runnable() {
    final Random rand = new Random();
    @Override
    public void run() {
        for (View bar : bars) {
            float scale = 0.5f + rand.nextFloat(); // Scale between 0.5 and 1.5
            bar.animate().scaleY(scale).setDuration(200).start();
        }
        animHandler.postDelayed(this, 200);
    }
};

// Play/Pause button logic
play_pause_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View _view) {
        if (!mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.setDataSource(streamUrl);
                mediaPlayer.prepareAsync();
                play_pause_btn.setText("Loading...");
                for (View bar : bars) bar.setVisibility(View.VISIBLE);

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        animHandler.post(animateBars[0]);
                        play_pause_btn.setText("Pause");
                    }
                });
            } catch (Exception e) {
                SketchwareUtil.showMessage(getApplicationContext(), "Error: " + e.getMessage());
            }
        } else {
            mediaPlayer.pause();
            play_pause_btn.setText("Play");
            for (View bar : bars) bar.setVisibility(View.INVISIBLE);
        }
    }
});

// Stop everything on close
bottom_sheetD.setOnDismissListener(new DialogInterface.OnDismissListener() {
    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        mediaPlayer.release();
        animHandler.removeCallbacks(animateBars[0]);
    }
});

bottom_sheetD.setCancelable(false);
bottom_sheetD.show();

final com.google.android.material.bottomsheet.BottomSheetDialog bottom_sheetD = new com.google.android.material.bottomsheet.BottomSheetDialog(ChatActivity.this);
View bottom_sheetV = getLayoutInflater().inflate(R.layout.voice, null);
bottom_sheetD.setContentView(bottom_sheetV);

// Views
final TextView music_title = (TextView) bottom_sheetV.findViewById(R.id.music_title);
final Button play_pause_btn = (Button) bottom_sheetV.findViewById(R.id.play_pause_btn);
final ViewGroup container = (ViewGroup) bottom_sheetV.findViewById(R.id.wave_holder);
final LinearLayout music_controls = (LinearLayout) bottom_sheetV.findViewById(R.id.music_controls);

// Set rounded background with 36dp corner radius
music_controls.setBackground(new GradientDrawable() {{
	setCornerRadius(SketchwareUtil.getDip(getApplicationContext(), 36));
	setColor(Color.WHITE);
}});

// MediaPlayer
final MediaPlayer mediaPlayer = new MediaPlayer();
final String streamUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";

// Bar dimensions
final int barWidth = (int) SketchwareUtil.getDip(getApplicationContext(), 8);
final int barHeight = (int) SketchwareUtil.getDip(getApplicationContext(), 50);
final int margin = (int) SketchwareUtil.getDip(getApplicationContext(), 4);

// Initialize bar views
final ArrayList<View> bars = new ArrayList<>();
container.removeAllViews();

int barCount = 5;

for (int i = 0; i < barCount; i++) {
    View bar = new View(ChatActivity.this);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(barWidth, barHeight);
    params.setMargins(margin, margin, margin, margin);
    bar.setLayoutParams(params);

    GradientDrawable drawable = new GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        new int[]{Color.parseColor("#D48DF3"), Color.parseColor("#8E90FF")}
    );
    drawable.setCornerRadius(barWidth);
    bar.setBackgroundDrawable(drawable);

    bar.setScaleY(1f);
    bar.setPivotY(barHeight);
    bar.setVisibility(View.INVISIBLE);

    container.addView(bar);
    bars.add(bar);
}

// Bar animation
final Handler animHandler = new Handler();
final Runnable[] animateBars = new Runnable[1];
animateBars[0] = new Runnable() {
    final Random rand = new Random();
    @Override
    public void run() {
        for (View bar : bars) {
            float scale = 0.5f + rand.nextFloat(); // Scale between 0.5 and 1.5
            bar.animate().scaleY(scale).setDuration(200).start();
        }
        animHandler.postDelayed(this, 200);
    }
};

// Play/Pause logic
play_pause_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View _view) {
        if (!mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.setDataSource(streamUrl);
                mediaPlayer.prepareAsync();
                play_pause_btn.setText("Loading...");
                for (View bar : bars) bar.setVisibility(View.VISIBLE);

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        animHandler.post(animateBars[0]);
                        play_pause_btn.setText("Pause");
                    }
                });
            } catch (Exception e) {
                SketchwareUtil.showMessage(getApplicationContext(), "Error: " + e.getMessage());
            }
        } else {
            mediaPlayer.pause();
            play_pause_btn.setText("Play");
            for (View bar : bars) bar.setVisibility(View.INVISIBLE);
        }
    }
});

// Stop on close
bottom_sheetD.setOnDismissListener(new DialogInterface.OnDismissListener() {
    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        mediaPlayer.release();
        animHandler.removeCallbacks(animateBars[0]);
    }
});

bottom_sheetD.setCancelable(false);
bottom_sheetD.show();

final com.google.android.material.bottomsheet.BottomSheetDialog bottom_sheetD = new com.google.android.material.bottomsheet.BottomSheetDialog(ChatActivity.this);
View bottom_sheetV = getLayoutInflater().inflate(R.layout.voice, null);
bottom_sheetD.setContentView(bottom_sheetV);

// Views
final TextView music_title = (TextView) bottom_sheetV.findViewById(R.id.music_title);
final Button play_pause_btn = (Button) bottom_sheetV.findViewById(R.id.play_pause_btn);
final LinearLayout container = (LinearLayout) bottom_sheetV.findViewById(R.id.wave_holder);
final LinearLayout music_controls = (LinearLayout) bottom_sheetV.findViewById(R.id.music_controls);
final ImageView close = (ImageView) bottom_sheetV.findViewById(R.id.close);

// Rounded background
GradientDrawable bg = new GradientDrawable();
bg.setColor(Color.WHITE);
bg.setCornerRadius(SketchwareUtil.getDip(getApplicationContext(), 36));
music_controls.setBackground(bg);

// MediaPlayer
final MediaPlayer mediaPlayer = new MediaPlayer();
final String streamUrl = _url; //"https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";

// Wave bar sizes
final int barWidth = (int) SketchwareUtil.getDip(getApplicationContext(), 8);
final int barHeight = (int) SketchwareUtil.getDip(getApplicationContext(), 50);
final int margin = (int) SketchwareUtil.getDip(getApplicationContext(), 4);

// Center bars
container.setGravity(Gravity.CENTER_HORIZONTAL);
container.setOrientation(LinearLayout.HORIZONTAL);

// Create wave bars
final ArrayList<View> bars = new ArrayList<>();
container.removeAllViews();

int barCount = 5;
for (int i = 0; i < barCount; i++) {
    View bar = new View(ChatActivity.this);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(barWidth, barHeight);
    params.setMargins(margin, margin, margin, margin);
    bar.setLayoutParams(params);

    GradientDrawable drawable = new GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        new int[]{Color.parseColor("#D48DF3"), Color.parseColor("#8E90FF")}
    );
    drawable.setCornerRadius(barWidth);
    bar.setBackground(drawable);

    bar.setScaleY(1f);
    bar.setPivotY(barHeight);
    bar.setVisibility(View.INVISIBLE);

    container.addView(bar);
    bars.add(bar);
}

// Animate bars
final Handler animHandler = new Handler();
final Runnable[] animateBars = new Runnable[1];
animateBars[0] = new Runnable() {
    final Random rand = new Random();
    @Override
    public void run() {
        for (View bar : bars) {
            float scale = 0.5f + rand.nextFloat(); // between 0.5 and 1.5
            bar.animate().scaleY(scale).setDuration(200).start();
        }
        animHandler.postDelayed(this, 200);
    }
};

//close Button
close.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View _view) {
bottom_sheetD.dismiss(); // dismiss sheet on stop
            _removeMusicNotification();

    }
});

// Play/pause button
play_pause_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View _view) {
        if (!mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.setDataSource(streamUrl);
                mediaPlayer.prepareAsync();
                play_pause_btn.setText("Loading...");

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        for (View bar : bars) bar.setVisibility(View.VISIBLE);
                        animHandler.post(animateBars[0]);
                        play_pause_btn.setText("Stop");
                        _showMusicNotification();
                    }
                });
            } catch (Exception e) {
                SketchwareUtil.showMessage(getApplicationContext(), "Error: " + e.getMessage());
            }
        } else {
            // Stop playing
            mediaPlayer.stop();
            mediaPlayer.reset(); // allow reuse
            for (View bar : bars) bar.setVisibility(View.INVISIBLE);
            animHandler.removeCallbacks(animateBars[0]);
            bottom_sheetD.dismiss(); // dismiss sheet on stop
            _removeMusicNotification();
        }
    }
});

// On dismiss
bottom_sheetD.setOnDismissListener(new DialogInterface.OnDismissListener() {
    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        mediaPlayer.release();
        animHandler.removeCallbacks(animateBars[0]);
    }
});

bottom_sheetD.setCancelable(false);
bottom_sheetD.show();

MediaPlayer mediaPlayer;


	if (mediaPlayer != null) {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
		mediaPlayer.release();
		mediaPlayer = null;
	}

	mediaPlayer = new MediaPlayer();
	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

	try {
		mediaPlayer.setDataSource(url);
		mediaPlayer.prepareAsync();

		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
			}
		});

		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				mediaPlayer = null;

				// Set image back to "mic"
				imageView.setImageResource(R.drawable.mic);

				Toast.makeText(ChatActivity.this, "Music finished", Toast.LENGTH_SHORT).show();
			}
		});
	} catch (IOException e) {
		e.printStackTrace();
		Toast.makeText(ChatActivity.this, "Failed to play", Toast.LENGTH_SHORT).show();
	}


*/
	}
	
	
	public void _showMusicNotification() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(
			"music_channel_id",
			"Music Playback",
			NotificationManager.IMPORTANCE_LOW
			);
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			manager.createNotificationChannel(channel);
		}
		
		// Intent for Stop Button in Notification
		Intent stopIntent = new Intent("ACTION_STOP_MUSIC");
		PendingIntent stopPendingIntent = PendingIntent.getBroadcast(
		getApplicationContext(),
		0,
		stopIntent,
		PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
		);
		
		// Main PendingIntent to open app
		Intent intent = new Intent(ChatActivity.this, ChatActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(
		ChatActivity.this,
		0,
		intent,
		PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
		);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(ChatActivity.this, "music_channel_id")
		.setContentTitle("Now Playing")
		.setContentText("Streaming Music")
		.setSmallIcon(android.R.drawable.ic_media_play) // fallback icon
		.addAction(android.R.drawable.ic_media_pause, "Stop", stopPendingIntent) // system stop icon
		.setContentIntent(pendingIntent)
		.setOngoing(true)
		.setPriority(NotificationCompat.PRIORITY_LOW);
		
		NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notifyManager.notify(1001, builder.build());
		
	}
	
	
	public void _removeMusicNotification() {
		
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(1001);
		
		
	}
	
	
	public void _CodeForPlayVoice() {
	}
	
	/*
private void _PlayMusic2(String url, final ImageView imageView, final ProgressBar probar1) {
	if (mediaPlayer != null) {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
		mediaPlayer.release();
		mediaPlayer = null;
	}

	mediaPlayer = new MediaPlayer();
	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

	try {
		mediaPlayer.setDataSource(url);
		mediaPlayer.prepareAsync();

		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();

				// Set max value of progress bar
				probar1.setMax(mp.getDuration());

				// Start updating progress
				updateProgress = new Runnable() {
					@Override
					public void run() {
						if (mediaPlayer != null && mediaPlayer.isPlaying()) {
							probar1.setProgress(mediaPlayer.getCurrentPosition());
							handler.postDelayed(this, 500); // Update every 0.5 sec
                           imageView.setImageResource(R.drawable.pause);
						}
					}
				};
				handler.post(updateProgress);
			}
		});

		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				mediaPlayer = null;

				imageView.setImageResource(R.drawable.mic);
				probar1.setProgress(0); // Reset progress

				if (updateProgress != null) {
					handler.removeCallbacks(updateProgress);
				}
			}
		});
	} catch (IOException e) {
		e.printStackTrace();
		Toast.makeText(ChatActivity.this, "Failed to play", Toast.LENGTH_SHORT).show();
	}
}

//old Code to Play
private boolean isPaused = false;

private ImageView currentImageView;
private ProgressBar currentProgressBar;

private void _PlayMusic2(final String url, final ImageView imageView, final ProgressBar probar1) {
    if (mediaPlayer != null) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
            imageView.setImageResource(R.drawable.play2); // Paused icon
            return;
        } else if (isPaused) {
            mediaPlayer.start();
            isPaused = false;
            imageView.setImageResource(R.drawable.pause); // Resume icon
            handler.post(updateProgress);
            return;
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Reset previous UI
        if (currentImageView != null) currentImageView.setImageResource(R.drawable.mic);
        if (currentProgressBar != null) currentProgressBar.setProgress(0);
        if (updateProgress != null) handler.removeCallbacks(updateProgress);
    }

    // Save current views
    currentImageView = imageView;
    currentProgressBar = probar1;
    isPaused = false;

    mediaPlayer = new MediaPlayer();
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    try {
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepareAsync();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                imageView.setImageResource(R.drawable.pause); // Playing icon
                probar1.setMax(mp.getDuration());

                updateProgress = new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            probar1.setProgress(mediaPlayer.getCurrentPosition());
                            handler.postDelayed(this, 500);
                        }
                    }
                };
                handler.post(updateProgress);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mediaPlayer = null;
                isPaused = false;

                imageView.setImageResource(R.drawable.mic); // End icon
                probar1.setProgress(0);

                if (updateProgress != null) handler.removeCallbacks(updateProgress);
            }
        });
    } catch (IOException e) {
        e.printStackTrace();
        Toast.makeText(ChatActivity.this, "Failed to play", Toast.LENGTH_SHORT).show();
    }
}

*/
	private boolean isPaused = false;
	private ImageView currentImageView;
	private ProgressBar currentProgressBar;
	
	
	private void _PlayMusic2(final String url, final ImageView imageView, final ProgressBar probar1) {
		if (mediaPlayer != null) {
			if (currentImageView != null && currentImageView != imageView) {
				currentImageView.setImageResource(R.drawable.mic);
			}
			
			if (currentProgressBar != null && currentProgressBar != probar1) {
				currentProgressBar.setProgress(0);
			}
			
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
				mediaPlayer.release();
				mediaPlayer = null;
				isPaused = false;
				
				if (updateProgress != null) {
					handler.removeCallbacks(updateProgress);
				}
			} else if (isPaused && currentImageView == imageView) {
				mediaPlayer.start();
				isPaused = false;
				imageView.setImageResource(R.drawable.pause);
				handler.post(updateProgress);
				return;
			}
		}
		
		currentImageView = imageView;
		currentProgressBar = probar1;
		isPaused = false;
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		try {
			mediaPlayer.setDataSource(url);
			mediaPlayer.prepareAsync();
			
			mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					mp.start();
					imageView.setImageResource(R.drawable.pause);
					probar1.setMax(mp.getDuration());
					
					probar1.setVisibility(View.VISIBLE);
					
					updateProgress = new Runnable() {
						@Override
						public void run() {
							if (mediaPlayer != null && mediaPlayer.isPlaying()) {
								probar1.setProgress(mediaPlayer.getCurrentPosition());
								handler.postDelayed(this, 500);
							}
						}
					};
					handler.post(updateProgress);
				}
			});
			
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
					mediaPlayer = null;
					isPaused = false;
					
					imageView.setImageResource(R.drawable.mic);
					probar1.setProgress(0);
					
					probar1.setVisibility(View.GONE);
					
					if (updateProgress != null) {
						handler.removeCallbacks(updateProgress);
					}
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(ChatActivity.this, "Failed to play", Toast.LENGTH_SHORT).show();
		}
	}
	
	{
	}
	
	
	public void _ShowPop2(final View _view) {
		/* don't change anything!!
View popupView = getLayoutInflater().inflate(R.layout.popshow, null);
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
ImageView imageDelete = popupView.findViewById(R.id.image_delete);

// Set rounded background
bg.setBackground(new android.graphics.drawable.GradientDrawable() {
    public android.graphics.drawable.GradientDrawable getIns(int a, int b) {
        this.setCornerRadius(a);
        this.setColor(b);
        return this;
    }
}.getIns(36, 0xFFFFFFFF));

// Set color filter to image_delete
imageDelete.setColorFilter(Color.parseColor("#FF4444"), PorterDuff.Mode.SRC_IN);

// Button Click Listeners
r1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        
        SketchwareUtil.showMessage(getApplicationContext(), "Scan");
        popup.dismiss();
    }
});

r2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SketchwareUtil.showMessage(getApplicationContext(), "Add Friend");
        popup.dismiss();
    }
});

r3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        
        popup.dismiss();
    }
});

r4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        
        popup.dismiss();
    }
});

// Set default popup animation
popup.setAnimationStyle(android.R.style.Animation_Dialog);

// Show the popup
popup.showAsDropDown(_view, 0, 0);

*/
		
		View popupView = getLayoutInflater().inflate(R.layout.popshow, null);
		final PopupWindow popup = new PopupWindow(popupView,
		ViewGroup.LayoutParams.WRAP_CONTENT,
		ViewGroup.LayoutParams.WRAP_CONTENT,
		true);
		
		popup.setBackgroundDrawable(new BitmapDrawable());
		popup.setOutsideTouchable(true);
		
		LinearLayout bg = popupView.findViewById(R.id.bg);
		LinearLayout r1 = popupView.findViewById(R.id.r1);
		LinearLayout r2 = popupView.findViewById(R.id.r2);
		LinearLayout r3 = popupView.findViewById(R.id.r3);
		LinearLayout r4 = popupView.findViewById(R.id.r4);
		ImageView imageDelete = popupView.findViewById(R.id.image_delete);
		
		// Set rounded background
		bg.setBackground(new android.graphics.drawable.GradientDrawable() {
			public android.graphics.drawable.GradientDrawable getIns(int a, int b) {
				this.setCornerRadius(a);
				this.setColor(b);
				return this;
			}
		}.getIns(36, 0xFFFFFFFF));
		
		// Set delete icon color
		imageDelete.setColorFilter(Color.parseColor("#FF4444"), PorterDuff.Mode.SRC_IN);
		
		// Button click listeners
		r1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Scan");
				popup.dismiss();
			}
		});
		
		r2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Add Friend");
				popup.dismiss();
			}
		});
		
		r3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				popup.dismiss();
			}
		});
		
		r4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				popup.dismiss();
			}
		});
		
		popup.setAnimationStyle(android.R.style.Animation_Dialog);
		
		// Get view position and screen height
		int[] location = new int[2];
		_view.getLocationOnScreen(location);
		int yPosition = location[1];
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenHeight = displayMetrics.heightPixels;
		
		popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int popupHeight = popupView.getMeasuredHeight();
		
		if (yPosition + _view.getHeight() + popupHeight > screenHeight) {
			popup.showAtLocation(_view, Gravity.NO_GRAVITY,
			location[0], yPosition - popupHeight);
		} else {
			popup.showAsDropDown(_view, 0, 0);
		}
		
		
	}
	
	
	public void _ShowPopCode() {
	}
	private final int[] touchX = new int[1];
	private final int[] touchY = new int[1];
	
	private void _ShowPopupAtPosition(View _view, int x, int y, String deletekey) {
		View popupView = getLayoutInflater().inflate(R.layout.popshow, null);
		final PopupWindow popup = new PopupWindow(popupView,
		ViewGroup.LayoutParams.WRAP_CONTENT,
		ViewGroup.LayoutParams.WRAP_CONTENT,
		true);
		
		popup.setBackgroundDrawable(new BitmapDrawable());
		popup.setOutsideTouchable(true);
		popup.setAnimationStyle(android.R.style.Animation_Dialog);
		
		// Find elements
		LinearLayout bg = popupView.findViewById(R.id.bg);
		LinearLayout r1 = popupView.findViewById(R.id.r1);
		LinearLayout r2 = popupView.findViewById(R.id.r2);
		LinearLayout r3 = popupView.findViewById(R.id.r3);
		LinearLayout r4 = popupView.findViewById(R.id.r4);
		ImageView imageDelete = popupView.findViewById(R.id.image_delete);
		
		// Rounded background
		bg.setBackground(new android.graphics.drawable.GradientDrawable() {
			public android.graphics.drawable.GradientDrawable getIns(int a, int b) {
				this.setCornerRadius(a);
				this.setColor(b);
				return this;
			}
		}.getIns(36, 0xFFFFFFFF));
		
		// Delete icon color
		imageDelete.setColorFilter(Color.parseColor("#FF4444"), PorterDuff.Mode.SRC_IN);
		
		// Set visibility based on deletekey
		String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		if (deletekey.equals(currentUid)) {
			r1.setVisibility(View.VISIBLE);
			r2.setVisibility(View.VISIBLE);
			r3.setVisibility(View.VISIBLE);
			r4.setVisibility(View.VISIBLE);
		} else {
			r1.setVisibility(View.VISIBLE);
			r2.setVisibility(View.GONE);
			r3.setVisibility(View.GONE);
			r4.setVisibility(View.VISIBLE);
		}
		
		// Click actions
		r1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("clipboard", copy);
				clipboard.setPrimaryClip(clip);
				SketchwareUtil.showMessage(getApplicationContext(), "Copied");
				popup.dismiss();
			}
		});
		
		r2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				_DeleteMessage();
				popup.dismiss();
			}
		});
		
		r3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				popup.dismiss();
			}
		});
		
		r4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				popup.dismiss();
			}
		});
		
		// Adjust if going out of screen
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenHeight = displayMetrics.heightPixels;
		int screenWidth = displayMetrics.widthPixels;
		
		popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int popupHeight = popupView.getMeasuredHeight();
		int popupWidth = popupView.getMeasuredWidth();
		
		// Fix overflow
		int finalX = x;
		int finalY = y;
		if ((y + popupHeight) > screenHeight) finalY = screenHeight - popupHeight;
		if ((x + popupWidth) > screenWidth) finalX = screenWidth - popupWidth;
		
		popup.showAtLocation(_view, Gravity.NO_GRAVITY, finalX, finalY);
	}
	
	{
	}
	
	
	public void _DeleteMessage() {
		
		timest = Calendar.getInstance(); 
		chat_key = Chat1.push().getKey(); 
		
		DatabaseReference currentUserInboxRef = _firebase.getReference("inbox")
		.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
		.child(getIntent().getStringExtra("user2"));
		
		final HashMap<String, Object> currentUserUpdateMap = new HashMap<>();
		currentUserUpdateMap.put("subimg", "null");
		currentUserUpdateMap.put("lastMessage", "Message was deleted"); // Last message sent
		currentUserUpdateMap.put("timestamp", timest.getTimeInMillis()); // Timestamp
		currentUserInboxRef.updateChildren(currentUserUpdateMap);
		
		// Update inbox for the recipient under inbox/{user2}/{current_user}
		final DatabaseReference recipientInboxRef = _firebase.getReference("inbox")
		.child(getIntent().getStringExtra("user2"))
		.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		
		recipientInboxRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				long unreadCount = 0;
				if (snapshot.hasChild("unreadCount")) {
					unreadCount = snapshot.child("unreadCount").getValue(Long.class);
				}
				HashMap<String, Object> updateMap = new HashMap<>();
				updateMap.put("subimg", "null"); 
				updateMap.put("lastMessage", "Message was deleted"); // Last message sent
				updateMap.put("timestamp", timest.getTimeInMillis()); // Timestamp
				// Increment unread count
				
				recipientInboxRef.updateChildren(updateMap);
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// Handle error if needed
			}
		});
		Chat1.child(deletekey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> task) {
				if (task.isSuccessful()) {
					SketchwareUtil.showMessage(getApplicationContext(), "Message deleted");
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Delete failed");
				}
			}
		});
		
		Chat2.child(deletekey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> task) {
				if (task.isSuccessful()) {
					SketchwareUtil.showMessage(getApplicationContext(), "Message deleted");
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Delete failed");
				}
			}
		});
		
	}
	
	
	public void _PickFileWithDialogue(final String _type) {
		
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(_type);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(Intent.createChooser(intent, "Select File"), 101);
		
		
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
	
	
	public void _extraCodes() {
	}
	private void performRemoveFriend() {
		SketchwareUtil.showMessage(getApplicationContext(), "Removing friend...");
		
		inboxRef.child(myUid).child(friendUid).removeValue()
		.addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {
				if (task.isSuccessful()) {
					inboxRef.child(friendUid).child(myUid).removeValue()
					.addOnCompleteListener(new OnCompleteListener<Void>() {
						@Override
						public void onComplete(@NonNull Task<Void> task) {
							if (task.isSuccessful()) {
								friendRequestRef.child(friendUid).child(myUid).removeValue()
								.addOnCompleteListener(new OnCompleteListener<Void>() {
									@Override
									public void onComplete(@NonNull Task<Void> task) {
										if (task.isSuccessful()) {
											SketchwareUtil.showMessage(getApplicationContext(), "Friend removed successfully.");
										} else {
											SketchwareUtil.showMessage(getApplicationContext(), "Failed to remove friend request.");
										}
									}
								});
							} else {
								SketchwareUtil.showMessage(getApplicationContext(), "Failed to remove from friend's inbox.");
							}
						}
					});
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Failed to remove from your inbox.");
				}
			}
		});
	}
	
	
	// Helper method to clear both chat nodes
	private void performClearChat() {
		// Chat1 = reference to chat/myUid/friendUid
		// Chat2 = reference to chat/friendUid/myUid
		// Both are already defined in your code
		
		// Show progress (optional)
		SketchwareUtil.showMessage(getApplicationContext(), "Clearing chat...");
		
		// Remove both nodes
		Chat1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task1) {
				if (task1.isSuccessful()) {
					// Delete the second copy as well
					Chat2.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
						@Override
						public void onComplete(@NonNull Task<Void> task2) {
							if (task2.isSuccessful()) {
								SketchwareUtil.showMessage(getApplicationContext(), "Chat cleared.");
							} else {
								SketchwareUtil.showMessage(getApplicationContext(), "Failed to clear friend's side.");
							}
						}
					});
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Failed to clear your side.");
				}
			}
		});
	}
	{
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.chats, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout date = _view.findViewById(R.id.date);
			final LinearLayout main = _view.findViewById(R.id.main);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final RelativeLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear_group = _view.findViewById(R.id.linear_group);
			final LinearLayout linear_g_dp_main = _view.findViewById(R.id.linear_g_dp_main);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final de.hdodenhof.circleimageview.CircleImageView group_img = _view.findViewById(R.id.group_img);
			final de.hdodenhof.circleimageview.CircleImageView admin = _view.findViewById(R.id.admin);
			final LinearLayout voice_message = _view.findViewById(R.id.voice_message);
			final androidx.cardview.widget.CardView image_box = _view.findViewById(R.id.image_box);
			final LinearLayout reply_main = _view.findViewById(R.id.reply_main);
			final LinearLayout chat_main = _view.findViewById(R.id.chat_main);
			final LinearLayout chat_time = _view.findViewById(R.id.chat_time);
			final ImageView image_voice = _view.findViewById(R.id.image_voice);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final TextView vm_text = _view.findViewById(R.id.vm_text);
			final ProgressBar probar1 = _view.findViewById(R.id.probar1);
			final ImageView image = _view.findViewById(R.id.image);
			final ImageView play = _view.findViewById(R.id.play);
			final androidx.cardview.widget.CardView reply_cardview = _view.findViewById(R.id.reply_cardview);
			final TextView reply_txt = _view.findViewById(R.id.reply_txt);
			final LinearLayout reply_card_child = _view.findViewById(R.id.reply_card_child);
			final LinearLayout reply_bar = _view.findViewById(R.id.reply_bar);
			final TextView chat_2 = _view.findViewById(R.id.chat_2);
			final de.hdodenhof.circleimageview.CircleImageView view_lock = _view.findViewById(R.id.view_lock);
			final TextView text_chat = _view.findViewById(R.id.text_chat);
			final LinearLayout linear_download = _view.findViewById(R.id.linear_download);
			final TextView edit = _view.findViewById(R.id.edit);
			final TextView time_txt = _view.findViewById(R.id.time_txt);
			final ImageView tick_image = _view.findViewById(R.id.tick_image);
			final ImageView imageview_download = _view.findViewById(R.id.imageview_download);
			final ProgressBar progressbar1 = _view.findViewById(R.id.progressbar1);
			
			// ===== Sender-Receiver-Identification =====
			if (_data.get((int)_position).containsKey("id")) {
				if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(_data.get((int)_position).get("id").toString())) {
					// === SenderStyling ===
					chat_main.setBackgroundResource(R.drawable.sender); 
					reply_main.setBackgroundResource(R.drawable.sender); 
					voice_message.setBackgroundResource(R.drawable.sender); 
					main.setGravity(Gravity.RIGHT);
					linear_group.setGravity(Gravity.RIGHT);
					chat_time.setVisibility(View.VISIBLE);
					reply_cardview.setCardBackgroundColor(0xFFC2F5D9);
					text_chat.setTextColor(0xFF000000);
					vm_text.setTextColor(0xFF000000);
					
					group_img.setVisibility(View.GONE);
					admin.setVisibility(View.GONE);
				}
				else {
					// === ReceiverStyling ===
					chat_main.setBackgroundResource(R.drawable.reciever); 
					reply_main.setBackgroundResource(R.drawable.reciever);
					voice_message.setBackgroundResource(R.drawable.reciever);
					main.setGravity(Gravity.LEFT);
					linear_group.setGravity(Gravity.LEFT);
					chat_time.setVisibility(View.GONE);
					reply_cardview.setCardBackgroundColor(0xFFECECEC);
					text_chat.setTextColor(0xFF000000);
					vm_text.setTextColor(0xFF000000);
					
					// === GroupChatImageHandling ===
					if (getIntent().getStringExtra("group").equals("true")) {
						if (_data.get((int)_position).containsKey("dp")) {
							linear2.setVisibility(View.VISIBLE);
							Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("dp").toString())).into(group_img);
						}
						else {
							linear2.setVisibility(View.GONE);
						}
					}
					else {
						linear2.setVisibility(View.GONE); 
					}
				}
			}
			
			// ===== TextMessageHandling =====
			if (_data.get((int)_position).containsKey("msg")) {
				view_lock.setVisibility(View.GONE);
				voice_message.setVisibility(View.GONE);
				chat_main.setVisibility(View.VISIBLE);
				reply_main.setVisibility(View.GONE);
				text_chat.setText(_data.get((int)_position).get("msg").toString());
				
				
			}else{
				chat_main.setVisibility(View.GONE);
			}
			// ===== ReplyMessageHandling =====
			if (_data.get((int)_position).containsKey("reply")) {
				reply_main.setVisibility(View.VISIBLE);
				chat_main.setVisibility(View.GONE);
				chat_2.setText(_data.get((int)_position).get("msg2").toString());
				reply_txt.setText(_data.get((int)_position).get("reply").toString());
				
			}
			else {
				reply_main.setVisibility(View.GONE);
			}
			
			// Time & Status
			
			if (_data.get((int)_position).containsKey("timestamp")) {
				t24 = Calendar.getInstance();
				_setTime(Double.parseDouble(_data.get((int)_position).get("timestamp").toString()), time_txt);
				
			}
			
			if (_data.get((int)_position).containsKey("status")) {
				if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(_data.get((int)_position).get("id").toString())) {
					tick_image.setVisibility(View.VISIBLE);
					if ("seen".equals(_data.get((int)_position).get("status").toString())) {
						tick_image.setImageResource(R.drawable.ic_done_all_white);
						tick_image.setColorFilter(0xFF2196F3, PorterDuff.Mode.MULTIPLY);
					}
					else {
						tick_image.setImageResource(R.drawable.ic_done_white);
						tick_image.setColorFilter(0xFF9E9E9E, PorterDuff.Mode.MULTIPLY);
					}
				}
				else {
					tick_image.setVisibility(View.GONE);
				}
			}
			
			
			// day of message (today etc.) & Edited text 
			
			edit.setVisibility(View.GONE);
			textview1.setBackground(new GradientDrawable() { 
				public GradientDrawable getIns(int a, int b) { 
					this.setCornerRadius(a); 
					this.setColor(b); 
					return this; 
				} 
			}.getIns((int)20, 0xFFF7F7F7));
			textview1.setTextColor(0xFF5C6267);
			
			// ===== MediaAttachments =====
			
			linear_download.setVisibility(View.GONE);
			
			if (_data.get((int)_position).containsKey("img")) {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("img").toString())).into(image);
				image_box.setVisibility(View.VISIBLE);
				play.setVisibility(View.GONE);
				
				
			}else {
				
				if (_data.get((int)_position).containsKey("video")) {
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("video").toString())).into(image);
					image_box.setVisibility(View.VISIBLE);
					play.setVisibility(View.VISIBLE);
					
				}else {
					play.setVisibility(View.GONE);
					image_box.setVisibility(View.GONE);
				}
				
			}
			
			
			
			// ===== FileAttachments =====
			if (_data.get((int)_position).containsKey("voice")) {
				voice_message.setVisibility(View.VISIBLE);
				reply_main.setVisibility(View.GONE);
				chat_main.setVisibility(View.GONE);
				vm_text.setText("Voice Message");
				
			}
			else {
				voice_message.setVisibility(View.GONE);
				
			}
			
			if (_data.get((int)_position).containsKey("date")) {
				timest = Calendar.getInstance();
				if (_data.get((int)_position).get("date").toString().equals(new SimpleDateFormat("E, MMM dd yyyy").format(timest.getTime()))) {
					textview1.setText("Today");
				}
				else {
					textview1.setText(_data.get((int)_position).get("date").toString());
				}
				if (_position == 0) {
					date.setVisibility(View.VISIBLE);
				}
				else {
					if (_data.get((int)_position).get("date").toString().equals(_data.get((int)_position - 1).get("date").toString())) {
						date.setVisibility(View.GONE);
					}
					else {
						date.setVisibility(View.VISIBLE);
					}
				}
				
			}
			
			
			// ===== ClickListeners =====
			main.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						touchX[0] = (int) event.getRawX();
						touchY[0] = (int) event.getRawY();
					}
					return false;
				}
			});
			
			main.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					try {
						
						deletekey = _data.get((int)_position).get("key").toString();
						_ShowPopupAtPosition(main, touchX[0], touchY[0],_data.get((int)_position).get("id").toString());
						
					}catch (Exception e) {
						
						SketchwareUtil.showMessage(getApplicationContext(), "Error getting ID");
						
					} 
					// For Copy 
					if (_data.get((int)_position).containsKey("msg")) {
						copy = _data.get((int)_position).get("msg").toString();
					}else{
						if (_data.get((int)_position).containsKey("reply")) {
							copy = _data.get((int)_position).get("reply").toString();
						}else{
							if (_data.get((int)_position).containsKey("img")) {
								copy = "protected-IMG_File";
							}else
							{
								if (_data.get((int)_position).containsKey("video")) {
									copy = "protected:VDO_File";
								}else{
									if (_data.get((int)_position).containsKey("voice")) {
										copy = "Voice-Message";
									}else{
										copy = "";
										
									}
								}
							}
							
						}
					}
					
					return true;
				}
			});
			
			
			main.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_data.get((int)_position).containsKey("voice")) {
						String url = _data.get((int)_position).get("voice").toString();
						_PlayMusic2( url ,image_voice,probar1);
					}
				}
			});
			progressbar1.setVisibility(View.GONE);
			if (file.contains("cust_font")) {
				if (FileUtil.isExistFile(file.getString("font_url", ""))) {
					textview1.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					vm_text.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					reply_txt.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					chat_2.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					text_chat.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					edit.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					time_txt.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
				} else {
					textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medium.ttf"), 0);
					vm_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
					reply_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
					chat_2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
					text_chat.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
					edit.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
					time_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medium.ttf"), 0);
				}
			} else {
				textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medium.ttf"), 0);
				vm_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
				reply_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
				chat_2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
				text_chat.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
				edit.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
				time_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medium.ttf"), 0);
			}
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