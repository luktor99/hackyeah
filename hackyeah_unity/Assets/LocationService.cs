using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LocationService : MonoBehaviour {

	// Use this for initialization
	void Start () {
		Input.location.Start ();
	}
		
	// Update is called once per frame
	void Update () {
		// Access granted and location value could be retrieved
		Debug.Log("Location: " + Input.location.lastData.latitude + " " + Input.location.lastData.longitude + " " + Input.location.lastData.altitude + " " + Input.location.lastData.horizontalAccuracy + " " + Input.location.lastData.timestamp);
	}


}
