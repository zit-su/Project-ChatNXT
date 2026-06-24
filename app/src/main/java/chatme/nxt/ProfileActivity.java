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
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import android.database.Cursor;
import android.provider.OpenableColumns;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.Response;
import java.io.IOException;
import java.io.File;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Buffer;
import okio.Sink;

public class ProfileActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private String fontName = "";
	private String typeace = "";
	private boolean useLocalFont = false;
	private HashMap<String, Object> all_months = new HashMap<>();
	private String profile_path = "";
	private boolean isDOBChoosed = false;
	private String fileName = "";
	private String fileSize = "";
	private String filePath = "";
	private String dataPath = "";
	private HashMap<String, Object> map = new HashMap<>();
	private boolean valid = false;
	private String localFontPath = "";
	private String Url = "";
	private String Api = "";
	private String File_Name = "";
	
	private ArrayList<String> ls = new ArrayList<>();
	
	private LinearLayout toolbar;
	private LinearLayout content_wrapper;
	private ImageView back_img;
	private TextView toolbar_title;
	private ImageView done_img;
	private TextView Credit_to;
	private RelativeLayout profile_relative;
	private TextView username_setted;
	private LinearLayout email_field;
	private LinearLayout username_field;
	private LinearLayout bio_field;
	private LinearLayout dob_field;
	private LinearLayout profile_wrapper;
	private LinearLayout choose_wrapper;
	private CircleImageView profile_image;
	private LinearLayout choose_layout;
	private ImageView choose_image;
	private TextView email_title;
	private EditText email_edittext;
	private LinearLayout email_base;
	private TextView email_error;
	private TextView username_title;
	private LinearLayout username_wrapper;
	private LinearLayout username_base;
	private TextView username_error;
	private TextView at_rate;
	private EditText username_edittext;
	private TextView bio_title;
	private EditText bio_edittext;
	private LinearLayout bio_base;
	private TextView bio_error;
	private TextView dob_title;
	private LinearLayout dob_wrapper;
	private LinearLayout date_field;
	private LinearLayout month_field;
	private LinearLayout year_field;
	private TextView date_text;
	private LinearLayout date_base;
	private TextView month_text;
	private LinearLayout month_base;
	private TextView year_text;
	private LinearLayout year_base;
	
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
	private StorageReference dp = _firebase_storage.getReference("dp");
	private OnCompleteListener<Uri> _dp_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _dp_download_success_listener;
	private OnSuccessListener _dp_delete_success_listener;
	private OnProgressListener _dp_upload_progress_listener;
	private OnProgressListener _dp_download_progress_listener;
	private OnFailureListener _dp_failure_listener;
	private ProgressDialog prog;
	private RequestNetwork network;
	private RequestNetwork.RequestListener _network_request_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.profile);
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
		content_wrapper = findViewById(R.id.content_wrapper);
		back_img = findViewById(R.id.back_img);
		toolbar_title = findViewById(R.id.toolbar_title);
		done_img = findViewById(R.id.done_img);
		Credit_to = findViewById(R.id.Credit_to);
		profile_relative = findViewById(R.id.profile_relative);
		username_setted = findViewById(R.id.username_setted);
		email_field = findViewById(R.id.email_field);
		username_field = findViewById(R.id.username_field);
		bio_field = findViewById(R.id.bio_field);
		dob_field = findViewById(R.id.dob_field);
		profile_wrapper = findViewById(R.id.profile_wrapper);
		choose_wrapper = findViewById(R.id.choose_wrapper);
		profile_image = findViewById(R.id.profile_image);
		choose_layout = findViewById(R.id.choose_layout);
		choose_image = findViewById(R.id.choose_image);
		email_title = findViewById(R.id.email_title);
		email_edittext = findViewById(R.id.email_edittext);
		email_base = findViewById(R.id.email_base);
		email_error = findViewById(R.id.email_error);
		username_title = findViewById(R.id.username_title);
		username_wrapper = findViewById(R.id.username_wrapper);
		username_base = findViewById(R.id.username_base);
		username_error = findViewById(R.id.username_error);
		at_rate = findViewById(R.id.at_rate);
		username_edittext = findViewById(R.id.username_edittext);
		bio_title = findViewById(R.id.bio_title);
		bio_edittext = findViewById(R.id.bio_edittext);
		bio_base = findViewById(R.id.bio_base);
		bio_error = findViewById(R.id.bio_error);
		dob_title = findViewById(R.id.dob_title);
		dob_wrapper = findViewById(R.id.dob_wrapper);
		date_field = findViewById(R.id.date_field);
		month_field = findViewById(R.id.month_field);
		year_field = findViewById(R.id.year_field);
		date_text = findViewById(R.id.date_text);
		date_base = findViewById(R.id.date_base);
		month_text = findViewById(R.id.month_text);
		month_base = findViewById(R.id.month_base);
		year_text = findViewById(R.id.year_text);
		year_base = findViewById(R.id.year_base);
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		fauth = FirebaseAuth.getInstance();
		network = new RequestNetwork(this);
		
		back_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (getIntent().hasExtra("main")) {
					a.setClass(getApplicationContext(), MainActivity.class);
					startActivity(a);
					finish();
				} else {
					a.setClass(getApplicationContext(), SettingsActivity.class);
					startActivity(a);
					finish();
				}
			}
		});
		
		done_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (profile_path.equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Select your profile picture");
				} else {
					if (email_edittext.getText().toString().equals("")) {
						_email_case();
					} else {
						if (username_edittext.getText().toString().equals("")) {
							_username_case();
						} else {
							if (bio_edittext.getText().toString().equals("")) {
								_bio_case();
							} else {
								if (valid) {
									if (isDOBChoosed) {
										// do whatever if user has given his/her DOB
										_create_account("");
									} else {
										// do whatever if user hasn't given his/her DOB
										_create_account("");
									}
									SketchwareUtil.showMessage(getApplicationContext(), "Successfully Updated");
								} else {
									SketchwareUtil.showMessage(getApplicationContext(), "Profile Not Updated!!");
								}
							}
						}
					}
				}
			}
		});
		
		choose_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PickFile("image/*");
			}
		});
		
		email_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		username_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
				final EditText editTextUsername = findViewById(R.id.username_edittext); // EditText reference
				
				// Get the value from EditText in lowercase
				final String textValue = editTextUsername.getText().toString().trim().toLowerCase();
				
				// Regular expression to check for special characters (only lowercase letters, numbers, and underscores)
				String specialCharacterPattern = "^[a-z0-9_]*$"; 
				
				if (!textValue.matches(specialCharacterPattern)) {
					// Set an error message on EditText if special characters are found
					editTextUsername.setError("No special characters allowed!");
					valid = false;
				} else {
					// Get current user UUID (Make it final)
					final String currentUserUUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
					
					usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot snapshot) {
							boolean matchFound = false;
							
							// Get current user's username from database
							String currentUserUsername = snapshot.child(currentUserUUID).child("username").getValue(String.class);
							if (currentUserUsername != null) {
								currentUserUsername = currentUserUsername.toLowerCase();
							}
							
							// Iterate through all users
							for (DataSnapshot userSnapshot : snapshot.getChildren()) {
								String userValue = userSnapshot.child("username").getValue(String.class);
								
								// Check if username exists but ignore the current user's username
								if (userValue != null && userValue.toLowerCase().equals(textValue) 
								&& !userSnapshot.getKey().equals(currentUserUUID)) {
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
final EditText editTextUsername = findViewById(R.id.username_edittext); // EditText reference

// Get the value from EditText
final String textValue = editTextUsername.getText().toString().trim();

// Regular expression to check for special characters
String specialCharacterPattern = "^[a-zA-Z0-9_]*$"; // Only allows letters, numbers, and underscores

if (!textValue.matches(specialCharacterPattern)) {
    // Set an error message on EditText if special characters are found
    editTextUsername.setError("No special characters allowed!");
    valid = false;
} else {
    // Get current user UUID (Make it final)
    final String currentUserUUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            boolean matchFound = false;

            // Get current user's username from database
            String currentUserUsername = snapshot.child(currentUserUUID).child("username").getValue(String.class);

            // Iterate through all users
            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                String userValue = userSnapshot.child("username").getValue(String.class);
                
                // Check if username exists but ignore current user's username
                if (userValue != null && userValue.equals(textValue) && !userSnapshot.getKey().equals(currentUserUUID)) {
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
		
		date_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_datePicker(date_text, month_text, year_text);
			}
		});
		
		month_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_datePicker(date_text, month_text, year_text);
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
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(profile_image);
							profile_path = _childValue.get("dp").toString();
						} else {
							
						}
						if (_childValue.containsKey("username")) {
							username_setted.setText(_childValue.get("username").toString());
							email_edittext.setText(_childValue.get("username").toString());
						} else {
							username_setted.setText("Username not found!");
						}
						if (_childValue.containsKey("user")) {
							username_edittext.setText(_childValue.get("user").toString());
						} else {
							
						}
						if (_childValue.containsKey("bio")) {
							bio_edittext.setText(_childValue.get("bio").toString());
						} else {
							
						}
						if (_childValue.containsKey("dob")) {
							String profileName = _childValue.get("dob").toString();// प्रोफाइल नाव
							
							// TextView references
							TextView date_text = findViewById(R.id.date_text);
							TextView month_text = findViewById(R.id.month_text);
							TextView year_text = findViewById(R.id.year_text);
							
							// प्रोफाइल नाव स्प्लिट करा
							String[] nameParts = profileName.split(" ", 3);
							
							// पहिले नाव date_text मध्ये
							date_text.setText(nameParts[0]);
							
							// मधला भाग month_text मध्ये (जर असेल तर)
							if (nameParts.length > 1) {
								month_text.setText(nameParts[1]);
							} else {
								month_text.setText(""); // नाही असल्यास रिकामे ठेवा
							}
							
							// तिसरा भाग year_text मध्ये (जर असेल तर)
							if (nameParts.length > 2) {
								year_text.setText(nameParts[2]);
							} else {
								year_text.setText(""); // नाही असल्यास रिकामे ठेवा
							}
							
						} else {
							
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
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(profile_image);
						} else {
							
						}
						if (_childValue.containsKey("username")) {
							username_setted.setText(_childValue.get("username").toString());
							email_edittext.setText(_childValue.get("username").toString());
						} else {
							username_setted.setText("Username not found!");
						}
						if (_childValue.containsKey("user")) {
							username_edittext.setText(_childValue.get("user").toString());
						} else {
							
						}
						if (_childValue.containsKey("bio")) {
							bio_edittext.setText(_childValue.get("bio").toString());
						} else {
							
						}
						if (_childValue.containsKey("dob")) {
							String profileName = _childValue.get("dob").toString();// प्रोफाइल नाव
							
							// TextView references
							TextView date_text = findViewById(R.id.date_text);
							TextView month_text = findViewById(R.id.month_text);
							TextView year_text = findViewById(R.id.year_text);
							
							// प्रोफाइल नाव स्प्लिट करा
							String[] nameParts = profileName.split(" ", 3);
							
							// पहिले नाव date_text मध्ये
							date_text.setText(nameParts[0]);
							
							// मधला भाग month_text मध्ये (जर असेल तर)
							if (nameParts.length > 1) {
								month_text.setText(nameParts[1]);
							} else {
								month_text.setText(""); // नाही असल्यास रिकामे ठेवा
							}
							
							// तिसरा भाग year_text मध्ये (जर असेल तर)
							if (nameParts.length > 2) {
								year_text.setText(nameParts[2]);
							} else {
								year_text.setText(""); // नाही असल्यास रिकामे ठेवा
							}
							
						} else {
							
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
					if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						if (_childValue.containsKey("dp")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(profile_image);
						} else {
							
						}
						if (_childValue.containsKey("username")) {
							username_setted.setText(_childValue.get("username").toString());
							email_edittext.setText(_childValue.get("username").toString());
						} else {
							username_setted.setText("Username not found!");
						}
						if (_childValue.containsKey("user")) {
							username_edittext.setText(_childValue.get("user").toString());
						} else {
							
						}
						if (_childValue.containsKey("bio")) {
							bio_edittext.setText(_childValue.get("bio").toString());
						} else {
							
						}
						if (_childValue.containsKey("dob")) {
							String profileName = _childValue.get("dob").toString();// प्रोफाइल नाव
							
							// TextView references
							TextView date_text = findViewById(R.id.date_text);
							TextView month_text = findViewById(R.id.month_text);
							TextView year_text = findViewById(R.id.year_text);
							
							// प्रोफाइल नाव स्प्लिट करा
							String[] nameParts = profileName.split(" ", 3);
							
							// पहिले नाव date_text मध्ये
							date_text.setText(nameParts[0]);
							
							// मधला भाग month_text मध्ये (जर असेल तर)
							if (nameParts.length > 1) {
								month_text.setText(nameParts[1]);
							} else {
								month_text.setText(""); // नाही असल्यास रिकामे ठेवा
							}
							
							// तिसरा भाग year_text मध्ये (जर असेल तर)
							if (nameParts.length > 2) {
								year_text.setText(nameParts[2]);
							} else {
								year_text.setText(""); // नाही असल्यास रिकामे ठेवा
							}
							
						} else {
							
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
		
		_dp_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				_ProgresbarDimiss();
				_ProgresbarShow("Uploading.. [".concat(String.valueOf((long)(_progressValue)).concat("%]")));
			}
		};
		
		_dp_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_dp_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				
				// Get current user's UID and mark it as final
				final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
				final String newDp = _downloadUrl;
				
				// Reference to the user's data in Firebase
				final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(myUid);
				
				// Update the 'dp' key with the new URL
				userRef.child("dp").setValue(newDp).addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (task.isSuccessful()) {
							SketchwareUtil.showMessage(getApplicationContext(), "DP updated successfully");
							_ProgresbarDimiss();
							
						} else {
							SketchwareUtil.showMessage(getApplicationContext(), "Failed to update DP");
							_ProgresbarDimiss();
						}
					}
				});
				
			}
		};
		
		_dp_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_dp_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_dp_failure_listener = new OnFailureListener() {
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
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			valid = false;
			_SetFont("fnm1");
			_add_months();
			_edittext_logic();
			_ui_setup();
			Credit_to.setVisibility(View.GONE);
			_API();
		} else {
			a.setClass(getApplicationContext(), MainActivity.class);
			startActivity(a);
			finish();
		}
	}
	
	
	@Override
	public void onBackPressed() {
		if (getIntent().hasExtra("main")) {
			a.setClass(getApplicationContext(), MainActivity.class);
			startActivity(a);
			finish();
		} else {
			a.setClass(getApplicationContext(), SettingsActivity.class);
			startActivity(a);
			finish();
		}
	}
	public void _ui_setup() {
		choose_layout.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)5, 0xFFFFFFFF, 0xFF434065));
		toolbar_title.setTextSize((int)13);
		email_error.setVisibility(View.GONE);
		username_error.setVisibility(View.GONE);
		bio_error.setVisibility(View.GONE);
		_Add("#FFFFFF", choose_image);
		_ripple(back_img);
		_ripple(done_img);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(0xFFFFFFFF);
	}
	
	
	public void _Add(final String _Colour, final ImageView _Imageview) {
		_Imageview.getDrawable().setColorFilter(Color.parseColor(_Colour), PorterDuff.Mode.SRC_IN);
	}
	
	
	public void _edittext_logic() {
		_edittext_on_focus(email_edittext, email_base, email_title, email_error);
		_edittext_on_focus(username_edittext, username_base, username_title, username_error);
		_edittext_on_focus(bio_edittext, bio_base, bio_title, bio_error);
	}
	
	
	public void _edittext_on_focus(final EditText _edittext, final View _base, final TextView _title, final TextView _error) {
		_edittext.setOnFocusChangeListener(new OnFocusChangeListener() { @Override public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					_title.setTextColor(0xFF1976D2);
					_edittext.setTextColor(0xFF212121);
					_TransitionManager(content_wrapper, 200);
					_error.setVisibility(View.GONE);
					final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(_base,
					"backgroundColor",
					new ArgbEvaluator(),
					0xffBDBDBD,
					0xff1976B2);
					backgroundColorAnimator.setDuration(500);
					backgroundColorAnimator.start();
				} 
				else { 
					_title.setTextColor(0xFFBDBDBD);
					_edittext.setTextColor(0xFF9E9E9E);
					_TransitionManager(content_wrapper, 200);
					_error.setVisibility(View.GONE);
					final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(_base,
					"backgroundColor",
					new ArgbEvaluator(),
					0xff1976D2,
					0xffBDBDBD);
					backgroundColorAnimator.setDuration(500);
					backgroundColorAnimator.start();
				} } });
	}
	
	
	public void _ripple(final View _view) {
		
		int[] attrs = new int[]{android.R.attr.selectableItemBackgroundBorderless};
		android.content.res.TypedArray typedArray = this.obtainStyledAttributes(attrs);
		int backgroundResource = typedArray.getResourceId(0, 0); _view.setBackgroundResource(backgroundResource);
		_view.setClickable(true);
	}
	
	
	public void _datePicker(final TextView _day, final TextView _mon, final TextView _year) {
		in_tv_dateDay = _day;
		in_tv_dateMon = _mon;
		in_tv_dateYear = _year;
		showDatePickerDialog();
	}
	
	private TextView in_tv_dateDay;
	private TextView in_tv_dateMon;
	private TextView in_tv_dateYear;
	
	public void showDatePickerDialog() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int mon = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		DatePickerDialog mDP;
		mDP = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month, int day) {
				int mon = month + 1;
				isDOBChoosed = true;
				in_tv_dateDay.setText(String.format("%02d",day));
				in_tv_dateMon.setText(all_months.get(String.format("%02d",mon)).toString());
				in_tv_dateYear.setText(String.format("%04d",year));
			}
		}, year, mon, day);
		
		mDP.setTitle("Date");
		mDP.show();
	}
	
	
	public void _add_months() {
		all_months = new HashMap<>();
		all_months.put("01", "January");
		all_months.put("02", "February");
		all_months.put("03", "March");
		all_months.put("04", "April");
		all_months.put("05", "May");
		all_months.put("06", "June");
		all_months.put("07", "July");
		all_months.put("08", "August");
		all_months.put("09", "September");
		all_months.put("10", "October");
		all_months.put("11", "November");
		all_months.put("12", "September");
	}
	
	
	public void _TransitionManager(final View _view, final double _duration) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
	}
	
	
	public void _email_case() {
		_TransitionManager(content_wrapper, 200);
		email_error.setVisibility(View.VISIBLE);
		email_title.setTextColor(0xFFF44336);
		email_edittext.setTextColor(0xFFF44336);
		final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(email_base,
		"backgroundColor",
		new ArgbEvaluator(),
		0xffEEEEEE,
		0xffF44336);
		backgroundColorAnimator.setDuration(500);
		backgroundColorAnimator.start();
	}
	
	
	public void _bio_case() {
		_TransitionManager(content_wrapper, 200);
		bio_error.setVisibility(View.VISIBLE);
		bio_title.setTextColor(0xFFF44336);
		bio_edittext.setTextColor(0xFFF44336);
		email_error.setVisibility(View.GONE);
		email_title.setTextColor(0xFFBDBDBD);
		email_base.setBackgroundColor(0xFFBDBDBD);
		username_error.setVisibility(View.GONE);
		username_title.setTextColor(0xFFBDBDBD);
		username_base.setBackgroundColor(0xFFBDBDBD);
		final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(bio_base,
		"backgroundColor",
		new ArgbEvaluator(),
		0xffEEEEEE,
		0xffF44336);
		backgroundColorAnimator.setDuration(500);
		backgroundColorAnimator.start();
	}
	
	
	public void _username_case() {
		_TransitionManager(content_wrapper, 200);
		username_error.setVisibility(View.VISIBLE);
		username_title.setTextColor(0xFFF44336);
		username_edittext.setTextColor(0xFFF44336);
		email_error.setVisibility(View.GONE);
		email_title.setTextColor(0xFFBDBDBD);
		email_base.setBackgroundColor(0xFFBDBDBD);
		final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(username_base,
		"backgroundColor",
		new ArgbEvaluator(),
		0xffEEEEEE,
		0xffF44336);
		backgroundColorAnimator.setDuration(500);
		backgroundColorAnimator.start();
	}
	
	
	public void _PickFile(final String _extension) {
		//New Intent 
		Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		i.setType(_extension);
		i = Intent.createChooser(i, "Choose an File");
		startActivityForResult(i, 100);
	}
	
	
	public void _PickFileListener() {
	}
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) { 
		
		// If no sellection was made, we return back to the activity
		if (resultCode != RESULT_OK) {
			SketchwareUtil.showMessage(getApplicationContext(), "File not selected");
			return;
		} else {
			if (requestCode == 100) {
				//File picker returning a Uri
				Uri uri = data.getData();
				//App data directory
				String dataPath = "/data/data/"+getApplicationContext().getPackageName();
				
				Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
				/*
     * Get the column indexes of the data in the Cursor,
     * move to the first row in the Cursor, get the data,
     * and display it.
     */
				int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
				int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
				returnCursor.moveToFirst();
				String fileName = returnCursor.getString(nameIndex);
				String fileSize = Long.toString(returnCursor.getLong(sizeIndex));
				try {
					InputStream in = null;
					OutputStream out = null;
					try {
						// open the user-picked file for reading:
						in = getContentResolver().openInputStream(uri);
						// open the output-file,(to your app data Directory)
						out = new FileOutputStream(new File(dataPath +"/"+fileName));
						// copy the file
						byte[] buffer = new byte[1024];
						int len;
						while ((len = in.read(buffer)) != -1) {
							out.write(buffer, 0, len);
						}
						// Contents are copied
					} finally {
						if (in != null) {
							in.close();
						}
						if (out != null){
							out.close();
						}
					}
				} catch (IOException e) {
					e.printStackTrace(); 
					// handle exception correctly.
				}
				if (fileName.endsWith("jpg") || (fileName.endsWith("jpeg") || (fileName.endsWith("png") || fileName.endsWith("gif")))) {
					// user this code if you have firebase paid plan
					/*
profile_path = dataPath.concat("/".concat(fileName));
dp.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).putFile(Uri.fromFile(new File(dataPath.concat("/".concat(fileName))))).addOnFailureListener(_dp_failure_listener).addOnProgressListener(_dp_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
@Override
public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
return dp.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getDownloadUrl();
}}).addOnCompleteListener(_dp_upload_success_listener);
//old code stable!
_ProgresbarShow("Uploading..[0%] ");
SketchwareUtil.showMessage(getApplicationContext(), "File Uploading....");

String path = dataPath.concat("/".concat(fileName));

File_Name = fileName;

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

                    
                    final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    final String newDp = publicUrl;

// Reference to the user's data in Firebase
                    final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(myUid);

// Update the 'dp' key with the new URL
                    userRef.child("dp").setValue(newDp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()) {
                   SketchwareUtil.showMessage(getApplicationContext(), "DP updated successfully");
                  _ProgresbarDimiss();
            
                   } else {
                   SketchwareUtil.showMessage(getApplicationContext(), "Failed to update DP");
                   _ProgresbarDimiss();
                   }
    }
});


                    SketchwareUtil.showMessage(getApplicationContext(), " file Upload Successful");

                    

                } else {
                    _ProgresbarDimiss();
                   // textview2.setText(res);
                    SketchwareUtil.showMessage(getApplicationContext(), "Upload Failed");
                }
            }
        });
    }
});
*/
					// Show the progress dialog once
					_ProgresbarShow("Uploading..[0%]");
					SketchwareUtil.showMessage(getApplicationContext(), "File Uploading....");
					
					String path = dataPath.concat("/".concat(fileName));
					
					// Generate unique filename: profile_user_yyyyMMdd_HHmmss.ext
					String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault()).format(new java.util.Date());
					String ext = "";
					if (fileName.contains(".")) {
						ext = fileName.substring(fileName.lastIndexOf("."));
					}
					File_Name = "profile_user_" + timeStamp + ext;
					
					OkHttpClient client = new OkHttpClient();
					File file = new File(path);
					
					RequestBody fileBody = RequestBody.create(
					MediaType.parse("image/jpeg"),
					file
					);
					
					ProgressRequestBody progressBody = new ProgressRequestBody(fileBody, 
					new ProgressRequestBody.ProgressListener() {
						@Override
						public void onProgress(long bytesWritten, long contentLength) {
							final int progress = (int) ((bytesWritten * 100) / contentLength);
							
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// Update the existing dialog's message directly
									if (prog != null && prog.isShowing()) {
										prog.setMessage("Uploading..[" + progress + "%]");
									}
								}
							});
						}
					});
					
					Request request = new Request.Builder()
					.url(Url + "/storage/v1/object/ChatApp/" + File_Name)
					.addHeader("apikey", Api)
					.addHeader("Authorization", "Bearer " + Api)
					.addHeader("Content-Type", "image/jpeg")
					.addHeader("x-upsert", "true")
					.post(progressBody)
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
										final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
										final String newDp = publicUrl;
										
										final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(myUid);
										
										userRef.child("dp").setValue(newDp).addOnCompleteListener(new OnCompleteListener<Void>() {
											@Override
											public void onComplete(@NonNull Task<Void> task) {
												if (task.isSuccessful()) {
													SketchwareUtil.showMessage(getApplicationContext(), "DP updated successfully");
												} else {
													SketchwareUtil.showMessage(getApplicationContext(), "Failed to update DP");
												}
												_ProgresbarDimiss();
											}
										});
										
										SketchwareUtil.showMessage(getApplicationContext(), "File Upload Successful");
									} else {
										_ProgresbarDimiss();
										SketchwareUtil.showMessage(getApplicationContext(), "Upload Failed");
									}
								}
							});
						}
					});
				}
			}
		}
	}
	
	
	public void _ProgresbarShow(final String _title) {
		prog = new ProgressDialog(ProfileActivity.this);
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
	
	
	public void _create_account(final String _url) {
		map = new HashMap<>();
		map.put("username", email_edittext.getText().toString());
		map.put("user", username_edittext.getText().toString());
		map.put("bio", bio_edittext.getText().toString());
		map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
		if (isDOBChoosed) {
			map.put("dob", date_text.getText().toString().concat(" ".concat(month_text.getText().toString().concat(" ".concat(year_text.getText().toString())))));
		}
		users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
		map.clear();
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
	
	
	public void _API() {
		Url = "https://ovsmtwnqjfefpzjoznwd.supabase.co";
		Api = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im92c210d25xamZlZnB6am96bndkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODE2ODg5NTIsImV4cCI6MjA5NzI2NDk1Mn0.5b_7bBlv0mSgxiHETCUNum2p08JvS1SFSeNP7enjiMI";
	}
	
}