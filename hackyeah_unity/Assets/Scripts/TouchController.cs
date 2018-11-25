using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TouchController : MonoBehaviour {

	public StateController stateController;

	int[] win_values = {0,0,0,0,0,0,10,20,50,100};

	void Update()
	{
		if ((Input.touchCount > 0) && (Input.GetTouch(0).phase == TouchPhase.Began))
		{
			Ray raycast = Camera.main.ScreenPointToRay(Input.GetTouch(0).position);
			RaycastHit raycastHit;
			if (Physics.Raycast(raycast, out raycastHit))
				if (raycastHit.collider.CompareTag("Chest"))
				{
					Debug.Log("Chest clicked");
					int win_val_index = Random.Range (1, win_values.Length);
					raycastHit.collider.gameObject.GetComponent<ChestAnimationController> ().triggerAnimators (win_values[win_val_index]);
				}

		}
	}

}
