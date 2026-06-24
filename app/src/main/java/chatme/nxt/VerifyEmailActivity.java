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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.regex.*;
import org.json.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.CountDownTimer;

public class VerifyEmailActivity extends AppCompatActivity {
	
	private String text = "";
	private String fontName = "";
	private String typeace = "";
	private String localFontPath = "";
	
	private LinearLayout linear1;
	private LinearLayout linear4;
	private LinearLayout linear2;
	private TextView textview3;
	private LinearLayout linear5;
	private LinearLayout linear3;
	private ImageView mail;
	private TextView textview1;
	private TextView textview2;
	private TextView textview4;
	
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
	private SharedPreferences file;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.verify_email);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear4 = findViewById(R.id.linear4);
		linear2 = findViewById(R.id.linear2);
		textview3 = findViewById(R.id.textview3);
		linear5 = findViewById(R.id.linear5);
		linear3 = findViewById(R.id.linear3);
		mail = findViewById(R.id.mail);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		textview4 = findViewById(R.id.textview4);
		fauth = FirebaseAuth.getInstance();
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		
		textview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
				
				if (user != null) {
					user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
						@Override
						public void onComplete(Task<Void> task) {
							if (user.isEmailVerified()) {
								// Verified
								startActivity(new Intent(getApplicationContext(), MainActivity.class));
								finish();
							} else {
								// Not verified
								SketchwareUtil.showMessage(getApplicationContext(), "Please verify your email first");
							}
						}
					});
				}
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
				if (_success) {
					SketchwareUtil.showMessage(getApplicationContext(), "confirmation Email sent!");
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
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
		_SetFont("light");
		_UI();
	}
	
	public void _UI() {
		textview4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)25, 0x0DFF0000));
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		
		if (user != null) {
			user.sendEmailVerification()
			.addOnCompleteListener(fauth_emailVerificationSentListener);
			
			Toast.makeText(getApplicationContext(),
			"Resending Mail...",
			Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(),
			"Please login again",
			Toast.LENGTH_SHORT).show();
			
			startActivity(new Intent(getApplicationContext(), LogActivity.class));
			finish();
		}
		// Set Status Bar Color
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(0xFFFFFFFF);
		
		// Check if user is logged in
		if (FirebaseAuth.getInstance().getCurrentUser() != null) {
			
			String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
			
			String text = "We have sent email to " + email +
			" to confirm the validity of your email address. After receiving the email, follow the link provided to complete your @registration.";
			
			TextView textView2 = findViewById(R.id.textview2);
			SpannableString spannableString2 = new SpannableString(text);
			
			Pattern emailPattern = Patterns.EMAIL_ADDRESS;
			Matcher emailMatcher = emailPattern.matcher(text);
			
			if (emailMatcher.find()) {
				final String foundEmail = emailMatcher.group();
				
				spannableString2.setSpan(new ClickableSpan() {
					@Override
					public void onClick(@NonNull View widget) {
						Toast.makeText(getApplicationContext(),
						"Email clicked: " + foundEmail,
						Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void updateDrawState(@NonNull TextPaint ds) {
						super.updateDrawState(ds);
						ds.setColor(Color.BLUE);
						ds.setUnderlineText(true);
					}
				}, emailMatcher.start(), emailMatcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			
			textView2.setText(spannableString2);
			textView2.setMovementMethod(LinkMovementMethod.getInstance());
			
			// Resend confirmation mail text
			final TextView textView3 = findViewById(R.id.textview3);
			final String originalText = "If you not got any mail Resend confirmation mail";
			
			SpannableStringBuilder spannableString3 = new SpannableStringBuilder(originalText);
			
			String targetText = "Resend confirmation mail";
			int startIndex = originalText.indexOf(targetText);
			int endIndex = startIndex + targetText.length();
			
			if (startIndex != -1) {
				
				spannableString3.setSpan(new ForegroundColorSpan(Color.BLUE),
				startIndex, endIndex,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				spannableString3.setSpan(new StyleSpan(Typeface.BOLD),
				startIndex, endIndex,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				spannableString3.setSpan(new ClickableSpan() {
					
					boolean canResend = true;
					
					@Override
					public void onClick(@NonNull View widget) {
						
						if (canResend) {
							
							canResend = false;
							
							FirebaseAuth.getInstance()
							.getCurrentUser()
							.sendEmailVerification()
							.addOnCompleteListener(fauth_emailVerificationSentListener);
							
							Toast.makeText(getApplicationContext(),
							"Resending Mail...",
							Toast.LENGTH_SHORT).show();
							
							new CountDownTimer(60000, 1000) {
								@Override
								public void onTick(long millisUntilFinished) {
									textView3.setText("Please wait " +
									(millisUntilFinished / 1000) +
									" seconds before resending mail");
								}
								
								@Override
								public void onFinish() {
									canResend = true;
									
									SpannableStringBuilder resetText =
									new SpannableStringBuilder(originalText);
									
									resetText.setSpan(new ForegroundColorSpan(Color.BLUE),
									startIndex, endIndex,
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
									
									resetText.setSpan(new StyleSpan(Typeface.BOLD),
									startIndex, endIndex,
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
									
									resetText.setSpan(thisClickableSpan,
									startIndex, endIndex,
									Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
									
									textView3.setText(resetText);
									textView3.setMovementMethod(LinkMovementMethod.getInstance());
								}
								
								ClickableSpan thisClickableSpan = thisClickable();
							}.start();
							
						} else {
							Toast.makeText(getApplicationContext(),
							"Please wait 60 seconds",
							Toast.LENGTH_SHORT).show();
						}
					}
					
					public ClickableSpan thisClickable() {
						return this;
					}
					
					@Override
					public void updateDrawState(@NonNull TextPaint ds) {
						super.updateDrawState(ds);
						ds.setUnderlineText(false);
					}
					
				}, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			
			textView3.setText(spannableString3);
			textView3.setMovementMethod(LinkMovementMethod.getInstance());
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