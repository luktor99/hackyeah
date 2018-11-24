using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DirectionArrowController : MonoBehaviour {

	float game_point_langtitude = 50.0f;
	float game_point_longtitude = 50.0f;

	float device_langtitude;
	float device_longtitude;

	long vibration_time_ms = 500;

	AndroidJavaClass unity= new AndroidJavaClass("com.unity3d.player.UnityPlayer");
	AndroidJavaObject ca = unity.GetStatic("currentActivity");
	AndroidJavaClass vibratorClass = new AndroidJavaClass("android.os.Vibrator");
	AndroidJavaObject vibratorService = ca.Call("getSystemService",ca.GetStatic("VIBRATOR_SERVICE"));
	// Use this for initialization
	void Start () {

	
	}
	
	// Update is called once per frame
	void Update () {
		vibratorService.Call("vibrate", 	vibration_time_ms);
	}


}
