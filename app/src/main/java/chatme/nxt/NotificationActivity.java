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
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
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

public class NotificationActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String fontName = "";
	private String typeace = "";
	private double request_count = 0;
	private String myf = "";
	private String myUid = "";
	private String localFontPath = "";
	
	private ArrayList<HashMap<String, Object>> map = new ArrayList<>();
	
	private LinearLayout linear_head;
	private LinearLayout linear2;
	private TextView header_text;
	private ImageView settings;
	private RecyclerView recyclerview1;
	private LinearLayout linear_no_notifications;
	private ImageView imageview1;
	private TextView title;
	private TextView sub_title;
	
	private Intent a = new Intent();
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
	private DatabaseReference FriendRequests = _firebase.getReference("FriendRequests");
	private ChildEventListener _FriendRequests_child_listener;
	private SharedPreferences file;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.notification);
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
		linear_head = findViewById(R.id.linear_head);
		linear2 = findViewById(R.id.linear2);
		header_text = findViewById(R.id.header_text);
		settings = findViewById(R.id.settings);
		recyclerview1 = findViewById(R.id.recyclerview1);
		linear_no_notifications = findViewById(R.id.linear_no_notifications);
		imageview1 = findViewById(R.id.imageview1);
		title = findViewById(R.id.title);
		sub_title = findViewById(R.id.sub_title);
		fauth = FirebaseAuth.getInstance();
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		
		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				a.setClass(getApplicationContext(), SettingsActivity.class);
				startActivity(a);
				finish();
			}
		});
		
		_FriendRequests_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				FriendRequests.addListenerForSingleValueEvent(new ValueEventListener() {
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
				FriendRequests.addListenerForSingleValueEvent(new ValueEventListener() {
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
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				FriendRequests.addListenerForSingleValueEvent(new ValueEventListener() {
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
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		FriendRequests.addChildEventListener(_FriendRequests_child_listener);
		
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
			_friend_requests();
		} else {
			a.setClass(getApplicationContext(), LogActivity.class);
			startActivity(a);
			finish();
		}
	}
	
	@Override
	public void onBackPressed() {
		a.setClass(getApplicationContext(), MainActivity.class);
		startActivity(a);
		finish();
	}
	public void _UI() {
		myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
							}
						}
					}
					
					// Show dot if pending requests exist, else hide
					if (request_count > 0) {
						linear_no_notifications.setVisibility(View.GONE);
						recyclerview1.setVisibility(View.VISIBLE);
					} else {
						linear_no_notifications.setVisibility(View.VISIBLE);
						recyclerview1.setVisibility(View.GONE);
					}
					
					// Show request count (modify this part as needed)
					
				}
				
				@Override
				public void onCancelled(DatabaseError error) {
					Log.e("Firebase", "Database error: " + error.getMessage());
				}
			});
		}
		
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
	}
	
	
	public void _friend_requests() {
		FriendRequests.removeEventListener(_FriendRequests_child_listener);
		myf = "FriendRequests/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid());
		FriendRequests = _firebase.getReference(myf);
		FriendRequests.addChildEventListener(_FriendRequests_child_listener);
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
			View _v = _inflater.inflate(R.layout.request, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final RelativeLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final ImageView ingonre = _view.findViewById(R.id.ingonre);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = _view.findViewById(R.id.circleimageview1);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			
			textview2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFF434065));
			linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFFFFFFF));
			if (_data.get((int)_position).containsKey("username")) {
				textview1.setText(_data.get((int)_position).get("username").toString());
			}
			if (_data.get((int)_position).containsKey("dp")) {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("dp").toString())).into(circleimageview1);
			}
			if (_data.get((int)_position).containsKey("id")) {
				//Get the UIDs for the current user and friend
				final String friendUid = _data.get(_position).get("id").toString();
				final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
				
				// Firebase references
				final DatabaseReference friendRequestRef = FirebaseDatabase.getInstance().getReference("FriendRequests");
				final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
				final DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox");
				
				// Fetch friend's profile data and username (final to be used inside inner classes)
				usersRef.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot snapshot) {
						if (snapshot.exists()) {
							final String myDp = snapshot.child("dp").getValue(String.class);
							final String myUsername = snapshot.child("username").getValue(String.class);
							
							// Check if the friend has sent a request to the current user
							friendRequestRef.child(myUid).child(friendUid)
							.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									if (dataSnapshot.exists()) {
										// Friend sent a request, show the "Accept" button
										textview2.setText("Accept");
										textview2.setEnabled(true);
										
										// Set click listener for accepting the request
										textview2.setOnClickListener(new View.OnClickListener() {
											@Override
											public void onClick(View v) {
												textview2.setEnabled(false);
												textview2.setText("Accepting...");
												
												// Copy friend's data to inbox/myUid/friendUid
												usersRef.child(friendUid).addListenerForSingleValueEvent(new ValueEventListener() {
													@Override
													public void onDataChange(@NonNull DataSnapshot friendDataSnapshot) {
														if (friendDataSnapshot.exists()) {
															inboxRef.child(myUid).child(friendUid)
															.setValue(friendDataSnapshot.getValue())
															.addOnCompleteListener(new OnCompleteListener<Void>() {
																@Override
																public void onComplete(@NonNull Task<Void> task1) {
																	if (task1.isSuccessful()) {
																		usersRef.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
																			@Override
																			public void onDataChange(@NonNull DataSnapshot myDataSnapshot) {
																				if (myDataSnapshot.exists()) {
																					inboxRef.child(friendUid).child(myUid)
																					.setValue(myDataSnapshot.getValue())
																					.addOnCompleteListener(new OnCompleteListener<Void>() {
																						@Override
																						public void onComplete(@NonNull Task<Void> task2) {
																							if (task2.isSuccessful()) {
																								// Update the request status to "accepted"
																								friendRequestRef.child(myUid).child(friendUid)
																								.child("status")
																								.setValue("accepted")
																								.addOnCompleteListener(new OnCompleteListener<Void>() {
																									@Override
																									public void onComplete(@NonNull Task<Void> task3) {
																										if (task3.isSuccessful()) {
																											textview2.setText("Accepted");
																										} else {
																											SketchwareUtil.showMessage(getApplicationContext(), "Failed to update status");
																											textview2.setEnabled(true);
																											textview2.setText("Accept");
																										}
																									}
																								});
																							} else {
																								SketchwareUtil.showMessage(getApplicationContext(), "Failed to copy my data");
																								textview2.setEnabled(true);
																								textview2.setText("Accept");
																							}
																						}
																					});
																				} else {
																					SketchwareUtil.showMessage(getApplicationContext(), "Failed to fetch my data");
																				}
																			}
																			
																			@Override
																			public void onCancelled(@NonNull DatabaseError databaseError) {
																				SketchwareUtil.showMessage(getApplicationContext(), "Error: " + databaseError.getMessage());
																			}
																		});
																	} else {
																		SketchwareUtil.showMessage(getApplicationContext(), "Failed to copy friend's data");
																		textview2.setEnabled(true);
																		textview2.setText("Accept");
																	}
																}
															});
														} else {
															SketchwareUtil.showMessage(getApplicationContext(), "Failed to fetch friend's data");
														}
													}
													
													@Override
													public void onCancelled(@NonNull DatabaseError databaseError) {
														SketchwareUtil.showMessage(getApplicationContext(), "Error: " + databaseError.getMessage());
													}
												});
											}
										});
									} else {
										textview2.setVisibility(View.GONE);
									}
								}
								
								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
									SketchwareUtil.showMessage(getApplicationContext(), "Error: " + databaseError.getMessage());
								}
							});
						} else {
							SketchwareUtil.showMessage(getApplicationContext(), "Failed to fetch my profile");
						}
					}
					
					@Override
					public void onCancelled(@NonNull DatabaseError error) {
						SketchwareUtil.showMessage(getApplicationContext(), "Error: " + error.getMessage());
					}
				});
				
				
			}
			if (_data.get((int)_position).containsKey("status")) {
				if ("accepted".equals(_data.get((int)_position).get("status").toString())) {
					linear1.setVisibility(View.GONE);
				} else {
					linear1.setVisibility(View.VISIBLE);
					request_count++;
				}
			} else {
				linear1.setVisibility(View.VISIBLE);
			}
			if (file.contains("cust_font")) {
				if (FileUtil.isExistFile(file.getString("font_url", ""))) {
					textview1.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					textview2.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
					textview3.setTypeface(Typeface.createFromFile(file.getString("font_url", "")));
					
				} else {
					textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medium.ttf"), 0);
					textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
					textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
				}
			} else {
				textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medium.ttf"), 0);
				textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
				textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/light.ttf"), 0);
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