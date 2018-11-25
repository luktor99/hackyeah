using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DirectionArrowController : MonoBehaviour {

	float game_point_langtitude = 50.0f;
	float game_point_longtitude = 50.0f;

	float device_langtitude;
	float device_longtitude;

	long vibration_time_ms = 500;


	AndroidJavaObject vibrator;



	// Use this for initialization
	void Start () {
		AndroidJavaClass jc = new AndroidJavaClass ("com.hackyeah.lotto_app.UnityPlayer");
		AndroidJavaObject ac = jc.GetStatic<AndroidJavaObject> ("currentActivity");// Get the Current Activity from the Unity Player.
		vibrator = ac.Call<AndroidJavaObject>("getSystemService", "vibrator");// Then get the Vibration Service from the Current Activity.
	}
	
	// Update is called once per frame
	void Update () {
		vibrator.Call ("vibrate", vibration_time_ms);
	}


}
