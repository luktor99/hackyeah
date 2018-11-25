using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class StateController : MonoBehaviour {

	public List<GameObject> chests;

	enum GameState
	{
		
	}

	// Use this for initialization
	void Start () {
			
	}

	int drawWinningChest() {
		return Random.Range (1, 4);
	}

	public void triggerChests() {
		foreach (var chest in chests) {
			chest.GetComponentInChildren<ChestAnimationController> ().triggerAnimators (0);
		}
	}


}
