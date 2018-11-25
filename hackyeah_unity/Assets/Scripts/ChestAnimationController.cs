using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ChestAnimationController : MonoBehaviour {

	public Animator chest_animator;
	public Animator balls_animator;
	public Text win_val_text;

	string animator_trigger = "OpenChest";

	public void Start() {
		win_val_text.gameObject.SetActive (false);
	}

	public void triggerAnimators(int win_value){

		chest_animator.SetTrigger (animator_trigger);
		if (win_value != 0) {
			balls_animator.SetTrigger (animator_trigger);
			win_val_text.text = win_value.ToString ();
			win_val_text.gameObject.SetActive (true);
		}

	}

	public void setToChosenChest(){
		animator_trigger = "OpenChest";
	}
		
}
