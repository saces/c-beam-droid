package org.c_base.c_beam.activity;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

import org.c_base.c_beam.R;
import org.c_base.c_beam.Settings;
import org.c_base.c_beam.domain.C_beam;
import org.c_base.c_beam.domain.User;

public class SettingsActivity extends SherlockPreferenceActivity {

//    public static final String KEY_PREF_MQTT_USER = "pref_key_mqtt_user";
//    public static final String KEY_PREF_MQTT_PASSWORD = "pref_key_mqtt_password";
//    public static final String KEY_PREF_MQTT_URI = "pref_key_mqtt_uri";
//    public static final String KEY_PREF_MQTT_TLS = "pref_key_mqtt_tls";
//    public static final String KEY_PREF_DEFAULT_URL = "pref_key_default_url";

	C_beam c_beam = C_beam.getInstance(); //new C_beam(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		c_beam.setActivity(this);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

		final Context context = getApplicationContext();
		
		User user = c_beam.getCurrentUser();
		
		CheckBoxPreference stats_enabled = (CheckBoxPreference) findPreference(Settings.STATS_ENABLED);
		CheckBoxPreference push_missions = (CheckBoxPreference) findPreference(Settings.PUSH_MISSIONS);
		CheckBoxPreference push_boarding = (CheckBoxPreference) findPreference(Settings.PUSH_BOARDING);
		CheckBoxPreference push_eta = (CheckBoxPreference) findPreference(Settings.PUSH_ETA);
		
		if (user != null && c_beam.isInCrewNetwork()) {
			stats_enabled.setChecked(user.isStats_enabled());
			push_missions.setChecked(user.isPush_missions());
			push_boarding.setChecked(user.isPush_boarding());
			push_eta.setChecked(user.isPush_eta());
		} else {
			stats_enabled.setEnabled(false);
			push_missions.setEnabled(false);
			push_boarding.setEnabled(false);
			push_eta.setEnabled(false);
		}
		
		stats_enabled.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				c_beam.setStatsEnabled((Boolean) newValue);
//				if (((Boolean) newValue).booleanValue()) {
//
//				} else {
//
//				}
				return true;
			}
		});
		
		push_missions.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				c_beam.setPushMissions((Boolean) newValue);
				return true;
			}
		});
		
		push_boarding.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				c_beam.setPushBoarding((Boolean) newValue);
				return true;
			}
		});
		
		push_eta.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				c_beam.setPushETA((Boolean) newValue);
				return true;
			}
		});
		
		CheckBoxPreference push = (CheckBoxPreference) findPreference(Settings.PUSH);
		push.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {

                Toast.makeText(context, "Please restart device to apply this change",
                        Toast.LENGTH_LONG).show();

				/*
				TODO: later please do everything correctly - also listen to preference in withGCM flavor
				if (((Boolean) newValue).booleanValue()) {
					GCMManager.register(context);
				} else {
					GCMManager.unregister(context);
				}
				*/
                //GCMFacade.registerGCM(context, (Boolean) newValue);

				return true;
			}
		});
	}
}
