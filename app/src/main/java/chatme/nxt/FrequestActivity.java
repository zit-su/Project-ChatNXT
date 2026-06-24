package chatme.nxt;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class FrequestActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String uuid = "";
	private String Myuuid = "";
	private HashMap<String, Object> get = new HashMap<>();
	private String fontName = "";
	private String localFontPath = "";
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private CircleImageView circleimageview1;
	private TextView textview1;
	private TextView textview2;
	private Button button1;
	
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
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
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.frequest);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		circleimageview1 = findViewById(R.id.circleimageview1);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		button1 = findViewById(R.id.button1);
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		fauth = FirebaseAuth.getInstance();
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(uuid)) {
					if (!"null".equals(_childValue.get("dp").toString())) {
						Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(circleimageview1);
					} else {
						circleimageview1.setImageResource(R.drawable.logo);
						get = new HashMap<>();
						get.put("dp", file.getString("nulldp", ""));
						users.child(uuid).updateChildren(get);
						get.clear();
					}
					textview1.setText(_childValue.get("username").toString());
					textview2.setText(" @".concat(_childValue.get("user").toString()));
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(uuid)) {
					if (!"null".equals(_childValue.get("dp").toString())) {
						Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(circleimageview1);
					} else {
						circleimageview1.setImageResource(R.drawable.logo);
					}
					textview1.setText(_childValue.get("username").toString());
					textview2.setText("@".concat(_childValue.get("user").toString()));
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
		if (getIntent().hasExtra("user2")) {
			uuid = getIntent().getStringExtra("user2");
			Myuuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
			/*
// Get the UIDs (replace with actual logic to retrieve friendUid)
final String friendUid = uuid;  // Replace with the actual friend's UID
final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

// Firebase references
final DatabaseReference friendRequestRef = FirebaseDatabase.getInstance().getReference("FriendRequests");
final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
final DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox");

// Retrieve current user's data (e.g., "dp" and "username")
usersRef.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            // Store dp and username in final variables to avoid scope issues
            final String myDp = dataSnapshot.child("dp").getValue(String.class);
            final String myUsername = dataSnapshot.child("username").getValue(String.class);

            // Check if the friend is already added in the inbox
            inboxRef.child(myUid).child(friendUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot inboxSnapshot) {
                    if (inboxSnapshot.exists()) {
                        // Friend is already added, update button state
                        button1.setText("Already Friend");
                        button1.setEnabled(false);
                    } else {
                        // Check if a friend request was already sent
                        friendRequestRef.child(friendUid).child(myUid)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot requestSnapshot) {
                                    if (requestSnapshot.exists()) {
                                        // Request already sent, update button state
                                        button1.setText("Requested");
                                        button1.setEnabled(false);
                                    } else {
                                        // Allow sending a friend request
                                        button1.setText("Add Friend");
                                        button1.setEnabled(true);

                                        // Set click listener to send the friend request
                                        button1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                // Disable button and show progress
                                                button1.setEnabled(false);
                                                button1.setText("Requesting...");

                                                // Create the friend request object with "dp" and "username"
                                                Map<String, String> requestData = new HashMap<>();
                                                requestData.put("dp", myDp);  // Now accessible
                                                requestData.put("username", myUsername);  // Now accessible
                                                requestData.put("status", "pending");
                                                requestData.put("id", myUid);
                                                requestData.put("senderId", friendUid);

                                                // Send the friend request
                                                friendRequestRef.child(friendUid).child(myUid)
                                                    .setValue(requestData)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                // Request sent successfully
                                                                button1.setText("Requested");
                                                            } else {
                                                                // Handle failure
                                                                button1.setEnabled(true);
                                                                button1.setText("Add Friend");
                                                            }
                                                        }
                                                    });
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle request check error
                                }
                            });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle inbox check error
                }
            });
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        // Handle error fetching user data
    }
});

*/
			// Firebase references
			final String friendUid = uuid;  // Replace with the actual friend's UID
			final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
			final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
			final DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox");
			final DatabaseReference friendRequestRef = FirebaseDatabase.getInstance().getReference("FriendRequests");
			
			// Check the user's type
			usersRef.child(friendUid).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					if (snapshot.exists()) {
						String userType = snapshot.getValue(String.class);
						if ("group".equals(userType)) {
							// Add group to inbox
							usersRef.child(friendUid).addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot userSnapshot) {
									if (userSnapshot.exists()) {
										String groupDp = userSnapshot.child("dp").getValue(String.class);
										String groupName = userSnapshot.child("username").getValue(String.class);
										
										Map<String, String> groupData = new HashMap<>();
										groupData.put("dp", groupDp != null ? groupDp : "https://cdn-icons-png.flaticon.com/128/12882/12882578.png");
										groupData.put("username", groupName);
										groupData.put("type", "group");
										groupData.put("id", friendUid);
										
										inboxRef.child(myUid).child(friendUid).setValue(groupData)
										.addOnCompleteListener(new OnCompleteListener<Void>() {
											@Override
											public void onComplete(@NonNull Task<Void> task) {
												if (task.isSuccessful()) {
													finish();
													Toast.makeText(getApplicationContext(), "Group added to inbox!", Toast.LENGTH_SHORT).show();
												} else {
													Toast.makeText(getApplicationContext(), "Failed to add group to inbox!", Toast.LENGTH_SHORT).show();
												}
											}
										});
									}
								}
								
								@Override
								public void onCancelled(@NonNull DatabaseError error) {
									Toast.makeText(getApplicationContext(), "Error fetching group details: " + error.getMessage(), Toast.LENGTH_SHORT).show();
								}
							});
						} else {
							// If not a group, process friend request logic
							usersRef.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									if (dataSnapshot.exists()) {
										final String myDp = dataSnapshot.child("dp").getValue(String.class);
										final String myUsername = dataSnapshot.child("username").getValue(String.class);
										
										inboxRef.child(myUid).child(friendUid).addListenerForSingleValueEvent(new ValueEventListener() {
											@Override
											public void onDataChange(@NonNull DataSnapshot inboxSnapshot) {
												if (inboxSnapshot.exists()) {
													button1.setText("Already Friend");
													button1.setEnabled(false);
												} else {
													friendRequestRef.child(friendUid).child(myUid)
													.addListenerForSingleValueEvent(new ValueEventListener() {
														@Override
														public void onDataChange(@NonNull DataSnapshot requestSnapshot) {
															if (requestSnapshot.exists()) {
																button1.setText("Requested");
																button1.setEnabled(false);
															} else {
																button1.setText("Add Friend");
																button1.setEnabled(true);
																
																button1.setOnClickListener(new View.OnClickListener() {
																	@Override
																	public void onClick(View v) {
																		button1.setEnabled(false);
																		button1.setText("Requesting...");
																		
																		Map<String, String> requestData = new HashMap<>();
																		requestData.put("dp", myDp);
																		requestData.put("username", myUsername);
																		requestData.put("status", "pending");
																		requestData.put("id", myUid);
																		requestData.put("senderId", friendUid);
																		
																		friendRequestRef.child(friendUid).child(myUid)
																		.setValue(requestData)
																		.addOnCompleteListener(new OnCompleteListener<Void>() {
																			@Override
																			public void onComplete(@NonNull Task<Void> task) {
																				if (task.isSuccessful()) {
																					button1.setText("Requested");
																				} else {
																					button1.setEnabled(true);
																					button1.setText("Add Friend");
																				}
																			}
																		});
																	}
																});
															}
														}
														
														@Override
														public void onCancelled(@NonNull DatabaseError databaseError) {
														}
													});
												}
											}
											
											@Override
											public void onCancelled(@NonNull DatabaseError databaseError) {
											}
										});
									}
								}
								
								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
								}
							});
						}
					} else {
						Toast.makeText(getApplicationContext(), "User type not found.", Toast.LENGTH_SHORT).show();
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError error) {
					Toast.makeText(getApplicationContext(), "Error checking user type: " + error.getMessage(), Toast.LENGTH_SHORT).show();
				}
			});
			
		} else {
			SketchwareUtil.showMessage(getApplicationContext(), "Sorry, Can't Access user profile!");
			finish();
		}
		button1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)22, 0xFF1A3764));
		_SetFont("light");
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