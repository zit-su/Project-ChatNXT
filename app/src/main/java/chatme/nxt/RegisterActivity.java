package chatme.nxt;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
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
import java.io.*;
import java.io.File;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.Response;
import java.io.IOException;
import java.io.File;

public class RegisterActivity extends AppCompatActivity {
	
	public final int REQ_CD_FPICK = 101;
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	private boolean dp = false;
	private String dp_url = "";
	private String nldp = "";
	private boolean valid = false;
	private String path = "";
	private String filename = "";
	private String fontName = "";
	private String typeace = "";
	private String localFontPath = "";
	private String Url = "";
	private String Api = "";
	private String File_Name = "";
	
	private LinearLayout linear1;
	private LinearLayout dv1;
	private ImageView imageview1;
	private LinearLayout I_user;
	private LinearLayout l_email;
	private LinearLayout l_pass;
	private TextView textview2;
	private LinearLayout button_continue;
	private LinearLayout linear_dont_have;
	private LinearLayout dv2;
	private ImageView im_user;
	private EditText tx_username;
	private ImageView im_email;
	private EditText tx_email;
	private ImageView im_pass;
	private EditText tx_password;
	private TextView button_text;
	private ProgressBar progressbar1;
	private TextView intext;
	private TextView sign_in;
	
	private SharedPreferences file;
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
	private DatabaseReference up = _firebase.getReference("up");
	private ChildEventListener _up_child_listener;
	private Intent fpick = new Intent(Intent.ACTION_GET_CONTENT);
	private StorageReference storage = _firebase_storage.getReference("storage");
	private OnCompleteListener<Uri> _storage_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _storage_download_success_listener;
	private OnSuccessListener _storage_delete_success_listener;
	private OnProgressListener _storage_upload_progress_listener;
	private OnProgressListener _storage_download_progress_listener;
	private OnFailureListener _storage_failure_listener;
	private ProgressDialog prog;
	private Calendar dateOfjoin = Calendar.getInstance();
	private RequestNetwork network;
	private RequestNetwork.RequestListener _network_request_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.register);
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
		linear1 = findViewById(R.id.linear1);
		dv1 = findViewById(R.id.dv1);
		imageview1 = findViewById(R.id.imageview1);
		I_user = findViewById(R.id.I_user);
		l_email = findViewById(R.id.l_email);
		l_pass = findViewById(R.id.l_pass);
		textview2 = findViewById(R.id.textview2);
		button_continue = findViewById(R.id.button_continue);
		linear_dont_have = findViewById(R.id.linear_dont_have);
		dv2 = findViewById(R.id.dv2);
		im_user = findViewById(R.id.im_user);
		tx_username = findViewById(R.id.tx_username);
		im_email = findViewById(R.id.im_email);
		tx_email = findViewById(R.id.tx_email);
		im_pass = findViewById(R.id.im_pass);
		tx_password = findViewById(R.id.tx_password);
		button_text = findViewById(R.id.button_text);
		progressbar1 = findViewById(R.id.progressbar1);
		intext = findViewById(R.id.intext);
		sign_in = findViewById(R.id.sign_in);
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		fauth = FirebaseAuth.getInstance();
		fpick.setType("image/*");
		fpick.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		network = new RequestNetwork(this);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fpick, REQ_CD_FPICK);
			}
		});
		
		button_continue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (valid) {
					_accout_login(tx_email, tx_password);
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Username not validate!");
				}
			}
		});
		
		tx_username.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
				final EditText editTextUsername = findViewById(R.id.tx_username); // EditText reference
				
				// Get the value from EditText in lowercase
				final String textValue = editTextUsername.getText().toString().trim().toLowerCase();
				
				// Regular expression to check for special characters (Only allows letters, numbers, and underscores)
				String specialCharacterPattern = "^[a-z0-9_]*$"; 
				
				if (!textValue.matches(specialCharacterPattern)) {
					// Set an error message on EditText if special characters are found
					editTextUsername.setError("No special characters allowed!");
					valid = false;
				} else {
					// Perform Firebase query to check if the value exists in "user"
					usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot snapshot) {
							boolean matchFound = false;
							
							// Iterate through all users
							for (DataSnapshot userSnapshot : snapshot.getChildren()) {
								String userValue = userSnapshot.child("user").getValue(String.class);
								if (userValue != null && userValue.toLowerCase().equals(textValue)) {
									matchFound = true;
									break;
								}
							}
							
							if (matchFound) {
								// If a match is found, set an error message on EditText
								editTextUsername.setError("Username already taken!");
								valid = false;
							} else {
								// Clear any previous error if no match is found
								editTextUsername.setError(null);
								valid = true;
							}
						}
						
						@Override
						public void onCancelled(@NonNull DatabaseError error) {
							// Handle database error
							Toast.makeText(getApplicationContext(), "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
				}
				
				/*final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
final EditText editTextUsername = findViewById(R.id.tx_username); // EditText reference

// Get the value from EditText
final String textValue = editTextUsername.getText().toString().trim();

// Regular expression to check for special characters
String specialCharacterPattern = "^[a-zA-Z0-9_]*$"; // Only allows letters, numbers, and underscores

if (!textValue.matches(specialCharacterPattern)) {
    // Set an error message on EditText if special characters are found
    editTextUsername.setError("No special characters allowed!");
    valid = false;
} else {
    // Perform Firebase query to check if the value exists in "user"
    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            boolean matchFound = false;

            // Iterate through all users
            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                String userValue = userSnapshot.child("user").getValue(String.class);
                if (userValue != null && userValue.equals(textValue)) {
                    matchFound = true;
                    break;
                }
            }

            if (matchFound) {
                // If a match is found, set an error message on EditText
                editTextUsername.setError("Username already taken!");
                valid = false;
            } else {
                // Clear any previous error if no match is found
                editTextUsername.setError(null);
                valid = true;
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            // Handle database error
            Toast.makeText(getApplicationContext(), "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}
*/
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		sign_in.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				a.setClass(getApplicationContext(), LogActivity.class);
				startActivity(a);
				
				overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
				
				finish();
			}
		});
		
		_users_child_listener = new ChildEventListener() {
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
		users.addChildEventListener(_users_child_listener);
		
		_up_child_listener = new ChildEventListener() {
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
		up.addChildEventListener(_up_child_listener);
		
		_storage_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				_ProgresbarDimiss();
				_ProgresbarShow("Uploading.. ".concat(String.valueOf((long)(_progressValue)).concat("%")));
			}
		};
		
		_storage_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_storage_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				_ProgresbarDimiss();
				dp_url = _downloadUrl;
				Glide.with(getApplicationContext()).load(Uri.parse(_downloadUrl)).into(imageview1);
				imageview1.setBackgroundColor(0xFFFFFFFF);
				dp = true;
			}
		};
		
		_storage_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_storage_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_storage_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_network_request_listener = new RequestNetwork.RequestListener() {
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
				if (_success) {
					if (dp) {
						_create_account(dp_url);
					} else {
						_create_account(file.getString("nulldp", ""));
					}
					textview2.setTextColor(0xFF2196F3);
				} else {
					if ("A network error (such as timeout, interrupted connection or unreachable host) has occurred.".equals(_errorMessage)) {
						textview2.setText("A network error (such as timeout, interrupted connection) has occurred.");
					} else {
						if ("The email address is badly formatted.".equals(_errorMessage)) {
							((EditText)tx_email).setError("Invalid email address!");
						} else {
							textview2.setText(_errorMessage);
						}
					}
					button_continue.setEnabled(true);
					button_text.setVisibility(View.VISIBLE);
					progressbar1.setVisibility(View.GONE);
					textview2.setTextColor(0xFFE74C3C);
				}
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
		_UI();
		_updated_nullDP();
		_SetFont("fnm1");
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FPICK:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				/*
path = _filePath.get((int)(0));
filename = Uri.parse(_filePath.get((int)(0))).getLastPathSegment();
storage.child(filename).putFile(Uri.fromFile(new File(path))).addOnFailureListener(_storage_failure_listener).addOnProgressListener(_storage_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
@Override
public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
return storage.child(filename).getDownloadUrl();
}}).addOnCompleteListener(_storage_upload_success_listener);
*/
				SketchwareUtil.showMessage(getApplicationContext(), "File Uploading....");
				
				String path = _filePath.get(0);
				
				File_Name = Uri.parse(path).getLastPathSegment();
				
				OkHttpClient client = new OkHttpClient();
				
				File file = new File(path);
				
				RequestBody fileBody = RequestBody.create(
				MediaType.parse("image/jpeg"),
				file
				);
				
				Request request = new Request.Builder()
				.url(Url + "/storage/v1/object/ChatApp/" + File_Name)
				.addHeader("apikey", Api)
				.addHeader("Authorization", "Bearer " + Api)
				.addHeader("Content-Type", "image/jpeg")
				.addHeader("x-upsert", "true")
				.post(fileBody)
				.build();
				
				client.newCall(request).enqueue(new Callback() {
					@Override
					public void onFailure(Call call, final IOException e) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_ProgresbarDimiss();
								
								SketchwareUtil.showMessage(
								getApplicationContext(),
								"Upload Failed : " + e.getMessage()
								);
							}
						});
					}
					
					@Override
					public void onResponse(Call call, final Response response) throws IOException {
						final String res = response.body().string();
						
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_ProgresbarDimiss();
								
								if (response.isSuccessful()) {
									String publicUrl = Url + "/storage/v1/object/public/ChatApp/" + File_Name;
									
									dp_url = publicUrl;
									dp = true;
									
									SketchwareUtil.showMessage(getApplicationContext(), "Upload Successful");
									
									Glide.with(getApplicationContext())
									.load(publicUrl)
									.into(imageview1);
									
								} else {
									textview2.setText(res);
									SketchwareUtil.showMessage(getApplicationContext(), "Upload Failed");
								}
							}
						});
					}
				});
				_ProgresbarShow("Uploading.. ");
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	public void _lightStatusBar(final String _color) {
		getWindow().setStatusBarColor(Color.parseColor("#F7F6F2"));
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		
	}
	
	
	public void _UI() {
		valid = false;
		dp = false;
		progressbar1.setVisibility(View.GONE);
		button_continue.setElevation((float)3);
		_ProgressBarColour(progressbar1, "#FFFFFF");
		button_continue.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)38, 0xFF1A3764));
		I_user.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)38, 0xFFFFFFFF));
		l_email.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)38, 0xFFFFFFFF));
		l_pass.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)38, 0xFFFFFFFF));
		_ImageColor(im_user, "#1A3764");
		_ImageColor(im_email, "#1A3764");
		_ImageColor(im_pass, "#1A3764");
		Url = "https://ovsmtwnqjfefpzjoznwd.supabase.co";
		Api = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im92c210d25xamZlZnB6am96bndkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODE2ODg5NTIsImV4cCI6MjA5NzI2NDk1Mn0.5b_7bBlv0mSgxiHETCUNum2p08JvS1SFSeNP7enjiMI";
	}
	
	
	public void _ProgressBarColour(final ProgressBar _progressbar, final String _color) {
		if (android.os.Build.VERSION.SDK_INT >=21) {
			_progressbar.getIndeterminateDrawable() .setColorFilter(Color.parseColor(_color), PorterDuff.Mode.SRC_IN);
		}
	}
	
	
	public void _ImageColor(final ImageView _image, final String _color) {
		_image.setColorFilter(Color.parseColor(_color),PorterDuff.Mode.SRC_ATOP);
	}
	
	
	public void _accout_login(final TextView _t1, final TextView _t2) {
		if (!_t1.getText().toString().trim().equals("") && !_t2.getText().toString().trim().equals("")) {
			fauth.createUserWithEmailAndPassword(_t1.getText().toString(), _t2.getText().toString()).addOnCompleteListener(RegisterActivity.this, _fauth_create_user_listener);
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
	
	
	public void _create_account(final String _url) {
		dateOfjoin = Calendar.getInstance();
		map = new HashMap<>();
		map.put("username", tx_username.getText().toString());
		map.put("user", tx_username.getText().toString());
		map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
		map.put("dp", _url);
		map.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
		map.put("ban", "false");
		map.put("doj", new SimpleDateFormat("dd MMM yyyy").format(dateOfjoin.getTime()));
		map.put("verify", "false");
		map.put("type", "user");
		users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
		map.clear();
		a.setClass(getApplicationContext(), MainActivity.class);
		startActivity(a);
		finish();
	}
	
	
	public void _updated_nullDP() {
		nldp = String.valueOf((long)(SketchwareUtil.getRandom((int)(1), (int)(20))));
		
		if ("1".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868698.png?alt=media&token=8043c6c6-dbec-4634-9ce7-9021f17420e8").commit();
		}
		if ("2".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868700.png?alt=media&token=1e6fff6c-ae6d-4e2b-b885-5b6d3f4e3a25").commit();
		}
		if ("3".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868704.png?alt=media&token=d1707b42-ae70-46f2-a6e5-d863b0498e9c").commit();
		}
		if ("4".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868708.png?alt=media&token=80710c1b-7852-49de-b5de-a7b4a061ccf9").commit();
		}
		if ("5".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868711.png?alt=media&token=fbad192d-c541-4e11-b442-1c49bbacb8c6").commit();
		}
		if ("6".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868712.png?alt=media&token=9302987f-1762-4291-b7f2-b63265971e98").commit();
		}
		if ("7".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868713.png?alt=media&token=7dc60c31-364c-411f-bc0e-79e7b552f043").commit();
		}
		if ("8".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868714.png?alt=media&token=59192bec-864c-4c62-ab42-e7f9c7be4979").commit();
		}
		if ("9".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868715.png?alt=media&token=70d17325-2b4e-41ba-89a6-58ee88297761").commit();
		}
		if ("10".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868718.png?alt=media&token=356fb064-52ad-4099-b30c-1bdb91f288d3").commit();
		}
		if ("11".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868719.png?alt=media&token=f08ed490-9c8c-4ee3-97e3-3993520d6142").commit();
		}
		if ("12".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868725.png?alt=media&token=67b37819-3c6e-4f5b-84a3-560e7e90a14b").commit();
		}
		if ("13".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868726.png?alt=media&token=b6af1f43-f230-43dd-9710-369060c9f42c").commit();
		}
		if ("14".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868728.png?alt=media&token=b729c111-8ef0-405f-b47f-5b160335981b").commit();
		}
		if ("15".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868729.png?alt=media&token=51e1858e-787d-4395-a300-2fed645e2a21").commit();
		}
		if ("16".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868732.png?alt=media&token=e60b5c99-cbc2-4b30-a821-3c176a66af4f").commit();
		}
		if ("17".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868739.png?alt=media&token=35f5c79d-0fba-4483-b0fc-62aaa0de23a5").commit();
		}
		if ("18".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868742.png?alt=media&token=48910e86-5e38-4340-9ebf-1e84bb8a3362").commit();
		}
		if ("19".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868745.png?alt=media&token=ef00538d-f8f9-4348-8489-537cb3061faa").commit();
		}
		if ("20".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868747.png?alt=media&token=9b7011f0-9a0c-4950-a453-edf7d4a5003c").commit();
		}
	}
	
	
	public void _ProgresbarShow(final String _title) {
		prog = new ProgressDialog(RegisterActivity.this);
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