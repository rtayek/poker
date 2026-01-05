function spoiler(button)
{
	var spoilerbox = button.parentNode.parentNode;
	var borderdiv = spoilerbox.getElementsByTagName("div")[0];
	var hiddendiv = borderdiv.getElementsByTagName("div")[0];

	if(hiddendiv.style.visibility != 'visible') {
		hiddendiv.style.visibility = 'visible';
		button.innerHTML = 'Hide';
	} else {
		hiddendiv.style.visibility = 'hidden';
		button.innerHTML = 'Show';
	}

	return false;
}
