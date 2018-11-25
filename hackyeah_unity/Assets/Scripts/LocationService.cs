using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using Random = System.Random;

public class LocationService : MonoBehaviour
{
	private int iterator, time, timeit;
	private int czestotliwosc = 2;
	public GameObject text, dioda;
	public GameObject text2;
	public StateController stateController;
	private float dLon, dLat;
	private double[] dpattern = {24, 20, 13, 10, 8, 5, 4, 3, 1};

	// Use this for initialization
	void Start () {
		iterator = 0;
		time = 0;
		timeit = 0;
		Input.compass.enabled = true;
		Input.location.Start(0.1f, 0.01f);

		dLat = 52.216528f;
		dLon = 21.017639f;
	}

	int SignalStrength(float destLat, float destLon, float currLat, float currLon, float heading, int maxval)
	{
		var sig = Math.Atan2(Math.Sin(Math.Abs(currLon - destLon)) * Math.Cos(destLat),
			Math.Cos(currLat) * Math.Sin(destLat) -
			Math.Sin(currLat) * Math.Cos(destLat) * Math.Cos(Math.Abs(currLon - destLon)));
		var y = Math.Sin(destLon - currLon) * Math.Cos(destLat);
		var x = Math.Cos(currLat) * Math.Sin(destLat) -
			Math.Sin(currLat) * Math.Cos(destLat) * Math.Cos(destLon - currLon);
		var brng = (180 / Math.PI) * Math.Atan2(y, x);
		var diff = Math.Abs(heading+180 - brng);
		var realdiff = Math.Min(diff, 360 - diff);

		var R = 6371e3;
		var φ1 = (Math.PI / 180) * currLat;
		var φ2 = (Math.PI / 180) * destLat;
		var Δφ = (Math.PI / 180) * (destLat - currLat);
		var Δλ = (Math.PI / 180) * (destLon - currLon);

		var a = Math.Sin(Δφ / 2) * Math.Sin(Δφ / 2) +
			Math.Cos(φ1) * Math.Cos(φ2) *
			Math.Sin(Δλ / 2) * Math.Sin(Δλ / 2);
		var c = 2 * Math.Atan2(Math.Sqrt(a), Math.Sqrt(1 - a));

		var d = R * c;

		var dist = Math.Min(100, d);
		var sqrdist = dist * dist / 10000;

		var sqrdiff = realdiff * realdiff * realdiff * realdiff / (180 * 180 * 180 * 180);
		var minval = 2;
		int ret = minval + (int) ((maxval - minval) * (sqrdiff * sqrdist));
		return ret;
	}

	int SignalStrength(float destLat, float destLon, float currLat, float currLon, float heading, int maxval, double distance)
	{
		var sig = Math.Atan2(Math.Sin(Math.Abs(currLon - destLon)) * Math.Cos(destLat),
			Math.Cos(currLat) * Math.Sin(destLat) -
			Math.Sin(currLat) * Math.Cos(destLat) * Math.Cos(Math.Abs(currLon - destLon)));
		var y = Math.Sin(destLon - currLon) * Math.Cos(destLat);
		var x = Math.Cos(currLat) * Math.Sin(destLat) -
			Math.Sin(currLat) * Math.Cos(destLat) * Math.Cos(destLon - currLon);
		var brng = (180 / Math.PI) * Math.Atan2(y, x);
		var diff = Math.Abs(heading + 180 - brng)%360;
		var realdiff = Math.Min(diff, 360 - diff);

		var d = distance;

		var dist = Math.Min(100, d);
		var sqrdist = dist / 25;
		var sqrdiff = Math.Pow(Math.Abs(realdiff / (180)),1);
		var minval = 2;
		var ret = 50 + (int)((maxval - 50) * (sqrdiff));
		ret = (int) (ret * sqrdist);
		return ret;
	}
	// Update is called once per frame
	void Update ()
	{
		time++;
		if (time % 800 == 0)
		{
			timeit++;
		}
		var signal = SignalStrength(90.0f, 0.0f, Input.location.lastData.latitude, Input.location.lastData.longitude,
			Input.compass.trueHeading, 150, dpattern[Math.Min(timeit, dpattern.Length - 1)]);

		iterator++;



		if (signal <= 2)
		{
			text.GetComponent<Text>().text = "Rozejrzyj się dookoła";
			text.GetComponent<Text>().fontSize = 100;
			stateController.triggerChests ();
			dioda.SetActive (false);
			text.SetActive (false);
			text2.SetActive (true);
		}
		else
		{
			if (iterator >= signal)
			{
				iterator = 0;

				changeColor();
			}
			text.GetComponent<Text>().text = "Location:\n" +
				Input.location.lastData.latitude.ToString() + "\n" +
				Input.location.lastData.longitude.ToString() + "\n" +
				"Compass: " + Input.compass.trueHeading.ToString();
		}


	}

	void changeColor()
	{
		if (dioda.GetComponent<Image>().color == Color.yellow)
		{
			dioda.GetComponent<Image>().color = Color.black;
		}
		else
		{
			dioda.GetComponent<Image>().color = Color.yellow;
		}
	}

}